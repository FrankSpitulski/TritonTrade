package com.tmnt.tritontrade;
import android.os.StrictMode;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Frank on 31/01/2017.
 */

public class Server {
    //Information of Frank's Server TODO DELETE AFTER SQL METHODS ARE REWRITTEN
    final static String serverName = "http://spitulski.no-ip.biz";
    final static String uid = "Michelangelo";
    final static String pwd = "Leonardo";
    final static String database = "TritonTrade";




    //whether or not the server is currently connected
    private static boolean connected;

    public static void test()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url;
        HttpURLConnection client = null;
        try {
            url = new URL(serverName + "/db/api.php/users");
            client = (HttpURLConnection) url.openConnection();
            BufferedInputStream in = new BufferedInputStream(client.getInputStream());
            Log.d("DEBUG", new BufferedReader(new InputStreamReader(in)).readLine());
        }catch (Exception e){
            Log.d("DEBUG", e.toString());
        }
        finally {
            if (client != null) {
                client.disconnect();
            }
        }
        //Console.WriteLine(client.GetStringAsync("http://spitulski.no-ip.biz/db/api.php/users").Result);
        String path = "doge.png";
        //System.IO.Stream stream = new System.IO.FileStream(path, System.IO.FileMode.Open);
        //HttpContent fileStreamContent = new StreamContent(stream);
        //var formData = new MultipartFormDataContent();
        //formData.Add(fileStreamContent, "name", "Image.jpg");
        //System.out.print(client.PostAsync(serverAddress + "/img/images.php", formData).Result.Content.ReadAsStringAsync().Result);
    }


    /**
     * Add a post to the database
     *
     * @return Whether or not the operation was successful
     */
    public static boolean addPost(Post post)
    {

        // TODO add post

        // no duplicate posts allowed, return false if duplicate postID
        return false;
    }

    /**
     * Adds a user to the database
     *
     * @return true if add successful
     */
    public static boolean addNewUser(String name, String photo, String bio,
                                     String mobileNumber, String email, String password)
    {
        // Makes sure that the email given is a ucsd email
        if (!Pattern.matches("ucsd.edu$", email))
        {
            Log.d("DEBUG", "email rejected");
            return false;
        }

        //debug message
        Log.d("DEBUG", "email accepted");


        /*

        // check to see if email is already registered
        String sqlString = "SELECT profileID FROM users WHERE email='" + email + "'";
        MySqlCommand cmd = new MySqlCommand(sqlString, connection);
        MySqlDataAdapter adapter = new MySqlDataAdapter(cmd);
        System.Data.DataSet data = new System.Data.DataSet();
        adapter.Fill(data);

        if (data.Tables[0].Rows.Count != 0)
        {
            Log.d("DEBUG", "user duplicate");
            return false;
        }

        Log.d("DEBUG", "user not duplicate");

        // get userID
        sqlString = "SELECT COUNT(*) FROM users";
        cmd = new MySqlCommand(sqlString, connection);
        adapter = new MySqlDataAdapter(cmd);
        adapter.Fill(data);
        if (data.Tables[0].Rows.Count == 0)
        {
            Log.d("DEBUG", "bad count querry");
            return false; // bad query
        }
        int profileID = (int)(System.Int64)data.Tables[0].Rows[0][1] + 1;

        // get unused ID
        while (searchUserIDs(profileID) != null)
        {
            profileID++;
        }

        Log.d("DEBUG", "new ID found " + profileID);

        // get salt for passowrd
        String salt = BCrypt.Net.BCrypt.GenerateSalt();

        // create user object, TODO implement verification, true for now
        User newUser = new User(name, photo, profileID, bio, mobileNumber, email,
                BCrypt.Net.BCrypt.HashPassword(password, salt), salt, new ArrayList<Integer>(),
                true, new ArrayList<Integer>());

        Log.d("DEBUG", "user object generated");

        // add user object to server
        sqlString = "INSERT INTO users (name,photo,profileID,bio,mobileNumber"
                + ",email,password,salt,postHistory,verified,cartIDs)"
                + "\nVALUES('" + newUser.getName() + "'"
                + ", '" + newUser.getPhoto() + "'"
                + ", " + newUser.getProfileID()
                + ", '" + newUser.getBio() + "'"
                + ", '" + newUser.getMobileNumber() + "'"
                + ", '" + newUser.getEmail() + "'"
                + ", '" + newUser.getPassword() + "'"
                + ", '" + newUser.getSalt() + "'"
                + ", '" + newUser.getPostHistoryString() + "'"
                + ", " + newUser.getVerified()
                + ", '" + newUser.getCartIDsString() + "'"
                + ");";

        cmd = new MySqlCommand(sqlString, connection);
        cmd.ExecuteNonQuery();

        Log.d("DEBUG", "user added to database");
        */
        return true;
    }

    /**
     * Attempts to log in to the server with a given login and password
     * @param email email login
     * @param password password login
     * @return true if add successful
     */
    public static User login(String email, String password) // returns a null or empty User if bad login
    {
    /*
        // sql lookup command
        String sqlString = "SELECT profileID FROM users WHERE email='" + email + "'";
        MySqlCommand cmd = new MySqlCommand(sqlString, connection);
        MySqlDataAdapter adapter = new MySqlDataAdapter(cmd);
        System.Data.DataSet data = new System.Data.DataSet();
        adapter.Fill(data);

        // duplicate or no emails
        if (data.Tables.Count != 1 || data.Tables[0].Rows.Count != 1) {
            Log.d("DEBUG", "bad email search");
            return null;
        }

        // get ID of email
        int userID = (int)(System.Int64)data.Tables[0].Rows[0][0];

        // get data of valid user
        User user = searchUserIDs(userID);

        if (!user.getVerified())
        {
            Log.d("DEBUG", "user not verified");
            return null; // unverified cannot login
        }

        // password test
        if (BCrypt.Net.BCrypt.HashPassword(password, user.getSalt())
                == user.getPassword())
        {
            Log.d("DEBUG", "user login successful");
            return user;
        }

        // password did not match
        Log.d("DEBUG", "password mismatch");

        */
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
        //TODO IMPLEMENENT
        Log.d("DEBUG", "NOT IMPLEMENTED");
        return new ArrayList<Post>();
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
        ArrayList<Post> post = searchPostTags(single);
        return (post != null) ? post : new ArrayList<Post>();
    }

    /**
     * Returns the users with the ids in the given list in an arbitrary order
     *
     * @param ids The list of ids to grab
     * @return The list of User objects with the given ids
     *
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


        //TODO USE NEW API THAT RETURNS JSON TO GET DATA OF OBJECTS

        //uses HTTP GET
        //first element of list
        String databaseString = serverName + "/db/api.php/users?filter[]=profileID,eq," + ids.get(0);

        //for every id after the first one
        for (int x = 1; x < ids.size(); x++)
        {
            databaseString = databaseString + "&filter[]=profileID,eq," + ids.get(x);
        }
        //add so that only has to be one of the ids in the list
        databaseString = databaseString + "&satisfy=any";

        //TODO USE HTTP TO GET JSON AND THEN PARSE IN TO OBJECTs AND RETURN LIST OF OBJECTS


        //old SQL method of retrieval

        /*
        //beginning part of sql command
        String sqlString = "SELECT * FROM users WHERE profileID='";
        //iterate through ids and add them to the sql String command
        for (int i = 0; i < ids.Count - 1; i++)
        {
            sqlString += ids[i] + "' OR profileID='";
        }
        //final String should not have an OR profileID= appended
        sqlString += ids[ids.Count - 1];
        sqlString += "'";

        //create command with sqlString and run it
        MySqlCommand cmd = new MySqlCommand(sqlString, connection);
        MySqlDataAdapter adapter = new MySqlDataAdapter(cmd);
        System.Data.DataSet data = new System.Data.DataSet();
        adapter.Fill(data);
        // duplicate or no emails
        if (data.Tables.Count == 0 || data.Tables[0].Rows.Count == 0)
        {
            disconnect();
            return new ArrayList<User>();
        }
        //grab data from adapter and make into user object
        for (int i = 0; i < data.Tables[0].Rows.Count; i++)
        {
            users.Add(new User(
                (String)(System.String)data.Tables[0].Rows[i][0],
                (String)(System.String)data.Tables[0].Rows[i][1],
                (int)(System.Int64)data.Tables[0].Rows[i][2],
                (String)(System.String)data.Tables[0].Rows[i][3],
                (String)(System.String)data.Tables[0].Rows[i][4],
                (String)(System.String)data.Tables[0].Rows[i][5],
                (String)(System.String)data.Tables[0].Rows[i][6],
                (String)(System.String)data.Tables[0].Rows[i][7],
                User.getPostHistoryFromString(((String)(System.String)data.Tables[0].Rows[i][8])),
                (int)(System.SByte)data.Tables[0].Rows[i][9] == 1,
                User.getCartIDsFromString((String)(System.String)data.Tables[0].Rows[i][10])
                ));
        }
        */

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
        ArrayList<Integer> single = new ArrayList<Integer>(1);
        single.add(id);
        ArrayList<User> users = searchUserIDs(single);
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


        //TODO USE NEW API THAT RETURNS JSON TO GET DATA OF OBJECTS

        //uses HTTP GET
        //first element of list
        String databaseString = serverName + "/db/api.php/posts?filter[]=postID,eq," + ids.get(0);

        //for every id after the first one
        for (int x = 1; x < ids.size(); x++)
        {
            databaseString = databaseString + "&filter[]=postID,eq," + ids.get(x);
        }
        //add so that only has to be one of the ids in the list
        databaseString = databaseString + "&satisfy=any";


        //TODO USE NEW API THAT RETURNS JSON TO GET DATA OF OBJECTS


        //TODO UPDATE RETURN TYPE
        return new ArrayList<Post>();
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
     * Gets the user with the specified id
     *
     * @param post The id to search for
     * @return The user with the given id
     */
    public static boolean modifyExistingPost(Post post)
    {
        // modify post based on a search of postID from the argument with the
        // changed fields
        // return true on success
        return false; // when doesn't exist
    }

    public static boolean modifyExistingUser(User user)
    {
        // modify user based on a search of postID from the argument with the
        // changed fields
        // return true on success
        return false; // when doesn't exist
    }

    private static User jsonToUser(String json){

        return null;
    }

    private static Post jsonToPost(String json){

        return null;
    }

    private static User userToJson(String json){

        return null;
    }

    private static User postToJson(String json){

        return null;
    }
}
