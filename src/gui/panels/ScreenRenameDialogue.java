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
	private Box container;
	private JTextField newNameField;
	private JButton renameButton;

	public ScreenRenameDialogue(FrameManager manager)
	{
		this.manager = manager;
		this.newNameField = ComponentGenerator.generateTextField("Enter the new name for the image here...", this, CENTER_ALIGNMENT);
		this.renameButton = ComponentGenerator.generateButton("Rename", this, CENTER_ALIGNMENT);
		this.container = Box.createVerticalBox();
		this.container.add(this.newNameField);
		this.container.add(Box.createVerticalStrut(40));
		this.container.add(this.renameButton);
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
	
}
