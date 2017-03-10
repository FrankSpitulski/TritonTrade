package com.tmnt.tritontrade;


import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static com.tmnt.tritontrade.view.Register_Account.convertMobileNumberToDatabaseFormat;
import static org.junit.Assert.*;


/**
 * Class for testing of the conversion of mobile numbers to database format using regex
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedMobileNumberConversionTest
{
    //paired array lists that store the unformatted number and the expected result of each
    ArrayList<String> unformattedNumbers;
    ArrayList<String> databaseFormatNumbers;

    @BeforeClass
    public void setup()
    {
        //initialize the lists
        unformattedNumbers = new ArrayList<String>();
        databaseFormatNumbers = new ArrayList<String>();

        //add elements to the lists

    }

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
