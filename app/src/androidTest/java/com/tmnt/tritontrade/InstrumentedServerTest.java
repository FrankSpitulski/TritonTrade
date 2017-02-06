package com.tmnt.tritontrade;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;

/**
 * Instrumented tests for Server methods
 */

@RunWith(AndroidJUnit4.class)
public class InstrumentedServerTest
{
    /**
     * Tests Making a new user with a non UCSD email
     */
    @Test
    public void testNonUCSDEmailNewUser()
    {

        //Try adding new user with non uscd email, should return false
        assertFalse(Server.addNewUser("I  AM STEVEEEE","PHOTO LINK HERE","I ARE VERY INTERESTING",
                "(510) 999-999","NOTUCSDEMAIL@gmail.com","hunter2"));
    }

}