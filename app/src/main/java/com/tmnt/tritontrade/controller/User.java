package com.tmnt.tritontrade.controller;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {
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
                boolean deleted)
    {
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
        this.emailVerificationLink = emailVerificationLink;
        this.deleted = deleted;
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
        if(mobileNumber==null)
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
        if (email == null)
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
        if (id == 0)
            return false;
        postHistory.add(id);
        return true;
    }

    /**
     * Adds item to cart by id
     */
    public boolean addToCart(int id)
    {
        if(id==0)
            return false;

        cartIDs.add(id);
        return true;
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
     * Returns the postHistory (List)
     * @param history the post history(String)
     * @return a list of postIDs
     */
    static ArrayList<Integer> getPostHistoryFromString(String history)
    {
        if(history.equals(""))
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

    /**
     * Returns a list of cartIDs parsed from input
     * @param history
     * @return a list of CartIDs
     */
    static ArrayList<Integer> getCartIDsFromString(String history)
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
                + "[" + getCartIDsString() + "], "
                + "[" + getEmailVerificationLink() + "], "
                + "[" + getDeleted()+"]";
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


}
