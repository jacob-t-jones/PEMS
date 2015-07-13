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
<<<<<<< HEAD
<<<<<<< HEAD
	private JLabel logoLabel;
	private JLabel titleLabel;
	private JLabel nameLabel;
	private LinkedList<JLabel> photos;
=======
	private LinkedList<JLabel> imageLabels;
>>>>>>> origin/master
=======
	private LinkedList<BufferedImage> images;
	private String imageDirectoryName;
>>>>>>> origin/master
	
	private Box inputBox;
	private JLabel instructionsLabel;
	private JTextField caseNumField;
	private JButton continueButton;

	public ScreenImport(FrameManager manager)
	{
		this.manager = manager;
<<<<<<< HEAD
<<<<<<< HEAD
		this.populateInputBox();
		
		
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.getImages();
		
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		//this.constructTitleLabel();
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		//this.constructNameLabel();
	}
	
	private void populateInputBox()
	{
		this.inputBox = Box.createVerticalBox();
		this.inputBox.add(Box.createVerticalStrut(20));
		this.constructInstructionsLabel();
		this.inputBox.add(Box.createVerticalStrut(60));
		this.constructCaseNumField();
		this.inputBox.add(Box.createVerticalStrut(80));
		this.constructContinueButton();
		this.add(this.inputBox);
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
=======
		this.imageLabels = this.getImages();
=======
		this.images = this.getImages();
>>>>>>> origin/master
	} 
>>>>>>> origin/master
	
	/* getImages - creates "photoLists" and adds images to the ScreenImport
	 */
	private LinkedList<BufferedImage> getImages()
	{
<<<<<<< HEAD
		LinkedList<BufferedImage> photoList = new LinkedList<BufferedImage>();
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
	
	
<<<<<<< HEAD
=======
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
>>>>>>> origin/master
=======
		File imageDirectory = new File(this.imageDirectoryName);
		String[] imageFileNames = imageDirectory.list();
		LinkedList<BufferedImage> imageList = new LinkedList<BufferedImage>();
		BufferedImage currentImage = null;
		for (int i = 0; i < imageFileNames.length; i++)
		{
		    try 
		    {   
		    	currentImage = ImageIO.read(new File(imageDirectory + imageFileNames[i]));
		    } 
		    catch (Exception e)
		    {
		    	System.out.println("Error - Please connect the camera via USB");
		    	return null;
		    }
		    imageList.addLast(currentImage);
		}
	    return imageList;
	}
	
>>>>>>> origin/master

}

