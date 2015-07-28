// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenEdit.java

package gui.display.editimg;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;

import tools.*;
import gui.*;
import gui.display.FrameManager;
import gui.display.finish.FinishPanel;

public class EditImgPanel extends JPanel implements ActionListener, MouseListener
{
	
	private FrameManager manager;
	private Stack<BufferedImage> selectedImageHistorySaved;
	private Stack<BufferedImage> selectedImageHistoryUndone;
	private ArrayList<Thumbnail> caseThumbnails;
	private BufferedImage selectedImage;
	private Point[] cropVals;
	private Box mainContainer;
	private Box selectedImageContainer;
	private Box caseThumbnailContainer;
	private JLabel selectedImageLabel;
	private JButton loadNextThumbnails;
	private JButton loadPrevThumbnails;
	private JButton continueButton;
	
	//Editor Buttons on screen
	private JButton removeButton;
	private JButton antiAliasButton;
	private JButton brightenButton;
	private JButton darkenButton;
	private JButton grayscaleButton;
	private JButton resizeButton;
	private JButton cropButton;
	private JButton rotate90Button;
	private JButton renameButton;
	private JButton undoButton;
	private JButton redoButton;
	private Box editorBox;
	
	//crop box stuff
	private DrawRect cropBox;
	private Box cropContainer;
	private Point mousePoint;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu caseMenu;
	private JMenu imageMenu;
	private JMenuItem saveImageMenuItem;
	private JMenuItem renameImageMenuItem;
	private JMenuItem quitMenuItem;
	private JMenuItem undoMenuItem;
	private JMenuItem redoMenuItem;
	private JMenuItem removeImageMenuItem;
	private JMenuItem antiAliasMenuItem;
	private JMenuItem brightenMenuItem;
	private JMenuItem darkenMenuItem;
	private JMenuItem grayscaleMenuItem;
	private JMenuItem resizeMenuItem;
	private JMenuItem cropMenuItem;
	private JMenuItem rotate90MenuItem;
	private JMenuItem rotate180MenuItem;
	private JMenuItem rotate270MenuItem;
	private String caseNum;
	private String selectedImagePath;
	private String selectedImageName;
	private String selectedImageExt;
	private boolean saved;
	private boolean cropping;
	private int caseThumbnailIndex;
	
	
	public EditImgPanel(FrameManager manager, String caseNum)
	{
		this.manager = manager;
		this.caseNum = caseNum;
		this.saved = true;
		this.cropping = false;
		this.caseThumbnailIndex = 0;
		
		this.cropBox = new DrawRect(this.getGraphics(), null);
		this.cropContainer = Box.createHorizontalBox();
		this.cropContainer.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		this.add(this.cropContainer);
		
		this.selectedImageHistorySaved = new Stack<BufferedImage>();
		this.selectedImageHistoryUndone = new Stack<BufferedImage>();
		this.caseThumbnails = this.getCaseThumbnails();
		this.mainContainer = Box.createVerticalBox();
		this.selectedImageContainer = Box.createHorizontalBox();
		this.caseThumbnailContainer = Box.createHorizontalBox();
		this.selectedImageContainer.add(this.cropBox);
		this.editorBox = Box.createVerticalBox();
		this.caseThumbnailContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.populateEditorBox();
		this.resetCropValues();
		this.refreshCaseThumbnails(0);
		this.loadFirstImage();
		this.populateMainContainer();
		this.add(this.mainContainer);
		this.constructMenuBar();
		this.manager.getMainWindow().setJMenuBar(this.menuBar);
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.revalidate();
		this.repaint();
	}
	
	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *                 1. attempts to load the next eight Thumbnails included in the current case
	 *                 2. attempts to load the previous eight Thumbnails included in the current case
	 *                 3. calls saveImage()
	 *                 4. displays the dialogue for renaming an image
	 *                 5. if the user has saved, the program quits; otherwise, a dialogue warning him or her to save is displayed
	 *                 6. calls undo()
	 *                 7. calls redo()
	 *                 8. displays a dialogue which assures that the user wishes to remove the image from the case
	 *                 9. calls antiAlias()
	 *                 10. calls brighten()
	 *                 11. calls darken()
	 *                 12. calls grayScale()
	 *                 13. displays the dialogue for resizing an image
	 *                 14. begins the cropping process
	 *                 15. calls rotate90()
	 *                 16. calls rotate180()
	 *                 17. calls rotate270()
	 *                 18. if the user has saved, removes the menu bar and pushes ScreenFinish; otherwise, a dialogue warning him or her to save is displayed       
	 */
	public void actionPerformed(ActionEvent e)
	{ 
		if (e.getSource() == this.loadNextThumbnails)
		{
        	if (this.caseThumbnailIndex + 8 < this.caseThumbnails.size())
        	{
        		this.refreshCaseThumbnails(this.caseThumbnailIndex + 8);
        	}
		}
		else if (e.getSource() == this.loadPrevThumbnails)
		{
          	if (this.caseThumbnailIndex >= 8)
        	{
        		this.refreshCaseThumbnails(this.caseThumbnailIndex - 8);
        	}
		}
		else if (e.getSource() == this.saveImageMenuItem)
		{
			this.saveImage();
		}
		else if (e.getSource() == this.renameImageMenuItem)
		{
			//this.manager.displayRenameDialogue(this);
		}
		else if (e.getSource() == this.quitMenuItem)
		{
			if (this.saved)
			{
				System.exit(0);
			}
			else
			{
				//this.manager.displayQuitWarningDialogue(this);
			}
		}
		else if (e.getSource() == this.undoMenuItem || e.getSource() == this.undoButton)
		{
			this.undo();
		}
		else if (e.getSource() == this.redoMenuItem || e.getSource() == this.redoButton)
		{
			this.redo();
		}
		else if (e.getSource() == this.removeImageMenuItem || e.getSource() == this.removeButton)
		{
			//this.manager.displayRemoveWarningDialogue(this);
		}
	    else if (e.getSource() == this.antiAliasMenuItem || e.getSource() == this.antiAliasButton)
		{
	    	this.antiAlias();
		}
		else if (e.getSource() == this.brightenMenuItem || e.getSource() == this.brightenButton)
		{
			this.brighten();
		}
		else if (e.getSource() == this.darkenMenuItem || e.getSource() == this.darkenButton)
		{
			this.darken();
		}
		else if (e.getSource() == this.grayscaleMenuItem || e.getSource() == this.grayscaleButton)
		{
			this.grayscale();
		}
		else if (e.getSource() == this.resizeMenuItem || e.getSource() == this.resizeButton)
		{
			//this.manager.displayResizeDialogue(this, this.selectedImage.getWidth(), this.selectedImage.getHeight());
		}
		else if (e.getSource() == this.cropMenuItem || e.getSource() == this.cropButton)
		{
			this.cropping = true;
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
		else if (e.getSource() == this.rotate90MenuItem || e.getSource() == this.rotate90Button)
		{
			this.rotate90();
		}
		else if (e.getSource() == this.rotate180MenuItem)
		{
			this.rotate180();
		}
		else if (e.getSource() == this.rotate270MenuItem)
		{
			this.rotate270();
		}
		else if (e.getSource() == this.continueButton)
		{
			if (this.saved)
			{
				//this.manager.removeMenuBar();//?
				try
				{
					//this.manager.closeRenameDialogue();
					this.menuBar.removeAll(); ////not removing menu bar..
				}
				catch (Exception e1)
				{
					System.out.println("error - no dialogue panels open");
				}
		    	this.manager.getMainWindow().pushPanel(new FinishPanel(manager, caseNum), "PEMS - Finish");
			}
			else
			{
				//this.manager.displayContinueWarningDialogue(this);
			}
		}
	}
	
	/* mouseClicked - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	          e - the event in question
	 *              1. displays the image selected for editing by the user when he or she clicks on its corresponding Thumbnail in the bottom panel
	 *              2. carries out the cropping procedure once the user has opted in and selected two distinct locations on the image
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if (e.getSource() instanceof Thumbnail)
		{
			Thumbnail selectedThumbnail = (Thumbnail)e.getSource();
			this.selectedImage = null;
			try 
			{      
				this.selectedImage = ImageIO.read(new File(selectedThumbnail.getFilePath()));
			} 
			catch (IOException e1)
			{
				System.out.println("Error - Unable to import selected image");
				e1.printStackTrace();
				return;
			}
			this.selectedImagePath = selectedThumbnail.getFilePath();
			this.selectedImageName = selectedThumbnail.getFileName();
			this.selectedImageExt = selectedThumbnail.getFileExt();
			this.selectedImageHistorySaved.clear();
			this.selectedImageHistoryUndone.clear();
			this.selectedImageHistorySaved.add(this.selectedImage);
			this.refreshSelectedImage();
		}
		else if (e.getSource() == this.selectedImageLabel && this.cropping)
		{
			System.out.println("cropVal[0]: " + this.cropVals[0]);
			
			if (this.cropVals[0] == null)
			{
				this.cropVals[0] = new Point();
				this.cropVals[0].setLocation(e.getX(), e.getY());

				this.mousePoint  = e.getLocationOnScreen();
				this.drawRec(this.getGraphics(), this.mousePoint);
				
				return;
			}
			this.cropVals[1] = new Point();
			this.cropVals[1].setLocation(e.getX(), e.getY());
			this.crop(this.cropVals[0], this.cropVals[1]);
		}
	}
	
	
	private void drawRec(Graphics g, Point initPoint)
	{
		//this.remove(cropBox);
		cropBox = new DrawRect(this.getGraphics(), initPoint);
		cropBox.paint(g, initPoint);
		//this.revalidate();
		this.cropBox.repaint();
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
	
	/* saveImage - saves the image currently being edited to both the "backups" and "cases" folders, overwriting previous saves in "cases" and saving a uniquely named version in "backups"
	 */
	public void saveImage()
	{
		int backupIncrement = 0;
		if (Files.exists(Paths.get("backups/" + this.caseNum + "/" +  this.selectedImageName + this.selectedImageExt)))
		{
			backupIncrement++;
			while (Files.exists(Paths.get("backups/" + this.caseNum + "/" +  this.selectedImageName + " (" + backupIncrement + ")" + this.selectedImageExt)))
			{
				backupIncrement++;
			}
		}
		try 
		{
			if (backupIncrement == 0)
			{
				ImageIO.write(this.selectedImage, this.getImageFileType(this.selectedImageExt), new File("backups/" + this.caseNum + "/" + this.selectedImageName + this.selectedImageExt));
			}
			else
			{
				ImageIO.write(this.selectedImage, this.getImageFileType(this.selectedImageExt), new File("backups/" + this.caseNum + "/" + this.selectedImageName + " (" + backupIncrement + ")" + this.selectedImageExt));
			}
		    ImageIO.write(this.selectedImage, this.getImageFileType(this.selectedImageExt), new File("cases/" + this.caseNum + "/" + this.selectedImageName + this.selectedImageExt));
		} 
		catch (IOException e) 
		{
		    System.out.println("Error - Save failed");
		    e.printStackTrace();
		    return;
		}
		this.saved = true;
	}
	
	/* renameImage - renames the "cases" version of the image that is currently being edited
	 * 	   newName - the new name of the file
	 */
	public void renameImage(String newName)
	{
		File oldFile = new File(this.selectedImagePath);
		File newFile = new File("cases/" + this.caseNum + "/" + newName + this.selectedImageExt);
		oldFile.renameTo(newFile);
	}
	
	/* resizeImage - resizes the image that is currently being edited to the size specified in the paramaters
	 *    newWidth - the new width of the image
	 *   newHeight - the new height of the image
	 */
	public void resizeImage(int newWidth, int newHeight)
	{
		this.selectedImageHistoryUndone.clear();
		this.selectedImage = ImageEditor.resizeImage(this.selectedImage, newWidth, newHeight);
		this.selectedImageHistorySaved.push(this.selectedImage);
		this.refreshSelectedImage();
		this.saved = false;
	}
	
	/* getCaseThumbnails - returns an ArrayList of Thumbnail objects read in from the currently selected case folder
	 */
	private ArrayList<Thumbnail> getCaseThumbnails()
	{
		ArrayList<Thumbnail> thumbnailList = new ArrayList<Thumbnail>();
	    File directory = new File("cases" + "/" + this.caseNum + "/");
		String[] fileNames = directory.list(); //directory is empty, never copies images to the directory

		for (int i = 0; i < fileNames.length; i++)//never gets in loop
		{
			System.out.println("files in directory: "+ fileNames[i]);
			String currentFileName = fileNames[i].substring(0, fileNames[i].indexOf('.')).toLowerCase();
			String currentExtension = fileNames[i].substring(fileNames[i].indexOf('.'), fileNames[i].length()).toLowerCase();
			if ((currentExtension.equalsIgnoreCase(".png") || currentExtension.equalsIgnoreCase(".jpg") || currentExtension.equalsIgnoreCase(".jpeg")))
			{ 
				BufferedImage currentImage = null;
				String currentPath = "cases/" + this.caseNum + "/" + fileNames[i];
				System.out.println("directory: "+ currentPath);
			    try 
			    {   
			    	 currentImage = ImageIO.read(new File(currentPath));
			    }
			    catch (IOException e)
			    {
			    	System.out.println("Error - Unable to read image into memory");
			    	e.printStackTrace();
				    return null;
			    }	    	
			    Thumbnail currentThumb = ComponentGenerator.generateThumbnail(ImageEditor.resizeThumbnail(currentImage, 80), currentPath, currentFileName, currentExtension);
				currentThumb.setAlignmentX(CENTER_ALIGNMENT);
			    currentThumb.addMouseListener(this);
			    thumbnailList.add(currentThumb);
			    System.out.println(thumbnailList.size());
			}
		}
		//System.out.println("files in directory: "+ fileNames);
		System.out.println("length of list: "+ thumbnailList.size());
		System.out.println("case Number: " + caseNum);
		System.out.println("thumb list: " + thumbnailList);
	    return thumbnailList;
	}
	
	/* populateEditorBox - insert the buttons into the editor box
	 */
	private void populateEditorBox()
	{
		this.antiAliasButton = ComponentGenerator.generateButton("AntiAlias", this, CENTER_ALIGNMENT);
		this.brightenButton = ComponentGenerator.generateButton("Brighten", this, CENTER_ALIGNMENT);
		this.darkenButton = ComponentGenerator.generateButton("Darken", this, CENTER_ALIGNMENT);
		this.cropButton = ComponentGenerator.generateButton("Crop", this, CENTER_ALIGNMENT);
		this.grayscaleButton = ComponentGenerator.generateButton("Grayscale", this, CENTER_ALIGNMENT);
		this.rotate90Button = ComponentGenerator.generateButton("Rotate 90", this, CENTER_ALIGNMENT);
		this.resizeButton = ComponentGenerator.generateButton("Resize", this, CENTER_ALIGNMENT);
		this.renameButton = ComponentGenerator.generateButton("Rename", this, CENTER_ALIGNMENT);
		this.removeButton = ComponentGenerator.generateButton("Remove", this, CENTER_ALIGNMENT);
		this.undoButton = ComponentGenerator.generateButton("undo", this, CENTER_ALIGNMENT);
		this.redoButton = ComponentGenerator.generateButton("redo", this, CENTER_ALIGNMENT);
		
		//add images to the editor box
		this.editorBox.add(antiAliasButton);
		this.editorBox.add(brightenButton);
		this.editorBox.add(darkenButton);
		this.editorBox.add(Box.createVerticalStrut(30));
		this.editorBox.add(cropButton);
		this.editorBox.add(grayscaleButton);
		this.editorBox.add(rotate90Button);
		this.editorBox.add(resizeButton);
		this.editorBox.add(renameButton);
		this.editorBox.add(Box.createVerticalStrut(30));
		this.editorBox.add(undoButton);
		this.editorBox.add(redoButton);
		this.editorBox.add(Box.createVerticalStrut(30));
		this.editorBox.add(removeButton);
	}
	
	/* refreshSelectedImage - refreshes "selectedImageLabel" so that it contains "selectedImage", resizing the label if the image is too large to fit on screen
	 */
	private void refreshSelectedImage()
	{
		this.selectedImageContainer.removeAll();
		if (this.selectedImage.getHeight() > 500)
		{
			this.selectedImageLabel = ComponentGenerator.generateLabel(ImageEditor.resizeImage(this.selectedImage, 500), CENTER_ALIGNMENT);
			this.selectedImageLabel.addMouseListener(this);

		}
		else if (this.selectedImage.getWidth() > 1000)
		{
			this.selectedImageLabel = ComponentGenerator.generateLabel(ImageEditor.resizeImage(this.selectedImage, 1000), CENTER_ALIGNMENT);
			this.selectedImageLabel.addMouseListener(this);
		}
		else
		{
			this.selectedImageLabel = ComponentGenerator.generateLabel(this.selectedImage, CENTER_ALIGNMENT);
			this.selectedImageLabel.addMouseListener(this);
		}
		this.selectedImageContainer.setMaximumSize(new Dimension(1000, 500));
		this.selectedImageContainer.setMinimumSize(new Dimension(1000, 500));
		this.selectedImageContainer.add(Box.createHorizontalGlue());
		this.selectedImageContainer.add(Box.createVerticalStrut(500));
		this.selectedImageContainer.add(this.selectedImageLabel);
		this.selectedImageContainer.add(Box.createVerticalStrut(500));
		this.selectedImageContainer.add(Box.createHorizontalGlue());
		this.selectedImageContainer.add(this.editorBox);
		this.revalidate();
		this.repaint();
	}
	
	private void refreshCaseThumbnails(int caseThumbnailIndex)
	{
		this.loadNextThumbnails = ComponentGenerator.generateButton("Next     >", this);
		this.loadPrevThumbnails = ComponentGenerator.generateButton("<     Prev", this); 
		this.caseThumbnailContainer.removeAll();
		this.caseThumbnailIndex = caseThumbnailIndex;
		this.caseThumbnailContainer.add(this.loadPrevThumbnails);
		for (int i = 0; i < 8; i++)
		{
			Box col = Box.createVerticalBox();
			col.setMinimumSize(new Dimension(100, 100));
			col.setMaximumSize(new Dimension(100, 100));
			if (this.caseThumbnailIndex < this.caseThumbnails.size())
			{
				col.add(Box.createVerticalGlue());
				col.add(Box.createHorizontalStrut(100));
				col.add(this.caseThumbnails.get(this.caseThumbnailIndex));
				col.add(Box.createHorizontalStrut(100));
				col.add(Box.createVerticalGlue());
			}
			else
			{
				col.add(Box.createVerticalGlue());
				col.add(Box.createHorizontalStrut(100));
				col.add(Box.createVerticalGlue());
			}
			this.caseThumbnailContainer.add(col);
			this.caseThumbnailIndex++;
		}
		this.caseThumbnailContainer.add(this.loadNextThumbnails);
		this.caseThumbnailContainer.add(Box.createVerticalStrut(100));
		this.caseThumbnailIndex = caseThumbnailIndex;
		this.revalidate();
		this.repaint();
	}
	
	/* loadFirstImage - loads the first image linked to in the "caseThumbnails" ArrayList immediately upon the JPanel loading
	 */
	private void loadFirstImage()
	{
		Thumbnail selectedThumbnail = this.caseThumbnails.get(0);
		this.selectedImage = null;
		try 
		{      
			this.selectedImage = ImageIO.read(new File(selectedThumbnail.getFilePath()));
		} 
		catch (IOException e)
		{
			System.out.println("Error - Unable to import selected image");
			e.printStackTrace();
			return;
		}
		this.selectedImagePath = selectedThumbnail.getFilePath();
		this.selectedImageName = selectedThumbnail.getFileName();
		this.selectedImageExt = selectedThumbnail.getFileExt();
		this.selectedImageHistorySaved.push(this.selectedImage);
		this.refreshSelectedImage();
	}
	
	/* populateMainContainer - adds "selectedImageContainer", "caseThumbnailContainer", and "continueButton" to "mainContainer"
	 */
	private void populateMainContainer()
	{
		this.continueButton = ComponentGenerator.generateButton("Continue", this, CENTER_ALIGNMENT);
		this.mainContainer.add(this.selectedImageContainer);
		this.mainContainer.add(Box.createVerticalStrut(40));
		this.mainContainer.add(this.caseThumbnailContainer);
		this.mainContainer.add(Box.createVerticalStrut(10));
		this.mainContainer.add(this.continueButton);
	}
	
	/* constructMenuBar - creates and populates the JMenuBar displayed at the top of the JFrame
	 */
	private void constructMenuBar()
	{
		this.menuBar = new JMenuBar();
		this.fileMenu = new JMenu("File");
		this.editMenu = new JMenu("Edit");
		this.caseMenu = new JMenu("Case");
		this.imageMenu = new JMenu("Image");
		this.saveImageMenuItem = ComponentGenerator.generateMenuItem("Save Image", this, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		this.renameImageMenuItem = ComponentGenerator.generateMenuItem("Rename Image", this);
		this.quitMenuItem = ComponentGenerator.generateMenuItem("Quit", this, KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		this.undoMenuItem = ComponentGenerator.generateMenuItem("Undo", this, KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		this.redoMenuItem = ComponentGenerator.generateMenuItem("Redo", this, KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		this.removeImageMenuItem = ComponentGenerator.generateMenuItem("Remove Image from Case", this);
		this.antiAliasMenuItem = ComponentGenerator.generateMenuItem("Apply Anti Aliasing", this);
		this.brightenMenuItem = ComponentGenerator.generateMenuItem("Brighten by 10%", this);
		this.darkenMenuItem = ComponentGenerator.generateMenuItem("Darken by 10%", this);
		this.grayscaleMenuItem = ComponentGenerator.generateMenuItem("Convert to Grayscale", this);
		this.resizeMenuItem = ComponentGenerator.generateMenuItem("Resize Image", this);
		this.cropMenuItem = ComponentGenerator.generateMenuItem("Crop Image", this);
		this.rotate90MenuItem = ComponentGenerator.generateMenuItem("Rotate Image 90\u00b0 Right", this);
		this.rotate180MenuItem = ComponentGenerator.generateMenuItem("Rotate Image 180\u00b0 Right", this);
		this.rotate270MenuItem = ComponentGenerator.generateMenuItem("Rotate Image 270\u00b0 Right", this);
		this.fileMenu.add(this.saveImageMenuItem);
		this.fileMenu.add(this.renameImageMenuItem);
		this.fileMenu.addSeparator();
		this.fileMenu.add(this.quitMenuItem);
		this.editMenu.add(this.undoMenuItem);
		this.editMenu.add(this.redoMenuItem);
		this.caseMenu.add(this.removeImageMenuItem);
		this.imageMenu.add(this.antiAliasMenuItem);
		this.imageMenu.addSeparator();
		this.imageMenu.add(this.brightenMenuItem);
		this.imageMenu.add(this.darkenMenuItem);
		this.imageMenu.addSeparator();
		this.imageMenu.add(this.grayscaleMenuItem);
		this.imageMenu.addSeparator();
		this.imageMenu.add(this.resizeMenuItem);
		this.imageMenu.addSeparator();
		this.imageMenu.add(this.cropMenuItem);
		this.imageMenu.addSeparator();
		this.imageMenu.add(this.rotate90MenuItem);
		this.imageMenu.add(this.rotate180MenuItem);
		this.imageMenu.add(this.rotate270MenuItem);
		this.menuBar.add(this.fileMenu);
		this.menuBar.add(this.editMenu);
		this.menuBar.add(this.caseMenu);
		this.menuBar.add(this.imageMenu);
	}
	
	/* undo - undoes the most recent action applied by the user
	 */
	private void undo()
	{
		if (this.selectedImageHistorySaved.size() > 1)
		{
			this.selectedImageHistoryUndone.push(this.selectedImageHistorySaved.pop());
			this.selectedImage = this.selectedImageHistorySaved.peek();
			this.refreshSelectedImage();
			this.saved = false;
		}
	}
	
	/* redo - redoes the most recent action undone by the user
	 */
	private void redo()
	{
		if (this.selectedImageHistoryUndone.size() > 0)
		{
			this.selectedImageHistorySaved.push(this.selectedImageHistoryUndone.pop());
			this.selectedImage = this.selectedImageHistorySaved.peek();
			this.refreshSelectedImage();
			this.saved = false;
		}
	}
	
	/* antiAlias - applies an anti aliasing procedure to the image currently being edited
	 */
	private void antiAlias()
	{
		this.selectedImageHistoryUndone.clear();
		this.selectedImage = ImageEditor.applyAntiAliasing(this.selectedImage);
		this.selectedImageHistorySaved.push(this.selectedImage);
		this.refreshSelectedImage();
		this.saved = false;
	}
	
	/* brighten - applies a brightening procedure to the image currently being edited
	 */
	private void brighten()
	{
		this.selectedImageHistoryUndone.clear();
		this.selectedImage = ImageEditor.brightenImage(this.selectedImage);
		this.selectedImageHistorySaved.push(this.selectedImage);
		this.refreshSelectedImage();
		this.saved = false;
	}
	
	/* darken - applies a darkening procedure to the image currently being edited
	 */
	private void darken()
	{  
		this.selectedImageHistoryUndone.clear();
		this.selectedImage = ImageEditor.darkenImage(this.selectedImage);
		this.selectedImageHistorySaved.push(this.selectedImage);
		this.refreshSelectedImage();
		this.saved = false;
	}
	
	/* grayscale - converts the image currently being edited to grayscale
	 */
	private void grayscale()
	{
		this.selectedImageHistoryUndone.clear();
		this.selectedImage = ImageEditor.toGrayscale(this.selectedImage);
		this.selectedImageHistorySaved.push(this.selectedImage);
		this.refreshSelectedImage();
		this.saved = false;
	}
	
	/* crop - crops the current image based on the Point objects passed in as parameters
	 * locA - the first point selected by the user for the cropping procedure
	 * locB - the second point selected by the user for the cropping procedure
	 *    *mousePos - the location of the mouse coordinate after the initial click
	 */
	private void crop(Point locA, Point locB)
	{
		int x = (int)Math.min(locA.getX(), locB.getX());
		int y = (int)Math.min(locA.getY(), locB.getY());
		int width = (int)Math.abs(locB.getX() - locA.getX());
		int height = (int)Math.abs(locB.getY() - locA.getY());
		if (this.selectedImage.getWidth() != this.selectedImageLabel.getWidth() || this.selectedImage.getHeight() != this.selectedImageLabel.getHeight())
		{
			x = (this.selectedImage.getWidth() * x) / this.selectedImageLabel.getWidth();
			y = (this.selectedImage.getHeight() * y) / this.selectedImageLabel.getHeight();
			width = (this.selectedImage.getWidth() * width) / this.selectedImageLabel.getWidth();
			height = (this.selectedImage.getHeight() * height) / this.selectedImageLabel.getHeight();
		}
		this.saved = false;
		this.cropping = false;
		this.resetCropValues();
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.selectedImageHistoryUndone.clear();
		this.selectedImage = ImageEditor.cropImage(this.selectedImage, x, y, width, height);
		this.selectedImageHistorySaved.push(this.selectedImage);
		this.refreshSelectedImage();
	}
	
	/* rotate90 - rotates the image currently being edited 90 degrees to the right
	 */
	private void rotate90()
	{
		this.selectedImageHistoryUndone.clear();
		this.selectedImage = ImageEditor.rotateRight90(this.selectedImage);
		this.selectedImageHistorySaved.push(this.selectedImage);
		this.refreshSelectedImage();
		this.saved = false;
	}
	
	/* rotate180 - rotates the image currently being edited 180 degrees to the right
	 */
	private void rotate180()
	{
		this.selectedImageHistoryUndone.clear();
		this.selectedImage = ImageEditor.rotateRight180(this.selectedImage);
		this.selectedImageHistorySaved.push(this.selectedImage);
		this.refreshSelectedImage();
		this.saved = false;
	}
	
	/* rotate270 - rotates the image currently being edited 270 degrees to the right
	 */
	private void rotate270()
	{
		this.selectedImageHistoryUndone.clear();
		this.selectedImage = ImageEditor.rotateRight270(this.selectedImage);
		this.selectedImageHistorySaved.push(this.selectedImage);
		this.refreshSelectedImage();
		this.saved = false;
	}
	
	/* getImageFileType - returns the file format for an image based on its extension
	 *        extension - the extension of said image (".png", ".jpg", etc.)
	 */
	private String getImageFileType(String extension)
	{
		if (extension.equalsIgnoreCase(".png"))
		{
			return "png";
		}
		else if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg"))
		{
			return "jpg";
		}
		return null;
	}
	
	/* resetCropValues - resets the Point objects used in the cropping procedure
	 */
	private void resetCropValues()
	{
		this.cropVals = new Point[2];
		this.cropVals[0] = null;
		this.cropVals[1] = null;
	}



}
