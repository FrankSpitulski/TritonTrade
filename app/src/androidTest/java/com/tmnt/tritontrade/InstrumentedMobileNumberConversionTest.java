package com.tmnt.tritontrade;


import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static com.tmnt.tritontrade.controller.User.convertMobileNumberToDatabaseFormat;
import static org.junit.Assert.*;


/**
 * Class for testing of the conversion of mobile numbers to database format using regex
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedMobileNumberConversionTest
{
    //paired array lists that store the unformatted number and the expected result of each
    private static ArrayList<String> unformattedNumbers;
    private static ArrayList<String> databaseFormatNumbers;

    /**
     * Initializes the array lists holding the test cases
     */
    @BeforeClass
    public static void setup()
    {
        //initialize the lists
        unformattedNumbers = new ArrayList<String>();
        databaseFormatNumbers = new ArrayList<String>();

        //add elements to the lists

        //standard database format numbers
        unformattedNumbers.add("+0001 (123) 532-1111");
        databaseFormatNumbers.add("+0001 (123) 532-1111");

        //10 digits no spaces
        unformattedNumbers.add("6382431324");
        databaseFormatNumbers.add("+0001 (638) 243-1324");

        unformattedNumbers.add("0000000000");
        databaseFormatNumbers.add("+0001 (000) 000-0000");

        //no country code
        unformattedNumbers.add("(632) 888-7777");
        databaseFormatNumbers.add("+0001 (632) 888-7777");

        unformattedNumbers.add("(000) 000-0000");
        databaseFormatNumbers.add("+0001 (000) 000-0000");

        //10 digits, space between are and number, dash in number
        unformattedNumbers.add("134 432-2222");
        databaseFormatNumbers.add("+0001 (134) 432-2222");

        //dashes between are and number
        unformattedNumbers.add("838-999-9999");
        databaseFormatNumbers.add("+0001 (838) 999-9999");
    }

    /**
     * Tests whether or not numbers are converted to the database format properly
     */
    @Test
    public void testConvertMobileNumberToDatabaseFormat()
    {
        //for every input
        for (int x = 0; x < unformattedNumbers.size(); x ++)
        {
            //get result of conversion
            String result = convertMobileNumberToDatabaseFormat(unformattedNumbers.get(x));

            if (result == null)
            {
                Log.d("DEBUG","Phone Number \"" + unformattedNumbers.get(x) + "\" failed");
                //fail, valid number could not be converted
                fail();
            }
            //compare result to expected result
            else
            {
                assertTrue(result.equals(databaseFormatNumbers.get(x)));
            }
        }
    }



}
