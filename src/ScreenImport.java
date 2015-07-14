//PEMS (Police Evidence Management System) Version 0.1
//Copyright 2015 - Jacob Jones and Andrew Rottier
//ScreenImport.java

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ScreenImport extends JPanel
{

	private FrameManager manager;
	private String imageDirectoryName;
	private JLabel instructionsLabel;
	private JButton continueButton;
	private ImageEditor imgEditor;
	private ArrayList<BufferedImage> images;
	private ArrayList<JLabel> labels;
	private String directoryName;
	private Box imgBox;
	private Box selBox; //selected box
	private Box buttonsBox;
	private JButton nextButton;
	private JButton prevButton;
	private int imagePlace;
	
	private int selectedImagePlace;
	private JLabel displayLabel;
	private ArrayList<JLabel> selected;

	public ScreenImport(FrameManager manager)
	{
		this.manager = manager;
		this.imgEditor = new ImageEditor();
		this.directoryName = "/Users/andrewrottier/Documents/Pictures/SamplePictures";
		this.imagePlace = 0; this.selectedImagePlace = 0;
		this.images = this.getImages();
		this.labels = this.fillLabels();
		
		
		this.selBox = Box.createHorizontalBox();
		this.selBox.setBorder(BorderFactory.createLineBorder(Color.black));
		this.imgBox = Box.createVerticalBox();
		this.imgBox.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.initializedisplayImages();
		this.initializeSelectedImages();
		this.selected = new ArrayList<JLabel>();
		this.addActions();
		this.populateButtonsBox();
		this.manager.setResizable(true);
		this.manager.maximizeFrame();
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

	
	private void initializedisplayImages(){
		this.constructLabel("Select the images you would like to import:");
		this.displayImages();
	}
	
	/* fillRows - fills a new row with 5 new images from our labels list
	 * *****check to see how many images there are ******
	 */
	private void displayImages()
	{
		
		//add the images on the camera to the screen
		imagePlace = 0;
		
		for (int i = 0; i < 3; i++)
		{
			Box row = Box.createHorizontalBox();
			for (int j = 0; j < 5; j++)
			{
				try
				{
					row.add(this.labels.get(this.imagePlace));
					imagePlace++;
				}
				catch (Exception e)
				{
					row.add(Box.createHorizontalStrut(150)); //space the size of a picture
				}
				row.add(Box.createHorizontalStrut(25)); //spacing between pictures
				
			}
			row.setAlignmentX(CENTER_ALIGNMENT);
			this.imgBox.add(row);
		}
		this.add(this.imgBox);
		//this.constructLabel("Click to remove an image:");
		revalidate();
		repaint();
		return;
	}
	
	//break up into an initalize image function and display image func
	private void initializeSelectedImages(){
		this.constructLabel("Click to remove an image:");
		this.displaySelectedImages();
	}
	
	private void displaySelectedImages()
	{
		
		//add selected images to the screen
		selectedImagePlace = 0;
		
		for(int i = 0; i < 15; i++){
			try
			{
				//this.imgBox.setAlignmentX(CENTER_ALIGNMENT);
				this.selBox.setAlignmentX(CENTER_ALIGNMENT);
				selBox.add(this.selected.get(this.selectedImagePlace));
				//selBox.add(Box.createHorizontalStrut(25));
				selectedImagePlace++;
			}
			catch(Exception e){} 
			
		}
		this.imgBox.add(this.selBox);
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
						displayImages();
						//constructLabel("Click to remove an image:");
						displaySelectedImages();
					}
					else if(labels.contains(currentLabel))
					{
						selected.add(currentLabel);
						labels.remove(currentLabel);
						displayImages();
						//constructLabel("Click to remove an image:");
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
	
	private void constructLabel(String text)
	{
		this.displayLabel = new JLabel(text);
		this.displayLabel.setFont(this.manager.STANDARD_TEXT_FONT);
		this.displayLabel.setForeground(this.manager.STANDARD_TEXT_COLOR);
		this.displayLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.imgBox.add(this.displayLabel);
	}
	
	/* populateButtonsBox - fills the "buttonsBox" layout structure with the necessary components
	 */
	private void populateButtonsBox()
	{
		this.buttonsBox = Box.createHorizontalBox();
		this.constructNewCaseButton();
		this.add(this.buttonsBox);
	}
	
	/* createNextButton - navigate to the next screen
	 */
	private void constructNewCaseButton()
	{
		this.nextButton = new JButton("Next");
		this.nextButton.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	manager.pushPanel(new ScreenEdit(manager), "PEMS - Edit Photos");
            }
		});
		this.buttonsBox.add(this.nextButton);
	}
	
	private void createPrevButton()
	{
		
	}

}