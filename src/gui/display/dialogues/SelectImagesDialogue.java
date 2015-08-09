// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// SelectImagesDialogue.java

package gui.display.dialogues;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import exceptions.*;
import gui.*;
import gui.components.img.*;
import gui.display.*;

public class SelectImagesDialogue extends JPanel implements ActionListener, MouseListener
{
	
	private FrameManager manager;
	private ArrayList<ThumbnailImg> displayedThumbnails;
	private ArrayList<ThumbnailImg> selectedThumbnails;
	private Box container;
	private Box buttonsContainer;
	private Box thumbnailContainer;
	private JLabel titleLabel;
	private JButton nextButton;
	private JButton prevButton;
	private JButton continueButton;
	private String caseNum;
	private int thumbnailPlace;
	
	public SelectImagesDialogue(FrameManager manager, String caseNum)
	{
		this.manager = manager;
		this.caseNum = caseNum;
		this.thumbnailPlace = 0;
		this.displayedThumbnails = this.generateThumbnails();
		this.selectedThumbnails = new ArrayList<ThumbnailImg>();
		this.container = Box.createVerticalBox();
		this.buttonsContainer = Box.createHorizontalBox();
		this.thumbnailContainer = Box.createVerticalBox();
		this.populateButtonsContainer();
		this.refreshThumbnailContainer(0);
		this.populateContainer();
		this.add(this.container);
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.revalidate();
		this.repaint();
	}
	
	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *                 1. attempts to load the next six images from the case within "thumbnailContainer"
	 *                 2. attempts to load the previous six images from the case within "thumbnailContainer"
	 *                 3. attempts to push PrintSetUpDialogue and displays an error message if this fails
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.nextButton)
		{
			if (this.thumbnailPlace + 6 < this.displayedThumbnails.size())
			{
				this.refreshThumbnailContainer(this.thumbnailPlace + 6);
			}
		}
		else if (e.getSource() == this.prevButton)
		{
			if (this.thumbnailPlace >= 6)
			{
				this.refreshThumbnailContainer(this.thumbnailPlace - 6);
			}
		}
		else if (e.getSource() == this.continueButton)
		{
			if (this.selectedThumbnails.size() > 0)
			{
				this.manager.closeDialogue();
				this.manager.openDialogue("Print Setup", new PrintSetUpDialogue(this.manager, this.selectedThumbnails), 50, 75);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "You have not selected any images to print!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/* mouseClicked - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	          e - the event in question
	 *              1. if the clicked on thumbnail is selected, it is removed from "selectedThumbnails"
	 *              2. if the clicked on thumbnail is not selected, it is added to "selectedThumbnails"
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if (this.selectedThumbnails.contains(e.getSource()))
		{
			this.selectedThumbnails.remove((ThumbnailImg)e.getSource());
		}
		else if (this.displayedThumbnails.contains(e.getSource()))
		{
			this.selectedThumbnails.add((ThumbnailImg)e.getSource());
		}
		this.refreshThumbnailContainer(this.thumbnailPlace);
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
	
	/* generateThumbnails - returns an ArrayList of ThumbnailImg objects representing all of the images contained within the currently selected case
	 */
	private ArrayList<ThumbnailImg> generateThumbnails()
	{
		ArrayList<ThumbnailImg> thumbnailList = new ArrayList<ThumbnailImg>();
	    File directory = new File("cases" + "/" + this.caseNum + "/");
		for (int i = 0; i < directory.listFiles().length; i++)
		{
			String currentName = directory.listFiles()[i].getName();
			String currentExtension = currentName.substring(currentName.indexOf('.')).toLowerCase();
			if ((currentExtension.equalsIgnoreCase(".png") || currentExtension.equalsIgnoreCase(".jpg") || currentExtension.equalsIgnoreCase(".jpeg")))
			{ 
				try 
				{
					ThumbnailImg currentThumbnail = ComponentGenerator.generateThumbnailImg(directory.listFiles()[i].getPath(), 140, CENTER_ALIGNMENT);
				    currentThumbnail.addMouseListener(this);
				    thumbnailList.add(currentThumbnail);
				} 
				catch (InvalidImgException e) 
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	    return thumbnailList;
	}
	
	/* refreshThumbnailContainer - refreshes the thumbnails displayed on the screen
	 *            thumbnailPlace - the index within "displayedThumbnails" at which we should begin 
	 */
	private void refreshThumbnailContainer(int thumbnailPlace)
	{
		this.thumbnailPlace = thumbnailPlace;
		this.thumbnailContainer.removeAll();
		this.thumbnailContainer.add(Box.createVerticalStrut(5));;
		for (int i = 0; i < 2; i++)
		{
			Box row = Box.createHorizontalBox();
			for (int j = 0; j < 3; j++)
			{
				Box col = Box.createHorizontalBox();
				col.setMinimumSize(new Dimension(150, 150));
				col.setMaximumSize(new Dimension(150, 150));
				if (this.thumbnailPlace < this.displayedThumbnails.size())
				{
					col.add(Box.createHorizontalGlue());
					col.add(Box.createVerticalStrut(150));
					col.add(this.displayedThumbnails.get(this.thumbnailPlace));
					col.add(Box.createVerticalStrut(150));
					col.add(Box.createHorizontalGlue());
					if (this.selectedThumbnails.contains(this.displayedThumbnails.get(this.thumbnailPlace)))
					{
						col.setBackground(ComponentGenerator.SELECTED_THUMBNAIL_BG_COLOR);
						col.setOpaque(true);
					}
				}
				else
				{
					col.add(Box.createHorizontalGlue());
					col.add(Box.createVerticalStrut(150));
					col.add(Box.createHorizontalGlue());
				}
				row.add(col);
				this.thumbnailPlace++;
			}
			this.thumbnailContainer.add(row);
		}
		this.thumbnailContainer.add(Box.createHorizontalStrut(450));
		this.thumbnailPlace = thumbnailPlace;
		this.revalidate();
		this.repaint();
	}
	
	/* populateButtonsContainer - populates "buttonsContainer" with the necessary components
	 */
	private void populateButtonsContainer()
	{
		this.nextButton = ComponentGenerator.generateButton("Next >", this, CENTER_ALIGNMENT);
		this.prevButton = ComponentGenerator.generateButton("< Prev", this, CENTER_ALIGNMENT);
		this.buttonsContainer.add(this.prevButton);
		this.buttonsContainer.add(Box.createHorizontalStrut(250));
		this.buttonsContainer.add(this.nextButton);
	}
	
	/* populateContainer - populates "container" with the necessary components
	 */
	private void populateContainer()
	{
		this.titleLabel = ComponentGenerator.generateLabel("Please select all of the images you would like to print:", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.continueButton = ComponentGenerator.generateButton("Continue", this, CENTER_ALIGNMENT);
		this.container.add(Box.createVerticalStrut(5));
		this.container.add(this.titleLabel);
		this.container.add(Box.createVerticalStrut(15));
		this.container.add(this.buttonsContainer);
		this.container.add(this.thumbnailContainer);
		this.container.add(Box.createVerticalStrut(5));
		this.container.add(this.continueButton);
	}

}