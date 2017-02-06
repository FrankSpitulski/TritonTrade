package com.tmnt.tritontrade;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Unit Tests for User Class
 */
public class UserTest {

    //a random user profile for testing if set methods return false on invalid input and true
    //on valid input
    User validUser;

    //lists of valid and invalid phone numbers
    static ArrayList<String> badPhoneNumbers;
    static ArrayList<String> goodPhoneNumbers;

    /**
     * Creates background variables once for use in tests, DO NOT MODIFY THESE VARIABLES
     * IN YOUR TESTS
     */
    @BeforeClass
    public static void setUpClass()
    {
        //instantiate Lists of phone numbers
        badPhoneNumbers = new ArrayList<String>();
        goodPhoneNumbers = new ArrayList<String>();

        badPhoneNumbers.add("129364182365891265885");
        badPhoneNumbers.add("THIS IS NOT A NUMBER");
        //random kanji
        badPhoneNumbers.add("私の名前は山本です");
        //random escaped characters
        badPhoneNumbers.add("\"\"\b\b\b\n\n\"\"\"");

        goodPhoneNumbers.add("(510) 143-2534");
    }

    /**
     * Set up variables before every test
     */
    @Before
    public void setUp()
    {
        validUser = new User("IAMBOB","PHOTOLINK",0,"I ARE INTERESTING","(555) 555-5555",
                "SUPER@ucsd.edu","hunter2","SALTY",new ArrayList<Integer>(),true,
                new ArrayList<Integer>(),"EMAIL VERI LINK",false);

    }

    /**
     * Test valid and invalid phone numbers in setMobileNumber method
     */
    @Test
    public void testSetMobileNumbers()
    {
        for (String s: badPhoneNumbers)
        {
            assertFalse(validUser.setMobileNumber(s));
        }

        for(String s: goodPhoneNumbers)
        {
            assertTrue(validUser.setMobileNumber(s));
        }

    }
}
