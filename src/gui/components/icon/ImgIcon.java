// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ImgIcon.java

package gui.components.icon;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import org.apache.sanselan.*;
import org.imgscalr.*;
import backend.exceptions.*;

/** Subclass of <code>JLabel</code> that holds a downsized icon version of a given image.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class ImgIcon extends JLabel
{
	
	private BufferedImage image;

	/** Retrieves and rezises the image found at the passed in file path. Displays it within this icon.
	 * 
	 *  @param filePath the file path of the image to display in this icon
	 *  @param method the ImgScalr resampling method to use
	 *  @param size the size that this icon should be shrunk down to
	 *  @throws InvalidFileException if the image contained within this component can not be read into memory
	 */
	public ImgIcon(String filePath, Scalr.Method method, int size) throws InvalidFileException 
	{
		this.image = Scalr.resize(this.retrieveImage(filePath), method, size, Scalr.OP_ANTIALIAS);
		super.setIcon(new ImageIcon(this.image));
	}
	
	/** Retrieves and rezises the image found at the passed in file path. Displays it within this icon.
	 * 
	 *  @param filePath the file path of the image to display in this icon
	 *  @param method the ImgScalr resampling method to use
	 *  @param width the width that this icon should be shrunk down to
	 *  @param height the height that this icon should be shrunk down to
	 *  @throws InvalidFileException if the image contained within this component can not be read into memory
	 */
	public ImgIcon(String filePath, Scalr.Method method, int width, int height) throws InvalidFileException 
	{
		this.image = Scalr.resize(this.retrieveImage(filePath), method, width, height, Scalr.OP_ANTIALIAS);
		super.setIcon(new ImageIcon(this.image));
	}
	
	/** Uses the passed in file path to read the associated <code>BufferedImage</code> into memory.
	 * 
	 *  @param filePath the file path in question
	 *  @return <code>BufferedImage<code> object representing the raw image found at the passed in file path
	 *  @throws InvalidFileException if the image can not be read into memory
	 */
	private BufferedImage retrieveImage(String filePath) throws InvalidFileException
	{
		File imageFile = new File(filePath);
		try 
		{
			return Sanselan.getBufferedImage(imageFile);
		} 
		catch (ImageReadException | IOException e) 
		{
			throw new InvalidFileException("Unable to retrieve image", e);
		}
	}
	
}