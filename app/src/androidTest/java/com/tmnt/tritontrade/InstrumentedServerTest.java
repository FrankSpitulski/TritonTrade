package com.tmnt.tritontrade;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;

/**
 * Instrumented tests for Server methods
 */

@RunWith(AndroidJUnit4.class)
public class InstrumentedServerTest
{
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
        //random kanji
        funStrings.add("私の名前は山本です");
        //random chinese
        funStrings.add("由銀河互動網路公司所經營，盼藉台灣風土人文，喚起人們守護台灣的心。\n" +
                "讓我們一起找回善良純樸的台灣精神，善待這片土地，珍惜我們自己。");
        //gibberish bengali
        funStrings.add("নলডনফ্ল্যান্সলনফ্ল্যান্সল্যাফ ");
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
            assertFalse(Server.addNewUser("I  AM STEVEEEE", "PHOTO LINK HERE", "I ARE VERY INTERESTING",
                    "(510) 999-999", "NOTUCSDEMAIL@gmail.com", "hunter2"));
        }catch(IOException e){}
    }

    /**
     * Tests adding users with valid but uncommon ucsd emails (eg BOB+BOBBITY@ucsd.edu, etc)
     */
    @Test
    public void testWeirdEmailNewUser()
    {
        //TODO IMPLEMENT
    }

}