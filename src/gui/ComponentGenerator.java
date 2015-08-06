// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ComponentGenerator.java

package gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import exceptions.*;
import gui.components.field.*;
import gui.components.img.*;

public class ComponentGenerator 
{
	
	public static final Font TITLE_FONT = new Font("Courier New", Font.BOLD, 30);
	public static final Font SUBTITLE_FONT = new Font("Courier New", Font.BOLD, 22);
	public static final Font STANDARD_TEXT_FONT = new Font("Georgia", Font.PLAIN, 14);
	public static final Font STANDARD_TEXT_FONT_BOLD = new Font("Georgia", Font.BOLD, 14);
	public static final Font STANDARD_TEXT_FONT_ITALIC = new Font("Georgia", Font.ITALIC, 14);
	public static final Font ERROR_TEXT_FONT = new Font("Georgia", Font.ITALIC, 12);
	public static final Font SMALL_TEXT_FONT = new Font ("Georgia", Font.PLAIN, 10);
	public static final Font MINI_TEXT_FONT = new Font("Georgia", Font.PLAIN, 8);
	public static final Color TITLE_COLOR = new Color(46, 46, 46);
	public static final Color SUBTITLE_COLOR = new Color(2, 2, 123);
	public static final Color STANDARD_TEXT_COLOR = new Color(44, 29, 29);
	public static final Color ERROR_TEXT_COLOR = new Color(202, 12, 12);
	public static final Color CROPBOX_COLOR = new Color(250, 40, 25);
	
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
	 *          img - the text displayed in the label
	 *          font - the font type to use in the label
	 *         color - the font color to use in the label
	 *    alignmentX - the horizontal alignment of the label
	 */
	public static JLabel generateLabel(Img img, float alignmentX)
	{
		JLabel newLabel = new JLabel();
		newLabel.add(img);
		newLabel.setAlignmentX(alignmentX);
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
	
	/* generateStringField - creates and returns a StringField that complies with the parameters
	 * 	              text - the default text displayed in the field
	 */
	public static StringField generateStringField(String text)
	{
		StringField newStringField = new StringField(text);
		return newStringField;
	}
	
	/* generateStringField - creates and returns a StringField that complies with the parameters
	 * 	              text - the default text displayed in the field
	 *          alignmentX - the horizontal alignment of the field
	 */
	public static StringField generateStringField(String text, float alignmentX)
	{
		StringField newStringField = new StringField(text);
		newStringField.setAlignmentX(alignmentX);
		return newStringField;
	}
	
	/* generateStringField - creates and returns a StringField that complies with the parameters
	 * 	              text - the default text displayed in the field
	 *          alignmentX - the horizontal alignment of the field
	 *          alignmentY - the vertical alignment of the field
	 */
	public static StringField generateStringField(String text, float alignmentX, float alignmentY)
	{
		StringField newStringField = new StringField(text);
		newStringField.setAlignmentX(alignmentX);
		newStringField.setAlignmentY(alignmentY);
		return newStringField;
	}
	
	/* generateDecimalField - creates and returns a DecimalField that complies with the parameters
	 *                value - the default value displayed in the field
	 *                focus - the FocusListener for the field
	 */
	public static DecimalField generateDecimalField(double value, FocusListener focus)
	{
		DecimalField newDecimalField = new DecimalField(value);
		newDecimalField.addFocusListener(focus);
		return newDecimalField;
	}
	
	/* generateDecimalField - creates and returns a DecimalField that complies with the parameters
	 *                value - the default value displayed in the field
	 *                focus - the FocusListener for the field
	 *           alignmentX - the horizontal alignment of the field
	 */
	public static DecimalField generateDecimalField(double value, FocusListener focus, float alignmentX)
	{
		DecimalField newDecimalField = new DecimalField(value);
		newDecimalField.addFocusListener(focus);
		newDecimalField.setAlignmentX(alignmentX);
		return newDecimalField;
	}
	
	/* generateDecimalField - creates and returns a DecimalField that complies with the parameters
	 *                value - the default value displayed in the field
	 *                focus - the FocusListener for the field
	 *           alignmentX - the horizontal alignment of the field
	 *           alignmentY - the vertical alignment of the field
	 */
	public static DecimalField generateDecimalField(double value, FocusListener focus, float alignmentX, float alignmentY)
	{
		DecimalField newDecimalField = new DecimalField(value);
		newDecimalField.addFocusListener(focus);
		newDecimalField.setAlignmentX(alignmentX);
		newDecimalField.setAlignmentY(alignmentY);
		return newDecimalField;
	}
	
	/* generateIntegerField - creates and returns a IntegerField that complies with the parameters
	 *                value - the default value displayed in the field
	 *                focus - the FocusListener for the field
	 */
	public static IntegerField generateIntegerField(int value, FocusListener focus)
	{
		IntegerField newIntegerField = new IntegerField(value);
		newIntegerField.addFocusListener(focus);
		return newIntegerField;
	}
	
	/* generateIntegerField - creates and returns a IntegerField that complies with the parameters
	 *                value - the default value displayed in the field
	 *                focus - the FocusListener for the field
	 *           alignmentX - the horizontal alignment of the field
	 */
	public static IntegerField generateIntegerField(int value, FocusListener focus, float alignmentX)
	{
		IntegerField newIntegerField = new IntegerField(value);
		newIntegerField.addFocusListener(focus);
		newIntegerField.setAlignmentX(alignmentX);
		return newIntegerField;
	}
	
	/* generateIntegerField - creates and returns a IntegerField that complies with the parameters
	 *                value - the default value displayed in the field
	 *                focus - the FocusListener for the field
	 *           alignmentX - the horizontal alignment of the field
	 *           alignmentY - the vertical alignment of the field
	 */
	public static IntegerField generateIntegerField(int value, FocusListener focus, float alignmentX, float alignmentY)
	{
		IntegerField newIntegerField = new IntegerField(value);
		newIntegerField.addFocusListener(focus);
		newIntegerField.setAlignmentX(alignmentX);
		newIntegerField.setAlignmentY(alignmentY);
		return newIntegerField;
	}
	
	/* generateImg - creates and returns an Img that complies with the parameters
	 *    filePath - the directory path the image will be loaded from
	 *             * throws InvalidImgException if the image cannot be loaded into memory
	 */
	public static Img generateImg(String filePath) throws InvalidImgException
	{
		Img newImg = new Img(filePath);
		return newImg;
	}
	
	/* generateImg - creates and returns an Img that complies with the parameters
	 *    filePath - the directory path the image will be loaded from
	 *  alignmentX - the horizontal alignment of the image
	 *             * throws InvalidImgException if the image cannot be loaded into memory
	 */
	public static Img generateImg(String filePath, float alignmentX) throws InvalidImgException
	{
		Img newImg = new Img(filePath);
		newImg.setAlignmentX(alignmentX);
		return newImg;
	}
	
	/* generateImg - creates and returns an Img that complies with the parameters
	 *    filePath - the directory path the image will be loaded from
	 *  alignmentX - the horizontal alignment of the image
	 *  alignmentY - the vertical alignment of the image
	 *             * throws InvalidImgException if the image cannot be loaded into memory
	 */
	public static Img generateImg(String filePath, float alignmentX, float alignmentY) throws InvalidImgException
	{
		Img newImg = new Img(filePath);
		newImg.setAlignmentX(alignmentX);
		newImg.setAlignmentY(alignmentY);
		return newImg;
	}
	
	/* generateEditedImg - creates and returns an EditedImg that complies with the parameters
	 *          filePath - the directory path the image will be loaded from
	 *           		 * throws InvalidImgException if the image cannot be loaded into memory
	 */
	public static EditedImg generateEditedImg(String filePath) throws InvalidImgException
	{
		EditedImg newEditedImg = new EditedImg(filePath);
		return newEditedImg;
	}
	
	/* generateEditedImg - creates and returns an EditedImg that complies with the parameters
	 *          filePath - the directory path the image will be loaded from
	 *        alignmentX - the horizontal alignment of the image
	 *           		 * throws InvalidImgException if the image cannot be loaded into memory
	 */
	public static EditedImg generateEditedImg(String filePath, float alignmentX) throws InvalidImgException
	{
		EditedImg newEditedImg = new EditedImg(filePath);
		newEditedImg.setAlignmentX(alignmentX);
		return newEditedImg;
	}
	
	/* generateEditedImg - creates and returns an EditedImg that complies with the parameters
	 *          filePath - the directory path the image will be loaded from
	 *        alignmentX - the horizontal alignment of the image
	 *        alignmentY - the vertical alignment of the image
	 *           		 * throws InvalidImgException if the image cannot be loaded into memory
	 */
	public static EditedImg generateEditedImg(String filePath, float alignmentX, float alignmentY) throws InvalidImgException
	{
		EditedImg newEditedImg = new EditedImg(filePath);
		newEditedImg.setAlignmentX(alignmentX);
		newEditedImg.setAlignmentY(alignmentY);
		return newEditedImg;
	}
	
	/* generateThumbnailImg - creates and returns a ThumbnailImg that complies with the parameters
	 *             filePath - the directory path the image will be loaded from
	 *                 size - the new size of the image
	 *           		    * throws InvalidImgException if the image cannot be loaded into memory
	 */
	public static ThumbnailImg generateThumbnailImg(String filePath, int size) throws InvalidImgException
	{
		ThumbnailImg newThumbnailImg = new ThumbnailImg(filePath, size);
		return newThumbnailImg;
	}
	
	/* generateThumbnailImg - creates and returns a ThumbnailImg that complies with the parameters
	 *             filePath - the directory path the image will be loaded from
	 *                 size - the new size of the image
	 *           alignmentX - the horizontal alignment of the image
	 *           		    * throws InvalidImgException if the image cannot be loaded into memory
	 */
	public static ThumbnailImg generateThumbnailImg(String filePath, int size, float alignmentX) throws InvalidImgException
	{
		ThumbnailImg newThumbnailImg = new ThumbnailImg(filePath, size);
		newThumbnailImg.setAlignmentX(alignmentX);
		return newThumbnailImg;
	}
	
	/* generateThumbnailImg - creates and returns a ThumbnailImg that complies with the parameters
	 *             filePath - the directory path the image will be loaded from
	 *                 size - the new size of the image
	 *           alignmentX - the horizontal alignment of the image
	 *           alignmentY - the vertical alignment of the image
	 *                      * throws InvalidImgException if the image cannot be loaded into memory
	 */
	public static ThumbnailImg generateThumbnailImg(String filePath, int size, float alignmentX, float alignmentY) throws InvalidImgException
	{
		ThumbnailImg newThumbnailImg = new ThumbnailImg(filePath, size);
		newThumbnailImg.setAlignmentX(alignmentX);
		newThumbnailImg.setAlignmentY(alignmentY);
		return newThumbnailImg;
	}
	
}