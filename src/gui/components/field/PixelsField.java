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

public class PixelsField extends JFormattedTextField
{
	
	private ResizeDialogue currentPanel;
	
	public PixelsField(int defaultValue, ResizeDialogue currentPanel)
	{
		super(new Integer(defaultValue));
		super.setFormatterFactory(new DefaultFormatterFactory(this.generateFormatter()));
		this.currentPanel = currentPanel;
	}
	
	/* getVal - returns the value from the text field in int format
	 */
	public int getVal()
	{
		return (int)super.getValue();
	}
	
	/* setVal - sets the value in the text field to be the int passed in as a parameter
	 * newVal - the new int value
	 */
	public void setVal(int newValue)
	{
		super.setValue(new Integer(newValue));
	}
	
	/* processFocusEvent - override function used to update the text fields in ResizeDialogue upon the component gaining or losing focus
	 *                 e - the event in question
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
	
	/* generateFormatter - returns a NumberFormatter instance that only accepts positive integer values and allows users to temporarily make invalid inputs
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
	
	/* generateFormat - returns an instance of NumberFormat that does not allow decimals or utilize grouping symbols (commas, etc.)
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