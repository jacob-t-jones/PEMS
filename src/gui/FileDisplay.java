package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;

import tools.ImageEditor;



public class FileDisplay extends Box
{
	
	BufferedImage filejpg;
	String directoryName;
	
	public FileDisplay(int axis) {
		super(axis);  
		this.initializeDisplayInfo();
		
	}
	
	public String getDirectoryName(){
		return this.directoryName;
	}
	
	public ImageIcon getFilejpg(){
		return new ImageIcon(this.filejpg);
	}
	
	private void initializeDisplayInfo(){
		try 
		{
			this.filejpg = ImageIO.read(new File("/Users/andrewrottier/Documents/Pictures/folder.png"));
			filejpg = ImageEditor.resizeImage(filejpg, 15);
			try
			{
				directoryName = "/Users/andrewrottier/Documents/Pictures/";
			}
			catch (Exception e)
			{
				System.out.println("Error - file name could not be found");
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Error - file could not be found");
		}
		return;
	}

}
