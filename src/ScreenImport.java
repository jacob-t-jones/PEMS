//PEMS (Police Evidence Management System) Version 0.1
//Copyright 2015 - Jacob Jones and Andrew Rottier
//ScreenImport.java

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ScreenImport extends JPanel
{

	private FrameManager manager;
	private ImageEditor imgEditor;
	private ArrayList<BufferedImage> images;
	private ArrayList<JLabel> labels;
	private String directoryName;
	private Box imgBox;
	private Box buttonsBox;
	private JButton nextButton;
	private JButton prevButton;
	private int imagePlace;

	public ScreenImport(FrameManager manager)
	{
		this.manager = manager;
<<<<<<< HEAD
		this.imgEditor = new ImageEditor();
		this.directoryName = "/Users/Jacob/Documents/Pics";
		this.imagePlace = 0;
		this.images = this.getImages();
		this.labels = this.fillLabels(); 
		this.displayImages();
		this.populateButtonsBox();
		this.manager.setResizable(true);
		this.manager.maximizeFrame();
=======
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
		
>>>>>>> origin/master
	}
	
	/* getImages - creates "photoLists" and adds images to the ScreenImport
	 */
	private ArrayList<BufferedImage> getImages()
	{
		ArrayList<BufferedImage> imageList = new ArrayList<BufferedImage>();
	    File imageDirectory = new File(this.directoryName);
		String[] imageFileNames = imageDirectory.list();
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
		    	System.out.println("Error - Unable to read image");
		    	return null;
		    }
		    imageList.add(currentImage);
		}
	    return imageList;
	}
	
	/* fillLabels - creates "labelList" and adds the images into an array of JLabels
	 */
	private ArrayList<JLabel> fillLabels()
	{
		ArrayList<JLabel> labelList = new ArrayList<JLabel>();
		for (int i = 0; i < this.images.size(); i++)
		{
			labelList.add(new JLabel(new ImageIcon(this.imgEditor.resizeImage(this.images.get(i), 150, 200))));
		}
		return labelList;
	}
	
	/* fillRows - fills a new row with 5 new images from our labels list
	 * *****after put all code inside a for loop to create 3 rows******
	 */
	private void displayImages()
	{
		this.imgBox = Box.createVerticalBox();
		for (int i = 0; i < 3; i++)
		{
			Box row = Box.createHorizontalBox();
			for (int j = 0; j < 5; j++)
			{
				row.add(this.labels.get(this.imagePlace));
				row.add(Box.createHorizontalStrut(25));
				imagePlace++;
			}
			this.imgBox.add(Box.createVerticalStrut(10));
			this.imgBox.add(row);
			this.imgBox.add(Box.createVerticalStrut(10));
		}
		this.add(this.imgBox);
		return;
	}
	
	private void populateButtonsBox()
	{
		
	}
	
	private void createNextButton()
	{
		
	}
	
	private void createPrevButton()
	{
		
	}

}