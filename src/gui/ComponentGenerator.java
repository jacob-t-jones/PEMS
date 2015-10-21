// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ComponentGenerator.java

package gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.util.*;
import javax.swing.*;
import gui.components.field.*;
import gui.display.dialogues.*;

/** Class containing <code>public</code> <code>static</code> methods used to generate various graphical components. Also defines <code>public</code> <code>static</code> <code>Font</code> and <code>Color</code> instance fields for use throughout the GUI.  
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class ComponentGenerator
{
	
	/** <code>Font</code> utilized in the title text on <code>StartPanel</code>. 
	 *  <p>
	 *  Specifications:
	 *  <ul>
	 *  	<li>Name: <i>Courier New</i>
	 *  	<li>Style: Bold
	 *  	<li>Size: 30
	 *  </ul>
	 */
	public static final Font TITLE_FONT = new Font("Courier New", Font.BOLD, 30);
	/** <code>Font</code> utilized in the subtitle text on <code>StartPanel</code>. 
	 *  <p>
	 *  Specifications:
	 *  <ul>
	 *  	<li>Name: <i>Courier New</i>
	 *  	<li>Style: Bold
	 *  	<li>Size: 22
	 *  </ul>
	 */
	public static final Font SUBTITLE_FONT = new Font("Courier New", Font.BOLD, 22);
	/** Standard <code>Font</code> utilized throughout the program. 
	 *  <p>
	 *  Specifications:
	 *  <ul>
	 *  	<li>Name: <i>Georgia</i>
	 *  	<li>Style: Plain
	 *  	<li>Size: 14
	 *  </ul>
	 */
	public static final Font STANDARD_TEXT_FONT = new Font("Georgia", Font.PLAIN, 14);
	/** Standard <code>Font</code> utilized throughout the program, in bold.
	 *  <p>
	 *  Specifications:
	 *  <ul>
	 *  	<li>Name: <i>Georgia</i>
	 *  	<li>Style: Bold
	 *  	<li>Size: 14
	 *  </ul>
	 */
	public static final Font STANDARD_TEXT_FONT_BOLD = new Font("Georgia", Font.BOLD, 14);
	/** Standard <code>Font</code> utilized throughout the program, in italics.
	 *  <p>
	 *  Specifications:
	 *  <ul>
	 *  	<li>Name: <i>Georgia</i>
	 *  	<li>Style: Italic
	 *  	<li>Size: 14
	 *  </ul>
	 */
	public static final Font STANDARD_TEXT_FONT_ITALIC = new Font("Georgia", Font.ITALIC, 14);
	/** Small <code>Font</code> utilized throughout the program. 
	 *  <p>
	 *  Specifications:
	 *  <ul>
	 *  	<li>Name: <i>Courier New</i>
	 *  	<li>Style: Plain
	 *  	<li>Size: 12
	 *  </ul>
	 */
	public static final Font SMALL_TEXT_FONT = new Font("Courier New", Font.PLAIN, 12);
	/** Small <code>Font</code> utilized throughout the program, in bold.
	 *  <p>
	 *  Specifications:
	 *  <ul>
	 *  	<li>Name: <i>Courier New</i>
	 *  	<li>Style: Bold
	 *  	<li>Size: 12
	 *  </ul>
	 */
	public static final Font SMALL_TEXT_FONT_BOLD = new Font("Courier New", Font.BOLD, 12);
	/** Small <code>Font</code> utilized throughout the program, in italics.
	 *  <p>
	 *  Specifications:
	 *  <ul>
	 *  	<li>Name: <i>Courier New</i>
	 *  	<li>Style: Italic
	 *  	<li>Size: 12
	 *  </ul>
	 */
	public static final Font SMALL_TEXT_FONT_ITALIC = new Font("Courier New", Font.ITALIC, 12);
	/** Miniature <code>Font</code> utilized throughout the program. 
	 *  <p>
	 *  Specifications:
	 *  <ul>
	 *  	<li>Name: <i>Georgia</i>
	 *  	<li>Style: Plain
	 *  	<li>Size: 10
	 *  </ul>
	 */
	public static final Font MINI_TEXT_FONT = new Font ("Georgia", Font.PLAIN, 10);
	/** Miniature <code>Font</code> utilized throughout the program, in bold.
	 *  <p>
	 *  Specifications:
	 *  <ul>
	 *  	<li>Name: <i>Georgia</i>
	 *  	<li>Style: Bold
	 *  	<li>Size: 10
	 *  </ul>
	 */
	public static final Font MINI_TEXT_FONT_BOLD = new Font ("Georgia", Font.BOLD, 10);
	/** Miniature <code>Font</code> utilized throughout the program, in italics.
	 *  <p>
	 *  Specifications:
	 *  <ul>
	 *  	<li>Name: <i>Georgia</i>
	 *  	<li>Style: Italic
	 *  	<li>Size: 10
	 *  </ul>
	 */
	public static final Font MINI_TEXT_FONT_ITALIC = new Font ("Georgia", Font.ITALIC, 10);
	/** <code>Color</code> applied to the title text on <code>StartPanel</code>. 
	 *  <p>
	 *  Description: Dark gray
	 */
	public static final Color TITLE_COLOR = new Color(46, 46, 46);
	/** <code>Color</code> applied to the subtitle text on <code>StartPanel</code>. 
	 *  <p>
	 *  Description: Dark blue
	 */
	public static final Color SUBTITLE_COLOR = new Color(2, 2, 123);
	/** Default <code>Color</code> applied to text within the GUI.
	 *  <p>
	 *  Description: Medium-dark gray
	 */
	public static final Color STANDARD_TEXT_COLOR = new Color(44, 29, 29);
	/** <code>Color</code> applied to text notifying the user of errors and exceptions.
	 *  <p>
	 *  Description: Medium-dark red
	 */
	public static final Color ERROR_TEXT_COLOR = new Color(202, 12, 12);
	/** <code>Color</code> applied to links.
	 *  <p>
	 *  Description: Medium blue
	 */
	public static final Color LINK_TEXT_COLOR = new Color(42, 68, 219);
	/** <code>Color</code> applied to the rectangle drawn on an image while it is being cropped.
	 *  <p>
	 *  Description: Light red
	 */
	public static final Color CROPBOX_COLOR = new Color(250, 40, 25);
	/** Background <code>Color</code> applied to selected images on <code>SelectImagesDialogue</code>
	 *  <p>
	 *  Description: Light gray
	 */
	public static final Color SELECTED_THUMBNAIL_BG_COLOR = new Color(219, 219, 219);
	
	/** Default constructor, does absolutely nothing.
	 */
	public ComponentGenerator()
	{
		
	}
	
	/** Creates and returns a <code>JButton</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display in the <code>JButton</code>
	 *  @param action the <code>ActionListener</code> for the <code>JButton</code>
	 *  @return <code>JButton</code> that complies with the passed in parameter values
	 */
	public static JButton generateButton(String text, ActionListener action)
	{
		JButton newButton = new JButton(text);
		newButton.addActionListener(action);
		return newButton;
	}
	
	/** Creates and returns a <code>JButton</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display in the <code>JButton</code>
	 *  @param action the <code>ActionListener</code> for the <code>JButton</code>
	 *  @param alignmentX the horizontal alignment of the <code>JButton</code>
	 *  @return <code>JButton</code> that complies with the passed in parameter values
	 */
	public static JButton generateButton(String text, ActionListener action, float alignmentX)
	{
		JButton newButton = new JButton(text);
		newButton.addActionListener(action);
		newButton.setAlignmentX(alignmentX);
		return newButton;
	}
	
	/** Creates and returns a <code>JButton</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display in the <code>JButton</code>
	 *  @param action the <code>ActionListener</code> for the <code>JButton</code>
	 *  @param alignmentX the horizontal alignment of the <code>JButton</code>
	 *  @param alignmentY the vertical alignment of the <code>JButton</code>
	 *  @return <code>JButton</code> that complies with the passed in parameter values
	 */
	public static JButton generateButton(String text, ActionListener action, float alignmentX, float alignmentY)
	{
		JButton newButton = new JButton(text);
		newButton.addActionListener(action);
		newButton.setAlignmentX(alignmentX);
		newButton.setAlignmentY(alignmentY);
		return newButton;
	}
	
	/** Creates and returns a <code>JLabel</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display in the <code>JLabel</code>
	 *  @param font the <code>Font</code> to utilize in the <code>JLabel</code>
	 *  @param color the <code>Color</code> to utilize in the <code>JLabel</code>
	 *  @return <code>JLabel</code> that complies with the passed in parameter values
	 */
	public static JLabel generateLabel(String text, Font font, Color color)
	{
		JLabel newLabel = new JLabel(text);
		newLabel.setFont(font);
		newLabel.setForeground(color);
		return newLabel;
	}	
	
	/** Creates and returns a <code>JLabel</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display in the <code>JLabel</code>
	 *  @param font the <code>Font</code> to utilize in the <code>JLabel</code>
	 *  @param color the <code>Color</code> to utilize in the <code>JLabel</code>
	 *  @param alignmentX the horizontal alignment of the <code>JLabel</code>
	 *  @return <code>JLabel</code> that complies with the passed in parameter values
	 */
	public static JLabel generateLabel(String text, Font font, Color color, float alignmentX)
	{
		JLabel newLabel = new JLabel(text);
		newLabel.setFont(font);
		newLabel.setForeground(color);
		newLabel.setAlignmentX(alignmentX);
		return newLabel;
	}
	
	/** Creates and returns a <code>JLabel</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display in the <code>JLabel</code>
	 *  @param font the <code>Font</code> to utilize in the <code>JLabel</code>
	 *  @param color the <code>Color</code> to utilize in the <code>JLabel</code>
	 *  @param alignmentX the horizontal alignment of the <code>JLabel</code>
	 *  @param alignmentY the vertical alignment of the <code>JLabel</code>
	 *  @return <code>JLabel</code> that complies with the passed in parameter values
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
	
	/** Creates and returns a <code>JCheckBox</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display with the <code>JCheckBox</code>
	 *  @param selected <code>boolean</code> value indicating whether or not the <code>JCheckBox</code> should default to selected
	 *  @return <code>JCheckBox</code> that complies with the passed in parameter values
	 */
	public static JCheckBox generateCheckBox(String text, boolean selected)
	{
		JCheckBox newCheckBox = new JCheckBox(text, selected);
		return newCheckBox;
	}

	/** Creates and returns a <code>JCheckBox</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display with the <code>JCheckBox</code>
	 *  @param selected <code>boolean</code> value indicating whether or not the <code>JCheckBox</code> should default to selected
	 *  @param alignmentX the horizontal alignment of the <code>JCheckBox</code>
	 *  @return <code>JCheckBox</code> that complies with the passed in parameter values
	 */
	public static JCheckBox generateCheckBox(String text, boolean selected, float alignmentX)
	{
		JCheckBox newCheckBox = new JCheckBox(text, selected);
		newCheckBox.setAlignmentX(alignmentX);
		return newCheckBox;
	}

	/** Creates and returns a <code>JCheckBox</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display with the <code>JCheckBox</code>
	 *  @param selected <code>boolean</code> value indicating whether or not the <code>JCheckBox</code> should default to selected
	 *  @param alignmentX the horizontal alignment of the <code>JCheckBox</code>
	 *  @param alignmentY the vertical alignment of the <code>JCheckBox</code>
	 *  @return <code>JCheckBox</code> that complies with the passed in parameter values
	 */
	public static JCheckBox generateCheckBox(String text, boolean selected, float alignmentX, float alignmentY)
	{
		JCheckBox newCheckBox = new JCheckBox(text, selected);
		newCheckBox.setAlignmentX(alignmentX);
		newCheckBox.setAlignmentY(alignmentY);
		return newCheckBox;
	}
	
	/** Creates and returns a <code>JMenuItem</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display in the <code>JMenuItem</code>
	 *  @param action the <code>ActionListener</code> for the <code>JMenuItem</code>
	 *  @return <code>JMenuItem</code> that complies with the passed in parameter values
	 */
	public static JMenuItem generateMenuItem(String text, ActionListener action)
	{
		JMenuItem newMenuItem = new JMenuItem(text);
		newMenuItem.addActionListener(action);
		return newMenuItem;
	}
	
	/** Creates and returns a <code>JMenuItem</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display in the <code>JMenuItem</code>
	 *  @param action the <code>ActionListener</code> for the <code>JMenuItem</code>
	 *  @param accelerator <code>KeyStroke</code> object representing the accelerator (key combination used to select menu) for the <code>JMenuItem</code>
	 *  @return <code>JMenuItem</code> that complies with the passed in parameter values
	 */
	public static JMenuItem generateMenuItem(String text, ActionListener action, KeyStroke accelerator)
	{
		JMenuItem newMenuItem = new JMenuItem(text);
		newMenuItem.addActionListener(action);
		newMenuItem.setAccelerator(accelerator);
		return newMenuItem;
	}
	
	/** Creates and returns a <code>JMenuItem</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display in the <code>JMenuItem</code>
	 *  @param action the <code>ActionListener</code> for the <code>JMenuItem</code>
	 *  @param accelerator <code>KeyStroke</code> object representing the accelerator (key combination used to select menu) for the <code>JMenuItem</code>
	 *  @param mnemonic <code>int</code> value repesenting the mnemonic (key combination used to open menu) for the <code>JMenuItem</code>
	 *  @return <code>JMenuItem</code> that complies with the passed in parameter values
	 */
	public static JMenuItem generateMenuItem(String text, ActionListener action, KeyStroke accelerator, int mnemonic)
	{
		JMenuItem newMenuItem = new JMenuItem(text);
		newMenuItem.addActionListener(action);
		newMenuItem.setAccelerator(accelerator);
		newMenuItem.setMnemonic(mnemonic);
		return newMenuItem;
	}
	
	/** Creates and returns a <code>JSpinner</code> that complies with the passed in parameter values.
	 * 
	 *  @param value the initial value to display in the <code>JSpinner</code>
	 *  @param min the minimum value for the <code>JSpinner</code>
	 *  @param max the maximum value for the <code>JSpinner</code>
	 *  @param increment the plus or minus increment for the <code>JSpinner</code>
	 *  @param height the height of the <code>JSpinner</code>
	 *  @return <code>JSpinner</code> that complies with the passed in parameter values
	 */
	public static JSpinner generateNumberSpinner(int value, int min, int max, int increment, int height)
	{
		JSpinner newSpinner = new JSpinner(new SpinnerNumberModel(value, min, max, increment));
		newSpinner.setMinimumSize(new Dimension(newSpinner.getPreferredSize().width, height));
		newSpinner.setMaximumSize(new Dimension(newSpinner.getPreferredSize().width, height));
		return newSpinner;
	}
	
	/** Creates and returns a <code>JSpinner</code> that complies with the passed in parameter values.
	 * 
	 *  @param value the initial value to display in the <code>JSpinner</code>
	 *  @param min the minimum value for the <code>JSpinner</code>
	 *  @param max the maximum value for the <code>JSpinner</code>
	 *  @param increment the plus or minus increment for the <code>JSpinner</code>
	 *  @param height the height of the <code>JSpinner</code>
	 *  @param alignmentX the horizontal alignment of the <code>JSpinner</code>
	 *  @return <code>JSpinner</code> that complies with the passed in parameter values
	 */
	public static JSpinner generateNumberSpinner(int value, int min, int max, int increment, int height, float alignmentX)
	{
		JSpinner newSpinner = new JSpinner(new SpinnerNumberModel(value, min, max, increment));
		newSpinner.setMinimumSize(new Dimension(newSpinner.getPreferredSize().width, height));
		newSpinner.setMaximumSize(new Dimension(newSpinner.getPreferredSize().width, height));
		newSpinner.setAlignmentX(alignmentX);
		return newSpinner;
	}
	
	/** Creates and returns a <code>JSpinner</code> that complies with the passed in parameter values.
	 * 
	 *  @param value the initial value to display in the <code>JSpinner</code>
	 *  @param min the minimum value for the <code>JSpinner</code>
	 *  @param max the maximum value for the <code>JSpinner</code>
	 *  @param increment the plus or minus increment for the <code>JSpinner</code>
	 *  @param height the height of the <code>JSpinner</code>
	 *  @param alignmentX the horizontal alignment of the <code>JSpinner</code>
	 *  @param alignmentY the vertical alignment of the <code>JSpinner</code>
	 *  @return <code>JSpinner</code> that complies with the passed in parameter values
	 */
	public static JSpinner generateNumberSpinner(int value, int min, int max, int increment, int height, float alignmentX, float alignmentY)
	{
		JSpinner newSpinner = new JSpinner(new SpinnerNumberModel(value, min, max, increment));
		newSpinner.setMinimumSize(new Dimension(newSpinner.getPreferredSize().width, height));
		newSpinner.setMaximumSize(new Dimension(newSpinner.getPreferredSize().width, height));
		newSpinner.setAlignmentX(alignmentX);
		newSpinner.setAlignmentY(alignmentY);
		return newSpinner;
	}
	
	/** Creates and returns a <code>JRadioButton</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display with the <code>JRadioButton</code>
	 *  @param selected <code>boolean</code> value indicating whether or not the <code>JRadioButton</code> should be initially selected
	 *  @return <code>JRadioButton</code> that complies with the passed in parameter values
	 */
	public static JRadioButton generateRadioButton(String text, boolean selected)
	{
		JRadioButton newRadioButton = new JRadioButton(text, selected);
		return newRadioButton;
	}
	
	/** Creates and returns a <code>JRadioButton</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display with the <code>JRadioButton</code>
	 *  @param selected <code>boolean</code> value indicating whether or not the <code>JRadioButton</code> should be initially selected
	 *  @param alignmentX the horizontal alignment of the <code>JRadioButton</code>
	 *  @return <code>JRadioButton</code> that complies with the passed in parameter values
	 */
	public static JRadioButton generateRadioButton(String text, boolean selected, float alignmentX)
	{
		JRadioButton newRadioButton = new JRadioButton(text, selected);
		newRadioButton.setAlignmentX(alignmentX);
		return newRadioButton;
	}
	
	/** Creates and returns a <code>JRadioButton</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the text to display with the <code>JRadioButton</code>
	 *  @param selected <code>boolean</code> value indicating whether or not the <code>JRadioButton</code> should be initially selected
	 *  @param alignmentX the horizontal alignment of the <code>JRadioButton</code>
	 *  @param alignmentY the vertical alignment of the <code>JRadioButton</code>
	 *  @return <code>JRadioButton</code> that complies with the passed in parameter values
	 */
	public static JRadioButton generateRadioButton(String text, boolean selected, float alignmentX, float alignmentY)
	{
		JRadioButton newRadioButton = new JRadioButton(text, selected);
		newRadioButton.setAlignmentX(alignmentX);
		newRadioButton.setAlignmentY(alignmentY);
		return newRadioButton;
	}
	
	/** Creates and returns a <code>JComboBox</code> that complies with the passed in parameter values.
	 * 
	 *  @param items genericized array of items to display in the <code>JComboBox</code>
	 *  @return <code>JComboBox</code> that complies with the passed in parameter values
	 */
	public static <T> JComboBox<T> generateComboBox(T[] items)
	{
		JComboBox<T> newComboBox = new JComboBox<T>(items);
		return newComboBox;
	}
	
	/** Creates and returns a <code>JComboBox</code> that complies with the passed in parameter values.
	 * 
	 *  @param items genericized array of items to display in the <code>JComboBox</code>
	 *  @param alignmentX the horizontal alignment of the <code>JComboBox</code>
	 *  @return <code>JComboBox</code> that complies with the passed in parameter values
	 */
	public static <T> JComboBox<T> generateComboBox(T[] items, float alignmentX)
	{
		JComboBox<T> newComboBox = new JComboBox<T>(items);
		newComboBox.setAlignmentX(alignmentX);
		return newComboBox;
	}
	
	/** Creates and returns a <code>JComboBox</code> that complies with the passed in parameter values.
	 * 
	 *  @param items genericized array of items to display in the <code>JComboBox</code>
	 *  @param alignmentX the horizontal alignment of the <code>JComboBox</code>
	 *  @param alignmentY the vertical alignment of the <code>JComboBox</code>
	 *  @return <code>JComboBox</code> that complies with the passed in parameter values
	 */
	public static <T> JComboBox<T> generateComboBox(T[] items, float alignmentX, float alignmentY)
	{
		JComboBox<T> newComboBox = new JComboBox<T>(items);
		newComboBox.setAlignmentX(alignmentX);
		newComboBox.setAlignmentY(alignmentY);
		return newComboBox;
	}
	
	/** Creates and returns an <code>InchesField</code> that complies with the passed in parameter values.
	 * 
	 *  @param value the default value displayed in the field
	 *  @param currentPanel the instance of <code>ResizeDialogue</code> tied to the component
	 *  @return <code>InchesField</code> that complies with the passed in parameter values
	 */
	public static InchesField generateInchesField(double value, ResizeDialogue currentPanel)
	{
		InchesField newInchesField = new InchesField(value, currentPanel);
		return newInchesField;
	}
	
	/** Creates and returns an <code>InchesField</code> that complies with the passed in parameter values.
	 * 
	 *  @param value the default value displayed in the field
	 *  @param currentPanel the instance of <code>ResizeDialogue</code> tied to the component
	 *  @param alignmentX the horizontal alignment of the field
	 *  @return <code>InchesField</code> that complies with the passed in parameter values
	 */
	public static InchesField generateInchesField(double value, ResizeDialogue currentPanel, float alignmentX)
	{
		InchesField newInchesField = new InchesField(value, currentPanel);
		newInchesField.setAlignmentX(alignmentX);
		return newInchesField;
	}
	
	/** Creates and returns an <code>InchesField</code> that complies with the passed in parameter values.
	 * 
	 *  @param value the default value displayed in the field
	 *  @param currentPanel the instance of <code>ResizeDialogue</code> tied to the component
	 *  @param alignmentX the horizontal alignment of the field
	 *  @param alignmentY the vertical alignment of the field
	 *  @return <code>InchesField</code> that complies with the passed in parameter values
	 */
	public static InchesField generateInchesField(double value, ResizeDialogue currentPanel, float alignmentX, float alignmentY)
	{
		InchesField newInchesField = new InchesField(value, currentPanel);
		newInchesField.setAlignmentX(alignmentX);
		newInchesField.setAlignmentY(alignmentY);
		return newInchesField;
	}
	
	/** Creates and returns a <code>PixelsField</code> that complies with the passed in parameter values.
	 * 
	 *  @param value the default value displayed in the field
	 *  @param currentPanel the instance of <code>ResizeDialogue</code> tied to the component
	 *  @return <code>PixelsField</code> that complies with the passed in parameter values
	 */
	public static PixelsField generatePixelsField(int value, ResizeDialogue currentPanel)
	{
		PixelsField newPixelsField = new PixelsField(value, currentPanel);
		return newPixelsField;
	}
	
	/** Creates and returns a <code>PixelsField</code> that complies with the passed in parameter values.
	 * 
	 *  @param value the default value displayed in the field
	 *  @param currentPanel the instance of <code>ResizeDialogue</code> tied to the component
	 *  @param alignmentX the horizontal alignment of the field
	 *  @return <code>PixelsField</code> that complies with the passed in parameter values
	 */
	public static PixelsField generatePixelsField(int value, ResizeDialogue currentPanel, float alignmentX)
	{
		PixelsField newPixelsField = new PixelsField(value, currentPanel);
		newPixelsField.setAlignmentX(alignmentX);
		return newPixelsField;
	}
	
	/** Creates and returns a <code>PixelsField</code> that complies with the passed in parameter values.
	 * 
	 *  @param value the default value displayed in the field
	 *  @param currentPanel the instance of <code>ResizeDialogue</code> tied to the component
	 *  @param alignmentX the horizontal alignment of the field
	 *  @param alignmentY the vertical alignment of the field
	 *  @return <code>PixelsField</code> that complies with the passed in parameter values
	 */
	public static PixelsField generatePixelsField(int value, ResizeDialogue currentPanel, float alignmentX, float alignmentY)
	{
		PixelsField newPixelsField = new PixelsField(value, currentPanel);
		newPixelsField.setAlignmentX(alignmentX);
		newPixelsField.setAlignmentY(alignmentY);
		return newPixelsField;
	}
	
	/** Creates and returns a <code>StringField</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the default text displayed in the field
	 *  @return <code>StringField</code> that complies with the passed in parameter values
	 */
	public static StringField generateStringField(String text)
	{
		StringField newStringField = new StringField(text);
		return newStringField;
	}
	
	/** Creates and returns a <code>StringField</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the default text displayed in the field
	 *  @param alignmentX the horizontal alignment of the field
	 *  @return <code>StringField</code> that complies with the passed in parameter values
	 */
	public static StringField generateStringField(String text, float alignmentX)
	{
		StringField newStringField = new StringField(text);
		newStringField.setAlignmentX(alignmentX);
		return newStringField;
	}

	/** Creates and returns a <code>StringField</code> that complies with the passed in parameter values.
	 * 
	 *  @param text the default text displayed in the field
	 *  @param alignmentX the horizontal alignment of the field
	 *  @param alignmentY the vertical alignment of the field
	 *  @return <code>StringField</code> that complies with the passed in parameter values
	 */
	public static StringField generateStringField(String text, float alignmentX, float alignmentY)
	{
		StringField newStringField = new StringField(text);
		newStringField.setAlignmentX(alignmentX);
		newStringField.setAlignmentY(alignmentY);
		return newStringField;
	}
	
	/** Accepts a <code>Font</code> object and returns that same object, with underline formatting applied.
	 * 
	 *  @param font the original <code>Font</code>
	 *  @return the <code>Font</code> with underline formatting applied
	 */
	public static Font addUnderline(Font font)
	{
		Map<TextAttribute, Integer>  attributes = new HashMap<TextAttribute, Integer>();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		return font.deriveFont(attributes);
	}
	
	/** Accepts a <code>Font</code> object and returns that same object, with underline formatting removed.
	 * 
	 *  @param font the original <code>Font</code>
	 *  @return the <code>Font</code> with underline formatting removed
	 */
	public static Font removeUnderline(Font font)
	{
		Map<TextAttribute, Integer>  attributes = new HashMap<TextAttribute, Integer>();
		attributes.put(TextAttribute.UNDERLINE, -1);
		return font.deriveFont(attributes);
	}
	
}