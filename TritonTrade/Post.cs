using System;
using System.Collections.Generic;

public class Post
{
	private string productName;
	private List<string> photos; // list of URLs to images
	private string description;
	private float price;
	private List<string> tags;
	private ulong profileID;
	private ulong postID;
	private bool selling;
	private DateTime dateCreated;
	private string contactInfo;

	public Post(string productName, List<string> photos, string description,
		float price, List<string> tags, ulong profileID, ulong postID,
		bool selling, DateTime dateCreated, string contactInfo)
	{

	}

	override public string ToString()
	{
		return "";
	}

	// getters and setters
}
