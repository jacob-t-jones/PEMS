// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// MainWindow.java

package gui.window;
import java.awt.event.*;
import javax.swing.*;
import gui.display.*;
import gui.display.start.*;

/** Subclass of <code>Window</code> used to display the main program interface.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class MainWindow extends Window implements WindowListener
{

	/** Calls the parent constructor, sets the bounds and other options for this window.
	 * 
	 *  @param title the title of this window
	 *  @param manager the instance of <code>FrameManager</code> that created this window
	 *  @param panel the <code>JPanel</code> to display in this window
	 */
	public MainWindow(String title, FrameManager manager, JPanel panel)
	{
		super(title, manager, panel);
        super.addWindowListener(this);
        super.setBounds(50, 50);
		super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        super.setResizable(false);
        super.setVisible(true);
	}
	
	/** Mandatory method required in all classes that implement <code>WindowListener</code>.
	 */
	public void windowOpened(WindowEvent e) 
	{
		return;
	}
	
	/** Mandatory method required in all classes that implement <code>WindowListener</code>.
	 *  <p>
	 *  <b>When this window is about to close:</b>
	 *  <ul>
	 *  	<li>If calling <code>getPersistence</code> within the global instance of <code>Config</code> returns <code>true</code>, this window is minimized and <code>StartPanel</code> is pushed. Otherwise, the program exits as per usual.</li>
	 *  <ul>
	 */
	public void windowClosing(WindowEvent e) 
	{
		if (super.getManager().getConfiguration().getPersistence())
		{
			super.setExtendedState(ICONIFIED);
			super.setResizable(true);
			super.setBounds(50, 50);
			super.setResizable(false);
			this.getManager().closeDialogue();
			this.pushPanel(new StartPanel(super.getManager()), "PEMS (Police Evidence Management System) Version 0.1");
		}
		else
		{
			System.exit(0);
		}
	}

	/** Mandatory method required in all classes that implement <code>WindowListener</code>.
	 */
	public void windowClosed(WindowEvent e) 
	{
		return;
	}
	
	/** Mandatory method required in all classes that implement <code>WindowListener</code>.
	 */
	public void windowIconified(WindowEvent e) 
	{
		return;
	}

	/** Mandatory method required in all classes that implement <code>WindowListener</code>.
	 */
	public void windowDeiconified(WindowEvent e) 
	{
		return;
	}

	/** Mandatory method required in all classes that implement <code>WindowListener</code>.
	 */
	public void windowActivated(WindowEvent e) 
	{
		return;
	}

	/** Mandatory method required in all classes that implement <code>WindowListener</code>.
	 */
	public void windowDeactivated(WindowEvent e) 
	{
		return;
	}
	
	/** Removes the currently displayed <code>JPanel</code> from this window and replaces it with a newly constructed one.
	 * 
	 *  @param panel the new <code>JPanel</code> to display
	 *  @param title the new title of this window
	 *  @throws NullPointerException if any parameters are null
	 */
	public void pushPanel(JPanel panel, String title)
	{
		if (panel == null || title == null)
		{
			throw new NullPointerException();
		}
		super.getContentPane().removeAll();
		super.getContentPane().add(panel);
		super.getContentPane().revalidate();
		super.getContentPane().repaint();
		super.setTitle(title);
	}
	
	/** Removes the menu bar from this window.
	 */
	public void removeMenuBar()
	{
		super.setJMenuBar(null);
		super.revalidate();
		super.repaint();
	}
	
	/** Removes all current <code>WindowListener</code> instances from this window and replaces them with the one passed in as a parameter.
	 * 
	 *  @param window the new <code>WindowListener</code>
	 */
	public void setWindowListener(WindowListener window)
	{
		for (int i = 0; i < super.getWindowListeners().length; i++)
		{
			super.removeWindowListener(super.getWindowListeners()[i]);
		}
		if (window != null)
		{
			super.addWindowListener(window);
		}
	}
	
}