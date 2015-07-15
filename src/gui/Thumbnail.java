// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Thumbnail.java

package gui;
import java.awt.image.*;

public class Thumbnail 
{

	private BufferedImage image;
	private String location;
	
	public Thumbnail(BufferedImage image, String location)
	{
		this.image = image;
		this.location = location;
	}
	
	/* getImage - returns "image", the BufferedImage associated with the class
	 */
	public BufferedImage getImage()
	{
		return this.image;
	}
	
	/* setImage - sets "image" to be the BufferedImage passed in as a parameter 
	 *    image - the new image
	 */
	public void setImage(BufferedImage image)
	{
		this.image = image;
	}
	
	/* getLocation - returns "location", the file system path associated with the thumbnail
	 */
	public String getLocation()
	{
		return this.location;
	}
	
	/* setLocation - sets "location" to be the String passed in as a parameter
	 *    location - the new location
	 */
	public void setLocation(String location)
	{
		this.location = location;
	}
	
}