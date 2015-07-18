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
	private ActionListener loadNextAction;
	private ActionListener loadPrevAction;
	private ActionListener continueAction;
	private ActionListener loadNextSelectedAction;
	private ActionListener loadPrevSelectedAction;
	private Box mainContainer;
	private Box leftContainer;
	private Box rightContainer;
	private Box displayedContainer;
	private Box selectedContainer;
	private Box buttonsContainer;
	private JLabel instructionsLabel;
	private JButton loadNextButton;
	private JButton loadPrevButton;
	private JButton loadNextSelectedButton;
	private JButton loadPrevSelectedButton;
	private JButton continueButton;
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
		this.mainContainer = Box.createHorizontalBox();
		this.leftContainer = Box.createVerticalBox();
		this.rightContainer = Box.createVerticalBox();
		this.displayedContainer = Box.createVerticalBox();
		this.displayedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.selectedContainer = Box.createVerticalBox();
		this.selectedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.buttonsContainer = Box.createHorizontalBox();
		this.generateListeners();
		this.refreshDisplayedLabels(0);
		this.refreshSelectedLabels(0);
		this.populateButtonsContainer();
		this.leftContainer.add(this.displayedContainer);
		this.leftContainer.add(Box.createVerticalStrut(30));
		this.leftContainer.add(this.buttonsContainer);
		this.rightContainer.add(this.selectedContainer);
		this.mainContainer.add(this.leftContainer);
		this.mainContainer.add(Box.createHorizontalStrut(100));
		this.mainContainer.add(this.rightContainer);
		this.add(this.mainContainer);
		this.manager.setResizable(true);
		this.manager.maximizeFrame();
		this.revalidate();
		this.repaint();
	}
	
	private void generateListeners()
	{
		this.loadNextAction = new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	if (displayedImagePlace + 15 < displayedLabels.size())
            	{
            		System.out.println(displayedImagePlace);
            		System.out.println(displayedLabels.size());
            		refreshDisplayedLabels(displayedImagePlace + 15);
            	}
            }
		};
		this.loadPrevAction = new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	if (displayedImagePlace >= 15)
            	{
            		refreshDisplayedLabels(displayedImagePlace - 15);
            	}
            }
		};
		this.continueAction = new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	manager.pushPanel(new ScreenEdit(manager, thumbnails, selectedLabels), "PEMS - Edit Photos");
            }
		};
		this.loadNextSelectedAction = new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	if (selectedImagePlace + 3 < selectedLabels.size())
            	{
            		refreshSelectedLabels(selectedImagePlace + 3);
            	}
            }
		};
		this.loadPrevSelectedAction = new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	if (selectedImagePlace >= 3)
            	{
            		refreshSelectedLabels(selectedImagePlace - 3);
            	}
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
					refreshSelectedLabels(selectedImagePlace);
				}
				else if (displayedLabels.contains(selectedLabel))
				{
					selectedLabels.add(selectedLabel);
					displayedLabels.remove(selectedLabel);
					refreshDisplayedLabels(displayedImagePlace);
					refreshSelectedLabels(selectedImagePlace);
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
			newLabel.addMouseListener(generateLabelSelectionListener(newLabel));
			labelList.add(newLabel);
		}
		return labelList;
	}
	
	/* refreshDisplayedLabels - refreshes the JLabels for images not yet selected by the user
	 */
	private void refreshDisplayedLabels(int displayedImagePlace)
	{
		this.instructionsLabel = ComponentGenerator.generateLabel("Images Detected on Camera", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.displayedImagePlace = displayedImagePlace;
		this.displayedContainer.removeAll();
		this.displayedContainer.add(this.instructionsLabel);
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
	    this.loadNextSelectedButton = ComponentGenerator.generateButton("Next", this.loadNextSelectedAction, CENTER_ALIGNMENT);
		this.loadPrevSelectedButton = ComponentGenerator.generateButton("Prev", this.loadPrevSelectedAction, CENTER_ALIGNMENT);
		this.selectedContainer.removeAll();
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
		this.loadNextButton = ComponentGenerator.generateButton("Load More Images", this.loadNextAction);
		this.loadPrevButton = ComponentGenerator.generateButton("Load Previous Images", this.loadPrevAction);
		this.continueButton = ComponentGenerator.generateButton("Finish", this.continueAction);
		this.buttonsContainer = Box.createHorizontalBox();
		this.buttonsContainer.setAlignmentX(CENTER_ALIGNMENT);
		this.buttonsContainer.add(this.loadPrevButton);
		this.buttonsContainer.add(Box.createHorizontalStrut(100));
		this.buttonsContainer.add(this.continueButton);
		this.buttonsContainer.add(Box.createHorizontalStrut(100));
		this.buttonsContainer.add(this.loadNextButton);
	}
		
}