// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// SelectImagesDialogue.java

package gui.display.dialogues;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import gui.*;
import gui.components.icon.*;
import gui.display.*;

/** Subclass of <code>JPanel</code> displayed when the user is selecting images to print.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class SelectImagesDialogue extends JPanel implements ActionListener, MouseListener
{
	
	private FrameManager manager;
	private ArrayList<CaseIcon> caseIcons;
	private ArrayList<CaseIcon> selectedIcons;
	private Box mainContainer;
	private Box buttonContainer;
	private Box iconContainer;
	private JLabel titleLabel;
	private JButton nextButton;
	private JButton prevButton;
	private JButton continueButton;
	private String caseNum;
	private int iconPlace;
	
	/** Populates this dialogue with all of the necessary graphical components.
	 * 
	 *  @param manager the instance of <code>FrameManager</code> that initialized this dialogue
     *  @param caseNum the number of the case that the images are being selected from
     *  @throws NullPointerException if any parameters are null
	 */
	public SelectImagesDialogue(FrameManager manager, String caseNum)
	{
		if (manager == null || caseNum == null)
		{
			throw new NullPointerException();
		}
		this.manager = manager;
		this.caseNum = caseNum;
		this.iconPlace = 0;
		this.caseIcons = this.generateIcons();
		this.selectedIcons = new ArrayList<CaseIcon>();
		this.mainContainer = Box.createVerticalBox();
		this.buttonContainer = Box.createHorizontalBox();
		this.iconContainer = Box.createVerticalBox();
		this.populateButtonContainer();
		this.refreshIconContainer(0);
		this.populateMainContainer();
		this.add(this.mainContainer);
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.revalidate();
		this.repaint();
	}
	
	/** Mandatory method required in all classes that implement <code>ActionListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li><code>nextButton</code></li>
	 *  		<ul>
	 *  			<li>Attempts to load the next six images from the case within <code>iconContainer</code>.</li>
	 *  		</ul>
	 *  	<li><code>prevButton</code></li>
	 *  		<ul>
	 *  			<li>Attempts to load the previous six images from the case within <code>iconContainer</code>.</li>
	 *  		</ul>
	 *  	<li><code>continueButton</code></li>
	 *  		<ul>
	 *  			<li>Attempts to push <code>PrintSetUpDialogue</code> and displays an error message if this fails.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.nextButton)
		{
			if (this.iconPlace + 6 < this.caseIcons.size())
			{
				this.refreshIconContainer(this.iconPlace + 6);
			}
		}
		else if (e.getSource() == this.prevButton)
		{
			if (this.iconPlace >= 6)
			{
				this.refreshIconContainer(this.iconPlace - 6);
			}
		}
		else if (e.getSource() == this.continueButton)
		{
			if (this.selectedIcons.size() > 0)
			{
				this.manager.closeDialogue();
				this.manager.openDialogue("Print Setup", new PrintSetUpDialogue(this.manager, this.caseNum, this.selectedIcons), 60, 75);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "You have not selected any images to print!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
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
		this.refreshIconContainer(this.iconPlace);
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
	
	/** Refreshes the icons displayed on the screen.
	 * 
	 *  @param iconPlace the index within <code>caseIcons</code> at which we should begin displaying said icons
	 */
	private void refreshIconContainer(int iconPlace)
	{
		this.iconPlace = iconPlace;
		this.iconContainer.removeAll();
		this.iconContainer.add(Box.createVerticalStrut(5));;
		for (int i = 0; i < 2; i++)
		{
			Box row = Box.createHorizontalBox();
			for (int j = 0; j < 3; j++)
			{
				Box col = Box.createHorizontalBox();
				col.setMinimumSize(new Dimension(150, 150));
				col.setMaximumSize(new Dimension(150, 150));
				if (this.iconPlace < this.caseIcons.size())
				{
					col.add(Box.createHorizontalGlue());
					col.add(Box.createVerticalStrut(150));
					col.add(this.caseIcons.get(this.iconPlace));
					col.add(Box.createVerticalStrut(150));
					col.add(Box.createHorizontalGlue());
					if (this.selectedIcons.contains(this.caseIcons.get(this.iconPlace)))
					{
						col.setBackground(ComponentGenerator.SELECTED_THUMBNAIL_BG_COLOR);
						col.setOpaque(true);
					}
				}
				else
				{
					col.add(Box.createHorizontalGlue());
					col.add(Box.createVerticalStrut(150));
					col.add(Box.createHorizontalGlue());
				}
				row.add(col);
				this.iconPlace++;
			}
			this.iconContainer.add(row);
		}
		this.iconContainer.add(Box.createHorizontalStrut(450));
		this.iconPlace = iconPlace;
		this.revalidate();
		this.repaint();
	}
	
	/** Adds <code>nextButton</code> and <code>prevButton</code> to <code>buttonContainer</code>.
	 */
	private void populateButtonContainer()
	{
		this.nextButton = ComponentGenerator.generateButton("Next >", this, CENTER_ALIGNMENT);
		this.prevButton = ComponentGenerator.generateButton("< Prev", this, CENTER_ALIGNMENT);
		this.buttonContainer.add(this.prevButton);
		this.buttonContainer.add(Box.createHorizontalStrut(250));
		this.buttonContainer.add(this.nextButton);
	}
	
	/** Adds <code>titleLabel</code>, <code>buttonContainer</code>, <code>iconContainer</code>, and <code>continueButton</code> to <code>mainContainer</code>.
	 */
	private void populateMainContainer()
	{
		this.titleLabel = ComponentGenerator.generateLabel("Please select all of the images you would like to print:", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.continueButton = ComponentGenerator.generateButton("Continue", this, CENTER_ALIGNMENT);
		this.mainContainer.add(Box.createVerticalStrut(5));
		this.mainContainer.add(this.titleLabel);
		this.mainContainer.add(Box.createVerticalStrut(15));
		this.mainContainer.add(this.buttonContainer);
		this.mainContainer.add(this.iconContainer);
		this.mainContainer.add(Box.createVerticalStrut(5));
		this.mainContainer.add(this.continueButton);
	}
	
	/** Returns an <code>ArrayList</code> of <code>CaseIcon</code> objects representing all of the images contained within the currently selected case.
	 * 
	 *  @return <code>ArrayList</code> of <code>CaseIcon</code> objects representing all of the images contained within the currently selected case
	 */
	private ArrayList<CaseIcon> generateIcons()
	{
	    for (int i = 0; i < this.manager.getStorageManager().getCases().size(); i++)
	    {
	    	if (this.manager.getStorageManager().getCases().get(i).getCaseNum().equalsIgnoreCase(this.caseNum))
	    	{
	    		return this.manager.getStorageManager().getCases().get(i).getCaseIcons(140, this, CENTER_ALIGNMENT);
	    	}
	    }
		return new ArrayList<CaseIcon>();
	}

}