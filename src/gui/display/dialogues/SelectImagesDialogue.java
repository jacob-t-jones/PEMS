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

/** Subclass of <code>JPanel</code> allowing the user to select from a list of images.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class SelectImagesDialogue extends JPanel implements ActionListener
{
	
	private FrameManager manager;
	private Box mainContainer;
	private Box buttonContainer;
	private Box iconContainer;
	private JLabel instructionsLabel;
	private JButton nextButton;
	private JButton prevButton;
	private JButton continueButton;
	private String caseNum;
	private String instructions;
	private int iconPlace;
	
	/** Populates this dialogue with all of the necessary graphical components.
	 * 
	 *  @param manager the instance of <code>FrameManager</code> that initialized this dialogue
	 *  @param caseNum the number of the case that the images are being selected for or from
	 *  @param instructions <code>String</code> containing the instructions to display in <code>instructionsLabel</code>
	 *  @throws NullPointerException if any parameters are null
	 */
	public SelectImagesDialogue(FrameManager manager, String caseNum, String instructions)
	{
		if (manager == null || caseNum == null || instructions == null)
		{
			throw new NullPointerException();
		}
		this.manager = manager;
		this.caseNum = caseNum;
		this.iconPlace = 0;
		this.mainContainer = Box.createVerticalBox();
		this.buttonContainer = Box.createHorizontalBox();
		this.iconContainer = Box.createVerticalBox();
		this.populateButtonContainer();
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
	 *  			<li>Calls the <code>nextButtonClicked</code> method.</li>
	 *  		</ul>
	 *  	<li><code>prevButton</code></li>
	 *  		<ul>
	 *  			<li>Calls the <code>prevButtonClicked</code> method.</li>
	 *  		</ul>
	 *  	<li><code>continueButton</code></li>
	 *  		<ul>
	 *  			<li>Calls the <code>continueButtonClicked</code> method.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.nextButton)
		{
			this.nextButtonClicked();
		}
		else if (e.getSource() == this.prevButton)
		{
			this.prevButtonClicked();
		}
		if (e.getSource() == this.continueButton)
		{
			this.continueButtonClicked();
		}
	}
	
	/** Returns the instance of <code>FrameManager</code> associated with this dialogue.
	 * 
	 *  @return the instance of <code>FrameManager</code> associated with this dialogue
	 */
	protected FrameManager getManager()
	{
		return this.manager;
	}
	
	/** Returns a <code>String</code> containing the case number of the currently selected case.
	 * 
	 *  @return <code>String</code> containing the case number of the currently selected case
	 */
	protected String getCaseNum()
	{
		return this.caseNum;
	}
	
	/** Returns the 
	 * 
	 *  @return
	 */
	protected int getIconPlace()
	{
		return this.iconPlace;
	}
	
	/** Placeholder method - should be overriden in subclasses.
	 */
	protected void nextButtonClicked()
	{
		return;
	}
	
	/** Placeholder method - should be overriden in subclasses.
	 */
	protected void prevButtonClicked()
	{
		return;
	}
	
	/** Placeholder method - should be overriden in subclasses.
	 */
	protected void continueButtonClicked()
	{
		return;
	}

	/** Refreshes the contents of <code>iconContainer</code> in accordance with the passed in parameters.
	 * 
	 *  @param iconPlace the index within <code>displayedIcons</code> of the first image to be displayed
	 *  @param displayedIcons genericized <code>ArrayList</code> containing all of the icons currently being displayed
	 *  @param selectedIcons genericized <code>ArrayList</code> containing the icons that have been selected by the user
	 */
	protected <T> void refreshIconContainer(int iconPlace, ArrayList<T> displayedIcons, ArrayList<T> selectedIcons)
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
				if (this.iconPlace < displayedIcons.size())
				{
					col.add(Box.createHorizontalGlue());
					col.add(Box.createVerticalStrut(150));
					col.add((ImgIcon)displayedIcons.get(this.iconPlace));
					col.add(Box.createVerticalStrut(150));
					col.add(Box.createHorizontalGlue());
					if (selectedIcons.contains(displayedIcons.get(this.iconPlace)))
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
	
	/** Adds <code>prevButton</code> and <code>nextButton</code> to <code>buttonContainer</code>.
	 */
	private void populateButtonContainer()
	{
		this.nextButton = ComponentGenerator.generateButton("Next >", this, CENTER_ALIGNMENT);
		this.prevButton = ComponentGenerator.generateButton("< Prev", this, CENTER_ALIGNMENT);
		this.buttonContainer.add(this.prevButton);
		this.buttonContainer.add(Box.createHorizontalStrut(250));
		this.buttonContainer.add(this.nextButton);
	}
	
	/** Adds <code>instructionsLabel</code>, <code>buttonContainer</code>, <code>iconContainer</code>, and <code>continueButton</code> to <code>mainContainer</code>.
	 */
	private void populateMainContainer()
	{
		this.instructionsLabel = ComponentGenerator.generateLabel(this.instructions, ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.continueButton = ComponentGenerator.generateButton("Continue", this, CENTER_ALIGNMENT);
		this.mainContainer.add(Box.createVerticalStrut(5));
		this.mainContainer.add(this.instructionsLabel);
		this.mainContainer.add(Box.createVerticalStrut(15));
		this.mainContainer.add(this.buttonContainer);
		this.mainContainer.add(this.iconContainer);
		this.mainContainer.add(Box.createVerticalStrut(5));
		this.mainContainer.add(this.continueButton);
	}

}