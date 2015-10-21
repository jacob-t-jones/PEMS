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

/** Subclass of <code>JFormattedTextField</code> used to display the inches value in <code>ResizeDialogue</code>.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class InchesField extends JFormattedTextField
{
	
	private ResizeDialogue currentPanel;
	
	/** Calls the parent constructor for this field, and sets its format.
	 * 
	 *  @param defaultValue the default <code>double</code> value to display in this field
	 *  @param currentPanel the instance of <code>ResizeDialogue</code> this field is a part of
	 *  @throws NullPointerException if any parameters are null
	 */
	public InchesField(double defaultValue, ResizeDialogue currentPanel)
	{
		super(new Double(defaultValue));
		if (currentPanel == null)
		{
			throw new NullPointerException();
		}
		super.setFormatterFactory(new DefaultFormatterFactory(this.generateFormatter()));
		this.currentPanel = currentPanel;
	}
	
	/** Returns the value from this field in <code>double</code> format.
	 * 
	 *  @return the value from this field in <code>double</code> format
	 */
	public double getVal()
	{
		return (double)super.getValue();
	}
	
	/** Sets the value in this field to be the <code>double</code> passed in as a parameter.
	 * 
	 *  @param newVal the value in question
	 */
	public void setVal(double newVal)
	{
		super.setValue(new Double(newVal));
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
	
	/** Returns a <code>NumberFormatter</code> instance that only accepts positive <code>double</code> values and allows users to temporarily make invalid inputs.
	 * 
	 *  @return <code>NumberFormatter</code> instance that only accepts positive <code>double</code> values and allows users to temporarily make invalid inputs
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
	
	/** Returns an instance of <code>NumberFormat</code> that allows one decimal place and does not utilize grouping symbols (commas, etc.).
	 * 
	 * @return instance of <code>NumberFormat</code> that allows one decimal place and does not utilize grouping symbols (commas, etc.)
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