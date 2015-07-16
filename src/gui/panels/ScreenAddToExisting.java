package gui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

public class ScreenAddToExisting extends JPanel
{
	private FrameManager manager;
	private Box container;
	private File[] fileList;
	private String filePath;
	private ActionListener continueAction;
	private ArrayList<Thumbnail> fileButtons = new ArrayList<Thumbnail>();
	private static String folderImageLocation = "/Users/andrewrottier/Documents/Pictures/folder.pgn";
	
	private JLabel selectedCase;
	private JButton continueButton;
	private int caseNumField;
	
	public ScreenAddToExisting(FrameManager manager) throws IOException 
	{
		this.manager = manager;
		this.container = Box.createVerticalBox();
		//this.container.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.populateContainer();
		this.manager.setResizable(true);
	}
	
	/* populateContainer - adds components  to the container before displaying it
	 */
	private void populateContainer() throws IOException
	{
		this.container = Box.createHorizontalBox();
		this.container.add(Box.createVerticalStrut(40));
		//below line doesn't add text to the screen
		this.container.add(Box.createVerticalStrut(20));
		this.displayImages(0);

		this.add(this.container);
	}
	
	/* displayImages - gets the folders from the cases directory and display them on screen
	 *      imageNum - the number of folders to be displayed on the screen at one time
	 * WARNING - when truncating the path of the directories, will need to replace location with location
	 *           on police computers!
	 */
	private void displayImages(int imageNum) throws IOException
	{
		this.container.removeAll();
		this.remove(this.container);
		this.repaint();
		this.revalidate(); 
		
		this.container.add(ComponentGenerator.generateLabel("Choose a case", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT));
		
		Box col = Box.createVerticalBox();
		
		col.setAlignmentX(LEFT_ALIGNMENT);
		
		File directory = new File("/Users/andrewrottier/Documents/Pictures/");
		fileList = directory.listFiles();
		int numberOfFiles = fileList.length;
		
		for (File file : fileList) {
			
	        if (file.isDirectory()) {
	        	
	        	Thumbnail tempLabel = new Thumbnail(null, filePath);
	        	try{
		        	tempLabel = ComponentGenerator.generateThumbnail(ImageIO.read(new File("/Users/andrewrottier/Documents/Pictures/folder.png")), file.getAbsolutePath());
	        	}
	        	catch(Exception e){
	        		System.out.println("Error - the folder image does not exist in the folder");
	        	}
	        	tempLabel.setImage(ImageEditor.resizeImage(tempLabel.getImage(), 20));
	        	this.fileButtons.add(tempLabel);//construct or list of thumbnails to later turn to buttons
	        	
	        	//create the row and add elements to it
	        	Box row = Box.createHorizontalBox();
	        	row.addMouseListener(this.generateThumbnailListener(tempLabel));
	        	row.add(ComponentGenerator.generateLabel(tempLabel.getImage()));
	        	
	        	//truncate off the beginning of the path so more directories can fit the screen
	        	int endOfCase = tempLabel.getFileLocation().length();
	        	int startOfCase = tempLabel.getFileLocation().indexOf("Pictures/")+9; //replace with cases once on police computers!!!!!
	        	String displayCaseName = tempLabel.getFileLocation().substring(startOfCase, endOfCase);
	        	
	        	row.add(ComponentGenerator.generateLabel(displayCaseName, ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR));
	        	row.setAlignmentX(LEFT_ALIGNMENT);
	        	
	        	//working on making columns
	        	int onLine = 0;
	        	for(int i = 0; i < numberOfFiles; i++){
	        		if(onLine < 10){
	        			col.add(row);
	        			col.add(Box.createVerticalStrut(10));
	        			onLine++;
	        		}
	        		else{
	        			//Box col2 = Box.createVerticalBox();
	        			onLine = 0;
	        			
	        		}
	        	}
	        	
	        	//col.add(row);
	        	//col.add(Box.createVerticalStrut(10));
	        
	        } else if (file.isFile()) {
	        	//System.out.println("Other file: " + file);
	        }
	    }
		this.container.add(col);
		
		this.add(this.container);
		revalidate();
		repaint();
		return;
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
				System.out.println("1");
				manager.pushPanel(new ScreenImport(manager, folderThumbnail.getFileLocation()), "PEMS - Import Images");
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
	
	
	
}