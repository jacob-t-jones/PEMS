// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// DialogueWindow.java

package gui.window;
import javax.swing.*;
import gui.display.*;

public class DialogueWindow extends Window
{
	
	public DialogueWindow(String title, FrameManager manager, JPanel panel, int width, int height)
	{
		super(title, manager, panel);
        super.setBounds(width, height);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        super.setResizable(false);
        super.setVisible(true);
	}
	
}
