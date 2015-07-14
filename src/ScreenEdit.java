// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenStart.java

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ScreenEdit extends JPanel{
	
	private FrameManager manager;
	
	private ImageEditor imgEditor;
	private ArrayList<BufferedImage> images;
	private ArrayList<JLabel> labels;
	private String directoryName;
	private Box imgBox;
	private Box selBox; //selected box
	private Box buttonsBox;
	private JButton nextButton;
	private JButton prevButton;
	
	private int selectedImagePlace;
	private JLabel displayLabel;
	private ArrayList<JLabel> selected;
	
	public ScreenEdit(FrameManager manager)
	{
		this.manager = manager;
		this.imgEditor = new ImageEditor();
		this.directoryName = "/Users/andrewrottier/Documents/Pictures/SamplePictures";
		this.selectedImagePlace = 0;
		this.images = this.getImages();
		this.labels = this.fillLabels();
		this.selected = new ArrayList<JLabel>();
		
		this.selBox = Box.createHorizontalBox();
		this.selBox.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.initializeSelectedImages();
		
		this.addActions();
		
		this.manager.setResizable(true);
		this.manager.maximizeFrame();
	}
	
	//break up into an initailize image function and display image func
	private void initializeSelectedImages(){
		this.constructLabel("Click an image to edit it");
		this.displaySelectedImages();
	}
		
	private void displaySelectedImages()
	{
			
		//add selected images to the screen
		selectedImagePlace = 0;
			
		for(int i = 0; i < 15; i++){
			try
			{
				this.selBox.setAlignmentX(CENTER_ALIGNMENT);
				selBox.add(this.selected.get(this.selectedImagePlace));
				selectedImagePlace++;
			}
			catch(Exception e){} 
			
		}
		this.add(this.selBox);
	}
		
	private void constructLabel(String text)
	{
		this.displayLabel = new JLabel(text);
		this.displayLabel.setFont(this.manager.STANDARD_TEXT_FONT);
		this.displayLabel.setForeground(this.manager.STANDARD_TEXT_COLOR);
		this.displayLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.add(this.displayLabel);
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
			JLabel newLabel = new JLabel(new ImageIcon(this.imgEditor.resizeImage(this.images.get(i), 150, 200)));
			newLabel.setAlignmentX(CENTER_ALIGNMENT);
			labelList.add(newLabel);
		}
		return labelList;
	}
	
	/* addActions - turns each picture into a button
	 */
	private void addActions(){
		
		for(int i = 0; i < labels.size(); i++)
		{
			final JLabel currentLabel = this.labels.get(i);
			currentLabel.addMouseListener(new MouseListener()
			{
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) 
				{
					if(selected.contains(currentLabel))
					{
						labels.add(currentLabel);
						selected.remove(currentLabel);
						displaySelectedImages();
					}
					else if(labels.contains(currentLabel))
					{
						selected.add(currentLabel);
						labels.remove(currentLabel);
						displaySelectedImages();
					}
					
					
				}

				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(java.awt.event.MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(java.awt.event.MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(java.awt.event.MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				
			});
		}
	}

	
	

}
