// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// NumericField.java

package gui.components.field;
import java.text.*;
import javax.swing.*;
import javax.swing.text.*;

public class NumericField extends JFormattedTextField
{
	
	public NumericField(int defaultValue)
	{
		super(new Integer(defaultValue));
		super.setFormatter(this.generateFormatter());
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
	
	/* generateFormat - returns an instance of NumberFormat that does not utilize grouping symbols (commas, periods, etc.)
	 */
	private NumberFormat generateFormat()
	{
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		return format;
	}
	
}
