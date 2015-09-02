// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Img.java

package backend.storage.file.img;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import org.apache.sanselan.*;
import org.apache.sanselan.common.*;
import org.imgscalr.*;
import backend.exceptions.*;
import backend.storage.file.*;

/** Class used to represent the raw images contained within the evidence files managed by PEMS.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class Img 
{
	
	private LiveFile parentFile;
	private Stack<BufferedImage> currentHistorySequence;
	private Stack<BufferedImage> undoneHistorySequence;
	private BufferedImage image;
	private IImageMetadata metadata;
	private String timestamp;
	
	/** Populates instance fields and pushes the original unedited version of <code>image</code> to <code>currentHistorySequence</code>.
	 * 
	 *  @param parentFile the instance of <code>LiveFile</code> that this image is associated with
	 *  @throws InvalidFileException if there is an error reading the image into memory
	 */
	public Img(LiveFile parentFile) throws InvalidFileException
	{
		this.parentFile = parentFile;
		this.currentHistorySequence = new Stack<BufferedImage>();
		this.undoneHistorySequence = new Stack<BufferedImage>();
		this.image = this.retrieveImage();
		this.metadata = this.retrieveMetadata();
		this.timestamp = this.retrieveTimestamp();
		this.currentHistorySequence.push(this.image);
	}
	
	/** Returns the <code>BufferedImage</code> associated with this class.
	 * 
	 *  @return the <code>BufferedImage</code> associated with this class
	 */
	public BufferedImage getImage()
	{
		return this.image;
	}
	
	/** Returns a <code>String</code> value containing the timestamp for the instant that this image was originally created.
	 * 
	 *  @return <code>String</code> value containing the timestamp for the instant that this image was originally created
	 */
	public String getTimestamp()
	{
		return this.timestamp;
	}
	
	/** Undoes the most recent action taken by the user by popping a value from <code>currentHistorySequence</code> and pushing it to <code>undoneHistorySequence</code>.
	 */
	public void undo()
	{
		if (this.currentHistorySequence.size() > 1)
		{
			this.undoneHistorySequence.push(this.currentHistorySequence.pop());
			this.parentFile.setSaved(false);
			this.image = this.currentHistorySequence.peek();
		}
	}
	
	/** Redoes the most recent action undone by the user by popping a value from <code>undoneHistorySequence</code> and pushing it to <code>currentHistorySequence</code>.
	 */
	public void redo()
	{
		if (this.undoneHistorySequence.size() > 0)
		{
			this.currentHistorySequence.push(this.undoneHistorySequence.pop());
			this.parentFile.setSaved(false);
			this.image = this.currentHistorySequence.peek();
		}
	}
	
	/** Adds padding on all four sides of <code>image</code>.
	 * 
	 *  @param size the width of the padding
	 *  @param red the red RGB color value for the padding
	 *  @param green the green RGB color value for the padding
	 *  @param blue the blue RGB color value for the padding
	 */
	public void addPadding(int size, int red, int green, int blue)
	{
		this.image = Scalr.pad(this.image, size, new Color(red, green, blue));
		this.parentFile.setSaved(false);
		this.undoneHistorySequence.clear();
		this.currentHistorySequence.push(this.image);
	}

	/** Applies an anti aliasing procedure to <code>image</code>.
	 */
	public void applyAntiAliasing()
	{
		this.image = Scalr.apply(this.image, Scalr.OP_ANTIALIAS);
		this.parentFile.setSaved(false);
		this.undoneHistorySequence.clear();
		this.currentHistorySequence.push(this.image);
	}
	
	/** Brightens <code>image</code> by 10%.
	 */
	public void brightenImage()
	{
		this.image = Scalr.apply(this.image, Scalr.OP_BRIGHTER);
		this.parentFile.setSaved(false);
		this.undoneHistorySequence.clear();
		this.currentHistorySequence.push(this.image);
	}

	/** Crops <code>image</code> to the specified dimensions.
	 * 
	 *  @param x the x value to begin the crop at
	 *  @param y the y value to begin the crop at
	 *  @param width the width of the crop
	 *  @param height the height of the crop
	 */
	public void cropImage(int x, int y, int width, int height)
	{
		this.image = Scalr.crop(this.image, x, y, width, height, Scalr.OP_ANTIALIAS);
		this.parentFile.setSaved(false);
		this.undoneHistorySequence.clear();
		this.currentHistorySequence.push(this.image);
	}
	
	/** Darkens <code>image</code> by 10%.
	 */
	public void darkenImage()
	{
		this.image = Scalr.apply(this.image, Scalr.OP_DARKER);
		this.parentFile.setSaved(false);
		this.undoneHistorySequence.clear();
		this.currentHistorySequence.push(this.image);
	}
	
	/** Resizes <code>image</code> to the specified size.
	 * 
	 *  @param method the resizing method to use (BALANCED, SPEED, QUALITY, etc.)
	 *  @param size the new size of the image
	 */
	public void resizeImage(Scalr.Method method, int size)
	{
		this.image = Scalr.resize(this.image, method, size, Scalr.OP_ANTIALIAS);
		this.parentFile.setSaved(false);
		this.undoneHistorySequence.clear();
		this.currentHistorySequence.push(this.image);
	}

	/** Resizes <code>image</code> to the specified width and height.
	 * 
	 *  @param method the resizing method to use (BALANCED, SPEED, QUALITY, etc.)
	 *  @param width the new width of the image
	 *  @param height the new height of the image
	 */
	public void resizeImage(Scalr.Method method, int width, int height)
	{
		this.image = Scalr.resize(this.image, method, width, height, Scalr.OP_ANTIALIAS);
		this.parentFile.setSaved(false);
		this.undoneHistorySequence.clear();
		this.currentHistorySequence.push(this.image);
	}

	/** Rotates <code>image</code> 90 degrees to the right.
	 */
	public void rotateRight90()
	{
		this.image = Scalr.rotate(this.image, Scalr.Rotation.CW_90);
		this.parentFile.setSaved(false);
		this.undoneHistorySequence.clear();
		this.currentHistorySequence.push(this.image);
	}
	
	/** Rotates <code>image</code> 180 degrees to the right.
	 */
	public void rotateRight180()
	{
		this.image = Scalr.rotate(this.image, Scalr.Rotation.CW_180);
		this.parentFile.setSaved(false);
		this.undoneHistorySequence.clear();
		this.currentHistorySequence.push(this.image);
	}

	/** Rotates <code>image</code> 270 degrees to the right.
	 */
	public void rotateRight270()
	{
		this.image = Scalr.rotate(this.image, Scalr.Rotation.CW_270);
		this.parentFile.setSaved(false);
		this.undoneHistorySequence.clear();
		this.currentHistorySequence.push(this.image);
	}
	
	/** Converts <code>image</code> to grayscale format.
	 */
	public void toGrayscale()
	{
		this.image = Scalr.apply(this.image, Scalr.OP_GRAYSCALE);
		this.parentFile.setSaved(false);
		this.undoneHistorySequence.clear();
		this.currentHistorySequence.push(this.image);
	}
	
	/** Uses the file path from <code>parentFile</code> to read the associated <code>BufferedImage</code> into memory.
	 * 
	 *  @return <code>BufferedImage</code> contained within <code>parentFile</code>
	 *  @throws InvalidFileException if the image can not be read into memory
	 */
	private BufferedImage retrieveImage() throws InvalidFileException
	{
		File imageFile = new File(this.parentFile.getFilePath());
		try 
		{
			return Sanselan.getBufferedImage(imageFile);
		} 
		catch (ImageReadException | IOException e) 
		{
			throw new InvalidFileException("Unable to retrieve image", e);
		}
	}
	
	/** Attempts to find and read the metadata for <code>image</code>.
	 * 
	 *  @return <code>IImageMetadata</code> object associated with <code>image</code> and <code>parentFile</code>
	 *  @throws InvalidFileException if the image metadata can not be read into memory
	 */
	private IImageMetadata retrieveMetadata() throws InvalidFileException
	{
		File imageFile = new File(this.parentFile.getFilePath());
		try 
		{
			return Sanselan.getMetadata(imageFile);
		} 
		catch (ImageReadException | IOException e) 
		{
			throw new InvalidFileException("Unable to retrieve image metadata", e);
		}
	}
	
	/** Acquires the value from one of the timestamp fields contained in <code>metadata</code> if said field is not <code>null</code>. Order of precedence for these fields, from first to last, is "Date Time Original" > "Create Date" > "Modify Date".
	 * 
	 *  @return <code>String</code> containing the timestamp associated with this file, or "DATE NOT FOUND" if a timestamp cannot be found
	 */
	private String retrieveTimestamp()
	{
		if (this.metadata != null)
		{
			String exifData = this.metadata.toString();
			if (exifData.contains("Date Time Original"))
			{
				return exifData.substring(exifData.indexOf("Date Time Original") + 21, exifData.indexOf("'", exifData.indexOf("Date Time Original") + 21));
			}
			if (exifData.contains("Create Date"))
			{
				return exifData.substring(exifData.indexOf("Create Date") + 14, exifData.indexOf("'", exifData.indexOf("Create Date") + 14));
			}
			else if (exifData.contains("Modify Date"))
			{
				return exifData.substring(exifData.indexOf("Modify Date") + 14, exifData.indexOf("'", exifData.indexOf("Modify Date") + 14));
			}
		}
		return "DATE NOT FOUND";
	}
	
}