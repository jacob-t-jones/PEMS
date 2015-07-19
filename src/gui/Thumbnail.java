// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Thumbnail.java

package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import javax.swing.*;

public class Thumbnail extends JLabel implements ActionListener
{

	private BufferedImage image;
	private String filePath;
	private String fileName;
	
	public Thumbnail(BufferedImage image, String filePath, String fileName)
	{
		super(new ImageIcon(image));
		this.image = image;
		this.filePath = filePath;
		this.fileName = fileName;
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
	
	/* getFilePath - returns "filePath", the file system path associated with the Thumbnail
	 */
	public String getFilePath()
	{
		return this.filePath;
	}
	
	/* setFilePath - sets "filePath" to be the String passed in as a parameter
	 *    filePath - the new file path
	 */
	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}
	
	/* getFileName - returns "fileName", the name of the image file associated with the Thumbnail
	 */
	public String getFileName()
	{
		return this.fileName;
	}
	
	/* setFileName - sets "fileName" to be the String passed in as a parameter
	 *    fileName - the new file name
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	/* actionPerformed - mandatory override method
	 *               e - mandatory ActionEvent parameter
	 */
	public void actionPerformed(ActionEvent e) 
	{

	}
	
}