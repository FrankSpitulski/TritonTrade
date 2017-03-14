package com.tmnt.tritontrade.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;

import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

/**
 * Static server class for interaction with the database
 * Server to be accessed with https://github.com/mevdschee/php-crud-api
 */

public class Server {
    //Information of Frank's Server

    //domain name of server
    final private static String serverName = "https://tritontrade.ddns.net";

    /**
     * Returns the domain name of the database server
     *
     * @return a copy of the server name
     */
    public static String getServerName(){
        return serverName + "";
    }

    /**
     * removes the outer json object from the array
     * @param json
     * @return stripped json
     */
    static String stripOuterJson(String json){
        return json.substring(9, json.length() - 1);
    }

    /**
     * calls the verification email code on the server
     *
     * @param email email to send to
     * @return null on fail, verification string on success
     */
    private static String sendEmailVerification(String email) throws IOException {
        String verificationString = new BigInteger(130, new Random()).toString(32);

        String response = httpGetRequest("/db/sendEmailValidation.php?validation=" +
                verificationString + "&email=" + email);

        if (response.equals("")
                || response.equals("Mailer Error: You must provide at least one recipient email address.")) {
            return null;
        }
        return verificationString;
    }

    /**
     * sends an email to the given email if it exists providing a link to set their password
     * @param email email to send to
     * @return whether or not the operation was successful
     * @throws IOException
     */
    public static boolean sendPasswordResetEmail(String email) throws IOException{
        // check to see if there is an email in the database
        String response = httpGetRequest("/db/api.php/users?filter[]=email,eq," + email + "&transform=1");

        ArrayList<User> users = filterDeletedUsers(jsonToUser(response));
        if(users.size() == 0){
            return false;
        }

        // must be verified
        if(!users.get(0).getVerified()){
            throw new IOException("Unverified user");
        }

        // set verification string, overwrites old string
        String verificationString = new BigInteger(130, new Random()).toString(32);
        users.get(0).setEmailVerificationLink(verificationString);
        modifyExistingUser(users.get(0));


        response = httpGetRequest("/db/sendPasswordResetEmail.php?validation=" +
                verificationString + "&email=" + email);
        if (response.equals("")
                || response.equals("Mailer Error: You must provide at least one recipient email address.")) {
            return false;
        }
        return true;
    }

    /**
     * Add a post to the database
     *
     * @throws IOException Server could not process request
     * @return The post created by the operation, null + exception if error occurred
     */
    public static Post addPost(String productName, ArrayList<String> photos, String description,
                               float price, ArrayList<String> tags, int profileID,
                               boolean selling, String contactInfo)
            throws IOException {

        // get postID
        int postID = new Integer(httpGetRequest("/db/postCount.php")).intValue();

        // get unused ID
        while (searchPostIDs(postID) != null) {
            postID++;
        }

        //Make array list of posts as json converts array lists of posts as input
        ArrayList<Post> newPostList = new ArrayList<Post>();
        Post post = new Post(productName, photos, description, price, tags, profileID,
                postID, selling, true, new Date(), contactInfo, false);
        newPostList.add(post);

        //Open connection to server and write json to it
        URL url = new URL(serverName + "/db/api.php/posts");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        OutputStreamWriter out = new OutputStreamWriter(
                connection.getOutputStream());
        out.write(stripOuterJson(postToJson(newPostList)));
        out.close();
        String response = readStream(connection.getInputStream());

        //if error happened, return false
        if (response.equals("null") || response.contains("Not found")) {
            Log.d("DEBUG","COULD NOT UPLOAD USER FOR SOME REASON");
            throw new IOException("Server could not process request");
        }

        try {
            // add post to user's post history
            User user = searchUserIDs(post.getProfileID());
            user.addToPostHistory(post.getPostID());
            modifyExistingUser(user);
        }catch(IOException e){
            post.setDeleted(true);
            modifyExistingPost(post);
            throw new IOException("Could not add user.");
        }
        //return success
        return post;
    }

    /**
     * Adds a user to the database, THe email must be a ucsd email, and must not already have a
     * user registered with that email
     *
     * @throws IOException If Server throws an error, will return IOEXception, such as from
     *   a non ucsd email, duplicate email, or other failure
     * @throws IllegalArgumentException The input data to the User is not valid
     * @return The user created by the method in the server
     */
    public static User addNewUser(String name, String photo, String bio,
                                     String mobileNumber, String email, String password)
            throws IOException, IllegalArgumentException{

        // Makes sure that the email given is a ucsd email
        if (!Pattern.matches(".*ucsd.edu$", email)) {
            Log.d("DEBUG", "email rejected");
            throw new IOException("Email does not end in UCSD or otherwise rejected");
        }

        String regexMobile = "^\\+[0-9][0-9][0-9][0-9] \\([0-9][0-9][0-9]\\) [0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$";
        if(mobileNumber==null || !mobileNumber.matches(regexMobile))
            throw new IllegalArgumentException("Phone number does not match acceptable pattern.");

        // check to see if email is already registered
        String response = httpGetRequest("/db/api.php/users?filter[]=email,eq,"
                + email + "&transform=1");

        //convert returned json to a list of users with the same email
        ArrayList<User> users = filterDeletedUsers(jsonToUser(response));

        //if there are a nonzero number of same email users, return error
        if (users.size() != 0) {
            Log.d("DEBUG", "duplicate email");
            throw new IOException("Duplicate Email");
        }

        //DEBUG
        Log.d("DEBUG", "user not duplicate");

        Log.d("DEBUG", httpGetRequest("/db/userCount.php"));
        // get userID
        int profileID = new Integer(httpGetRequest("/db/userCount.php")).intValue();

        // get unused ID from server
        while (searchUserIDs(profileID) != null) {
            profileID++;
        }

        //DEBUG
        Log.d("DEBUG", "new ID found " + profileID);

        //get email link from server
        String emailLink = sendEmailVerification(email);

        //if something bad happened, return exception
        if (emailLink == null) {
            throw new IOException("Email Failure"); // bad email verification
        }

        // get salt for password
        String salt = Encrypt.gensalt();

        // create user object
        User newUser = new User(name, photo, profileID, bio, mobileNumber, email,
                Encrypt.hashpw(password, salt), salt, new ArrayList<Integer>(),
                false, new ArrayList<Integer>(), emailLink, false);

        //DEBUG
        Log.d("DEBUG", "user object generated");
        Log.d("DEBUG", "Attempting to add user: " + newUser);
        ArrayList<User> newUserList = new ArrayList<User>();
        newUserList.add(newUser);

        Log.d("DEBUG", "JSON: " + stripOuterJson(userToJson(newUserList)));

        URL url = new URL(serverName + "/db/api.php/users");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(stripOuterJson(userToJson(newUserList)));
        out.close();
        response = readStream(connection.getInputStream());

        if(!response.equals("[0]")){
            Log.d("DEBUG", "Server failed to add user");
            throw new IOException("Server could not add a user");
        }

        //return user object
        Log.d("DEBUG", "user added");
        return newUser;
    }

    /**
     * Attempts to log in to the server with a given login and password
     *
     * @param email    email login
     * @param password password login
     * @return user if login successful, null if unsuccessful
     */
    public static User login(String email, String password) throws IOException {

        String response = httpGetRequest("/db/api.php/users?filter[]=email,eq,"
                + email + "&transform=1");
        ArrayList<User> users = jsonToUser(response);

        users = filterDeletedUsers(users);
        //if did not return only one user with that email, not found or something weird happened
        if (users.size() != 1) {
            Log.d("DEBUG", "bad email search");
            return null;
        }

        // get data of valid user
        User user = users.get(0);

        //if not verified, reject login
        if (!user.getVerified()) {
            Log.d("DEBUG", "user not verified");
            return null; // unverified cannot login
        }

        // password test
        if (Encrypt.hashpw(password, user.getSalt()).equals(user.getPassword())) {
            Log.d("DEBUG", "user login successful");
            return user;
        }

        // password did not match
        Log.d("DEBUG", "password mismatch");

        //password test failed, return null
        return null;
    }

    /**
     * Searches the database for posts with the given tags
     *
     * @param tags The list of tags to search with
     * @return The list of posts with those tags
     */
    public static ArrayList<Post> searchPostTags(ArrayList<String> tags) throws IOException {
        if (tags.size() == 0) {
            return new ArrayList<Post>();
        }

        //ArrayList to be outputted
        ArrayList<Post> posts = new ArrayList<Post>();

        //uses HTTP GET
        //first element of list
        String request = "/db/api.php/posts?filter[]=tags,cs,:" + tags.get(0) + ":";

        //for every id after the first one
        for (int x = 1; x < tags.size(); x++) {
            request = request + "&filter[]=tags,cs,:" + tags.get(x) + ":";
        }
        //add so that only has to be one of the ids in the list and convert to objects
        request = request + "&satisfy=any&order=dateCreated,desc&transform=1";

        //attempt to convert to array list from json

        posts = jsonToPost(httpGetRequest(request));

        posts = filterDeletedPosts(posts);

        //return list of users
        return posts;
    }

    /**
     * Searches the database for posts with the tag
     *
     * @param tag The tag to search with
     * @return The list of posts with those tags
     */
    public static ArrayList<Post> searchPostTags(String tag) throws IOException {
        //Calls search on a list with a single entry inside
        ArrayList<String> single = new ArrayList<String>(1);
        single.add(tag);

        //call method
        ArrayList<Post> post = searchPostTags(single);

        //if not null, return list, else return empty array list
        return (post != null) ? post : new ArrayList<Post>();
    }

    /**
     * Returns the users with the ids in the given list in an arbitrary order
     *
     * @param ids The list of ids to grab
     * @return The list of User objects with the given ids
     */
    public static ArrayList<User> searchUserIDs(ArrayList<Integer> ids) throws IOException {
        //if somehow asked with an empty list, return empty list
        if (ids.size() == 0) {
            return new ArrayList<User>();
        }

        //ArrayList to be outputted
        ArrayList<User> users = new ArrayList<User>();

        //uses HTTP GET
        //first element of list
        String request = "/db/api.php/users?filter[]=profileID,eq," + ids.get(0);

        //for every id after the first one
        for (int x = 1; x < ids.size(); x++) {
            request = request + "&filter[]=profileID,eq," + ids.get(x);
        }
        //add so that only has to be one of the ids in the list and convert to json
        request = request + "&satisfy=any&transform=1";

        //try to convert to json
        String var = httpGetRequest(request);
        Log.d("DEBUG", var);
        users = jsonToUser(var);
        Log.d("DEBUG", users.toString());

        users = filterDeletedUsers(users);

        //return list of users
        return users;
    }

    /**
     * Gets the user with the specified id
     *
     * @param id The id to search for
     * @return The user with the given id, null if no match
     */
    public static User searchUserIDs(int id) throws IOException {
        //Call searchUserIds on list with one element
        ArrayList<Integer> single = new ArrayList<Integer>(1);
        single.add(id);

        //call method on single element array
        ArrayList<User> users = searchUserIDs(single);

        //sanity check
        if (users.size() == 0) {
            return null;
        }

        //return user (should be only one in array)
        return users.get(0);
    }

    /**
     * Get the Posts with the given ids
     *
     * @param ids the ID numbers to search for
     * @return The Posts with the given ids
     */
    public static ArrayList<Post> searchPostIDs(ArrayList<Integer> ids) throws IOException {
        //if ArrayList is empty, return an empty list of posts
        if (ids.size() == 0) {
            return new ArrayList<Post>();
        }

        //uses HTTP GET
        //first element of list
        String request = "/db/api.php/posts?filter[]=postID,eq," + ids.get(0);

        //for every id after the first one
        for (int x = 1; x < ids.size(); x++) {
            request = request + "&filter[]=postID,eq," + ids.get(x);
        }
        //add so that only has to be one of the ids in the list and convert to json
        request = request + "&satisfy=any&order=dateCreated,desc&transform=1";

        //list to be outputted
        ArrayList<Post> posts = new ArrayList<Post>();

        //try to convert json string to ArrayList of Posts

        posts = jsonToPost(httpGetRequest(request));

        posts = filterDeletedPosts(posts);

        //return list of posts
        return posts;
    }

    /**
     * Gets the post with the specified id
     *
     * @param id The id to search for
     * @return The post with the given id, null if no match
     */
    public static Post searchPostIDs(int id) throws IOException {
        //Call searchPostId on list with one element
        ArrayList<Integer> single = new ArrayList<Integer>(1);
        single.add(id);
        ArrayList<Post> posts = searchPostIDs(single);

        //if no posts found, return null
        if (posts.size() == 0) {
            return null;
        }

        //return the post
        return posts.get(0);
    }

    /**
     * modify post based on a search of postID from the argument with the changed fields
     *
     * @param post The post to update
     * @return true on success false on failure
     */
    public static boolean modifyExistingPost(Post post) throws IOException {
        //Json methods handle lists of users
        ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(post);


        //open http connection and send to server
        URL url = new URL(serverName + "/db/api.php/posts/" + post.getPostID());
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(
                connection.getOutputStream());
        out.write(stripOuterJson(postToJson(posts)));
        out.close();

        //output from server
        String response = readStream(connection.getInputStream());

        //WARNING: NOT COMPREHENSIVE
        //if bad response from server, return false
        if (!response.equals("[1]")) {
            return false;
        }

        //return success
        return true;
    }

    /**
     * modify post based on a search of postID from the argument with the changed fields
     *
     * @param user The post to update
     * @return true on success false on failure
     */
    public static boolean modifyExistingUser(User user) throws IOException {
        //Json handles arrays of users, not individual users
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);


        //open http connection and send command
        URL url = new URL(serverName + "/db/api.php/users/" + user.getProfileID());
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(stripOuterJson(userToJson(users)));

        Log.d("DEBUG", stripOuterJson(userToJson(users)));
        out.close();

        //response from server
        String response = readStream(connection.getInputStream());

        //if response from server is bad, return false
        if (!response.equals("[1]")) {
            return false;
        }else {
            //return success
            return true;
        }
    }

    /**
     * Filters out the deleted users from server
     *
     * @param list The list of the users
     * @return the users that are not deleted
     */
    private static ArrayList<User> filterDeletedUsers(ArrayList<User> list) {
        //iterate through each user in the list
        Iterator<User> it = list.iterator();
        while (it.hasNext()) {
            User user = it.next();

            //if the user has been soft deleted, take it out of the list
            if (user.getDeleted()) {
                it.remove();
            }
        }

        //return the list with no users that have been deleted
        return list;
    }

    /**
     * Filters out the deleted posts from server
     *
     * @param list The ArrayList<Post> representing the posts of the users
     * @return the posts that are not deleted
     */
    private static ArrayList<Post> filterDeletedPosts(ArrayList<Post> list) {
        //iterate through every post in the list
        Iterator<Post> it = list.iterator();
        while (it.hasNext()) {
            Post post = it.next();

            //if the post is soft deleted, remove it from the list
            if (post.getDeleted()) {
                it.remove();
            }
        }

        //return the list of undeleted posts
        return list;
    }

    /**
     * Deletes a post from the database
     *
     * @param post the post to delete
     * @throws IOException
     */
    public static void hardDeletePost(Post post) throws IOException{

        //open http connection and send to server
        URL url = new URL(serverName + "/db/api.php/posts/" + post.getPostID());
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("DELETE");
        OutputStreamWriter out = new OutputStreamWriter(
                connection.getOutputStream());
        out.close();

        //response from server
        String response = readStream(connection.getInputStream());

        //if response from server was bad
        if (!response.equals("1")) {
            throw new IOException("Could not delete post");
        }
    }

    /**
     * Converts a json string to an Array List of users
     *
     * @param json The string representing the JSON of the array of users
     * @return an Array List of users in the JSON. Order is arbitrary
     */
    private static ArrayList<User> jsonToUser(String json) {
        //Create the GSON builder to construct the Array List of users
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(User.JSONUser.class, new User.UserDeserializer());
        Gson gson = builder.create();

        //Parse the JSON string
        User.JSONUser jUser= gson.fromJson(json, User.JSONUser.class);

        ArrayList<User> toReturn= jUser.users;
        //User user1 = gson.fromJson(json, User.class);

        return toReturn;
    }

    /**
     * Converts a string in json format to an Array List of posts
     *
     * @param json The string that represents json data
     * @return The ArrayList of Posts represented in json
     */
    public static ArrayList<Post> jsonToPost(String json) {
        //Create the GSON builder to construct the Array List of posts
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Post.JSONPost.class, new Post.PostDeserializer());
        Gson gson = builder.create();

        //Parse the JSON string into a JSONPost object
        Post.JSONPost jPost = gson.fromJson(json, Post.JSONPost.class);

        return jPost.posts;
    }

    /**
     * Converts the list of Users into json format
     *
     * @param users The array list of users
     * @return The json of the list
     */
    static String userToJson(ArrayList<User> users) {
        //Create the GSON builder to construct the JSON format of the ArrayList of users
        GsonBuilder builder = new GsonBuilder();

        // Register the Custom UserSerializer with the JSONUser Class and create the gson
        Type arrayListUserType = new TypeToken<ArrayList<User>>(){}.getType();
        builder.registerTypeAdapter(arrayListUserType, new User.UserSerializer());
        Gson gson = builder.serializeNulls().create();

        //Create the JSON format for the ArrayList of users
        String toReturn = gson.toJson(users, arrayListUserType);

        //return JSON string
        return toReturn;
    }

    /**
     * Converts the list of Posts into json format
     *
     * @param posts The array list of posts
     * @return The json of the list
     */
    public static String postToJson(ArrayList<Post> posts) {
        //Create the GSON builder to construct the JSON format of the ArrayList of users
        GsonBuilder builder = new GsonBuilder();

        // Register the Custom UserSerializer with the JSONUser Class and create the gson
        Type arrayListPostType = new TypeToken<ArrayList<Post>>(){}.getType();
        builder.registerTypeAdapter(arrayListPostType, new Post.PostSerializer());
        Gson gson = builder.serializeNulls().create();

        //Create the JSON format for the ArrayList of users
        String toReturn = gson.toJson(posts, arrayListPostType);

        //return JSON string
        return toReturn;
    }

    /**
     * Converts an InputStream to a string
     *
     * @param in stream input
     * @return String that was in the stream
     * @author https://stackoverflow.com/a/16507509, Frank
     */
    private static String readStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();
        String line = "";

        //get each line and append to string builder
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        //close stream
        reader.close();

        //return string
        return builder.toString();
    }

    /**
     * Wrapper to simplify requests
     *
     * @param request String formatted like /db/api.php...
     * @return response string
     */
    private static String httpGetRequest(String request) throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);
        URL url = new URL(serverName + request);
        HttpsURLConnection client = (HttpsURLConnection) url.openConnection();
        String output = readStream(client.getInputStream());

        client.disconnect();
        return output;
    }

    /**
     * uploads an image to the server
     *
     * @param fileStream    a steam of the image to be sent
     * @param fileExtension the extension of the file without the period (ex. jpg, png)
     * @return String of where the file is now located
     * @throws IOException
     */
    public static String uploadImage(InputStream fileStream, String fileExtension) throws IOException {
        Bitmap bmp = BitmapFactory.decodeStream(fileStream);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, stream);
        InputStream is = new ByteArrayInputStream(stream.toByteArray());
        String charset = "UTF-8";
        String requestURL = serverName + "/img/images.php";
        MultipartUtility multipart = new MultipartUtility(requestURL, charset);
        multipart.addFilePart("name", "doge." + fileExtension, is);
        return "/img/" + multipart.finish(); // response from server.
    }


    /**
     * @author https://stackoverflow.com/a/33149413, Frank
     *         usage:
     *         String charset = "UTF-8";
     *         String requestURL = "YOUR_URL";
     *         MultipartUtility multipart = new MultipartUtility(requestURL, charset);
     *         multipart.addFormField("param_name_1", "param_value");
     *         multipart.addFormField("param_name_2", "param_value");
     *         multipart.addFormField("param_name_3", "param_value");
     *         multipart.addFilePart("file_param_1", "file_name", new InputStream());
     *         String response = multipart.finish(); // response from server.
     */
    private static class MultipartUtility {

        private final String boundary;
        private static final String LINE_FEED = "\r\n";
        private HttpsURLConnection httpConn;
        private String charset;
        private OutputStream outputStream;
        private PrintWriter writer;

        /**
         * This constructor initializes a new HTTP POST request with content type
         * is set to multipart/form-data
         *
         * @param requestURL url to send request
         * @param charset    what type of character to send, normally utf8
         * @throws IOException
         */
        public MultipartUtility(String requestURL, String charset)
                throws IOException {
            this.charset = charset;

            // creates a unique boundary based on time stamp
            boundary = "===" + System.currentTimeMillis() + "===";

            URL url = new URL(requestURL);

            Log.e("URL", "URL : " + requestURL);
            httpConn = (HttpsURLConnection) url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true); // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
            httpConn.setRequestProperty("Test", "Bonjour");
            outputStream = httpConn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                    true);
        }

        /**
         * Adds a form field to the request
         *
         * @param name  field name
         * @param value field value
         */
        public void addFormField(String name, String value) {
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + charset).append(
                    LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a upload file section to the request
         *
         * @param fieldName   name attribute in <input type="file" name="..." />
         * @param fileName    name of File to be uploaded
         * @param inputStream stream of File to be uploaded
         * @throws IOException
         */
        public void addFilePart(String fieldName, String fileName, InputStream inputStream)
                throws IOException {
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append(
                    "Content-Disposition: form-data; name=\"" + fieldName
                            + "\"; filename=\"" + fileName + "\"")
                    .append(LINE_FEED);
            writer.append(
                    "Content-Type: "
                            + java.net.URLConnection.guessContentTypeFromName(fileName))
                    .append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();

            writer.append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a header field to the request.
         *
         * @param name  - name of the header field
         * @param value - value of the header field
         */
        public void addHeaderField(String name, String value) {
            writer.append(name + ": " + value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Completes the request and receives response from the server.
         *
         * @return a list of Strings as response in case the server returned
         * status OK, otherwise an exception is thrown.
         * @throws IOException
         */
        public String finish() throws IOException {
            StringBuffer response = new StringBuffer();

            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            // checks server's status code first
            int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                httpConn.disconnect();
            } else {
                throw new IOException("Server returned non-OK status: " + status);
            }

            return response.toString();
        }
    }
}
