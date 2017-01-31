package com.tmnt.tritontrade;
import java.util.ArrayList;
/**
 * Created by Frank on 31/01/2017.
 */

public class User {
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

    public User(String name, String photo, int profileID, String bio,
                String mobileNumber, String email, String password, String salt,
                ArrayList<Integer> postHistory, boolean verified, ArrayList<Integer> cartIDs)
    {
        //TODO ERROR CHECKS
        this.name = name;
        this.photo = photo;
        this.profileID = profileID;
        this.bio = bio;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.postHistory = postHistory;
        this.verified = verified;
        this.cartIDs = cartIDs;
        Server.addNewUser(name, photo, bio, mobileNumber, email, password);
    }

    /**
     * No bio constructor
     */
    public User(String name, String photo, int profileID, String mobileNumber, String email,
                String password, String salt, ArrayList<Integer> postHistory, boolean verified, ArrayList<Integer> cartIDs)
    {
        //TODO ERROR CHECKS
        this.name = name;
        this.photo = photo;
        this.profileID = profileID;
        this.bio = "";
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.postHistory = postHistory;
        this.verified = verified;
        this.cartIDs = cartIDs;
        Server.addNewUser(name, photo, bio, mobileNumber, email, password);
    }

    /**
     * No photo constructor
     */
    public User(String name, int profileID, String bio,
                String mobileNumber, String email, String password, String salt,
                ArrayList<Integer> postHistory, boolean verified, ArrayList<Integer> cartIDs)
    {
        //TODO ERROR CHECKS
        this.name = name;
        this.photo = "";
        this.profileID = profileID;
        this.bio = bio;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.postHistory = postHistory;
        this.verified = verified;
        this.cartIDs = cartIDs;
        Server.addNewUser(name, photo, bio, mobileNumber, email, password);
    }

    /**
     * No bio nor photo constructor
     */
    public User(String name, int profileID, String mobileNumber, String email, String password, String salt,
                ArrayList<Integer> postHistory, boolean verified, ArrayList<Integer> cartIDs)
    {
        //TODO ERROR CHECKS
        this.name = name;
        this.photo = "";
        this.profileID = profileID;
        this.bio = "";
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.postHistory = postHistory;
        this.verified = verified;
        this.cartIDs = cartIDs;
        Server.addNewUser(name, photo, bio, mobileNumber, email, password);
    }

    /**
     * Getter for name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter for name
     */
    public boolean setName(String name)
    {
        if(name==null)
            return false;

        this.name = name;
        return Server.modifyExistingUser(this);
    }

    /**
     * Getter for photo
     */
    public String getPhoto()
    {
        return photo;
    }

    /**
     * Setter for photo
     */
    public boolean setPhoto(String photo)
    {
        if(photo==null)
            return false;
        this.photo = photo;
        return Server.modifyExistingUser(this);
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
        if(profileID==0)
            return false;
        this.profileID = profileID;
        return Server.modifyExistingUser(this);
    }

    /**
     * Getter for bio
     */
    public String getBio()
    {
        return bio;
    }

    /**
     * Setter for bio
     */
    public boolean setBio(String bio)
    {
        if(bio==null)
            return false;
        this.bio = bio;
        return Server.modifyExistingUser(this);
    }

    /**
     * Getter for mobile number
     */
    public String getMobileNumber()
    {
        return mobileNumber;
    }

    /**
     * Setter for mobile number
     */
    public boolean setMobileNumber(String mobileNumber)
    {
        if(mobileNumber==null)
            return false;
        this.mobileNumber = mobileNumber;
        return Server.modifyExistingUser(this);
    }

    /**
     * Getter for email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Setter for email
     */
    public boolean setEmail(String email)
    {
        if (email == null)
            return false;
        this.email = email;
        return Server.modifyExistingUser(this);
    }

    /**
     * Getter for cartIDs
     */
    public ArrayList<Integer> getCartIDs()
    {
        return cartIDs;
    }

    /**
     * Setter for cartIDs
     */
    public boolean setCartIDs(ArrayList<Integer> cartIDs)
    {
        if(cartIDs==null)
            return false;
        this.cartIDs = cartIDs;
        return Server.modifyExistingUser(this);
    }

    /**
     * Getter for password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets user password
     */
    public boolean setPassword(String password)
    {
        if (password == null)
            return false;
        this.password = password;
        return Server.modifyExistingUser(this);
    }

    /**
     * Getter for salt
     */
    public String getSalt()
    {
        return salt;
    }

    /**
     * Setter for salt
     */
    public boolean setSalt(String salt)
    {
        if (salt == null)
            return false;
        this.salt = salt;
        return Server.modifyExistingUser(this);
    }

    /**
     * Getter for verified field
     */
    public boolean getVerified()
    {
        return verified;
    }

    /**
     * Sets the user as verified
     */
    public boolean setVerified(boolean verified)
    {
        this.verified = verified;
        return Server.modifyExistingUser(this);
    }

    /**
     * Adds post to postHistory by ID
     */
    public boolean addToPostHistory(int id)
    {
        if (id == 0)
            return false;
        postHistory.add(id);
        return Server.modifyExistingUser(this);
    }

    /**
     * Adds item to cart by id
     */
    public boolean addToCart(int id)
    {
        if(id==0)
            return false;

        cartIDs.add(id);
        return Server.modifyExistingUser(this);
    }

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

    static ArrayList<Integer> getPostHistoryFromString(String history)
    {
        if(history == "")
        {
            return new ArrayList<Integer>();
        }
        String[] split = history.split("\n");
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < split.length; i++)
        {
            list.add(Integer.getInteger(split[i]));
        }
        return list;
    }

    static ArrayList<Integer> getCartIDsFromString(String history)
    {
        if (history == "")
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
    public String ToString()
    {
        return "["
                + "[" + getName() + "], "
                + "[" + getPhoto() + "], "
                + "[" + getProfileID() + "], "
                + "[" + getBio() + "], "
                + "[" + getMobileNumber() + "], "
                + "[" + getEmail() + "], "
                + "[" + getPassword() + "], "
                + "[" + getSalt() + "], "
                + "[" + getPostHistoryString() + "], "
                + "[" + getVerified() + "], "
                + "[" + getCartIDs() + "]]";
    }
}
