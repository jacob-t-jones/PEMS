// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenNewCase.java

package gui.panels;
import java.awt.event.*;
import java.nio.file.*;

import javax.swing.*;

import gui.*;

public class ScreenNewCase extends JPanel
{
	
	private FrameManager manager;
	private Box container;
	private JLabel instructionsLabel;
	private JLabel errorLabel;
	private JTextField caseNumField;
	private JButton continueButton;
	private JButton addToExisting;
	
	
	public ScreenNewCase(FrameManager manager) 
	{
		this.manager = manager;
		this.populateContainer();
	}
	
	/* populateContainer - adds "instructionsLabel", "errorLabel", "caseNumField", and "continueButton" to "container" before displaying it
	 */
	private void populateContainer()
	{
		this.container = Box.createVerticalBox();
		this.container.add(Box.createVerticalStrut(20));
		this.constructInstructionsLabel();
		this.container.add(Box.createVerticalStrut(10));
		this.constructErrorLabel();
		this.container.add(Box.createVerticalStrut(50));
		this.constructCaseNumField();
		//this.container.add(Box.createVerticalStrut(40));
		this.createAddToExistingButton();
		this.container.add(Box.createVerticalStrut(80));
		this.constructContinueButton();
		this.add(this.container);
	}
	
	/* constructInstructionsLabel - creates "instructionsLabel", sets its font and alignment, and adds it to "container"
	 */
	private void constructInstructionsLabel()
	{
		this.instructionsLabel = new JLabel("Please enter the case number below:");
		this.instructionsLabel.setFont(this.manager.STANDARD_TEXT_FONT);
		this.instructionsLabel.setForeground(this.manager.STANDARD_TEXT_COLOR);
		this.instructionsLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.container.add(this.instructionsLabel);
	}
	
	/* constructErrorLabel - creates "errorLabel", sets its font and alignment, and adds it to "container"
	 */
	private void constructErrorLabel()
	{
		this.errorLabel = new JLabel("");
		this.errorLabel.setFont(this.manager.ERROR_TEXT_FONT);
		this.errorLabel.setForeground(this.manager.ERROR_TEXT_COLOR);
		this.errorLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.container.add(errorLabel);
	}
	
	/* constructCaseNumField - creates "caseNumField", makes a FocusListener for it, and adds it to "container"
	 *           focusGained - clears the default text in "caseNumField"
	 *             focusLost - does absolutely nothing
	 */
	private void constructCaseNumField()
	{
		this.caseNumField = new JTextField("Type here...");
		this.caseNumField.setAlignmentX(CENTER_ALIGNMENT);
		this.caseNumField.addFocusListener(new FocusListener()
		{
			public void focusGained(FocusEvent e)
			{
				caseNumField.setText("");
			}
			public void focusLost(FocusEvent e)
			{
				return;
			}
		});
		this.container.add(this.caseNumField);
	}
	
	/* constructContinueButton - creates "continueButton", makes an ActionListener for it, and adds it to "container"
	 *         actionPerformed - checks the input case number for validity, and pushes ScreenImport if it is valid
	 */
	private void constructContinueButton()
	{
		this.continueButton = new JButton("Continue");
	    this.continueButton.setAlignmentX(CENTER_ALIGNMENT);
	    this.continueButton.addActionListener(new ActionListener()
	    {
	    	public void actionPerformed(ActionEvent e)
	    	{
	    		if (isValidCaseNum(caseNumField.getText()))
	    		{
	    			boolean isDirectory = false;
	    			try
	    			{
	    				isDirectory = Files.isDirectory(Paths.get("cases/" + caseNumField.getText() + "/"));
	    			}
	    			catch (Exception e1)
	    			{
	    				return;
	    			}
	    			if (!isDirectory)
	    			{
	    				try
	    				{
	    					Files.createDirectory(Paths.get("cases/" + caseNumField.getText() + "/"));
	    				}
	    				catch (Exception e2)
	    				{
	    					return;
	    				}
	    	    		manager.pushPanel(new ScreenImport(manager), "PEMS - Import Images");
	    			}
	    			else
	    			{
	    				caseNumField.setText("");
	    				errorLabel.setText("Error - A case with that number already exists!");
	    			} 
	    		}
	    		else
	    		{
	    			caseNumField.setText("");
	    			errorLabel.setText("Error - Invalid case number. Please use only letters and numbers!");
	    		}
	    	}
	    });
		this.container.add(this.continueButton);
	}
	
	/* createAddToExistingButton - navigate to the addToExisting screen
	 */
	private void createAddToExistingButton()
	{
		this.addToExisting = new JButton("Add to Existing File");
		this.addToExisting.setAlignmentX(CENTER_ALIGNMENT);
		this.addToExisting.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
            {
            	manager.pushPanel(new ScreenAddToExisting(manager), "PEMS - Add to Existing File");
            }
		});
		this.container.add(this.addToExisting);
	}
	
	/* isValidCaseNum - returns a boolean value indicating whether or not the given case number is valid (only letters and numbers, at least one character)
	 *        caseNum - the number to check, input by the user
	 */
	private boolean isValidCaseNum(String caseNum)
	{
		boolean valid = true;
		if (caseNum.length() <= 0)
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
