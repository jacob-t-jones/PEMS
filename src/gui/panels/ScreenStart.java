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

public class ScreenStart extends JPanel
{

	private FrameManager manager;
	private BufferedImage logoImage;
	private BufferedImage bgImage;
	private ActionListener newCaseListener;
	private ActionListener editCaseListener;
	private ActionListener settingsListener;
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
		this.generateListeners();
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
	
	/* generateListeners - initializes listeners for all of the components within the JPanel
	 *   newCaseListener - pushes ScreenNewCase to the JFrame
	 *  editCaseListener - pushes ScreenAddToExisting to the JFrame
	 *  settingsListener - pushes ScreenSettings to the JFrame
	 */
	private void generateListeners()
	{
		this.newCaseListener = new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	manager.pushPanel(new ScreenNewCase(manager), "PEMS - Create New Case");
            }
		};
		this.editCaseListener = new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	try 
            	{
					manager.pushPanel(new ScreenAddToExisting(manager), "PEMS - Edit Existing Case");
				} catch (IOException e1) 
            	{
					e1.printStackTrace();
				}
            }
		};
		this.settingsListener = new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	// TODO: On button press actions
            }
		};
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
		this.newCaseButton = ComponentGenerator.generateButton("New Case", this.newCaseListener);
		this.editCaseButton = ComponentGenerator.generateButton("Edit Case", this.editCaseListener);
		this.settingsButton = ComponentGenerator.generateButton("Settings", this.settingsListener);
		this.bottomContainer = Box.createHorizontalBox();
		this.bottomContainer.add(this.newCaseButton);
		this.bottomContainer.add(Box.createHorizontalStrut(120));		
		this.bottomContainer.add(this.editCaseButton);
		this.bottomContainer.add(Box.createHorizontalStrut(120));
		this.bottomContainer.add(this.settingsButton);
		this.add(this.bottomContainer);
	}
	
}