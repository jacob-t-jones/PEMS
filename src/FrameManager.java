import java.awt.*;
import javax.swing.*;

public class FrameManager 
{
	
	private JFrame mainFrame;
	
	public FrameManager()
	{
        this.mainFrame = new JFrame("PEMS (Police Evidence Management System) 0.1");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.pack();
        this.mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.mainFrame.setVisible(true);
	}
	
	public void pushPanel(JPanel panel)
	{
		this.mainFrame.getContentPane().removeAll();
		this.mainFrame.getContentPane().add(panel);
		this.mainFrame.getContentPane().revalidate();
		this.mainFrame.getContentPane().repaint();
	}
	
	public void setTitle(String title)
	{
		this.mainFrame.setTitle(title);
	}
	
}
