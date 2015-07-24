// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenNewCase.java

package gui.panels;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;
import gui.*;

public class ScreenNewCase extends JPanel implements ActionListener, FocusListener
{
	
	private FrameManager manager;
	private Box container;
	private JLabel instructionsLabel;
	private JLabel errorLabel;
	private JTextField caseNumField;
	private JButton continueButton;
	
	public ScreenNewCase(FrameManager manager) 
	{
		this.manager = manager;
		this.container = Box.createVerticalBox();
		this.populateContainer();
		this.add(this.container);
	}
	
	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *                 1. attempts to create directories for the user specified case number, pushes ScreenImport if successful
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.continueButton)
		{
			if (this.createCase())
			{
				this.manager.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				this.manager.pushPanel(new ScreenImport(this.manager, this.caseNumField.getText()), "PEMS - Import Images");
			}
		}
	}
	
	/* focusGained - mandatory for any class implementing FocusListener, checks the source of the FocusEvent and executes the appropriate code 
	 *           e - the event in question
	 *             1. clears the text within "caseNumField" upon said component coming into focus
	 */
	public void focusGained(FocusEvent e) 
	{
		if (e.getSource() == this.caseNumField)
		{
			this.caseNumField.setText("");
		}
	}

	/* focusLost - mandatory for any class implementing FocusListener, checks the source of the FocusEvent and executes the appropriate code 
	 *         e - the event in question
	 */
	public void focusLost(FocusEvent e) 
	{
		return;
	}
	
	/* populateContainer - adds "instructionsLabel", "errorLabel", "caseNumField", and "continueButton" to "container" 
	 */
	private void populateContainer()
	{
		this.instructionsLabel = ComponentGenerator.generateLabel("Please enter the case number below:", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.errorLabel = ComponentGenerator.generateLabel("", ComponentGenerator.ERROR_TEXT_FONT, ComponentGenerator.ERROR_TEXT_COLOR, CENTER_ALIGNMENT);
		this.caseNumField = ComponentGenerator.generateTextField("Type here...", this, CENTER_ALIGNMENT);
		this.caseNumField.setMaximumSize(new Dimension(250, 30));
		this.caseNumField.setMinimumSize(new Dimension(250, 30));
		this.continueButton = ComponentGenerator.generateButton("Continue", this, CENTER_ALIGNMENT);
		this.container.add(Box.createVerticalStrut(20));
		this.container.add(this.instructionsLabel);
		this.container.add(Box.createVerticalStrut(10));
		this.container.add(this.errorLabel);
		this.container.add(Box.createVerticalStrut(50));
		this.container.add(this.caseNumField);
		this.container.add(Box.createVerticalStrut(80));
		this.container.add(this.continueButton);
	}
	
	/* createCase - attempts to create directories for the case in the "backups" and "cases" folders, and returns a boolean value indicating success
	 */
	private boolean createCase()
	{
		if (this.isValidCaseNum(this.caseNumField.getText()))
		{
			boolean isDirectory = Files.isDirectory(Paths.get("cases/" + this.caseNumField.getText() + "/")) && Files.isDirectory(Paths.get("backups/" + this.caseNumField.getText() + "/"));
			if (!isDirectory)
			{
				try
				{
					Files.createDirectory(Paths.get("cases/" + this.caseNumField.getText() + "/"));
					Files.createDirectory(Paths.get("backups/" + this.caseNumField.getText() + "/"));
				}
				catch (IOException e1)
				{
					System.out.println("Error - Unable to create case directory");
					e1.printStackTrace();
					return false;
				}
				return true;
			}
			else
			{
				this.caseNumField.setText("");
				this.errorLabel.setText("Error - A case with that number already exists!");
				return false;
			} 
		}
		else
		{
			this.caseNumField.setText("");
			this.errorLabel.setText("Error - Invalid case number. Please do not exceed ten characters. Use only letters and numbers.");
			return false;
		}
	}
	
	/* isValidCaseNum - returns a boolean value indicating whether or not the given case number is valid (only letters and numbers, at least one character, no more than ten characters)
	 *        caseNum - the number to check, input by the user
	 */
	private boolean isValidCaseNum(String caseNum)
	{
		boolean valid = true;
		if (caseNum.length() <= 0 || caseNum.length() > 10)
		{
			valid = false;
		}
		for (int i = 0; i < caseNum.length(); i++)
		{
			int charVal = (int)caseNum.charAt(i);
			if (charVal < 48 || charVal > 122 || (charVal > 57 && charVal < 65) || (charVal > 90 && charVal < 97))
			{
				valid = false;
			}
		}
		return valid;
	}
	
}