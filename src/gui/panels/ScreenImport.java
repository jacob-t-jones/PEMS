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
	private Box imageContainer;
	private Box displayedContainer;
	private Box selectedContainer; 
	private Box buttonsContainer;
	private JButton loadNextButton;
	private JButton loadPrevButton;
	private JButton continueButton;
	private String directoryName;
	private int displayedImagePlace;
	private int selectedImagePlace;

	public ScreenImport(FrameManager manager, String filePath)
	{
		this.manager = manager;
		this.directoryName = filePath;
		this.displayedImagePlace = 0;
		this.selectedImagePlace = 0;
		this.thumbnails = this.getThumbnails();
		this.displayedLabels = this.getDisplayedLabels();
		this.selectedLabels = new ArrayList<JLabel>();
		this.imageContainer = Box.createHorizontalBox();
		this.displayedContainer = Box.createVerticalBox();
		this.displayedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.selectedContainer = Box.createVerticalBox();
		this.selectedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.refreshDisplayedLabels(0);
		this.refreshSelectedLabels();
		this.generateListeners();
		this.populateButtonsContainer();
		this.add(this.imageContainer);
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
			JLabel newLabel = ComponentGenerator.generateLabel(ImageEditor.resizeImage(this.thumbnails.get(i).getImage(), 200), CENTER_ALIGNMENT);
			newLabel.addMouseListener(generateLabelSelectionListener(newLabel));
			labelList.add(newLabel);
		}
		return labelList;
	}
	
	/* refreshDisplayedLabels - refreshes the JLabels for images not yet selected by the user
	 */
	private void refreshDisplayedLabels(int displayedImagePlace)
	{
		this.displayedContainer.removeAll();
		this.remove(this.displayedContainer);
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
			this.displayedContainer.add(row);
		}
		this.displayedImagePlace = displayedImagePlace;
		this.imageContainer.add(this.displayedContainer);
		this.imageContainer.add(Box.createHorizontalStrut(20));
		this.revalidate();
		this.repaint();
	}
	
	/* refreshSelectedLabels - refreshes the JLabels placed in "selectedContainer" by the user
	 */
	private void refreshSelectedLabels()
	{
		this.selectedImagePlace = 0;	
		this.selectedContainer.setAlignmentX(CENTER_ALIGNMENT);
		for (int i = 0; i < 15; i++)
		{
			if (this.selectedImagePlace < this.selectedLabels.size())
			{
				selectedContainer.add(this.selectedLabels.get(this.selectedImagePlace));
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
		this.buttonsContainer.add(this.loadPrevButton);
		this.buttonsContainer.add(Box.createVerticalStrut(100));
		this.buttonsContainer.add(this.continueButton);
		this.buttonsContainer.add(Box.createVerticalStrut(100));
		this.buttonsContainer.add(this.loadNextButton);
		this.add(this.buttonsContainer);
		this.buttonsContainer.setLocation(600, 700);
	}
		
}