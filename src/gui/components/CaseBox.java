// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// CaseBox.java

package gui.components;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.imgscalr.*;
import backend.exceptions.*;
import gui.*;
import gui.components.icon.*;

/** Subclass of <code>Box</code> containing a folder icon and a case number. Used to graphically represent the cases managed by PEMS.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class CaseBox extends Box implements MouseListener
{

	private ImgIcon folderIcon;
	private JLabel numLabel;
	private String caseNum;

	/** Sets the dimensions of this <code>Box</code>, and adds a folder icon and a <code>JLabel</code> containing the case number to it.
	 * 
	 *  @param caseNum the number of the case that this <code>Box</code> represents
	 *  @throws NullPointerException if the parameter is null
	 */
	public CaseBox(String caseNum)
	{
		super(BoxLayout.X_AXIS);
		if (caseNum == null)
		{
			throw new NullPointerException();
		}
		this.caseNum = caseNum;
		this.populateBox();
		super.setPreferredSize(new Dimension(130, 30));
		super.setMinimumSize(new Dimension(130, 30));
		super.setMaximumSize(new Dimension(130, 30));
		super.addMouseListener(this);
		super.revalidate();
		super.repaint();
	}
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseClicked(MouseEvent e) 
	{
		
	}

	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mousePressed(MouseEvent e) 
	{
		
	}

	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseReleased(MouseEvent e) 
	{
		
	}

	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 *  <p>
	 *  When the mouse enters the area contained within this <code>Box</code>, the underlining is removed from <code>numLabel</code>.
	 */
	public void mouseEntered(MouseEvent e) 
	{
		this.numLabel.setFont(ComponentGenerator.removeUnderline(ComponentGenerator.SMALL_TEXT_FONT));
		this.numLabel.revalidate();
		this.numLabel.repaint();
	}

	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 *  <p>
	 *  When the mouse exits the area contained within this <code>Box</code>, the underlining is re-added to <code>numLabel</code>.
	 */
	public void mouseExited(MouseEvent e) 
	{
		this.numLabel.setFont(ComponentGenerator.addUnderline(ComponentGenerator.SMALL_TEXT_FONT));
		this.numLabel.repaint();
		this.numLabel.repaint();
	}
	
	/** Returns a <code>String</code> containing the case number of the case that this <code>Box</code> represents.
	 * 
	 *  @return <code>String</code> containing the case number of the case that this <code>Box</code> represents
	 */
	public String getCaseNum()
	{
		return this.caseNum;
	}
	
	/** Adds <code>folderIcon</code> and <code>numLabel</code> to this <code>Box</code>.
	 */
	private void populateBox()
	{
		this.numLabel = ComponentGenerator.generateLabel(this.caseNum, ComponentGenerator.addUnderline(ComponentGenerator.SMALL_TEXT_FONT), ComponentGenerator.LINK_TEXT_COLOR);
		try 
		{
			this.folderIcon = new ImgIcon("resources/folder.png", Scalr.Method.ULTRA_QUALITY, 25, 25);
			super.add(this.folderIcon);
		} 
		catch (InvalidFileException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			super.add(Box.createHorizontalStrut(25));
		}
		super.add(Box.createHorizontalStrut(25));
		super.add(this.numLabel);
	}
		
}