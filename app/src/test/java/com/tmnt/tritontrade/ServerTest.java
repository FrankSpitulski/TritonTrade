package com.tmnt.tritontrade;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;
import java.lang.reflect.*;

import com.tmnt.tritontrade.Server;


/**
 * Unit tests for Server class
 */
public class ServerTest {

    /**
     * Tests conversion of User Array Lists to and from JSON
     */
    //@Test UNCOMMENT WHEN IMPLEMENTATION WORKS
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
        String json = null;

        Class[] userJsonClass = {list2.getClass()};
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

        //compare to strings that are the same
        assertTrue(list2.get(0).toString().equals(list.get(0).toString()));
    }

    /**
     * Tests conversion of Post Array Lists to and from JSON
     */
    @Test
    public void testPostJsonConversion()
    {
//TODO IMPLEMENT AFTER FIGURING OUT REFLECTOIN ON GENERICS
    }

    /**
     * Test Search Post ID
     */
    @Test
    public void testSearchPostIDs()
    {

    }
}
