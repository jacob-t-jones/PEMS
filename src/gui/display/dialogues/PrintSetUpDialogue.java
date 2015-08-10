// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// PrintSetUpDialogue.java

package gui.display.dialogues;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import org.apache.pdfbox.exceptions.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.edit.*;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.xobject.*;
import org.imgscalr.*;
import exceptions.*;
import gui.*;
import gui.components.img.*;
import gui.display.*;

public class PrintSetUpDialogue extends JPanel implements ActionListener
{
	
	private FrameManager manager;
	private ArrayList<ThumbnailImg> selectedThumbnails;
	private ArrayList<Img> printableImgs;
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
		this.printableImgs = new ArrayList<Img>();
		this.pages = new ArrayList<PDPage>();
		this.document = new PDDocument();
		this.imgsPerPage = 1;
		this.container = Box.createVerticalBox();
		this.displayContainer = Box.createHorizontalBox();
		this.buttonsContainer = Box.createVerticalBox();
		this.generateLayoutDiagrams();
		this.populateButtonsContainer();
		this.populateDisplayContainer(1);
		this.populateContainer();
		this.add(this.container);
	}
	
	/* actionPerformed - mandatory for any class implementing ActionListener, checks the source of the ActionEvent and executes the appropriate code 
	 *	             e - the event in question
	 *                 1. sets the selected layout type to one image per page
	 *                 2. sets the selected layout type to two images per page
	 *                 3. sets the selected layout type to four images per page
	 *                 4. sets the selected layout type to eight images per page
	 *                 5. initiates the print sequence
	 */
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
			try 
			{
				this.generatePDF();
			} 
			catch (IOException | InvalidImgException e1) 
			{
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}
		}
	}
	
	/* generateLayoutDiagrams - creates and initializes the images used to represent the four different layouts
	 */
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
	
	/* populateButtonsContainer - adds the necessary components to "buttonsContainer"
	 */
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
	
	/* populateDisplayContainer - adds the necessary components to "displayContainer"
	 */
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
	
	/* populateContainer - adds the necessary components to "container"
	 */
	private void populateContainer()
	{
		this.instructionsLabel = ComponentGenerator.generateLabel("Please select the layout you would like to use to print the selected images:", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.printButton = ComponentGenerator.generateButton("Print", this, CENTER_ALIGNMENT);
		this.container.add(this.instructionsLabel);
		this.container.add(Box.createVerticalStrut(25));
		this.container.add(this.displayContainer);
		this.container.add(Box.createVerticalStrut(15));
		this.container.add(this.printButton);
	}
	
	/* generatePDF - primary function for carrying out the PDF generation process
	 */
	private void generatePDF() throws IOException, InvalidImgException 
	{
		this.generatePages();
		this.generatePrintableImgs();
		this.addHeader();
		this.addImgs();
		try 
		{
			this.document.save("TEST.pdf");
		} 
		catch (COSVisitorException e) 
		{
			e.printStackTrace();
		}
		this.document.close();
	}
	
	/* generatePages - determines how many pages should be generated for the PDF, creates them, and adds them to the "pages" ArrayList
	 */
	private void generatePages()
	{
		int numPages = 0;
		if (this.imgsPerPage >= this.selectedThumbnails.size())
		{
			numPages = 1;
		}
		else
		{
			if (this.selectedThumbnails.size() % this.imgsPerPage == 0)
			{
				numPages = this.selectedThumbnails.size() / this.imgsPerPage;
			}
			else
			{
				numPages = (this.selectedThumbnails.size() / this.imgsPerPage) + 1; 
			}
		}
		for (int i = 0; i < numPages; i++)
		{
			this.pages.add(new PDPage());
		}
	}
	
	/* generatePrintableImgs - populates the "printableImgs" ArrayList by generating fitted Img versions of the ThumbnailImg objects in "selectedThumbnails"
	 */
	private void generatePrintableImgs()
	{
		for (int i = 0; i < this.selectedThumbnails.size(); i++)
		{
			Img currentImg = null;
			try
			{
				currentImg = ComponentGenerator.generateImg(this.selectedThumbnails.get(i).getFilePath());
				currentImg = this.resizeForPrint(currentImg);
				this.printableImgs.add(currentImg);
			}
			catch (InvalidImgException e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
				break;
			}
		}
	}
	
	/* addHeader - 
	 */
	private void addHeader() throws IOException, InvalidImgException
	{
		Img logoImg = ComponentGenerator.generateImg("resources/logo.png", CENTER_ALIGNMENT);
		logoImg.resizeImage(Scalr.Method.ULTRA_QUALITY, 50);
		PDXObjectImage pdfLogo = new PDJpeg(this.document, logoImg.getImage());
		for (int i = 0; i < this.pages.size(); i++)
		{
			this.document.addPage(this.pages.get(i));
			PDPageContentStream contentStream = new PDPageContentStream(this.document, this.pages.get(i));
			contentStream.drawImage(pdfLogo, 190, 720);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.moveTextPositionByAmount(250, 760);
			contentStream.drawString("Plainville Police Department");
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
			contentStream.moveTextPositionByAmount(0, -12);
			contentStream.drawString("19 Neal Ct");
			contentStream.moveTextPositionByAmount(0, -12);
			contentStream.drawString("Plainville, CT");
			contentStream.moveTextPositionByAmount(0, -12);
			contentStream.drawString("(860) 747-1616");
			contentStream.endText();
			contentStream.close();
		}
	}
	
	private void addImgs() throws IOException
	{
		int currentImgIndex = 0;
		int currentX = 0;
		int currentY = 0;
		int incrementX = 0;
		int incrementY = 0;
		int rowsPerPage = 0;
		int colsPerPage = 0;
		if (this.imgsPerPage == 1)
		{
			rowsPerPage = 1;
			colsPerPage = 1;
			incrementX = 270 + (int)(this.pages.get(0).getMediaBox().getWidth() - (2 * (50 + 270)));
			incrementY = 290;
		}
		else if (this.imgsPerPage == 2)
		{
			rowsPerPage = 2;
			colsPerPage = 1;
			incrementX = 270 + (int)(this.pages.get(0).getMediaBox().getWidth() - (2 * (50 + 270)));
			incrementY = 290;
		}
		else if (this.imgsPerPage == 4)
		{
			rowsPerPage = 2;
			colsPerPage = 2;
			incrementX = 230 + (int)(this.pages.get(0).getMediaBox().getWidth() - (2 * (50 + 230)));
			incrementY = 250;
		}
		else if (this.imgsPerPage == 8)
		{
			rowsPerPage = 4;
			colsPerPage = 2;
			incrementX = 125 + (int)(this.pages.get(0).getMediaBox().getWidth() - (2 * (50 + 125)));
			incrementY = 145;
		}
		for (int i = 0; i < this.pages.size(); i++)
		{
			PDPageContentStream contentStream = new PDPageContentStream(this.document, this.pages.get(i), true ,true);
			currentY = 550;
			for (int j = 0; j < rowsPerPage; j++)
			{
				currentX = 50;
				for (int k = 0; k < colsPerPage; k++)
				{
					if (currentImgIndex < this.printableImgs.size())
					{
						PDXObjectImage currentPDFImg = new PDJpeg(this.document, this.printableImgs.get(currentImgIndex).getImage());
						contentStream.drawImage(currentPDFImg, currentX, currentY);
						System.out.println(currentX);
						System.out.println(incrementX);
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(currentX, currentY - 12);
						contentStream.drawString(this.printableImgs.get(currentImgIndex).getTimestamp());
						contentStream.endText();
						currentImgIndex++;
					}
					currentX += incrementX;
				}
				currentY -= incrementY;
			}
			contentStream.close();
		}
	}
	
	/* resizeForPrint - returns a modified version of a given Img, resized to fit properly on the PDF
	 *            img - the image to resize
	 */
	private Img resizeForPrint(Img img)
	{
		if (this.imgsPerPage == 1 || this.imgsPerPage == 2)
		{
			if (img.getImage().getHeight() > 270)
			{
				int newWidth = (img.getImage().getWidth() * 270) / img.getImage().getHeight();
				img.resizeImage(Scalr.Method.ULTRA_QUALITY, newWidth, 270);
			}
			if (img.getImage().getWidth() > 270)
			{
				int newHeight = (img.getImage().getHeight() * 270) / img.getImage().getWidth();
				img.resizeImage(Scalr.Method.ULTRA_QUALITY, 270, newHeight);
			}
		}
		else if (this.imgsPerPage == 4)
		{
			if (img.getImage().getHeight() > 230)
			{
				int newWidth = (img.getImage().getWidth() * 230) / img.getImage().getHeight();
				img.resizeImage(Scalr.Method.ULTRA_QUALITY, newWidth, 230);
			}
			if (img.getImage().getWidth() > 230)
			{
				int newHeight = (img.getImage().getHeight() * 230) / img.getImage().getWidth();
				img.resizeImage(Scalr.Method.ULTRA_QUALITY, 230, newHeight);
			}
		}
		else if (this.imgsPerPage == 8)
		{
			if (img.getImage().getHeight() > 125)
			{
				int newWidth = (img.getImage().getWidth() * 125) / img.getImage().getHeight();
				img.resizeImage(Scalr.Method.ULTRA_QUALITY, newWidth, 125);
			}
			if (img.getImage().getWidth() > 125)
			{
				int newHeight = (img.getImage().getHeight() * 125) / img.getImage().getWidth();
				img.resizeImage(Scalr.Method.ULTRA_QUALITY, 125, newHeight);
			}
		}
		return img;
	}

}