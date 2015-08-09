// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// FinishPanel.java

package gui.display.finish;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import org.imgscalr.*;
import exceptions.*;
import gui.*;
import gui.components.img.*;
import gui.display.*;
import gui.display.dialogues.*;
import gui.display.start.*;

public class FinishPanel extends JPanel implements MouseListener
{

	private FrameManager manager;
	private Box container;
	private Box optionsContainer;
	private Box openContainer;
	private Box printContainer;
	private Box closeContainer;
	private Img openImg;
	private Img printImg;
	private Img closeImg;
	private JLabel openLabel;
	private JLabel printLabel;
	private JLabel closeLabel;
	private String caseNum;

	public FinishPanel(FrameManager manager, String caseNum) 
	{
		this.manager = manager;
		this.caseNum = caseNum;
		this.container = Box.createVerticalBox();
		this.optionsContainer = Box.createHorizontalBox();
		this.openContainer = Box.createVerticalBox();
		this.printContainer = Box.createVerticalBox();
		this.closeContainer = Box.createVerticalBox();
		this.populateOpenContainer();
		this.populatePrintContainer();
		this.populateCloseContainer();
		this.populateOptionsContainer();
		this.populateContainer();
		this.add(this.container);
		this.revalidate();
		this.repaint();
	}
	
	/* mouseClicked - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	          e - the event in question
	 *				1-3. determines which option was selected and calls the appropriate method
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if (e.getSource() == this.openImg)
		{
			this.open();
		}
		else if (e.getSource() == this.printImg)
		{
			this.print();
		}
		else if (e.getSource() == this.closeImg)
		{
			this.close();
		}
	}

	/* mousePressed - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	          e - the event in question
	 */
	public void mousePressed(MouseEvent e) 
	{
		return;
	}
	
	/* mouseReleased - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	           e - the event in question
	 */
	public void mouseReleased(MouseEvent e) 
	{
		return;
	}
	
	/* mouseEntered - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	          e - the event in question
	 */
	public void mouseEntered(MouseEvent e) 
	{
		return;
	}

	/* mouseExited - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	         e - the event in question
	 */
	public void mouseExited(MouseEvent e) 
	{
		return;
	}
	
	/* open - attempts to open the case directory in Finder if the user is using OSX, or Explorer if he or she is using Windows
	 */
	private void open()
	{
		try 
		{
			Desktop.getDesktop().open(new File("cases/" + this.caseNum + "/"));
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/* print - opens an instance of SelectImagesDialogue
	 */
	private void print()
	{
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.manager.openDialogue("Select Images to Print", new SelectImagesDialogue(this.manager, this.caseNum), 40, 55);
	}
	
	/* close - executes the appropriate quit procedure depending upon the "persistence" configuration value
	 */
	private void close()
	{
		if (this.manager.getConfiguration().getPersistence())
		{
			this.manager.getMainWindow().setExtendedState(JFrame.ICONIFIED);
			this.manager.getMainWindow().setResizable(true);
			this.manager.getMainWindow().setBounds(50, 50);
			this.manager.getMainWindow().setResizable(false);
			this.manager.closeDialogue();
			this.manager.getMainWindow().pushPanel(new StartPanel(this.manager), "PEMS (Police Evidence Management System) Version 0.1");
		}
		else
		{
			System.exit(0);
		}
	}
	
	/* populateOpenContainer - adds "openImg" and "openLabel" to "openContainer"
	 */
	private void populateOpenContainer()
	{
		try 
		{
			this.openImg = ComponentGenerator.generateImg("resources/harddrive.png", CENTER_ALIGNMENT);
			this.openImg.resizeImage(Scalr.Method.ULTRA_QUALITY, 150);
			this.openImg.addMouseListener(this);
			this.openContainer.add(this.openImg);
		} 
		catch (InvalidImgException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.openLabel = ComponentGenerator.generateLabel("Show Images in File System", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.openContainer.add(Box.createVerticalStrut(30));
		this.openContainer.add(this.openLabel);
	}
	
	/* populatePrintContainer - adds "printImg" and "printLabel" to "printContainer"
	 */
	private void populatePrintContainer()
	{
		try 
		{
			this.printImg = ComponentGenerator.generateImg("resources/printer.png", CENTER_ALIGNMENT);
			this.printImg.resizeImage(Scalr.Method.ULTRA_QUALITY, 150);
			this.printImg.addMouseListener(this);
			this.printContainer.add(this.printImg);
		} 
		catch (InvalidImgException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.printLabel = ComponentGenerator.generateLabel("Print Images from Case", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.printContainer.add(Box.createVerticalStrut(30));
		this.printContainer.add(this.printLabel);
	}
	
	/* populateCloseContainer - adds "closeImg" and "closeLabel" to "closeContainer"
	 */
	private void populateCloseContainer()
	{
		try 
		{
			this.closeImg = ComponentGenerator.generateImg("resources/quit.png", CENTER_ALIGNMENT);
			this.closeImg.resizeImage(Scalr.Method.ULTRA_QUALITY, 150);
			this.closeImg.addMouseListener(this);
			this.closeContainer.add(this.closeImg);
		} 
		catch (InvalidImgException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.closeLabel = ComponentGenerator.generateLabel("Close PEMS", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.closeContainer.add(Box.createVerticalStrut(30));
		this.closeContainer.add(this.closeLabel);
	}
	
	/* populateOptionsContainer - adds the containers for the three options to their parent container
	 */
	private void populateOptionsContainer()
	{
		this.optionsContainer.add(this.openContainer);
		this.optionsContainer.add(Box.createHorizontalStrut(150));
		this.optionsContainer.add(this.printContainer);
		this.optionsContainer.add(Box.createHorizontalStrut(150));
		this.optionsContainer.add(this.closeContainer);
	}
	
	/* populateContainer - populates the primary container
	 */
	private void populateContainer()
	{
		this.container.add(Box.createVerticalStrut(200));
		this.container.add(this.optionsContainer);
	}
	
}