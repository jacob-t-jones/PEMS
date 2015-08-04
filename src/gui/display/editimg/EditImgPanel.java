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

public class EditImgPanel extends JPanel implements ActionListener, MouseListener, WindowListener
{
	
	private FrameManager manager;
	private ArrayList<ThumbnailImg> caseThumbnails;
	private EditedImg selectedImage;
	private EditImgMenuBar menuBar;
	private Box mainContainer;
	private Box selectedImageContainer;
	private Box caseThumbnailContainer;
	private JButton loadNextThumbnails;
	private JButton loadPrevThumbnails;
	private JButton continueButton;
	private String caseNum;
	private int caseThumbnailIndex;
	private boolean cropping;
	
	/*private Point[] cropVals;
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
	private DrawRect cropBox;
	private Box cropContainer;
	private Point mousePoint;*/
	
	public EditImgPanel(FrameManager manager, String caseNum)
	{
		this.manager = manager;
		this.caseNum = caseNum;
		this.caseThumbnailIndex = 0;
		this.cropping = false;
		this.caseThumbnails = this.getCaseThumbnails();
		this.menuBar = new EditImgMenuBar(this);
		this.mainContainer = Box.createVerticalBox();
		this.selectedImageContainer = Box.createHorizontalBox();
		this.caseThumbnailContainer = Box.createHorizontalBox();
		this.caseThumbnailContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.refreshCaseThumbnails(0);
		this.populateMainContainer();
		this.add(this.mainContainer);
		this.manager.getMainWindow().setWindowListener(this);
		this.manager.getMainWindow().setJMenuBar(this.menuBar);
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.revalidate();
		this.repaint();
	}
	
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
			if (!this.selectedImage.getSaved())
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
	
	public void mouseClicked(MouseEvent e) 
	{
		if (e.getSource() instanceof ThumbnailImg)
		{
			if (!this.selectedImage.getSaved())
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
			try 
			{
				this.selectedImage = ComponentGenerator.generateEditedImg(selectedThumbnail.getFilePath(), CENTER_ALIGNMENT);
			} 
			catch (InvalidImgException e1) 
			{
				System.out.println(e1.getMessage());
				e1.printStackTrace();
				return;
			}
			this.selectedImageContainer.removeAll();
			this.selectedImageContainer.add(this.selectedImage);
			this.revalidate();
			this.repaint();
		}
	}
	
	public void mousePressed(MouseEvent e) 
	{
		return;
	}
	
	public void mouseReleased(MouseEvent e) 
	{
		return;
	}
	
	public void mouseEntered(MouseEvent e) 
	{
		return;
	}

	public void mouseExited(MouseEvent e) 
	{
		return;
	}
	
	public void windowOpened(WindowEvent e) 
	{
		return;
	}

	public void windowClosing(WindowEvent e) 
	{
		this.quit();
	}

	public void windowClosed(WindowEvent e) 
	{
		return;
	}

	public void windowIconified(WindowEvent e) 
	{
		return;
	}

	public void windowDeiconified(WindowEvent e) 
	{
		return;
	}

	public void windowActivated(WindowEvent e) 
	{
		return;
	}

	public void windowDeactivated(WindowEvent e) 
	{
		return;
	}

	public void saveImg()
	{
		SaveFileResult result = this.manager.getFileHandler().saveFile(this.caseNum, this.selectedImage);
		if (result == SaveFileResult.SAVE_FAILED)
		{
			JOptionPane.showMessageDialog(this.manager.getMainWindow(), "Save unexpectedly failed! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			this.selectedImage.setSaved(true);
		}
	}
	
	public void quit()
	{
		if (!this.selectedImage.getSaved())
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
	
	public void undo()
	{
		this.selectedImage.undo();
	}
	
	public void redo()
	{
		this.selectedImage.redo();
	}
	
	public void removeImg()
	{
		int selection = JOptionPane.showConfirmDialog(this.manager.getMainWindow(), "Are you sure you would like to remove this image from the case?", "Remove Image", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (selection == JOptionPane.YES_OPTION)
		{
			DeleteFileResult result = this.manager.getFileHandler().deleteFile(this.selectedImage);
			if (result == DeleteFileResult.DELETION_FAILED)
			{
				JOptionPane.showMessageDialog(this.manager.getMainWindow(), "File deletion unexpectedly failed! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				return;
			}
		}
		else if (selection == JOptionPane.NO_OPTION)
		{
			return;
		}
	}
	
	public void antiAlias()
	{
		this.selectedImage.applyAntiAliasing();
	}
	
	public void brighten()
	{
		this.selectedImage.brightenImage();
	}
	
	public void darken()
	{  
		this.selectedImage.darkenImage();
	}
	
	public void grayscale()
	{
		this.selectedImage.toGrayscale();
	}
	
	public void resizeImg()
	{
		this.manager.openDialogue("Resize Image", new ResizeDialogue(this.manager, this, this.selectedImage.getImage().getWidth(), this.selectedImage.getImage().getHeight()), 40, 30);
	}
	
	public void resizeImg(int newWidth, int newHeight)
	{
		this.selectedImage.resizeImage(newWidth, newHeight);
	}
	
	public void crop()
	{
		this.cropping = true;
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}
	
	public void rotate90()
	{
		this.selectedImage.rotateRight90();
	}
	
	public void rotate180()
	{
		this.selectedImage.rotateRight180();
	}
	
	public void rotate270()
	{
		this.selectedImage.rotateRight270();
	}
	
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
	
	private void populateMainContainer()
	{
		this.continueButton = ComponentGenerator.generateButton("Continue", this, CENTER_ALIGNMENT);
		try 
		{
			this.selectedImage = ComponentGenerator.generateEditedImg(this.caseThumbnails.get(0).getFilePath(), CENTER_ALIGNMENT);
			this.selectedImageContainer.add(this.selectedImage);
		} 
		catch (InvalidImgException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.mainContainer.add(this.selectedImageContainer);
		this.mainContainer.add(Box.createVerticalStrut(40));
		this.mainContainer.add(this.caseThumbnailContainer);
		this.mainContainer.add(Box.createVerticalStrut(10));
		this.mainContainer.add(this.continueButton);
	}

	
	
	
	
	
	
	
	
	
	
	/*private void drawRec(Graphics g, Point initPoint)
	{
		//this.remove(cropBox);
		cropBox = new DrawRect(this.getGraphics(), initPoint);
		cropBox.paint(g, initPoint);
		//this.revalidate();
		this.cropBox.repaint();
	}

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
		this.cropping = false;
		this.resetCropValues();
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.selectedImage.cropImage(x, y, width, height);
	}
	
	private void resetCropValues()
	{
		this.cropVals = new Point[2];
		this.cropVals[0] = null;
		this.cropVals[1] = null;
	}*/
	
}