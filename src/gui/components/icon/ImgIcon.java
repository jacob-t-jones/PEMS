// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ImgIcon.java

package gui.components.icon;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import org.imgscalr.*;
import backend.exceptions.*;
import backend.storage.file.img.*;
import gui.*;

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
	
	/** Retrieves the image found within the passed in <code>Img</code> object. Displays it within this icon.
	 * 
	 *  @param img the <code>Img</code> in question
	 */
	public ImgIcon(Img img)
	{
		this.image = img.getImage();
		super.setIcon(new ImageIcon(this.image));
	}
	
	/** Retrieves and rezises the image found within the passed in <code>Img</code> object. Displays it within this icon.
	 * 
	 *  @param img the <code>Img</code> in question
	 *  @param method the ImgScalr resampling method to use
	 *  @param size the size that this icon should be shrunk down to
	 */
	public ImgIcon(Img img, Scalr.Method method, int size)
	{
		this.image = Scalr.resize(img.getImage(), method, size, Scalr.OP_ANTIALIAS);
		super.setIcon(new ImageIcon(this.image));
	}
	
	/** Retrieves and rezises the image found within the passed in <code>Img</code> object. Displays it within this icon.
	 * 
	 *  @param img the <code>Img</code> in question
	 *  @param method the ImgScalr resampling method to use
	 *  @param width the width that this icon should be shrunk down to
	 *  @param height the height that this icon should be shrunk down to
	 */
	public ImgIcon(Img img, Scalr.Method method, int width, int height)
	{
		this.image = Scalr.resize(img.getImage(), method, width, height, Scalr.OP_ANTIALIAS);
		super.setIcon(new ImageIcon(this.image));
	}
	
	/** Returns the <code>BufferedImage</code> associated with this class.
	 * 
	 *  @return the <code>BufferedImage</code> associated with this class
	 */
	public BufferedImage getImage()
	{
		return this.image;
	}
	
	/** Draws a rectangle on this icon based on the <code>Point</code> coordinates passed in as parameters.
	 * 
	 *  @param a <code>Point</code> object that serves as one corner of the rectangle
	 *  @param b <code>Point</code> object that serves as the other corner of the rectangle
	 */
	public void drawCropBox(Point a, Point b)
	{
		Graphics2D g = (Graphics2D)this.getGraphics();
		super.update(g);
		g.setColor(ComponentGenerator.CROPBOX_COLOR);
		g.setStroke(new BasicStroke(3));
		g.drawLine((int)a.getX(), (int)a.getY(), (int)a.getX(), (int)b.getY());
		g.drawLine((int)a.getX(), (int)a.getY(), (int)b.getX(), (int)a.getY());
	    g.drawLine((int)b.getX(), (int)b.getY(), (int)b.getX(), (int)a.getY());
		g.drawLine((int)b.getX(), (int)b.getY(), (int)a.getX(), (int)b.getY());
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
			return ImageIO.read(imageFile);
		}
		catch (IOException e)
		{
			throw new InvalidFileException("Unable to read image into memory", e);
		}
	}
	
}