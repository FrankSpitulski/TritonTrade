package com.tmnt.tritontrade;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Frank on 31/01/2017.
 */

public class Post {
    private String productName;
    private String photos; // URL to image
    private String description;
    private float price;
    private ArrayList<String> tags;
    private int profileID;
    private int postID;
    private boolean selling;
    private Date dateCreated;
    private String contactInfo;

    /***
     * Constructor for the post class
     */
    public Post(String productName, String photos, String description,
                float price, ArrayList<String> tags, String profileID, int postID,
                boolean selling, Date dateCreated, String contactInfo)
    {
        this.productName = productName;
        this.photos = photos;
        this.description = description;
        this.price = price;
        this.tags = tags;
        this.productName = productName;
        this.postID = postID;
        this.selling = selling;
        this.dateCreated = dateCreated;
        this.contactInfo = contactInfo;
    }

    /**
     * Getter for product name
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * Setter for product name
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
     */
    public String getPhotos()
    {
        return photos;
    }

    /**
     * Setter for photos
     */
    public boolean setPhotos(String photos)
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
     */

    public String getDescription()
    {
        return description;
    }

    /**
     * Setter for description
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

    /***
     * Getter for price
     */
    public float getPrice()
    {
        return price;
    }

    /**
     * Setter for price
     */
    public boolean setPrice(float price)
    {
        if (price == 0)
        {
            return false;
        }
        this.price = price;
        return Server.modifyExistingPost(this);
    }

    /**
     * Getter for tags
     */
    public ArrayList<String> getTags()
    {
        return tags;
    }

    /**
     * Setter for tags
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
     */
    public int getProfileID()
    {
        return profileID;
    }

    /**
     * Setter for profileID
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
     */
    public int getPostID()
    {
        return postID;
    }

    /**
     * Setter for postID
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
     */
    public boolean getSelling()
    {
        return selling;
    }

    /**
     * Setter for selling
     */
    public boolean setSelling(boolean selling)
    {
        this.selling = selling;
        return Server.modifyExistingPost(this);
    }

    /**
     * Getter for dateCreated
     */
    public Date getDateCreated()
    {
        return dateCreated;
    }

    /**
     * Setter for dateCreated
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
     * Getter for contacInfo
     */
    public String getContactInfo()
    {
        return contactInfo;
    }

    /**
     * Setter for contactInfo
     */
    public boolean setContacInfo(String contactInfo)
    {
        if (contactInfo == null)
        {
            return false;
        }
        this.contactInfo = contactInfo;
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
                + "[" + getPrice() + "], "
                + "[" + getTags() + "], "
                + "[" + getProfileID() + "], "
                + "[" + getPostID() + "], "
                + "[" + getSelling() + "], "
                + "[" + getDateCreated() + "], "
                + "[" + getContactInfo() + "]]";
    }

}
