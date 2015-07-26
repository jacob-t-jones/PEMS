// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenStart.java
package gui.display.print;

import gui.*;
import gui.display.FrameManager;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.ImageEditor;

public class PrintPanel extends JPanel implements ActionListener, MouseListener
{
	
	private FrameManager manager;
	private ArrayList<Thumbnail> displayedThumbnails;
	private ArrayList<Thumbnail> selectedThumbnails;
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
	private String caseNum;
	private String directoryName;
	private int displayedImagePlace;
	private int selectedImagePlace;
	
	
	public PrintPanel(FrameManager manager, String caseNum) 
	{
		this.manager = manager;
		this.caseNum = caseNum;
		this.directoryName = "/Users/andrewrottier/Documents/Pictures/CrimePhotos/";
		this.displayedImagePlace = 0;
		this.selectedImagePlace = 0;
		this.displayedThumbnails = this.getThumbnails(); ////
		this.selectedThumbnails = new ArrayList<Thumbnail>();
		this.mainContainer = Box.createVerticalBox();
		this.innerContainer = Box.createHorizontalBox();
		this.leftContainer = Box.createVerticalBox();
		this.rightContainer = Box.createVerticalBox();
		this.displayedContainer = Box.createVerticalBox();
		this.displayedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.selectedContainer = Box.createVerticalBox();
		this.selectedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.buttonsContainer = Box.createHorizontalBox();
		this.instructionsLabel = ComponentGenerator.generateLabel("Select the images you would like to print. Once selected, an image can be removed from the case by simply clicking on it again in the selected box.", ComponentGenerator.STANDARD_TEXT_FONT_ITALIC, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.refreshDisplayedThumbnails(0);
		this.refreshSelectedThumbnails(0);
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
		this.manager.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.revalidate();
		this.repaint();
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
				//Check this line of code
				String currentThumbExt = fileNames[i].substring(fileNames[i].length()-3, fileNames[i].length());
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
			    Thumbnail currentThumb = ComponentGenerator.generateThumbnail(ImageEditor.resizeThumbnail(currentImage, 120), currentLocation, currentFileName, currentThumbExt);
			    currentThumb.addMouseListener(this);
			    thumbnailList.add(currentThumb);
			} 
		}
	    return thumbnailList;
	}



	
	
	/* refreshDisplayedThumbnails - refreshes the Thumbnails for images not yet selected by the user
	 */
	private void refreshDisplayedThumbnails(int displayedImagePlace)
	{
		this.displayedTitleLabel = ComponentGenerator.generateLabel("Images in case file", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
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
				if (this.displayedImagePlace < this.displayedThumbnails.size())
				{
					col.add(Box.createHorizontalGlue());
					col.add(Box.createVerticalStrut(150));
					col.add(this.displayedThumbnails.get(this.displayedImagePlace));
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
	
	/* refreshSelectedLabels - refreshes the Thumbnails placed in "selectedContainer" by the user
	 */
	private void refreshSelectedThumbnails(int selectedImagePlace)
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
			if (this.selectedImagePlace < this.selectedThumbnails.size())
			{
				row.add(Box.createHorizontalGlue());
				row.add(Box.createVerticalStrut(150));
				row.add(this.selectedThumbnails.get(this.selectedImagePlace));
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
	
	
	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *                 1. attempts to load the next fifteen images from the camera within "displayedContainer"
	 *                 2. attempts to load the previous fifteen images from the camera within "displayedContainer"
	 *                 3. pushes the ScreenEdit JPanel into view, copies imported images to the proper case folder
	 *                 4. attempts to load the next three selected images within "selectedContainer"
	 *                 5. attempts to load the previous three selected images within "selectedContainer"
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.loadNextButton)
		{
        	if (this.displayedImagePlace + 15 < this.displayedThumbnails.size())
        	{
        		this.refreshDisplayedThumbnails(this.displayedImagePlace + 15);
        	}
		}
		else if (e.getSource() == this.loadPrevButton)
		{
          	if (this.displayedImagePlace >= 15)
        	{
        		this.refreshDisplayedThumbnails(this.displayedImagePlace - 15);
        	}
		}
		else if (e.getSource() == this.finishButton)
		{
    		//this.manager.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        	//OLD METHOD TO DO ONE PIC PER PAGE
    		//this.printImages();
        	//this.manager.pushPanel(new ScreenFinish(manager, caseNum), "PEMS - Finish");
			try {
				this.manager.pushPanel(new PrintSetUpPanel(this.manager, selectedThumbnails), "PEMS - PDF generator");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (e.getSource() == this.loadNextSelectedButton)
		{
        	if (this.selectedImagePlace + 3 < this.selectedThumbnails.size())
        	{
        		this.refreshSelectedThumbnails(this.selectedImagePlace + 3);
        	}
		}
		else if (e.getSource() == this.loadPrevSelectedButton)
		{
        	if (this.selectedImagePlace >= 3)
        	{
        		this.refreshSelectedThumbnails(this.selectedImagePlace - 3);
        	}
		}
	}
	
	/* populateButtonsContainer - fills the "buttonsContainer" layout structure with the necessary components
	 */
	private void populateButtonsContainer()
	{
		this.loadNextButton = ComponentGenerator.generateButton("Load Next Images   >", this);
		this.loadPrevButton = ComponentGenerator.generateButton("<   Load Previous Images", this);
		this.finishButton = ComponentGenerator.generateButton("Print Selected", this);
		this.buttonsContainer = Box.createHorizontalBox();
		this.buttonsContainer.setAlignmentX(CENTER_ALIGNMENT);
		this.buttonsContainer.add(this.loadPrevButton);
		this.buttonsContainer.add(Box.createHorizontalStrut(100));
		this.buttonsContainer.add(this.finishButton);
		this.buttonsContainer.add(Box.createHorizontalStrut(100));
		this.buttonsContainer.add(this.loadNextButton);
	}
	
	/* mouseClicked - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	          e - the event in question
	 *              1. removes the source Thumbnail from "selectedThumbnails" and adds it to "displayedThumbnails"
	 *              2. removes the source Thumbnail from "displayedThumbnails" and adds it to "selectedThumbnails"
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if (this.selectedThumbnails.contains(e.getSource()))
		{
			this.displayedThumbnails.add((Thumbnail)e.getSource());
			this.selectedThumbnails.remove(e.getSource());
			this.refreshDisplayedThumbnails(this.displayedImagePlace);
			this.refreshSelectedThumbnails(this.selectedImagePlace);
		}
		else if (displayedThumbnails.contains(e.getSource()))
		{
			this.selectedThumbnails.add((Thumbnail)e.getSource());
			this.displayedThumbnails.remove(e.getSource());
			this.refreshDisplayedThumbnails(this.displayedImagePlace);
			this.refreshSelectedThumbnails(this.selectedImagePlace);
		}
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void printImages() {
	    //The desktop api can help calling other applications in our machine
	    //and also many other features...
	    Desktop desktop = Desktop.getDesktop();
	    for(int i = 0; i < selectedThumbnails.size(); i++)
	    {
	    	try 
	    	{
	    		desktop.print(new File(selectedThumbnails.get(i).getFilePath()));
	    	} 
	    	catch (IOException e) 
	    	{   
	    		System.out.println("error - file, " + selectedThumbnails.get(i).getFilePath() + ", could not be printed.");
	    		e.printStackTrace();
	    	}
	    }
	    
	}

}
