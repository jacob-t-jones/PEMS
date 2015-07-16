// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenNewCase.java

package gui.panels;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;
import gui.*;

public class ScreenNewCase extends JPanel
{
	
	private FrameManager manager;
	private ActionListener continueAction;
	private FocusListener caseNumFocus;
	private Box container;
	private JLabel instructionsLabel;
	private JLabel errorLabel;
	private JTextField caseNumField;
	private JButton continueButton;
	
	public ScreenNewCase(FrameManager manager) 
	{
		this.manager = manager;
		this.generateListeners();
		this.populateContainer();
	}
	
<<<<<<< HEAD
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
		this.container.add(Box.createVerticalStrut(80));
		this.constructContinueButton();
		this.add(this.container);
	}
	
	/* constructInstructionsLabel - creates "instructionsLabel", sets its font and alignment, and adds it to "container"
	 */
	private void constructInstructionsLabel()
	{
		this.instructionsLabel = new JLabel("Please enter the case number below:");
		this.instructionsLabel.setFont(ComponentGenerator.STANDARD_TEXT_FONT);
		this.instructionsLabel.setForeground(ComponentGenerator.STANDARD_TEXT_COLOR);
		this.instructionsLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.container.add(this.instructionsLabel);
	}
	
	/* constructErrorLabel - creates "errorLabel", sets its font and alignment, and adds it to "container"
	 */
	private void constructErrorLabel()
	{
		this.errorLabel = new JLabel("");
		this.errorLabel.setFont(ComponentGenerator.ERROR_TEXT_FONT);
		this.errorLabel.setForeground(ComponentGenerator.ERROR_TEXT_COLOR);
		this.errorLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.container.add(errorLabel);
	}
	
	/* constructCaseNumField - creates "caseNumField", makes a FocusListener for it, and adds it to "container"
	 *           focusGained - clears the default text in "caseNumField"
	 *             focusLost - does absolutely nothing
=======
	/* generateListeners - initializes listeners for all of the components within the JPanel
	 *    continueAction - attempts to create a directory for the user specified case number
	 *      caseNumFocus - clears the text within "caseNumField" upon said component coming into focus
>>>>>>> origin/master
	 */
	private void generateListeners()
	{
		this.continueAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
	    		if (isValidCaseNum(caseNumField.getText()))
	    		{
	    			boolean isDirectory = Files.isDirectory(Paths.get("cases/" + caseNumField.getText() + "/"));
	    			if (!isDirectory)
	    			{
	    				try
	    				{
	    					Files.createDirectory(Paths.get("cases/" + caseNumField.getText() + "/"));
	    				}
	    				catch (IOException e1)
	    				{
	    					System.out.println("Error - Unable to create case directory");
	    					e1.printStackTrace();
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
		};
		this.caseNumFocus = new FocusListener()
		{
			public void focusGained(FocusEvent e)
			{
				caseNumField.setText("");
			}
			public void focusLost(FocusEvent e)
			{
				return;
			}
		};
	}
	
	/* populateContainer - adds "instructionsLabel", "errorLabel", "caseNumField", and "continueButton" to "container" before displaying it
	 */
	private void populateContainer()
	{
		this.instructionsLabel = ComponentGenerator.generateLabel("Please enter the case number below:", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.errorLabel = ComponentGenerator.generateLabel("", ComponentGenerator.ERROR_TEXT_FONT, ComponentGenerator.ERROR_TEXT_COLOR, CENTER_ALIGNMENT);
		this.caseNumField = ComponentGenerator.generateTextField("Type here...", this.caseNumFocus, CENTER_ALIGNMENT);
		this.continueButton = ComponentGenerator.generateButton("Continue", this.continueAction, CENTER_ALIGNMENT);
		this.container = Box.createVerticalBox();
		this.container.add(Box.createVerticalStrut(20));
		this.container.add(this.instructionsLabel);
		this.container.add(Box.createVerticalStrut(10));
		this.container.add(this.errorLabel);
		this.container.add(Box.createVerticalStrut(50));
		this.container.add(this.caseNumField);
		this.container.add(Box.createVerticalStrut(80));
		this.container.add(this.continueButton);
		this.add(this.container);
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
