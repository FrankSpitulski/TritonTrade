package com.tmnt.tritontrade;


import android.support.test.runner.AndroidJUnit4;

import android.util.Log;

import com.tmnt.tritontrade.controller.Post;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Instrumented tests for Server methods
 */

@RunWith(AndroidJUnit4.class)
public class InstrumentedServerTest
{


    //List of FUN strings with various possibly problematic strings to test
    static ArrayList<String> funStrings = new ArrayList<String>();
    //list of invalid phone numbers
    static ArrayList<String> badPhoneNumbers = new ArrayList<String>();
    //list of valid phone numbers
    static ArrayList<String> goodPhoneNumbers = new ArrayList<String>();

    //List of users used in the test, automatically cleaned up from server after each test
    private ArrayList<User> testUsers;

    //List of Posts to be cleaned up on server after each test
    private ArrayList<Post> testPosts;

    //run once before all tests, DO NOT EDIT THE AFFECTED VARIABLES IN TESTS
    @BeforeClass
    public static void setUpClass()
    {
        //WEIRD STRINGS
        //empty string
        funStrings.add("");
        //string of only spaces
        funStrings.add("      ");
        //bunch of escaped characters
        funStrings.add("\"\"\n\n\n\b\b");
        //random symbols
        funStrings.add("%$&^(*!&#(*@NULLnullnull__+");
        //forward and back slashes for days
        funStrings.add("\\//////\\\\\\\\////////");
        //nuke database sql command
        funStrings.add("DROP TABLE users;--");
        //more random symbols
        funStrings.add("`~[{]})(*&^%$#@!:;\"\'\'\'\'\"\"\'**--++__--++__)))(");

        //GOOD PHONE NUMBERS
        //use country node, no idea if actual phone number is valid, area code is
        goodPhoneNumbers.add("+0001 (510) 535-1234");

        //BAD PHONE NUMBERS
        //no country node
        badPhoneNumbers.add("(510) 535-1234");
        //bad country code
        badPhoneNumbers.add("+12345643 (111) 535-1234");
        //missing parenthesis
        badPhoneNumbers.add("+0001 111 535-1234");
        //too many numbers
        badPhoneNumbers.add("+0001 (510) 535-12344");
        //invalid symbols
        badPhoneNumbers.add("+0001 (510) 515-123a");
        badPhoneNumbers.add("+0001 (510) 515-1234!");
        //no dash in middle
        badPhoneNumbers.add("+0001 (510) 5151234");
        badPhoneNumbers.add("+0001 (510) 515 1234");
        //no + in front of country code
        badPhoneNumbers.add("0001 (510) 515-1234");
        //no spaces
        badPhoneNumbers.add("+0001(510)515-1234");
        //4 digit area code
        badPhoneNumbers.add("+0001 (5231) 513-1423");
    }

    /**
     * Set up before each test
     */
    @Before
    public void setUp()
    {
        testUsers = new ArrayList<User>();
        testPosts = new ArrayList<Post>();
    }

    /**
     * Clean up after every test
     */
    @After
    public void CleanUpAfterTest()
    {
        //hard delete every post in the list
        for (Post p: testPosts)
        {
            try {
                Server.hardDeletePost(p);
            }
            catch (IOException e)
            {
                Log.d("DEBUG","POST FAILED TO DELETE");
            }
        }

        //nuke all users used in testing
        cleanUp(testUsers);
    }

    /**
     * Tests adding duplicate emails to the server
     */
    @Test
    public void testAddNewUserDuplicateEmail()
    {
        //add valid user to the database
        try {
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "+0001 (510) 999-9999", "k5mao@ucsd.edu", "hunter2"));
        }
        catch (IOException e)
        {
            fail();
        }

        //try same user email again
        try {
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "+0001 (510) 999-9999", "k5mao@ucsd.edu", "hunter2"));

            //failed, did not catch
            fail("Duplicate email not caught");
        }
        catch (IOException e)
        {
            //SUCCESS, DUPLICATE EMAIL CAUGHT
        }

    }
    /**
     * Tests Making a new user with a non UCSD email, should return false
     */
    @Test
    public void testAddNewUserNonUCSDEmailNewUser()
    {
        try {
            //Try adding new user with non uscd email, should return false
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "+0001 (510) 999-9999", "NOTUCSDEMAIL@gmail.com", "hunter2"));
            fail("Invalid email not caught");
        }
        catch(IOException e)
        {
            //success, exception caught
        }

        try {
            //Try adding new user with non uscd email, should return false
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "+0001 (510) 999-9999", "NOTUCSDEMAIL@ucsd.eduu", "hunter2"));
            fail("Invalid email not caught");

        }
        catch(IOException e)
        {
            //success, exception caught

        }

        try {
            //Try adding new user with non uscd email, should return false
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "+0001 (510) 999-9999", "NOTUCSDEMAIL@ucsd.com", "hunter2"));
            fail("Invalid email not caught");

        }
        catch(IOException e)
        {
            //success, exception caught
        }



    }

    /**
     * Tests adding users with valid email
     */
    @Test
    public void testAddNewUserStandardEmail()
    {
        //try to add weird but valid ucsd emails as users
        try {
            Log.d("DEBUG","AFTER0");

            //Valid email
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE",
                    "I ARE VERY INTERESTING", "+0001 (510) 999-9999", "k5mao@ucsd.edu", "hunter2"));

        }
        catch (IOException e)
        {
            fail();
            Log.d("DEBUG","EXCEPTIONNN",e);
        }

    }

    /**
     * Tests logging in to a valid email
     */
    @Test
    public void testLogInValidUser()
    {
        //Valid email
        try {
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE",
                    "I ARE VERY INTERESTING", "+0001 (510) 999-9999", "k5mao@ucsd.edu", "hunter2"));
        }
        catch (IOException e)
        {
            //test failed on valid email
            fail();
        }

        //set in database that the valid user has been verified
        testUsers.get(0).setVerified(true);
        try {
            Server.modifyExistingUser(testUsers.get(0));
        } catch (IOException e) {
            //server failed
            fail();
        }


        //try logging in
        try {
            if (Server.login("k5mao@ucsd.edu", "hunter2") == null)
            {
                //if failed to log in with valid credentials, fail
                fail();
            }
        }
        catch (IOException e)
        {
            //Server exploded
            fail();
        }
    }

    /**
     * Tests logging in to an unverified email
     */
    @Test
    public void testLogInUnverified()
    {
        //Valid email
        try {
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE",
                    "I ARE VERY INTERESTING", "+0001 (510) 999-9999", "k5mao@ucsd.edu", "hunter2"));
        }
        catch (IOException e)
        {
            //test failed on valid email
            fail();
        }

        //try logging in to unverified user
        try {
            if (Server.login("k5mao@ucsd.edu", "hunter2") != null)
            {
                //if did not fail to log in
                fail();
            }

        }
        //failed to log in, fail
        catch (IOException e)
        {
            //Server exploded
            fail();
        }
    }

    /**
     * Tests logging in to a user that does not exist
     */
    @Test
    public void testLogInUserDoesNotExist()
    {
        //try logging in to unverified user
        try {
            if (Server.login("THISAREVALIDEMAIL3286129836497163596123976.edu", "hunter2") != null)
            {
                fail();
            }
        }
        //failed to log in, fail
        catch (IOException e)
        {
            //Server exploded
            fail();
        }
    }

    /**
     * Tests logging in to a valid email
     */
    @Test
    public void testLogInWrongPassword()
    {
        //Valid email
        try {
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE",
                    "I ARE VERY INTERESTING", "+0001 (510) 999-9999", "k5mao@ucsd.edu", "hunter2"));
        }
        catch (IOException e)
        {
            //test failed on valid email
            fail();
        }

        //set in database that the valid user has been verified
        testUsers.get(0).setVerified(true);
        try {
            Server.modifyExistingUser(testUsers.get(0));
        } catch (IOException e) {
            //server failed
            fail();
        }


        //try logging in  with incorrect password
        try {
            //did not return null, fail
            if (Server.login("k5mao@ucsd.edu", "hunter3") != null) {
                fail();
            }
        }
        //failed to log in
        catch (IOException e)
        {
            //Server exploded or something
            fail();
        }
    }

    /**
     * Tests adding users with correct phone number formats
     */
    @Test
    public void testAddNewUserCorrectPhoneNumberFormat()
    {
        //for every good phone number we are testing
        for (String s: goodPhoneNumbers) {
            //add user with bad phone number to database
            try {
                testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                        s, "k5mao@ucsd.edu", "hunter2"));
            } catch (IllegalArgumentException e) {
                //fail, valid user was invalid
                fail();
            }
            //test failed to complete
            catch (IOException e)
            {
                fail();
            }
        }
    }
    /**
     * Tests adding users with wrong phone number formats
     */
    @Test
    public void testAddNewUserWrongPhoneNumberFormat()
    {
        //for every bad phone number we are testing
        for (String s: badPhoneNumbers) {
            //add user with bad phone number to database
            try {
                testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                        s, "k5mao@ucsd.edu", "hunter2"));

                //fail, let an invalid user through
                fail();
            } catch (IllegalArgumentException e) {
                //success, exception caught
            }
            //test failed to complete
            catch (IOException e)
            {
                fail();
            }
        }
    }

    /**
     * Tests searching for a given user by ID retrieves the same user that was uploaded
     */
    @Test
    public void testSearchUserById()
    {
        //add valid user to the database
        try {
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "+0001 (510) 999-9999", "k5mao@ucsd.edu", "hunter2"));
        }
        catch (IOException e)
        {
            fail();
        }

        User u = null;
        //get user with the id of the user we just added
        try {
            u = Server.searchUserIDs(testUsers.get(0).getProfileID());
        }
        catch (IOException e)
        {
            fail();
        }

        //assert that the retrieved user and the uploaded user are the same
        assertTrue(u.toString().equals(testUsers.get(0).toString()));
    }

    /**
     * Tests modifying a User object on the server
     */
    @Test
    public void testModifyUser()
    {
        //add valid user to the database
        try {
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "+0001 (510) 999-9999", "k5mao@ucsd.edu", "hunter2"));
        }
        catch (IOException e)
        {
            fail();
        }

        User u = testUsers.get(0);

        for (String s: funStrings) {
            u.setName(s);
            //Change user name
            try {
                Server.modifyExistingUser(u);
            } catch (IOException e) {
                fail();
            }

            //get new instance of the user form the server
            try {
                u = Server.searchUserIDs(u.getProfileID());
            } catch (IOException e) {
                fail();
            }

            //assert that the changed name equals the one gotten from the server
            assertTrue(u.getName().equals(s));
        }
    }

    /**
     * Test posting with a given user, normal non-insane cases
     */
    @Test
    public void testPostNormal() {
        //add valid user to the database
        try {
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "+0001 +0001 (510) 999-99999", "k5mao@ucsd.edu", "hunter2"));
        } catch (IOException e) {
            fail();
        }

        //user that will be posting stuffs
        User u = testUsers.get(0);

        //add post to database
        try {
            testPosts.add(Server.addPost("SLIGHTLY USED TURNIP", new ArrayList<String>(),
                    "TURNIPS ARE GOOD FOR YOUUUU", (float) 42.0, new ArrayList<String>(),
                    u.getProfileID(), true, "FIND ME IN KONSTANTIYYE"));
        }
        //fail post not added
        catch (IOException e)
        {
            fail();
        }

        Post post1 = testPosts.get(0);
        Post post2 = null;
        //get post back from server
        try {
            post2 = Server.searchPostIDs(post1.getPostID());
        }
        catch (IOException e)
        {
            fail();
        }

        //DEBUG IN CASE POST STRINGS ARENT THE SAME
        //TODO CHANGE COMPARISON TO USE JSON WHEN REFACTORED
        Log.d("DEBUG",post1.toString());
        Log.d("DEBUG",post2.toString());

        //assert that both posts are the same
        assertTrue(post1.toString().equals(post2.toString()));
        //assert that the user we made to post it is the same
        assertEquals(post2.getProfileID(),u.getProfileID());
    }


    /**
     * Deletes all of the specified users from the database.
     * DO NOT USE UNLESS YOU KNOW WHAT YOURE DOING. IF YOU HAVE TO ASK YOU PROBABLY DONT
     */
    private void cleanUp(ArrayList<User> u)
    {
        Log.d("DEBUG", "CLEANING UP " + u.size() + " USERS");

        for (User user: u)
        {
            deleteUser(user);
        }
    }

    /**
     * Deletes a user from the database. WARNING: THIS IS A DIRTY DELETION. DO NOT USE FOR
     * ANYTHING EXCEPT TESTING. DO NOT USE ON USERS THAT HAVE POSTS ATTACHED TO THEM. PLEASE
     * DO NOT USE THIS METHOD UNLESS YOU KNOW EXACTLY WHAT YOU ARE DOING. IMPROPER USAGE
     * CAN DAMAGE THE DATABASE WHICH WILL MAKE US ALL SAD
     *
     * @param u The user with the id to delete
     */
    private void deleteUser(User u)
    {
        Log.d("DEBUG", "DELETING USER ID # " + u.getProfileID());
        //open http connection and send to server
        try {
            URL url = new URL(Server.getServerName() + "/db/api.php/users/" + u.getProfileID());
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
        catch (IOException e)
        {
            Log.d("DEBUG","FAILED TO DELETE USER " + u.getProfileID() + " FROM SERVER");

            deleteUser(u);
        }
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
}