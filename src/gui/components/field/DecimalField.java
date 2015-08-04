// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// DecimalField.java

package gui.components.field;
import java.text.*;
import javax.swing.*;
import javax.swing.text.*;

public class DecimalField extends JFormattedTextField
{
	
	public DecimalField(double defaultValue)
	{
		super(new Double(defaultValue));
		super.setFormatterFactory(new DefaultFormatterFactory(this.generateFormatter()));
	}
	
	public double getVal()
	{
		return (double)super.getValue();
	}
	
	public void setVal(double newValue)
	{
		super.setValue(new Double(newValue));
	}
	
	/* generateFormatter - returns a NumberFormatter instance that only accepts positive double values and allows users to temporarily make invalid inputs
	 */
	private NumberFormatter generateFormatter()
	{
		NumberFormatter formatter = new NumberFormatter(this.generateFormat());
		formatter.setValueClass(Double.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Double.MAX_VALUE);
		formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);
	    return formatter;
	}
	
	/* generateFormat - returns an instance of NumberFormat that allows one decimal place and does not utilize grouping symbols (commas, periods, etc.)
	 */
	private DecimalFormat generateFormat()
	{
		DecimalFormat format = new DecimalFormat("#.0");
		format.setGroupingUsed(false);
		return format;
	}
	
}
