package com.tmnt.tritontrade.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Method to convert an ArrayList of users to its json sequence
 */

public class UserSerializer implements JsonSerializer<ArrayList<User>> {

    @Override
    public JsonElement serialize(ArrayList<User> src, Type typeOfSrc, JsonSerializationContext context) {

        // JsonObject and the JsonArray that we will add it to
        JsonObject jObject= new JsonObject();
        JsonArray jArr= new JsonArray();

        // Add the user objects to the jArr
        for (User curr: src){

            // Set up the boolean values that must be converted to integers
            int verTA= (curr.getVerified())? 1:0;
            int delTA= (curr.getDeleted())? 1:0;

            // Get the profileID in string form
            String pIDTA= Integer.toString(curr.getProfileID());

            // Create a jsonobject to add all of the user data to
            JsonObject object= new JsonObject();
            object.addProperty("name", curr.getName());
            object.addProperty("photo", curr.getPhoto());
            object.addProperty("profileID", pIDTA);
            object.addProperty("bio", curr.getBio());
            object.addProperty("mobileNumber", curr.getMobileNumber());
            object.addProperty("email", curr.getEmail());
            object.addProperty("password", curr.getPassword());
            object.addProperty("salt", curr.getSalt());
            object.addProperty("postHistory", curr.getPostHistoryString());
            object.addProperty("verified", verTA);
            object.addProperty("cartIDs", curr.getCartIDsString());
            object.addProperty("emailVerificationLink", curr.getEmailVerificationLink());
            object.addProperty("deleted", delTA);

            // Add the JsonObject to the jArr
            jArr.add(object);
        }

        // Add the jArr to the Top Level JsonObject and return that JsonObject
        jObject.add("users", jArr);

        return jObject;
    }
}
