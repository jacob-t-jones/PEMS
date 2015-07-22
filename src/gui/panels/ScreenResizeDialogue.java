// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenResizeDialogue.java

package gui.panels;
import java.awt.event.*;
import javax.swing.*;
import gui.*;

public class ScreenResizeDialogue extends JPanel implements ActionListener
{
	
	private FrameManager manager;
	private ScreenEdit currentScreen;
	private Box container;
	private JLabel widthLabel;
	private JLabel heightLabel;
	private JTextField widthField;
	private JTextField heightField;
	private int currentWidth;
	private int currentHeight;

	public ScreenResizeDialogue(FrameManager manager, ScreenEdit currentScreen, int currentWidth, int currentHeight)
	{
		this.manager = manager;
		this.currentScreen = currentScreen;
		this.currentWidth = currentWidth;
		this.currentHeight = currentHeight;
		this.container = Box.createVerticalBox();
		this.add(this.container);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		
	}
	
}
