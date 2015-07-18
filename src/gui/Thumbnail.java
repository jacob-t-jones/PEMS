// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Thumbnail.java

package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import javax.swing.JLabel;

public class Thumbnail extends JLabel implements ActionListener
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
	
	/* getLocation - returns "location", the file system path associated with the Thumbnail
	 */
	public String getFileLocation()
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

	/* actionPerformed - mandatory override method
	 *               e - mandatory ActionEvent parameter
	 */
	public void actionPerformed(ActionEvent e) 
	{

	}
	
}