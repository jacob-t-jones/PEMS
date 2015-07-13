// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ImageEditor.java

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageEditor 
{
	
	public ImageEditor()
	{
		
	}
	
	/* resizeImage - returns a resized version of a given BufferedImage
	 * 		 image - the image to resize
	 * 	     width - the desired width
	 *      height - the desired height
	 */
	public BufferedImage resizeImage(BufferedImage image, int width, int height)
	{
		Image tempImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); 
		Graphics2D g = newImage.createGraphics();
		g.drawImage(tempImage, 0, 0, null);
		g.dispose();
		return newImage;
	}

}


