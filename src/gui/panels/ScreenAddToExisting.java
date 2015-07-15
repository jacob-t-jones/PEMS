package gui.panels;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.FileDisplay;
import gui.FrameManager;

public class ScreenAddToExisting extends JPanel
{
	private FrameManager manager;
	private Box container;
	private JLabel instructionsLabel;
	private JLabel selectedCase;
	private JButton continueButton;
	private int imagePlace;
	private ArrayList<JLabel> cases;
	private ArrayList<FileDisplay> folders;
	
	public ScreenAddToExisting(FrameManager manager) 
	{
		this.manager = manager;
		this.container = Box.createVerticalBox();
		this.container.setBorder(BorderFactory.createLineBorder(Color.black));
		this.populateContainer();
	}
	
	private void displayImages(int imageNum)
	{
		this.container.removeAll();
		this.remove(this.container);
		this.repaint();
		this.revalidate(); 
		
		imagePlace = imageNum;
		
		File directory = new File("/Users/andrewrottier/Documents/Pictures/");
		File tempFile = new File("");
		File[] fileList = directory.listFiles();
		
		for (File file : fileList) {
	        if (file.isFile()) {
	        	JLabel tempFolder = new JLabel();
	        	
	        	tempFile.renameTo(file);
	        	tempFolder.add(tempFile);
	        	Box col = Box.createVerticalBox();
	        	col.add(tempFile);
	            //files.add(file);
	        } else if (file.isDirectory()) {
	            listf(file.getAbsolutePath(), files);
	        }
	    }
		
		for (int i = 0; i < 3; i++)
		{
			Box row = Box.createHorizontalBox();
			for (int j = 0; j < 15; j++)
			{
				try
				{
					FileDisplay newFile = new FileDisplay(0);
					row.add(this.folders.get(this.imagePlace));
					System.out.println(imagePlace);
					imagePlace++;
				}
				catch (Exception e)
				{
					row.add(Box.createHorizontalStrut(10)); //space the size of a picture
				}
				row.add(Box.createHorizontalStrut(10)); //spacing between pictures
				
			}
			row.setAlignmentX(CENTER_ALIGNMENT);
			this.container.add(row);
		}
		imagePlace = imageNum; //reset back to original param to avoid errors w next/prev buttons
		this.add(this.container);
		revalidate();
		repaint();
		return;
	}
	
	/* populateContainer - adds componenets before displaying it
	 */
	private void populateContainer()
	{
		this.container = Box.createVerticalBox();
		this.container.add(Box.createVerticalStrut(20));
		this.constructLabel("Choose a case");
		this.container.add(Box.createVerticalStrut(20));
		this.displayImages(0);
		//this.container.add(Box.createVerticalStrut(40));
		//this.createAddToExistingButton();
		this.add(this.container);
	}
	
	private void constructLabel(String text)
	{
		this.instructionsLabel = new JLabel(text);
		this.instructionsLabel.setFont(this.manager.STANDARD_TEXT_FONT);
		this.instructionsLabel.setForeground(this.manager.STANDARD_TEXT_COLOR);
		this.instructionsLabel.setAlignmentX(LEFT_ALIGNMENT);
		this.instructionsLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.container.add(this.instructionsLabel);
	}
}
