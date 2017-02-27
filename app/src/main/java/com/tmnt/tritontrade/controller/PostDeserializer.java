package com.tmnt.tritontrade.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Converts the json sequence into an arrayList of Post objects
 */

public class PostDeserializer implements JsonDeserializer<JSONPost> {

    @Override
    public JSONPost deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        // Get the first element of the json sequence, the arraylist of posts, and
        // the JSONPost object to be returned
        JsonObject jObj= (JsonObject) json;
        JSONPost jPost= new JSONPost();
        ArrayList<Post> toReturn = new ArrayList<Post>();

        // Get the array of posts from the json object
        JsonArray _posts= jObj.getAsJsonArray("posts");

        // Iterate through the Json array adding the post objects
        int index= 0;
        while (index < _posts.size()){

            // Get the JsonObject post
            JsonObject obj= _posts.get(index).getAsJsonObject();

            // Create the ArrayList<Strings>(s) that need to be added to the post object
            ArrayList<String> photosTA= new ArrayList<String>();
            ArrayList<String> tagsTA= new ArrayList<String>();

            String[] _photos = obj.get("photos").getAsString().split("\n");
            String[] _tags = obj.get("tags").getAsString().split(":");

            int i= 0;
            while(i < _photos.length){
                photosTA.add(_photos[i]);
                i= i+1;
            }
            i=0;
            while (i< _tags.length){
                tagsTA.add(_tags[i]);
                i= i+1;
            }

            // Get the selling, active, and deleted boolean fields
            boolean selling, active, deleted;
            int _selling, _active, _deleted;

            _selling = obj.get("selling").getAsInt();
            _active = obj.get("active").getAsInt();
            _deleted = obj.get("deleted").getAsInt();

            selling = (_selling==1);
            active = (_active==1);
            deleted = (_deleted==1);

            // Create the Date object for the post object
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date dateTA = null;
            try {
                dateTA = dateFormat.parse(obj.get("dateCreated").getAsString());
            }
            catch (ParseException e){
                e.printStackTrace();
            }

            // Initialize the post object that is to be added to the arrayList<Post> toReturn
            Post newPost = new Post(obj.get("productName").getAsString(), photosTA,
                    obj.get("description").getAsString(), obj.get("price").getAsFloat(), tagsTA,
                    obj.get("profileID").getAsInt(), obj.get("postID").getAsInt(),
                    selling, active, dateTA, obj.get("contactInfo").getAsString(), deleted);

            // Add the post to the arrayList
            toReturn.add(newPost);

            index= index+1;
        }

        // Return the jPost with the array of post objects
        jPost.posts = toReturn;

        return jPost;
    }

}
