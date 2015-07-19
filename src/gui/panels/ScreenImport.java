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

public class ScreenImport extends JPanel implements ActionListener, MouseListener
{

	private FrameManager manager;
	private ArrayList<Thumbnail> thumbnails;
	private ArrayList<JLabel> displayedLabels;
	private ArrayList<JLabel> selectedLabels;
	private Box mainContainer;
	private Box innerContainer;
	private Box leftContainer;
	private Box rightContainer;
	private Box displayedContainer;
	private Box selectedContainer;
	private Box buttonsContainer;
	private JLabel instructionsLabel;
	private JLabel displayedTitleLabel;
	private JLabel selectedTitleLabel;
	private JButton loadNextButton;
	private JButton loadPrevButton;
	private JButton loadNextSelectedButton;
	private JButton loadPrevSelectedButton;
	private JButton finishButton;
	private String directoryName;
	private int displayedImagePlace;
	private int selectedImagePlace;

	public ScreenImport(FrameManager manager, String filePath)
	{
		this.manager = manager;
		this.directoryName = "/Users/Jacob/Documents/Pics";
		this.displayedImagePlace = 0;
		this.selectedImagePlace = 0;
		this.thumbnails = this.getThumbnails();
		this.displayedLabels = this.getDisplayedLabels();
		this.selectedLabels = new ArrayList<JLabel>();
		this.mainContainer = Box.createVerticalBox();
		this.innerContainer = Box.createHorizontalBox();
		this.leftContainer = Box.createVerticalBox();
		this.rightContainer = Box.createVerticalBox();
		this.displayedContainer = Box.createVerticalBox();
		this.displayedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.selectedContainer = Box.createVerticalBox();
		this.selectedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.buttonsContainer = Box.createHorizontalBox();
		this.instructionsLabel = ComponentGenerator.generateLabel("Click on any of the images below to import them into the current case. Once selected, an image can be removed from the case by simply clicking on it again.", ComponentGenerator.STANDARD_TEXT_FONT_ITALIC, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.refreshDisplayedLabels(0);
		this.refreshSelectedLabels(0);
		this.populateButtonsContainer();
		this.leftContainer.add(this.displayedContainer);
		this.leftContainer.add(Box.createVerticalStrut(30));
		this.leftContainer.add(this.buttonsContainer);
		this.rightContainer.add(this.selectedContainer);
		this.innerContainer.add(this.leftContainer);
		this.innerContainer.add(Box.createHorizontalStrut(100));
		this.innerContainer.add(this.rightContainer);
		this.mainContainer.add(Box.createVerticalStrut(20));
		this.mainContainer.add(this.instructionsLabel);
		this.mainContainer.add(Box.createVerticalStrut(30));
		this.mainContainer.add(this.innerContainer);
		this.add(this.mainContainer);
		this.manager.setResizable(true);
		this.manager.maximizeFrame();
		this.revalidate();
		this.repaint();
	}
	
	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *                 1. attempts to load the next fifteen images from the camera within "displayedContainer"
	 *                 2. attempts to load the previous fifteen images from the camera within "displayedContainer"
	 *                 3. pushes the ScreenEdit JPanel into view
	 *                 4. attempts to load the next three selected images within "selectedContainer"
	 *                 5. attempts to load the previous three selected images within "selectedContainer"
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.loadNextButton)
		{
        	if (displayedImagePlace + 15 < displayedLabels.size())
        	{
        		refreshDisplayedLabels(displayedImagePlace + 15);
        	}
		}
		else if (e.getSource() == this.loadPrevButton)
		{
          	if (displayedImagePlace >= 15)
        	{
        		refreshDisplayedLabels(displayedImagePlace - 15);
        	}
		}
		else if (e.getSource() == this.finishButton)
		{
        	manager.pushPanel(new ScreenEdit(manager), "PEMS - Edit Photos");
		}
		else if (e.getSource() == this.loadNextSelectedButton)
		{
        	if (selectedImagePlace + 3 < selectedLabels.size())
        	{
        		refreshSelectedLabels(selectedImagePlace + 3);
        	}
		}
		else if (e.getSource() == this.loadPrevSelectedButton)
		{
        	if (selectedImagePlace >= 3)
        	{
        		refreshSelectedLabels(selectedImagePlace - 3);
        	}
		}
	}
	
	/* mouseClicked - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	          e - the event in question
	 *              1. removes the source JLabel from "selectedLabels" and adds it to "displayedLabels"
	 *              2. removes the source JLabel from "displayedLabels" and adds it to "selectedLabels"
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if (selectedLabels.contains(e.getSource()))
		{
			displayedLabels.add((JLabel)e.getSource());
			selectedLabels.remove(e.getSource());
			refreshDisplayedLabels(displayedImagePlace);
			refreshSelectedLabels(selectedImagePlace);
		}
		else if (displayedLabels.contains(e.getSource()))
		{
			selectedLabels.add((JLabel)e.getSource());
			displayedLabels.remove(e.getSource());
			refreshDisplayedLabels(displayedImagePlace);
			refreshSelectedLabels(selectedImagePlace);
		}
	}
	
	/* mousePressed - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	          e - the event in question
	 */
	public void mousePressed(MouseEvent e) 
	{

	}
	 
	/* mouseReleased - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	           e - the event in question
	 */
	public void mouseReleased(MouseEvent e)
	{

	}
	
	/* mouseEntered - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	          e - the event in question
	 */
	public void mouseEntered(MouseEvent e) 
	{

	}
	
	/* mouseExited - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	         e - the event in question
	 */
	public void mouseExited(MouseEvent e) 
	{

	}	
	
	/* getThumbnails - fills the "thumbnails" ArrayList by importing images from the camera into memory
	 */
	private ArrayList<Thumbnail> getThumbnails()
	{ 
		ArrayList<Thumbnail> thumbnailList = new ArrayList<Thumbnail>();
	    File directory = new File(this.directoryName);
		String[] fileNames = directory.list();
		for (int i = 0; i < fileNames.length; i++)
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
			JLabel newLabel = ComponentGenerator.generateLabel(ImageEditor.resizeImage(this.thumbnails.get(i).getImage(), 120), CENTER_ALIGNMENT);
			newLabel.addMouseListener(this);
			labelList.add(newLabel);
		}
		return labelList;
	}
	
	/* refreshDisplayedLabels - refreshes the JLabels for images not yet selected by the user
	 */
	private void refreshDisplayedLabels(int displayedImagePlace)
	{
		this.displayedTitleLabel = ComponentGenerator.generateLabel("Images Detected on Camera", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.displayedImagePlace = displayedImagePlace;
		this.displayedContainer.removeAll();
		this.displayedContainer.add(this.displayedTitleLabel);
		for (int i = 0; i < 3; i++)
		{
			Box row = Box.createHorizontalBox();
			for (int j = 0; j < 5; j++)
			{
				Box col = Box.createHorizontalBox();
				col.setMinimumSize(new Dimension(150, 150));
				col.setMaximumSize(new Dimension(150, 150));
				if (this.displayedImagePlace < this.displayedLabels.size())
				{
					col.add(Box.createHorizontalGlue());
					col.add(Box.createVerticalStrut(150));
					col.add(this.displayedLabels.get(this.displayedImagePlace));
					col.add(Box.createVerticalStrut(150));
					col.add(Box.createHorizontalGlue());
				}
				else
				{
					col.add(Box.createHorizontalGlue());
					col.add(Box.createVerticalStrut(150));
					col.add(Box.createHorizontalGlue());
				}
				row.add(col);
				this.displayedImagePlace++;
			}
			this.displayedContainer.add(row);
		}
		this.displayedContainer.add(Box.createHorizontalStrut(750));
		this.displayedImagePlace = displayedImagePlace;
		this.revalidate();
		this.repaint();
	}
	
	/* refreshSelectedLabels - refreshes the JLabels placed in "selectedContainer" by the user
	 */
	private void refreshSelectedLabels(int selectedImagePlace)
	{
		this.selectedTitleLabel = ComponentGenerator.generateLabel("Selected Images", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
	    this.loadNextSelectedButton = ComponentGenerator.generateButton("Next", this, CENTER_ALIGNMENT);
		this.loadPrevSelectedButton = ComponentGenerator.generateButton("Prev", this, CENTER_ALIGNMENT);
		this.selectedContainer.removeAll();
		this.selectedContainer.add(this.selectedTitleLabel);
		this.selectedImagePlace = selectedImagePlace;
		this.selectedContainer.add(this.loadPrevSelectedButton);
		for (int i = 0; i < 3; i++)
		{
			Box row = Box.createHorizontalBox();
			row.setMinimumSize(new Dimension(150, 150));
			row.setMaximumSize(new Dimension(150, 150));
			if (this.selectedImagePlace < this.selectedLabels.size())
			{
				row.add(Box.createHorizontalGlue());
				row.add(Box.createVerticalStrut(150));
				row.add(this.selectedLabels.get(this.selectedImagePlace));
				row.add(Box.createVerticalStrut(150));
				row.add(Box.createHorizontalGlue());
			}
			else
			{
				row.add(Box.createHorizontalGlue());
				row.add(Box.createVerticalStrut(150));
				row.add(Box.createHorizontalGlue());
			}
			this.selectedContainer.add(row);
			this.selectedImagePlace++;
		}
		this.selectedContainer.add(Box.createHorizontalStrut(150));
		this.selectedContainer.add(this.loadNextSelectedButton);
		this.selectedImagePlace = selectedImagePlace;
		this.revalidate();
		this.repaint();
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
	
	/* populateButtonsContainer - fills the "buttonsContainer" layout structure with the necessary components
	 */
	private void populateButtonsContainer()
	{
		this.loadNextButton = ComponentGenerator.generateButton("Load Next Images   >", this);
		this.loadPrevButton = ComponentGenerator.generateButton("<   Load Previous Images", this);
		this.finishButton = ComponentGenerator.generateButton("Finish Importing", this);
		this.buttonsContainer = Box.createHorizontalBox();
		this.buttonsContainer.setAlignmentX(CENTER_ALIGNMENT);
		this.buttonsContainer.add(this.loadPrevButton);
		this.buttonsContainer.add(Box.createHorizontalStrut(100));
		this.buttonsContainer.add(this.finishButton);
		this.buttonsContainer.add(Box.createHorizontalStrut(100));
		this.buttonsContainer.add(this.loadNextButton);
	}
		
}