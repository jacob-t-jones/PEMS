package gui.display.dialogues;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.apache.pdfbox.pdmodel.*;
import exceptions.*;
import gui.*;
import gui.components.img.*;
import gui.display.*;

public class PrintSetUpDialogue extends JPanel implements ActionListener
{
	
	private FrameManager manager;
	private ArrayList<ThumbnailImg> selectedThumbnails;
	private ArrayList<PDPage> pages;
	private PDDocument document;
	private Img oneImgDiagram;
	private Img twoImgDiagram;
	private Img fourImgDiagram;
	private Img eightImgDiagram;
	private Box container;
	private Box displayContainer;
	private Box buttonsContainer;
	private JLabel instructionsLabel;
	private JLabel layoutLabel;
	private JButton oneImgButton;
	private JButton twoImgButton;
	private JButton fourImgButton;
	private JButton eightImgButton;
	private JButton printButton;
	private int imgsPerPage;
	
	public PrintSetUpDialogue(FrameManager manager, ArrayList<ThumbnailImg> selectedThumbnails) 
	{
		this.manager = manager;
		this.selectedThumbnails = selectedThumbnails;
		this.imgsPerPage = 0;
		this.container = Box.createVerticalBox();
		this.displayContainer = Box.createHorizontalBox();
		this.buttonsContainer = Box.createVerticalBox();
		this.generateLayoutDiagrams();
		this.populateButtonsContainer();
		this.populateDisplayContainer(1);
		this.populateContainer();
		this.add(this.container);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.oneImgButton)
		{
			this.populateDisplayContainer(1);
			this.imgsPerPage = 1;
		}
		else if (e.getSource() == this.twoImgButton)
		{
			this.populateDisplayContainer(2);
			this.imgsPerPage = 2;
		}
		else if (e.getSource() == this.fourImgButton)
		{
			this.populateDisplayContainer(4);
			this.imgsPerPage = 4;
		}
		else if (e.getSource() == this.eightImgButton)
		{
			this.populateDisplayContainer(8);
			this.imgsPerPage = 8;
		}
		else if (e.getSource() == this.printButton)
		{
			this.generatePDF();
		}
	}
	
	private void generateLayoutDiagrams()
	{
		try
		{
			this.oneImgDiagram = ComponentGenerator.generateImg("resources/layout1.png", CENTER_ALIGNMENT);
			this.twoImgDiagram = ComponentGenerator.generateImg("resources/layout2.png", CENTER_ALIGNMENT);
			this.fourImgDiagram = ComponentGenerator.generateImg("resources/layout4.png", CENTER_ALIGNMENT);
			this.eightImgDiagram = ComponentGenerator.generateImg("resources/layout8.png", CENTER_ALIGNMENT);
		}
		catch (InvalidImgException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void populateButtonsContainer()
	{
		this.layoutLabel = ComponentGenerator.generateLabel("Images per Page", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.oneImgButton = ComponentGenerator.generateButton("1 per page", this, CENTER_ALIGNMENT);
		this.twoImgButton = ComponentGenerator.generateButton("2 per page", this, CENTER_ALIGNMENT);
		this.fourImgButton = ComponentGenerator.generateButton("4 per page", this, CENTER_ALIGNMENT);
		this.eightImgButton = ComponentGenerator.generateButton("8 per page", this, CENTER_ALIGNMENT);
		this.buttonsContainer.add(layoutLabel);
		this.buttonsContainer.add(Box.createVerticalStrut(15));
		this.buttonsContainer.add(oneImgButton);
		this.buttonsContainer.add(Box.createVerticalStrut(10));
		this.buttonsContainer.add(twoImgButton);
		this.buttonsContainer.add(Box.createVerticalStrut(10));
		this.buttonsContainer.add(fourImgButton);
		this.buttonsContainer.add(Box.createVerticalStrut(10));
		this.buttonsContainer.add(eightImgButton);
	}
	
	private void populateDisplayContainer(int imgsPerPage)
	{
		this.displayContainer.removeAll();
		this.displayContainer.add(this.buttonsContainer);
		this.displayContainer.add(Box.createHorizontalStrut(20));
		if (imgsPerPage == 1)
		{
			this.displayContainer.add(this.oneImgDiagram);
		}
		else if (imgsPerPage == 2)
		{
			this.displayContainer.add(this.twoImgDiagram);
		}
		else if (imgsPerPage == 4)
		{ 
			this.displayContainer.add(this.fourImgDiagram);
		}
		else if (imgsPerPage == 8)
		{
			this.displayContainer.add(this.eightImgDiagram);
		}
		this.revalidate();
		this.repaint();
	}
	
	private void populateContainer()
	{
		this.instructionsLabel = ComponentGenerator.generateLabel("Please select the layout you would like to use to print the selected images:", ComponentGenerator.STANDARD_TEXT_FONT_ITALIC, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.printButton = ComponentGenerator.generateButton("Print", this, CENTER_ALIGNMENT);
		this.container.add(this.instructionsLabel);
		this.container.add(Box.createVerticalStrut(25));
		this.container.add(this.displayContainer);
		this.container.add(Box.createVerticalStrut(15));
		this.container.add(this.printButton);
	}
	
	private void generatePDF()
	{
		
	}

}