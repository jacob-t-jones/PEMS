// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ImageEditor.java

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;
public class ImageEditor 
{
	
	public ImageEditor()
	{
		
	}
	
	/* resizeImage - returns a resized version of a given BufferedImage
	 * 		 image - the image to resize
	 * 	     width - the desired width
	 *      height - the desired height
	 *
	public BufferedImage resizeImage(BufferedImage image, int width, int height)
	{
		Image tempImage = image.getScaledInstance(width, height, Image.SCALE_FAST);
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); 
		Graphics2D g = newImage.createGraphics();
		g.drawImage(tempImage, 0, 0, null);
		g.dispose();
		return newImage;
	}*/
	
	public BufferedImage resizeFullImage(BufferedImage image, int width, int height)
	{
		return Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, width, height, Scalr.OP_ANTIALIAS);
	}
	
	public BufferedImage resizeThumbnailImage(BufferedImage image, int size)
	{
		return Scalr.resize(image, Scalr.Method.SPEED, size, Scalr.OP_ANTIALIAS);
	}
	
	public BufferedImage brightenImage(BufferedImage image)
	{
		return Scalr.apply(image, Scalr.OP_BRIGHTER);
	}
	
	public BufferedImage darkenImage(BufferedImage image)
	{
		return Scalr.apply(image, Scalr.OP_DARKER);
	}
	
	public BufferedImage cropImage(BufferedImage image, int x, int y, int width, int height)
	{
		return Scalr.crop(image, x, y, width, height, Scalr.OP_ANTIALIAS);
	}
	
	public BufferedImage rotateRight90(BufferedImage image)
	{
		return Scalr.rotate(image, Scalr.Rotation.CW_90);
	}
	
	public BufferedImage rotateRight180(BufferedImage image)
	{
		return Scalr.rotate(image, Scalr.Rotation.CW_180);
	}
	
	public BufferedImage rotateRight270(BufferedImage image)
	{
		return Scalr.rotate(image, Scalr.Rotation.CW_270);
	}

}


