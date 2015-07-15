package gui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.*;

public class ScreenAddToExisting extends JPanel
{
	private FrameManager manager;
	private Box container;
	private File[] fileList;
	private JLabel selectedCase;
	private JButton continueButton;
	
	public ScreenAddToExisting(FrameManager manager) 
	{
		this.manager = manager;
		this.container = Box.createVerticalBox();
		this.container.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.populateContainer();
		this.manager.setResizable(true);
	}
	
	/* populateContainer - adds components  to thecontainer before displaying it
	 */
	private void populateContainer()
	{
		this.container = Box.createVerticalBox();
		this.container.add(Box.createVerticalStrut(40));
		//below line doesn't add text to the screen
		this.container.add(ComponentGenerator.generateLabel("Choose a case", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT));;
		this.container.add(Box.createVerticalStrut(20));
		this.displayImages(0);
		this.add(this.container);
	}
	
	private void displayImages(int imageNum)
	{
		this.container.removeAll();
		this.remove(this.container);
		this.repaint();
		this.revalidate(); 
		
		//imagePlace = imageNum;
		Box col = Box.createVerticalBox();
		
		col.setAlignmentX(CENTER_ALIGNMENT);
		
		File directory = new File("/Users/andrewrottier/Documents/Pictures/");
		fileList = directory.listFiles();
		
		for (File file : fileList) {
			
	        if (file.isDirectory()) {
	        	
	        	//convert the folder image and name to jlabels
	        	FileDisplay tempFolder = new FileDisplay(1);
	        	JLabel tempLabel = new JLabel(tempFolder.getFilejpg());
	        	JLabel tempFileName = new JLabel("" + file);
	        	
	        	//create the row and add elements to it
	        	Box row = Box.createHorizontalBox();
	        	row.add(tempLabel);
	        	row.add(tempFileName);
	        	row.setAlignmentX(LEFT_ALIGNMENT);
	        	
	        	col.add(row);
	        	col.add(Box.createVerticalStrut(10));
	        
	        } else if (file.isFile()) {
	        	//System.out.println("Other file: " + file);
	        }
	    }
		this.container.add(col);
		
		//imagePlace = imageNum; //reset back to original param to avoid errors w next/prev buttons
		this.add(this.container);
		revalidate();
		repaint();
		return;
	}
	
	
}