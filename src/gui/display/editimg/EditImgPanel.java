// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// EditImgPanel.java

package gui.display.editimg;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.imgscalr.*;
import backend.exceptions.*;
import backend.storage.file.*;
import backend.storage.file.LiveFile.*;
import backend.storage.file.img.*;
import gui.*;
import gui.components.icon.*;
import gui.display.*;
import gui.display.dialogues.*;
import gui.display.finish.*;
import gui.display.start.*;

/** Subclass of <code>JPanel</code> displayed when the user is editing an image;
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class EditImgPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener, WindowListener
{
	
	private FrameManager manager;
	private ArrayList<CaseIcon> caseIcons;
	private Point[] cropCoords;
	private EditImgMenuBar menuBar;
	private Img selectedImg;
	private ImgIcon selectedIcon;
	private Box mainContainer;
	private Box selectedImgContainer;
	private Box caseIconContainer;
	private JButton loadNextIconsButton;
	private JButton loadPrevIconsButton;
	private JButton continueButton;
	private String caseNum;
	private int caseIconIndex;
	private boolean cropping;
	
	/** Populates this panel with all of the necessary graphical components.
	 * 
	 *  @param manager the instance of <code>FrameManager</code> that initialized this panel
	 *  @param caseNum the number of the case currently being edited
	 *  @throws NullPointerException if any parameters are null
	 */
	public EditImgPanel(FrameManager manager, String caseNum)
	{
		if (manager == null || caseNum == null)
		{
			throw new NullPointerException();
		}
		this.manager = manager;
		this.caseNum = caseNum;
		this.caseIconIndex = 0;
		this.cropping = false;
		this.caseIcons = this.generateCaseIcons();
		this.cropCoords = new Point[2];
		this.menuBar = new EditImgMenuBar(this);
		this.mainContainer = Box.createVerticalBox();
		this.selectedImgContainer = Box.createHorizontalBox();
		this.caseIconContainer = Box.createHorizontalBox();
		this.caseIconContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.refreshCaseIcons(0);
		this.setSelectedImage(this.caseIcons.get(0).getParentFile());
		this.populateMainContainer();
		this.add(this.mainContainer);
		this.manager.getMainWindow().setWindowListener(this);
		this.manager.getMainWindow().setJMenuBar(this.menuBar);
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.revalidate();
		this.repaint();
	}
	
	/** Mandatory method required in all classes that implement <code>ActionListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li><code>loadNextIconsButton</code></li>
	 *  		<ul>
	 *  			<li>Attempts to load the next eight images in <code>caseIconContainer</code>.</li>
	 *  		</ul>
	 *  	<li><code>loadPrevIconsButton</code></li>
	 *  		<ul>
	 *  			<li>Attempts to load the previous eight images in <code>caseIconContainer</code>.</li>
	 *  		</ul>
	 *  	<li><code>continueButton</code></li>
	 *  		<ul>
	 *  			<li>Displays a dialogue prompting the user to save, pushes <code>FinishPanel</code> to the <code>JFrame</code>.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void actionPerformed(ActionEvent e)
	{ 
		if (e.getSource() == this.loadNextIconsButton)
		{
        	if (this.caseIconIndex + 8 < this.caseIcons.size())
        	{
        		this.refreshCaseIcons(this.caseIconIndex + 8);
        	}
		}
		else if (e.getSource() == this.loadPrevIconsButton)
		{
          	if (this.caseIconIndex >= 8)
        	{
        		this.refreshCaseIcons(this.caseIconIndex - 8);
        	}
		}
		else if (e.getSource() == this.continueButton)
		{
			if (!this.selectedImg.getParentFile().getSaved())
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
			this.manager.getMainWindow().setWindowListener(this.manager.getMainWindow());
			this.manager.getMainWindow().removeMenuBar();
		    this.manager.getMainWindow().pushPanel(new FinishPanel(manager, caseNum), "PEMS - Finish");
		}
	}
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li>An instance of <code>CaseIcon</code></li>
	 *  		<ul>
	 *  			<li>Prompts the user to save before pushing the image associated with the selected <code>CaseIcon</code> into the editing area.</li>
	 *  		</ul>
	 *  	<li>Any component</li>
	 *  		<ul>
	 *  			<li>If cropping has been initiated and the user right clicked, the cropping procedure is cancelled.</li>
	 *  		</ul>
	 *  	<li><code>selectedIcon</code></li>
	 *  		<ul>
	 *  			<li>If cropping has been initiated, the next step in the cropping procedure is carried out.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if (e.getSource() instanceof CaseIcon)
		{
			if (!this.selectedImg.getParentFile().getSaved())
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
			CaseIcon selectedIcon = (CaseIcon)e.getSource();
			this.setSelectedImage(selectedIcon.getParentFile());
		}
		else if (SwingUtilities.isRightMouseButton(e) && this.cropping)
		{
			this.cropping = false;
			this.cropCoords = new Point[2];
			this.selectedIcon.repaint();
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		else if (e.getSource() == this.selectedIcon && this.cropping)
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
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mousePressed(MouseEvent e) 
	{
		return;
	}
	 
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseReleased(MouseEvent e)
	{
		return;
	}
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseEntered(MouseEvent e) 
	{
		return;
	}
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseExited(MouseEvent e) 
	{
		return;
	}	
	
	/** Mandatory method required in all classes that implement <code>MouseMotionListener</code>.
	 */
	public void mouseDragged(MouseEvent e) 
	{
		return;
	}

	/** Mandatory method required in all classes that implement <code>MouseMotionListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li><code>selectedIcon</code></li>
	 *  		<ul>
	 *  			<li>If cropping has been initiated and the user has already selected the first point, a crop box is drawn on <code>selectedIcon</code>.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void mouseMoved(MouseEvent e) 
	{
		if (e.getSource() == this.selectedIcon && this.cropping && this.cropCoords[0] != null && this.cropCoords[1] == null)
		{
			this.selectedIcon.drawCropBox(this.cropCoords[0], e.getPoint());
		}
	}
	
	/** Mandatory method required in all classes that implement <code>WindowListener</code>.
	 */
	public void windowOpened(WindowEvent e) 
	{
		return;
	}

	/** Mandatory method required in all classes that implement <code>WindowListener</code>.
	 * 
	 *  Calls the <code>quit</code> method.
	 */
	public void windowClosing(WindowEvent e) 
	{
		this.quit();
	}

	/** Mandatory method required in all classes that implement <code>WindowListener</code>.
	 */
	public void windowClosed(WindowEvent e) 
	{
		return;
	}

	/** Mandatory method required in all classes that implement <code>WindowListener</code>.
	 */
	public void windowIconified(WindowEvent e) 
	{
		return;
	}

	/** Mandatory method required in all classes that implement <code>WindowListener</code>.
	 */
	public void windowDeiconified(WindowEvent e) 
	{
		return;
	}

	/** Mandatory method required in all classes that implement <code>WindowListener</code>.
	 */
	public void windowActivated(WindowEvent e) 
	{
		return;
	}

	/** Mandatory method required in all classes that implement <code>WindowListener</code>.
	 */
	public void windowDeactivated(WindowEvent e) 
	{
		return;
	}
	
	/** Attempts to save the image currently being edited by the user.
	 */
	public void saveImg()
	{
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		SaveFileResult result = this.selectedImg.getParentFile().saveFile();
		if (result == SaveFileResult.SAVE_FAILED)
		{
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			JOptionPane.showMessageDialog(this.manager.getMainWindow(), "Save unexpectedly failed! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			this.caseIcons = this.generateCaseIcons();
			this.refreshCaseIcons(0);
			this.selectedImg.getParentFile().setSaved(true);
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	/** Displays a dialogue prompting the user to save his or her changes before proceeding, and then executes the appropriate exit procedure.
	 */
	public void quit()
	{
		if (!this.selectedImg.getParentFile().getSaved())
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
			this.manager.getMainWindow().setResizable(true);
			this.manager.getMainWindow().setBounds(50, 50);
			this.manager.getMainWindow().setResizable(false);
			this.manager.getMainWindow().setWindowListener(this.manager.getMainWindow());
			this.manager.getMainWindow().removeMenuBar();
			this.manager.closeDialogue();
			this.manager.getMainWindow().pushPanel(new StartPanel(this.manager), "PEMS (Police Evidence Management System) Version 0.1");
		}
		else
		{
			System.exit(0);
		}
	}
	
	/** Undoes the action most recently taken by the user.
	 */
	public void undo()
	{
		this.selectedImg.undo();
		this.refreshSelectedImage();
	}
	
	/** Redoes the action most recently undone by the user.
	 */
	public void redo()
	{
		this.selectedImg.redo();
		this.refreshSelectedImage();
	}
	
	/** Attempts to remove the currently selected image from the <i>cases</i> folder, leaving a copy of the file in the <i>backups</i> directory.
	 */
	public void removeImg()
	{
		int selection = JOptionPane.showConfirmDialog(this.manager.getMainWindow(), "Are you sure you would like to remove this image from the case?", "Remove Image", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (selection == JOptionPane.YES_OPTION)
		{
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			DeleteFileResult result = this.selectedImg.getParentFile().deleteFile();
			if (result == DeleteFileResult.DELETION_FAILED)
			{
				this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				JOptionPane.showMessageDialog(this.manager.getMainWindow(), "File deletion unexpectedly failed! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				this.caseIcons = this.generateCaseIcons();
				this.refreshCaseIcons(0);
				if (this.caseIcons.size() == 0)
				{
					this.manager.getMainWindow().pushPanel(new FinishPanel(manager, caseNum), "PEMS - Finish");
				}
				else 
				{
					this.setSelectedImage(this.caseIcons.get(0).getParentFile());
				}
				this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
		else if (selection == JOptionPane.NO_OPTION)
		{
			return;
		}
	}
	
	/** Applies an anti aliasing procedure to <code>selectedImg</code>.
	 */
	public void antiAlias()
	{
		this.selectedImg.applyAntiAliasing();
		this.refreshSelectedImage();
	}
	
	/** Brightens <code>selectedImg</code> by 10%.
	 */
	public void brighten()
	{
		this.selectedImg.brightenImage();
		this.refreshSelectedImage();
	}
	
	/** Darkens <code>selectedImg</code> by 10%.
	 */
	public void darken()
	{  
		this.selectedImg.darkenImage();
		this.refreshSelectedImage();
	}
	
	/** Converts <code>selectedImg</code> to grayscale format.
	 */
	public void grayscale()
	{
		this.selectedImg.toGrayscale();
		this.refreshSelectedImage();
	}
	
	/** Initializes and displays the resize image dialogue.
	 */
	public void resizeImg()
	{
		this.manager.openDialogue("Resize Image", new ResizeDialogue(this.manager, this, this.selectedImg.getImage().getWidth(), this.selectedImg.getImage().getHeight()), 40, 30);
	}
	
	/** Applies the image resizing procedure to <code>selectedImg</code>.
	 * 
	 *  @param newWidth the new width of the image in pixels
	 *  @param newHeight the new height of the image in pixels
	 */
	public void resizeImg(int newWidth, int newHeight)
	{
		this.selectedImg.resizeImage(Scalr.Method.ULTRA_QUALITY, newWidth, newHeight);
		this.refreshSelectedImage();
	}
	
	/** Initializes the cropping procedure for <code>selectedImg</code>.
	 */
	public void crop()
	{
		this.cropping = true;
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}
	
	/** Rotates <code>selectedImg</code> 90 degrees to the right.
	 */
	public void rotate90()
	{
		this.selectedImg.rotateRight90();
		this.refreshSelectedImage();
	}
	
	/** Rotates <code>selectedImg</code> 180 degrees to the right.
	 */
	public void rotate180()
	{
		this.selectedImg.rotateRight180();
		this.refreshSelectedImage();
	}
	
	/** Rotates <code>selectedImg</code> 270 degrees to the right.
	 */
	public void rotate270()
	{
		this.selectedImg.rotateRight270();
		this.refreshSelectedImage();
	}
	
	/** Uses the <code>Point</code> values stored in <code>cropCoords</code> to apply a cropping procedure to <code>selectedImg</code>.
	 */
	private void applyCrop()
	{
		int fittedX = (int)Math.min(this.cropCoords[0].getX(), this.cropCoords[1].getX());
		int fittedY = (int)Math.min(this.cropCoords[0].getY(), this.cropCoords[1].getY());
		int fittedWidth = (int)Math.abs(this.cropCoords[1].getX() - this.cropCoords[0].getX());
		int fittedHeight = (int)Math.abs(this.cropCoords[1].getY() - this.cropCoords[0].getY());
		int trueX = (this.selectedImg.getImage().getWidth() / this.selectedIcon.getImage().getWidth()) * fittedX;
		int trueY = (this.selectedImg.getImage().getHeight() / this.selectedIcon.getImage().getHeight()) * fittedY;
		int trueWidth = (this.selectedImg.getImage().getWidth() / this.selectedIcon.getImage().getWidth()) * fittedWidth;
		int trueHeight = (this.selectedImg.getImage().getHeight() / this.selectedIcon.getImage().getHeight()) * fittedHeight;
		this.cropping = false;
		this.cropCoords = new Point[2];
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.selectedImg.cropImage(trueX, trueY, trueWidth, trueHeight);
		this.refreshSelectedImage();
	}
	
	/** Refreshes the <code>CaseIcon</code> objects contained within <code>caseIconContainer</code>.
	 * 
	 *  @param caseIconIndex the index within <code>caseIcons</code> of the first image to be displayed
	 */
	private void refreshCaseIcons(int caseIconIndex)
	{
		this.loadNextIconsButton = ComponentGenerator.generateButton("Next     >", this);
		this.loadPrevIconsButton = ComponentGenerator.generateButton("<     Prev", this); 
		this.caseIconContainer.removeAll();
		this.caseIconIndex = caseIconIndex;
		this.caseIconContainer.add(this.loadPrevIconsButton);
		for (int i = 0; i < 8; i++)
		{
			Box col = Box.createVerticalBox();
			col.setMinimumSize(new Dimension(100, 100));
			col.setMaximumSize(new Dimension(100, 100));
			if (this.caseIconIndex < this.caseIcons.size())
			{
				col.add(Box.createVerticalGlue());
				col.add(Box.createHorizontalStrut(100));
				col.add(this.caseIcons.get(this.caseIconIndex));
				col.add(Box.createHorizontalStrut(100));
				col.add(Box.createVerticalGlue());
			}
			else
			{
				col.add(Box.createVerticalGlue());
				col.add(Box.createHorizontalStrut(100));
				col.add(Box.createVerticalGlue());
			}
			this.caseIconContainer.add(col);
			this.caseIconIndex++;
		}
		this.caseIconContainer.add(this.loadNextIconsButton);
		this.caseIconContainer.add(Box.createVerticalStrut(100));
		this.caseIconIndex = caseIconIndex;
		this.revalidate();
		this.repaint();
	}
	
	/** Refreshes the image currently displayed in the editing area.
	 */
	private void refreshSelectedImage()
	{
		this.selectedIcon = this.selectedImg.getIcon();
		this.selectedIcon.addMouseListener(this);
		this.selectedIcon.addMouseMotionListener(this);
		this.selectedImgContainer.removeAll();
		this.selectedImgContainer.add(Box.createVerticalStrut(500));
		this.selectedImgContainer.add(this.selectedIcon);
		this.selectedImgContainer.add(Box.createVerticalStrut(500)); 
		this.revalidate();
		this.repaint();
	}
	
	/** Sets <code>selectedImg</code> to a new <code>Img</code> object created using the <code>LiveFile</code> passed in as a parameter.
	 * 
	 *  @param file the <code>LiveFile</code> in question
	 */
	private void setSelectedImage(LiveFile file)
	{
		try 
		{
			this.selectedImg = new Img(file);
			this.refreshSelectedImage();
		} 
		catch (InvalidFileException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/** Adds <code>selectedImgContainer</code>, <code>caseIconContainer</code>, and <code>continueButton</code> to <code>mainContainer</code>.
	 */
	private void populateMainContainer()
	{
		this.continueButton = ComponentGenerator.generateButton("Continue", this, CENTER_ALIGNMENT);
		this.mainContainer.add(this.selectedImgContainer);
		this.mainContainer.add(Box.createVerticalStrut(40));
		this.mainContainer.add(this.caseIconContainer);
		this.mainContainer.add(Box.createVerticalStrut(10));
		this.mainContainer.add(this.continueButton);
	}
	
	/** Returns an <code>ArrayList</code> of <code>CaseIcon</code> objects representing all of the image files that are a part of the currently selected case.
	 * 
	 *  @return <code>ArrayList</code> of <code>CaseIcon</code> objects representing all of the image files that are a part of the currently selected case
	 */
	private ArrayList<CaseIcon> generateCaseIcons()
	{
	    for (int i = 0; i < this.manager.getStorageManager().getCases().size(); i++)
	    {
	    	if (this.manager.getStorageManager().getCases().get(i).getCaseNum().equalsIgnoreCase(this.caseNum))
	    	{
	    		return this.manager.getStorageManager().getCases().get(i).getCaseIcons(80, this, CENTER_ALIGNMENT);
	    	}
	    }
	    return new ArrayList<CaseIcon>();
	}
	
}