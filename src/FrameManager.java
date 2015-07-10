// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// FrameManager.java

import java.awt.*;
import javax.swing.*;

public class FrameManager 
{
	
	private JFrame mainFrame;
	private Config configuration;
	
	public FrameManager()
	{
        this.mainFrame = new JFrame("PEMS (Police Evidence Management System) 0.1");
        this.mainFrame.getContentPane().add(new ScreenStart(this));
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.pack();
        this.mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.mainFrame.setVisible(true);
	}
	
	/* pushPanel - removes the current panel and replaces it with a newly constructed one
	 *	   panel - new panel to display
	 *     title - title of the new panel
	 */
	public void pushPanel(JPanel panel, String title)
	{
		this.mainFrame.getContentPane().removeAll();
		this.mainFrame.getContentPane().add(panel);
		this.mainFrame.getContentPane().revalidate();
		this.mainFrame.getContentPane().repaint();
		this.mainFrame.setTitle(title);
	}
	
	/* widthToPixels - converts a screen width percentage value to pixels
	 *       percent - the value to convert to pixels
	 */
	public int widthToPixels(double percent)
	{
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		return (int)(screenWidth * (percent / 100.0));
	}
	
	/* heightToPixels - converts a screen height percentage value to pixels
	 *        percent - the value to convert to pixels
	 */
	public int heightToPixels(double percent)
	{
		double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();	
		return (int)(screenHeight * (percent / 100.0));
	}

}
