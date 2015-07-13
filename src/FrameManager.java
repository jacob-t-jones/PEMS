// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// FrameManager.java

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class FrameManager 
{
	
	public final Font TITLE_FONT = new Font("Courier New", Font.BOLD, 30);
	public final Font SUBTITLE_FONT = new Font("Courier New", Font.BOLD, 22);
	public final Font STANDARD_TEXT_FONT = new Font("Arial", Font.PLAIN, 18);
	public final Color TITLE_COLOR = new Color(46, 46, 46);
	public final Color SUBTITLE_COLOR = new Color(2, 2, 123);
	//public final Color STANDARD_TEXT_COLOR = new Color
	private Config configuration;
	private JFrame mainFrame;
	
	public FrameManager()
	{
		this.configuration = new Config();
        this.mainFrame = new JFrame("PEMS (Police Evidence Management System) Version 0.1");
        this.mainFrame.getContentPane().add(new ScreenStart(this));
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.pack();
        this.mainFrame.setBounds(this.widthToPixels(25), this.heightToPixels(25), this.widthToPixels(50), this.heightToPixels(50));
        this.mainFrame.setResizable(false);
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
	
	/* maximizeFrame - maximizes the window the program is currently running in
	 */
	public void maximizeFrame()
	{
		this.mainFrame.setResizable(true);
		this.mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/* widthToPixels - converts a width percentage value to its equivalent pixel value
	 *       percent - the value to convert to pixels
	 */
	public int widthToPixels(double percent)
	{
		return (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * (percent / 100.0));
	}
	
	/* heightToPixels - converts a height percentage value to its equivalent pixel value
	 *        percent - the value to convert to pixels
	 */
	public int heightToPixels(double percent)
	{
		return (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * (percent / 100.0));
	}
	
	/* resizeImage - returns a resized version of a given BufferedImage
	 * 		 image - the image to resize
	 * 	     width - the desired width
	 *      height - the desired height
	 */
	public BufferedImage resizeImage(BufferedImage image, int width, int height)
	{
		Image tempImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); 
		Graphics2D g = newImage.createGraphics();
		g.drawImage(tempImage, 0, 0, null);
		g.dispose();
		return newImage;
	}
	
	/* getConfiguration - returns the global instance of the Config class
	 */
	public Config getConfiguration()
	{
		return this.configuration;
	}
	
}
