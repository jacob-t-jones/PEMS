// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// SelectPanel.java

package gui.display.select;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.imgscalr.*;
import exceptions.*;
import gui.*;
import gui.components.img.*;
import gui.display.*;
import gui.display.editimg.*;
import tools.FileHandler.*;

public class SelectPanel extends JPanel implements ActionListener, MouseListener 
{

	private FrameManager manager;
	private ArrayList<ThumbnailImg> displayedThumbnails;
	private ArrayList<ThumbnailImg> selectedThumbnails;
	private Box mainContainer;
	private Box innerContainer;
	private Box leftContainer;
	private Box rightContainer;
	private Box displayedContainer;
	private Box displayedTopContainer;
	private Box selectedContainer;
	private Box buttonsContainer;
	private Img refreshImg;
	private JLabel instructionsLabel;
	private JLabel displayedTitleLabel;
	private JLabel selectedTitleLabel;
	private JButton loadNextButton;
	private JButton loadPrevButton;
	private JButton loadNextSelectedButton;
	private JButton loadPrevSelectedButton;
	private JButton finishButton;
	private String caseNum;
	private int displayedImagePlace;
	private int selectedImagePlace;

	public SelectPanel(FrameManager manager, String caseNum) 
	{
		this.manager = manager;
		this.caseNum = caseNum;
		this.displayedImagePlace = 0;
		this.selectedImagePlace = 0;
		this.displayedThumbnails = this.generateThumbnails();
		this.selectedThumbnails = new ArrayList<ThumbnailImg>();
		this.mainContainer = Box.createVerticalBox();
		this.innerContainer = Box.createHorizontalBox();
		this.leftContainer = Box.createVerticalBox();
		this.rightContainer = Box.createVerticalBox();
		this.displayedContainer = Box.createVerticalBox();
		this.displayedTopContainer = Box.createHorizontalBox();
		this.selectedContainer = Box.createVerticalBox();
		this.buttonsContainer = Box.createHorizontalBox();
		this.displayedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.selectedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.populateDisplayedTopContainer();
		this.refreshDisplayedThumbnails(0);
		this.refreshSelectedThumbnails(0);
		this.populateButtonsContainer();
		this.populateLeftContainer();
		this.populateRightContainer();
		this.populateInnerContainer();
		this.populateMainContainer();
		this.add(this.mainContainer);
		this.manager.getMainWindow().setResizable(true);
		this.manager.getMainWindow().setMaximized();
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.revalidate();
		this.repaint();
	}

	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *                 1. attempts to load the next fifteen images from the camera within "displayedContainer"
	 *                 2. attempts to load the previous fifteen images from the camera within "displayedContainer"
	 *                 3. displays a dialogue asking the user for his or her import preferences
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
			if (this.selectedThumbnails.isEmpty())
			{
				if (this.displayedThumbnails.isEmpty())
				{
					JOptionPane.showMessageDialog(this.manager.getMainWindow(), "Unable to locate any images on external drives. Please disconnect and reconnect all cameras and devices, restart the program, and try again.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(this.manager.getMainWindow(), "You must select at least one image to import into the newly created case!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				int selection = JOptionPane.showConfirmDialog(this.manager.getMainWindow(), "Would you like to delete the original copies of all imported files from the camera?", "Delete Selected Files", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (selection == JOptionPane.YES_OPTION)
				{
					this.attemptCopying(true);
				}
				else if (selection == JOptionPane.NO_OPTION)
				{
					this.attemptCopying(false);
				}
				else if (selection == JOptionPane.CANCEL_OPTION)
				{
					return;
				}
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

	/* mouseClicked - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	          e - the event in question
	 *              1. refreshes the thumbnails in "displayedContainer" by once again scanning peripheral devices for images files 
	 *              2. removes the source ThumbnailImg from "selectedThumbnails" and adds it to "displayedThumbnails"
	 *              3. removes the source ThumbnailImg from "displayedThumbnails" and adds it to "selectedThumbnails"
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if (e.getSource() == this.refreshImg)
		{
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			this.manager.getFileHandler().refreshPeripheralFiles();
			this.displayedThumbnails = this.generateThumbnails();
			for (int i = 0; i < this.selectedThumbnails.size(); i++)
			{
				ThumbnailImg currentSelectedThumbnail = this.selectedThumbnails.get(i);
				for (int j = 0; j < this.displayedThumbnails.size(); j++)
				{
					ThumbnailImg currentDisplayedThumbnail = this.displayedThumbnails.get(j);
					if (currentSelectedThumbnail.getFilePath().equalsIgnoreCase(currentDisplayedThumbnail.getFilePath()))
					{
						this.displayedThumbnails.remove(j);
					}
				}
			}
			this.refreshDisplayedThumbnails(0);
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		else if (this.selectedThumbnails.contains(e.getSource()))
		{
			this.displayedThumbnails.add((ThumbnailImg)e.getSource());
			this.selectedThumbnails.remove(e.getSource());
			this.refreshDisplayedThumbnails(this.displayedImagePlace);
			this.refreshSelectedThumbnails(this.selectedImagePlace);
		}
		else if (displayedThumbnails.contains(e.getSource()))
		{
			this.selectedThumbnails.add((ThumbnailImg)e.getSource());
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

	/* refreshDisplayedThumbnails - refreshes the ThumbnailImgs for images not yet selected by the user
	 *        displayedImagePlace - the index within "displayedThumbnails" of the first image to be displayed
	 */
	private void refreshDisplayedThumbnails(int displayedImagePlace)
	{
		this.displayedImagePlace = displayedImagePlace;
		this.displayedContainer.removeAll();
		this.displayedContainer.add(Box.createVerticalStrut(5));
		this.displayedContainer.add(this.displayedTopContainer);
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

	/* refreshSelectedThumbnails - refreshes the ThumbnailImgs placed in "selectedContainer" by the user
	 *        selectedImagePlace - the index within "selectedThumbnails" of the first image to be displayed
	 */
	private void refreshSelectedThumbnails(int selectedImagePlace)
	{
		this.selectedTitleLabel = ComponentGenerator.generateLabel("Selected Images", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
	    this.loadNextSelectedButton = ComponentGenerator.generateButton("Next", this, CENTER_ALIGNMENT);
		this.loadPrevSelectedButton = ComponentGenerator.generateButton("Previous", this, CENTER_ALIGNMENT);
		this.selectedImagePlace = selectedImagePlace;
		this.selectedContainer.removeAll();
		this.selectedContainer.add(this.selectedTitleLabel);
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
		this.selectedContainer.add(this.loadNextSelectedButton);
		this.selectedContainer.add(Box.createHorizontalStrut(150));
		this.selectedImagePlace = selectedImagePlace;
		this.revalidate();
		this.repaint();
	}
	
	/* populateDisplayedTopContainer() - fills the "displayedTopContainer" layout structure with the necessary components
	 */
	private void populateDisplayedTopContainer()
	{
		this.displayedTitleLabel = ComponentGenerator.generateLabel("Images Detected on External Devices", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		try 
		{
			this.refreshImg = ComponentGenerator.generateImg("resources/refresh.png");
			this.refreshImg.resizeImage(Scalr.Method.ULTRA_QUALITY, 30);
			this.refreshImg.addMouseListener(this);
			this.displayedTopContainer.add(Box.createHorizontalStrut(5));
			this.displayedTopContainer.add(this.refreshImg);
		} 
		catch (InvalidImgException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.displayedTopContainer.add(Box.createHorizontalGlue());
		this.displayedTopContainer.add(this.displayedTitleLabel);
		this.displayedTopContainer.add(Box.createHorizontalGlue());
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
	
	/* generateThumbnails - returns an ArrayList of ThumbnailImg objects generated from the peripheral image files detected by the global instance of FileHandler
	 */
	private ArrayList<ThumbnailImg> generateThumbnails()
	{
		ArrayList<ThumbnailImg> thumbnails = new ArrayList<ThumbnailImg>();
		for (int i = 0; i < this.manager.getFileHandler().getPeripheralFiles().size(); i++)
		{
			try 
			{
				ThumbnailImg newThumbnail = ComponentGenerator.generateThumbnailImg(this.manager.getFileHandler().getPeripheralFiles().get(i).getPath(), 120);
				//ThumbnailImg newThumbnail = ComponentGenerator.generateThumbnailImg("/Users/andrewrottier/Documents/Pictures/folder.png", 120);

				newThumbnail.addMouseListener(this);
				thumbnails.add(newThumbnail);
			} 
			catch (InvalidImgException e) 
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return thumbnails;
	}
	
	/* attemptCopying - attempts to copy the selected image files into the local file system, where they can be managed by the program
	 * 	       delete - boolean value indicating whether or not the user wishes to delete the original copies of the image files that he or she is importing
	 */
	private void attemptCopying(boolean delete)
	{
		CopyFilesResult result = this.manager.getFileHandler().copyFiles(delete, this.caseNum, this.selectedThumbnails);
		if (result == CopyFilesResult.COPY_FAILED)
		{
			JOptionPane.showMessageDialog(this.manager.getMainWindow(), "Importation of the selected image files has unexpectedly failed!\nPlease disconnect and reconnect all cameras and devices, restart the program, and try again.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			this.manager.getMainWindow().pushPanel(new EditImgPanel(this.manager, this.caseNum), "PEMS - Edit Photos");
		}
	}

}