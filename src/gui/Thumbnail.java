// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Thumbnail.java

package gui;
import java.awt.image.*;
import java.nio.file.*;

public class Thumbnail 
{

	private BufferedImage image;
	private Path location;
	
	public Thumbnail(BufferedImage image, Path location)
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
	public Path getLocation()
	{
		return this.location;
	}
	
	/* setLocation - sets "location" to be the Path passed in as a parameter
	 *    location - the new location
	 */
	public void setLocation(Path location)
	{
		this.location = location;
	}
	
}