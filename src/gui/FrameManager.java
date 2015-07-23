// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// FrameManager.java

package gui;
import java.awt.*;
import javax.swing.*;
import gui.panels.*;
import tools.*;

public class FrameManager 
{
	
	private Config configuration;
	private JFrame mainFrame;
	private JFrame renameDialogue;
	private JFrame resizeDialogue;
	private JFrame quitWarningDialogue;
	private JFrame removeWarningDialogue;
	private JFrame switchWarningDialogue;
	private JFrame deleteImportsDialogue;
	
	public FrameManager()
	{
		this.configuration = new Config();
        this.mainFrame = new JFrame("PEMS (Police Evidence Management System) Version 0.1");
        this.mainFrame.getContentPane().add(new ScreenStart(this));
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.pack();
        this.mainFrame.setBounds(this.widthToPixels(25), this.heightToPixels(25), this.widthToPixels(50), this.heightToPixels(50));
        this.mainFrame.setResizable(false);
        this.mainFrame.setVisible(true);
	}
	
	/* getConfiguration - returns the global instance of the Config class
	 */
	public Config getConfiguration()
	{
		return this.configuration;
	}
	
	/* displayRenameDialogue - initializes and opens the JFrame for the rename image dialogue 
	 */
	public void displayRenameDialogue(ScreenEdit currentScreen)
	{
        this.renameDialogue = new JFrame("Rename Image");
        this.renameDialogue.getContentPane().add(new ScreenRenameDialogue(this, currentScreen));
        this.renameDialogue.pack();
        this.renameDialogue.setBounds(this.widthToPixels(30), this.heightToPixels(0), this.widthToPixels(40), this.heightToPixels(30));
        this.renameDialogue.setResizable(false);
        this.renameDialogue.setVisible(true);
	}
	
	/* closeRenameDialogue - closes and disposes of "renameDialogue"
	 */
	public void closeRenameDialogue()
	{
		this.renameDialogue.setVisible(false);
		this.renameDialogue.dispose();
	}
	
	/* displayResizeDialogue - initializes and opens the JFrame for the resize image dialogue 
	 */
	public void displayResizeDialogue(ScreenEdit currentScreen, int currentWidth, int currentHeight)
	{
        this.resizeDialogue = new JFrame("Resize Image");
        this.resizeDialogue.getContentPane().add(new ScreenResizeDialogue(this, currentScreen, currentWidth, currentHeight));
        this.resizeDialogue.pack();
        this.resizeDialogue.setBounds(this.widthToPixels(30), this.heightToPixels(0), this.widthToPixels(40), this.heightToPixels(30));
        this.resizeDialogue.setResizable(false);
        this.resizeDialogue.setVisible(true);
	}
	
	/* closeResizeDialogue - closes and disposes of "resizeDialogue"
	 */
	public void closeResizeDialogue()
	{
		this.resizeDialogue.setVisible(false);
		this.resizeDialogue.dispose();
	}
	
	/* displayQuitWarningDialogue - initializes and opens the JFrame for the quit warning dialogue 
	 */
	public void displayQuitWarningDialogue(ScreenEdit currentScreen)
	{
        this.quitWarningDialogue = new JFrame("Quit Program");
        //this.resizeDialogue.getContentPane().add();
        this.quitWarningDialogue.pack();
        this.quitWarningDialogue.setBounds(this.widthToPixels(30), this.heightToPixels(0), this.widthToPixels(40), this.heightToPixels(30));
        this.quitWarningDialogue.setResizable(false);
        this.quitWarningDialogue.setVisible(true);
	}
	
	/* closeQuitWarningDialogue - closes and disposes of "quitWarningDialogue"
	 */
	public void closeQuitWarningDialogue()
	{
		this.quitWarningDialogue.setVisible(false);
		this.quitWarningDialogue.dispose();
	}
	
	/* displayRemoveWarningDialogue - initializes and opens the JFrame for the remove warning dialogue 
	 */
	public void displayRemoveWarningDialogue(ScreenEdit currentScreen)
	{
        this.removeWarningDialogue = new JFrame("Remove Image");
        //this.resizeDialogue.getContentPane().add();
        this.removeWarningDialogue.pack();
        this.removeWarningDialogue.setBounds(this.widthToPixels(30), this.heightToPixels(0), this.widthToPixels(40), this.heightToPixels(30));
        this.removeWarningDialogue.setResizable(false);
        this.removeWarningDialogue.setVisible(true);
	}
	
	/* closeRemoveWarningDialogue - closes and disposes of "removeWarningDialogue"
	 */
	public void closeRemoveWarningDialogue()
	{
		this.removeWarningDialogue.setVisible(false);
		this.removeWarningDialogue.dispose();
	}
	
	/* displaySwitchWarningDialogue - initializes and opens the JFrame for the switch warning dialogue 
	 */
	public void displaySwitchWarningDialogue(ScreenEdit currentScreen)
	{
        this.switchWarningDialogue = new JFrame("Edit New Image");
        //this.resizeDialogue.getContentPane().add();
        this.switchWarningDialogue.pack();
        this.switchWarningDialogue.setBounds(this.widthToPixels(30), this.heightToPixels(0), this.widthToPixels(40), this.heightToPixels(30));
        this.switchWarningDialogue.setResizable(false);
        this.switchWarningDialogue.setVisible(true);
	}
	
	/* closeSwitchWarningDialogue - closes and disposes of "switchWarningDialogue"
	 */
	public void closeSwitchWarningDialogue()
	{
		this.switchWarningDialogue.setVisible(false);
		this.switchWarningDialogue.dispose();
	}
	
	/* displayDeleteImportsDialogue - initializes and opens the JFrame for the delete imports dialogue 
	 */
	public void displayDeleteImportsDialogue(ScreenEdit currentScreen)
	{
        this.deleteImportsDialogue = new JFrame("Delete Imported Files");
        //this.resizeDialogue.getContentPane().add();
        this.deleteImportsDialogue.pack();
        this.deleteImportsDialogue.setBounds(this.widthToPixels(30), this.heightToPixels(0), this.widthToPixels(40), this.heightToPixels(30));
        this.deleteImportsDialogue.setResizable(false);
        this.deleteImportsDialogue.setVisible(true);
	}
	
	/* closeDeleteImportsDialogue - closes and disposes of "deleteImportsDialogue"
	 */
	public void closeDeleteImportsDialogue()
	{
		this.deleteImportsDialogue.setVisible(false);
		this.deleteImportsDialogue.dispose();
	}
	
	/* pushPanel - removes the current panel from "mainFrame" and replaces it with a newly constructed one
	 *	   panel - new panel to display
	 *     title - title of the new panel
	 */
	public void pushPanel(JPanel panel, String title)
	{
		this.mainFrame.getContentPane().removeAll();
		this.mainFrame.getContentPane().add(panel);
		this.mainFrame.getContentPane().revalidate();
		this.mainFrame.getContentPane().repaint();
		this.mainFrame.setTitle(title);
	}
	
	/* closePanel - removes all displayed panels from "mainFrame" and then repaints said JFrame
	 */
	public void closePanel()
	{
		this.mainFrame.getContentPane().removeAll();
		this.mainFrame.getContentPane().revalidate();
		this.mainFrame.getContentPane().repaint();
	}
	
	/* maximizeFrame - maximizes "mainFrame"
	 */
	public void maximizeFrame()
	{
		this.mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	/* setCursor - sets the cursor displayed in "mainFrame"
	 *    cursor - the cursor to be displayed
	 */
	public void setCursor(Cursor cursor)
	{
		this.mainFrame.setCursor(cursor);
	}
	
	/* setResizable - determines whether or not "mainFrame" is resizable
	 *    resizable - boolean value that determines the status of the JFrame
	 */
	public void setResizable(boolean resizable)
	{
		this.mainFrame.setResizable(resizable);
	}
	
	/* setMenuBar - adds the JMenuBar specified in the parameters to "mainFrame"
	 *    menuBar - the JMenuBar to display
	 */
	public void setMenuBar(JMenuBar menuBar)
	{
		this.mainFrame.setJMenuBar(menuBar);
	}
	
	/* removeMenuBar - removes the JMenuBar from "mainFrame"
	 */
	public void removeMenuBar()
	{
		this.mainFrame.remove(this.mainFrame.getMenuBar());
	}
	
	/* widthToPixels - converts a width percentage value to its equivalent pixel value
	 *       percent - the value to convert to pixels
	 */
	public int widthToPixels(double percent)
	{
		return (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * (percent / 100.0));
	}
	
	/* heightToPixels - converts a height percentage value to its equivalent pixel value
	 *        percent - the value to convert to pixels
	 */
	public int heightToPixels(double percent)
	{
		return (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * (percent / 100.0));
	}

}