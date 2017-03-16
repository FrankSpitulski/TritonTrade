package com.tmnt.tritontrade;

import android.support.test.runner.AndroidJUnit4;

import com.tmnt.tritontrade.controller.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;

/**
 * Tests Post and User data and methods without server interaction
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTestUserPost {


    @Test
    public void testPostHistory()
    {
        User u = new User("Name",User.getDefaultImage(),1,"BIO","+0001 (510) 888-8888",
                "bob@ucsd.edu","hunter2","SASLLLLLT",new ArrayList<Integer>(),true,
                new ArrayList<Integer>(),"Asd",false);

        u.addToPostHistory(100);

        assertTrue(100 == u.getPostHistory().get(0));
    }
}
