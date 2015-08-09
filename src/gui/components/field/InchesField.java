// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// InchesField.java

package gui.components.field;
import java.awt.event.*;
import java.math.*;
import java.text.*;
import javax.swing.*;
import javax.swing.text.*;
import gui.display.dialogues.*;

public class InchesField extends JFormattedTextField
{
	
	private ResizeDialogue currentPanel;
	
	public InchesField(double defaultValue, ResizeDialogue currentPanel)
	{
		super(new Double(defaultValue));
		super.setFormatterFactory(new DefaultFormatterFactory(this.generateFormatter()));
		this.currentPanel = currentPanel;
	}
	
	/* getVal - returns the value from the text field in double format
	 */
	public double getVal()
	{
		return (double)super.getValue();
	}
	
	/* setVal - sets the value in the text field to be the double passed in as a parameter
	 * newVal - the new double value
	 */
	public void setVal(double newVal)
	{
		super.setValue(new Double(newVal));
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
	
	/* generateFormatter - returns a NumberFormatter instance that only accepts positive double values and allows users to temporarily make invalid inputs
	 */
	private NumberFormatter generateFormatter()
	{
		NumberFormatter formatter = new NumberFormatter(this.generateFormat());
		formatter.setValueClass(Double.class);
		formatter.setMinimum(0.0);
		formatter.setMaximum(Double.MAX_VALUE);
		formatter.setAllowsInvalid(true);
	    formatter.setCommitsOnValidEdit(true);
	    return formatter;
	}
	
	/* generateFormat - returns an instance of NumberFormat that allows one decimal place and does not utilize grouping symbols (commas, etc.)
	 */
	private NumberFormat generateFormat()
	{
		NumberFormat format = DecimalFormat.getInstance();
		format.setMinimumFractionDigits(0);
		format.setMaximumFractionDigits(1);
		format.setRoundingMode(RoundingMode.HALF_UP);
		format.setGroupingUsed(false);
		return format;
	}
	
}