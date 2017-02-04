package com.tmnt.tritontrade;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.*;

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
                boolean selling, boolean active,Date dateCreated, String contactInfo, boolean deleted)
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
        if(productName == null)
        {
            return false;
        }
        this.productName = productName;
        return Server.modifyExistingPost(this);
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
        if (photos == null)
        {
            return false;
        }
        this.photos = photos;
        return Server.modifyExistingPost(this);
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
        if (description == null)
        {
            return false;
        }
        this.description = description;
        return Server.modifyExistingPost(this);
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
        if (price < 0)
        {
            return false;
        }
        this.price = price;
        return Server.modifyExistingPost(this);
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
        if (tags == null)
        {
            return false;
        }
        this.tags = tags;
        return Server.modifyExistingPost(this);
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
        if (profileID == 0)
        {
            return false;
        }
        this.profileID = profileID;
        return Server.modifyExistingPost(this);
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
        if (postID == 0)
        {
            return false;
        }
        this.postID = postID;
        return Server.modifyExistingPost(this);
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
        return Server.modifyExistingPost(this);
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
        return Server.modifyExistingPost(this);
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
        return Server.modifyExistingPost(this);
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
        if (contactInfo == null)
        {
            return false;
        }
        this.contactInfo = contactInfo;
        return Server.modifyExistingPost(this);
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
        return Server.modifyExistingPost(this);
    }

    /**
     * Returns a unique String identifying the post
     *
     * @return The String representing the post
     */
    public String toString()
    {
        return "["
                + "[" + getProductName() + "], "
                + "[" + getPhotos() + "], "
                + "[" + getDescription() + "], "
                + "[" + getPrice() + "], "
                + "[" + getTags() + "], "
                + "[" + getProfileID() + "], "
                + "[" + getPostID() + "], "
                + "[" + getSelling() + "], "
                + "[" + getSold() + "], "
                + "[" + getDateCreated() + "], "
                + "[" + getContactInfo() + "], "
                + "[" + getDeleted() + "]]";
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





}
