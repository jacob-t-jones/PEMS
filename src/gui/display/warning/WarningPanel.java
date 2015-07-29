// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// WarningPanel.java

package gui.display.warning;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.ComponentGenerator;
import gui.display.FrameManager;
import gui.display.print.PrintPanel;

public class WarningPanel extends JPanel implements ActionListener, FocusListener {

	private FrameManager manager;
	private PrintPanel currentScreen;
	private Box container;
	private Box heightContainer;
	private JLabel message;
	private JButton continueButton;

	public WarningPanel(FrameManager manager, PrintPanel currentScreen) {
		this.manager = manager;
		this.currentScreen = currentScreen;
		this.container = Box.createVerticalBox();
		this.heightContainer = Box.createHorizontalBox();
		this.populateHeightContainer();
		this.populateContainer();
		this.add(this.container);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.continueButton) 
		{
			this.manager.closeDialogue();
		}
	}

	@Override
	public void focusGained(FocusEvent e) 
	{
		return;
	}


	private void populateHeightContainer() {
		this.message = ComponentGenerator.generateLabel("You have not selected any images!", ComponentGenerator.STANDARD_TEXT_FONT_BOLD,
				ComponentGenerator.STANDARD_TEXT_COLOR);
		this.heightContainer.add(this.message);
		this.heightContainer.add(Box.createHorizontalStrut(20));
	}

	private void populateContainer() {
		this.continueButton = ComponentGenerator.generateButton("Return", this);
		this.container.add(Box.createVerticalStrut(10));
		this.container.add(this.heightContainer);
		this.container.add(Box.createVerticalStrut(10));
		this.container.add(this.continueButton);
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

}


