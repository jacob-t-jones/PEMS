// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenRenameDialogue.java

package gui.panels;
import java.awt.event.*;
import javax.swing.*;
import gui.*;

public class ScreenRenameDialogue extends JPanel implements ActionListener, FocusListener
{
	
	private FrameManager manager;
	private ScreenEdit currentScreen;
	private Box container;
	private JLabel errorLabel;
	private JTextField newNameField;
	private JButton renameButton;

	public ScreenRenameDialogue(FrameManager manager, ScreenEdit currentScreen)
	{
		this.manager = manager;
		this.currentScreen = currentScreen;
		this.container = Box.createVerticalBox();
		this.populateContainer();
		this.add(this.container);
	}
	
	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *                 1. validates the file name entered by the user and, if valid, renames the file currently being edited
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.renameButton)
		{
			if (this.isValidName(this.newNameField.getText()))
			{
				this.currentScreen.renameImage(this.newNameField.getText());
				this.manager.closeRenameDialogue();
			}
			else
			{
				this.errorLabel.setText("Error - Only letters and numbers can be used in the file name!");
			}
		}
	}
	
	/* focusGained - mandatory for any class implementing FocusListener, checks the source of the FocusEvent and executes the appropriate code 
	 *           e - the event in question
	 *             1. clears the text within "newNameField" upon said component coming into focus
	 */
	public void focusGained(FocusEvent e) 
	{
		if (e.getSource() == this.newNameField)
		{
			this.newNameField.setText("");
		}
	}
	
	/* focusLost - mandatory for any class implementing FocusListener, checks the source of the FocusEvent and executes the appropriate code 
	 *         e - the event in question
	 */
	public void focusLost(FocusEvent e) 
	{
		return;
	}
	
	/* populateContainer - adds "errorLabel", "newNameField", and "renameButton" to "container"
	 */
	private void populateContainer()
	{
		this.errorLabel = ComponentGenerator.generateLabel("", ComponentGenerator.ERROR_TEXT_FONT, ComponentGenerator.ERROR_TEXT_COLOR, CENTER_ALIGNMENT);
		this.newNameField = ComponentGenerator.generateTextField("Enter the new name for the image here...", this, CENTER_ALIGNMENT);
		this.renameButton = ComponentGenerator.generateButton("Rename", this, CENTER_ALIGNMENT);
		this.container.add(this.errorLabel);
		this.container.add(Box.createVerticalStrut(20));
		this.container.add(this.newNameField);
		this.container.add(Box.createVerticalStrut(40));
		this.container.add(this.renameButton);
	}

	/* isValidName - returns a boolean value indicating whether or not the given file name is valid (only letters and numbers, at least one character)
	 *        name - the file name to check, input by the user
	 */
	private boolean isValidName(String name)
	{
		boolean valid = true;
		if (name.length() <= 0)
		{
			valid = false;
		}
		for (int i = 0; i < name.length(); i++)
		{
			int charVal = (int)name.charAt(i);
			if (charVal < 48 || charVal > 122 || (charVal > 57 && charVal < 65) || (charVal > 90 && charVal < 97))
			{
				valid = false;
			}
		}
		return valid;
	}
	
}