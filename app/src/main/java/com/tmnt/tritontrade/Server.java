package com.tmnt.tritontrade;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
    final private static String serverName = "https://spitulski.no-ip.biz";

    public static void test(Context c)
    {
        try {
            Log.d("DEBUG", httpGetRequest("/db/api.php/users"  + "?transform=1")); // test pull info
            String response = uploadImage(c.getResources().openRawResource(R.raw.doge), "jpg"); // test file upload
            Log.d("DEBUG", response);
            Log.d("DEBUG", httpGetRequest("/db/userCount.php"));
        }catch(IOException e){
            Log.d("DEBUG", e.toString());
        }
    }

    /**
     * calls the verification email code on the server
     * @param email email to send to
     * @return null on fail, verification string on success
     */
    private static String sendEmailVerification(String email){
        String verificationString = new BigInteger(130, new Random()).toString(32);
        String response = "";
        try {
            response = httpGetRequest("/db/sendEmailValidation.php?validation=" +
                    verificationString + "&email=" + email);
        }catch (IOException e){
            Log.d("DEBUG", response);
            Log.d("DEBUG", e.toString());
            return null;
        }
        if(response.equals("")){
            return null;
        }
        return verificationString;
    }

    /**
     * Add a post to the database
     *
     * @return Whether or not the operation was successful
     */
    public static boolean addPost(String productName, ArrayList<String> photos, String description,
                                  float price, ArrayList<String> tags, int profileID,
                                  boolean selling, String contactInfo)
    {
        try {
            // get postID
            int postID = Integer.getInteger(httpGetRequest("/db/postCount.php")) + 1;

            // get unused ID
            while (searchUserIDs(postID) != null) {
                postID++;
            }

            ArrayList<Post> newPostList = new ArrayList<Post>();
            newPostList.add(new Post(productName, photos, description, price, tags, profileID,
                    postID, selling, true, new Date(), contactInfo, false));

            URL url = new URL(serverName + "/db/api.php/posts");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream());
            out.write(postToJson(newPostList));
            out.close();
            String response = readStream(connection.getInputStream());

        }catch(IOException e){
            Log.d("DEBUG", e.toString());
            return false;
        }

        return true;
    }

    /**
     * Adds a user to the database, must have @ucsd.edu email
     *
     * @return true if add successful
     */
    public static boolean addNewUser(String name, String photo, String bio,
                                     String mobileNumber, String email, String password)
    {
        try {
            // Makes sure that the email given is a ucsd email
            if (!Pattern.matches("ucsd.edu$", email)) {
                Log.d("DEBUG", "email rejected");
                return false;
            }

            //debug message
            Log.d("DEBUG", "email accepted");


            // check to see if email is already registered
            String response = httpGetRequest("/db/api.php/users?filter[]=email,eq,"
                    + email + "&transform=1");
            ArrayList<User> users = jsonToUser(response);

            if (users.size() != 0) {
                //Log.d("DEBUG", "duplicate email");
                return false;
            }

            //Log.d("DEBUG", "user not duplicate");

            // get userID
            int profileID = Integer.getInteger(httpGetRequest("/db/userCount.php")) + 1;

            // get unused ID
            while (searchUserIDs(profileID) != null) {
                profileID++;
            }

            Log.d("DEBUG", "new ID found " + profileID);

            String emailLink = sendEmailVerification(email);

            if(emailLink == null){
                return false; // bad email verification
            }

            // get salt for password
            String salt = BCrypt.gensalt(10);

            // create user object
            User newUser = new User(name, photo, profileID, bio, mobileNumber, email,
                    BCrypt.hashpw(password, salt), salt, new ArrayList<Integer>(),
                    false, new ArrayList<Integer>(), emailLink, false);

            Log.d("DEBUG", "user object generated");

            ArrayList<User> newUserList = new ArrayList<User>();
            newUserList.add(newUser);

            URL url = new URL(serverName + "/db/api.php/users");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream());
            out.write(userToJson(newUserList));
            out.close();
            response = readStream(connection.getInputStream());

            if(response == null || response.equals("")){
                Log.d("DEBUG", "user failed to add");
                return false;
            }
            Log.d("DEBUG", "user added");
            return true;

        }catch(IOException e){
            Log.d("DEBUG", e.toString());
        }
        return false;
    }

    /**
     * Attempts to log in to the server with a given login and password
     *
     * @param email email login
     * @param password password login
     * @return user if login successful, null if unsuccessful
     */
    public static User login(String email, String password)
    {
        try {
            String response = httpGetRequest("/db/api.php/users?filter[]=email,eq,"
                    + email + "&transform=1");
            ArrayList<User> users = jsonToUser(response);

            if(users.size() != 1){
                Log.d("DEBUG", "bad email search");
                return null;
            }

            // get data of valid user
            User user = users.get(0);

            if (!user.getVerified()) {
                Log.d("DEBUG", "user not verified");
                return null; // unverified cannot login
            }

            // password test
            if (BCrypt.hashpw(password, user.getSalt()).equals(user.getPassword())) {
                Log.d("DEBUG", "user login successful");
                return user;
            }

            // password did not match
            Log.d("DEBUG", "password mismatch");

        }catch(IOException e){
            Log.d("DEBUG", e.toString());
        }
        return null;
    }

    /**
     * Searches the database for posts with the given tags
     *
     * @param tags The list of tags to search with
     * @return The list of posts with those tags
     */
    public static ArrayList<Post> searchPostTags(ArrayList<String> tags)
    {
        if (tags.size() == 0)
        {
            return new ArrayList<Post>();
        }

        //ArrayList to be outputted
        ArrayList<Post> posts = new ArrayList<Post>();

        //uses HTTP GET
        //first element of list
        String request = "/db/api.php/posts?filter[]=profileID,cs,:" + tags.get(0) + ":";

        //for every id after the first one
        for (int x = 1; x < tags.size(); x++)
        {
            request = request + "&filter[]=profileID,cs,:" + tags.get(x) + ":";
        }
        //add so that only has to be one of the ids in the list and convert to objects
        request = request + "&satisfy=any&transform=1";

        //attempt to convert to array list from json
        try {
            posts = jsonToPost(httpGetRequest(request));
        }catch(IOException e){
            Log.d("DEBUG", e.toString());
            return posts;
        }

        //return list of users
        return posts;
    }

    /**
     * Searches the database for posts with the tag
     *
     * @param tag The tag to search with
     * @return The list of posts with those tags
     */
    public static ArrayList<Post> searchPostTags(String tag)
    {
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
    public static ArrayList<User> searchUserIDs(ArrayList<Integer> ids)
    {
        //if somehow asked with an empty list, return empty list
        if (ids.size() == 0)
        {
            return new ArrayList<User>();
        }

        //ArrayList to be outputted
        ArrayList<User> users = new ArrayList<User>();

        //uses HTTP GET
        //first element of list
        String request = "/db/api.php/users?filter[]=profileID,eq," + ids.get(0);

        //for every id after the first one
        for (int x = 1; x < ids.size(); x++)
        {
            request = request + "&filter[]=profileID,eq," + ids.get(x);
        }
        //add so that only has to be one of the ids in the list and convert to json
        request = request + "&satisfy=any&transform=1";

        //try to convert to json
        try {
            users = jsonToUser(httpGetRequest(request));
        }catch(IOException e){
            Log.d("DEBUG", e.toString());
            return users;
        }


        //return list of users
        return users;
    }

    /**
     * Gets the user with the specified id
     *
     * @param id The id to search for
     * @return The user with the given id, null if no match
     */
    public static User searchUserIDs(int id)
    {
        //Call searchUserIds on list with one element
        ArrayList<Integer> single = new ArrayList<Integer>(1);
        single.add(id);

        //call method on single element array
        ArrayList<User> users = searchUserIDs(single);

        //sanity check
        if(users.size() == 0)
        {
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
    public static ArrayList<Post> searchPostIDs(ArrayList<Integer> ids)
    {
        //if ArrayList is empty, return an empty list of posts
        if (ids.size() == 0)
        {
            return new ArrayList<Post>();
        }

        //uses HTTP GET
        //first element of list
        String request = "/db/api.php/posts?filter[]=postID,eq," + ids.get(0);

        //for every id after the first one
        for (int x = 1; x < ids.size(); x++)
        {
            request = request + "&filter[]=postID,eq," + ids.get(x);
        }
        //add so that only has to be one of the ids in the list and convert to json
        request = request + "&satisfy=any&transform=1";

        //list to be outputted
        ArrayList<Post> posts = new ArrayList<Post>();

        //try to convert json string to ArrayList of Posts
        try{
            posts = jsonToPost(httpGetRequest(request));
        }catch (IOException e){
            //something went wrong
            Log.d("DEBUG", e.toString());
            return posts;
        }

        //return list of posts
        return posts;
    }

    /**
     * Gets the post with the specified id
     *
     * @param id The id to search for
     * @return The post with the given id, null if no match
     */
    public static Post searchPostIDs(int id)
    {
        //Call searchPostId on list with one element
        ArrayList<Integer> single = new ArrayList<Integer>(1);
        single.add(id);
        ArrayList<Post> posts = searchPostIDs(single);

        //if no posts found, return null
        if(posts.size() == 0)
        {
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
    public static boolean modifyExistingPost(Post post)
    {
        //Json methods handle lists of users
        ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(post);

        //try to connect to server
        try {
            //open http connection and send to server
            URL url = new URL(serverName + "/db/api.php/posts/" + post.getPostID());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream());
            out.write(postToJson(posts));
            out.close();

        }catch(IOException e){
            //print error
            Log.d("DEBUG", e.toString());
            return false;// when doesn't exist
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
    public static boolean modifyExistingUser(User user)
    {
        //Json handles arrays of users, not individual users
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);

        //attempt to connect
        try {
            //open http connection and send command
            URL url = new URL(serverName + "/db/api.php/users/" + user.getProfileID());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream());
            out.write(userToJson(users));
            out.close();
        }catch(IOException e){
            //print error
            Log.d("DEBUG", e.toString());
            return false;// when doesn't exist
        }

        //return success
        return true;
    }

    /**
     * Converts a json string to an Array List of users
     *
     * @param json The string representing the JSON of the array of users
     * @return an Array List of users in the JSON. Order is arbritrary
     */
    private static ArrayList<User> jsonToUser(String json){
        //TODO IMPLEMENT
        return null;
    }

    /**
     * Converts a string in json format to an Array List of posts
     *
     * @param json The string that represents json data
     * @return The ArrayList of Posts represented in json
     */
    private static ArrayList<Post> jsonToPost(String json){
       //TODO IMPLEMENT
        return null;
    }

    /**
     * Converts the list of Users into json format
     *
     * @param users The array list of users
     * @return The json of the list
     */
    private static String userToJson(ArrayList<User> users){
       //TODO IMPLEMENT
        return null;
    }

    /**
     * Converts the list of Posts into json format
     *
     * @param posts The array list of posts
     * @return The json of the list
     */
    private static String postToJson(ArrayList<Post> posts){
        //TODO IMPLEMENT
        return null;
    }

    /**
     * Converts an InputStream to a string
     *
     * @author https://stackoverflow.com/a/16507509
     * @param in stream input
     * @return String that was in the stream
     */
    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    /**
     * Wrapper to simplify requests
     *
     * @param request String formatted like /db/api.php...
     * @return response string
     */
    private static String httpGetRequest(String request) throws IOException{
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);
        URL url;
        HttpsURLConnection client = null;
        String output = null;
        try {
            url = new URL(serverName + request);
            client = (HttpsURLConnection) url.openConnection();
            output = readStream(client.getInputStream());
        }catch (Exception e){
            Log.d("DEBUG", e.toString());
        }
        finally {
            if (client != null) {
                client.disconnect();
            }
        }
        return output;
    }

    /**
     * uploads an image to the server
     *
     * @param fileStream a steam of the image to be sent
     * @param fileExtension the extension of the file without the period (ex. jpg, png)
     * @return String of where the file is now located
     * @throws IOException
     */
    public static String uploadImage(InputStream fileStream, String fileExtension) throws IOException{
        String charset = "UTF-8";
        String requestURL = serverName + "/img/images.php";
        MultipartUtility multipart = new MultipartUtility(requestURL, charset);
        multipart.addFilePart("name", "doge." + fileExtension, fileStream);
        return multipart.finish(); // response from server.
    }


    /**
     * @author https://stackoverflow.com/a/33149413, Frank
     * usage:
     *      String charset = "UTF-8";
     *      String requestURL = "YOUR_URL";
     *      MultipartUtility multipart = new MultipartUtility(requestURL, charset);
     *      multipart.addFormField("param_name_1", "param_value");
     *      multipart.addFormField("param_name_2", "param_value");
     *      multipart.addFormField("param_name_3", "param_value");
     *      multipart.addFilePart("file_param_1", "file_name", new InputStream());
     *      String response = multipart.finish(); // response from server.
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
         * @param charset what type of character to send, normally utf8
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
         * @param fieldName  name attribute in <input type="file" name="..." />
         * @param fileName name of File to be uploaded
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
