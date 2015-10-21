// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// EditImgMenuBar.java

package gui.display.editimg;
import java.awt.event.*;
import javax.swing.*;
import gui.*;

/** Subclass of <code>JMenuBar</code> displayed and utilized within <code>EditImgPanel</code>.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class EditImgMenuBar extends JMenuBar implements ActionListener
{
	
	private EditImgPanel currentPanel;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu caseMenu;
	private JMenu imageMenu;
	private JMenuItem saveImageMenuItem;
	private JMenuItem quitMenuItem;
	private JMenuItem undoMenuItem;
	private JMenuItem redoMenuItem;
	private JMenuItem removeImageMenuItem;
	private JMenuItem antiAliasMenuItem;
	private JMenuItem brightenMenuItem;
	private JMenuItem darkenMenuItem;
	private JMenuItem grayscaleMenuItem;
	private JMenuItem resizeMenuItem;
	private JMenuItem cropMenuItem;
	private JMenuItem rotate90MenuItem;
	private JMenuItem rotate180MenuItem;
	private JMenuItem rotate270MenuItem;

	/** Calls the parent constructor and populates this menu bar.
	 * 
	 *  @param currentPanel the instance of <code>EditImgPanel</code> associated with this class
	 *  @throws NullPointerException if the parameter is null
	 */
	public EditImgMenuBar(EditImgPanel currentPanel)
	{
		super();
		if (currentPanel == null)
		{
			throw new NullPointerException();
		}
		this.currentPanel = currentPanel;
		this.constructMenuItems();
		this.constructMenus();
		this.add(this.fileMenu);
		this.add(this.editMenu);
		this.add(this.caseMenu);
		this.add(this.imageMenu);
	}

	/** Mandatory method required in all classes that implement <code>ActionListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li>An instance of <code>JMenuItem</code></li>
	 *  		<ul>
	 *  			<li>Calls the matching method for the selected <code>JMenuItem</code> within <code>EditImgPanel</code>.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.saveImageMenuItem)
		{
			this.currentPanel.saveImg();
		}
		else if (e.getSource() == this.quitMenuItem)
		{
			this.currentPanel.quit();
		}
		else if (e.getSource() == this.undoMenuItem)
		{
			this.currentPanel.undo();
		}
		else if (e.getSource() == this.redoMenuItem)
		{
			this.currentPanel.redo();
		}
		else if (e.getSource() == this.removeImageMenuItem)
		{
			this.currentPanel.removeImg();
		}
	    else if (e.getSource() == this.antiAliasMenuItem)
		{
	    	this.currentPanel.antiAlias();
		}
		else if (e.getSource() == this.brightenMenuItem)
		{
			this.currentPanel.brighten();
		}
		else if (e.getSource() == this.darkenMenuItem)
		{
			this.currentPanel.darken();
		}
		else if (e.getSource() == this.grayscaleMenuItem)
		{
			this.currentPanel.grayscale();
		}
		else if (e.getSource() == this.resizeMenuItem)
		{
			this.currentPanel.resizeImg();
		}
		else if (e.getSource() == this.cropMenuItem)
		{
			this.currentPanel.crop();
		}
		else if (e.getSource() == this.rotate90MenuItem)
		{
			this.currentPanel.rotate90();
		}
		else if (e.getSource() == this.rotate180MenuItem)
		{
			this.currentPanel.rotate180();
		}
		else if (e.getSource() == this.rotate270MenuItem)
		{
			this.currentPanel.rotate270();
		}
	}
	
	/** Initializes all of the menu items.
	 */
	private void constructMenuItems()
	{
		this.saveImageMenuItem = ComponentGenerator.generateMenuItem("Save Image", this, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		this.quitMenuItem = ComponentGenerator.generateMenuItem("Quit", this, KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		this.undoMenuItem = ComponentGenerator.generateMenuItem("Undo", this, KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		this.redoMenuItem = ComponentGenerator.generateMenuItem("Redo", this, KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		this.removeImageMenuItem = ComponentGenerator.generateMenuItem("Remove Image from Case", this);
		this.antiAliasMenuItem = ComponentGenerator.generateMenuItem("Apply Anti Aliasing", this);
		this.brightenMenuItem = ComponentGenerator.generateMenuItem("Brighten by 10%", this);
		this.darkenMenuItem = ComponentGenerator.generateMenuItem("Darken by 10%", this);
		this.grayscaleMenuItem = ComponentGenerator.generateMenuItem("Convert to Grayscale", this);
		this.resizeMenuItem = ComponentGenerator.generateMenuItem("Resize Image", this);
		this.cropMenuItem = ComponentGenerator.generateMenuItem("Crop Image", this);
		this.rotate90MenuItem = ComponentGenerator.generateMenuItem("Rotate Image 90\u00b0 Right", this);
		this.rotate180MenuItem = ComponentGenerator.generateMenuItem("Rotate Image 180\u00b0 Right", this);
		this.rotate270MenuItem = ComponentGenerator.generateMenuItem("Rotate Image 270\u00b0 Right", this);
	}
	
	/** Initializes and populates all of the menus.
	 */
	private void constructMenus()
	{
		this.fileMenu = new JMenu("File");
		this.editMenu = new JMenu("Edit");
		this.caseMenu = new JMenu("Case");
		this.imageMenu = new JMenu("Image");
		this.fileMenu.add(this.saveImageMenuItem);
		this.fileMenu.addSeparator();
		this.fileMenu.add(this.quitMenuItem);
		this.editMenu.add(this.undoMenuItem);
		this.editMenu.add(this.redoMenuItem);
		this.caseMenu.add(this.removeImageMenuItem);
		this.imageMenu.add(this.antiAliasMenuItem);
		this.imageMenu.addSeparator();
		this.imageMenu.add(this.brightenMenuItem);
		this.imageMenu.add(this.darkenMenuItem);
		this.imageMenu.addSeparator();
		this.imageMenu.add(this.grayscaleMenuItem);
		this.imageMenu.addSeparator();
		this.imageMenu.add(this.resizeMenuItem);
		this.imageMenu.addSeparator();
		this.imageMenu.add(this.cropMenuItem);
		this.imageMenu.addSeparator();
		this.imageMenu.add(this.rotate90MenuItem);
		this.imageMenu.add(this.rotate180MenuItem);
		this.imageMenu.add(this.rotate270MenuItem);
	}
	
}