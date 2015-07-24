// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenResizeDialogue.java

package gui.panels;
import java.awt.event.*;
import javax.swing.*;
import gui.*;

public class ScreenResizeDialogue extends JPanel implements ActionListener, FocusListener
{
	
	private FrameManager manager;
	private ScreenEdit currentScreen;
	private Box container;
	private Box widthContainer;
	private Box heightContainer;
	private JLabel widthLabel;
	private JLabel heightLabel;
	private JFormattedTextField widthField;
	private JFormattedTextField heightField;
	private JCheckBox aspectRatio;
	private JButton applyButton;
	private int originalWidth;
	private int originalHeight;

	public ScreenResizeDialogue(FrameManager manager, ScreenEdit currentScreen, int originalWidth, int originalHeight)
	{
		this.manager = manager;
		this.currentScreen = currentScreen;
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
	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.applyButton)
		{
			this.currentScreen.resizeImage(Integer.parseInt(this.widthField.getValue().toString()), Integer.parseInt(this.widthField.getValue().toString()));
			this.manager.closeResizeDialogue();
		}
	}
	
	public void focusGained(FocusEvent e) 
	{
		return;
	}

	public void focusLost(FocusEvent e) 
	{
		if (e.getSource() == this.widthField && this.aspectRatio.isSelected())
		{
			int newHeight = (this.originalHeight * Integer.parseInt(this.widthField.getValue().toString())) / this.originalWidth;
			this.heightField.setValue(new Integer(newHeight));
		}
		else if (e.getSource() == this.heightField && this.aspectRatio.isSelected())
		{
			int newWidth = (this.originalWidth * Integer.parseInt(this.heightField.getValue().toString())) / this.originalHeight;
			this.widthField.setValue(new Integer(newWidth));
		}
	}
	
	private void populateWidthContainer()
	{
		this.widthLabel = ComponentGenerator.generateLabel("Width:", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.widthField = ComponentGenerator.generateIntegerOnlyTextField(new Integer(this.originalWidth), this, this);
		this.widthContainer.add(this.widthLabel);
		this.widthContainer.add(Box.createHorizontalStrut(20));
		this.widthContainer.add(this.widthField);
	}
	
	private void populateHeightContainer()
	{
		this.heightLabel = ComponentGenerator.generateLabel("Height:", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.heightField = ComponentGenerator.generateIntegerOnlyTextField(new Integer(this.originalHeight), this, this);
		this.heightContainer.add(this.heightLabel);
		this.heightContainer.add(Box.createHorizontalStrut(20));
		this.heightContainer.add(this.heightField);
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
	
}