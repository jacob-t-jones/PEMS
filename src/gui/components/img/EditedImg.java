// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// EditedImg.java

package gui.components.img;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import org.imgscalr.*;
import exceptions.*;
import gui.*;
 
public class EditedImg extends Img
{
	
	private Stack<BufferedImage> currentHistorySequence;
	private Stack<BufferedImage> undoneHistorySequence;
	private boolean saved;

	public EditedImg(String filePath) throws InvalidImgException 
	{
		super(filePath);
		this.currentHistorySequence = new Stack<BufferedImage>();
		this.undoneHistorySequence = new Stack<BufferedImage>();
		this.saved = true;
		this.currentHistorySequence.push(super.getImage());
	}
	
	/* getSaved - returns "saved", a boolean value indicating whether or not the image has been saved since the most recent change
	 */
	public boolean getSaved()
	{
		return this.saved;
	}
	
	/* setSaved - sets "saved" to be the boolean value passed in as a parameter
	 *    saved - the true or false value that "saved" will be set to
	 */
	public void setSaved(boolean saved)
	{
		this.saved = saved;
	}
	
	/* undo - undoes the most recent change to the image by popping a value from "currentHistorySequence" and pushing it to "undoneHistorySequence"
	 */
	public void undo()
	{
		if (this.currentHistorySequence.size() > 1)
		{
			this.undoneHistorySequence.push(this.currentHistorySequence.pop());
			this.saved = false;
			super.setImage(this.currentHistorySequence.peek());
			super.refreshIcon();
		}
	}
	
	/* redo - redoes the most recently undone action by popping a value from "undoneHistorySequence" and pushing it to "currentHistorySequence"
	 */
	public void redo()
	{
		if (this.undoneHistorySequence.size() > 0)
		{
			this.currentHistorySequence.push(this.undoneHistorySequence.pop());
			this.saved = false;
			super.setImage(this.currentHistorySequence.peek());
			super.refreshIcon();
		}
	}
	
	/* drawCropBox - draws a rectangle on the image based on the coordinates passed in as parameters
	 *           a - Point object that serves as one corner of the rectangle
	 *           b - Point object that serves as the other corner of the rectangle
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
	
	/* refreshIcon - calls the parent version of this method, clears "undoneHistorySequence", adds to "currentHistorySequence", sets "saved" to false, and repaints the image
	 */
	protected void refreshIcon()
	{
		super.refreshIcon();
		this.undoneHistorySequence.clear();
		this.currentHistorySequence.push(super.getImage());
		this.saved = false;
		this.revalidate();
		this.repaint();
	}

}