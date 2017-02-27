package com.tmnt.tritontrade.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Converts the ArrayList of post objects into the correct json format
 */

public class PostSerializer implements JsonSerializer<ArrayList<Post>> {
    @Override
    public JsonElement serialize(ArrayList<Post> src, Type typeOfSrc, JsonSerializationContext context) {

        // JsonObject and the JsonArray that we will add it to
        JsonObject jObject= new JsonObject();
        JsonArray jArr= new JsonArray();

        // Add the post objects
        for (Post curr: src){

            // Set up the JSON elements for ArrayList objects
            String photosTA= "";
            String tagsTA="";

            for (int i=0; i< curr.getPhotos().size(); i++){

                photosTA= photosTA + curr.getPhotos().get(i).toString() + "\n";
            }

            for (int i=0; i< curr.getTags().size(); i++){

                tagsTA= tagsTA + ":" + curr.getTags().get(i).toString() + ":\n";
            }


            // Set up the boolean values that must be converted to integers
            int actTA= (curr.getSold())? 1:0;
            int selTA= (curr.getSelling())? 1:0;
            int delTA= (curr.getDeleted())? 1:0;

            // Set up the correct date
            Calendar cal= GregorianCalendar.getInstance();
            cal.setTime(curr.getDateCreated());

            String dateTA= cal.get(Calendar.YEAR) + "-" +
                    (cal.get(Calendar.MONTH)+1<=9? "0"+(cal.get(Calendar.MONTH)+1):cal.get(Calendar.MONTH)+1)
                    + "-" + cal.get(Calendar.DAY_OF_MONTH) + " " +
                    (cal.get(Calendar.HOUR_OF_DAY)<=9? "0"+(cal.get(Calendar.HOUR_OF_DAY)):cal.get(Calendar.HOUR_OF_DAY))
                    + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);

            // Create a jsonobject to add all of the user data to
            JsonObject object= new JsonObject();
            object.addProperty("productName", curr.getProductName());
            object.addProperty("photos", photosTA);
            object.addProperty("description", curr.getDescription());
            object.addProperty("price", curr.getPrice());
            object.addProperty("tags", tagsTA);
            object.addProperty("profileID", curr.getProfileID());
            object.addProperty("postID", curr.getPostID());
            object.addProperty("selling", selTA);
            object.addProperty("dateCreated", dateTA);
            object.addProperty("contactInfo", curr.getContactInfo());
            object.addProperty("deleted", delTA);
            object.addProperty("active", actTA);

            // Add the JsonObject to the jArr
            jArr.add(object);
        }

        // Add the jArr to the Top Level JsonObject and return that JsonObject
        jObject.add("posts", jArr);

        return jObject;
    }
}
