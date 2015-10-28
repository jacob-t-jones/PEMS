// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// SelectPanel.java

package gui.display.select;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.imgscalr.*;
import backend.exceptions.*;
import backend.storage.Case.*;
import gui.*;
import gui.components.icon.*;
import gui.display.*;
import gui.display.editimg.*;

/** Subclass of <code>JPanel</code> displayed when the user is importing external evidence files into a case.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class SelectPanel extends JPanel implements ActionListener, MouseListener
{

	private FrameManager manager;
	private ArrayList<PeripheralIcon> detectedIcons;
	private ArrayList<PeripheralIcon> selectedIcons;
	private Box mainContainer;
	private Box innerContainer;
	private Box leftContainer;
	private Box rightContainer;
	private Box detectedContainer;
	private Box detectedHeaderContainer;
	private Box selectedContainer;
	private Box buttonContainer;
	private ImgIcon refreshIcon;
	private JLabel instructionsLabel;
	private JLabel detectedTitleLabel;
	private JLabel selectedTitleLabel;
	private JButton loadNextDetectedButton;
	private JButton loadPrevDetectedButton;
	private JButton loadNextSelectedButton;
	private JButton loadPrevSelectedButton;
	private JButton finishButton;
	private String caseNum;
	private int detectedIconPlace;
	private int selectedIconPlace;

	/** Populates this panel with all of the necessary graphical components.
	 * 
	 *  @param manager the instance of <code>FrameManager</code> that initialized this panel
	 *  @param caseNum the number of the case currently being edited
	 *  @throws NullPointerException if any parameters are null
	 */
	public SelectPanel(FrameManager manager, String caseNum) 
	{
		if (manager == null || caseNum == null)
		{
			throw new NullPointerException();
		}
		this.manager = manager;
		this.caseNum = caseNum;
		this.detectedIconPlace = 0;
		this.selectedIconPlace = 0;
		this.detectedIcons = this.generateIcons();
		this.selectedIcons = new ArrayList<PeripheralIcon>();
		this.mainContainer = Box.createVerticalBox();
		this.innerContainer = Box.createHorizontalBox();
		this.leftContainer = Box.createVerticalBox();
		this.rightContainer = Box.createVerticalBox();
		this.detectedContainer = Box.createVerticalBox();
		this.detectedHeaderContainer = Box.createHorizontalBox();
		this.selectedContainer = Box.createVerticalBox();
		this.buttonContainer = Box.createHorizontalBox();
		this.detectedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.selectedContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		this.populateDetectedHeaderContainer();
		this.refreshDetectedIcons(0);
		this.refreshSelectedIcons(0);
		this.populateButtonContainer();
		this.populateLeftContainer();
		this.populateRightContainer();
		this.populateInnerContainer();
		this.populateMainContainer();
		this.add(this.mainContainer);
		this.manager.getMainWindow().setMaximized();
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.revalidate();
		this.repaint();
	}
	
	/** Mandatory method required in all classes that implement <code>ActionListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li><code>loadNextDetectedButton</code></li>
	 *  		<ul>
	 *  			<li>Attempts to load the next fifteen images detected on peripheral devices within <code>detectedContainer</code>.</li>
	 *  		</ul>
	 *  	<li><code>loadPrevDetectedButton</code></li>
	 *  		<ul>
	 *  			<li>Attempts to load the previous fifteen images detected on peripheral devices within <code>detectedContainer</code>.</li>
	 *  		</ul>
	 *  	<li><code>finishButton</code></li>
	 *  		<ul>
	 *  			<li>Displays a dialogue asking the user for his or her import preferences.</li>
	 *  		</ul>
	 *  	<li><code>loadNextSelectedButton</code></li>
	 *  		<ul>
	 *  			<li>Attempts to load the next three user-selected images within <code>selectedContainer</code>.</li>
	 *  		</ul>
	 *  	<li><code>loadPrevSelectedButton</code></li>
	 *  		<ul>
	 *  			<li>Attempts to load the previous three user-selected images within <code>selectedContainer</code>.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.loadNextDetectedButton)
		{
        	if (this.detectedIconPlace + 15 < this.detectedIcons.size())
        	{
        		this.refreshDetectedIcons(this.detectedIconPlace + 15);
        	}
		}
		else if (e.getSource() == this.loadPrevDetectedButton)
		{
          	if (this.detectedIconPlace >= 15)
        	{
        		this.refreshDetectedIcons(this.detectedIconPlace - 15);
        	}
		}
		else if (e.getSource() == this.finishButton)
		{	
			if (this.selectedIcons.isEmpty())
			{
				if (this.detectedIcons.isEmpty())
				{
					JOptionPane.showMessageDialog(this.manager.getMainWindow(), "Unable to locate any images on external drives. Please disconnect and reconnect all cameras and devices, restart the program, and try again.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(this.manager.getMainWindow(), "You must select at least one image to import into the newly created case!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				int selection = JOptionPane.showConfirmDialog(this.manager.getMainWindow(), "Would you like to delete the original copies of all imported files from the camera?", "Delete Selected Files", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (selection == JOptionPane.YES_OPTION)
				{
					this.attemptCopying(true);
				}
				else if (selection == JOptionPane.NO_OPTION)
				{
					this.attemptCopying(false);
				}
				else if (selection == JOptionPane.CANCEL_OPTION)
				{
					return;
				}
			}
		}
		else if (e.getSource() == this.loadNextSelectedButton)
		{
        	if (this.selectedIconPlace + 3 < this.selectedIcons.size())
        	{
        		this.refreshSelectedIcons(this.selectedIconPlace + 3);
        	}
		}
		else if (e.getSource() == this.loadPrevSelectedButton)
		{
        	if (this.selectedIconPlace >= 3)
        	{
        		this.refreshSelectedIcons(this.selectedIconPlace - 3);
        	}
		}
	}
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li><code>refreshIcon</code></li>
	 *  		<ul>
	 *  			<li>Refreshes the icons in <code>detectedContainer</code> by once again scanning peripheral devices for images files.</li>
	 *  		</ul>
	 *  	<li>An instance of <code>PeripheralIcon</code> contained within the <code>selectedIcons</code> <code>ArrayList</code></li>
	 *  		<ul>
	 *  			<li>Removes the source <code>PeripheralIcon</code> from <code>selectedIcons</code> and adds it to <code>detectedIcons</code>.</li>
	 *  		</ul>
	 *  	<li>An instance of <code>PeripheralIcon</code> contained within the <code>detectedIcons</code> <code>ArrayList</code></li>
	 *  		<ul>
	 *  			<li>Removes the source <code>PeripheralIcon</code> from <code>detectedIcons</code> and adds it to <code>selectedIcons</code>.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if (e.getSource() == this.refreshIcon)
		{
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			this.manager.getPeripheralManager().refreshDevices();
			this.detectedIcons = this.generateIcons();
			for (int i = 0; i < this.selectedIcons.size(); i++)
			{
				PeripheralIcon currentSelectedIcon = this.selectedIcons.get(i);
				for (int j = 0; j < this.detectedIcons.size(); j++)
				{
					PeripheralIcon currentDetectedIcon = this.detectedIcons.get(j);
					if (currentSelectedIcon.getParentFile().getFilePath().equalsIgnoreCase(currentDetectedIcon.getParentFile().getFilePath()))
					{
						this.detectedIcons.remove(j);
					}
				}
			}
			this.refreshDetectedIcons(0);
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		else if (this.selectedIcons.contains(e.getSource()))
		{
			this.detectedIcons.add((PeripheralIcon)e.getSource());
			this.selectedIcons.remove(e.getSource());
			this.refreshDetectedIcons(this.detectedIconPlace);
			this.refreshSelectedIcons(this.selectedIconPlace);
		}
		else if (this.detectedIcons.contains(e.getSource()))
		{
			this.selectedIcons.add((PeripheralIcon)e.getSource());
			this.detectedIcons.remove(e.getSource());
			this.refreshDetectedIcons(this.detectedIconPlace);
			this.refreshSelectedIcons(this.selectedIconPlace);
		}
	}
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mousePressed(MouseEvent e) 
	{
		return;
	}
	 
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseReleased(MouseEvent e)
	{
		return;
	}
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseEntered(MouseEvent e) 
	{
		return;
	}
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseExited(MouseEvent e) 
	{
		return;
	}	
	
	/** Refreshes the <code>PeripheralIcon</code> objects for images not yet selected by the user.
	 * 
	 *  @param detectedIconPlace the index within <code>detectedIcons</code> of the first image to be displayed
	 */
	private void refreshDetectedIcons(int detectedIconPlace)
	{
		this.detectedIconPlace = detectedIconPlace;
		this.detectedContainer.removeAll();
		this.detectedContainer.add(Box.createVerticalStrut(5));
		this.detectedContainer.add(this.detectedHeaderContainer);
		for (int i = 0; i < 3; i++)
		{
			Box row = Box.createHorizontalBox();
			for (int j = 0; j < 5; j++)
			{
				Box col = Box.createHorizontalBox();
				col.setMinimumSize(new Dimension(150, 150));
				col.setMaximumSize(new Dimension(150, 150));
				if (this.detectedIconPlace < this.detectedIcons.size())
				{
					col.add(Box.createHorizontalGlue());
					col.add(Box.createVerticalStrut(150));
					col.add(this.detectedIcons.get(this.detectedIconPlace));
					col.add(Box.createVerticalStrut(150));
					col.add(Box.createHorizontalGlue());
				}
				else
				{
					col.add(Box.createHorizontalGlue());
					col.add(Box.createVerticalStrut(150));
					col.add(Box.createHorizontalGlue());
				}
				row.add(col);
				this.detectedIconPlace++;
			}
			this.detectedContainer.add(row);
		}
		this.detectedContainer.add(Box.createHorizontalStrut(750));
		this.detectedIconPlace = detectedIconPlace;
		this.revalidate();
		this.repaint();
	}
	
	/** Refreshes the <code>PeripheralIcon</code> objects placed in <code>selectedContainer</code> by the user.
	 * 
	 *  @param selectedIconPlace the index within <code>selectedIcons</code> of the first image to be displayed
	 */
	private void refreshSelectedIcons(int selectedIconPlace)
	{
		this.selectedTitleLabel = ComponentGenerator.generateLabel("Selected Images", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
	    this.loadNextSelectedButton = ComponentGenerator.generateButton("Next", this, CENTER_ALIGNMENT);
		this.loadPrevSelectedButton = ComponentGenerator.generateButton("Previous", this, CENTER_ALIGNMENT);
		this.selectedIconPlace = selectedIconPlace;
		this.selectedContainer.removeAll();
		this.selectedContainer.add(this.selectedTitleLabel);
		this.selectedContainer.add(this.loadPrevSelectedButton);
		for (int i = 0; i < 3; i++)
		{
			Box row = Box.createHorizontalBox();
			row.setMinimumSize(new Dimension(150, 150));
			row.setMaximumSize(new Dimension(150, 150));
			if (this.selectedIconPlace < this.selectedIcons.size())
			{
				row.add(Box.createHorizontalGlue());
				row.add(Box.createVerticalStrut(150));
				row.add(this.selectedIcons.get(this.selectedIconPlace));
				row.add(Box.createVerticalStrut(150));
				row.add(Box.createHorizontalGlue());
			}
			else
			{
				row.add(Box.createHorizontalGlue());
				row.add(Box.createVerticalStrut(150));
				row.add(Box.createHorizontalGlue());
			}
			this.selectedContainer.add(row);
			this.selectedIconPlace++;
		}
		this.selectedContainer.add(this.loadNextSelectedButton);
		this.selectedContainer.add(Box.createHorizontalStrut(150));
		this.selectedIconPlace = selectedIconPlace;
		this.revalidate();
		this.repaint();
	}
	
	/** Adds <code>detectedTitleLabel</code> and <code>refreshIcon</code> to <code>detectedHeaderContainer</code>.
	 */
	private void populateDetectedHeaderContainer()
	{
		this.detectedTitleLabel = ComponentGenerator.generateLabel("Images Detected on External Devices", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		try 
		{
			this.refreshIcon = new ImgIcon("resources/refresh.png", Scalr.Method.ULTRA_QUALITY, 30);
			this.refreshIcon.addMouseListener(this);
			this.detectedHeaderContainer.add(Box.createHorizontalStrut(5));
			this.detectedHeaderContainer.add(this.refreshIcon);
		} 
		catch (InvalidFileException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.detectedHeaderContainer.add(Box.createHorizontalGlue());
		this.detectedHeaderContainer.add(this.detectedTitleLabel);
		this.detectedHeaderContainer.add(Box.createHorizontalGlue());
	}
	
	/** Adds <code>loadNextDetectedButton</code>, <code>loadPrevDetectedButton</code>, and <code>finishButton</code> to <code>buttonContainer</code>.
	 */
	private void populateButtonContainer()
	{
		this.loadNextDetectedButton = ComponentGenerator.generateButton("Load Next Images   >", this);
		this.loadPrevDetectedButton = ComponentGenerator.generateButton("<   Load Previous Images", this);
		this.finishButton = ComponentGenerator.generateButton("Finish Importing", this);
		this.buttonContainer = Box.createHorizontalBox();
		this.buttonContainer.setAlignmentX(CENTER_ALIGNMENT);
		this.buttonContainer.add(this.loadPrevDetectedButton);
		this.buttonContainer.add(Box.createHorizontalStrut(100));
		this.buttonContainer.add(this.finishButton);
		this.buttonContainer.add(Box.createHorizontalStrut(100));
		this.buttonContainer.add(this.loadNextDetectedButton);
	}
	
	/** Adds <code>detectedContainer</code> and <code>buttonContainer</code> to <code>leftContainer</code>.
	 */
	private void populateLeftContainer()
	{
		this.leftContainer.add(this.detectedContainer);
		this.leftContainer.add(Box.createVerticalStrut(30));
		this.leftContainer.add(this.buttonContainer);
	}
	
	/** Adds <code>selectedContainer</code> to <code>rightContainer</code>.
	 */
	private void populateRightContainer()
	{
		this.rightContainer.add(this.selectedContainer);
	}
	
	/** Adds <code>leftContainer</code> and <code>rightContainer</code> to <code>innerContainer</code>.
	 */
	private void populateInnerContainer()
	{
		this.innerContainer.add(this.leftContainer);
		this.innerContainer.add(Box.createHorizontalStrut(100));
		this.innerContainer.add(this.rightContainer);
	}
	
	/** Adds <code>instructionsLabel</code> and <code>innerContainer</code> to <code>mainContainer</code>.
	 */
	private void populateMainContainer()
	{
		this.instructionsLabel = ComponentGenerator.generateLabel("Click on any of the images below to import them into the current case. Selected images will appear on the right, and can be removed from the case by simply clicking on them again.", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.mainContainer.add(Box.createVerticalStrut(20));
		this.mainContainer.add(this.instructionsLabel);
		this.mainContainer.add(Box.createVerticalStrut(30));
		this.mainContainer.add(this.innerContainer);
	}
	
	/** Returns an <code>ArrayList</code> of <code>PeripheralIcon</code> objects generated using the raw evidence files detected by the global instance of <code>PeripheralManager</code>.
	 * 
	 *  @return <code>ArrayList</code> of <code>PeripheralIcon</code> objects generated using the raw evidence files detected by the global instance of <code>PeripheralManager</code>
	 */
	private ArrayList<PeripheralIcon> generateIcons()
	{
		ArrayList<PeripheralIcon> icons = new ArrayList<PeripheralIcon>();
		for (int i = 0; i < this.manager.getPeripheralManager().getDevices().size(); i++)
		{
			for (int j = 0; j < this.manager.getPeripheralManager().getDevices().get(i).getFiles().size(); j++)
			{
				try 
				{
					PeripheralIcon newIcon = new PeripheralIcon(this.manager.getPeripheralManager().getDevices().get(i).getFiles().get(j), 120);
					newIcon.addMouseListener(this);
					icons.add(newIcon);
				} 
				catch (InvalidFileException e) 
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return icons;
	}
	
	/** Attempts to copy the selected image files into the local file system, where they can be managed by PEMS.
	 * 
	 *  @param delete <code>boolean</code> value indicating whether or not the original copies of the selected files should be deleted as the program proceeds
	 */
	private void attemptCopying(boolean delete)
	{
		AddFileResult result = this.manager.getStorageManager().addFiles(delete, this.caseNum, this.selectedIcons);
		if (result == AddFileResult.ADD_FAILED)
		{
			JOptionPane.showMessageDialog(this.manager.getMainWindow(), "Importation of the selected image files has unexpectedly failed!\nPlease disconnect and reconnect all cameras and devices, restart the program, and try again.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			this.manager.getMainWindow().pushPanel(new EditImgPanel(this.manager, this.caseNum), "PEMS - Edit Photos");
		}
	}

}