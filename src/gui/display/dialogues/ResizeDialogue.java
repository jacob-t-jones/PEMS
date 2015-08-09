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

	public ResizeDialogue(FrameManager manager, EditImgPanel currentPanel, int originalWidth, int originalHeight) 
	{
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

	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *                 1. calls the resize method within the current instance of EditImgPanel using the width and height values from the dialogue
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.applyButton) 
		{
			this.currentPanel.resizeImg((int)this.widthPixelsField.getValue(), (int)this.heightPixelsField.getValue());
			this.manager.closeDialogue();
		}
	}
	
	/* updateFields - upon one of the four text fields losing focus, the other text fields are updated, either to maintain aspect ratio or to keep the pixel/inch values in accordance with each other 
	 *            e - the FocusEvent being processed
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

	/* populateWidthContainer - places the necessary components within "widthContainer"
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

	/* populateHeightContainer - places the necessary components within "heightContainer"
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

	/* populateContainer - adds "widthContainer", "heightContainer", "aspectRatio", and "applyButton" to "container"
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
	
	/* inchesToPixels - accepts a double inches value and outputs a corresponding pixel value in integer format
	 */
	private int inchesToPixels(double inches)
	{
		return (int)(inches * Toolkit.getDefaultToolkit().getScreenResolution());
	}

	/* pixelsToInches - accepts an integer pixel value and outputs a corresponding inches value in double format
	 */
	private double pixelsToInches(int pixels)
	{
		return (double)(pixels * (1.0 / Toolkit.getDefaultToolkit().getScreenResolution()));
	}

}