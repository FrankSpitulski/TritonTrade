package com.tmnt.tritontrade.controller;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Post implements Parcelable {
    private String productName;
    private ArrayList<String> photos; // URL to image
    private String description;
    private float price;
    private ArrayList<String> tags;
    private int profileID;
    private int postID;
    private boolean selling;
    private boolean active;
    private Date dateCreated;
    private String contactInfo;
    private boolean deleted;

    /***
     * Constructor for the post class
     */
    public Post(String productName, ArrayList<String> photos, String description,
                float price, ArrayList<String> tags, int profileID, int postID,
                boolean selling, boolean active ,Date dateCreated, String contactInfo, boolean deleted)
    {
        this.productName = productName;
        this.photos = photos;
        this.description = description;
        this.price = price;
        this.tags = tags;
        this.productName = productName;
        this.profileID = profileID;
        this.postID = postID;
        this.selling = selling;
        this.active = active;
        this.dateCreated = dateCreated;
        this.contactInfo = contactInfo;
        this.deleted = deleted;
    }

    /**
     * Getter for productName
     * @return string containing the productName
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * Setter for productName
     * @param productName
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setProductName(String productName)
    {
        //makes sure string is not null
        if(productName == null)
        {
            return false;
        }
        this.productName = productName;
        return true;
    }

    /**
     * Getter for photos
     * @return string containing photos
     */
    public ArrayList<String> getPhotos()
    {
        return photos;
    }

    /**
     * Setter for photos
     * @param photos
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setPhotos(ArrayList<String> photos)
    {
        //makes sure arraylist is not null
        if (photos == null)
        {
            return false;
        }
        this.photos = photos;
        return true;
    }

    /**
     * Getter for description
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Setter for description
     * @param description
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setDescription(String description)
    {
        //makes sure string is not null
        if (description == null)
        {
            return false;
        }
        this.description = description;
        return true;
    }

    /**
     * Getter for price
     * @return the posting price
     */
    public float getPrice()
    {
        return price;
    }

    /**
     * Setter for price
     * @param price
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setPrice(float price)
    {
        //makes sure price is not negative
        if (price < 0)
        {
            return false;
        }
        this.price = price;
        return true;
    }

    /**
     * Getter for tags
     * @return a list of tags
     */
    public ArrayList<String> getTags()
    {
        return tags;
    }

    /**
     * Setter for tags
     * @param tags
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setTags(ArrayList<String> tags)
    {
        //makes sure arraylist is not null
        if (tags == null)
        {
            return false;
        }
        this.tags = tags;
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
     * @param profileID
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setProfileID(int profileID)
    {
        this.profileID = profileID;
        return true;
    }

    /**
     * Getter for postID
     * @return the postID
     */
    public int getPostID()
    {
        return postID;
    }

    /**
     * Setter for postID
     * @param postID
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setPostID(int postID)
    {
        this.postID = postID;
        return true;
    }

    /**
     * Getter for selling
     * @return true if selling, false otherwise
     */
    public boolean getSelling()
    {
        return selling;
    }

    /**
     * Setter for selling
     * @param selling
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setSelling(boolean selling)
    {
        this.selling = selling;
        return true;
    }

    /**
     * Getter for active
     * @return true if item of post is active, false otherwise
     */
    public boolean getSold(){
        return active;
    }

    /**
     * Setter for active
     * @param active
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setActive(boolean active){
        this.active = active;
        return true;
    }

    /**
     * Getter for dateCreated
     * @return the date created
     */
    public Date getDateCreated()
    {
        return dateCreated;
    }

    /**
     * Setter for dateCreated
     * @param dateCreated
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setDateCreated(Date dateCreated)
    {
        if (dateCreated == null)
        {
            return false;
        }
        this.dateCreated = dateCreated;
        return true;
    }

    /**
     * Getter for contactInfo
     * @return the contact info
     */
    public String getContactInfo()
    {
        return contactInfo;
    }

    /**
     * Setter for contactInfo
     * @param contactInfo the contactInfo
     * @return If invalid input, nothing is updated and returns false
     */
    public boolean setContactInfo(String contactInfo)
    {
        //makes sure contactInfo string is not null
        if (contactInfo == null)
        {
            return false;
        }
        this.contactInfo = contactInfo;
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
     * Returns a unique String identifying the post
     *
     * @return The String representing the post
     */
    public String toString()
    {
        ArrayList<Post> posts = new ArrayList<Post>(1);
        posts.add(this);
        return Server.stripOuterJson(Server.postToJson(posts));
    }

    //*********PARCELABLE METHODS************
    protected Post(Parcel in) {
        productName = in.readString();
        if (in.readByte() == 0x01) {
            photos = new ArrayList<String>();
            in.readList(photos, String.class.getClassLoader());
        } else {
            photos = null;
        }
        description = in.readString();
        price = in.readFloat();
        if (in.readByte() == 0x01) {
            tags = new ArrayList<String>();
            in.readList(tags, String.class.getClassLoader());
        } else {
            tags = null;
        }
        profileID = in.readInt();
        postID = in.readInt();
        selling = in.readByte() != 0x00;
        active = in.readByte() != 0x00;
        long tmpDateCreated = in.readLong();
        dateCreated = tmpDateCreated != -1 ? new Date(tmpDateCreated) : null;
        contactInfo = in.readString();
        deleted = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        if (photos == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(photos);
        }
        dest.writeString(description);
        dest.writeFloat(price);
        if (tags == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(tags);
        }
        dest.writeInt(profileID);
        dest.writeInt(postID);
        dest.writeByte((byte) (selling ? 0x01 : 0x00));
        dest.writeByte((byte) (active ? 0x01 : 0x00));
        dest.writeLong(dateCreated != null ? dateCreated.getTime() : -1L);
        dest.writeString(contactInfo);
        dest.writeByte((byte) (deleted ? 0x01 : 0x00));
    }

    public static String getDefaultImage(){
        return "/img/imageCDN/default/defaultPost.jpg";
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };


    /**
     * Converts the ArrayList of post objects into the correct json format
     */

    public static class PostSerializer implements JsonSerializer<ArrayList<Post>> {
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

    /**
     * Converts the json sequence into an arrayList of Post objects
     */

    public static class PostDeserializer implements JsonDeserializer<JSONPost> {

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

    /**
     * Class that is used to deserialize the json post objects when they are being deserialized
     */

    public static class JSONPost {

        public ArrayList<Post> posts;
    }
}
