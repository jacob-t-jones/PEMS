// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// CaseBox.java

package gui.components.box;
import java.awt.event.*;
import javax.swing.*;
import org.imgscalr.*;
import backend.exceptions.*;
import backend.storage.*;
import gui.*;
import gui.components.icon.*;

public class CaseBox extends Box implements MouseListener
{

	private Case curCase;
	private ImgIcon folderIcon;
	private JLabel numLabel;

	public CaseBox(Case curCase)
	{
		super(BoxLayout.LINE_AXIS);
		if (curCase == null)
		{
			throw new NullPointerException();
		}
		this.curCase = curCase;
		this.populateBox();
		super.revalidate();
		super.repaint();
	}
	
	private void populateBox()
	{
		this.numLabel = ComponentGenerator.generateLabel(this.curCase.getCaseNum(), ComponentGenerator.addUnderline(ComponentGenerator.SMALL_TEXT_FONT), ComponentGenerator.LINK_TEXT_COLOR);
		this.numLabel.addMouseListener(this);
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

	public void mouseClicked(MouseEvent e) 
	{

	}

	public void mousePressed(MouseEvent e) 
	{
		
	}

	public void mouseReleased(MouseEvent e) 
	{
		
	}

	public void mouseEntered(MouseEvent e) 
	{
		if (e.getSource() == this.numLabel)
		{
			this.numLabel.setFont(ComponentGenerator.removeUnderline(ComponentGenerator.SMALL_TEXT_FONT));
			this.numLabel.revalidate();
			this.numLabel.repaint();
		}
	}

	public void mouseExited(MouseEvent e) 
	{
		if (e.getSource() == this.numLabel)
		{
			this.numLabel.setFont(ComponentGenerator.addUnderline(ComponentGenerator.SMALL_TEXT_FONT));
			this.numLabel.repaint();
			this.numLabel.repaint();
		}
	}
		
}