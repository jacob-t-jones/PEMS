// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// NewCasePanel.java

package gui.display.newcase;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import backend.StorageManager.*;
import gui.*;
import gui.components.field.*;
import gui.display.*;
import gui.display.select.*;
import gui.display.start.*;

/** Subclass of <code>JPanel</code> displayed when the user wants to create a new case.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class NewCasePanel extends JPanel implements ActionListener
{
	
	private FrameManager manager;
	private Box mainContainer;
	private Box buttonContainer;
	private StringField caseNumField;
	private JLabel instructionsLabel;
	private JLabel errorLabel;
	private JButton backButton;
	private JButton continueButton;
	
	/** Populates this panel with all of the necessary graphical components.
	 * 
	 *  @param manager the instance of <code>FrameManager</code> that initialized this panel
	 */
	public NewCasePanel(FrameManager manager) 
	{
		this.manager = manager;
		this.mainContainer = Box.createVerticalBox();
		this.buttonContainer = Box.createHorizontalBox();
		this.populateMainContainer();
		this.add(this.mainContainer);
	}
	
	/** Mandatory method required in all classes that implement <code>ActionListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li><code>continueButton</code></li>
	 *  		<ul>
	 *  			<li>If the case creation procedure succeeds, <code>SelectPanel</code> is pushed into view.</li>
	 *  		</ul>
	 *  	<li><code>backButton</code></li>
	 *  		<ul>
	 *  			<li><code>StartPanel</code> is pushed into view.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.continueButton)
		{
			String caseNum = this.caseNumField.getText();
			if (this.attemptCaseCreation(caseNum))
			{
				this.manager.getMainWindow().setCursor((Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)));
				this.manager.getMainWindow().pushPanel(new SelectPanel(this.manager, caseNum), "PEMS - Import Images");
			}
		}
		else if (e.getSource() == this.backButton) 
		{
			this.manager.getMainWindow().pushPanel(new StartPanel(this.manager), "PEMS (Police Evidence Management System) Version 0.1");
		}
	}
	
	/** Adds <code>instructionsLabel</code>, <code>errorLabel</code>, <code>caseNumField</code>, <code>backButton</code>, and <code>continueButton</code> to <code>mainContainer</code>.
	 */
	private void populateMainContainer()
	{
		this.instructionsLabel = ComponentGenerator.generateLabel("Please enter the case number below:", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.errorLabel = ComponentGenerator.generateLabel("", ComponentGenerator.SMALL_TEXT_FONT_ITALIC, ComponentGenerator.ERROR_TEXT_COLOR, CENTER_ALIGNMENT);
		this.caseNumField = ComponentGenerator.generateStringField("Type here...", CENTER_ALIGNMENT);
		this.caseNumField.setMaximumSize(new Dimension(250, 30));
		this.caseNumField.setMinimumSize(new Dimension(250, 30));
		this.backButton = ComponentGenerator.generateButton("<      Back", this, CENTER_ALIGNMENT);
		this.continueButton = ComponentGenerator.generateButton("Continue  >", this, CENTER_ALIGNMENT);
		this.mainContainer.add(Box.createVerticalStrut(20));
		this.mainContainer.add(this.instructionsLabel);
		this.mainContainer.add(Box.createVerticalStrut(20));
		this.mainContainer.add(this.errorLabel);
		this.mainContainer.add(Box.createVerticalStrut(50));
		this.mainContainer.add(this.caseNumField);
		this.mainContainer.add(Box.createVerticalStrut(120));
		this.buttonContainer.add(this.backButton);
		this.buttonContainer.add(Box.createHorizontalStrut(250));
		this.buttonContainer.add(this.continueButton);
		this.mainContainer.add(this.buttonContainer);
	}
	
	/** Attempts to create a new case with the given number, checking for edge cases in the process. Returns a <code>boolean</code> value indicating the success of this creation.
	 * 
	 *  @param caseNum the case number in question
	 *  @return <code>true</code> if the case was successfully created; <code>false</code> if otherwise
	 */
	private boolean attemptCaseCreation(String caseNum)
	{
		CaseCreationResult result = this.manager.getStorageManager().createCase(caseNum);
		if (result == CaseCreationResult.INVALID_CASE_NUMBER)
		{
			this.caseNumField.setText("");
			this.errorLabel.setText("Error - Please do not exceed ten characters. Use only letters and numbers.");
			return false;
		}
		else if (result == CaseCreationResult.CASE_ALREADY_EXISTS)
		{
			this.caseNumField.setText("");
			this.errorLabel.setText("Error - A case with that number already exists!");
			return false;
		}
		else if (result == CaseCreationResult.DIRECTORY_CREATION_FAILED)
		{
			this.caseNumField.setText("");
			this.errorLabel.setText("Error - Case creation failed. Please try again!");
			return false;
		}
		return true;
	}
	
}