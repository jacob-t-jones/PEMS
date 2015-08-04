// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// IntegerField.java

package gui.components.field;
import java.text.*;
import javax.swing.*;
import javax.swing.text.*;

public class IntegerField extends JFormattedTextField
{
	
	public IntegerField(int defaultValue)
	{
		super(new Integer(defaultValue));
		super.setFormatterFactory(new DefaultFormatterFactory(this.generateFormatter()));
	}
	
	public int getVal()
	{
		return (int)super.getValue();
	}
	
	public void setVal(int newValue)
	{
		super.setValue(new Integer(newValue));
	}
	
	/* generateFormatter - returns a NumberFormatter instance that only accepts positive integer values and allows users to temporarily make invalid inputs
	 */
	private NumberFormatter generateFormatter()
	{
		NumberFormatter formatter = new NumberFormatter(this.generateFormat());
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);
	    return formatter;
	}
	
	/* generateFormat - returns an instance of NumberFormat that does not utilize grouping symbols (commas, periods, etc.)
	 */
	private NumberFormat generateFormat()
	{
		NumberFormat format = NumberFormat.getIntegerInstance();
		format.setGroupingUsed(false);
		return format;
	}
	
}