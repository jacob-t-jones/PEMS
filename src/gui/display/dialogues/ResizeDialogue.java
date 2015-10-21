// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ResizeDialogue.java

package gui.display.dialogues;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gui.*;
import gui.components.field.*;
import gui.display.*;
import gui.display.editimg.*;

/** Subclass of <code>JPanel</code> displayed when the resize image option is selected within <code>EditImgPanel</code>.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class ResizeDialogue extends JPanel implements ActionListener
{

	private FrameManager manager;
	private EditImgPanel currentPanel;
	private Box container;
	private Box widthContainer;
	private Box heightContainer;
	private JLabel widthLabel;
	private JLabel heightLabel;
	private JLabel widthPixelsLabel;
	private JLabel widthInchesLabel;
	private JLabel heightPixelsLabel;
	private JLabel heightInchesLabel;
	private PixelsField widthPixelsField;
	private PixelsField heightPixelsField;
	private InchesField widthInchesField;
	private InchesField heightInchesField;
	private JCheckBox aspectRatio;
	private JButton applyButton;
	private int originalWidth;
	private int originalHeight;

	/** Populates this dialogue with all of the necessary graphical components.
	 * 
	 *  @param manager the instance of <code>FrameManager</code> that initialized this dialogue
	 *  @param currentPanel the instance of <code>EditImgPanel</code> associated with this class
	 *  @param originalWidth the original width of the image being resized, in pixels
	 *  @param originalHeight the original height of the image being resized, in pixels
	 *  @throws NullPointerException if any parameters are null
	 *  @throws IllegalArgumentException if <code>originalWidth</code> or <code>originalHeight</code> are negative
	 */
	public ResizeDialogue(FrameManager manager, EditImgPanel currentPanel, int originalWidth, int originalHeight) 
	{
		if (manager == null || currentPanel == null)
		{
			throw new NullPointerException();
		}
		if (originalWidth < 0 || originalHeight < 0)
		{
			throw new IllegalArgumentException();
		}
		this.manager = manager;
		this.currentPanel = currentPanel;
		this.originalWidth = originalWidth;
		this.originalHeight = originalHeight;
		this.container = Box.createVerticalBox();
		this.widthContainer = Box.createHorizontalBox();
		this.heightContainer = Box.createHorizontalBox();
		this.populateWidthContainer();
		this.populateHeightContainer();
		this.populateContainer();
		this.add(this.container);
	}

	/** Mandatory method required in all classes that implement <code>ActionListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li><code>applyButton</code></li>
	 *  		<ul>
	 *  			<li>Calls the <code>resizeImg</code> method within the current instance of <code>EditImgPanel</code> using the width and height values from this dialogue.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.applyButton) 
		{
			this.currentPanel.resizeImg((int)this.widthPixelsField.getValue(), (int)this.heightPixelsField.getValue());
			this.manager.closeDialogue();
		}
	}
	
	/** Upon one of the four text fields losing focus, the other text fields are updated, either to maintain aspect ratio or to keep the pixel/inch values in accordance with each other.
	 * 
	 *  @param e the <code>FocusEvent</code> being processed
	 */
	public void updateFields(FocusEvent e) 
	{
		if (e.getSource() == this.widthPixelsField) 
		{
			this.widthInchesField.setValue(this.pixelsToInches(this.widthPixelsField.getVal()));
			if (this.aspectRatio.isSelected())
			{
				int newHeight = (this.originalHeight * this.widthPixelsField.getVal()) / this.originalWidth;
				this.heightPixelsField.setVal(newHeight);
				this.heightInchesField.setVal(this.pixelsToInches(this.heightPixelsField.getVal()));
			}
		} 
		else if (e.getSource() == this.heightPixelsField) 
		{
			this.heightInchesField.setValue(new Double(this.pixelsToInches((int)this.heightPixelsField.getValue())));
			if (this.aspectRatio.isSelected())
			{
				int newWidth = (this.originalWidth * (int)this.heightPixelsField.getValue()) / this.originalHeight;
				this.widthPixelsField.setValue(new Integer(newWidth));
				this.widthInchesField.setValue(new Double(this.pixelsToInches((int)this.widthPixelsField.getValue())));
			}
		}
		else if (e.getSource() == this.widthInchesField)
		{
			this.widthPixelsField.setValue(new Integer(this.inchesToPixels((double)this.widthInchesField.getValue())));
			if (this.aspectRatio.isSelected())
			{
				int newHeight = (this.originalHeight * (int)this.widthPixelsField.getValue()) / this.originalWidth;
				this.heightPixelsField.setValue(new Integer(newHeight));
				this.heightInchesField.setValue(new Double(this.pixelsToInches((int)this.heightPixelsField.getValue())));
			}
		}
		else if (e.getSource() == this.heightInchesField)
		{
			this.heightPixelsField.setValue(new Integer(this.inchesToPixels((double)this.heightInchesField.getValue())));
			if (this.aspectRatio.isSelected())
			{
				int newWidth = (this.originalWidth * (int)this.heightPixelsField.getValue()) / this.originalHeight;
				this.widthPixelsField.setValue(new Integer(newWidth));
				this.widthInchesField.setValue(new Double(this.pixelsToInches((int)this.widthPixelsField.getValue())));
			}
		}
	}

	/** Adds <code>widthLabel</code>, <code>widthPixelsField</code>, <code>widthPixelsLabel</code>, <code>widthInchesField</code>, and <code>widthInchesLabel</code> to <code>widthContainer</code>.
	 */
	private void populateWidthContainer() 
	{
		this.widthLabel = ComponentGenerator.generateLabel("Width:", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.widthPixelsLabel = ComponentGenerator.generateLabel("px", ComponentGenerator.MINI_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.widthInchesLabel = ComponentGenerator.generateLabel("in", ComponentGenerator.MINI_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.widthPixelsField = ComponentGenerator.generatePixelsField(this.originalWidth, this);
		this.widthInchesField = ComponentGenerator.generateInchesField(this.pixelsToInches(this.originalWidth), this);
		this.widthContainer.add(this.widthLabel);
		this.widthContainer.add(Box.createHorizontalStrut(20));
		this.widthContainer.add(this.widthPixelsField);
		this.widthContainer.add(Box.createHorizontalStrut(2));
		this.widthContainer.add(this.widthPixelsLabel);
		this.widthContainer.add(Box.createHorizontalStrut(20));
		this.widthContainer.add(this.widthInchesField);
		this.widthContainer.add(Box.createHorizontalStrut(2));
		this.widthContainer.add(this.widthInchesLabel);
	}

	/** Adds <code>heightLabel</code>, <code>heightPixelsField</code>, <code>heightPixelsLabel</code>, <code>heightInchesField</code>, and <code>heightInchesLabel</code> to <code>heightContainer</code>.
	 */
	private void populateHeightContainer() 
	{
		this.heightLabel = ComponentGenerator.generateLabel("Height:", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.heightPixelsLabel = ComponentGenerator.generateLabel("px", ComponentGenerator.MINI_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.heightInchesLabel = ComponentGenerator.generateLabel("in", ComponentGenerator.MINI_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.heightPixelsField = ComponentGenerator.generatePixelsField(this.originalHeight, this);
		this.heightInchesField = ComponentGenerator.generateInchesField(this.pixelsToInches(this.originalHeight), this);
		this.heightContainer.add(this.heightLabel);
		this.heightContainer.add(Box.createHorizontalStrut(20));
		this.heightContainer.add(this.heightPixelsField);
		this.heightContainer.add(Box.createHorizontalStrut(2));
		this.heightContainer.add(this.heightPixelsLabel);
		this.heightContainer.add(Box.createHorizontalStrut(20));
		this.heightContainer.add(this.heightInchesField);
		this.heightContainer.add(Box.createHorizontalStrut(2));
		this.heightContainer.add(this.heightInchesLabel);
	}

	/** Adds <code>widthContainer</code>, <code>heightContainer</code>, <code>aspectRatio</code>, and <code>applyButton</code> to <code>container</code>.
	 */
	private void populateContainer() 
	{
		this.aspectRatio = ComponentGenerator.generateCheckBox("Maintain Aspect Ratio", true);
		this.applyButton = ComponentGenerator.generateButton("Apply Changes", this);
		this.container.add(Box.createVerticalStrut(10));
		this.container.add(this.widthContainer);
		this.container.add(Box.createVerticalStrut(5));
		this.container.add(this.heightContainer);
		this.container.add(Box.createVerticalStrut(10));
		this.container.add(this.aspectRatio);
		this.container.add(Box.createVerticalStrut(10));
		this.container.add(this.applyButton);
	}
	
	/** Accepts a <code>double</code> inches value and outputs a corresponding pixel value in <code>int</code> format.
	 * 
	 *  @param inches the inches value in question
	 *  @return the corresponding pixel value
	 */
	private int inchesToPixels(double inches)
	{
		return (int)(inches * Toolkit.getDefaultToolkit().getScreenResolution());
	}

	/** Accepts an <code>int</code> pixel value and outputs a corresponding inches value in <code>double</code> format.
	 * 
	 *  @param pixels the pixel value in question
	 *  @return the corresponding inches value
	 */
	private double pixelsToInches(int pixels)
	{
		return (double)(pixels * (1.0 / Toolkit.getDefaultToolkit().getScreenResolution()));
	}

}