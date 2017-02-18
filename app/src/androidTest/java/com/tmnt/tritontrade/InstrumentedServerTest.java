package com.tmnt.tritontrade;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Instrumented tests for Server methods
 */

@RunWith(AndroidJUnit4.class)
public class InstrumentedServerTest
{
    /**
     * Deletes all of the specified users from the database.
     * DO NOT USE UNLESS YOU KNOW WHAT YOURE DOING. IF YOU HAVE TO ASK YOU PROBABLY DONT
     */
    private void cleanUp(ArrayList<User> u)
    {
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
        }
        catch (IOException e)
        {
            Log.d("DEBUG","FAILED TO DELETE USER " + u.getProfileID() + " FROM SERVER");
            //TRY AGAIN PLEASE
            try {
                wait(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            deleteUser(u);
        }
    }

    //List of FUN strings with various possibly problematic strings to test
    static ArrayList<String> funStrings = new ArrayList<String>();

    //run once before all tests, DO NOT EDIT THE AFFECTED VARIABLES IN TESTS
    @BeforeClass
    public static void setUpClass()
    {
        //bunch of escaped characters
        funStrings.add("\"\"\n\n\n\b\b");
        //random symbols
        funStrings.add("%$&^(*!&#(*@NULLnullnull__+");
    }

    /**
     * Set up before each test
     */
    @Before
    public void setUp()
    {

    }
    /**
     * Tests adding duplicate emails to the server
     */
    @Test
    public void testDuplicateEmail()
    {
        ArrayList<User> testUsers = new ArrayList<>();

        //add valid user to the database
        try {
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "(510) 999-999", "k5mao@ucsd.edu", "hunter2"));
        }
        catch (IOException e)
        {
            fail();
        }

        //try same user email again
        try {
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "(510) 999-999", "k5mao@ucsd.edu", "hunter2"));
        }
        catch (IOException e)
        {
            //SUCCESS, DUPLICATE EMAIL CAUGHT
        }
        finally
        {
            //nuke the user from the database
            cleanUp(testUsers);
        }

        //failed, did not catch
        fail("Duplicate email not caught");

    }
    /**
     * Tests Making a new user with a non UCSD email, should return false
     */
    @Test
    public void testNonUCSDEmailNewUser()
    {
        ArrayList<User> testUsers = new ArrayList<>();

        try {
            //Try adding new user with non uscd email, should return false
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "(510) 999-999", "NOTUCSDEMAIL@gmail.com", "hunter2"));
            fail("Invalid email not caught");
        }
        catch(IOException e)
        {
            //success, exception caught

        }

        try {
            //Try adding new user with non uscd email, should return false
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "(510) 999-999", "NOTUCSDEMAIL@ucsd.eduu", "hunter2"));
            fail("Invalid email not caught");

        }
        catch(IOException e)
        {
            //success, exception caught

        }

        try {
            //Try adding new user with non uscd email, should return false
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "(510) 999-999", "NOTUCSDEMAIL@ucsd.com", "hunter2"));
            fail("Invalid email not caught");

        }
        catch(IOException e)
        {
            //success, exception caught

        }



    }



    /**
     * Tests adding users with valid but uncommon ucsd emails (eg BOB+BOBBITY@ucsd.edu, etc)
     */
    @Test
    public void testStandardEmail()
    {
        ArrayList<User> testUsers = new ArrayList<User>();
        //try to add weird but valid ucsd emails as users
        try {
            Log.d("DEBUG","AFTER0");

            //single char email
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE",
                    "I ARE VERY INTERESTING", "(510) 999-999", "k5mao@ucsd.edu", "hunter2"));

        }
        catch (IOException e)
        {
            fail();
            Log.d("DEBUG","EXCEPTIONNN",e);
        }
        //no matter what happens, clean up test users from database
        finally
        {
            Log.d("DEBUG","REMOVING USERS");

            //delete all the users added to the list
            for (User u: testUsers)
            {
                deleteUser(u);
            }
            Log.d("DEBUG","END TEST");
        }
    }

}