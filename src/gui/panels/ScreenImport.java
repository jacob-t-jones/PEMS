//PEMS (Police Evidence Management System) Version 0.1
//Copyright 2015 - Jacob Jones and Andrew Rottier
//ScreenImport.java

package gui.panels;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import gui.*;
import tools.*;

public class ScreenImport extends JPanel
{

	private FrameManager manager;
	private ArrayList<Thumbnail> images;
	private ArrayList<JLabel> labels;
	private String imageDirectoryName;
	private JLabel instructionsLabel;
	private JButton continueButton;
	private ImageEditor imgEditor;
	private String directoryName;
	private Box imgBox;
	private Box selBox; //selected box
	private Box buttonsBox;
	private JButton nextButton;
	private JButton loadMoreImagesButton;
	private JButton loadPrevImagesButton;
	private JButton prevButton;
	private int imagePlace;
	private int selectedImagePlace;
	private JLabel displayLabel;
	private ArrayList<JLabel> selected;

	public ScreenImport(FrameManager manager)
	{
		this.manager = manager;
		this.populateButtonsBox();
		this.imgEditor = new ImageEditor();
		this.directoryName = "/Users/andrewrottier/Documents/Pictures/CrimePhotos";
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
		
		this.manager.setResizable(true);
		this.manager.maximizeFrame();
	}
	
	/* getImages - creates "photoLists" and adds images to the ScreenImport
	 */
	private ArrayList<Thumbnail> getImages()
	{
		ArrayList<Thumbnail> imageList = new ArrayList<Thumbnail>();
	    File imageDirectory = new File(this.directoryName);
		String[] imageFileNames = imageDirectory.list();
		BufferedImage currentImage = new BufferedImage(12, 12, 12);
		String currentPath = new String();
		for (int i = 0; i < imageFileNames.length; i++)
		{
			
			System.out.println(imageFileNames[i]);
		    try 
		    {   
		    	currentImage = ImageIO.read(new File(imageDirectory + "/" + imageFileNames[i]));
		    	try
		    	{
		    		currentPath = imageDirectory + "/" + imageFileNames[i];
		    	}
		    	catch(Exception e)
		    	{
		    		System.out.println("Error - Unable to get file location");
			    	return null;
		    	}
		    } 
		    catch (Exception e)
		    {
		    	System.out.println("Error - Unable to read image");
		    	return null;
		    }
		    Thumbnail currentThumb = new Thumbnail(currentImage, currentPath);
		    imageList.add(currentThumb);
		}
	    return imageList;
	}
	
	/* fillLabels - creates "labelList" and adds the images into an array of JLabels
	 */
	private ArrayList<JLabel> fillLabels()
	{
		ArrayList<JLabel> labelList = new ArrayList<JLabel>();
		JLabel newLabel = new JLabel();
		for (int i = 0; i < this.images.size(); i++)
		{
			try
			{
				newLabel = new JLabel(new ImageIcon(this.imgEditor.resizeImage(this.images.get(i).getImage(), 200)));
			}
			catch(Exception e)
			{
				System.out.println("Error - image could not be resized");
			}
			newLabel.setAlignmentX(CENTER_ALIGNMENT);
			labelList.add(newLabel);
		}
		return labelList;
	}

	/* initializedisplayImages - Initialize the displayed images box - contents from the case folder
	 */
	private void initializedisplayImages(){
		this.constructLabel("Select the images you would like to import:");
		this.displayImages(0);
	}
	
	/* displayImages - iterate through the images in a case file and display the 
	 * 				   specified ones on the screen
	 *      imageNum - the image place holder that specifies which image you would like
	 *                 to start at when displaying a screen of images
	 */
	private void displayImages(int imageNum)
	{
		this.imgBox.removeAll();
		this.remove(this.imgBox);
		this.repaint();
		this.revalidate();
		//add the images on the camera to the screen
		imagePlace = imageNum;
		for (int i = 0; i < 3; i++)
		{
			Box row = Box.createHorizontalBox();
			for (int j = 0; j < 5; j++)
			{
				try
				{
					row.add(this.labels.get(this.imagePlace));
					System.out.println(imagePlace);
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
		imagePlace = imageNum; //reset back to original param to avoid errors w next/prev buttons
		this.add(this.imgBox);
		revalidate();
		repaint();
		return;
	}
	
	/* initializeSelectedImages - Initialize the selected images box
	 */
	private void initializeSelectedImages(){
		this.constructLabel("Click to remove an image:");
		this.displaySelectedImages();
	}
	
	/* displaySelectedImages - iterate through the images in a case file and display the 
	 * 				  		   specified ones on the screen
	 *      		imageNum - the image place holder that specifies which image you would like
	 *              		   to start at when displaying a screen of images
	 */
	private void displaySelectedImages()
	{
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
						displayImages(imagePlace);
						//constructLabel("Click to remove an image:");
						displaySelectedImages();
					}
					else if(labels.contains(currentLabel))
					{
						selected.add(currentLabel);
						labels.remove(currentLabel);
						displayImages(imagePlace);
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
		this.displayLabel.setFont(ComponentGenerator.STANDARD_TEXT_FONT);
		this.displayLabel.setForeground(ComponentGenerator.STANDARD_TEXT_COLOR);
		this.displayLabel.setAlignmentX(LEFT_ALIGNMENT);
		this.displayLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.imgBox.add(this.displayLabel);
	}
	
	/* populateButtonsBox - fills the "buttonsBox" layout structure with the necessary components
	 */
	private void populateButtonsBox()
	{
		this.buttonsBox = Box.createHorizontalBox();
		this.buttonsBox.setAlignmentX(CENTER_ALIGNMENT);
		this.add(Box.createVerticalStrut(100));
		this.createLoadPrevButton();
		this.add(Box.createVerticalStrut(100));
		this.constructNextButton();
		this.add(Box.createVerticalStrut(100));
		this.createLoadMoreButton();
		this.add(this.buttonsBox);
		this.buttonsBox.setLocation(600, 700);
	}
	
	/* createNextButton - navigate to the next screen
	 */
	private void constructNextButton()
	{
		this.nextButton = new JButton("Next");
		this.nextButton.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	manager.pushPanel(new ScreenEdit(manager, selected), "PEMS - Edit Photos");
            }
		});
		this.buttonsBox.add(this.nextButton);
	}

	/* getSelected - return the selected pictures array 
	 */
	public ArrayList<JLabel> getSelected(){
		return this.selected;
	}
	
	/* createLoadPrevButton - Load the previous 15 pictures to display
	 */
	private void createLoadPrevButton()
	{
		this.loadPrevImagesButton = new JButton("Load Prev");
		this.loadPrevImagesButton.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	if(imagePlace > 0)
            		displayImages(imagePlace-15);
            }
		});
		this.buttonsBox.add(this.loadPrevImagesButton);
	}
	
	/* createLoadMoreButton - Load the next 15 pictures to display
	 */
	private void createLoadMoreButton()
	{
		this.loadMoreImagesButton = new JButton("Load More");
		this.loadMoreImagesButton.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	if(imagePlace < labels.size())
            		displayImages(imagePlace+15);
            }
		});
		this.buttonsBox.add(this.loadMoreImagesButton);
	}
	
	private void createPrevButton()
	{
		
	}
		

}