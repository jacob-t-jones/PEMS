// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// PixelsField.java

package gui.components.field;
import java.awt.event.*;
import java.math.*;
import java.text.*;

import javax.swing.*;
import javax.swing.text.*;

import gui.display.dialogues.*;

/** Subclass of <code>JFormattedTextField</code> used to display the pixels value in <code>ResizeDialogue</code>.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class PixelsField extends JFormattedTextField
{
	
	private ResizeDialogue currentPanel;
	
	/** Calls the parent constructor for this field, and sets its format.
	 * 
	 *  @param defaultValue the default <code>int</code> value to display in this field
	 *  @param currentPanel the instance of <code>ResizeDialogue</code> this field is a part of
	 *  @throws NullPointerException if any parameters are null
	 */
	public PixelsField(int defaultValue, ResizeDialogue currentPanel)
	{
		super(new Integer(defaultValue));
		if (currentPanel == null)
		{
			throw new NullPointerException();
		}
		super.setFormatterFactory(new DefaultFormatterFactory(this.generateFormatter()));
		this.currentPanel = currentPanel;
	}
	
	/** Returns the value from this field in <code>int</code> format.
	 * 
	 *  @return the value from this field in <code>int</code> format
	 */
	public int getVal()
	{
		return (int)super.getValue();
	}
	
	/** Sets the value in this field to be the <code>int</code> passed in as a parameter.
	 * 
	 *  @param newVal the value in question
	 */
	public void setVal(int newVal)
	{
		super.setValue(new Integer(newVal));
	}
	
	/** Override method used to update the text fields in <code>ResizeDialogue</code> upon this component gaining or losing focus.
	 */
	protected void processFocusEvent(final FocusEvent e) 
    {  
		super.processFocusEvent(e);
		SwingUtilities.invokeLater(new Runnable() 
		{    
			public void run() 
			{  
				currentPanel.updateFields(e);
			}   
		});
	} 
	
	/** Returns a <code>NumberFormatter</code> instance that only accepts positive <code>int</code> values and allows users to temporarily make invalid inputs.
	 * 
	 *  @return <code>NumberFormatter</code> instance that only accepts positive <code>int</code> values and allows users to temporarily make invalid inputs
	 */
	private NumberFormatter generateFormatter()
	{
		NumberFormatter formatter = new NumberFormatter(this.generateFormat());
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(true);
	    formatter.setCommitsOnValidEdit(true);
	    return formatter;
	}
	
	/** Returns an instance of <code>NumberFormat</code> that does not allow decimals or utilize grouping symbols (commas, etc.).
	 * 
	 *  @return instance of <code>NumberFormat</code> that does not allow decimals or utilize grouping symbols (commas, etc.)
	 */
	private NumberFormat generateFormat()
	{
		NumberFormat format = NumberFormat.getIntegerInstance();
		format.setMaximumFractionDigits(0);
		format.setRoundingMode(RoundingMode.HALF_UP);
		format.setGroupingUsed(false);
		return format;
	}
	
}