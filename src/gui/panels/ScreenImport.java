// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenImport.java

package gui.panels;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import gui.*;
import tools.*;

public class ScreenImport extends JPanel
{

	private FrameManager manager;
	private ArrayList<Thumbnail> thumbnails;
	private ArrayList<JLabel> displayedLabels;
	private ArrayList<JLabel> selectedLabels;
	private String imageDirectoryName;
	private Box displayedBox;
	private Box selectedBox; 
	private Box buttonsBox;
	private JLabel instructionsLabel;
	private JButton loadNextButton;
	private JButton loadPrevButton;
	private JButton continueButton;
	private String directoryName;
	private int displayedImagePlace;
	private int selectedImagePlace;

	public ScreenImport(FrameManager manager)
	{
		this.manager = manager;
		this.directoryName = "/Users/Jacob/Documents/Pics";
		this.displayedImagePlace = 0;
		this.selectedImagePlace = 0;
		this.thumbnails = this.getThumbnails();
		this.displayedLabels = this.getDisplayedLabels();
		this.selectedLabels = new ArrayList<JLabel>();
		/*this.selBox = Box.createHorizontalBox();
		this.selBox.setBorder(BorderFactory.createLineBorder(Color.black));
		this.imgBox = Box.createVerticalBox();
		this.imgBox.setBorder(BorderFactory.createLineBorder(Color.black));
		this.initializedisplayImages();
		this.initializeSelectedImages();
		this.addActions();*/
		this.manager.setResizable(true);
		this.manager.maximizeFrame();
	}
	
	/* getThumbnails - fills "thumbnails" by importing images into memory
	 */
<<<<<<< HEAD
	private ArrayList<Thumbnail> getImages()
	{
		ArrayList<Thumbnail> imageList = new ArrayList<Thumbnail>();
	    File imageDirectory = new File(this.directoryName);
		String[] imageFileNames = imageDirectory.list();
		BufferedImage currentImage = new BufferedImage(12, 12, 12);
		String currentPath = new String();
		for (int i = 0; i < imageFileNames.length; i++)
=======
	private ArrayList<Thumbnail> getThumbnails()
	{ 
		ArrayList<Thumbnail> thumbnailList = new ArrayList<Thumbnail>();
	    File directory = new File(this.directoryName);
		String[] fileNames = directory.list();
		for (int i = 0; i < fileNames.length; i++)
>>>>>>> origin/master
		{
			String currentFileName = fileNames[i];
			if (this.validateExtension(currentFileName))
			{
				BufferedImage currentImage = null;
				String currentLocation = this.directoryName + "/" + fileNames[i];
			    try 
			    {   
			    	 currentImage = ImageIO.read(new File(currentLocation));
			    }
			    catch (IOException e)
			    {
			    	System.out.println("Error - Unable to read image into memory");
			    	e.printStackTrace();
				    return null;
			    }	    	
			    Thumbnail currentThumb = new Thumbnail(currentImage, currentLocation);
			    thumbnailList.add(currentThumb);
			}
		}
	    return thumbnailList;
	}
	
	/* getDisplayedLabels - fills "displayedLabels" by creating a JLabel for each entry in the "thumbnails" ArrayList.
	 */
	private ArrayList<JLabel> getDisplayedLabels()
	{
		ArrayList<JLabel> labelList = new ArrayList<JLabel>();
		for (int i = 0; i < this.thumbnails.size(); i++)
		{
			JLabel newLabel = ComponentGenerator.generateLabel(ImageEditor.resizeImage(this.thumbnails.get(i).getImage(), 200), CENTER_ALIGNMENT);
			labelList.add(newLabel);
		}
		return labelList;
	}
<<<<<<< HEAD

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
=======
	
	/* refreshDisplayedLabels - refreshes the JLabels displayed on the screen
>>>>>>> origin/master
	 */
	private void refreshDisplayedLabels(int displayedImagePlace)
	{
		this.displayedBox.removeAll();
		this.remove(this.displayedBox);
		this.revalidate();
		this.repaint();
		this.displayedImagePlace = displayedImagePlace;
		for (int i = 0; i < 3; i++)
		{
			Box row = Box.createHorizontalBox();
			row.setAlignmentX(CENTER_ALIGNMENT);
			for (int j = 0; j < 5; j++)
			{
				if (this.displayedImagePlace < this.displayedLabels.size())
				{
					row.add(this.displayedLabels.get(this.displayedImagePlace));
					this.displayedImagePlace++;
				}
				else
				{
					row.add(Box.createHorizontalStrut(150));
				}
				row.add(Box.createHorizontalStrut(25));
			}
			this.displayedBox.add(row);
		}
<<<<<<< HEAD
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
=======
		this.displayedImagePlace = displayedImagePlace;
		this.add(this.displayedBox);
		this.revalidate();
		this.repaint();
>>>>>>> origin/master
	}
	
	/* displaySelectedImages - iterate through the images in a case file and display the 
	 * 				  		   specified ones on the screen
	 *      		imageNum - the image place holder that specifies which image you would like
	 *              		   to start at when displaying a screen of images
	 */
	private void displaySelectedImages()
	{
<<<<<<< HEAD
		selectedImagePlace = 0;
		
		for(int i = 0; i < 15; i++){
			try
			{
				this.selBox.setAlignmentX(CENTER_ALIGNMENT);
				selBox.add(this.selected.get(this.selectedImagePlace));
=======
		this.selectedImagePlace = 0;	
		this.selectedBox.setAlignmentX(CENTER_ALIGNMENT);
		for (int i = 0; i < 15; i++)
		{
			if (this.selectedImagePlace < this.selectedLabels.size())
			{
				selectedBox.add(this.selectedLabels.get(this.selectedImagePlace));
>>>>>>> origin/master
				selectedImagePlace++;
			}
		}
		this.displayedBox.add(this.selectedBox);
	}
	
	/* validateExtension - determines whether or not the extension in a given file name is that of a valid image (.png, .jpg, .jpeg)
	 *          fileName - the file name to check
	 */
	private boolean validateExtension(String fileName)
	{
		if (fileName.length() > 4)
		{
			String threeLetterExt = fileName.substring(fileName.length() - 4, fileName.length());
			String fourLetterExt = fileName.substring(fileName.length() - 5, fileName.length());
			if (threeLetterExt.equalsIgnoreCase(".png") || threeLetterExt.equalsIgnoreCase(".jpg") || fourLetterExt.equalsIgnoreCase(".jpeg"))
			{
				return true;
			}
		}
		return false;		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	private void initializedisplayImages(){
		this.constructLabel("Select the images you would like to import:");
		this.displayImages(0);
	}
	
	//break up into an initalize image function and display image func
	private void initializeSelectedImages(){
		this.constructLabel("Click to remove an image:");
		this.displaySelectedImages();
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
		
	}*/
		

}