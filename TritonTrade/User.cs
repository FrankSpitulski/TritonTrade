using System;
using System.Collections.Generic;

public class User
{
	private string name;
	private string photo; // URL to image
	private ulong profileID;
	private string bio;
	private string mobileNumber;
	private string email;
	private string password; // hashed
	private string salt;
	private List<ulong> postHistory;
	private bool verified;
	private List<ulong> cartIDs;

	public User(string name, string photo, ulong profileID, string bio,
		string mobileNumber, string email, string password, string salt,
		List<ulong> postHistory, bool verified, List<ulong> cartIDs)
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
     * Getter for name
     */
	public string getName()
	{
		return name;
	}

	/**
     * Setter for name
     */
	public bool setName(string name)
	{
		if (name == null)
			return false;

		this.name = name;
		return Server.modifyExistingUser(this);
	}

	/**
     * Getter for photo
     */
	public string getPhoto()
	{
		return photo;
	}

	/**
     * Setter for photo
     */
	public bool setPhoto(string photo)
	{
		if (photo == null)
			return false;
		this.photo = photo;
		return Server.modifyExistingUser(this);
	}

	/**
     * Getter for profileID
     */
	public ulong getProfileID()
	{
		return profileID;
	}

	/**
     * Setter for profileID
     */
	public bool setProfileID(ulong profileID)
	{
		if (profileID == 0)
			return false;
		this.profileID = profileID;
		return Server.modifyExistingUser(this);
	}

	/**
     * Getter for bio
     */
	public string getBio()
	{
		return bio;
	}

	/**
	 * Setter for bio
	 */
	public bool setBio(string bio)
	{
		if (bio == null)
			return false;
		this.bio = bio;
		return Server.modifyExistingUser(this);
	}

	/**
     * Getter for mobile number
     */
	public string getMobileNumber()
	{
		return mobileNumber;
	}

	/**
     * Setter for mobile number
     */
	public bool setMobileNumber(string mobileNumber)
	{
		if (mobileNumber == null)
			return false;
		this.mobileNumber = mobileNumber;
		return Server.modifyExistingUser(this);
	}

	/**
     * Getter for email
     */
	public string getEmail()
	{
		return email;
	}

	/**
     * Setter for email
     */
	public bool setEmail(string email)
	{
		if (email == null)
			return false;
		this.email = email;
		return Server.modifyExistingUser(this);
	}

	/**
     * Getter for cartIDs
     */
	public List<ulong> getCartIDs()
	{
		return cartIDs;
	}

	/**
     * Setter for cartIDs
     */
	public bool setCartIDs(List<ulong> cartIDs)
	{
		if (cartIDs == null)
			return false;
		this.cartIDs = cartIDs;
		return Server.modifyExistingUser(this);
	}

	/**
     * Getter for password
     */
	public string getPassword()
	{
		return password;
	}

	/**
     * Sets user password
     */
	public bool setPassword(string password)
	{
		if (password == null)
			return false;
		this.password = password;
		return Server.modifyExistingUser(this);
	}

	/**
     * Getter for salt
     */
	public string getSalt()
	{
		return salt;
	}

	/**
     * Setter for salt
     */
	public bool setSalt(string salt)
	{
		if (salt == null)
			return false;
		this.salt = salt;
		return Server.modifyExistingUser(this);
	}

	/**
     * Getter for verified field
     */
	public bool getVerified()
	{
		return verified;
	}

	/**
     * Sets the user as verified
     */
	public bool setVerified(bool verified)
	{
		this.verified = verified;
		return Server.modifyExistingUser(this);
	}

	/**
     * Adds post to postHistory by ID
     */
	public bool addToPostHistory(ulong id)
	{
		if (id == 0)
			return false;
		postHistory.Add(id);
		return Server.modifyExistingUser(this);
	}

	/**
	 * Adds item to cart by id
	 */
	public bool addToCart(ulong id)
	{
		if (id == 0)
			return false;

		cartIDs.Add(id);
		return Server.modifyExistingUser(this);
	}

	public string getPostHistoryString()
	{
		string history = "";
		for (int i = 0; i < postHistory.Count - 1; i++)
		{
			history += postHistory[i] + "\n";
		}
		if (postHistory.Count > 0)
		{
			history += postHistory[postHistory.Count - 1];
		}
		return history;
	}

	public string getCartIDsString()
	{
		string history = "";
		for (int i = 0; i < cartIDs.Count - 1; i++)
		{
			history += cartIDs[i] + "\n";
		}
		if (cartIDs.Count > 0)
		{
			history += cartIDs[cartIDs.Count - 1];
		}
		return history;
	}

	internal static List<ulong> getPostHistoryFromString(string history)
	{
		if (history == "")
		{
			return new List<ulong>();
		}
		string[] split = history.Split('\n');
		List<ulong> list = new List<ulong>();
		for (int i = 0; i < split.Length; i++)
		{
			list.Add(Convert.ToUInt64(split[i]));
		}
		return list;
	}

	internal static List<ulong> getCartIDsFromString(string history)
	{
		if (history == "")
		{
			return new List<ulong>();
		}
		string[] split = history.Split('\n');
		List<ulong> list = new List<ulong>();
		for (int i = 0; i < split.Length; i++)
		{
			list.Add(Convert.ToUInt64(split[i]));
		}
		return list;
	}

	override public string ToString()
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
