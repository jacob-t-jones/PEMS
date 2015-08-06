// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// NewCasePanel.java

package gui.display.newcase;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gui.*;
import gui.components.field.*;
import gui.display.*;
import gui.display.select.*;
import gui.display.start.StartPanel;
import tools.FileHandler.*;

public class NewCasePanel extends JPanel implements ActionListener
{
	
	private FrameManager manager;
	private Box container;
	private Box buttonsBox;
	private StringField caseNumField;
	private JLabel instructionsLabel;
	private JLabel errorLabel;
	private JButton backButton;
	private JButton continueButton;
	
	public NewCasePanel(FrameManager manager) 
	{
		this.manager = manager;
		this.container = Box.createVerticalBox();
		this.buttonsBox = Box.createHorizontalBox();
		this.populateContainer();
		this.add(this.container);
	}
	
	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *                 1. if case creation is successful, the wait cursor is displayed and SelectPanel is pushed to the JFrame
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.continueButton)
		{
			String caseNum = this.caseNumField.getText();
			if (this.attemptCaseCreation(caseNum))
			{
				this.manager.getMainWindow().setCursor((Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)));
				this.manager.getMainWindow().pushPanel(new SelectPanel(this.manager, caseNum), "PEMS - Import Images");
			}
		}
		else if (e.getSource() == this.backButton) {
				manager.getMainWindow().pushPanel(new StartPanel(manager), "PEMS (Police Evidence Management System) Version 0.1");
		}
	}
	
	/* populateContainer - adds "instructionsLabel", "errorLabel", "caseNumField", and "continueButton" to "container" 
	 */
	private void populateContainer()
	{
		this.backButton = ComponentGenerator.generateButton("Back", this, CENTER_ALIGNMENT);
		this.instructionsLabel = ComponentGenerator.generateLabel("Please enter the case number below:", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.errorLabel = ComponentGenerator.generateLabel("", ComponentGenerator.ERROR_TEXT_FONT, ComponentGenerator.ERROR_TEXT_COLOR, CENTER_ALIGNMENT);
		this.caseNumField = ComponentGenerator.generateStringField("Type here...", CENTER_ALIGNMENT);
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
		
		this.buttonsBox.add(backButton);
		this.buttonsBox.add(Box.createHorizontalStrut(80));
		this.buttonsBox.add(this.continueButton);
		this.container.add(buttonsBox);
	}
	
	/* attemptCaseCreation - attempts to create a new case with the given number, checking for edge cases in the process, and returns a boolean value indicating the success of said creation
	 *             caseNum - the case number for the newly created case
	 */
	private boolean attemptCaseCreation(String caseNum)
	{
		CaseCreationResult result = this.manager.getFileHandler().createCase(caseNum);
		if (result == CaseCreationResult.INVALID_CASE_NUMBER)
		{
			this.caseNumField.setText("");
			this.errorLabel.setText("Error - Invalid case number! Please do not exceed ten characters. Use only letters and numbers.");
			return false;
		}
		else if (result == CaseCreationResult.CASE_ALREADY_EXISTS)
		{
			this.caseNumField.setText("");
			this.errorLabel.setText("Error - A case with that number already exists!");
			return false;
		}
		else if (result == CaseCreationResult.DIRECTORY_CREATION_FAILED)
		{
			this.caseNumField.setText("");
			this.errorLabel.setText("Error - Case creation failed. Please try again!");
			return false;
		}
		return true;
	}
	
}