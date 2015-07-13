//PEMS (Police Evidence Management System) Version 0.1
//Copyright 2015 - Jacob Jones and Andrew Rottier
//ScreenImport.java

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ScreenImport extends JPanel
{
	
	private FrameManager manager;
	private JLabel logoLabel;
	private JLabel titleLabel;
	private JLabel nameLabel;
	private LinkedList<JLabel> photos;
	
	public ScreenImport(FrameManager manager)
	{
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.manager = manager;
		
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.getImages();
		
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
	
	/* getImages - creates "photoLists" and adds images to the ScreenImport
	 */
	private LinkedList<JLabel> getImages()
	{
		LinkedList<JLabel> photoList = new LinkedList<JLabel>();
		BufferedImage logoImage = null;
		
	    try 
	    {   
	    	//for loop to add all of the photos to the photoList..
	    	
	    	//replace with path of files on the camera when plugged in
	    	logoImage = ImageIO.read(new File("resources/logo.png"));
	    } 
	    catch (Exception e)
	    {
	    	//otherwise add the gif image prompting the user to plug in the camera
			System.out.println("Please connected the camera via USB");
			return null;
	    }
	    this.logoLabel = new JLabel(new ImageIcon(this.manager.resizeImage(logoImage, 200, 200)));
	    this.logoLabel.setAlignmentX(CENTER_ALIGNMENT);
	    this.add(this.logoLabel);
	    //add images to a list to return
	    return photoList;
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

