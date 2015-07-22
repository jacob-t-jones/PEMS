// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Thumbnail.java

package gui;
import java.awt.image.*;
import javax.swing.*;

public class Thumbnail extends JLabel
{

	private BufferedImage image;
	private String filePath;
	private String fileName;
	private String fileExt;
	
	public Thumbnail(BufferedImage image, String filePath, String fileName, String fileExt)
	{
		super(new ImageIcon(image));
		this.image = image;
		this.filePath = filePath;
		this.fileName = fileName;
		this.fileExt = fileExt;
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
	
	/* getFileExt - returns "fileExt", the extension of the image file associated with the Thumbnail
	 */
	public String getFileExt()
	{
		return this.fileExt;
	}
	
	/* setFileExt - sets "fileExt" to be the String passed in as a parameter
	 *    fileExt - the new file extension
	 */
	public void setFileExt(String fileExt)
	{
		this.fileExt = fileExt;
	}
	
}