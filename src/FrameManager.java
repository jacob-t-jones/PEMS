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
	
	/* pushPanel - removes the current panel and replaces it with the newly opened panel
	 *   panel - new panel to display
	 *   title - title of the new panel
	 */
	public void pushPanel(JPanel panel, String title)
	{
		this.mainFrame.getContentPane().removeAll();
		this.mainFrame.getContentPane().add(panel);
		this.mainFrame.getContentPane().revalidate();
		this.mainFrame.getContentPane().repaint();
		this.mainFrame.setTitle(title);
	}
	
	
}
