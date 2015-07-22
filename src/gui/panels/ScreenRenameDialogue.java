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
	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.renameButton)
		{
			if (isValidName(this.newNameField.getText()))
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
	
	public void focusGained(FocusEvent e) 
	{
		if (e.getSource() == this.newNameField)
		{
			this.newNameField.setText("");
		}
	}

	public void focusLost(FocusEvent e) 
	{
		return;
	}
	
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