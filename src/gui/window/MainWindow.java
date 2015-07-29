// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// MainWindow.java

package gui.window;
import java.awt.event.*;
import javax.swing.*;
import gui.display.*;

public class MainWindow extends Window implements WindowListener
{

	public MainWindow(String title, FrameManager manager, JPanel panel)
	{
		super(title, manager, panel);
        super.addWindowListener(this);
        super.setBounds(50, 50);
		super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        super.setResizable(false);
        super.setVisible(true);
	}
	
	/* windowOpened - mandatory for any class implementing WindowListener, checks the source of the WindowEvent and executes the appropriate code 
	 *	          e - the event in question
	 */
	public void windowOpened(WindowEvent e) 
	{
		return;
	}

	/* windowClosing - mandatory for any class implementing WindowListener, checks the source of the WindowEvent and executes the appropriate code 
	 *	           e - the event in question
	 *				 1. "persistence" is set to true in config => application remains open, JFrame is minimized
	 *				 2. "persistence" is set to false in config => application quits as per usual
	 */
	public void windowClosing(WindowEvent e) 
	{
		if (super.getManager().getConfiguration().getPersistence())
		{
			super.setExtendedState(ICONIFIED);
		}
		else
		{
			System.exit(0);
		}
	}

	/* windowClosed - mandatory for any class implementing WindowListener, checks the source of the WindowEvent and executes the appropriate code 
	 *	          e - the event in question
	 */
	public void windowClosed(WindowEvent e) 
	{
		return;
	}
	
	/* windowIconified - mandatory for any class implementing WindowListener, checks the source of the WindowEvent and executes the appropriate code 
	 *	             e - the event in question
	 */
	public void windowIconified(WindowEvent e) 
	{
		return;
	}

	/* windowDeiconified - mandatory for any class implementing WindowListener, checks the source of the WindowEvent and executes the appropriate code 
	 *	               e - the event in question
	 */
	public void windowDeiconified(WindowEvent e) 
	{
		return;
	}

	/* windowActivated - mandatory for any class implementing WindowListener, checks the source of the WindowEvent and executes the appropriate code 
	 *	             e - the event in question
	 */
	public void windowActivated(WindowEvent e) 
	{
		return;
	}

	/* windowDeactivated - mandatory for any class implementing WindowListener, checks the source of the WindowEvent and executes the appropriate code 
	 *	               e - the event in question
	 */
	public void windowDeactivated(WindowEvent e) 
	{
		return;
	}
	
	/* pushPanel - removes the currently displayed panel from the JFrame and replaces it with a newly constructed one
	 *	   panel - the new panel to display
	 *     title - the title of the new panel
	 */
	public void pushPanel(JPanel panel, String title)
	{
		super.getContentPane().removeAll();
		super.getContentPane().add(panel);
		super.getContentPane().revalidate();
		super.getContentPane().repaint();
		super.setTitle(title);
	}
	
	/* setWindowListener - removes all current WindowListeners from the JFrame and replaces them with the one passed as a parameter
	 *            window - the WindowListener to add
	 */
	public void setWindowListener(WindowListener window)
	{
		for (int i = 0; i < super.getWindowListeners().length; i++)
		{
			super.removeWindowListener(super.getWindowListeners()[i]);
		}
		super.addWindowListener(window);
	}
	
}