//PEMS (Police Evidence Management System) Version 0.1
//Copyright 2015 - Jacob Jones and Andrew Rottier
//ScreenImport.java

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ScreenImport extends JPanel
{
	
	private FrameManager manager;
	private JLabel logoLabel;
	private JLabel titleLabel;
	private JLabel nameLabel;
	
	public ScreenImport(FrameManager manager)
	{
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.manager = manager;
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.constructLogoLabel();
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.constructTitleLabel();
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.constructNameLabel();
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
	
	/* constructTitleLabel - creates "titleLabel" and adds it to ScreenStart
	 */
	private void constructTitleLabel()
	{
		this.titleLabel = new JLabel("Police Evidence Management System");
		this.titleLabel.setFont(this.manager.TITLE_FONT);
		this.titleLabel.setForeground(this.manager.TITLE_COLOR);
	    this.titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.add(this.titleLabel);
	}
	
	/* constructNameLabel - creates "nameLabel" and adds it to ScreenStart
	 */
	private void constructNameLabel()
	{
		this.nameLabel = new JLabel(this.manager.getConfiguration().getDepartmentName());
		this.nameLabel.setFont(this.manager.SUBTITLE_FONT);
		this.nameLabel.setForeground(this.manager.SUBTITLE_COLOR);
	    this.nameLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.add(this.nameLabel);
	}
	
	
}

