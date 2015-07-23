// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenDeleteImportsDialogue.java

package gui.panels;
import java.awt.event.*;
import javax.swing.*;
import gui.*;

public class ScreenDeleteImportsDialogue extends JPanel implements ActionListener
{
	
	private FrameManager manager;
	private ScreenImport currentScreen;
	private Box mainContainer;
	private Box buttonsContainer;
	private JLabel questionLabel;
	private JButton yesButton;
	private JButton noButton;
	private JButton cancelButton;

	public ScreenDeleteImportsDialogue(FrameManager manager, ScreenImport currentScreen)
	{
		this.manager = manager;
		this.currentScreen = currentScreen;
		this.mainContainer = Box.createVerticalBox();
		this.buttonsContainer = Box.createHorizontalBox();
		this.populateButtonsContainer();
		this.populateMainContainer();
		this.add(this.mainContainer);
	}
	
	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *                 1. informs ScreenEdit that originals should be deleted and removes this dialogue from view
	 *                 2. informs ScreenEdit that originals should not be deleted and removes this dialogue from view
	 *                 3. removes this dialogue from view
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.yesButton)
		{
			this.currentScreen.deleteOptionSelected(true);
			this.manager.closeDeleteImportsDialogue();
		}
		else if (e.getSource() == this.noButton)
		{
			this.currentScreen.deleteOptionSelected(false);
			this.manager.closeDeleteImportsDialogue();
		}
		else if (e.getSource() == this.cancelButton)
		{
			this.manager.closeDeleteImportsDialogue();
		}
	}
	
	/* populateButtonsContainer - adds "yesButton", "noButton", and "cancelButton" to "buttonsContainer"
	 */
	private void populateButtonsContainer()
	{
		this.yesButton = ComponentGenerator.generateButton("Yes", this);
		this.noButton = ComponentGenerator.generateButton("No", this);
		this.cancelButton = ComponentGenerator.generateButton("Cancel", this);
		this.buttonsContainer.add(this.yesButton);
		this.buttonsContainer.add(Box.createHorizontalStrut(20));
		this.buttonsContainer.add(this.noButton);
		this.buttonsContainer.add(Box.createHorizontalStrut(20));
		this.buttonsContainer.add(this.cancelButton);
	}
	
	/* populateMainContainer - adds "questionLabel" and "buttonsContainer" to "mainContainer"
	 */
	private void populateMainContainer()
	{
		this.questionLabel = ComponentGenerator.generateLabel("Would you like to delete the original copies of all imported files from the camera?", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.mainContainer.add(this.questionLabel);
		this.mainContainer.add(Box.createVerticalStrut(25));
		this.mainContainer.add(this.buttonsContainer);
	}

}
