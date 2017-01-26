using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text.RegularExpressions;
public static class Server
{
	//Information of Frank's Server
	const string serverName = "spitulski.no-ip.biz";
	const string uid = "Michelangelo";
	const string pwd = "Leonardo";
	const string database = "TritonTrade";

	//the server connection
	//private static MySqlConnection connection;

	//whether or not the server is currently connected
	private static bool connected;

	/**
     * Opens a server connection. Be sure to run disconnect() for each connect()
     * 
     * @return Whether or not the connection returned an exception
     */
	private static bool connect()
	{

		/*string myConnectionString = "server=" + serverName + ";uid=" + uid + ";pwd=" + pwd + ";database=" + database + ";";

		try
		{
			connection = new MySqlConnection(myConnectionString);
			connection.Open();
			connected = true;
		}
		catch (Exception e)
		{
			Debug.WriteLine(e);
			connected = false;
			return false;
		}
        */
		return true;
	}

	/**
     * Closes a server connection. Be sure to run after every connect()
     *
     * @return Whether or not the connection returned an exception
     */
	private static bool disconnect()
	{
		/*if (!connected)
		{
			return true;
		}
		try
		{
			connection.Close();
			connected = false;
		}
		catch (Exception e)
		{
			Debug.WriteLine(e);
			return false;
		}*/
		return true;
	}

	/**
     * Add a post to the database
     * 
     * @return Whether or not the operation was successful
     */
	public static bool addPost(Post post)
	{
		while (!connected)
		{
			try
			{
				connect();
			}
			catch (Exception e)
			{
				Debug.WriteLine(e);
				return false;
			}

		}
		// add post

		disconnect();

		// no duplicate posts allowed, return false if duplicate postID
		return false;
	}

	/**
     * Adds a user to the database
     * 
     * @return true if add successful
     */
	public static bool addNewUser(string name, string photo, string bio,
		string mobileNumber, string email, string password)
	{
		// ucsd.edu only!
		if (!Regex.IsMatch(email, @"ucsd.edu$", RegexOptions.IgnoreCase))
		{
			Debug.WriteLine("email rejected");
			return false;
		}
		Debug.WriteLine("email accepted");

		// connect
		while (!connected)
		{
			try
			{
				connect();
			}
			catch (Exception e)
			{
				Debug.WriteLine(e);
				Debug.WriteLine("connection failed");
				return false;
			}
		}
		Debug.WriteLine("connection established");
        /*
		// check to see if email is already registered
		string sqlString = "SELECT profileID FROM users WHERE email='" + email + "'";
		MySqlCommand cmd = new MySqlCommand(sqlString, connection);
		MySqlDataAdapter adapter = new MySqlDataAdapter(cmd);
		System.Data.DataSet data = new System.Data.DataSet();
		adapter.Fill(data);

		if (data.Tables[0].Rows.Count != 0)
		{
			Debug.WriteLine("user duplicate");
			disconnect();
			return false;
		}

		Debug.WriteLine("user not duplicate");

		// get userID
		sqlString = "SELECT COUNT(*) FROM users";
		cmd = new MySqlCommand(sqlString, connection);
		adapter = new MySqlDataAdapter(cmd);
		adapter.Fill(data);
		if (data.Tables[0].Rows.Count == 0)
		{
			Debug.WriteLine("bad count querry");
			disconnect();
			return false; // bad query
		}
		ulong profileID = (ulong)(System.Int64)data.Tables[0].Rows[0][1] + 1;

		// get unused ID
		while (searchUserIDs(profileID) != null)
		{
			profileID++;
		}

		Debug.WriteLine("new ID found " + profileID);

		// get salt for passowrd
		string salt = BCrypt.Net.BCrypt.GenerateSalt();

		// create user object, TODO implement verification, true for now
		User newUser = new User(name, photo, profileID, bio, mobileNumber, email,
			BCrypt.Net.BCrypt.HashPassword(password, salt), salt, new List<ulong>(),
			true, new List<ulong>());

		Debug.WriteLine("user object generated");

		// add user object to server
		sqlString = "INSERT INTO users (name,photo,profileID,bio,mobileNumber"
			+ ",email,password,salt,postHistory,verified,cartIDs)"
			+ "\nVALUES('" + newUser.getName() + "'"
			+ ", '" + newUser.getPhoto() + "'"
			+ ", " + newUser.getProfileID()
			+ ", '" + newUser.getBio() + "'"
			+ ", '" + newUser.getMobileNumber() + "'"
			+ ", '" + newUser.getEmail() + "'"
			+ ", '" + newUser.getPassword() + "'"
			+ ", '" + newUser.getSalt() + "'"
			+ ", '" + newUser.getPostHistoryString() + "'"
			+ ", " + newUser.getVerified()
			+ ", '" + newUser.getCartIDsString() + "'"
			+ ");";

		// connect
		while (!connected)
		{
			try
			{
				connect();
			}
			catch (Exception e)
			{
				Debug.WriteLine(e);
				Debug.WriteLine("connection failed");
				return false;
			}
		}

		cmd = new MySqlCommand(sqlString, connection);
		cmd.ExecuteNonQuery();

		Debug.WriteLine("user added to database");

		disconnect();
        */
		return true;
	}

	/**
     * Attempts to log in to the server with a given login and password
     * 
     * @return true if add successful
     */
	public static User login(string email, string password) // returns a null or empty User if bad login
	{
		while (!connected)
		{
			try
			{
				connect();
			}
			catch (Exception e)
			{
				Debug.WriteLine(e);
				Debug.WriteLine("connection failed");
				return null;
			}
		}

		Debug.WriteLine("connected");
        /*
		// sql lookup command
		string sqlString = "SELECT profileID FROM users WHERE email='" + email + "'";
		MySqlCommand cmd = new MySqlCommand(sqlString, connection);
		MySqlDataAdapter adapter = new MySqlDataAdapter(cmd);
		System.Data.DataSet data = new System.Data.DataSet();
		adapter.Fill(data);

		// duplicate or no emails
		if (data.Tables.Count != 1 || data.Tables[0].Rows.Count != 1)
		{
			Debug.WriteLine("bad email search");
			disconnect();
			return null;
		}

		// get ID of email
		ulong userID = (ulong)(System.Int64)data.Tables[0].Rows[0][0];

		// get data of valid user
		User user = searchUserIDs(userID);

		if (!user.getVerified())
		{
			Debug.WriteLine("user not verified");
			disconnect();
			return null; // unverified cannot login
		}

		// password test
		if (BCrypt.Net.BCrypt.HashPassword(password, user.getSalt())
			== user.getPassword())
		{
			Debug.WriteLine("user login successful");
			return user;
		}

		// password did not match
		Debug.WriteLine("password mismatch");
		disconnect();
        */
		return null;
	}

	/**
     * Searches the database for posts with the given tags
     * 
     * @param tags The list of tags to search with
     * @return The list of posts with those tags
     */
	public static List<Post> searchPostTags(List<string> tags)
	{
		return new List<Post>();
	}

	/**
     * Searches the database for posts with the tag
     * 
     * @param tags The tag to search with
     * @return The list of posts with those tags
     */
	public static List<Post> searchPostTags(string tag)
	{
		List<string> single = new List<string>(1);
		single.Add(tag);
		return searchPostTags(single);
	}

	/**
     * Returns the users with the ids in the given list in an arbitrary order
     *  
     * @param tags The list of ids to grab
     * @return The list of User objects with the given ids
     * 
     */
	public static List<User> searchUserIDs(List<ulong> ids)
	{
		//if somehow asked with an empty list, return empty list
		if (ids.Count == 0)
		{
			return new List<User>();
		}

		//tries to connect to server
		while (!connected)
		{
			try
			{
				connect();
			}
			catch (Exception e)
			{
				Debug.WriteLine(e);
				return new List<User>();
			}
		}

		//List to be outputted
		List<User> users = new List<User>();

		//beginning part of sql command
		string sqlString = "SELECT * FROM users WHERE profileID='";
		//iterate through ids and add them to the sql string command
		for (int i = 0; i < ids.Count - 1; i++)
		{
			sqlString += ids[i] + "' OR profileID='";
		}
		//final string should not have an OR profileID= appended
		sqlString += ids[ids.Count - 1];
		sqlString += "'";
        /*
		//create command with sqlString and run it
		MySqlCommand cmd = new MySqlCommand(sqlString, connection);
		MySqlDataAdapter adapter = new MySqlDataAdapter(cmd);
		System.Data.DataSet data = new System.Data.DataSet();
		adapter.Fill(data);

		// duplicate or no emails
		if (data.Tables.Count == 0 || data.Tables[0].Rows.Count == 0)
		{
			disconnect();
			return new List<User>();
		}
		for (int i = 0; i < data.Tables[0].Rows.Count; i++)
		{
			users.Add(new User(
				(string)(System.String)data.Tables[0].Rows[i][0],
				(string)(System.String)data.Tables[0].Rows[i][1],
				(ulong)(System.Int64)data.Tables[0].Rows[i][2],
				(string)(System.String)data.Tables[0].Rows[i][3],
				(string)(System.String)data.Tables[0].Rows[i][4],
				(string)(System.String)data.Tables[0].Rows[i][5],
				(string)(System.String)data.Tables[0].Rows[i][6],
				(string)(System.String)data.Tables[0].Rows[i][7],
				User.getPostHistoryFromString(((string)(System.String)data.Tables[0].Rows[i][8])),
				(int)(System.SByte)data.Tables[0].Rows[i][9] == 1,
				User.getCartIDsFromString((string)(System.String)data.Tables[0].Rows[i][10])
				));
		}

		//disconnect from server
		disconnect();
		//return list of users
        */
		return users;
	}

	/**
     * Gets the user with the specified id
     * 
     * @param id The id to search for
     * @return The user with the given id, null if no match
     */
	public static User searchUserIDs(ulong id)
	{
		List<ulong> single = new List<ulong>(1);
		single.Add(id);
		List<User> users = searchUserIDs(single);
		if (users.Count == 0)
		{
			return null;
		}
		return users[0];
	}

	/**
     * Get the Posts with the given ids
     * 
     * @param ids the ID numbers to search for
     * @return The Posts with the given ids
     */
	public static List<Post> searchPostIDs(List<ulong> ids)
	{
		return new List<Post>();
	}

	/**
     * Gets the post with the specified id
     * 
     * @param id The id to search for
     * @return The post with the given id, null if no match
     */
	public static Post searchPostIDs(ulong id)
	{
		List<ulong> single = new List<ulong>(1);
		single.Add(id);
		List<Post> posts = searchPostIDs(single);
		if (posts.Count == 0)
		{
			return null;
		}
		return posts[0];
	}

	/**
     * Gets the user with the specified id
     * 
     * @param id The id to search for
     * @return The user with the given id
     */
	public static bool modifyExistingPost(Post post)
	{
		// modify post based on a search of postID from the argument with the
		// changed fields
		// return true on success
		return false; // when doesn't exist
	}

	public static bool modifyExistingUser(User user)
	{
		// modify user based on a search of postID from the argument with the
		// changed fields
		// return true on success
		return false; // when doesn't exist
	}

}
