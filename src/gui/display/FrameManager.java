// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// FrameManager.java

package gui.display;
import javax.swing.*;
import backend.*;
import gui.display.start.*;
import gui.window.*;

/** Class used to tie all of the aspects of PEMS together. Creates and maintains the graphical user interface, and allows it to communicate with various backend classes.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class FrameManager
{
	
	private Config configuration;
	private PeripheralManager peripheralManager;
	private StorageManager storageManager;
	private MainWindow mainWindow;
	private DialogueWindow dialogueWindow;
	
	/** Populates instance fields, initializes <code>MainWindow</code> and pushes <code>StartPanel</code> into view.
	 */
	public FrameManager()
	{
		this.configuration = new Config();
		this.peripheralManager = new PeripheralManager();
		this.storageManager = new StorageManager();
		this.mainWindow = new MainWindow("PEMS (Police Evidence Management System) Version 0.1", this, new StartPanel(this));
	}
	
	/** Returns the global instance of the <code>Config</code> class.
	 * 
	 *  @return the global instance of the <code>Config</code> class
	 */
	public Config getConfiguration()
	{
		return this.configuration;
	}
	
	/** Returns the global instance of the <code>PeripheralManager</code> class.
	 * 
	 *  @return the global instance of the <code>PeripheralManager</code> class
	 */
	public PeripheralManager getPeripheralManager()
	{
		return this.peripheralManager;
	}

	/** Returns the global instance of the <code>StorageManager</code> class.
	 * 
	 *  @return the global instance of the <code>StorageManager</code> class
	 */
	public StorageManager getStorageManager()
	{
		return this.storageManager;
	}
	
	/** Returns the global instance of <code>MainWindow</code>, the primary <code>JFrame</code> used by the application.
	 * 
	 *  @return the global instance of <code>MainWindow</code>, the primary <code>JFrame</code> used by the application
	 */
	public MainWindow getMainWindow()
	{
		return this.mainWindow;
	}
	
	/** Initializes and displays a new instance of <code>DialogueWindow</code> based on the passed in parameters.
	 * 
	 *  @param title the title of the new dialogue
	 *  @param panel the <code>JPanel</code> to display in the new dialogue
	 *  @param width the width of the new dialogue, as a percentage of total screen width
	 *  @param height the height of the new dialogue, as a percentage of total screen height
	 */
	public void openDialogue(String title, JPanel panel, int width, int height)
	{
		this.dialogueWindow = new DialogueWindow(title, this, panel, width, height);
	}
	
	/** Hides and disposes of the current instance of <code>DialogueWindow</code>. 
	 */
	public void closeDialogue()
	{
		if (this.dialogueWindow != null)
		{
			this.dialogueWindow.setVisible(false);
			this.dialogueWindow.dispose();
		}
	}
	
}