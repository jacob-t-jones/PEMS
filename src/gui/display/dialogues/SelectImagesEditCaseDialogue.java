// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// SelectImagesEditCaseDialogue.java

package gui.display.dialogues;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import backend.exceptions.*;
import backend.storage.Case.*;
import gui.components.icon.*;
import gui.display.*;
import gui.display.editimg.*;

/** Subclass of <code>SelectImagesDialogue</code> displayed when the user is selecting images to add to an already existing case.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class SelectImagesEditCaseDialogue extends SelectImagesDialogue implements MouseListener
{

	private ArrayList<PeripheralIcon> peripheralIcons;
	private ArrayList<PeripheralIcon> selectedIcons;
	
	/** Calls the parent constructor, populates <code>peripheralIcons</code>, initializes <code>selectedIcons</code>.
	 * 
	 *  @param manager the instance of <code>FrameManager</code> that initialized this dialogue
	 *  @param caseNum the number of the case that the images are being selected for
	 */
	public SelectImagesEditCaseDialogue(FrameManager manager, String caseNum)
	{
		super(manager, caseNum, "Please select all of the images you would like to add to the case:");
		this.peripheralIcons = this.generateIcons();
		this.selectedIcons = new ArrayList<PeripheralIcon>();
		super.refreshIconContainer(0, this.peripheralIcons, this.selectedIcons);
		super.revalidate();
		super.repaint();
	}
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li>An instance of <code>PeripheralIcon</code> contained within both the <code>peripheralIcons</code> and <code>selectedIcons</code> <code>ArrayLists</code>.</li>
	 *  		<ul>
	 *  			<li>Said instance is removed from <code>selectedIcons</code>.</li>
	 *  		</ul>
	 *  	<li>An instance of <code>PeripheralIcon</code> contained within the <code>peripheralIcons</code> <code>ArrayList</code>, but not within the <code>selectedIcons</code> <code>ArrayList</code>.</li>
	 *  		<ul>
	 *  			<li>Said instance is added to <code>selectedIcons</code>.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if (this.selectedIcons.contains(e.getSource()))
		{
			this.selectedIcons.remove((PeripheralIcon)e.getSource());
		}
		else if (this.peripheralIcons.contains(e.getSource()))
		{
			this.selectedIcons.add((PeripheralIcon)e.getSource());
		}
		super.refreshIconContainer(super.getIconPlace(), this.peripheralIcons, this.selectedIcons);
	}

	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mousePressed(MouseEvent e) 
	{

	}

	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseReleased(MouseEvent e) 
	{

	}

	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseEntered(MouseEvent e) 
	{

	}

	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseExited(MouseEvent e) 
	{

	}
	
	/** Override method - attempts to load the next six images from peripheral devices within <code>iconContainer</code>.
	 */
	protected void nextButtonClicked()
	{
		if (super.getIconPlace() + 6 < this.peripheralIcons.size())
		{
			super.refreshIconContainer(super.getIconPlace() + 6, this.peripheralIcons, this.selectedIcons);
		}
	}
	
	/** Override method - attempts to load the previous six images from peripheral devices within <code>iconContainer</code>.
	 */
	protected void prevButtonClicked()
	{
		if (super.getIconPlace() >= 6)
		{
			super.refreshIconContainer(super.getIconPlace() - 6, this.peripheralIcons, this.selectedIcons);
		}
	}
	
	/** Override method - attempts to copy selected images to the case and push <code>EditImgPanel</code> into view, displays an error message if this fails.
	 */
	protected void continueButtonClicked()
	{
		if (this.selectedIcons.size() > 0)
		{
			int selection = JOptionPane.showConfirmDialog(this, "Would you like to delete the original copies of all imported files from the camera?", "Delete Selected Files", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			if (selection == JOptionPane.YES_OPTION)
			{
				this.attemptCopying(true);
			}
			else if (selection == JOptionPane.NO_OPTION)
			{
				this.attemptCopying(false);
			}
			else if (selection == JOptionPane.CANCEL_OPTION)
			{
				return;
			}
		}
		else
		{
			int selection = JOptionPane.showConfirmDialog(this, "Are you sure you would like to continue without adding any new images to this case?", "Proceed to Next Step", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			if (selection == JOptionPane.YES_OPTION)
			{
				super.getManager().closeDialogue();
				super.getManager().getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				super.getManager().getMainWindow().pushPanel(new EditImgPanel(super.getManager(), super.getCaseNum()), "PEMS - Edit Photos");
			}
			else if (selection == JOptionPane.NO_OPTION)
			{
				return;
			}
			else if (selection == JOptionPane.CANCEL_OPTION)
			{
				return;
			}
		}
	}
	
	/** Attempts to copy the selected image files into the local file system, where they can be managed by PEMS.
	 * 
	 *  @param delete <code>boolean</code> value indicating whether or not the original copies of the selected files should be deleted as the program proceeds
	 */
	private void attemptCopying(boolean delete)
	{
		AddFileResult result = super.getManager().getStorageManager().addFiles(delete, super.getCaseNum(), this.selectedIcons);
		if (result == AddFileResult.ADD_FAILED)
		{
			JOptionPane.showMessageDialog(this, "Importation of the selected image files has unexpectedly failed!\nPlease disconnect and reconnect all cameras and devices, restart the program, and try again.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			super.getManager().closeDialogue();
			super.getManager().getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			super.getManager().getMainWindow().pushPanel(new EditImgPanel(super.getManager(), super.getCaseNum()), "PEMS - Edit Photos");
		}
	}
	
	/** Returns an <code>ArrayList</code> of <code>PeripheralIcon</code> objects representing all of the images found on connected peripheral storage devices.
	 * 
	 *  @return <code>ArrayList</code> of <code>PeripheralIcon</code> objects representing all of the images found on connected peripheral storage devices
	 */
	private ArrayList<PeripheralIcon> generateIcons()
	{
		ArrayList<PeripheralIcon> icons = new ArrayList<PeripheralIcon>();
		for (int i = 0; i < super.getManager().getPeripheralManager().getDevices().size(); i++)
		{
			for (int j = 0; j < super.getManager().getPeripheralManager().getDevices().get(i).getFiles().size(); j++)
			{
				try 
				{
					PeripheralIcon newIcon = new PeripheralIcon(super.getManager().getPeripheralManager().getDevices().get(i).getFiles().get(j), 140);
					newIcon.setAlignmentX(CENTER_ALIGNMENT);
					newIcon.addMouseListener(this);
					icons.add(newIcon);
				} 
				catch (InvalidFileException e) 
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return icons;
	}
	
}