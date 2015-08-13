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
	private Box returnContainer;
	private Img openImg;
	private Img printImg;
	private Img returnImg;
	private JLabel openLabel;
	private JLabel printLabel;
	private JLabel returnLabel;
	private String caseNum;

	public FinishPanel(FrameManager manager, String caseNum) 
	{
		this.manager = manager;
		this.caseNum = caseNum;
		this.container = Box.createVerticalBox();
		this.optionsContainer = Box.createHorizontalBox();
		this.openContainer = Box.createVerticalBox();
		this.printContainer = Box.createVerticalBox();
		this.returnContainer = Box.createVerticalBox();
		this.populateOpenContainer();
		this.populatePrintContainer();
		this.populateReturnContainer();
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
			this.openCase();
		}
		else if (e.getSource() == this.printImg)
		{
			this.printImgs();
		}
		else if (e.getSource() == this.returnImg)
		{
			this.returnHome();
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
	
	/* openCase - attempts to open the case directory in Finder if the user is using OSX, or Explorer if he or she is using Windows
	 */
	private void openCase()
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
	
	/* printImgs - opens an instance of SelectImagesDialogue
	 */
	private void printImgs()
	{
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.manager.openDialogue("Select Images to Print", new SelectImagesDialogue(this.manager, this.caseNum), 40, 55);
	}
	
	/* returnHome - pushes StartPanel into the JFrame
	 */
	private void returnHome()
	{
		this.manager.getMainWindow().setResizable(true);
		this.manager.getMainWindow().setBounds(50, 50);
		this.manager.getMainWindow().setResizable(false);
		this.manager.closeDialogue();
		this.manager.getMainWindow().pushPanel(new StartPanel(this.manager), "PEMS (Police Evidence Management System) Version 0.1");
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
	
	/* populateReturnContainer - adds "returnImg" and "returnLabel" to "returnContainer"
	 */
	private void populateReturnContainer()
	{
		try 
		{
			this.returnImg = ComponentGenerator.generateImg("resources/next.png", CENTER_ALIGNMENT);
			this.returnImg.resizeImage(Scalr.Method.ULTRA_QUALITY, 150);
			this.returnImg.addMouseListener(this);
			this.returnContainer.add(this.returnImg);
		} 
		catch (InvalidImgException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.returnLabel = ComponentGenerator.generateLabel("Return to Start", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.returnContainer.add(Box.createVerticalStrut(30));
		this.returnContainer.add(this.returnLabel);
	}
	
	/* populateOptionsContainer - adds the containers for the three options to their parent container
	 */
	private void populateOptionsContainer()
	{
		this.optionsContainer.add(this.openContainer);
		this.optionsContainer.add(Box.createHorizontalStrut(150));
		this.optionsContainer.add(this.printContainer);
		this.optionsContainer.add(Box.createHorizontalStrut(150));
		this.optionsContainer.add(this.returnContainer);
	}
	
	/* populateContainer - populates the primary container
	 */
	private void populateContainer()
	{
		this.container.add(Box.createVerticalStrut(200));
		this.container.add(this.optionsContainer);
	}
	
}