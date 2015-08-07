package gui.display.editimg;

import exceptions.InvalidImgException;
import gui.ComponentGenerator;
import gui.components.img.ThumbnailImg;
import gui.display.FrameManager;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectImagesDialogue extends JPanel implements ActionListener, MouseListener
{
	
	private FrameManager manager;
	private JPanel currentPanel;
	private ArrayList<ThumbnailImg> displayedThumbnails;
	private ArrayList<ThumbnailImg> selectedThumbnails;
	private Box container;
	private Box buttonsContainer;
	private Box thumbnailContainer;
	private JLabel titleLabel;
	private JButton nextButton;
	private JButton prevButton;
	private JButton continueButton;
	private String caseNum;
	private int thumbnailPlace;
	
	public SelectImagesDialogue(FrameManager manager, JPanel currentPanel, String caseNum)
	{
		this.manager = manager;
		this.currentPanel = currentPanel;
		this.caseNum = caseNum;
		this.thumbnailPlace = 0;
		this.displayedThumbnails = this.generateThumbnails();
		this.selectedThumbnails = new ArrayList<ThumbnailImg>();
		this.container = Box.createVerticalBox();
		this.buttonsContainer = Box.createHorizontalBox();
		this.thumbnailContainer = Box.createVerticalBox();
		this.populateButtonsContainer();
		this.refreshThumbnailContainer(0);
		this.populateContainer();
		this.add(this.container);
		this.manager.getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.revalidate();
		this.repaint();
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.nextButton)
		{
			if (this.thumbnailPlace + 6 < this.displayedThumbnails.size())
			{
				this.refreshThumbnailContainer(this.thumbnailPlace + 6);
			}
		}
		else if (e.getSource() == this.prevButton)
		{
			if (this.thumbnailPlace >= 6)
			{
				this.refreshThumbnailContainer(this.thumbnailPlace - 6);
			}
		}
	}

	public void mouseClicked(MouseEvent e) 
	{
		if (this.selectedThumbnails.contains(e.getSource()))
		{
			this.selectedThumbnails.remove((ThumbnailImg)e.getSource());
		}
		else if (this.displayedThumbnails.contains(e.getSource()))
		{
			this.selectedThumbnails.add((ThumbnailImg)e.getSource());
		}
		this.refreshThumbnailContainer(this.thumbnailPlace);
	}

	public void mousePressed(MouseEvent e) 
	{
		
	}

	public void mouseReleased(MouseEvent e) 
	{

	}

	public void mouseEntered(MouseEvent e) 
	{
		
	}

	public void mouseExited(MouseEvent e)
	{
		
	}
	
	private ArrayList<ThumbnailImg> generateThumbnails()
	{
		ArrayList<ThumbnailImg> thumbnailList = new ArrayList<ThumbnailImg>();
	    File directory = new File("cases" + "/" + this.caseNum + "/");
		for (int i = 0; i < directory.listFiles().length; i++)
		{
			String currentName = directory.listFiles()[i].getName();
			String currentExtension = currentName.substring(currentName.indexOf('.')).toLowerCase();
			if ((currentExtension.equalsIgnoreCase(".png") || currentExtension.equalsIgnoreCase(".jpg") || currentExtension.equalsIgnoreCase(".jpeg")))
			{ 
				try 
				{
					ThumbnailImg currentThumbnail = ComponentGenerator.generateThumbnailImg(directory.listFiles()[i].getPath(), 140, CENTER_ALIGNMENT);
				    currentThumbnail.addMouseListener(this);
				    thumbnailList.add(currentThumbnail);
				} 
				catch (InvalidImgException e) 
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	    return thumbnailList;
	}
	
	private void refreshThumbnailContainer(int thumbnailPlace)
	{
		this.thumbnailPlace = thumbnailPlace;
		this.thumbnailContainer.removeAll();
		this.thumbnailContainer.add(Box.createVerticalStrut(5));;
		for (int i = 0; i < 2; i++)
		{
			Box row = Box.createHorizontalBox();
			for (int j = 0; j < 3; j++)
			{
				Box col = Box.createHorizontalBox();
				col.setMinimumSize(new Dimension(150, 150));
				col.setMaximumSize(new Dimension(150, 150));
				if (this.thumbnailPlace < this.displayedThumbnails.size())
				{
					col.add(Box.createHorizontalGlue());
					col.add(Box.createVerticalStrut(150));
					col.add(this.displayedThumbnails.get(this.thumbnailPlace));
					col.add(Box.createVerticalStrut(150));
					col.add(Box.createHorizontalGlue());
					if (this.selectedThumbnails.contains(this.displayedThumbnails.get(this.thumbnailPlace)))
					{
						col.setBackground(ComponentGenerator.SELECTED_THUMBNAIL_COLOR);
						col.setOpaque(true);
					}
				}
				else
				{
					col.add(Box.createHorizontalGlue());
					col.add(Box.createVerticalStrut(150));
					col.add(Box.createHorizontalGlue());
				}
				row.add(col);
				this.thumbnailPlace++;
			}
			this.thumbnailContainer.add(row);
		}
		this.thumbnailContainer.add(Box.createHorizontalStrut(450));
		this.thumbnailPlace = thumbnailPlace;
		this.revalidate();
		this.repaint();
	}
	
	private void populateButtonsContainer()
	{
		this.nextButton = ComponentGenerator.generateButton("Next", this, CENTER_ALIGNMENT);
		this.prevButton = ComponentGenerator.generateButton("Prev", this, CENTER_ALIGNMENT);
		this.buttonsContainer.add(this.prevButton);
		this.buttonsContainer.add(Box.createHorizontalStrut(100));
		this.buttonsContainer.add(this.nextButton);
	}
	
	private void populateContainer()
	{
		this.titleLabel = ComponentGenerator.generateLabel("Please select all of the images you would like to print:", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.container.add(Box.createVerticalStrut(10));
		this.container.add(this.titleLabel);
		this.container.add(Box.createVerticalStrut(10));
		this.container.add(this.buttonsContainer);
		this.container.add(Box.createVerticalStrut(15));
		this.container.add(this.thumbnailContainer);
	}

}