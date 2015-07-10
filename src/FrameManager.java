// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// FrameManager.java

import java.awt.*;
import javax.swing.*;

public class FrameManager 
{
	
	private JFrame mainFrame;
	
	public FrameManager()
	{
        this.mainFrame = new JFrame("PEMS (Police Evidence Management System) 0.1");
        this.mainFrame.getContentPane().add(new ScreenStart(this));
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.pack();
        this.mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.mainFrame.setVisible(true);
	}
	
	public void pushPanel(JPanel panel, String title)
	{
		this.mainFrame.getContentPane().removeAll();
		this.mainFrame.getContentPane().add(panel);
		this.mainFrame.getContentPane().revalidate();
		this.mainFrame.getContentPane().repaint();
		this.mainFrame.setTitle(title);
	}

}
