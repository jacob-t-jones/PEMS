package gui;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;

import org.imgscalr.Scalr;

import gui.components.img.Img;
//import tools.ImageEditor;

public class FileDisplay extends Box {

	BufferedImage filejpg;
	String directoryName;

	public FileDisplay(int axis) {
		super(axis);
		this.initializeDisplayInfo();

	}

	/*
	 * getDirectoryName - return the directory name
	 */
	public String getDirectoryName() {
		return this.directoryName;
	}

	/*
	 * getFileIcon - return the BufferedImage as an ImageIcon
	 */
	public ImageIcon getFileIcon() {
		return new ImageIcon(this.filejpg);
	}

	/*
	 * getFile - return the BufferedImage as an ImageIcon
	 */
	public BufferedImage getFile() {
		return this.filejpg;
	}

	/*
	 * initializeDisplayInfo - obtain the information about the file directory
	 * such as the folder.png image and the location of the cases directory
	 */
	private void initializeDisplayInfo() {
		try {
			this.filejpg = ImageIO.read(new File(
					"/Users/andrewrottier/Documents/Pictures/folder.png"));
			//filejpg = Img.resizeImage(filejpg, 15);
			Img tempImg = ComponentGenerator.generateImg(this.directoryName, 15);
			tempImg.resizeImage(Scalr.Method.ULTRA_QUALITY, 15);
			this.filejpg = tempImg.getImage();
			try {
				directoryName = "/Users/andrewrottier/Documents/Pictures/";
			} catch (Exception e) {
				System.out.println("Error - file name could not be found");
			}
		} catch (Exception e) {
			System.out.println("Error - file could not be found");
		}
		return;
	}

}
