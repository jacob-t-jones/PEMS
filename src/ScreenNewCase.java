// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenNewCase.java

import java.awt.*;

import javax.swing.*;

public class ScreenNewCase extends JPanel
{
	
	private FrameManager manager;
	private Box inputBox;
	private JLabel instructionsLabel;
	private JTextField caseNumField;
	private JButton continueButton;
	
	public ScreenNewCase(FrameManager manager) 
	{
		this.manager = manager;
	}
	
	private void populateInputBox()
	{
		this.inputBox = Box.createVerticalBox();
		this.constructInstructionsLabel();
		this.inputBox.add(Box.createVerticalStrut(20));
		this.constructCaseNumField();
		this.inputBox.add(Box.createVerticalStrut(20));
		this.constructContinueButton();
		this.add(this.inputBox);
	}
	
	private void constructInstructionsLabel()
	{
		this.instructionsLabel = new JLabel("Please enter the case number below:");
		this.instructionsLabel.setFont(this.manager.STANDARD_TEXT_FONT);
		this.instructionsLabel.setForeground(this.manager.STANDARD_TEXT_COLOR);
		this.inputBox.add(this.instructionsLabel);
	}
	
	private void constructCaseNumField()
	{
		
	}
	
	private void constructContinueButton()
	{
		
	}
	
}
