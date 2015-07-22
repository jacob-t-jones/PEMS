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
	private JLabel widthLabel;
	private JLabel heightLabel;
	private JTextField widthField;
	private JTextField heightField;
	private JButton applyButton;
	private int currentWidth;
	private int currentHeight;

	public ScreenResizeDialogue(FrameManager manager, ScreenEdit currentScreen, int currentWidth, int currentHeight)
	{
		this.manager = manager;
		this.currentScreen = currentScreen;
		this.currentWidth = currentWidth;
		this.currentHeight = currentHeight;
		this.container = Box.createVerticalBox();
		this.add(this.container);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.applyButton)
		{
			this.currentScreen.resizeImage(Integer.parseInt(this.widthField.getText()), Integer.parseInt(this.heightField.getText()));
		}
	}
	
	public void focusGained(FocusEvent e) 
	{
		
	}

	public void focusLost(FocusEvent e) 
	{

	}
	
	private void populateContainer()
	{
		this.widthLabel = ComponentGenerator.generateLabel("Width:", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.heightLabel = ComponentGenerator.generateLabel("Height:", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR);
		this.widthField = ComponentGenerator.generateTextField(String.valueOf(this.currentWidth), this);
		this.heightField = ComponentGenerator.generateTextField(String.valueOf(this.currentHeight), this);
		this.applyButton = ComponentGenerator.generateButton("Apply Changes", this);
	}
	
}