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
			if (this.newNameField.getText().length() > 0)
			{
				this.currentScreen.renameImage(this.newNameField.getText());
				this.manager.closeRenameDialogue();
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
		this.newNameField = ComponentGenerator.generateTextField("Enter the new name for the image here...", this, CENTER_ALIGNMENT);
		this.renameButton = ComponentGenerator.generateButton("Rename", this, CENTER_ALIGNMENT);
		this.container.add(this.newNameField);
		this.container.add(Box.createVerticalStrut(40));
		this.container.add(this.renameButton);
	}
	
}