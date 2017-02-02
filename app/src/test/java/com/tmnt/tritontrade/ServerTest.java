package com.tmnt.tritontrade;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Server class
 */
public class ServerTest {

    /**
     * Tests conversion of User Array Lists to and from JSON
     */
    @Test
    public void testUserJsonConversion()
    {
        //user to convert to JSON
        User user1 = new User(
                "NAME", "PHOTOURL", 12345, "MY LIFE ARE INTERESTING", "(555) 555-5555",
            "BOB@ucsd.edu", "hunter2", "SALTY", new ArrayList<Integer>,
        true, new ArrayList<Integer>,"EMLIIA VERI LINK ARE HERE");

        //add to one element list
        ArrayList<User> list = new ArrayList<User>();
        list.add(user1);

        //convert to json and back
       String json = userToJson(list);
        ArrayList<User> list2 = jsonToUser(json);

        //compare to strings that are the same
        asertTrue(list2.get(0).toString().equals(list.get(0).toString()));

    }

    /**
     * Tests conversion of Post Array Lists to and from JSON
     */
    @Test
    public void testPostJsonConversion()
    {

    }
}
