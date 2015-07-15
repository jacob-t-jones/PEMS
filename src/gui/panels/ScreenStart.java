// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenStart.java

package gui.panels;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import gui.*;
import tools.*;

public class ScreenStart extends JPanel
{

	private FrameManager manager;
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
		this.populateTopContainer();
		this.populateBottomContainer();
	}
	
	/* paintComponent - override function used, in this case, to set a background image
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
	
	/* populateTopContainer - adds "logoLabel", "titleLabel", and "nameLabel" to "topContainer" before displaying it
	 */
	private void populateTopContainer()
	{
		this.topContainer = Box.createVerticalBox();
		this.constructLogoLabel();
		this.topContainer.add(Box.createVerticalStrut(20));
		this.constructTitleLabel();
		this.topContainer.add(Box.createVerticalStrut(10));
		this.constructNameLabel();
		this.topContainer.add(Box.createVerticalStrut(40));
		this.add(this.topContainer);
	}
	
	/* constructLogoLabel - creates "logoLabel" using an image imported from resources and adds it to "topContainer"
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
	    this.logoLabel = new JLabel(new ImageIcon(ImageEditor.resizeImage(logoImage, 200, 200)));
	    this.logoLabel.setAlignmentX(CENTER_ALIGNMENT);
	    this.topContainer.add(this.logoLabel);
	}
	
	/* constructTitleLabel - creates "titleLabel", sets its font and alignment, and adds it to "topContainer"
	 */
	private void constructTitleLabel()
	{
		this.titleLabel = new JLabel("Police Evidence Management System");
		this.titleLabel.setFont(ComponentGenerator.TITLE_FONT);
		this.titleLabel.setForeground(ComponentGenerator.TITLE_COLOR);
	    this.titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.topContainer.add(this.titleLabel);
	}
	
	/* constructNameLabel - creates "nameLabel", sets its font and alignment, and adds it to "topContainer"
	 */
	private void constructNameLabel()
	{
		this.nameLabel = new JLabel(this.manager.getConfiguration().getDepartmentName());
		this.nameLabel.setFont(ComponentGenerator.SUBTITLE_FONT);
		this.nameLabel.setForeground(ComponentGenerator.SUBTITLE_COLOR);
	    this.nameLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.topContainer.add(this.nameLabel);
	}
	
	/* populateBottomContainer - adds "newCaseButton" and "settingsButton" to "bottomContainer" before displaying it
	 */
	private void populateBottomContainer()
	{
		this.bottomContainer = Box.createHorizontalBox();
		this.constructNewCaseButton();
		this.bottomContainer.add(Box.createHorizontalStrut(120));		
		this.constructEditCaseButton();
		this.bottomContainer.add(Box.createHorizontalStrut(120));
		this.constructSettingsButton();
		this.add(this.bottomContainer);
	}
	
	/* constructNewCaseButton - creates "newCaseButton", makes an ActionListener for it, and adds it to "bottomContainer"
	 *        actionPerformed - pushes the ScreenNewCase JPanel into the JFrame
	 */
	private void constructNewCaseButton()
	{
		this.newCaseButton = new JButton("New Case");
		this.newCaseButton.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	manager.pushPanel(new ScreenNewCase(manager), "PEMS - Create New Case");
            }
		});
		this.bottomContainer.add(this.newCaseButton);
	}
	
	/* constructEditCaseButton - creates "editCaseButton", makes an ActionListener for it, and adds it to "bottomContainer"
	 *         actionPerformed - pushes the ScreenEditCase JPanel into the JFrame
	 */
	private void constructEditCaseButton()
	{
		this.editCaseButton = new JButton("Edit Existing Case");
		this.editCaseButton.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	manager.pushPanel(new ScreenAddToExisting(manager), "PEMS - Edit Existing Case");
            }
		});
		this.bottomContainer.add(this.editCaseButton);
	}
	
	/* constructSettingsButton - creates "settingsButton", makes an ActionListener for it, and adds it to "bottomContainer"
	 *         actionPerformed - pushes the ScreenSettings JPanel into the JFrame
	 */
	private void constructSettingsButton()
	{
		this.settingsButton = new JButton("Settings");
		this.settingsButton.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	// TODO: On button press actions
            }
		});
		this.bottomContainer.add(this.settingsButton);
	}
	
}