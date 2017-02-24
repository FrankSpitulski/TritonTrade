package com.tmnt.tritontrade.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Converts the 
 */

public class PostSerializer implements JsonSerializer<JSONPost> {
    @Override
    public JsonElement serialize(JSONPost src, Type typeOfSrc, JsonSerializationContext context) {

        // JsonObject and the JsonArray that we will add it to
        JsonObject jObject= new JsonObject();
        JsonArray jArr= new JsonArray();

        // Add the user objects to the jArr
        ArrayList<Post> _posts= src.posts;

        for (Post curr: _posts){

            // Set up the JSON elements for ArrayList objects
            String photosTA= "";
            String tagsTA=":";

            for (int i=0; i< curr.getPhotos().size(); i++){

                photosTA= photosTA + "\n" + curr.getPhotos().get(i).toString();
            }

            for (int i=0; i< curr.getTags().size(); i++){

                tagsTA= tagsTA + curr.getTags().get(i).toString() + ":";
            }


            // Set up the boolean values that must be converted to integers
            int actTA= (curr.getSold())? 1:0;
            int selTA= (curr.getSelling())? 1:0;
            int delTA= (curr.getDeleted())? 1:0;

            // Get the profileID in string form
            String priceTA= Float.toString(curr.getPrice());
            String postIDTA= Integer.toString(curr.getPostID());
            String profIDTA= Integer.toString(curr.getProfileID());

            // Create a jsonobject to add all of the user data to
            JsonObject object= new JsonObject();
            object.addProperty("name", curr.getProductName());
            object.addProperty("photo", photosTA);
            object.addProperty("description", curr.getDescription());
            object.addProperty("price", priceTA);
            object.addProperty("tags", tagsTA);
            object.addProperty("profileID", profIDTA);
            object.addProperty("postID", postIDTA);
            object.addProperty("selling", selTA);
            object.addProperty("active", actTA);
            object.addProperty("contactInfo", curr.getContactInfo());
            object.addProperty("Date", curr.getDateCreated().toString());
            object.addProperty("deleted", delTA);

            // Add the JsonObject to the jArr
            jArr.add(object);
        }

        // Add the jArr to the Top Level JsonObject and return that JsonObject
        jObject.add("users", jArr);

        return jObject;
    }
}
