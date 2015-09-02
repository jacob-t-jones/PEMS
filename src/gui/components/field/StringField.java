// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// StringField.java

package gui.components.field;
import java.awt.event.*;
import javax.swing.*;

/** Subclass of <code>JTextField</code> used as a standard <code>String</code> input field.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class StringField extends JTextField implements FocusListener
{
	
	private String defaultText;
	
	/** Calls the parent constructor for this field, and makes it its own <code>FocusListener</code>.
	 * 
	 *  @param text the default <code>String</code> to display in this field
	 */
	public StringField(String text)
	{
		super(text);
		super.addFocusListener(this);
		this.defaultText = text;
	}

	/** Mandatory method required in all classes that implement <code>FocusListener</code>.
	 *  <p>
	 *  <b>When this field gains focus:</b>
	 *  <ul>
	 *  	<li>If this field still contains the default text, it is cleared.</li>
	 *  <ul>
	 */
	public void focusGained(FocusEvent e) 
	{
		if (super.getText().equals(defaultText))
		{
			super.setText("");
		}
	}

	/** Mandatory method required in all classes that implement <code>FocusListener</code>.
	 *  <p>
	 *  <b>When this field loses focus:</b>
	 *  <ul>
	 *  	<li>If this field is empty, the default text is shown once again.</li>
	 *  <ul>
	 */

	public void focusLost(FocusEvent e)
	{
		if (super.getText().replaceAll("\\s", "").isEmpty())
		{
			super.setText(this.defaultText);
		}
	}

}