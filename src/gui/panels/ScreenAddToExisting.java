package gui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	private String filePath;
	private ActionListener continueAction;
	private ArrayList<JLabel> fileButtons = new ArrayList<JLabel>();
	
	private JLabel selectedCase;
	private JButton continueButton;
	private int caseNumField;
	
	public ScreenAddToExisting(FrameManager manager) 
	{
		this.manager = manager;
		this.container = Box.createVerticalBox();
		//this.container.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.populateContainer();
		generateListeners();
		this.manager.setResizable(true);
	}
	
	/* populateContainer - adds components  to the container before displaying it
	 */
	private void populateContainer()
	{
		this.container = Box.createVerticalBox();
		this.container.add(Box.createVerticalStrut(40));
		//below line doesn't add text to the screen
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
		
		this.container.add(ComponentGenerator.generateLabel("Choose a case", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT));
		
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
	        	this.fileButtons.add(tempLabel); //make the folder a click-able button
	        	JLabel tempFilePath = new JLabel("" + file);
	        	
	        	filePath = file.getAbsolutePath(); //
	        	
	        	//create the row and add elements to it
	        	Box row = Box.createHorizontalBox();
	        	row.add(tempLabel);
	        	row.add(tempFilePath);
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
	
	/* generateListeners - initializes listeners for all of the components within the JPanel
	 *    continueAction - attempts to create a directory for the user specified case number
	 *      caseNumFocus - clears the text within "caseNumField" upon said component coming into focus
	 */
	private void generateListeners()
	{
		for(int i = 0; i < fileButtons.size(); i++)
		{
			final JLabel currentLabel = this.fileButtons.get(i);
			currentLabel.addMouseListener(new MouseListener()
			{
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) 
				{
					manager.pushPanel(new ScreenImport(manager, filePath), "PEMS - Import Images");
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

				
			});
		}
	}
	
	
	
}