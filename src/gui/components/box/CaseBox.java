// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// CaseBox.java

package gui.components.box;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.imgscalr.*;

import backend.storage.file.img.Img;
import exceptions.*;
import gui.*;
import gui.components.img.*;
import gui.display.*;
import gui.display.select.*;

public class CaseBox extends Box implements MouseListener
{

	private FrameManager manager;
	private Img folderImg;
	private JLabel nameLabel;
	private String caseNum;

	public CaseBox(FrameManager manager, String caseNum)
	{
		super(BoxLayout.LINE_AXIS);
		this.manager = manager;
		this.caseNum = caseNum;
		this.populateBox();
		super.addMouseListener(this);
		super.revalidate();
		super.repaint();
	}
	
	private void populateBox()
	{
		this.nameLabel = ComponentGenerator.generateLabel(this.caseNum, ComponentGenerator.addUnderline(ComponentGenerator.SMALL_TEXT_FONT), ComponentGenerator.LINK_TEXT_COLOR);
		this.nameLabel.addMouseListener(this);
		try 
		{
			this.folderImg = ComponentGenerator.generateImg("resources/folder.png");
			this.folderImg.resizeImage(Scalr.Method.ULTRA_QUALITY, 25, 25);
			super.add(this.folderImg);
		} 
		catch (InvalidImgException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			super.add(Box.createHorizontalStrut(25));
		}
		super.add(Box.createHorizontalStrut(25));
		super.add(this.nameLabel);
	}

	public void mouseClicked(MouseEvent e) 
	{
		if (e.getSource() == this)
		{
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			this.manager.getMainWindow().pushPanel(new SelectPanel(this.manager, this.caseNum), "Import Images");
		}
	}

	public void mousePressed(MouseEvent e) 
	{
		
	}

	public void mouseReleased(MouseEvent e) 
	{
		
	}

	public void mouseEntered(MouseEvent e) 
	{
		if (e.getSource() == this.nameLabel)
		{
			this.nameLabel.setFont(ComponentGenerator.removeUnderline(ComponentGenerator.SMALL_TEXT_FONT));
			this.nameLabel.revalidate();
			this.nameLabel.repaint();
		}
	}

	public void mouseExited(MouseEvent e) 
	{
		if (e.getSource() == this.nameLabel)
		{
			this.nameLabel.setFont(ComponentGenerator.addUnderline(ComponentGenerator.SMALL_TEXT_FONT));
			this.nameLabel.repaint();
			this.nameLabel.repaint();
		}
	}
		
}
