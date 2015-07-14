// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ImageEditor.java

package tools;
import java.awt.*;
import java.awt.image.*;
import org.imgscalr.Scalr;

public class ImageEditor 
{
	
	public ImageEditor()
	{
		
	}
	
	/* addPadding - adds padding around a given image on all four sides
	 *      image - the image in question
	 *       size - the width of the padding in pixels
	 *        red - red RGB value for the color of the padding
	 *      green - green RGB value for the color of the padding
	 *       blue - blue RGB value for the color of the padding
	 */
	public BufferedImage addPadding(BufferedImage image, int size, int red, int green, int blue)
	{
		return Scalr.pad(image, size, new Color(red, green, blue));
	}
	
	/* applyAntiAliasing - applies anti aliasing procedure to the given image
	 *             image - the image in question
	 */
	public BufferedImage applyAntiAliasing(BufferedImage image)
	{
		return Scalr.apply(image, Scalr.OP_ANTIALIAS);
	}
	
	/* brightenImage - brightens the given image by 10%
	 *         image - the image in question
	 */
	public BufferedImage brightenImage(BufferedImage image)
	{
		return Scalr.apply(image, Scalr.OP_BRIGHTER);
	}
	
	/* cropImage - crops the given image to the specifications of the parameters
	 * 	   image - the image in question
	 *         x - the x coordinate to begin the crop at
	 *         y - the y coordinate to begin the crop at
	 *     width - the width of the crop
	 *    height - the height of the crop
	 */
	public BufferedImage cropImage(BufferedImage image, int x, int y, int width, int height)
	{
		return Scalr.crop(image, x, y, width, height, Scalr.OP_ANTIALIAS);
	}
	
	/* darkenImage - darkens the given image by 10%
	 *         image - the image in question
	 */
	public BufferedImage darkenImage(BufferedImage image)
	{
		return Scalr.apply(image, Scalr.OP_DARKER);
	}
	
	public BufferedImage resizeFullImage(BufferedImage image, int width, int height)
	{
		return Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, width, height, Scalr.OP_ANTIALIAS);
	}
	
	public BufferedImage resizeThumbnailImage(BufferedImage image, int size)
	{
		return Scalr.resize(image, Scalr.Method.SPEED, size, Scalr.OP_ANTIALIAS);
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
	
	public BufferedImage toGrayScale(BufferedImage image)
	{
		return Scalr.apply(image, Scalr.OP_GRAYSCALE);
	}

}


