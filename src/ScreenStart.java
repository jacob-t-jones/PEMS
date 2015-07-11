// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenStart.java

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
		this.manager = manager;
		this.constructLogoLabel();
		this.constructNameLabel();
	}
	
	/* constructLogoLabel - creates "logoLabel" and adds it to ScreenStart
	 */
	public void constructLogoLabel()
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
	    this.add(this.logoLabel);
	}
	
	/* constructNameLabel - creates "nameLabel" and adds it to ScreenStart
	 */
	public void constructNameLabel()
	{
		this.nameLabel = new JLabel(this.manager.getConfiguration().getDepartmentName());
		this.nameLabel.setFont(this.manager.TITLE_FONT);
		this.add(this.nameLabel);
	}
	
}
