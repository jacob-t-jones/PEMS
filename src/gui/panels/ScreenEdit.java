// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenEdit.java

package gui.panels;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import tools.ImageEditor;
import gui.*;

public class ScreenEdit extends JPanel implements ActionListener, MouseListener
{
	
	private FrameManager manager;
	private ArrayList<Thumbnail> caseThumbnails;
	private ArrayList<BufferedImage> selectedImageHistory;
	private BufferedImage selectedImage;
	private Box mainContainer;
	private Box selectedImageContainer;
	private Box caseThumbnailContainer;
	private JLabel selectedImageLabel;
	private JButton loadNextThumbnails;
	private JButton loadPrevThumbnails;
	private JButton nextButton;
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
	private JMenuItem rotate90MenuItem;
	private JMenuItem rotate180MenuItem;
	private JMenuItem rotate270MenuItem;
	private String caseNum;
	private String selectedImageName;
	private int selectedImageHistoryIndex;
	private int caseThumbnailIndex;
	
	public ScreenEdit(FrameManager manager, String caseNum)
	{
		this.manager = manager;
		this.selectedImageHistory = new ArrayList<BufferedImage>();
		this.caseNum = caseNum;
		this.selectedImageHistoryIndex = 0;
		this.caseThumbnailIndex = 0;
		this.mainContainer = Box.createVerticalBox();
		this.selectedImageContainer = Box.createHorizontalBox();
		this.caseThumbnailContainer = Box.createHorizontalBox();
		this.caseThumbnailContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.caseThumbnails = getCaseThumbnails();
		this.refreshCaseThumbnails(0);
		this.mainContainer.add(this.selectedImageContainer);
		this.mainContainer.add(Box.createVerticalStrut(40));
		this.mainContainer.add(this.caseThumbnailContainer);
		this.mainContainer.add(this.nextButton);
		this.add(this.mainContainer);
		this.constructMenuBar();
		this.manager.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
		else if (e.getSource() == this.saveImageMenuItem)
		{
			try 
			{
			    ImageIO.write(this.selectedImage, "jpg", new File("cases/" + this.caseNum + "/" + this.selectedImageName));
			} 
			catch (IOException e1) 
			{
			    System.out.println("Error - Save failed");
			    e1.printStackTrace();
			    return;
			}
		}
		else if (e.getSource() == this.renameImageMenuItem)
		{
			this.manager.displayRenameDialogue();
		}
		else if (e.getSource() == this.quitMenuItem)
		{
			System.exit(0);
		}
		else if (e.getSource() == this.undoMenuItem)
		{
			if (this.selectedImageHistoryIndex > 0)
			{
				this.selectedImageHistoryIndex--;
				this.selectedImage = this.selectedImageHistory.get(this.selectedImageHistoryIndex);
				this.refreshSelectedImage();
			}
		}
		else if (e.getSource() == this.redoMenuItem)
		{
			if (this.selectedImageHistoryIndex < this.selectedImageHistory.size() - 1)
			{
				this.selectedImageHistoryIndex++;
				this.selectedImage = this.selectedImageHistory.get(this.selectedImageHistoryIndex);
				this.refreshSelectedImage();
			}
		}
		else if (e.getSource() == this.removeImageMenuItem)
		{
			
		}
	    else if (e.getSource() == this.antiAliasMenuItem)
		{
	    	this.selectedImageHistoryIndex++;
	    	this.clearForwardHistory();
			this.selectedImage = ImageEditor.applyAntiAliasing(this.selectedImage);
	    	this.selectedImageHistory.add(this.selectedImage);
			this.refreshSelectedImage();
		}
		else if (e.getSource() == this.brightenMenuItem)
		{
			this.selectedImageHistoryIndex++;
	    	this.clearForwardHistory();
			this.selectedImage = ImageEditor.brightenImage(this.selectedImage);
	    	this.selectedImageHistory.add(this.selectedImage);
			this.refreshSelectedImage();
		}
		else if (e.getSource() == this.darkenMenuItem)
		{
			this.selectedImageHistoryIndex++;	    	
			this.clearForwardHistory();
			this.selectedImage = ImageEditor.darkenImage(this.selectedImage);
	    	this.selectedImageHistory.add(this.selectedImage);
			this.refreshSelectedImage();
		}
		else if (e.getSource() == this.grayscaleMenuItem)
		{
			this.selectedImageHistoryIndex++;
	    	this.clearForwardHistory();
			this.selectedImage = ImageEditor.toGrayscale(this.selectedImage);
	    	this.selectedImageHistory.add(this.selectedImage);
			this.refreshSelectedImage();
		}
		else if (e.getSource() == this.resizeMenuItem)
		{
			
		}
		else if (e.getSource() == this.rotate90MenuItem)
		{
			this.selectedImageHistoryIndex++;
	    	this.clearForwardHistory();
			this.selectedImage = ImageEditor.rotateRight90(this.selectedImage);
	    	this.selectedImageHistory.add(this.selectedImage);
			this.refreshSelectedImage();
		}
		else if (e.getSource() == this.rotate180MenuItem)
		{
			this.selectedImageHistoryIndex++;
	    	this.clearForwardHistory();
			this.selectedImage = ImageEditor.rotateRight180(this.selectedImage);
	    	this.selectedImageHistory.add(this.selectedImage);
			this.refreshSelectedImage();
		}
		else if (e.getSource() == this.rotate270MenuItem)
		{
			this.selectedImageHistoryIndex++;
	    	this.clearForwardHistory();
			this.selectedImage = ImageEditor.rotateRight270(this.selectedImage);
	    	this.selectedImageHistory.add(this.selectedImage);
			this.refreshSelectedImage();
		}
		else if (e.getSource() == this.nextButton)
		{
			this.manager.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
        	if (this.displayedImagePlace + 15 < this.displayedThumbnails.size())
        	{
        		this.refreshDisplayedThumbnails(this.displayedImagePlace + 15);
        	}
        	this.manager.pushPanel(new ScreenEdit(manager, caseNum), "PEMS - Finish");
		}
	}
	
	public void mouseClicked(MouseEvent e) 
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
    	this.selectedImageName = selectedThumbnail.getFileName();
	    this.selectedImageHistory.clear();
	    this.selectedImageHistory.add(this.selectedImage);
	    this.selectedImageHistoryIndex = 0;
	    this.refreshSelectedImage();
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
	
	private void clearForwardHistory()
	{
		int i = this.selectedImageHistoryIndex;
		while (i < this.selectedImageHistory.size())
		{
			this.selectedImageHistory.remove(i);
		}
	}
	
	private void refreshSelectedImage()
	{
		this.selectedImageContainer.removeAll();
		if (this.selectedImage.getHeight() > 500)
		{
			this.selectedImageLabel = ComponentGenerator.generateLabel(ImageEditor.resizeImage(this.selectedImage, 500), CENTER_ALIGNMENT);
		}
		else if (this.selectedImage.getWidth() > 1000)
		{
			this.selectedImageLabel = ComponentGenerator.generateLabel(ImageEditor.resizeImage(this.selectedImage, 1000), CENTER_ALIGNMENT);
		}
		else
		{
			this.selectedImageLabel = ComponentGenerator.generateLabel(this.selectedImage, CENTER_ALIGNMENT);
		}
		this.selectedImageContainer.setMaximumSize(new Dimension(1000, 500));
		this.selectedImageContainer.setMinimumSize(new Dimension(1000, 500));
		this.selectedImageContainer.add(Box.createHorizontalGlue());
		this.selectedImageContainer.add(Box.createVerticalStrut(500));
		this.selectedImageContainer.add(this.selectedImageLabel);
		this.selectedImageContainer.add(Box.createVerticalStrut(500));
		this.selectedImageContainer.add(Box.createHorizontalGlue());
		this.revalidate();
		this.repaint();
	}
	
	private ArrayList<Thumbnail> getCaseThumbnails()
	{
		ArrayList<Thumbnail> thumbnailList = new ArrayList<Thumbnail>();
	    File directory = new File("cases", "/" + this.caseNum + "/");
		String[] fileNames = directory.list();
		for (int i = 0; i < fileNames.length; i++)
		{
			String currentFileName = fileNames[i];
			if (this.validateExtension(currentFileName))
			{
				BufferedImage currentImage = null;
				String currentLocation = "cases/" + this.caseNum + "/" + fileNames[i];
			    try 
			    {   
			    	 currentImage = ImageIO.read(new File(currentLocation));
			    }
			    catch (IOException e)
			    {
			    	System.out.println("Error - Unable to read image into memory");
			    	e.printStackTrace();
				    return null;
			    }	    	
			    Thumbnail currentThumb = ComponentGenerator.generateThumbnail(ImageEditor.resizeThumbnail(currentImage, 80), currentLocation, currentFileName);
				currentThumb.setAlignmentX(CENTER_ALIGNMENT);
			    currentThumb.addMouseListener(this);
			    thumbnailList.add(currentThumb);
			}
		}
	    return thumbnailList;
	}
	
	private void refreshCaseThumbnails(int caseThumbnailIndex)
	{
		this.loadNextThumbnails = ComponentGenerator.generateButton("Next     >", this);
		this.loadPrevThumbnails = ComponentGenerator.generateButton("<     Prev", this); 
		this.nextButton = ComponentGenerator.generateButton("Continue", this);
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
	
	private boolean validateExtension(String fileName)
	{
		if (fileName.length() > 4)
		{
			String threeLetterExt = fileName.substring(fileName.length() - 4, fileName.length());
			String fourLetterExt = fileName.substring(fileName.length() - 5, fileName.length());
			if (threeLetterExt.equalsIgnoreCase(".png") || threeLetterExt.equalsIgnoreCase(".jpg") || fourLetterExt.equalsIgnoreCase(".jpeg"))
			{
				return true;
			}
		}
		return false;		
	}
	
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
		this.imageMenu.add(this.rotate90MenuItem);
		this.imageMenu.add(this.rotate180MenuItem);
		this.imageMenu.add(this.rotate270MenuItem);
		this.menuBar.add(this.fileMenu);
		this.menuBar.add(this.editMenu);
		this.menuBar.add(this.caseMenu);
		this.menuBar.add(this.imageMenu);
		this.manager.setMenuBar(this.menuBar);
	}

}
