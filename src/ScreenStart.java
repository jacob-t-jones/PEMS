// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenStart.java

import java.awt.*;
import java.awt.image.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ScreenStart extends JPanel
{
	
	private FrameManager manager;
	private JLabel logoLabel;
	private JLabel nameLabel;
	
	public ScreenStart(FrameManager manager)
	{
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.manager = manager;
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.constructLogoLabel();
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.constructNameLabel();
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
	    this.add(this.logoLabel);
	}
	
	/* constructNameLabel - creates "nameLabel" and adds it to ScreenStart
	 */
	private void constructNameLabel()
	{
		this.nameLabel = new JLabel(this.manager.getConfiguration().getDepartmentName());
		this.nameLabel.setFont(this.manager.TITLE_FONT);
	    this.nameLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.add(this.nameLabel);
	}
	
}
