// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// PrintSetUpDialogue.java

package gui.display.dialogues;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;
import java.util.*;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;

import org.apache.pdfbox.exceptions.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.edit.*;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.xobject.*;
import org.imgscalr.*;

import backend.exceptions.*;
import gui.*;
import gui.components.icon.*;
import gui.display.*;

public class PrintSetUpDialogue extends JPanel implements ActionListener, Printable
{
	
	private FrameManager manager;
	private ArrayList<CaseIcon> selectedIcons;
	private ArrayList<CaseIcon> printableIcons;
	private ArrayList<PDPage> pages;
	private PrintService[] printers;
	private String[] printerNames;
	private PDDocument document;
	private ImgIcon fullPageDiagram;
	private ImgIcon oneImgDiagram;
	private ImgIcon twoImgDiagram;
	private ImgIcon fourImgDiagram;
	private ImgIcon eightImgDiagram;
	private Box container;
	private Box displayContainer;
	private Box optionContainer;
	private ButtonGroup orientationRadioButtons;
	private JComboBox<String> printerComboBox;
	private JLabel instructionsLabel;
	private JLabel printerLabel;
	private JLabel layoutLabel;
	private JLabel copiesLabel;
	private JLabel orientationLabel;
	private JButton fullPageButton;
	private JButton oneImgButton;
	private JButton twoImgButton;
	private JButton fourImgButton;
	private JButton eightImgButton;
	private JButton printButton;
	private JSpinner copiesSpinner;
	private JRadioButton portraitRadioButton;
	private JRadioButton landscapeRadioButton;
	private String caseNum;
	private int imgsPerPage;
	
	public PrintSetUpDialogue(FrameManager manager, String caseNum, ArrayList<CaseIcon> selectedIcons) 
	{
		this.manager = manager;
		this.caseNum = caseNum;
		this.selectedIcons = selectedIcons;
		this.printableIcons = new ArrayList<CaseIcon>();
		this.pages = new ArrayList<PDPage>();
		this.printers = PrintServiceLookup.lookupPrintServices(null, null);
		this.printerNames = this.retrievePrinterNames();
		this.document = new PDDocument();
		this.imgsPerPage = 0;
		this.container = Box.createVerticalBox();
		this.displayContainer = Box.createHorizontalBox();
		this.optionContainer = Box.createVerticalBox();
		this.generateLayoutDiagrams();
		this.populateOptionContainer();
		this.populateDisplayContainer(0);
		this.populateContainer();
		this.add(this.container);
	}
	
	public enum PrintResult
	{
		PDF_GENERATION_FAILED, PDF_NOT_PRINTABLE, PRINTER_NOT_FOUND, UNEXPECTED_FAILURE, SUCCESS
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.fullPageButton)
		{
			this.populateDisplayContainer(0);
			this.landscapeRadioButton.setEnabled(true);
			this.imgsPerPage = 0;
		}
		else if (e.getSource() == this.oneImgButton)
		{
			this.populateDisplayContainer(1);
			this.landscapeRadioButton.setEnabled(false);
			this.portraitRadioButton.setSelected(true);
			this.imgsPerPage = 1;
		}
		else if (e.getSource() == this.twoImgButton)
		{
			this.populateDisplayContainer(2);
			this.landscapeRadioButton.setEnabled(false);
			this.portraitRadioButton.setSelected(true);
			this.imgsPerPage = 2;
		}
		else if (e.getSource() == this.fourImgButton)
		{
			this.populateDisplayContainer(4);
			this.landscapeRadioButton.setEnabled(false);
			this.portraitRadioButton.setSelected(true);
			this.imgsPerPage = 4;
		}
		else if (e.getSource() == this.eightImgButton)
		{
			this.populateDisplayContainer(8);
			this.landscapeRadioButton.setEnabled(false);
			this.portraitRadioButton.setSelected(true);
			this.imgsPerPage = 8;
		}
		else if (e.getSource() == this.printButton)
		{
			PrintResult result = this.initiatePrint();
			if (result == PrintResult.PDF_GENERATION_FAILED || result == PrintResult.PDF_NOT_PRINTABLE)
			{
				JOptionPane.showMessageDialog(this.manager.getMainWindow(), "PEMS was unable to generate a printable PDF file containing the selected images. Please try again.\nIf the problem persists, restart the program and try once more.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if (result == PrintResult.PRINTER_NOT_FOUND)
			{
				JOptionPane.showMessageDialog(this.manager.getMainWindow(), "The specified printer could not be located. Please select a different printer and try again.\nIf the problem persists, restart the program and try once more.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if (result == PrintResult.UNEXPECTED_FAILURE)
			{
				JOptionPane.showMessageDialog(this.manager.getMainWindow(), "The print job you were attempting to complete unexpectedly failed. Please try again.\nIf the problem persists, restart the program and try once more.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				this.manager.closeDialogue();
			}
		}
	}
	
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException 
	{
		if (this.imgsPerPage == 0)
		{
		    if (pageIndex < this.pages.size()) 
		    {
		        try 
		        {
					graphics.drawImage(this.pages.get(pageIndex).convertToImage(), 100, 100, null);
				} 
		        catch (IOException e) 
		        {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
		        return PAGE_EXISTS;
		    } 
		    else
		    {
		        return NO_SUCH_PAGE;
		    }
		}
		else
		{
		    if (pageIndex < this.printableIcons.size()) 
		    {
				graphics.drawImage(this.printableIcons.get(pageIndex).getImage(), 100, 100, null);
		        return PAGE_EXISTS;
		    } 
		    else
		    {
		        return NO_SUCH_PAGE;
		    }
		}

	}
	
	private void generateLayoutDiagrams()
	{
		try
		{
			this.fullPageDiagram = new ImgIcon("resources/layoutFull.png");
			this.fullPageDiagram.setAlignmentX(CENTER_ALIGNMENT);
			this.oneImgDiagram = new ImgIcon("resources/layout1.png");
			this.oneImgDiagram.setAlignmentX(CENTER_ALIGNMENT);
			this.twoImgDiagram = new ImgIcon("resources/layout2.png");
			this.twoImgDiagram.setAlignmentX(CENTER_ALIGNMENT);
			this.fourImgDiagram = new ImgIcon("resources/layout4.png");
			this.fourImgDiagram.setAlignmentX(CENTER_ALIGNMENT);
			this.eightImgDiagram = new ImgIcon("resources/layout8.png");
			this.eightImgDiagram.setAlignmentX(CENTER_ALIGNMENT);
		}
		catch (InvalidFileException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void populateOptionContainer()
	{
		this.printerLabel = ComponentGenerator.generateLabel("Printer", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.layoutLabel = ComponentGenerator.generateLabel("Print Layout", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.copiesLabel = ComponentGenerator.generateLabel("Copies", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.orientationLabel = ComponentGenerator.generateLabel("Orientation", ComponentGenerator.STANDARD_TEXT_FONT_BOLD, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.fullPageButton = ComponentGenerator.generateButton("Full Page", this, CENTER_ALIGNMENT);
		this.oneImgButton = ComponentGenerator.generateButton("1 per page", this, CENTER_ALIGNMENT);
		this.twoImgButton = ComponentGenerator.generateButton("2 per page", this, CENTER_ALIGNMENT);
		this.fourImgButton = ComponentGenerator.generateButton("4 per page", this, CENTER_ALIGNMENT);
		this.eightImgButton = ComponentGenerator.generateButton("8 per page", this, CENTER_ALIGNMENT);
		this.printerComboBox = ComponentGenerator.generateComboBox(this.printerNames, CENTER_ALIGNMENT);
		this.copiesSpinner = ComponentGenerator.generateNumberSpinner(1, 1, 100, 1, 30, CENTER_ALIGNMENT);
		this.portraitRadioButton = ComponentGenerator.generateRadioButton("Portrait", true, CENTER_ALIGNMENT);
		this.landscapeRadioButton = ComponentGenerator.generateRadioButton("Landscape", false, CENTER_ALIGNMENT);
		this.orientationRadioButtons = new ButtonGroup();
		this.orientationRadioButtons.add(this.portraitRadioButton);
		this.orientationRadioButtons.add(this.landscapeRadioButton);
		this.optionContainer.add(this.printerLabel);
		this.optionContainer.add(Box.createVerticalStrut(15));
		this.optionContainer.add(this.printerComboBox);
		this.optionContainer.add(Box.createVerticalStrut(15));
		this.optionContainer.add(this.layoutLabel);
		this.optionContainer.add(Box.createVerticalStrut(15));
		this.optionContainer.add(this.fullPageButton);
		this.optionContainer.add(Box.createVerticalStrut(10));
		this.optionContainer.add(this.oneImgButton);
		this.optionContainer.add(Box.createVerticalStrut(10));
		this.optionContainer.add(this.twoImgButton);
		this.optionContainer.add(Box.createVerticalStrut(10));
		this.optionContainer.add(this.fourImgButton);
		this.optionContainer.add(Box.createVerticalStrut(10));
		this.optionContainer.add(this.eightImgButton);
		this.optionContainer.add(Box.createVerticalStrut(15));
		this.optionContainer.add(this.copiesLabel);
		this.optionContainer.add(Box.createVerticalStrut(15));
		this.optionContainer.add(this.copiesSpinner);
		this.optionContainer.add(Box.createVerticalStrut(15));
		this.optionContainer.add(this.orientationLabel);
		this.optionContainer.add(Box.createVerticalStrut(15));
		this.optionContainer.add(this.portraitRadioButton);
		this.optionContainer.add(Box.createVerticalStrut(10));
		this.optionContainer.add(this.landscapeRadioButton);
	}
	
	private void populateDisplayContainer(int imgsPerPage)
	{
		this.displayContainer.removeAll();
		this.displayContainer.add(this.optionContainer);
		this.displayContainer.add(Box.createHorizontalStrut(20));
		if (imgsPerPage == 0)
		{
			this.displayContainer.add(this.fullPageDiagram);
		}
		else if (imgsPerPage == 1)
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
		this.instructionsLabel = ComponentGenerator.generateLabel("Below are the options for the current print job. Please ensure they are correct before continuing.", ComponentGenerator.STANDARD_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.printButton = ComponentGenerator.generateButton("Print", this, CENTER_ALIGNMENT);
		this.container.add(this.instructionsLabel);
		this.container.add(Box.createVerticalStrut(25));
		this.container.add(this.displayContainer);
		this.container.add(Box.createVerticalStrut(15));
		this.container.add(this.printButton);
	}
	
	private String[] retrievePrinterNames()
	{
		if (this.printers.length == 0)
		{
			String[] printerNames = {"NO PRINTERS FOUND"};
			return printerNames;
		}
		else
		{
			String[] printerNames = new String[this.printers.length];
			for (int i = 0; i < this.printers.length; i++)
			{
				printerNames[i] = this.printers[i].getName();
			}
			return printerNames;
		}
	}
	
	private PrintResult initiatePrint()
	{
		PrinterJob job = PrinterJob.getPrinterJob();
		for (int i = 0; i < this.printers.length; i++)
		{
			if (this.printers[i].getName().equalsIgnoreCase((String)(this.printerComboBox.getSelectedItem())))
			{
				try 
				{
					PrintService service = this.printers[i];
					job.setPrintService(service);
				} 
				catch (PrinterException e) 
				{
					return PrintResult.PRINTER_NOT_FOUND;
				}
				break;
			}
		}
		if (job.getPrintService() == null)
		{
			return PrintResult.PRINTER_NOT_FOUND;
		}
		if (this.imgsPerPage > 0)
		{
			try 
			{
				this.generatePDF();
				try 
				{
					job.setPageable(new PDPageable(document, job));
				} 
				catch (NullPointerException | IllegalArgumentException | PrinterException e) 
				{
					e.printStackTrace();
					System.out.println(e.getMessage());
					return PrintResult.PDF_NOT_PRINTABLE;
				}
			} 
			catch (IOException | InvalidFileException e)
			{
				e.printStackTrace();
				System.out.println(e.getMessage());
				return PrintResult.PDF_GENERATION_FAILED;
			}
		}
		PageFormat format = job.getPageFormat(null);
		if (this.portraitRadioButton.isSelected())
		{
			format.setOrientation(PageFormat.PORTRAIT);
		}
		else
		{
			format.setOrientation(PageFormat.LANDSCAPE);
		}
		job.setCopies((int)(this.copiesSpinner.getValue()));
		job.setPrintable(this, format);
		job.setJobName("Case " + this.caseNum);
		try 
		{
			this.document.silentPrint(job);
		} 
		catch (PrinterException e) 
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return PrintResult.UNEXPECTED_FAILURE;
		}
		return PrintResult.SUCCESS;
	}
	
	private void generatePDF() throws IOException, InvalidFileException 
	{
		this.generatePages();
		this.generatePrintableImgs();
		this.addHeader();
		this.addImgs();
		try 
		{
			this.document.save("PRINTJOB.pdf");
		} 
		catch (COSVisitorException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void generatePages()
	{
		int numPages = 0;
		if (this.imgsPerPage >= this.selectedIcons.size())
		{
			numPages = 1;
		}
		else
		{
			if (this.selectedIcons.size() % this.imgsPerPage == 0)
			{
				numPages = this.selectedIcons.size() / this.imgsPerPage;
			}
			else
			{
				numPages = (this.selectedIcons.size() / this.imgsPerPage) + 1; 
			}
		}
		for (int i = 0; i < numPages; i++)
		{
			this.pages.add(new PDPage());
		}
	}
	
	private void generatePrintableImgs()
	{
		for (int i = 0; i < this.selectedIcons.size(); i++)
		{
			CaseIcon currentImg = null;
			try
			{
				currentImg = this.resizeForPrint(new CaseIcon(this.selectedIcons.get(i).getParentFile()));
				this.printableIcons.add(currentImg);
			}
			catch (InvalidFileException e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
				break;
			}
		}
	}
	
	private void addHeader() throws IOException, InvalidFileException
	{
		ImgIcon logoIcon = new ImgIcon("resources/logosmall.png", Scalr.Method.ULTRA_QUALITY, 50);
		PDXObjectImage pdfLogo = new PDJpeg(this.document, logoIcon.getImage());
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
			contentStream.drawLine(0, 715, this.pages.get(i).getMediaBox().getWidth(), 715);
			contentStream.drawLine(0, 710, this.pages.get(i).getMediaBox().getWidth(), 710);
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
			incrementY = 310;
		}
		else if (this.imgsPerPage == 2)
		{
			rowsPerPage = 2;
			colsPerPage = 1;
			incrementX = 270 + (int)(this.pages.get(0).getMediaBox().getWidth() - (2 * (50 + 270)));
			incrementY = 310;
		}
		else if (this.imgsPerPage == 4)
		{
			rowsPerPage = 2;
			colsPerPage = 2;
			incrementX = 230 + (int)(this.pages.get(0).getMediaBox().getWidth() - (2 * (50 + 230)));
			incrementY = 270;
		}
		else if (this.imgsPerPage == 8)
		{
			rowsPerPage = 4;
			colsPerPage = 2;
			incrementX = 125 + (int)(this.pages.get(0).getMediaBox().getWidth() - (2 * (50 + 125)));
			incrementY = 165;
		}
		for (int i = 0; i < this.pages.size(); i++)
		{
			PDPageContentStream contentStream = new PDPageContentStream(this.document, this.pages.get(i), true ,true);
			currentY = 700 - incrementY;
			for (int j = 0; j < rowsPerPage; j++)
			{
				currentX = 50;
				for (int k = 0; k < colsPerPage; k++)
				{
					if (currentImgIndex < this.printableIcons.size())
					{
						PDXObjectImage currentPDFImg = new PDJpeg(this.document, this.printableIcons.get(currentImgIndex).getImage());
						contentStream.drawImage(currentPDFImg, currentX, currentY);
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(currentX, currentY - 12);
						contentStream.drawString(this.printableIcons.get(currentImgIndex).getParentFile().getImg().getTimestamp());
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
	
	private CaseIcon resizeForPrint(CaseIcon icon) throws InvalidFileException
	{
		if (this.imgsPerPage == 1 || this.imgsPerPage == 2)
		{
			if (icon.getImage().getHeight() > 270)
			{
				int newWidth = (icon.getImage().getWidth() * 270) / icon.getImage().getHeight();
				icon.resizeImage(newWidth, 270);
			}
			if (icon.getImage().getWidth() > 270)
			{
				int newHeight = (icon.getImage().getHeight() * 270) / icon.getImage().getWidth();
				icon.resizeImage(270, newHeight);
			}
		}
		else if (this.imgsPerPage == 4)
		{
			if (icon.getImage().getHeight() > 230)
			{
				int newWidth = (icon.getImage().getWidth() * 230) / icon.getImage().getHeight();
				icon.resizeImage(newWidth, 230);
			}
			if (icon.getImage().getWidth() > 230)
			{
				int newHeight = (icon.getImage().getHeight() * 230) / icon.getImage().getWidth();
				icon.resizeImage(230, newHeight);
			}
		}
		else if (this.imgsPerPage == 8)
		{
			if (icon.getImage().getHeight() > 125)
			{
				int newWidth = (icon.getImage().getWidth() * 125) / icon.getImage().getHeight();
				icon.resizeImage(newWidth, 125);
			}
			if (icon.getImage().getWidth() > 125)
			{
				int newHeight = (icon.getImage().getHeight() * 125) / icon.getImage().getWidth();
				icon.resizeImage(125, newHeight);
			}
		}
		return icon;
	}

}