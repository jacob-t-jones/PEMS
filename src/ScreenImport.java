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
	private BufferedImage[] images;
	private JLabel[] labels;
	private Box[] rows;
	private Box[] col;
	
	private String imageDirectoryName;
	private JLabel instructionsLabel;
	private JButton continueButton;

	public ScreenImport(FrameManager manager)
	{
		this.manager = manager;
		//this.constructInstructionsLabel();
		this.images = this.getImages();
		this.labels = this.fillLabels(images); 
		//now we have an array of Jlabels filled w images
		//display the images on the screen
		
	}
	
	/* fillLabels - creates "labelList" and adds the images into an array of JLabels
	 */
	private JLabel[] fillLabels(BufferedImage[] imageList){
		JLabel[] labelList = new JLabel[imageList.length];
		
		for(int i = 0; i < imageList.length; i++){
			labelList[i] = new JLabel(new ImageIcon(this.manager.resizeImage(imageList[i], 50, 40)));;
		}
		
		return labelList;
	}
 
	/* getImages - creates "photoLists" and adds images to the ScreenImport
	 */
	private BufferedImage[] getImages()
	{
	    File imageDirectory = new File(this.imageDirectoryName);
		String[] imageFileNames = imageDirectory.list();
		BufferedImage[] imageList = new BufferedImage[imageFileNames.length];
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
		    imageList[i] = currentImage;
		    
		}
	    return imageList;
	}
	
	/*private void constructInstructionsLabel()
	{
		this.instructionsLabel = new JLabel("Please select the images you would like to add to the case:");
		this.instructionsLabel.setFont(this.manager.STANDARD_TEXT_FONT);
		this.instructionsLabel.setForeground(this.manager.STANDARD_TEXT_COLOR);
		this.instructionsLabel.setAlignmentX(CENTER_ALIGNMENT);
	}*/

}