// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ComponentGenerator.java

package gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.text.*;

public class ComponentGenerator 
{
	
	public static final Font TITLE_FONT = new Font("Courier New", Font.BOLD, 30);
	public static final Font SUBTITLE_FONT = new Font("Courier New", Font.BOLD, 22);
	public static final Font STANDARD_TEXT_FONT = new Font("Georgia", Font.PLAIN, 14);
	public static final Font STANDARD_TEXT_FONT_BOLD = new Font("Georgia", Font.BOLD, 14);
	public static final Font STANDARD_TEXT_FONT_ITALIC = new Font("Georgia", Font.ITALIC, 14);
	public static final Font ERROR_TEXT_FONT = new Font("Georgia", Font.ITALIC, 12);
	public static final Font SMALL_TEXT_FONT = new Font ("Georgia", Font.PLAIN, 10);
	public static final Color TITLE_COLOR = new Color(46, 46, 46);
	public static final Color SUBTITLE_COLOR = new Color(2, 2, 123);
	public static final Color STANDARD_TEXT_COLOR = new Color(44, 29, 29);
	public static final Color ERROR_TEXT_COLOR = new Color(202, 12, 12);
	public static final Color CROPBOX_COLOR = new Color(1, 1, 1);
	
	public ComponentGenerator()
	{
		
	}
	
	/* generateButton - creates and returns a JButton that complies with the parameters
	 *           text - the text displayed in the button
	 *         action - the ActionListener for the button
	 */
	public static JButton generateButton(String text, ActionListener action)
	{
		JButton newButton = new JButton(text);
		newButton.addActionListener(action);
		return newButton;
	}
	
	/* generateButton - creates and returns a JButton that complies with the parameters
	 *           text - the text displayed in the button
	 *         action - the ActionListener for the button
	 *     alignmentX - the horizontal alignment of the button
	 */
	public static JButton generateButton(String text, ActionListener action, float alignmentX)
	{
		JButton newButton = new JButton(text);
		newButton.addActionListener(action);
		newButton.setAlignmentX(alignmentX);
		return newButton;
	}
	
	/* generateButton - creates and returns a JButton that complies with the parameters
	 *           text - the text displayed in the button
	 *         action - the ActionListener for the button
	 *     alignmentX - the horizontal alignment of the button
	 *     alignmentY - the vertical alignment of the button
	 */
	public static JButton generateButton(String text, ActionListener action, float alignmentX, float alignmentY)
	{
		JButton newButton = new JButton(text);
		newButton.addActionListener(action);
		newButton.setAlignmentX(alignmentX);
		newButton.setAlignmentY(alignmentY);
		return newButton;
	}
	
	/* generateLabel - creates and returns a JLabel that complies with the parameters
	 *         image - the image displayed in the label
	 */
	public static JLabel generateLabel(BufferedImage image)
	{
		JLabel newLabel = new JLabel(new ImageIcon(image));
		return newLabel;
	}
	
	/* generateLabel - creates and returns a JLabel that complies with the parameters
	 *         image - the image displayed in the label
	 *    alignmentX - the horizontal alignment of the label
	 */
	public static JLabel generateLabel(BufferedImage image, float alignmentX)
	{
		JLabel newLabel = new JLabel(new ImageIcon(image));
		newLabel.setAlignmentX(alignmentX);
		return newLabel;
	}
	
	/* generateLabel - creates and returns a JLabel that complies with the parameters
	 *         image - the image displayed in the label
	 *    alignmentX - the horizontal alignment of the label
	 *    alignmentY - the vertical alignment of the label
	 */
	public static JLabel generateLabel(BufferedImage image, float alignmentX, float alignmentY)
	{
		JLabel newLabel = new JLabel(new ImageIcon(image));
		newLabel.setAlignmentX(alignmentX);
		newLabel.setAlignmentY(alignmentY);
		return newLabel;
	}
	
	/* generateLabel - creates and returns a JLabel that complies with the parameters
	 *          text - the text displayed in the label
	 *          font - the font type to use in the label
	 *         color - the font color to use in the label
	 */
	public static JLabel generateLabel(String text, Font font, Color color)
	{
		JLabel newLabel = new JLabel(text);
		newLabel.setFont(font);
		newLabel.setForeground(color);
		return newLabel;
	}
	
	/* generateLabel - creates and returns a JLabel that complies with the parameters
	 *          text - the text displayed in the label
	 *          font - the font type to use in the label
	 *         color - the font color to use in the label
	 *    alignmentX - the horizontal alignment of the label
	 */
	public static JLabel generateLabel(String text, Font font, Color color, float alignmentX)
	{
		JLabel newLabel = new JLabel(text);
		newLabel.setFont(font);
		newLabel.setForeground(color);
		newLabel.setAlignmentX(alignmentX);
		return newLabel;
	}
	
	/* generateLabel - creates and returns a JLabel that complies with the parameters
	 *          text - the text displayed in the label
	 *          font - the font type to use in the label
	 *         color - the font color to use in the label
	 *    alignmentX - the horizontal alignment of the label
	 *    alignmentY - the vertical alignment of the label
	 */
	public static JLabel generateLabel(String text, Font font, Color color, float alignmentX, float alignmentY)
	{
		JLabel newLabel = new JLabel(text);
		newLabel.setFont(font);
		newLabel.setForeground(color);
		newLabel.setAlignmentX(alignmentX);
		newLabel.setAlignmentY(alignmentY);
		return newLabel;
	}
	
	/* generateTextField - creates and returns a JTextField that complies with the parameters
	 *              text - the default text displayed in the field
	 *             focus - the FocusListener for the field
	 */
	public static JTextField generateTextField(String text, FocusListener focus)
	{
		JTextField newTextField = new JTextField(text);
		newTextField.addFocusListener(focus);
		return newTextField;
	}
	
	/* generateTextField - creates and returns a JTextField that complies with the parameters
	 *              text - the default text displayed in the field
	 *             focus - the FocusListener for the field
	 *        alignmentX - the horizontal alignment of the field
	 */
	public static JTextField generateTextField(String text, FocusListener focus, float alignmentX)
	{
		JTextField newTextField = new JTextField(text);
		newTextField.addFocusListener(focus);
		newTextField.setAlignmentX(alignmentX);
		return newTextField;
	}
	
	/* generateTextField - creates and returns a JTextField that complies with the parameters
	 *              text - the default text displayed in the field
	 *             focus - the FocusListener for the field
	 *        alignmentX - the horizontal alignment of the field
	 *        alignmentY - the vertical alignment of the field
	 */
	public static JTextField generateTextField(String text, FocusListener focus, float alignmentX, float alignmentY)
	{
		JTextField newTextField = new JTextField(text);
		newTextField.addFocusListener(focus);
		newTextField.setAlignmentX(alignmentX);
		newTextField.setAlignmentY(alignmentY);
		return newTextField;
	}
	
	/* generateThumbnail - creates and returns an instance of our custom Thumbnail object, which inherits from JLabel
	 *             image - the image represented by the Thumbnail
	 *          filePath - the full file system path name of the aforementioned image
	 *          fileName - the shorthand file name of the aforementioned image
	 *           fileExt - the file extension (".png", ".jpg", etc.) for the image
	 */
	public static Thumbnail generateThumbnail(BufferedImage image, String filePath, String fileName, String fileExt)
	{
		Thumbnail newThumb = new Thumbnail(image, filePath, fileName, fileExt);
		return newThumb;
	}
	
	/* generateMenuItem - creates and returns a JMenuItem that complies with the parameters
	 *             text - the text displayed on the menu item
	 *           action - the ActionListener for the menu item
	 */
	public static JMenuItem generateMenuItem(String text, ActionListener action)
	{
		JMenuItem newMenuItem = new JMenuItem(text);
		newMenuItem.addActionListener(action);
		return newMenuItem;
	}
	
	/* generateMenuItem - creates and returns a JMenuItem that complies with the parameters
	 *             text - the text displayed on the menu item
	 *           action - the ActionListener for the menu item
	 *      accelerator - the KeyStroke to set the accelerator to (see: normal keyboard shortcut)
	 */
	public static JMenuItem generateMenuItem(String text, ActionListener action, KeyStroke accelerator)
	{
		JMenuItem newMenuItem = new JMenuItem(text);
		newMenuItem.addActionListener(action);
		newMenuItem.setAccelerator(accelerator);
		return newMenuItem;
	}
	
	/* generateMenuItem - creates and returns a JMenuItem that complies with the parameters
	 *             text - the text displayed on the menu item
	 *           action - the ActionListener for the menu item
	 *      accelerator - the KeyStroke to set the menu item accelerator to (see: normal keyboard shortcut)
	 *         mnemonic - the integer representation of the menu item mnemonic (see: stupid weird keyboard shortcut)
	 */
	public static JMenuItem generateMenuItem(String text, ActionListener action, KeyStroke accelerator, int mnemonic)
	{
		JMenuItem newMenuItem = new JMenuItem(text);
		newMenuItem.addActionListener(action);
		newMenuItem.setAccelerator(accelerator);
		newMenuItem.setMnemonic(mnemonic);
		return newMenuItem;
	}
	
	/*
	 * 
	 */
	public static void generateRectangle(Graphics g, Point first, Point second)
	{
		Rectangle r = new Rectangle(first.x, first.y, second.x, second.y);
		g.fillRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight()); 
		//return r;
	}
	/* generateCheckBox - creates and returns a JCheckBox that complies with the parameters
	 *             text - the text displayed alongside the check box
	 *         selected - boolean value indicating whether or not the check box should default to selected
	 */
	public static JCheckBox generateCheckBox(String text, boolean selected)
	{
		JCheckBox newCheckBox = new JCheckBox(text, selected);
		return newCheckBox;
	}
	
	/* generateCheckBox - creates and returns a JCheckBox that complies with the parameters
	 *             text - the text displayed alongside the check box
	 *         selected - boolean value indicating whether or not the check box should default to selected
	 *       alignmentX - the horizontal alignment of the check box
	 */
	public static JCheckBox generateCheckBox(String text, boolean selected, float alignmentX)
	{
		JCheckBox newCheckBox = new JCheckBox(text, selected);
		newCheckBox.setAlignmentX(alignmentX);
		return newCheckBox;
	}

	/* generateCheckBox - creates and returns a JCheckBox that complies with the parameters
	 *             text - the text displayed alongside the check box
	 *         selected - boolean value indicating whether or not the check box should default to selected
	 *       alignmentX - the horizontal alignment of the check box
	 *       alignmentY - the vertical alignment of the check box
	 */
	public static JCheckBox generateCheckBox(String text, boolean selected, float alignmentX, float alignmentY)
	{
		JCheckBox newCheckBox = new JCheckBox(text, selected);
		newCheckBox.setAlignmentX(alignmentX);
		newCheckBox.setAlignmentY(alignmentY);
		return newCheckBox;
	}
	
	/* generateIntegerOnlyTextField - creates and returns a JFormattedTextField that only accepts integer values
	 *                        value - the default value to place in the text field
	 */
	public static JFormattedTextField generateIntegerOnlyTextField(Object value) 
	{
		MaskFormatter formatter = null;
		try 
		{
			formatter = new MaskFormatter("######");
		} 
		catch (ParseException e) 
		{
			System.out.println("Error - Invalid text format");
			e.printStackTrace();
			return null;
		}
		JFormattedTextField newFormattedTextField = new JFormattedTextField(formatter);
		newFormattedTextField.setValue(value);
		return newFormattedTextField;
	}
	
	/* generateIntegerOnlyTextField - creates and returns a JFormattedTextField that only accepts integer values
	 *                        value - the default value to place in the text field
	 *                   alignmentX - the horizontal alignment of the text field
	 */
	public static JFormattedTextField generateIntegerOnlyTextField(Object value, float alignmentX)
	{
		MaskFormatter formatter = null;
		try 
		{
			formatter = new MaskFormatter("######");
		} 
		catch (ParseException e) 
		{
			System.out.println("Error - Invalid text format");
			e.printStackTrace();
			return null;
		}
		JFormattedTextField newFormattedTextField = new JFormattedTextField(formatter);
		newFormattedTextField.setValue(value);
		newFormattedTextField.setAlignmentX(alignmentX);
		return newFormattedTextField;
	}
	
	/* generateIntegerOnlyTextField - creates and returns a JFormattedTextField that only accepts integer values
	 *                        value - the default value to place in the text field
	 *                   alignmentX - the horizontal alignment of the text field
	 *                   alignmentY - the vertical alignment of the text field
	 */
	public static JFormattedTextField generateIntegerOnlyTextField(Object value, float alignmentX, float alignmentY)
	{
		MaskFormatter formatter = null;
		try 
		{
			formatter = new MaskFormatter("######");
		} 
		catch (ParseException e) 
		{
			System.out.println("Error - Invalid text format");
			e.printStackTrace();
			return null;
		}
		JFormattedTextField newFormattedTextField = new JFormattedTextField(formatter);
		newFormattedTextField.setValue(value);
		newFormattedTextField.setAlignmentX(alignmentX);
		newFormattedTextField.setAlignmentY(alignmentY);
		return newFormattedTextField;
	}
	
}