// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ComponentGenerator.java

package gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.util.*;
import javax.swing.*;
import org.imgscalr.*;
import exceptions.*;
import gui.components.box.*;
import gui.components.field.*;
import gui.components.img.*;
import gui.display.*;
import gui.display.dialogues.*;

public class ComponentGenerator 
{
	
	public static final Font TITLE_FONT = new Font("Courier New", Font.BOLD, 30);
	public static final Font SUBTITLE_FONT = new Font("Courier New", Font.BOLD, 22);
	public static final Font STANDARD_TEXT_FONT = new Font("Georgia", Font.PLAIN, 14);
	public static final Font STANDARD_TEXT_FONT_BOLD = new Font("Georgia", Font.BOLD, 14);
	public static final Font STANDARD_TEXT_FONT_ITALIC = new Font("Georgia", Font.ITALIC, 14);
	public static final Font SMALL_TEXT_FONT = new Font("Courier New", Font.PLAIN, 12);
	public static final Font SMALL_TEXT_FONT_BOLD = new Font("Courier New", Font.BOLD, 12);
	public static final Font SMALL_TEXT_FONT_ITALIC = new Font("Courier New", Font.ITALIC, 12);
	public static final Font MINI_TEXT_FONT = new Font ("Georgia", Font.PLAIN, 10);
	public static final Font MINI_TEXT_FONT_BOLD = new Font ("Georgia", Font.BOLD, 10);
	public static final Font MINI_TEXT_FONT_ITALIC = new Font ("Georgia", Font.ITALIC, 10);
	public static final Color TITLE_COLOR = new Color(46, 46, 46);
	public static final Color SUBTITLE_COLOR = new Color(2, 2, 123);
	public static final Color STANDARD_TEXT_COLOR = new Color(44, 29, 29);
	public static final Color ERROR_TEXT_COLOR = new Color(202, 12, 12);
	public static final Color LINK_TEXT_COLOR = new Color(42, 68, 219);
	public static final Color CROPBOX_COLOR = new Color(250, 40, 25);
	public static final Color SELECTED_THUMBNAIL_BG_COLOR = new Color(219, 219, 219);
	
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
	
	/* generateLabel - creates and returns a JLabel that complies with the parameters
	 *           img - the image displayed in the label
	 */
	public static JLabel generateLabel(Img img)
	{
		JLabel newLabel = new JLabel();
		newLabel.add(img);
		return newLabel;
	}
	
	/* generateLabel - creates and returns a JLabel that complies with the parameters
	 *           img - the image displayed in the label
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
	 *           img - the image displayed in the label
	 *    alignmentX - the horizontal alignment of the label
	 *    alignmentY - the vertical alignment of the label
	 */
	public static JLabel generateLabel(Img img, float alignmentX, float alignmentY)
	{
		JLabel newLabel = new JLabel();
		newLabel.add(img);
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
	
	/* generateCaseBox - creates and returns a CaseBox that complies with the parameters
	 *         manager - the current instance of FrameManager
	 *         caseNum - the number of the case that the component is being generated for
	 */
	public static CaseBox generateCaseBox(FrameManager manager, String caseNum)
	{
		CaseBox newCaseBox = new CaseBox(manager, caseNum);
		return newCaseBox;
	}
	
	/* generateCaseBox - creates and returns a CaseBox that complies with the parameters
	 *         manager - the current instance of FrameManager
	 *         caseNum - the number of the case that the component is being generated for
	 *      alignmentX - the horizontal alignment of the box
	 */
	public static CaseBox generateCaseBox(FrameManager manager, String caseNum, float alignmentX)
	{
		CaseBox newCaseBox = new CaseBox(manager, caseNum);
		newCaseBox.setAlignmentX(alignmentX);
		return newCaseBox;
	}
	
	/* generateCaseBox - creates and returns a CaseBox that complies with the parameters
	 *         caseNum - the number of the case that the component is being generated for
	 *      alignmentX - the horizontal alignment of the box
	 *      alignmentY - the vertical alignment of the box
	 */
	public static CaseBox generateCaseBox(FrameManager manager, String caseNum, float alignmentX, float alignmentY)
	{
		CaseBox newCaseBox = new CaseBox(manager, caseNum);
		newCaseBox.setAlignmentX(alignmentX);
		newCaseBox.setAlignmentY(alignmentY);
		return newCaseBox;
	}
	
	/* generateInchesField - creates and returns a InchesField that complies with the parameters
	 *                value - the default value displayed in the field
	 *         currentPanel - the instance of ResizeDialogue tied to the component
	 */
	public static InchesField generateInchesField(double value, ResizeDialogue currentPanel)
	{
		InchesField newInchesField = new InchesField(value, currentPanel);
		return newInchesField;
	}
	
	/* generateInchesField - creates and returns a InchesField that complies with the parameters
	 *                value - the default value displayed in the field
	 *         currentPanel - the instance of ResizeDialogue tied to the component
	 *           alignmentX - the horizontal alignment of the field
	 */
	public static InchesField generateInchesField(double value, ResizeDialogue currentPanel, float alignmentX)
	{
		InchesField newInchesField = new InchesField(value, currentPanel);
		newInchesField.setAlignmentX(alignmentX);
		return newInchesField;
	}
	
	/* generateInchesField - creates and returns a InchesField that complies with the parameters
	 *                value - the default value displayed in the field
	 *         currentPanel - the instance of ResizeDialogue tied to the component
	 *           alignmentX - the horizontal alignment of the field
	 *           alignmentY - the vertical alignment of the field
	 */
	public static InchesField generateInchesField(double value, ResizeDialogue currentPanel, float alignmentX, float alignmentY)
	{
		InchesField newInchesField = new InchesField(value, currentPanel);
		newInchesField.setAlignmentX(alignmentX);
		newInchesField.setAlignmentY(alignmentY);
		return newInchesField;
	}
	
	/* generatePixelsField - creates and returns a PixelsField that complies with the parameters
	 *                value - the default value displayed in the field
	 *         currentPanel - the instance of ResizeDialogue tied to the component
	 */
	public static PixelsField generatePixelsField(int value, ResizeDialogue currentPanel)
	{
		PixelsField newPixelsField = new PixelsField(value, currentPanel);
		return newPixelsField;
	}
	
	/* generatePixelsField - creates and returns a PixelsField that complies with the parameters
	 *                value - the default value displayed in the field
	 *         currentPanel - the instance of ResizeDialogue tied to the component
	 *           alignmentX - the horizontal alignment of the field
	 */
	public static PixelsField generatePixelsField(int value, ResizeDialogue currentPanel, float alignmentX)
	{
		PixelsField newPixelsField = new PixelsField(value, currentPanel);
		newPixelsField.setAlignmentX(alignmentX);
		return newPixelsField;
	}
	
	/* generatePixelsField - creates and returns a PixelsField that complies with the parameters
	 *                value - the default value displayed in the field
	 *         currentPanel - the instance of ResizeDialogue tied to the component
	 *           alignmentX - the horizontal alignment of the field
	 *           alignmentY - the vertical alignment of the field
	 */
	public static PixelsField generatePixelsField(int value, ResizeDialogue currentPanel, float alignmentX, float alignmentY)
	{
		PixelsField newPixelsField = new PixelsField(value, currentPanel);
		newPixelsField.setAlignmentX(alignmentX);
		newPixelsField.setAlignmentY(alignmentY);
		return newPixelsField;
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
	
	/* fitImageToScreen - returns a version of a given EditedImg that is shrunk down in order to fit EditImgPanel properly
	 *              img - the EditedImg to resize
	 */
	public static EditedImg fitImageToScreen(EditedImg img) throws InvalidImgException
	{
		EditedImg fittedImg = new EditedImg(img.getFilePath());
		if (fittedImg.getImage().getHeight() > 500)
		{
			fittedImg.clearLastHistoryEntry();
			fittedImg.resizeImage(Scalr.Method.QUALITY, 500);
		}
		else if (fittedImg.getImage().getWidth() > 1200)
		{
			fittedImg.clearLastHistoryEntry();
			fittedImg.resizeImage(Scalr.Method.QUALITY, 1200);

		}
		return fittedImg;
	}
	
	/* addUnderline - adds the underline attribute to the passed in Font, and returns the modified version
	 *         font - the Font to modify
	 */
	public static Font addUnderline(Font font)
	{
		Map<TextAttribute, Integer>  attributes = new HashMap<TextAttribute, Integer>();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		return font.deriveFont(attributes);
	}
	
	/* removeUnderline - removes the underline attribute from the passed in Font, and returns the modified version
	 *            font - the Font to modify
	 */
	public static Font removeUnderline(Font font)
	{
		Map<TextAttribute, Integer>  attributes = new HashMap<TextAttribute, Integer>();
		attributes.put(TextAttribute.UNDERLINE, -1);
		return font.deriveFont(attributes);
	}
	
}