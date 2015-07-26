package gui.display.editcase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import java.nio.file.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tools.ImageEditor;
import gui.*;
import gui.display.FrameManager;
//import gui.img.Thumbnail;
import gui.display.select.SelectPanel;

public class EditCasePanel extends JPanel implements ActionListener
{
	private FrameManager manager;
	private Box container;
	private File[] fileList;
	private String filePath;
	private JLabel displayedCasesLabel;
	private Box displayedContainer;
	private Box displayedImages;
	private Box titleBox;
	private int displayedImagePlace;
	private File directory;
	private JButton loadNextButton;
	private JButton loadPrevButton;
	private JButton backButton;
	private ActionListener continueAction;
	private ArrayList<Thumbnail> fileButtons = new ArrayList<Thumbnail>();
	private static String folderImageLocation = "/Users/andrewrottier/Documents/Pictures/folder.pgn";
	
	private JLabel selectedCase;
	private int caseNumField;
	
	public EditCasePanel(FrameManager manager) throws IOException 
	{
		this.manager = manager;
		this.displayedImages = Box.createHorizontalBox();
		this.displayedContainer = Box.createVerticalBox();
		this.titleBox = Box.createHorizontalBox();
		directory = new File("/Users/andrewrottier/Documents/Pictures/");
		this.displayedImagePlace = 0;
		this.populateContainer();
		this.manager.setResizable(true);
	}
	
	/* populateContainer - adds components  to the container before displaying it
	 */
	private void populateContainer() throws IOException
	{
		this.displayedContainer.add(Box.createVerticalStrut(20));
		this.displayedThumbnails(0);
		this.add(this.displayedContainer);
	}
	
	
	/* refreshDisplayedThumbnails - refreshes the Thumbnails for images not yet selected by the user
	 */
	private void displayedThumbnails(int displayedImagePlace)
	{
		this.displayedImages.removeAll();
		this.displayedContainer.removeAll();
		this.titleBox.removeAll();
		this.displayedCasesLabel = ComponentGenerator.generateLabel("Choose a case", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.displayedImagePlace = displayedImagePlace;
		this.fileList = this.getFileList(); //get list of files
		
		//add components to the title container
		this.titleBox.add(this.displayedCasesLabel);
		this.loadPrevButton = ComponentGenerator.generateButton("Prev", this, CENTER_ALIGNMENT);
		this.loadNextButton = ComponentGenerator.generateButton("Next", this, CENTER_ALIGNMENT);
		this.titleBox.add(loadPrevButton);
		this.titleBox.add(loadNextButton);
		
		this.displayedContainer.add(titleBox);
		
		for (int i = 0; i < 2; i++)
		{
			Box row = Box.createVerticalBox();
			
			for (int j = 0; j < 5; j++)
			{
				Box col = Box.createVerticalBox();
				
				if (this.displayedImagePlace < this.fileList.length) 
				{
		        	
		        	BufferedImage tempImg = new BufferedImage(1, 1, 1);
		        	Thumbnail tempLabel = new Thumbnail(tempImg, filePath, "", ".png");
		        	
		        	try{
			        	tempLabel = ComponentGenerator.generateThumbnail(ImageIO.read(new File("/Users/andrewrottier/Documents/Pictures/folder.png")), fileList[this.displayedImagePlace].getAbsolutePath(), "filename", "");
		        	}
		        	catch(Exception e){
		        		System.out.println("Error - the folder image does not exist in the folder");
		        	}
		        	tempLabel.setImage(ImageEditor.resizeImage(tempLabel.getImage(), 20));
		        	
		        	//truncate off the beginning of the path so more directories can fit the screen
		        	int endOfCase = tempLabel.getFilePath().length();
		        	int startOfCase = tempLabel.getFilePath().indexOf("Pictures/")+9; //replace with cases once on police computers!!!!!
		        	String displayCaseName = tempLabel.getFilePath().substring(startOfCase, endOfCase);
		        	System.out.println("end: " + endOfCase + " ... start: " + startOfCase + " ... displayCaseName: " + displayCaseName + " ... filepath: "+ tempLabel.getFilePath());
		        	
		        	this.fileButtons.add(tempLabel);
		        	
		        	col.addMouseListener(this.generateThumbnailListener(tempLabel)); //make buttons
		        	//create the row and add elements to it
					
		        	col.add(ComponentGenerator.generateLabel(tempLabel.getImage()));
		        	col.add(ComponentGenerator.generateLabel(displayCaseName, ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR));
		        	
					col.setAlignmentX(LEFT_ALIGNMENT);
					this.displayedImagePlace++;
				}
				
				row.add(col);
			}
			//this.displayedImagePlace = 0;
			this.displayedImages.add(row);
			this.displayedImages.add(Box.createHorizontalStrut(5));
		}
		
		this.displayedContainer.add(displayedImages);
		this.backButton = ComponentGenerator.generateButton("Back", this, CENTER_ALIGNMENT);
		this.displayedContainer.add(backButton);
		//this.displayedContainer.add(Box.createVerticalStrut(750));
		this.displayedImagePlace = displayedImagePlace;
	
		this.revalidate();
		this.repaint();
	}
	
	
	/* generateListeners - initializes listeners for all of the components within the JPanel
	 *    continueAction - attempts to create a directory for the user specified case number
	 *      caseNumFocus - clears the text within "caseNumField" upon said component coming into focus
	 */
	private MouseListener generateThumbnailListener(final Thumbnail folderThumbnail)
	{
		MouseListener thumbnailListener = new MouseListener()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) 
			{
				manager.pushPanel(new SelectPanel(manager, folderThumbnail.getFilePath()), "PEMS - Import Images");
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
		};
		return thumbnailListener;
	}
	

	private File[] getFileList(){
		return this.directory.listFiles();
	}


	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *               1. attempts to load the next x amount of cases
	 *                 2. attempts to load the previous x amount of cases
	 *                 3. returns the user back to the home screen
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.loadNextButton)
		{
			if (this.displayedImagePlace + 10 < this.fileList.length)
			{
				this.displayedThumbnails(this.displayedImagePlace + 10);
			}
		}
		
		else if (e.getSource() == this.loadPrevButton)
		{
			if (this.displayedImagePlace - 10 >= 0)
			{
				this.displayedThumbnails(this.displayedImagePlace - 10);
			}
		}
		else if(e.getSource() == this.backButton)
		{
			manager.pushPanel(new StartPanel(manager), "PEMS (Police Evidence Management System) Version 0.1");
		}
	
	}
	

	
}