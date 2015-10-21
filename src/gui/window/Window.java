// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Window.java

package gui.window;
import java.awt.*;
import javax.swing.*;
import gui.display.*;

/** The base class used when creating a GUI window. Inherits from <code>JFrame</code>.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class Window extends JFrame
{
	
	private FrameManager manager;

	/** Calls the <code>JFrame</code> parent constructor, pushes the passed in <code>JPanel</code> to this window.
	 * 
	 *  @param title the title of this window
	 *  @param manager the instance of <code>FrameManager</code> that created this window
	 *  @param panel the <code>JPanel</code> to display in this window
	 *  @throws NullPointerException if any parameters are null
	 */
	public Window(String title, FrameManager manager, JPanel panel)
	{
		super(title);
		if (title == null || manager == null || panel == null)
		{
			throw new NullPointerException();
		}
		super.getContentPane().add(panel);
		super.pack();
		this.manager = manager;
	}
	
	/** Resizes this window using the width and height percentage values specified in the parameters. Positions it at the center of the screen.
	 * 
	 *  @param width the new width of this window, as a percentage of total screen width
	 *  @param height the new height of this window, as a percentage of total screen height
	 *  @throws IllegalArgumentException if <code>width</code> or <code>height</code> is not a valid percentage
	 */
	public void setBounds(int width, int height)
	{
		if (width < 0 || width > 100 || height < 0 || height > 100)
		{
			throw new IllegalArgumentException();
		}
		super.setBounds(this.widthToPixels((100 - width) / 2), this.heightToPixels((100 - height) / 2), this.widthToPixels(width), this.heightToPixels(height));
	}
	
	/** Maximizes this window.
	 */
	public void setMaximized()
	{
		super.setResizable(true);
		super.setExtendedState(MAXIMIZED_BOTH);
	}
	
	/** Returns the instance of <code>FrameManager</code> associated with this window.
	 * 
	 *  @return the instance of <code>FrameManager</code> associated with this window
	 */
	protected FrameManager getManager()
	{
		return this.manager;
	}
	
	/** Converts a width percentage value to its equivalent pixel value.
	 * 
	 *  @param percent the percentage value to convert
	 *  @return <code>int</code> calculated by converting the passed in percentage of screen width into a corresponding pixel value
	 */
	private int widthToPixels(double percent)
	{
		return (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * (percent / 100.0));
	}
	
	/** Converts a height percentage value to its equivalent pixel value.
	 * 
	 *  @param percent the percentage value to convert
	 *  @return <code>int</code> calculated by converting the passed in percentage of screen height into a corresponding pixel value
	 */
	private int heightToPixels(double percent)
	{
		return (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * (percent / 100.0));
	}
	
}