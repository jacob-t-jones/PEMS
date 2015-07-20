package gui.panels;

import gui.ComponentGenerator;
import gui.FrameManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ScreenFinish extends JPanel implements ActionListener
{
	
	private FrameManager manager;
	private String caseNum;
	private Box container;
	private Box optionsBox;
	private JButton openInDirButton;
	private JButton printButton;
	private JButton closeButton;
	
	public ScreenFinish(FrameManager manager, String caseNum) 
	{
		this.manager = manager;
		this.caseNum = caseNum;
		this.container = Box.createVerticalBox();
		
	}
	
	private void populateBox()
	{
		this.openInDirButton = ComponentGenerator.generateButton("Open in Director", this, CENTER_ALIGNMENT);
		this.printButton = ComponentGenerator.generateButton("Print Image", this, CENTER_ALIGNMENT);
		this.closeButton = ComponentGenerator.generateButton("Exit", this, CENTER_ALIGNMENT);
		
		this.optionsBox.add(openInDirButton);
		this.optionsBox.add(printButton);
		this.container.add(optionsBox);
		this.container.add(closeButton);
		
		this.revalidate();
		this.repaint();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == this.closeButton)
		{
			this.manager.closePanel();
		}
		
		if (e.getSource() == this.printButton)
		{
			manager.pushPanel(new ScreenImport(manager, this.caseNum), "PEMS - Print Images");
		}
		
		if (e.getSource() == this.openInDirButton)
		{
			String OSname = getOS();
			if(OSname == "win")
			{
				//..CHANGE TO OPENING IN WINDOWS..
				try {
					Runtime.getRuntime().exec("open /Users/andrewrottier/Documents/Pictures/");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else if(OSname == "mac")
			{
				try {
					Runtime.getRuntime().exec("open /Users/andrewrottier/Documents/Pictures/");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		}
	
	}
	
	public String getOS()
	{
		 String OS = System.getProperty("os.name").toLowerCase();
	 
		if (isWindows(OS)) {
			return "win";
		} else if (isMac(OS)) {
			return "mac";
		} else {
			return "error - Your OS is not support!!";
		}
		
	}
	 
	public static boolean isWindows(String OS)
	{
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac(String OS) 
	{
		return (OS.indexOf("mac") >= 0);
	}
}

