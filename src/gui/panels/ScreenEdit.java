// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenEdit.java

package gui.panels;
import java.awt.event.*;
import javax.swing.*;
import gui.*;

public class ScreenEdit extends JPanel implements ActionListener
{
	
	private FrameManager manager;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu caseMenu;
	private JMenu imageMenu;
	private JMenuItem renameImageMenuItem;
	private JMenuItem removeImageMenuItem;
	private JMenuItem antiAliasMenuItem;
	private JMenuItem brightenMenuItem;
	private JMenuItem darkenMenuItem;
	private JMenuItem grayscaleMenuItem;
	private JMenuItem resizeMenuItem;
	private JMenuItem rotate90MenuItem;
	private JMenuItem rotate180MenuItem;
	private JMenuItem rotate270MenuItem;
	
	public ScreenEdit(FrameManager manager)
	{
		this.manager = manager;
		this.constructMenuBar();
	}
	
	public void actionPerformed(ActionEvent e)
	{ 
		if (e.getSource() == this.renameImageMenuItem)
		{
			
		}
		else if (e.getSource() == this.removeImageMenuItem)
		{
			
		}
	    else if (e.getSource() == this.antiAliasMenuItem)
		{
			
		}
		else if (e.getSource() == this.brightenMenuItem)
		{
			
		}
		else if (e.getSource() == this.darkenMenuItem)
		{
			
		}
		else if (e.getSource() == this.grayscaleMenuItem)
		{
			
		}
		else if (e.getSource() == this.resizeMenuItem)
		{
			
		}
		else if (e.getSource() == this.rotate90MenuItem)
		{
			
		}
		else if (e.getSource() == this.rotate180MenuItem)
		{
			
		}
		else if (e.getSource() == this.rotate270MenuItem)
		{
			
		}
	}
	
	private void constructMenuBar()
	{
		this.menuBar = new JMenuBar();
		this.fileMenu = new JMenu();
		this.caseMenu = new JMenu();
		this.imageMenu = new JMenu();
	}

}
