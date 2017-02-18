package com.tmnt.tritontrade;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

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
     * Deletes a user from the database. WARNING: THIS IS A DIRTY DELETION. DO NOT USE FOR
     * ANYTHING EXCEPT TESTING. DO NOT USE ON USERS THAT HAVE POSTS ATTACHED TO THEM. PLEASE
     * DO NOT USE THIS METHOD UNLESS YOU KNOW EXACTLY WHAT YOU ARE DOING. IMPROPER USAGE
     * CAN DAMAGE THE DATABASE WHICH WILL MAKE US ALL SAD
     *
     * @param u The user with the id to delete
     */
    public void deleteUser(User u)
    {
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
        //spanish only characters
        funStrings.add("-á, -é, -í, -ó, -ú, -ü, -ñ, ¿, ¡");

    }

    /**
     * Set up before each test
     */
    @Before
    public void setUp()
    {

    }
    /**
     * Tests Making a new user with a non UCSD email, should return false
     */
    @Test
    public void testNonUCSDEmailNewUser()
    {

        try {
            //Try adding new user with non uscd email, should return false
            Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "(510) 999-999", "NOTUCSDEMAIL@gmail.com", "hunter2");

            //fail if exception not caught
            fail();
            Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "(510) 999-999", "NOTUCSDEMAIL@ucsd.eduu", "hunter2");

            //fail if exception not caught
            fail();
            Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "(510) 999-999", "NOTUCSDEMAIL@ucsd.com", "hunter2");

            //fail if exception not caught
            fail();
        }catch(IOException e)
        {
            //success, exception caught

        }
    }

    /**
     * Tests adding users with valid but uncommon ucsd emails (eg BOB+BOBBITY@ucsd.edu, etc)
     */
    @Test
    public void testWeirdEmailNewUser()
    {
        ArrayList<User> testUsers = new ArrayList<User>();
        //try to add weird but valid ucsd emails as users
        try {
            Log.d("DEBUG","AFTER0");
            //weird symbols
            /*Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "(510) 999-999", "!#$%&'*+-/=?^_`{|}~@ucsd.edu", "hunter2");*/

            //single char email
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE",
                    "I ARE VERY INTERESTING", "(510) 999-999", "p@ucsd.edu", "hunter2"));

            //. and +'s in email
            testUsers.add(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE",
                    "I ARE VERY INTERESTING", "(510) 999-999",
                    "disposable.style.email.with+symbol@ucsd.edu", "hunter2"));

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