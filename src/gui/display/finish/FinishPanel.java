// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// FinishPanel.java

package gui.display.finish;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import org.imgscalr.*;
import backend.exceptions.*;
import gui.*;
import gui.components.icon.*;
import gui.display.*;
import gui.display.dialogues.*;
import gui.display.start.*;

/** Subclass of <code>JPanel</code> displayed when the user finishes editing a given case.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class FinishPanel extends JPanel implements MouseListener
{

	private FrameManager manager;
	private Box mainContainer;
	private Box optionsContainer;
	private Box openContainer;
	private Box printContainer;
	private Box returnContainer;
	private ImgIcon openIcon;
	private ImgIcon printIcon;
	private ImgIcon returnIcon;
	private JLabel openLabel;
	private JLabel printLabel;
	private JLabel returnLabel;
	private String caseNum;

	/** Populates this panel with all of the necessary graphical components.
	 * 
	 *  @param manager the instance of <code>FrameManager</code> that initialized this panel
	 *  @param caseNum the number of the case that was being edited
	 *  @throws NullPointerException if any parameters are null
	 */
	public FinishPanel(FrameManager manager, String caseNum) 
	{
		if (manager == null || caseNum == null)
		{
			throw new NullPointerException();
		}
		this.manager = manager;
		this.caseNum = caseNum;
		this.mainContainer = Box.createVerticalBox();
		this.optionsContainer = Box.createHorizontalBox();
		this.openContainer = Box.createVerticalBox();
		this.printContainer = Box.createVerticalBox();
		this.returnContainer = Box.createVerticalBox();
		this.populateOpenContainer();
		this.populatePrintContainer();
		this.populateReturnContainer();
		this.populateOptionsContainer();
		this.populateMainContainer();
		this.add(this.mainContainer);
		this.revalidate();
		this.repaint();
	}
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li><code>openIcon</code></li>
	 *  		<ul>
	 *  			<li>Calls the <code>openCase</code> method.</li>
	 *  		</ul>
	 *  	<li><code>printIcon</code></li>
	 *  		<ul>
	 *  			<li>Calls the <code>printImgs</code> method.</li>
	 *  		</ul>
	 *  	<li><code>returnIcon</code></li>
	 *  		<ul>
	 *  			<li>Calls the <code>returnHome</code> method.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if (e.getSource() == this.openIcon)
		{
			this.openCase();
		}
		else if (e.getSource() == this.printIcon)
		{
			this.printImgs();
		}
		else if (e.getSource() == this.returnIcon)
		{
			this.returnHome();
		}
	}

	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mousePressed(MouseEvent e) 
	{
		return;
	}
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseReleased(MouseEvent e) 
	{
		return;
	}
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseEntered(MouseEvent e) 
	{
		return;
	}

	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseExited(MouseEvent e) 
	{
		return;
	}
	
	/** Attempts to open the case directory in Finder if the user is using OSX, or Explorer if he or she is using Windows.
	 */
	private void openCase()
	{
		try 
		{
			Desktop.getDesktop().open(new File("cases/live/" + this.caseNum + "/"));
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/** Opens an instance of <code>SelectImagesDialogue</code>.
	 */
	private void printImgs()
	{
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.manager.openDialogue("Select Images to Print", new SelectImagesPrintDialogue(this.manager, this.caseNum), 40, 55);
	}
	
	/** Pushes <code>StartPanel</code> into the <code>JFrame</code>.
	 */
	private void returnHome()
	{
		this.manager.closeDialogue();
		this.manager.getMainWindow().pushPanel(new StartPanel(this.manager), "PEMS (Police Evidence Management System) Version 0.1");
	}
	
	/** Adds <code>openIcon</code> and <code>openLabel</code> to <code>openContainer</code>.
	 */
	private void populateOpenContainer()
	{
		try 
		{
			this.openIcon = new ImgIcon("resources/harddrive.png", Scalr.Method.ULTRA_QUALITY, 150);
			this.openIcon.addMouseListener(this);
			this.openIcon.setAlignmentX(CENTER_ALIGNMENT);
			this.openContainer.add(this.openIcon);
		} 
		catch (InvalidFileException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.openLabel = ComponentGenerator.generateLabel("Show Images in File System", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.openContainer.add(Box.createVerticalStrut(30));
		this.openContainer.add(this.openLabel);
	}
	
	/** Adds <code>printIcon</code> and <code>printLabel</code> to <code>printContainer</code>.
	 */
	private void populatePrintContainer()
	{
		try 
		{
			this.printIcon = new ImgIcon("resources/printer.png", Scalr.Method.ULTRA_QUALITY, 150);
			this.printIcon.addMouseListener(this);
			this.printIcon.setAlignmentX(CENTER_ALIGNMENT);
			this.printContainer.add(this.printIcon);
		} 
		catch (InvalidFileException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.printLabel = ComponentGenerator.generateLabel("Print Images from Case", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.printContainer.add(Box.createVerticalStrut(30));
		this.printContainer.add(this.printLabel);
	}
	
	/** Adds <code>returnIcon</code> and <code>returnLabel</code> to <code>returnContainer</code>.
	 */
	private void populateReturnContainer()
	{
		try 
		{
			this.returnIcon = new ImgIcon("resources/next.png", Scalr.Method.ULTRA_QUALITY, 150);
			this.returnIcon.addMouseListener(this);
			this.returnIcon.setAlignmentX(CENTER_ALIGNMENT);
			this.returnContainer.add(this.returnIcon);
		} 
		catch (InvalidFileException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.returnLabel = ComponentGenerator.generateLabel("Return to Start", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.returnContainer.add(Box.createVerticalStrut(30));
		this.returnContainer.add(this.returnLabel);
	}
	
	/** Adds <code>openContainer</code>, <code>printContainer</code>, and <code>returnContainer</code> to <code>optionsContainer</code>.
	 */
	private void populateOptionsContainer()
	{
		this.optionsContainer.add(this.openContainer);
		this.optionsContainer.add(Box.createHorizontalStrut(150));
		this.optionsContainer.add(this.printContainer);
		this.optionsContainer.add(Box.createHorizontalStrut(150));
		this.optionsContainer.add(this.returnContainer);
	}
	
	/** Adds <code>optionsContainer</code> to <code>mainContainer</code>.
	 */
	private void populateMainContainer()
	{
		this.mainContainer.add(Box.createVerticalStrut(200));
		this.mainContainer.add(this.optionsContainer);
	}
	
}