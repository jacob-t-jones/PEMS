// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenStart.java

package gui.panels;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.*;
import javax.swing.*;

import gui.*;
import tools.*;

public class ScreenStart extends JPanel implements ActionListener
{

	private FrameManager manager;
	private BufferedImage logoImage;
	private BufferedImage bgImage;
	private Box topContainer;
	private Box middleContainer;
	private Box bottomContainer;
	private JLabel logoLabel;
	private JLabel titleLabel;
	private JLabel nameLabel;
	private JLabel creditLabel;
	private JButton newCaseButton;
	private JButton editCaseButton;
	private JButton settingsButton;
	
	public ScreenStart(FrameManager manager)
	{
		this.manager = manager;
		this.importImages();
		this.topContainer = Box.createVerticalBox();
		this.middleContainer = Box.createHorizontalBox();
		this.bottomContainer = Box.createVerticalBox();
		this.populateTopContainer();
		this.populateMiddleContainer();
		this.populateBottomContainer();
		this.add(this.topContainer);
		this.add(this.middleContainer);
		this.add(this.bottomContainer);
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
			try {
				this.manager.pushPanel(new ScreenAddToExisting(this.manager), "PEMS - Edit Existing Case");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (e.getSource() == this.settingsButton)
		{
			//temp use this to create a pdf
			try {
				this.manager.pushPanel(new ScreenPrintSetUp(this.manager, new ArrayList<Thumbnail>() ), "PEMS - PDF generator");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

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
	
	/* populateTopContainer - adds "logoLabel", "titleLabel", and "nameLabel" to "topContainer" 
	 */
	private void populateTopContainer()
	{
		this.logoLabel = ComponentGenerator.generateLabel(ImageEditor.resizeImage(this.logoImage, 200, 200), CENTER_ALIGNMENT);
		this.titleLabel = ComponentGenerator.generateLabel("Police Evidence Management System", ComponentGenerator.TITLE_FONT, ComponentGenerator.TITLE_COLOR, CENTER_ALIGNMENT);
		this.nameLabel = ComponentGenerator.generateLabel(this.manager.getConfiguration().getDepartmentName(), ComponentGenerator.SUBTITLE_FONT, ComponentGenerator.SUBTITLE_COLOR, CENTER_ALIGNMENT);
		this.topContainer.add(this.logoLabel);
		this.topContainer.add(Box.createVerticalStrut(15));
		this.topContainer.add(this.titleLabel);
		this.topContainer.add(Box.createVerticalStrut(10));
		this.topContainer.add(this.nameLabel);
		this.topContainer.add(Box.createVerticalStrut(25));
	}
	
	/* populateMiddleContainer - adds "newCaseButton", "editCaseButton", and "settingsButton" to "middleContainer" 
	 */
	private void populateMiddleContainer()
	{
		this.newCaseButton = ComponentGenerator.generateButton("New Case", this);
		this.editCaseButton = ComponentGenerator.generateButton("Edit Case", this);
		this.settingsButton = ComponentGenerator.generateButton("Settings", this);
		this.middleContainer.add(this.newCaseButton);
		this.middleContainer.add(Box.createHorizontalStrut(120));		
		this.middleContainer.add(this.editCaseButton);
		this.middleContainer.add(Box.createHorizontalStrut(120));
		this.middleContainer.add(this.settingsButton);
	}
	
	/* populateBottomContainer - adds "creditLabel" to "bottomContainer"
	 */
	private void populateBottomContainer()
	{
		this.creditLabel = ComponentGenerator.generateLabel("Copyright 2015 \u00a9 Jacob Jones and Andrew Rottier", ComponentGenerator.SMALL_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.bottomContainer.add(Box.createVerticalStrut(5));
		this.bottomContainer.add(this.creditLabel);
	}
	
}