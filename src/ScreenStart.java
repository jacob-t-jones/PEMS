// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenStart.java

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ScreenStart extends JPanel
{
	
	private FrameManager manager;
	private Box titleBox;
	private Box buttonsBox;
	private JLabel logoLabel;
	private JLabel titleLabel;
	private JLabel nameLabel;
	private JButton newCaseButton;
	private JButton settingsButton;
	
	public ScreenStart(FrameManager manager)
	{
		this.manager = manager;
		this.populateTitleBox();
		this.populateButtonsBox();
	}
	
	/* paintComponent - override function 
	 * 			    g - the current Graphics instance
	 */
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Image backgroundImage = null;
	    try 
	    {                
	    	backgroundImage = ImageIO.read(new File("resources/background.png"));
	    } 
	    catch (Exception e)
	    {
			System.out.println("Error - Unable to find background image");
			return;
	    }
		g.drawImage(backgroundImage, 0, 0, null);
	}
	
	/* populateTitleBox - fills the "titleBox" layout structure with the necessary components.
	 */
	private void populateTitleBox()
	{
		this.titleBox = Box.createVerticalBox();
		this.constructLogoLabel();
		this.titleBox.add(Box.createVerticalStrut(20));
		this.constructTitleLabel();
		this.titleBox.add(Box.createVerticalStrut(10));
		this.constructNameLabel();
		this.titleBox.add(Box.createVerticalStrut(40));
		this.add(this.titleBox);
	}
	
	/* populateButtonsBox - fills the "buttonsBox" layout structure with the necessary components.
	 */
	private void populateButtonsBox()
	{
		this.buttonsBox = Box.createHorizontalBox();
		this.constructNewCaseButton();
		this.buttonsBox.add(Box.createHorizontalStrut(200));
		this.constructSettingsButton();
		this.add(this.buttonsBox);
	}
	
	/* constructLogoLabel - creates "logoLabel" and adds it to ScreenStart
	 */
	private void constructLogoLabel()
	{
		BufferedImage logoImage = null;
	    try 
	    {                
	    	logoImage = ImageIO.read(new File("resources/logo.png"));
	    } 
	    catch (Exception e)
	    {
			System.out.println("Error - Unable to find logo");
			return;
	    }
	    this.logoLabel = new JLabel(new ImageIcon(this.manager.resizeImage(logoImage, 200, 200)));
	    this.logoLabel.setAlignmentX(CENTER_ALIGNMENT);
	    this.titleBox.add(this.logoLabel);
	}
	
	/* constructTitleLabel - creates "titleLabel" and adds it to ScreenStart
	 */
	private void constructTitleLabel()
	{
		this.titleLabel = new JLabel("Police Evidence Management System");
		this.titleLabel.setFont(this.manager.TITLE_FONT);
		this.titleLabel.setForeground(this.manager.TITLE_COLOR);
	    this.titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.titleBox.add(this.titleLabel);
	}
	
	/* constructNameLabel - creates "nameLabel" and adds it to ScreenStart
	 */
	private void constructNameLabel()
	{
		this.nameLabel = new JLabel(this.manager.getConfiguration().getDepartmentName());
		this.nameLabel.setFont(this.manager.SUBTITLE_FONT);
		this.nameLabel.setForeground(this.manager.SUBTITLE_COLOR);
	    this.nameLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.titleBox.add(this.nameLabel);
	}
	
	/* constructNewCaseButton - creates "newCaseButton" and adds it to ScreenStart
	 */
	private void constructNewCaseButton()
	{
		this.newCaseButton = new JButton("New Case");
		this.newCaseButton.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	manager.pushPanel(new ScreenNewCase(manager), "PEMS - Create New Case");
            	manager.maximizeFrame();
            }
		});
		this.buttonsBox.add(this.newCaseButton);
	}
	
	/* constructSettingsButton - creates "settingsButton" and adds it to ScreenStart
	 */
	private void constructSettingsButton()
	{
		this.settingsButton = new JButton("Settings");
		this.settingsButton.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            }
		});
		this.buttonsBox.add(this.settingsButton);
	}
	
}
