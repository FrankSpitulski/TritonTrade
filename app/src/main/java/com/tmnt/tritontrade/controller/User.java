package com.tmnt.tritontrade.controller;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class User implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private String name;
    private String photo; // URL to image
    private int profileID;
    private String bio;
    private String mobileNumber;
    private String email;
    private String password; // hashed
    private String salt;
    private ArrayList<Integer> postHistory;
    private boolean verified;
    private ArrayList<Integer> cartIDs;
    private String emailVerificationLink;
    private boolean deleted;


    /**
     * @param name
     * @param photo
     * @param profileID
     * @param bio
     * @param mobileNumber
     * @param email
     * @param password
     * @param salt
     * @param postHistory
     * @param verified
     * @param cartIDs
     * @param emailVerificationLink
     */
    public User(String name, String photo, int profileID, String bio, String mobileNumber,
                String email, String password, String salt, ArrayList<Integer> postHistory,
                boolean verified, ArrayList<Integer> cartIDs, String emailVerificationLink,
                boolean deleted) throws IllegalArgumentException
    {
        this.postHistory = postHistory;

        //Set each element with setters, if any return false, throw new illegal argument exception
        if (!(this.setName(name) &&
        this.setPhoto(photo) &&
        this.setProfileID(profileID) &&
        this.setBio(bio) &&
        this.setMobileNumber(mobileNumber) &&
        this.setEmail(email) &&
        this.setPassword(password) &&
        this.setSalt(salt) &&
        this.setVerified(verified) &&
        this.setCartIDs(cartIDs) &&
        this.setEmailVerificationLink(emailVerificationLink) &&
        this.setDeleted(deleted)))
        //bad data, throwing exception
        {
            throw new IllegalArgumentException();
        }
    }


    //******PARCELABLE METHODS********
    protected User(Parcel in) {
        name = in.readString();
        photo = in.readString();
        profileID = in.readInt();
        bio = in.readString();
        mobileNumber = in.readString();
        email = in.readString();
        password = in.readString();
        salt = in.readString();
        if (in.readByte() == 0x01) {
            postHistory = new ArrayList<Integer>();
            in.readList(postHistory, Integer.class.getClassLoader());
        } else {
            postHistory = null;
        }
        verified = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            cartIDs = new ArrayList<Integer>();
            in.readList(cartIDs, Integer.class.getClassLoader());
        } else {
            cartIDs = null;
        }
        deleted = in.readByte() != 0x00;
    }

    /**
     * Returns the postHistory (List)
     * @param history the post history(String)
     * @return a list of postIDs
     */
    static ArrayList<Integer> getPostHistoryFromString(String history) {
        if (history.equals("")) {
            return new ArrayList<Integer>();
        }
        String[] split = history.split("\n");
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < split.length; i++) {
            list.add(Integer.getInteger(split[i]));
        }
        return list;
    }

    public static String getDefaultImage() {
        return "/img/imageCDN/default/defaultUser.jpg";
    }

    /**
     * Getter for name
     * @return the name of user
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter for name
     * @param name the name of the user
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setName(String name)
    {
        if(name==null)
            return false;

        this.name = name;
        return true;
    }

    /**
     * Getter for photo
     * @return the user photo
     */
    public String getPhoto()
    {
        return photo;
    }

    /**
     * Setter for photo
     * @param photo the photo of the user
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setPhoto(String photo)
    {
        if(photo==null)
            return false;
        this.photo = photo;
        return true;
    }

    /**
     * Getter for profileID
     * @return the profileID
     */
    public int getProfileID()
    {
        return profileID;
    }

    /**
     * Setter for profileID
     * @param profileID the profileID
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setProfileID(int profileID)
    {
        if(profileID == 0)
            return false;
        this.profileID = profileID;
        return true;
    }

    /**
     * Getter for bio
     * @return the user bio
     */
    public String getBio()
    {
        return bio;
    }

    /**
     * Setter for bio
     * @param bio the user bio
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setBio(String bio)
    {
        if(bio==null)
            return false;
        this.bio = bio;
        return true;
    }

    /**
     * Getter for mobileNumber
     * @return the mobile number
     */
    public String getMobileNumber()
    {
        return mobileNumber;
    }

    /**
     * Setter for mobileNumber
     * @param mobileNumber the mobile number
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setMobileNumber(String mobileNumber)
    {
        String regex = "^\\+[0-9][0-9][0-9][0-9] \\([0-9][0-9][0-9]\\) [0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$";
        if(mobileNumber==null || !mobileNumber.matches(regex))
            return false;
        this.mobileNumber = mobileNumber;
        return true;
    }

    /**
     * Getter for email
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Setter for email
     * @param email the user email
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setEmail(String email)
    {
        if (email == null || !email.matches(".*ucsd.edu$"))
            return false;
        this.email = email;
        return true;
    }

    /**
     * Getter for CartIDs
     * @return a list of cartIDs
     */
    public ArrayList<Integer> getCartIDs()
    {
        return cartIDs;
    }

    /**
     * Setter for cartIDs
     * @param cartIDs a list of cartIDs
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setCartIDs(ArrayList<Integer> cartIDs)
    {
        if(cartIDs==null)
            return false;
        this.cartIDs = cartIDs;
        return true;
    }

    /**
     * Getter for password
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Setter for password
     * @param password the password
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setPassword(String password)
    {
        if (password == null)
            return false;
        this.password = password;
        return true;
    }

    /**
     * hashes and sets a password
     * @param password an unhashed password
     * @return whether or not the operation was successful
     */
    public boolean hashAndSetPassword(String password) {
        if (password == null)
            return false;
        return setPassword(Encrypt.hashpw(password , getSalt()));

    }

    /**
     * Gets the ArrayList of Post ID's for the current user
     * @return ArrayList of integers containing the Post ID's
     */
    public ArrayList<Integer> getPostHistory() {
        return postHistory;
    }

    /**
     * Setter for salt
     * @return salt
     */
    public String getSalt()
    {
        return salt;
    }

    /**
     * Setter for salt
     * @param salt
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setSalt(String salt)
    {
        if (salt == null)
            return false;
        this.salt = salt;
        return true;
    }

    /**
     * Getter for verified
     * @return true if user is verified, else false
     */
    public boolean getVerified()
    {
        return verified;
    }

    /**
     * Setter for verified
     * @param verified
     * @return
     */
    public boolean setVerified(boolean verified)
    {
        this.verified = verified;
        return true;
    }

    /**
     * Getter for emailVerificationLink
     * @return the email verification link
     */
    public String getEmailVerificationLink(){
        return emailVerificationLink;
    }

    /**
     * Setter for email verification link
     * @param emailVerificationLink
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setEmailVerificationLink(String emailVerificationLink){
        if(emailVerificationLink == null){
            return false;
        }
        this.emailVerificationLink=emailVerificationLink;
        return true;
    }

    /**
     * Getter for deleted
     * @return deleted
     */
    public boolean getDeleted(){
        return deleted;
    }

    /**
     * Setter for deleted
     * @param deleted
     * @return true if updated, false if not updated
     */
    public boolean setDeleted(boolean deleted){
        this.deleted = deleted;
        return true;
    }

    /**
     * Adds post to history by id
     * @param id the id of the post
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean addToPostHistory(int id)
    {
        return postHistory.add(id);
    }

    /**
     * removes post from cart by id
     * @param id post id
     * @return whether or not the operation was successful
     */
    public boolean removeFromCart(int id){ return cartIDs.remove((Integer) new Integer(id)); }

    /**
     * adds post to cart by id. swallows duplicates
     * @param id post id
     * @return whether or not the operation was successful
     */
    public void addToCart(int id) {
        // no duplicates
        removeFromCart(id);
        cartIDs.add(0, id);
    }

    /**
     * Returns the postHistory
     * @return the postHistory
     */
    public String getPostHistoryString()
    {
        String history = "";
        for(int i = 0; i < postHistory.size() - 1; i++)
        {
            history += postHistory.get(i) + "\n";
        }
        if(postHistory.size() > 0)
        {
            history += postHistory.get(postHistory.size() - 1);
        }
        return history;
    }

    /**
     * Returns the cartIDs
     * @return the cartIDs
     */
    public String getCartIDsString()
    {
        String history = "";
        for (int i = 0; i < cartIDs.size() - 1; i++)
        {
            history += cartIDs.get(i) + "\n";
        }
        if (cartIDs.size() > 0)
        {
            history += cartIDs.get(cartIDs.size() - 1);
        }
        return history;
    }

    /**
     * Returns a list of cartIDs parsed from input
     * @param history
     * @return a list of CartIDs
     */
    public ArrayList<Integer> getCartIDsFromString(String history)
    {
        if (history.equals(""))
        {
            return new ArrayList<Integer>();
        }
        String[] split = history.split("\n");
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < split.length; i++)
        {
            list.add(Integer.getInteger(split[i]));
        }
        return list;
    }

    /**
     * Converts User object to unique String
     *
     * @return A unique String representing the user
     */
    public String toString()
    {
        ArrayList<User> users = new ArrayList<User>(1);
        users.add(this);
        return Server.stripOuterJson(Server.userToJson(users));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(photo);
        dest.writeInt(profileID);
        dest.writeString(bio);
        dest.writeString(mobileNumber);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(salt);
        if (postHistory == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(postHistory);
        }
        dest.writeByte((byte) (verified ? 0x01 : 0x00));
        if (cartIDs == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(cartIDs);
        }
        dest.writeByte((byte) (deleted ? 0x01 : 0x00));
    }


    /**
     * Method to convert an ArrayList of users to its json sequence
     */

    public static class UserSerializer implements JsonSerializer<ArrayList<User>> {

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

    /**
     * Convert from the json sequence to an arrayList of User Objects
     */

    static class UserDeserializer implements JsonDeserializer<JSONUser> {


        @Override
        public JSONUser deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException, NumberFormatException {

            //Get the first element of the json string and the JSONUser to be returned
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

                // Get the cartIDs and postHistory from the obj
                String[] pH= obj.get("postHistory").getAsString().split("\n");
                String[] cID= obj.get("cartIDs").getAsString().split("\n");

                ArrayList<Integer> cardIDsTA= new ArrayList<Integer>();
                ArrayList<Integer> postHistTA= new ArrayList<Integer>();

                int i=0;
                while (i< cID.length){

                    try{
                        cardIDsTA.add(Integer.parseInt(cID[i]));
                    }
                    catch(NumberFormatException e){
                        Log.e("ERROR", e.getMessage());
                        e.printStackTrace();
                    }

                    i= i+1;
                }

                i= 0;
                while (i< pH.length){

                    try{
                        postHistTA.add(Integer.parseInt(pH[i]));
                    }
                    catch(NumberFormatException e){
                        Log.e("ERROR", e.getMessage());
                        e.printStackTrace();
                    }

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

    /**
     * Class that is used to store the json user objects when they are being deserialized
     */

    public static class JSONUser {

        public ArrayList<User> users;
    }
}
