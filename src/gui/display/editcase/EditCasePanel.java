// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// EditCasePanel.java

package gui.display.editcase;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import gui.*;
import gui.components.*;
import gui.display.*;
import gui.display.dialogues.*;
import gui.display.start.*;

/** Subclass of <code>JPanel</code> displayed when the user wants to edit a case that has already been created.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class EditCasePanel extends JPanel implements ActionListener, MouseListener
{

	private FrameManager manager;
	private Box container;
	private Box topContainer;
	private Box caseContainer;
	private Box bottomContainer;
	private JScrollPane scroller;
	private JLabel instructionsLabel;
	private JButton backButton;

	/** Populates this panel with all of the necessary graphical components.
	 * 
	 *  @param manager the instance of <code>FrameManager</code> that initialized this panel
	 *  @throws NullPointerException if the parameter is null
	 */
	public EditCasePanel(FrameManager manager) 
	{
		if (manager == null)
		{
			throw new NullPointerException();
		}
		this.manager = manager;
		this.container = Box.createVerticalBox();
		this.topContainer = Box.createHorizontalBox();
		this.caseContainer = Box.createVerticalBox();
		this.bottomContainer = Box.createHorizontalBox();
		this.populateTopContainer();
		this.populateCaseContainer();
		this.populateBottomContainer();
		this.populateContainer();
		this.add(this.container);
		this.revalidate();
		this.repaint();
	}
	
	/** Mandatory method required in all classes that implement <code>ActionListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li><code>backButton</code></li>
	 *  		<ul>
	 *  			<li><code>StartPanel</code> is pushed into view.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.backButton)
		{
			this.manager.getMainWindow().pushPanel(new StartPanel(this.manager), "PEMS (Police Evidence Management System) Version 0.1");
		}
	}
	
	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 *  <p>
	 *  <b>Below is a list of possible source objects and their corresponding actions:</b>
	 *  <ul>
	 *  	<li>An instance of <code>CaseBox</code></li>
	 *  		<ul>
	 *  			<li>Opens an instance of <code>SelectImagesEditCaseDialogue</code>, allowing users to add additional images to the selected case.</li>
	 *  		</ul>
	 *  </ul>
	 */
	public void mouseClicked(MouseEvent e) 
	{
		if (e.getSource() instanceof CaseBox)
		{
			CaseBox selectedBox = (CaseBox)e.getSource();
			this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			this.manager.openDialogue("Add Images to Case", new SelectImagesEditCaseDialogue(this.manager, selectedBox.getCaseNum()), 40, 55);
		}
	}

	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mousePressed(MouseEvent e) 
	{

	}

	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseReleased(MouseEvent e) 
	{

	}

	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseEntered(MouseEvent e) 
	{

	}

	/** Mandatory method required in all classes that implement <code>MouseListener</code>.
	 */
	public void mouseExited(MouseEvent e) 
	{
		
	}
	
	/** Adds <code>instructionsLabel</code> to <code>topContainer</code>.
	 */
	private void populateTopContainer()
	{
		this.instructionsLabel = ComponentGenerator.generateLabel("Please select the case you wish to edit:", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.topContainer.add(this.instructionsLabel);
	}
	
	/** Populates <code>caseContainer</code> with <code>CaseBox</code> objects representing all of the cases actively being managed by PEMS. Then places <code>caseContainer</code> into an instance of <code>JScrollPane</code>.
	 */
	private void populateCaseContainer()
	{
		int index = 0;
		for (int i = 0; i < (this.manager.getStorageManager().getCases().size() / 3) + 1; i++)
		{
			Box row = Box.createHorizontalBox();
			for (int j = 0; j < 3; j++)
			{
				if (index < this.manager.getStorageManager().getCases().size())
				{
					CaseBox currentCaseBox = new CaseBox(this.manager.getStorageManager().getCases().get(index).getCaseNum());
					currentCaseBox.addMouseListener(this);
					row.add(currentCaseBox);
				}
				else
				{
					row.add(Box.createHorizontalStrut(130));
				}
				row.add(Box.createHorizontalStrut(30));
				index++;
			}
			this.caseContainer.add(row);
			this.caseContainer.add(Box.createVerticalStrut(10));
		}
		this.scroller = new JScrollPane(this.caseContainer);
		this.scroller.setPreferredSize(new Dimension(this.manager.getMainWindow().getWidth() - 100, this.manager.getMainWindow().getHeight() - 150));
		this.scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.scroller.setBackground(new Color(245, 245, 245));
		this.scroller.getViewport().setBackground(new Color(245, 245, 245));
	}
	
	/** Adds <code>backButton</code> to <code>bottomContainer</code>.
	 */
	private void populateBottomContainer()
	{
		this.backButton = ComponentGenerator.generateButton("<      Back", this);
		this.bottomContainer.add(this.backButton);
		this.bottomContainer.add(Box.createHorizontalGlue());
	}
	
	/** Adds <code>topContainer</code>, <code>scroller</code>, and <code>bottomContainer</code> to <code>container</code>.
	 */
	private void populateContainer()
	{
		this.container.add(Box.createVerticalStrut(10));
		this.container.add(this.topContainer);
		this.container.add(Box.createVerticalStrut(20));
		this.container.add(this.scroller, BorderLayout.CENTER);
		this.container.add(Box.createVerticalStrut(20));
		this.container.add(this.bottomContainer);
	}
	
}