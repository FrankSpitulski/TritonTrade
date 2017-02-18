package com.tmnt.tritontrade.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by terrancegriffith on 2/16/17.
 */

class UserDeserializer implements JsonDeserializer<JSONUser> {


    @Override
    public JSONUser deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        //Get the first element of the json string
        JsonObject jobj= (JsonObject) json;
        JSONUser jUser= new JSONUser();

        //Get the actual array of users from the json
        JsonArray _users= jobj.getAsJsonArray("users");
        ArrayList<User> toReturn= new ArrayList<User>();

        //Iterate through the array adding the user objects to the arrayList
        int index= 0;
        while(index< _users.size()){

            // Get the json object (User)
            JsonObject obj= _users.get(index).getAsJsonObject();

            //Integer object for cartIDs and postHistory
            Integer x;

            // Get the cartIDs and postHistory from the obj
            String pH= obj.get("postHistory").getAsString();
            String cID= obj.get("cartIDs").getAsString();

            ArrayList<Integer> cardIDsTA= new ArrayList<Integer>();
            ArrayList<Integer> postHistTA= new ArrayList<Integer>();

            int i=0;
            while (i< cID.length()){

                x= Character.getNumericValue(cID.charAt(i));
                cardIDsTA.add(x);

                i= i+1;
            }

            i= 0;
            while (i< pH.length()){

                x= Character.getNumericValue(pH.charAt(i));
                postHistTA.add(x);

                i= i+1;
            }

            // Get the verified field and deleted fields from the obj
            boolean verified, deleted;
            int _verified, _deleted;

            _verified= obj.get("verified").getAsInt();
            _deleted= obj.get("deleted").getAsInt();
            verified= (_verified==0) ? false : true;
            deleted= (_deleted==0) ? false : true;


            //Create the user object from the json object
            User newUser = new User(obj.get("name").getAsString(), obj.get("photo").getAsString(),
                    obj.get("profileID").getAsInt(), obj.get("bio").getAsString(),
                    obj.get("mobileNumber").getAsString(), obj.get("email").getAsString(),
                    obj.get("password").getAsString(), obj.get("salt").getAsString(), postHistTA,
                    verified, cardIDsTA, obj.get("emailVerificationLink").getAsString(), deleted);

            //Add the new user to the arrayList of users
            toReturn.add(newUser);

            index = index+1;

        }

        // Return the arraylist of users from the juser object
        jUser.users= toReturn;

        return jUser;
    }
}
