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
	private LinkedList<BufferedImage> images;
	private LinkedList<JLabel> labels;
	private String imageDirectoryName;
	private JLabel instructionsLabel;
	private JButton continueButton;

	public ScreenImport(FrameManager manager)
	{
		this.manager = manager;
		this.images = this.getImages();
	}
 

	/* getImages - creates "photoLists" and adds images to the ScreenImport
	 */
	private LinkedList<BufferedImage> getImages()
	{
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

}

