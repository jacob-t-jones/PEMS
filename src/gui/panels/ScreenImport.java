// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenImport.java

package gui.panels;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import gui.*;
import tools.*;

public class ScreenImport extends JPanel implements ActionListener, MouseListener
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

	public ScreenImport(FrameManager manager, String caseNum)
	{
		this.manager = manager;
		this.caseNum = caseNum;
		this.directoryName = "/Users/Jacob/Documents/Pics";
		this.displayedImagePlace = 0;
		this.selectedImagePlace = 0;
		this.displayedThumbnails = this.getThumbnails();
		this.selectedThumbnails = new ArrayList<Thumbnail>();
		this.mainContainer = Box.createVerticalBox();
		this.innerContainer = Box.createHorizontalBox();
		this.leftContainer = Box.createVerticalBox();
		this.rightContainer = Box.createVerticalBox();
		this.displayedContainer = Box.createVerticalBox();
		this.selectedContainer = Box.createVerticalBox();
		this.buttonsContainer = Box.createHorizontalBox();
		this.displayedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.selectedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.refreshDisplayedThumbnails(0);
		this.refreshSelectedThumbnails(0);
		this.populateButtonsContainer();
		this.populateLeftContainer();
		this.populateRightContainer();
		this.populateInnerContainer();
		this.populateMainContainer();
		this.add(this.mainContainer);
		this.manager.setResizable(true);
		this.manager.maximizeFrame();
		this.manager.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.revalidate();
		this.repaint();
	}
	
	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *                 1. attempts to load the next fifteen images from the camera within "displayedContainer"
	 *                 2. attempts to load the previous fifteen images from the camera within "displayedContainer"
	 *                 3. displays a dialogue asking the user for his or her import preferences, copies files to proper directories, and pushes ScreenEdit
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
			this.manager.displayDeleteImportsDialogue(this);
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
	
	/* mousePressed - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	          e - the event in question
	 */
	public void mousePressed(MouseEvent e) 
	{
		return;
	}
	 
	/* mouseReleased - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	           e - the event in question
	 */
	public void mouseReleased(MouseEvent e)
	{
		return;
	}
	
	/* mouseEntered - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	          e - the event in question
	 */
	public void mouseEntered(MouseEvent e) 
	{
		return;
	}
	
	/* mouseExited - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	         e - the event in question
	 */
	public void mouseExited(MouseEvent e) 
	{
		return;
	}	
	
	/* deleteOptionSelected - called when the user chooses an option on ScreenDeleteImportsDialogue
	 * 				 delete - boolean value indicating whether or not imported files should be deleted from the device
	 */
	public void deleteOptionSelected(boolean delete)
	{
		if (this.copyFiles(delete))
		{
			this.manager.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			this.manager.pushPanel(new ScreenEdit(manager, caseNum), "PEMS - Edit Photos");
		}
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
			String currentFileName = fileNames[i].substring(0, fileNames[i].indexOf('.')).toLowerCase();
			String currentExtension = fileNames[i].substring(fileNames[i].indexOf('.'), fileNames[i].length()).toLowerCase();
			if (currentExtension.equalsIgnoreCase(".png") || currentExtension.equalsIgnoreCase(".jpg") || currentExtension.equalsIgnoreCase(".jpeg"))
			{
				BufferedImage currentImage = null;
				String currentPath = this.directoryName + "/" + fileNames[i];
			    try 
			    {   
			    	 currentImage = ImageIO.read(new File(currentPath));
			    }
			    catch (IOException e)
			    {
			    	System.out.println("Error - Unable to read image into memory");
			    	e.printStackTrace();
				    return null;
			    }	    	
			    Thumbnail currentThumb = ComponentGenerator.generateThumbnail(ImageEditor.resizeThumbnail(currentImage, 120), currentPath, currentFileName, currentExtension);
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
	
	/* refreshSelectedLabels - refreshes the Thumbnails placed in "selectedContainer" by the user
	 */
	private void refreshSelectedThumbnails(int selectedImagePlace)
	{
		this.selectedTitleLabel = ComponentGenerator.generateLabel("Selected Images", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
	    this.loadNextSelectedButton = ComponentGenerator.generateButton("Next", this, CENTER_ALIGNMENT);
		this.loadPrevSelectedButton = ComponentGenerator.generateButton("Previous", this, CENTER_ALIGNMENT);
		this.selectedContainer.removeAll();
		this.selectedContainer.add(this.selectedTitleLabel);
		this.selectedContainer.add(this.loadPrevSelectedButton);
		this.selectedImagePlace = selectedImagePlace;
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
	
	/* populateLeftContainer - adds "displayedContainer" and "buttonsContainer" to "leftContainer"
	 */
	private void populateLeftContainer()
	{
		this.leftContainer.add(this.displayedContainer);
		this.leftContainer.add(Box.createVerticalStrut(30));
		this.leftContainer.add(this.buttonsContainer);
	}
	
	/* populateRightContainer - adds "selectedContainer" to "rightContainer"
	 */
	private void populateRightContainer()
	{
		this.rightContainer.add(this.selectedContainer);
	}
	
	/* populateInnerContainer - adds "leftContainer" and "rightContainer" to "innerContainer"
	 */
	private void populateInnerContainer()
	{
		this.innerContainer.add(this.leftContainer);
		this.innerContainer.add(Box.createHorizontalStrut(100));
		this.innerContainer.add(this.rightContainer);
	}
	
	/* populateMainContainer - adds "instructionsLabel" and "innerContainer" to "mainContainer"
	 */
	private void populateMainContainer()
	{
		this.instructionsLabel = ComponentGenerator.generateLabel("Click on any of the images below to import them into the current case. Selected images will appear on the right, and can be removed from the case by simply clicking on them again.", ComponentGenerator.STANDARD_TEXT_FONT_ITALIC, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.mainContainer.add(Box.createVerticalStrut(20));
		this.mainContainer.add(this.instructionsLabel);
		this.mainContainer.add(Box.createVerticalStrut(30));
		this.mainContainer.add(this.innerContainer);
	}
	
	/* copyFiles - copies files from the camera to the "cases" and "backups" folders, and returns a boolean value determined by whether or not the copy was successful
	 *	  delete - boolean value that determines whether original files should be deleted from the device as they are imported
	 */
	private boolean copyFiles(boolean delete)
	{
    	for (int i = 0; i < this.selectedThumbnails.size(); i++)
    	{
			Path currentPath = Paths.get(this.directoryName + "/" + this.selectedThumbnails.get(i).getFileName() + this.selectedThumbnails.get(i).getFileExt());
			Path casesPath = Paths.get("cases/" + this.caseNum + "/" + this.selectedThumbnails.get(i).getFileName() + this.selectedThumbnails.get(i).getFileExt());
			Path backupsPath = Paths.get("backups/" + this.caseNum + "/" + this.selectedThumbnails.get(i).getFileName() + this.selectedThumbnails.get(i).getFileExt());
			try 
			{
				Files.copy(currentPath, casesPath, StandardCopyOption.REPLACE_EXISTING);
				Files.copy(currentPath, backupsPath, StandardCopyOption.REPLACE_EXISTING);
				if (delete)
				{
					Files.delete(currentPath);
				}
			} 
			catch (IOException e1) 
			{
				System.out.println("Error - Unable to copy image files to new directory");
				e1.printStackTrace();
				return false;
			}
    	}
    	return true;
	}
		
}