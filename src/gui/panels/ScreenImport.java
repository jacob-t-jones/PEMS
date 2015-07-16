// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenImport.java

package gui.panels;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.Path;
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
	private ActionListener loadNextAction;
	private ActionListener loadPrevAction;
	private ActionListener continueAction;
	private Box imageContainer;
	private Box selectedContainer; 
	private Box buttonsContainer;
	private JLabel instructionsLabel;
	private JButton loadNextButton;
	private JButton loadPrevButton;
	private JButton continueButton;
	private String directoryName;
	private int displayedImagePlace;
	private int selectedImagePlace;

<<<<<<< HEAD
	public ScreenImport(FrameManager manager, Path filePath)
=======
	public ScreenImport(FrameManager manager, String filePath)
>>>>>>> origin/master
	{
		this.manager = manager;
		this.directoryName = "/Users/andrewrottier/Documents/Pictures/";
		this.displayedImagePlace = 0;
		this.selectedImagePlace = 0;
		this.thumbnails = this.getThumbnails();
		this.displayedLabels = this.getDisplayedLabels();
		this.selectedLabels = new ArrayList<JLabel>();
		this.imageContainer = Box.createVerticalBox();
		this.imageContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.selectedContainer = Box.createHorizontalBox();
		this.selectedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.refreshDisplayedLabels(0);
		this.refreshSelectedLabels();
		this.generateListeners();
		this.populateButtonsContainer();
		this.manager.setResizable(true);
		this.manager.maximizeFrame();
	}
	
	private void generateListeners()
	{
		this.loadNextAction = new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	if (displayedImagePlace < displayedLabels.size())
            	{
            		refreshDisplayedLabels(displayedImagePlace + 15);
            	}
            }
		};
		this.loadPrevAction = new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	if (displayedImagePlace > 15)
            	{
            		refreshDisplayedLabels(displayedImagePlace - 15);
            	}
            }
		};
		this.continueAction = new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	manager.pushPanel(new ScreenEdit(manager, selectedLabels), "PEMS - Edit Photos");
            }
		};
	}
	
	private MouseListener generateLabelSelectionListener(final JLabel selectedLabel)
	{
		MouseListener labelSelectionListener = new MouseListener()
		{
			public void mouseClicked(MouseEvent e) 
			{
				if (selectedLabels.contains(selectedLabel))
				{
					displayedLabels.add(selectedLabel);
					selectedLabels.remove(selectedLabel);
					refreshDisplayedLabels(displayedImagePlace);
					refreshSelectedLabels();
				}
				else if (displayedLabels.contains(selectedLabel))
				{
					selectedLabels.add(selectedLabel);
					displayedLabels.remove(selectedLabel);
					refreshDisplayedLabels(displayedImagePlace);
					refreshSelectedLabels();
				}
			}
			public void mousePressed(MouseEvent e) 
			{

			}
			public void mouseReleased(MouseEvent e)
			{

			}
			public void mouseEntered(MouseEvent e) 
			{

			}
			public void mouseExited(MouseEvent e) 
			{

			}	
		};
		return labelSelectionListener;
	}
	
	/* getThumbnails - fills "thumbnails" by importing images into memory
	 */
<<<<<<< HEAD
=======

	private ArrayList<Thumbnail> getImages()
	{
		ArrayList<Thumbnail> imageList = new ArrayList<Thumbnail>();
	    File imageDirectory = new File(this.directoryName);
		String[] imageFileNames = imageDirectory.list();
		BufferedImage currentImage = new BufferedImage(12, 12, 12);
		String currentPath = new String();
		for (int i = 0; i < imageFileNames.length; i++){
			
		}
	}
	
>>>>>>> origin/master
	private ArrayList<Thumbnail> getThumbnails()
	{ 
		ArrayList<Thumbnail> thumbnailList = new ArrayList<Thumbnail>();
	    File directory = new File(this.directoryName);
		String[] fileNames = directory.list();
		for (int i = 0; i < fileNames.length; i++)
<<<<<<< HEAD
=======

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
	
	/* getDisplayedLabels - fills "displayedLabels" by creating a JLabel for each entry in the "thumbnails" ArrayList
	 */
	private ArrayList<JLabel> getDisplayedLabels()
	{
		ArrayList<JLabel> labelList = new ArrayList<JLabel>();
		for (int i = 0; i < this.thumbnails.size(); i++)
		{
			JLabel newLabel = ComponentGenerator.generateLabel(ImageEditor.resizeImage(this.thumbnails.get(i).getImage(), 200), CENTER_ALIGNMENT);
			newLabel.addMouseListener(generateLabelSelectionListener(newLabel));
			labelList.add(newLabel);
		}
		return labelList;
	}
<<<<<<< HEAD
	
	/* refreshDisplayedLabels - refreshes the JLabels for images not yet selected by the user
=======


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
	
	/* refreshDisplayedLabels - refreshes the JLabels displayed on the screen
>>>>>>> origin/master
	 */
	private void refreshDisplayedLabels(int displayedImagePlace)
	{
		this.imageContainer.removeAll();
		this.remove(this.imageContainer);
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
			this.imageContainer.add(row);
		}
<<<<<<< HEAD
=======

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

>>>>>>> origin/master
		this.displayedImagePlace = displayedImagePlace;
		this.add(this.imageContainer);
		this.revalidate();
		this.repaint();
<<<<<<< HEAD
=======

>>>>>>> origin/master
	}
	
	/* refreshSelectedLabels - refreshes the JLabels placed in "selectedContainer" by the user
	 */
	private void refreshSelectedLabels()
	{
<<<<<<< HEAD
=======

		selectedImagePlace = 0;
		
		for(int i = 0; i < 15; i++){
			try
			{
				this.selBox.setAlignmentX(CENTER_ALIGNMENT);
				selBox.add(this.selected.get(this.selectedImagePlace));

>>>>>>> origin/master
		this.selectedImagePlace = 0;	
		this.selectedContainer.setAlignmentX(CENTER_ALIGNMENT);
		for (int i = 0; i < 15; i++)
		{
			if (this.selectedImagePlace < this.selectedLabels.size())
			{
<<<<<<< HEAD
				selectedContainer.add(this.selectedLabels.get(this.selectedImagePlace));
=======
				selectedBox.add(this.selectedLabels.get(this.selectedImagePlace));
>>>>>>> origin/master
				selectedImagePlace++;
			}
		}
		this.imageContainer.add(this.selectedContainer);
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
	
	/* populateButtonsBox - fills the "buttonsBox" layout structure with the necessary components
	 */
	private void populateButtonsContainer()
	{
		this.loadNextButton = ComponentGenerator.generateButton("Load More Images", this.loadNextAction);
		this.loadPrevButton = ComponentGenerator.generateButton("Load Previous Images", this.loadPrevAction);
		this.continueButton = ComponentGenerator.generateButton("Finish", this.continueAction);
		this.buttonsContainer = Box.createHorizontalBox();
		this.buttonsContainer.setAlignmentX(CENTER_ALIGNMENT);
		this.buttonsContainer.add(Box.createVerticalStrut(100));
		this.buttonsContainer.add(this.loadNextButton);
		this.buttonsContainer.add(Box.createVerticalStrut(100));
		this.buttonsContainer.add(this.continueButton);
		this.buttonsContainer.add(Box.createVerticalStrut(100));
		this.buttonsContainer.add(this.loadPrevButton);
		this.add(this.buttonsContainer);
		this.buttonsContainer.setLocation(600, 700);
	}
		
}