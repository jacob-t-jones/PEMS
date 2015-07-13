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
		this.imageDirectoryName = "/Users/andrewrottier/Documents/Pictures/Instagram";
		//this.constructInstructionsLabel();
		this.images = this.getImages();
		this.labels = this.fillLabels(); 
		//now we have an array of Jlabels filled w images
		//display the images on the screen
		this.displayImages(labels);
		
	}
	
	/* fillRows - fills a new row with 5 new images from our labels list
	 * *****after put all code inside a for loop to create 3 rows******
	 */
	private void displayImages(JLabel[] labelList){
		int imagePlace = 0;
		Box container = Box.createVerticalBox();
		
		for(int i = 0; i < 3; i++){
			Box row = Box.createHorizontalBox();
			for(int j = 0; j < 5; j++){
				row.add(labelList[imagePlace]);
				row.add(Box.createHorizontalStrut(10)); //space each pic horizontally
				imagePlace++;
			}
			container.add(row);
			container.add(Box.createVerticalStrut(20)); //space between each row
		}
		
		this.add(container);
		return;
		
	}
	
	/* fillLabels - creates "labelList" and adds the images into an array of JLabels
	 */
	private JLabel[] fillLabels()
	{
		JLabel[] labelList = new JLabel[this.images.length];
		
		for(int i = 0; i < images.length; i++){
			labelList[i] = new JLabel(new ImageIcon(this.manager.resizeImage(images[i], 50, 40)));;
		}
		
		return labelList;
	}
 
	/* getImages - creates "photoLists" and adds images to the ScreenImport
	 */
	private BufferedImage[] getImages()
	{
	    File imageDirectory = new File(this.imageDirectoryName);
		String[] imageFileNames = imageDirectory.list();
		System.out.println(imageDirectory.getPath());
		BufferedImage[] imageList = new BufferedImage[imageFileNames.length];
		BufferedImage currentImage = null;
		for (int i = 0; i < imageFileNames.length; i++)
		{
			System.out.println(imageFileNames[i]);
		    try 
		    {   
		    	currentImage = ImageIO.read(new File(imageDirectory + "/" + imageFileNames[i]));
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