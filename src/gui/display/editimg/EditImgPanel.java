// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// EditImgPanel.java

package gui.display.editimg;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import exceptions.*;
import gui.*;
import gui.components.img.*;
import gui.display.*;
import gui.display.dialogues.*;
import gui.display.finish.*;
import tools.FileHandler.*;

public class EditImgPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener, WindowListener
{
	
	private FrameManager manager;
	private ArrayList<ThumbnailImg> caseThumbnails;
	private Point[] cropCoords;
	private EditImgMenuBar menuBar;
	private EditedImg selectedImg;
	private EditedImg fittedImg;
	private Box mainContainer;
	private Box selectedImgContainer;
	private Box caseThumbnailContainer;
	private JButton loadNextThumbnails;
	private JButton loadPrevThumbnails;
	private JButton continueButton;
	private String caseNum;
	private int caseThumbnailIndex;
	private boolean cropping;
	
	public EditImgPanel(FrameManager manager, String caseNum)
	{
		this.manager = manager;
		this.caseNum = caseNum;
		this.caseThumbnailIndex = 0;
		this.cropping = false;
		this.caseThumbnails = this.getCaseThumbnails();
		this.cropCoords = new Point[2];
		this.menuBar = new EditImgMenuBar(this);
		this.mainContainer = Box.createVerticalBox();
		this.selectedImgContainer = Box.createHorizontalBox();
		this.caseThumbnailContainer = Box.createHorizontalBox();
		this.caseThumbnailContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.refreshCaseThumbnails(0);
		this.refreshSelectedImage(this.caseThumbnails.get(0).getFilePath());
		this.populateMainContainer();
		this.add(this.mainContainer);
		this.manager.getMainWindow().setWindowListener(this);
		this.manager.getMainWindow().setJMenuBar(this.menuBar);
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.revalidate();
		this.repaint();
	}
	
	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *                 1. attempts to load the next eight images in "caseThumbnailContainer"
	 *                 2. attempts to load the previous eight images in "caseThumbnailContainer"
	 *                 3. displays a dialogue prompting the user to save, pushes FinishPanel to the JFrame
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
		else if (e.getSource() == this.continueButton)
		{
			if (!this.selectedImg.getSaved())
			{
				int selection = JOptionPane.showConfirmDialog(this.manager.getMainWindow(), "Would you like to save the currently selected image before proceeding to the next step? All unsaved changes will be lost.", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (selection == JOptionPane.YES_OPTION)
				{
					this.saveImg();
				}
				else if (selection == JOptionPane.CANCEL_OPTION)
				{
					return;
				}
			}
		    this.manager.getMainWindow().pushPanel(new FinishPanel(manager, caseNum), "PEMS - Finish");
		}
	}
	
	/* mouseClicked - mandatory for any class implementing MouseListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	          e - the event in question
	 *              1. displays a dialogue prompting the user to save before pushing the selected thumbnail into the editing area
	 *              2. if the user is currently cropping and he or she right clicks, the cropping procedure is canceled
	 *              3. if the user clicks on "selectedImg" while he or she is cropping, the coordinates of the click are recorded and used in the procedure
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if (e.getSource() instanceof ThumbnailImg)
		{
			if (!this.selectedImg.getSaved())
			{
				int selection = JOptionPane.showConfirmDialog(this.manager.getMainWindow(), "Would you like to save the currently selected image before editing a new one? All unsaved changes will be lost.", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (selection == JOptionPane.YES_OPTION)
				{
					this.saveImg();
				}
				else if (selection == JOptionPane.CANCEL_OPTION)
				{
					return;
				}
			}
			ThumbnailImg selectedThumbnail = (ThumbnailImg)e.getSource();
			this.refreshSelectedImage(selectedThumbnail.getFilePath());
		}
		else if (SwingUtilities.isRightMouseButton(e) && this.cropping)
		{
			this.cropping = false;
			this.cropCoords = new Point[2];
			this.selectedImg.repaint();
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		else if (e.getSource() == this.fittedImg && this.cropping)
		{
			if (this.cropCoords[0] == null)
			{
				this.cropCoords[0] = e.getPoint();
			}
			else if (this.cropCoords[1] == null)
			{
				this.cropCoords[1] = e.getPoint();
				this.applyCrop();
			}
		}
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
	
	/* mouseDragged - mandatory for any class implementing MouseMotionListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	          e - the event in question
	 */
	public void mouseDragged(MouseEvent e) 
	{
		return;
	}

	/* mouseMoved - mandatory for any class implementing MouseMotionListener, checks the source of the MouseEvent and executes the appropriate code 
	 *	        e - the event in question
	 *            1. if the user moves the mouse over "selectedImg" while he or she is cropping, a red box is drawn over the area that he or she has selected
	 */
	public void mouseMoved(MouseEvent e) 
	{
		if (e.getSource() == this.fittedImg && this.cropping && this.cropCoords[0] != null && this.cropCoords[1] == null)
		{
			this.fittedImg.drawCropBox(this.cropCoords[0], e.getPoint());
		}
	}
	
	/* windowOpened - mandatory for any class implementing WindowListener, checks the source of the WindowEvent and executes the appropriate code 
	 *	          e - the event in question
	 */
	public void windowOpened(WindowEvent e) 
	{
		return;
	}

	/* windowClosing - mandatory for any class implementing WindowListener, checks the source of the WindowEvent and executes the appropriate code 
	 *	           e - the event in question
	 *               1. calls the quit() method
	 */
	public void windowClosing(WindowEvent e) 
	{
		this.quit();
	}

	/* windowClosed - mandatory for any class implementing WindowListener, checks the source of the WindowEvent and executes the appropriate code 
	 *	          e - the event in question
	 */
	public void windowClosed(WindowEvent e) 
	{
		return;
	}

	/* windowIconified - mandatory for any class implementing WindowListener, checks the source of the WindowEvent and executes the appropriate code 
	 *	             e - the event in question
	 */
	public void windowIconified(WindowEvent e) 
	{
		return;
	}

	/* windowDeiconified - mandatory for any class implementing WindowListener, checks the source of the WindowEvent and executes the appropriate code 
	 *	               e - the event in question
	 */
	public void windowDeiconified(WindowEvent e) 
	{
		return;
	}

	/* windowActivated - mandatory for any class implementing WindowListener, checks the source of the WindowEvent and executes the appropriate code 
	 *	             e - the event in question
	 */
	public void windowActivated(WindowEvent e) 
	{
		return;
	}

	/* windowDeactivated - mandatory for any class implementing WindowListener, checks the source of the WindowEvent and executes the appropriate code 
	 *	               e - the event in question
	 */
	public void windowDeactivated(WindowEvent e) 
	{
		return;
	}

	/* saveImg - attempts to save the image currently being edited by the user
	 */
	public void saveImg()
	{
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		SaveFileResult result = this.manager.getFileHandler().saveFile(this.caseNum, this.selectedImg);
		if (result == SaveFileResult.SAVE_FAILED)
		{
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			JOptionPane.showMessageDialog(this.manager.getMainWindow(), "Save unexpectedly failed! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			this.caseThumbnails = this.getCaseThumbnails();
			this.refreshCaseThumbnails(0);
			this.selectedImg.setSaved(true);
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	/* quit - displays a dialogue prompting the user to save his or her changes before proceeding, and then executes the appropriate exit procedure
	 */
	public void quit()
	{
		if (!this.selectedImg.getSaved())
		{
			int selection = JOptionPane.showConfirmDialog(this.manager.getMainWindow(), "Would you like to save the currently selected image before exiting the program?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			if (selection == JOptionPane.YES_OPTION)
			{
				this.saveImg();
			}
			else if (selection == JOptionPane.CANCEL_OPTION)
			{
				return;
			}
		}
		if (this.manager.getConfiguration().getPersistence())
		{
			this.manager.getMainWindow().setExtendedState(JFrame.ICONIFIED);
		}
		else
		{
			System.exit(0);
		}
	}
	
	/* undo - undoes the action most recently taken by the user
	 */
	public void undo()
	{
		this.selectedImg.undo();
		this.fittedImg.undo();
	}
	
	/* redo - redoes the action most recently undone by the user
	 */
	public void redo()
	{
		this.selectedImg.redo();
		this.fittedImg.redo();
	}
	
	/* removeImg - attempts to remove the currently selected image from the "cases" folder, leaving a copy of the file in the "backups" directory 
	 */
	public void removeImg()
	{
		int selection = JOptionPane.showConfirmDialog(this.manager.getMainWindow(), "Are you sure you would like to remove this image from the case?", "Remove Image", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (selection == JOptionPane.YES_OPTION)
		{
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			DeleteFileResult result = this.manager.getFileHandler().deleteFile(this.selectedImg);
			if (result == DeleteFileResult.DELETION_FAILED)
			{
				this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				JOptionPane.showMessageDialog(this.manager.getMainWindow(), "File deletion unexpectedly failed! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				this.caseThumbnails = this.getCaseThumbnails();
				this.refreshCaseThumbnails(0);
				if (this.caseThumbnails.size() == 0)
				{
					this.manager.getMainWindow().pushPanel(new FinishPanel(manager, caseNum), "PEMS - Finish");
				}
				else 
				{
					this.refreshSelectedImage(this.caseThumbnails.get(0).getFilePath());
				}
				this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
		else if (selection == JOptionPane.NO_OPTION)
		{
			return;
		}
	}
	
	/* antiAlias - applies an anti aliasing procedure to "selectedImg"
	 */
	public void antiAlias()
	{
		this.selectedImg.applyAntiAliasing();
		this.fittedImg.applyAntiAliasing();
	}
	
	/* brighten - brightens "selectedImg" by 10%
	 */
	public void brighten()
	{
		this.selectedImg.brightenImage();
		this.fittedImg.brightenImage();
	}
	
	/* darken - darkens "selectedImg" by 10%
	 */
	public void darken()
	{  
		this.selectedImg.darkenImage();
		this.fittedImg.darkenImage();
	}
	
	/* grayscale - converts "selectedImg" to grayscale format
	 */
	public void grayscale()
	{
		this.selectedImg.toGrayscale();
		this.fittedImg.toGrayscale();
	}
	
	/* resizeImg - initializes and displays the resize image dialogue
	 */
	public void resizeImg()
	{
		this.manager.openDialogue("Resize Image", new ResizeDialogue(this.manager, this, this.selectedImg.getImage().getWidth(), this.selectedImg.getImage().getHeight()), 40, 30);
	}
	
	/* resizeImg - applies the image resizing procedure to "selectedImg"
	 *  newWidth - the new width of the image in pixels
	 * newHeight - the new height of the image in pixels
	 */
	public void resizeImg(int newWidth, int newHeight)
	{
		this.selectedImg.resizeImage(newWidth, newHeight);
	}
	
	/* crop - initializes the cropping procedure for "selectedImg"
	 */
	public void crop()
	{
		this.cropping = true;
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}
	
	/* rotate90 - rotates "selectedImg" 90 degrees to the right
	 */
	public void rotate90()
	{
		this.selectedImg.rotateRight90();
		this.fittedImg.rotateRight90();
	}
	
	/* rotate180 - rotates "selectedImg" 180 degrees to the right
	 */
	public void rotate180()
	{
		this.selectedImg.rotateRight180();
		this.fittedImg.rotateRight180();
	}
	
	/* rotate270 - rotates "selectedImg" 270 degrees to the right
	 */
	public void rotate270()
	{
		this.selectedImg.rotateRight270();
		this.fittedImg.rotateRight270();
	}
	
	/* applyCrop - uses the Point values stored in "cropCoords" to apply a cropping procedure to "selectedImg"
	 */
	private void applyCrop()
	{
		int fittedX = (int)Math.min(this.cropCoords[0].getX(), this.cropCoords[1].getX());
		int fittedY = (int)Math.min(this.cropCoords[0].getY(), this.cropCoords[1].getY());
		int fittedWidth = (int)Math.abs(this.cropCoords[1].getX() - this.cropCoords[0].getX());
		int fittedHeight = (int)Math.abs(this.cropCoords[1].getY() - this.cropCoords[0].getY());
		int trueX = (this.selectedImg.getImage().getWidth() / this.fittedImg.getImage().getWidth()) * fittedX;
		int trueY = (this.selectedImg.getImage().getHeight() / this.fittedImg.getImage().getHeight()) * fittedY;
		int trueWidth = (this.selectedImg.getImage().getWidth() / this.fittedImg.getImage().getWidth()) * fittedWidth;
		int trueHeight = (this.selectedImg.getImage().getHeight() / this.fittedImg.getImage().getHeight()) * fittedHeight;
		this.cropping = false;
		this.cropCoords = new Point[2];
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.selectedImg.cropImage(trueX, trueY, trueWidth, trueHeight);
		this.fittedImg.cropImage(fittedX, fittedY, fittedWidth, fittedHeight);
	}
	
	/* getCaseThumbnails - returns an ArrayList of ThumbnailImg objects representing all of the image files that are a part of the currently selected case
	 */
	private ArrayList<ThumbnailImg> getCaseThumbnails()
	{
		ArrayList<ThumbnailImg> thumbnailList = new ArrayList<ThumbnailImg>();
	    File directory = new File("cases" + "/" + this.caseNum + "/");
		for (int i = 0; i < directory.listFiles().length; i++)
		{
			String currentName = directory.listFiles()[i].getName();
			String currentExtension = currentName.substring(currentName.indexOf('.')).toLowerCase();
			if ((currentExtension.equalsIgnoreCase(".png") || currentExtension.equalsIgnoreCase(".jpg") || currentExtension.equalsIgnoreCase(".jpeg")))
			{ 
				try 
				{
					ThumbnailImg currentThumbnail = ComponentGenerator.generateThumbnailImg(directory.listFiles()[i].getPath(), 80, CENTER_ALIGNMENT);
				    currentThumbnail.addMouseListener(this);
				    thumbnailList.add(currentThumbnail);
				} 
				catch (InvalidImgException e) 
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	    return thumbnailList;
	}
	
	/* refreshCaseThumbnails - refreshes the ThumbnailImg objects contained within "caseThumbnailContainer"
	 *    caseThumbnailIndex - the index within "caseThumbnails" of the first image to be displayed
	 */
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
	
	private void refreshSelectedImage(String filePath)
	{
		try 
		{
			this.selectedImg = ComponentGenerator.generateEditedImg(filePath, CENTER_ALIGNMENT);
			this.fittedImg = ComponentGenerator.fitImageToScreen(selectedImg);
			this.fittedImg.addMouseListener(this);
			this.fittedImg.addMouseMotionListener(this);
			this.selectedImgContainer.removeAll();
			this.selectedImgContainer.add(Box.createVerticalStrut(500));
			this.selectedImgContainer.add(this.fittedImg);
			this.selectedImgContainer.add(Box.createVerticalStrut(500));
		} 
		catch (InvalidImgException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.revalidate();
		this.repaint();
	}
	
	/* populateMainContainer - populates "mainContainer" with the necessary components
	 */
	private void populateMainContainer()
	{
		this.continueButton = ComponentGenerator.generateButton("Continue", this, CENTER_ALIGNMENT);
		this.mainContainer.add(this.selectedImgContainer);
		this.mainContainer.add(Box.createVerticalStrut(40));
		this.mainContainer.add(this.caseThumbnailContainer);
		this.mainContainer.add(Box.createVerticalStrut(10));
		this.mainContainer.add(this.continueButton);
	}
	
}