// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// StartPanel.java

package gui.display.start;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import org.apache.sanselan.*;
import org.imgscalr.*;
import backend.exceptions.*;
import gui.*;
import gui.components.icon.*;
import gui.display.*;
import gui.display.editcase.*;
import gui.display.newcase.*;

/** Subclass of <code>JPanel</code> displayed when the user first starts the program.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class StartPanel extends JPanel implements ActionListener
{

	private FrameManager manager;
	private Box topContainer;
	private Box middleContainer;
	private Box bottomContainer;
	private BufferedImage bgImage;
	private ImgIcon logoIcon;
	private JLabel titleLabel;
	private JLabel nameLabel;
	private JLabel creditLabel;
	private JButton newCaseButton;
	private JButton editCaseButton;
	private JButton settingsButton;
	
	/** Populates this panel with all of the necessary graphical components.
	 * 
	 *  @param manager the instance of <code>FrameManager</code> that initialized this panel
	 *  @throws NullPointerException if the parameter is null
	 */
	public StartPanel(FrameManager manager)
	{
		if (manager == null)
		{
			throw new NullPointerException();
		}
		this.manager = manager;
		this.topContainer = Box.createVerticalBox();
		this.middleContainer = Box.createHorizontalBox();
		this.bottomContainer = Box.createVerticalBox();
		this.populateTopContainer();
		this.populateMiddleContainer();
		this.populateBottomContainer();
		this.add(this.topContainer);
		this.add(this.middleContainer);
		this.add(this.bottomContainer);
		this.manager.getMainWindow().setBounds(640, 400);
	}
	
	/** Mandatory method required in all classes that implement <code>ActionListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li><code>newCaseButton</code></li>
	 *  		<ul>
	 *  			<li><code>NewCasePanel</code> is pushed into view.</li>
	 *  		</ul>
	 *  	<li><code>editCaseButton</code></li>
	 *  		<ul>
	 *  			<li><code>EditCasePanel</code> is pushed into view.</li>
	 *  		</ul>
	 *  	<li><code>settingsButton</code></li>
	 *  		<ul>
	 *  			<li>Nothing happens (for now).</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.newCaseButton)
		{
			this.manager.getMainWindow().pushPanel(new NewCasePanel(this.manager), "PEMS - Create New Case");
		}
		else if (e.getSource() == this.editCaseButton)
		{
			this.manager.getMainWindow().pushPanel(new EditCasePanel(this.manager), "PEMS - Edit Existing Case");
		}
		else if (e.getSource() == this.settingsButton)
		{
			return;
		}
	}
	
	/** Override method used, in this case, to set a background image.
	 */
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		try 
		{
			this.bgImage = Sanselan.getBufferedImage(new File("resources/background.png"));
		} 
		catch (ImageReadException | IOException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			return;
		}
		g.drawImage(this.bgImage, 0, 0, null);
	}
	
	/** Adds <code>titleLabel</code>, <code>nameLabel</code>, and <code>logoIcon</code> to <code>topContainer</code>.
	 */
	private void populateTopContainer()
	{
		this.titleLabel = ComponentGenerator.generateLabel("Police Evidence Management System", ComponentGenerator.TITLE_FONT, ComponentGenerator.TITLE_COLOR, CENTER_ALIGNMENT);
		this.nameLabel = ComponentGenerator.generateLabel(this.manager.getConfiguration().getDepartmentName(), ComponentGenerator.SUBTITLE_FONT, ComponentGenerator.SUBTITLE_COLOR, CENTER_ALIGNMENT);
		try 
		{
			this.logoIcon = new ImgIcon("resources/logo.png", Scalr.Method.ULTRA_QUALITY, 200, 200);
			this.logoIcon.setAlignmentX(CENTER_ALIGNMENT);
			this.topContainer.add(this.logoIcon);
		} 
		catch (InvalidFileException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.topContainer.add(Box.createVerticalStrut(15));
		this.topContainer.add(this.titleLabel);
		this.topContainer.add(Box.createVerticalStrut(10));
		this.topContainer.add(this.nameLabel);
		this.topContainer.add(Box.createVerticalStrut(25));
	}
	
	/** Adds <code>newCaseButton</code>, <code>editCaseButton</code>, and <code>settingsButton</code> to <code>middleContainer</code>.
	 */
	private void populateMiddleContainer()
	{
		this.newCaseButton = ComponentGenerator.generateButton("New Case", this);
		this.editCaseButton = ComponentGenerator.generateButton("Edit Case", this);
		this.settingsButton = ComponentGenerator.generateButton("Settings", this);
		this.middleContainer.add(this.newCaseButton);
		this.middleContainer.add(Box.createHorizontalStrut(120));	
		this.middleContainer.add(this.editCaseButton);
		this.middleContainer.add(Box.createHorizontalStrut(120));
		this.middleContainer.add(this.settingsButton);
	}
	
	/** Adds <code>creditLabel</code> to <code>bottomContainer</code>.
	 */
	private void populateBottomContainer()
	{
		this.creditLabel = ComponentGenerator.generateLabel("Copyright 2015 \u00a9 Jacob Jones and Andrew Rottier", ComponentGenerator.MINI_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.bottomContainer.add(Box.createVerticalStrut(5));
		this.bottomContainer.add(this.creditLabel);
	}
	
}