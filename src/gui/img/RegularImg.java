// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// RegularImg.java

package gui.img;
import java.awt.*;
import org.imgscalr.*;
import exceptions.*;
 
public class RegularImg extends BaseImg
{

	public RegularImg(String filePath) throws InvalidImgException 
	{
		super(filePath);
	}
	
	/* paintComponent - override function that paints "image" into the label using a Graphics instance
	 * 			    g - the current Graphics instance
	 */
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(super.getImage(), 0, 0, null);
	}
	
	/* resizeImage - resizes "image" to the specified size using the ULTRA_QUALITY setting
	 *        size - the new size of the image
	 */
	public void resizeImage(int size)
	{
		super.resizeImage(Scalr.Method.ULTRA_QUALITY, size);
	}
	
	/* resizeImage - resizes "image" to the specified width and height using the ULTRA_QUALITY setting
	 *       width - the new width of the image
	 *      height - the new height of the image
	 */
	public void resizeImage(int width, int height)
	{
		super.resizeImage(Scalr.Method.ULTRA_QUALITY, width, height);
	}

}