// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// EditCasePanel.java

package gui.display.editcase;
import javax.swing.*;
import gui.display.*;

public class EditCasePanel extends JPanel 
{
	
	private FrameManager manager;
	private Box container;

	public EditCasePanel(FrameManager manager)
	{
		this.manager = manager;
		this.container = Box.createVerticalBox();
		this.add(this.container);
		this.revalidate();
		this.repaint();
	}
	
}