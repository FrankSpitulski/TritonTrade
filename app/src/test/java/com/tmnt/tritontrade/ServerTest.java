package com.tmnt.tritontrade;

import android.util.Log;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.util.Date;

import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;


/**
 * Unit tests for Server class
 */
public class ServerTest {

    /**
     * Tests conversion of User Array Lists to and from JSON
     */
    @Test //UNCOMMENT WHEN IMPLEMENTATION WORKS
    public void testUserJsonConversion()
    {
        //TODO FIX, CAN NOT getClass() ON GENERICS SUCH AS ARRAY LIST<USER> FOR REFLECTION
        //TODO IF SOMEONE HAS IDEAS PLEASE DO WHATEVER YOU WANT

        //user to convert to JSON
        User user1 = new User(
                "NAME", "PHOTOURL", 12345, "MY LIFE ARE INTERESTING", "(555) 555-5555",
            "BOB@ucsd.edu", "hunter2", "SALTY", new ArrayList<Integer>(),
        true, new ArrayList<Integer>(),"EMAIl VERIFY LINK ARE HERE", false);

        //add to one element list
        ArrayList<User> list = new ArrayList<User>();
        list.add(user1);


        //new server instance to call invoke methods
        Server server = new Server();

        //Method to invoke
        Method userToJson = null;
        Method jsonToUser = null;

        //declarations
        ArrayList<User> list2 = null;
        String json = "https://spitulski.no-ip.biz/db/api.php/users?transform=1";

        Class[] userJsonClass = {list.getClass()};
        Class[] jsonUserClass = {json.getClass()};
        //get methods from class
        try {
            userToJson = Server.class.getDeclaredMethod("userToJson",userJsonClass);
            jsonToUser = Server.class.getDeclaredMethod("jsonToUser",jsonUserClass);
        } catch (NoSuchMethodException e) {
            //fail();
            e.printStackTrace();
        }



        //set private methods are accessible
        userToJson.setAccessible(true);
        jsonToUser.setAccessible(true);

        //try to convert to json and back
        try {
            //convert to json and back
            json = (String)userToJson.invoke(server,list);
           list2 = (ArrayList<User>) jsonToUser.invoke(server,json);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //Asserts to see whether the parsing was successful
//        assertTrue(list2.size() == 1);

//        assertTrue(list2.get(0).getBio().equals(user1.getBio()));
//        assertTrue(list2.get(0).getCartIDsString().equals(user1.getCartIDsString()));
//        assertTrue(list2.get(0).getEmail().equals(user1.getEmail()));
//        assertTrue(list2.get(0).getEmailVerificationLink().equals(user1.getEmailVerificationLink()));
    }

    /**
     * Tests conversion of Post Array Lists to and from JSON
     */
    @Test
    public void testPostJsonConversion()
    {
        //Create the Post object
        Post post1= new Post("Bagel", new ArrayList<String>(), "bagel", 0.99f,
                new ArrayList<String>(), 1, 2, false, false, new Date(), "info", false);

        //Add post object to list
        ArrayList<Post> posts= new ArrayList<Post>();
        posts.add(post1);

        //new server instance to call invoke methods
        Server server = new Server();

        //Method to invoke
        Method postToJson = null;
        Method jsonToPost = null;

        //declarations
        ArrayList<Post> posts2 = null;
        String json = "https://spitulski.no-ip.biz/db/api.php/users?transform=1";

        Class[] postJsonClass = {posts.getClass()};
        Class[] jsonPostClass = {json.getClass()};

        //get methods from class
        try {
            postToJson = Server.class.getDeclaredMethod("postToJson",postJsonClass);
            jsonToPost = Server.class.getDeclaredMethod("jsonToPost",jsonPostClass);
        } catch (NoSuchMethodException e) {
            //fail();
            e.printStackTrace();
        }

        //set private methods are accessible
        postToJson.setAccessible(true);
        jsonToPost.setAccessible(true);

        //try to convert to json and back
        try {
            //convert to json and back
            json = (String)postToJson.invoke(server,posts);
            posts2 = (ArrayList<Post>) jsonToPost.invoke(server,json);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //Asserts to see whether the object was parsed correctly
        assertTrue(posts2.size()==1);
        assertTrue(posts2.get(0).getDescription().equals(posts.get(0).getDescription()));
        assertTrue(posts2.get(0).getContactInfo().equals(posts.get(0).getContactInfo()));
        assertTrue(posts2.get(0).getProductName().equals(posts.get(0).getProductName()));
        assertTrue(posts2.get(0).getPrice()== posts.get(0).getPrice());
    }

    /**
     * Test Search Post ID
     */
    @Test
    public void testSearchPostIDs()
    {

    }
}
