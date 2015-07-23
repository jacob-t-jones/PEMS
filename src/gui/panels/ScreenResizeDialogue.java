// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenResizeDialogue.java

package gui.panels;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.swing.text.*;
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

	}
	
	public void focusGained(FocusEvent e) 
	{
		
	}

	public void focusLost(FocusEvent e) 
	{

	}
	
	private NumberFormatter getIntegerOnlyFormatter()
	{
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		format.setMinimumIntegerDigits(0);
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);
	    return formatter;
	}
	
	private void populateWidthContainer()
	{
		this.widthLabel = ComponentGenerator.generateLabel("Width:", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.widthField = ComponentGenerator.generateFormattedTextField(this.getIntegerOnlyFormatter(), new Integer(this.originalWidth));
		this.widthContainer.add(this.widthLabel);
		this.widthContainer.add(Box.createHorizontalStrut(20));
		this.widthContainer.add(this.widthField);
	}
	
	private void populateHeightContainer()
	{
		this.heightLabel = ComponentGenerator.generateLabel("Height:", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.heightField = ComponentGenerator.generateFormattedTextField(this.getIntegerOnlyFormatter(), new Integer(this.originalHeight));
		this.heightContainer.add(this.heightLabel);
		this.heightContainer.add(Box.createHorizontalStrut(20));
		this.heightContainer.add(this.heightField);
	}
	
	private void populateContainer()
	{
		this.aspectRatio = ComponentGenerator.generateCheckBox("Maintain Aspect Ratio", true);
		this.applyButton = ComponentGenerator.generateButton("Apply Changes", this);
		this.container.add(this.widthContainer);
		this.container.add(Box.createVerticalStrut(5));
		this.container.add(this.heightContainer);
		this.container.add(Box.createVerticalStrut(5));
		this.container.add(this.aspectRatio);
		this.container.add(Box.createVerticalStrut(5));
		this.container.add(this.applyButton);
	}
	
}