// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenStart.java

package gui.panels;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import gui.*;
import tools.*;

public class ScreenStart extends JPanel implements ActionListener
{

	private FrameManager manager;
	private BufferedImage logoImage;
	private BufferedImage bgImage;
	private Box topContainer;
	private Box bottomContainer;
	private JLabel logoLabel;
	private JLabel titleLabel;
	private JLabel nameLabel;
	private JButton newCaseButton;
	private JButton editCaseButton;
	private JButton settingsButton;
	
	public ScreenStart(FrameManager manager)
	{
		this.manager = manager;
		this.importImages();
		this.populateTopContainer();
		this.populateBottomContainer();
	}
	
	/* paintComponent - override function used, in this case, to set a background image
	 * 			    g - the current Graphics instance
	 */
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(this.bgImage, 0, 0, null);
	}
	
	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *	               1. pushes ScreenNewCase to the JFrame
	 *                 2. pushes ScreenAddToExisting to the JFrame
	 *                 3. pushes ScreenSettings to the JFrame
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.newCaseButton)
		{
			this.manager.pushPanel(new ScreenNewCase(this.manager), "PEMS - Create New Case");
		}
		else if (e.getSource() == this.editCaseButton)
		{
        	try 
        	{
				this.manager.pushPanel(new ScreenAddToExisting(this.manager), "PEMS - Edit Existing Case");
			} 
        	catch (IOException e1) 
        	{
				e1.printStackTrace();
			}
		}
		else if (e.getSource() == this.settingsButton)
		{
			// TODO: On button press actions
		}
	}
	
	/* importImages - reads all necessary images into memory
	 */
	private void importImages()
	{
		this.logoImage = null;
		this.bgImage = null;
	    try 
	    {      
	    	this.logoImage = ImageIO.read(new File("resources/logo.png"));
	    	this.bgImage = ImageIO.read(new File("resources/background.png"));
	    } 
	    catch (IOException e)
	    {
			System.out.println("Error - Unable to import images");
			e.printStackTrace();
			return;
	    }
	}
	
	/* populateTopContainer - adds "logoLabel", "titleLabel", and "nameLabel" to "topContainer" before displaying it
	 */
	private void populateTopContainer()
	{
		this.logoLabel = ComponentGenerator.generateLabel(ImageEditor.resizeImage(this.logoImage, 200, 200), CENTER_ALIGNMENT);
		this.titleLabel = ComponentGenerator.generateLabel("Police Evidence Management System", ComponentGenerator.TITLE_FONT, ComponentGenerator.TITLE_COLOR, CENTER_ALIGNMENT);
		this.nameLabel = ComponentGenerator.generateLabel(this.manager.getConfiguration().getDepartmentName(), ComponentGenerator.SUBTITLE_FONT, ComponentGenerator.SUBTITLE_COLOR, CENTER_ALIGNMENT);
		this.topContainer = Box.createVerticalBox();
		this.topContainer.add(this.logoLabel);
		this.topContainer.add(Box.createVerticalStrut(20));
		this.topContainer.add(this.titleLabel);
		this.topContainer.add(Box.createVerticalStrut(10));
		this.topContainer.add(this.nameLabel);
		this.topContainer.add(Box.createVerticalStrut(40));
		this.add(this.topContainer);
	}
	
	/* populateBottomContainer - adds "newCaseButton", "editCaseButton", and "settingsButton" to "bottomContainer" before displaying it
	 */
	private void populateBottomContainer()
	{
		this.newCaseButton = ComponentGenerator.generateButton("New Case", this);
		this.editCaseButton = ComponentGenerator.generateButton("Edit Case", this);
		this.settingsButton = ComponentGenerator.generateButton("Settings", this);
		this.bottomContainer = Box.createHorizontalBox();
		this.bottomContainer.add(this.newCaseButton);
		this.bottomContainer.add(Box.createHorizontalStrut(120));		
		this.bottomContainer.add(this.editCaseButton);
		this.bottomContainer.add(Box.createHorizontalStrut(120));
		this.bottomContainer.add(this.settingsButton);
		this.add(this.bottomContainer);
	}
	
}