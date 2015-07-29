// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Img.java

package gui.components.img;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.*;
import javax.imageio.*;
import javax.swing.*;
import org.imgscalr.*;
import exceptions.*;

public class Img extends JLabel
{
	
	private BufferedImage image;
	private String filePath;
	private String fileName;
	private String fileExt;
	private String fileType;
	private String date;

	public Img(String filePath) throws InvalidImgException
	{
		this.filePath = filePath;
		this.image = retrieveImage();
		this.fileName = retrieveFileName();
		this.fileExt = retrieveFileExt();
		this.fileType = retrieveFileType();
		this.date = this.getDate();
		this.refreshIcon();
	}

	public String getDate() 
	{
		// find path / property of the date in the image in the camera
		return "12/12/12";
	}

	public void displayDate(Graphics g) 
	{
		super.paint(g);
		g.setFont(g.getFont().deriveFont(30f));
		g.drawString(this.getDate(), 10, 10);
		g.dispose();
		this.refreshIcon();
	}

	/* getImage - returns "image", the raw BufferedImage stored by this class
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
	
	/* getFilePath - returns "filePath", the full directory path associated with the image
	 */
	public String getFilePath()
	{
		return this.filePath;
	}
	
	/* getFileName - returns "fileName", the file name of the image
	 */
	public String getFileName()
	{
		return this.fileName;
	}
	
	/* getFileExt - returns "fileExt", the file extension of the image
	 */
	public String getFileExt()
	{
		return this.fileExt;
	}
	
	/* getFileType - returns "fileType", the file type of the image
	 */
	public String getFileType()
	{
		return this.fileType;
	}
	
	/* addPadding - adds padding around "image" on all four sides
	 *       size - the width of the padding in pixels
	 *        red - red RGB value for the color of the padding
	 *      green - green RGB value for the color of the padding
	 *       blue - blue RGB value for the color of the padding
	 */
	public void addPadding(int size, int red, int green, int blue)
	{
		this.image = Scalr.pad(image, size, new Color(red, green, blue));
		this.refreshIcon();
	}

	/* applyAntiAliasing - applies anti aliasing procedure to "image"
	 */
	public void applyAntiAliasing()
	{
		this.image = Scalr.apply(image, Scalr.OP_ANTIALIAS);
		this.refreshIcon();
	}
	
	/* brightenImage - brightens "image" by 10%
	 */
	public void brightenImage()
	{
		this.image = Scalr.apply(image, Scalr.OP_BRIGHTER);
		this.refreshIcon();
	}

	/* cropImage - crops "image" to the specifications of the parameters
	 *         x - the x coordinate to begin the crop at
	 *         y - the y coordinate to begin the crop at
	 *     width - the width of the crop
	 *    height - the height of the crop
	 */
	public void cropImage(int x, int y, int width, int height)
	{
		this.image = Scalr.crop(image, x, y, width, height, Scalr.OP_ANTIALIAS);
		this.refreshIcon();
	}
	
	/* darkenImage - darkens "image" by 10%
	 */
	public void darkenImage()
	{
		this.image = Scalr.apply(image, Scalr.OP_DARKER);
		this.refreshIcon();
	}
	
	/* resizeImage - resizes "image" to the size specified in the parameters, maintaining its aspect ratio
	 * 	    method - the resizing method to utilize 
	 *        size - the new size of the image
	 */
	public void resizeImage(Scalr.Method method, int size)
	{
		this.image = Scalr.resize(image, method, size, Scalr.OP_ANTIALIAS);
		this.refreshIcon();
	}

	/* resizeImage - resizes "image" to the width and height specified in the parameters, disregarding aspect ratio
	 * 	    method - the resizing method to utilize 
	 *       width - the new width of the image
	 *      height - the new height of the image
	 */
	public void resizeImage(Scalr.Method method, int width, int height)
	{
		this.image = Scalr.resize(image, method, width, height, Scalr.OP_ANTIALIAS);
		this.refreshIcon();
	}

	/* rotateRight90 - rotates "image" right by 90 degrees
	 */
	public void rotateRight90()
	{
		this.image = Scalr.rotate(image, Scalr.Rotation.CW_90);
		this.refreshIcon();
	}
	
	/* rotateRight180 - rotates "image" right by 180 degrees
	 */
	public void rotateRight180()
	{
		this.image = Scalr.rotate(image, Scalr.Rotation.CW_180);
		this.refreshIcon();
	}

	/* rotateRight270 - rotates "image" right by 270 degrees
	 */
	public void rotateRight270()
	{
		this.image = Scalr.rotate(image, Scalr.Rotation.CW_270);
		this.refreshIcon();
	}
	
	/* toGrayScale - converts "image" to grayscale format
	 */
	public void toGrayscale()
	{
		this.image = Scalr.apply(image, Scalr.OP_GRAYSCALE);
		this.refreshIcon();
	}
	
	/* refreshIcon - reloads the ImageIcon for the label, thus displaying any new changes to "image"
	 */
	protected void refreshIcon()
	{
		super.setIcon(new ImageIcon(this.image));
	}
	
	/* retrieveImage - loads the image located at "filePath" into memory and returns it
	 *               * throws InvalidImgException if the file cannot be read
	 */
	private BufferedImage retrieveImage() throws InvalidImgException
	{    
		try
		{
			return ImageIO.read(new File(this.filePath));
		}
		catch (IOException e)
		{
			throw new InvalidImgException("Unable to read image into memory", e);
		}
	}
	
	/* retrieveFileName - returns the file name from "filePath"
	 */
	private String retrieveFileName()
	{
		return Paths.get(this.filePath).getFileName().toString();
	}
	
	/* retrieveFileExt - returns the file extension from "filePath"
	 * 			       * throws InvalidImgException if the file does not have an extension
	 */
	private String retrieveFileExt() throws InvalidImgException
	{
		if (this.filePath.contains("."))
		{
			return this.filePath.substring(this.filePath.indexOf('.'));
		}
		else
		{
			throw new InvalidImgException("Unable to determine file extension");
		}
	}
	
	/* retrieveFileType - returns the file type from "filePath"
	 * 			        * throws InvalidImgException if the file type is not that of a valid image (jpg or png)
	 */
	private String retrieveFileType() throws InvalidImgException
	{
		if (this.fileExt.equalsIgnoreCase(".png"))
		{
			return "png";
		}
		else if (this.fileExt.equalsIgnoreCase(".jpg") || this.fileExt.equalsIgnoreCase(".jpeg"))
		{
			return "jpg";
		}
		else 
		{
			throw new InvalidImgException("Invalid image file type");
		}
	}
	
}