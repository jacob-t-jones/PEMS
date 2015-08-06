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

public class ResizeDialogue extends JPanel implements ActionListener, FocusListener 
{

	private FrameManager manager;
	private EditImgPanel currentPanel;
	private Box container;
	private Box widthContainer;
	private Box heightContainer;
	private JLabel widthLabel;
	private JLabel heightLabel;
	private JLabel pixelsLabel;
	private JLabel inchesLabel;
	private IntegerField widthPixelsField;
	private IntegerField heightPixelsField;
	private DecimalField widthInchesField;
	private DecimalField heightInchesField;
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
		this.pixelsLabel = ComponentGenerator.generateLabel("px", ComponentGenerator.MINI_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.inchesLabel = ComponentGenerator.generateLabel("in", ComponentGenerator.MINI_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.populateWidthContainer();
		this.populateHeightContainer();
		this.populateContainer();
		this.add(this.container);
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.applyButton) 
		{
			this.currentPanel.resizeImg((int)this.widthPixelsField.getValue(), (int)this.heightPixelsField.getValue());
			this.manager.closeDialogue();
		}
	}

	public void focusGained(FocusEvent e) 
	{
		return;
	}

	public void focusLost(FocusEvent e) 
	{
		/*if (e.getSource() == this.widthPixelsField) 
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
		}*/
	}

	private void populateWidthContainer() 
	{
		this.widthLabel = ComponentGenerator.generateLabel("Width:", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.widthPixelsField = ComponentGenerator.generateIntegerField(this.originalWidth, this);
		this.widthInchesField = ComponentGenerator.generateDecimalField(this.pixelsToInches(this.originalWidth), this);
		this.widthContainer.add(this.widthLabel);
		this.widthContainer.add(Box.createHorizontalStrut(20));
		this.widthContainer.add(this.widthPixelsField);
		this.widthContainer.add(Box.createHorizontalStrut(2));
		this.widthContainer.add(this.pixelsLabel);
		this.widthContainer.add(Box.createHorizontalStrut(20));
		this.widthContainer.add(this.widthInchesField);
		this.widthContainer.add(Box.createHorizontalStrut(2));
		this.widthContainer.add(this.inchesLabel);
	}

	private void populateHeightContainer() 
	{
		this.heightLabel = ComponentGenerator.generateLabel("Height:", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.heightPixelsField = ComponentGenerator.generateIntegerField(this.originalHeight, this);
		this.heightInchesField = ComponentGenerator.generateDecimalField(this.pixelsToInches(this.originalHeight), this);
		this.heightContainer.add(this.heightLabel);
		this.heightContainer.add(Box.createHorizontalStrut(20));
		this.heightContainer.add(this.heightPixelsField);
		this.heightContainer.add(Box.createHorizontalStrut(2));
		this.heightContainer.add(this.pixelsLabel);
		this.heightContainer.add(Box.createHorizontalStrut(20));
		this.heightContainer.add(this.heightInchesField);
		this.heightContainer.add(Box.createHorizontalStrut(2));
		this.heightContainer.add(this.inchesLabel);
	}

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
	
	private int inchesToPixels(double inches)
	{
		return (int)(inches * Toolkit.getDefaultToolkit().getScreenResolution());
	}

	private double pixelsToInches(int pixels)
	{
		return (double)(pixels * (1.0 / Toolkit.getDefaultToolkit().getScreenResolution()));
	}

}