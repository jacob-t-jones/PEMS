// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// StringField.java

package gui.components;
import java.awt.event.*;
import javax.swing.*;

public class StringField extends JTextField implements FocusListener
{
	
	private String defaultText;
	
	public StringField(String text)
	{
		super(text);
		super.addFocusListener(this);
		this.defaultText = text;
	}

	/* focusGained - mandatory for any class implementing FocusListener, checks the source of the FocusEvent and executes the appropriate code 
	 *           e - the event in question
	 *			   1. clears the text field if it still contains "defaultText" at the time of focus being gained
	 */
	public void focusGained(FocusEvent e) 
	{
		if (super.getText().equals(defaultText))
		{
			super.setText("");
		}
	}

	/* focusLost - mandatory for any class implementing FocusListener, checks the source of the FocusEvent and executes the appropriate code 
	 *         e - the event in question
	 *			 1. returns "defaultText" to the text field if it is empty at the time of focus being lost
	 */
	public void focusLost(FocusEvent e)
	{
		if (super.getText().replaceAll("\\s", "").isEmpty())
		{
			super.setText(this.defaultText);
		}
	}

}