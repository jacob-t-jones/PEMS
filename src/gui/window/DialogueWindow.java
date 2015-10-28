// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// DialogueWindow.java

package gui.window;
import javax.swing.*;
import gui.display.*;

/** Subclass of <code>Window</code> used when displaying custom pop up dialogues.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class DialogueWindow extends Window
{
	
	/** Calls the parent constructor, sets the bounds and other options for this window.
	 * 
	 *  @param title the title of this window
	 *  @param manager the instance of <code>FrameManager</code> that created this window
	 *  @param panel the <code>JPanel</code> to display in this window
	 *  @param width the width of this window, as a percentage of total screen width
	 *  @param height the height of this window, as a percentage of total screen height
	 *  @throws NullPointerException if any parameters are null
	 */
	public DialogueWindow(String title, FrameManager manager, JPanel panel, int width, int height)
	{
		super(manager);	
		if (title == null || panel == null)
		{
			throw new NullPointerException();
		}
		super.setTitle(title);
		super.getContentPane().add(panel);
        super.setBounds(width, height);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        super.setVisible(true);
	}
	
}