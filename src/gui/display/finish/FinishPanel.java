package gui.display.finish;

import gui.ComponentGenerator;
import gui.display.FrameManager;
import gui.display.print.PrintPanel;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class FinishPanel extends JPanel implements ActionListener
{
	
	private FrameManager manager;
	private String caseNum;
	private Box container;
	private Box optionsBox;
	private JButton openInDirButton;
	private JButton printButton;
	private JButton closeButton;
	
	public FinishPanel(FrameManager manager, String caseNum) 
	{
		this.manager = manager;
		this.caseNum = caseNum;
		this.optionsBox = Box.createHorizontalBox();
		this.container = Box.createVerticalBox();
		this.populateBox();
		this.revalidate();
		this.repaint();
		this.manager.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
	}
	
	private void populateBox()
	{
		this.container.add(Box.createVerticalStrut(250));
		this.openInDirButton = ComponentGenerator.generateButton("Open in Directory", this, CENTER_ALIGNMENT);
		this.printButton = ComponentGenerator.generateButton("Print Image", this, CENTER_ALIGNMENT);
		this.closeButton = ComponentGenerator.generateButton("Exit", this, CENTER_ALIGNMENT);
		
		this.optionsBox.add(openInDirButton);
		this.optionsBox.add(printButton);
		this.container.add(Box.createVerticalStrut(50));
		this.container.add(optionsBox);
		this.container.add(closeButton);
		
		this.container.add(Box.createHorizontalStrut(50));
		
		this.add(container);

		this.revalidate();
		this.repaint();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == this.closeButton)
		{
			this.manager.closePanel();
			Runtime.getRuntime().exit(0);
		}
		
		if (e.getSource() == this.printButton)
		{
			this.manager.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			manager.pushPanel(new PrintPanel(manager, this.caseNum), "PEMS - Print Images");
		}
		
		if (e.getSource() == this.openInDirButton)
		{
			String OSname = getOS();
			if(OSname == "win")
			{
				//..CHANGE TO OPENING IN WINDOWS..
				try {
					File file = new File ("c:/users/cops/cases");
					Desktop desktop = Desktop.getDesktop();
					desktop.open(file);
					//or try: (with correct path)
					//Runtime.getRuntime().exec("open /Users/andrewrottier/Documents/Pictures/");
				} catch (IOException e1) {
					System.out.println("error - could not locate the directory");
					e1.printStackTrace();
				}
			} else if(OSname == "mac")
			{
				try {
					Runtime.getRuntime().exec("open /Users/andrewrottier/Documents/Pictures/");
				} catch (IOException e1) {
					System.out.println("error - could not locate the directory");
					e1.printStackTrace();
				}
				
			}
		}
	
	}
	
	public String getOS()
	{
		 String OS = System.getProperty("os.name").toLowerCase();
	 
		if (isWindows(OS)) 
		{
			return "win";
		} 
		else if (isMac(OS)) 
		{
			return "mac";
		} 
		else 
		{
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

