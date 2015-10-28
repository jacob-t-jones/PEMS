// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// SelectImagesPrintDialogue.java

package gui.display.dialogues;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import gui.components.icon.*;
import gui.display.*;

/** Subclass of <code>SelectImagesDialogue</code> displayed when the user is selecting images to print.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class SelectImagesPrintDialogue extends SelectImagesDialogue implements MouseListener
{
	
	private ArrayList<CaseIcon> caseIcons;
	private ArrayList<CaseIcon> selectedIcons;
	
	/** Calls the parent constructor, populates <code>caseIcons</code>, initializes <code>selectedIcons</code>.
	 * 
	 *  @param manager the instance of <code>FrameManager</code> that initialized this dialogue
	 *  @param caseNum the number of the case that the images are being selected from
	 */
	public SelectImagesPrintDialogue(FrameManager manager, String caseNum)
	{
		super(manager, caseNum, "Please select all of the images you would like to print:");
		this.caseIcons = this.generateIcons();
		this.selectedIcons = new ArrayList<CaseIcon>();
		super.refreshIconContainer(0, this.caseIcons, this.selectedIcons);
		super.revalidate();
		super.repaint();
	}
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li>An instance of <code>CaseIcon</code> contained within both the <code>caseIcons</code> and <code>selectedIcons</code> <code>ArrayLists</code>.</li>
	 *  		<ul>
	 *  			<li>Said instance is removed from <code>selectedIcons</code>.</li>
	 *  		</ul>
	 *  	<li>An instance of <code>CaseIcon</code> contained within the <code>caseIcons</code> <code>ArrayList</code>, but not within the <code>selectedIcons</code> <code>ArrayList</code>.</li>
	 *  		<ul>
	 *  			<li>Said instance is added to <code>selectedIcons</code>.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if (this.selectedIcons.contains(e.getSource()))
		{
			this.selectedIcons.remove((CaseIcon)e.getSource());
		}
		else if (this.caseIcons.contains(e.getSource()))
		{
			this.selectedIcons.add((CaseIcon)e.getSource());
		}
		super.refreshIconContainer(super.getIconPlace(), this.caseIcons, this.selectedIcons);
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
	
	/** Override method - attempts to load the next six images from the case within <code>iconContainer</code>.
	 */
	protected void nextButtonClicked()
	{
		if (super.getIconPlace() + 6 < this.caseIcons.size())
		{
			super.refreshIconContainer(super.getIconPlace() + 6, this.caseIcons, this.selectedIcons);
		}
	}
	
	/** Override method - attempts to load the previous six images from the case within <code>iconContainer</code>.
	 */
	protected void prevButtonClicked()
	{
		if (super.getIconPlace() >= 6)
		{
			super.refreshIconContainer(super.getIconPlace() - 6, this.caseIcons, this.selectedIcons);
		}
	}
	
	/** Override method - attempts to push <code>PrintSetUpDialogue</code> and displays an error message if this fails.
	 */
	protected void continueButtonClicked()
	{
		if (this.selectedIcons.size() > 0)
		{
			super.getManager().closeDialogue();
			super.getManager().openDialogue("Print Setup", new PrintSetUpDialogue(super.getManager(), super.getCaseNum(), this.selectedIcons), 60, 75);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "You have not selected any images to print!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/** Returns an <code>ArrayList</code> of <code>CaseIcon</code> objects representing all of the images contained within the currently selected case.
	 * 
	 *  @return <code>ArrayList</code> of <code>CaseIcon</code> objects representing all of the images contained within the currently selected case
	 */
	private ArrayList<CaseIcon> generateIcons()
	{
	    for (int i = 0; i < super.getManager().getStorageManager().getCases().size(); i++)
	    {
	    	if (super.getManager().getStorageManager().getCases().get(i).getCaseNum().equalsIgnoreCase(super.getCaseNum()))
	    	{
	    		return super.getManager().getStorageManager().getCases().get(i).getCaseIcons(140, this, CENTER_ALIGNMENT);
	    	}
	    }
		return new ArrayList<CaseIcon>();
	}

}