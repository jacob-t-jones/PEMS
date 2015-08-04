// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenStart.java
package gui.display.print;

import exceptions.InvalidImgException;
import gui.*;
import gui.components.img.Img;
import gui.components.img.ThumbnailImg;
import gui.display.FrameManager;
import gui.display.editimg.EditImgPanel;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.imgscalr.Scalr;

public class PrintSetUpPanel extends JPanel implements ActionListener, MouseListener,
		FocusListener {
	private FrameManager manager;
	private ArrayList<ThumbnailImg> selectedThumbnails;
	private EditImgPanel currentScreen;
	private Box container;
	private Box widthContainer;
	private Box heightContainer;
	private JButton printButton;
	private JButton layout1;
	private JButton layout2;
	private JButton layout4;
	private JButton layout8;
	private JLabel instructionsLabel;
	private JLabel heightLabel;
	private int originalWidth;
	private int originalHeight;
	private Component mainContainer;
	private Box buttonsContainer;
	private Img logoImage; // / was baseimg /buff
	// private Img logoImage;
	private PDXObjectImage pdfBadge;
	private PDDocument document;
	private Point pos;
	private Img[][] content;
	private int format;
	private Box displayContainer;
	private Img layoutImg;

	public PrintSetUpPanel(FrameManager manager, ArrayList<ThumbnailImg> selectedThumbnails) throws IOException {
		this.manager = manager;
		this.selectedThumbnails = selectedThumbnails;
		this.container = Box.createVerticalBox();
		this.buttonsContainer = Box.createVerticalBox();
		this.displayContainer = Box.createHorizontalBox();
		Img firstImg = this.getInitLayoutImg();
		this.populateButtonsContainer();
		//this.populateDisplayContainer(getInitLayoutImg());
		this.populateContainer(firstImg);
		
		
		this.checkConditions();
		//this.generatePDF(); //move to print button

		this.add(this.container);
	}
	
	/* getInitLayoutImg - grabs the first layout image for 1 image on a page
	 */
	private Img getInitLayoutImg()
	{
		layoutImg = null;
		try 
		{
			this.layoutImg = ComponentGenerator.generateImg("resources/layout1.png", CENTER_ALIGNMENT);
		} 
		catch(Exception ie)
		{
			System.out.println("error - could not find layout image");
		}
		return layoutImg;
	}
	
	private void populateDisplayContainer(Img layoutImage) {
		this.displayContainer.removeAll();
		this.displayContainer.add(buttonsContainer);
		this.displayContainer.add(layoutImage);
		return;
	}
	
	private void populateButtonsContainer() {
		this.layout1 = ComponentGenerator.generateButton("  1  ", this);
		this.layout2 = ComponentGenerator.generateButton("  2  ", this);
		this.layout4 = ComponentGenerator.generateButton("  4  ", this);
		this.layout8 = ComponentGenerator.generateButton("  8  ", this);
		this.buttonsContainer.add(this.layout1);
		this.container.add(Box.createVerticalStrut(10));
		this.buttonsContainer.add(this.layout2);
		this.container.add(Box.createVerticalStrut(10));
		this.buttonsContainer.add(this.layout4);
		this.container.add(Box.createVerticalStrut(10));
		this.buttonsContainer.add(this.layout8);
		
		this.buttonsContainer.add(Box.createHorizontalStrut(20));
	}

	private void populateContainer(Img img) {
		this.container.removeAll();
		this.instructionsLabel = ComponentGenerator.generateLabel("Select the format you wish to use:", ComponentGenerator.STANDARD_TEXT_FONT_BOLD,
				ComponentGenerator.STANDARD_TEXT_COLOR);
		this.container.add(this.instructionsLabel);
		this.container.add(Box.createVerticalStrut(20));
		
		this.populateDisplayContainer(img);
		this.container.add(Box.createVerticalStrut(20));
		this.container.add(this.displayContainer);
		
		this.printButton = ComponentGenerator.generateButton("Print", this);
		this.container.add(Box.createVerticalStrut(10));
		this.container.add(this.printButton);
		
		this.revalidate();
		this.repaint();
	}
	
	/* actionPerformed - display the layout image based on which button was selected
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.layout1) {
			try 
			{
				this.layoutImg = ComponentGenerator.generateImg("resources/layout1.png", CENTER_ALIGNMENT);
				this.format = 1;
			} 
			catch(Exception ie)
			{
				System.out.println("error - could not find layout image");
			}
		}
		else if (e.getSource() == this.layout2) {
			try 
			{
				this.layoutImg = ComponentGenerator.generateImg("resources/layout2.png", CENTER_ALIGNMENT);
				this.format = 2;
			} 
			catch(Exception ie)
			{
				System.out.println("error - could not find layout image");
			}
		}
		else if (e.getSource() == this.layout4) {
			try 
			{
				this.layoutImg = ComponentGenerator.generateImg("resources/layout4.png", CENTER_ALIGNMENT);
				this.format = 4;
			} 
			catch(Exception ie)
			{
				System.out.println("error - could not find layout image");
			}
		}
		else if (e.getSource() == this.layout8) {
			try 
			{
				this.layoutImg = ComponentGenerator.generateImg("resources/layout8.png", CENTER_ALIGNMENT);
				this.format = 8;
			} 
			catch(Exception ie)
			{
				System.out.println("error - could not find layout image");
			}
		}
		else if (e.getSource() == this.printButton)
		{
			try {
				this.generatePDF();
				System.out.println("Generated PDF");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		this.populateContainer(this.layoutImg);
		//this.populateDisplayContainer(layoutImg);
		return;
	}

	/* Check to see if all conditions are met before generating a PDF, otherwise it will exit the program
	 */
	private void checkConditions() {
		if(selectedThumbnails.size() == 0)
		{
			System.out.println("No images have been selected!");
			Runtime.getRuntime().exit(1);
		}
		else
		{
			return;
		}
	}

	/* importImages - reads all necessary images into memory
	 */
	private void importBadgeImages() {

		try {
			this.logoImage = ComponentGenerator.generateImg(
					"resources/logo.png", CENTER_ALIGNMENT);
			// setImage(ImageIO.read(new File("resources/logo.png")));

			this.logoImage.resizeImage(Scalr.Method.ULTRA_QUALITY, 50);
			this.pdfBadge = new PDJpeg(this.document, logoImage.getImage());
		} catch (IOException | InvalidImgException e) {
			System.out.println("Error - Unable to import badge image");
			e.printStackTrace();
		}
		try {
			this.pdfBadge = new PDJpeg(this.document, this.logoImage.getImage());
		} catch (IOException e) {
			System.out.println("Error - Unable to import badge image");
			e.printStackTrace();
		}

	}

	/* generatePDF - generates a PDF document for police evidence
	 */
	private PDDocument generatePDF() throws IOException {
		System.out.println("Creating PDF");
		// Create a document and add a page to it
		document = new PDDocument();
		
		//Determine how many pages are needed to print the document
		//this.createPages(); divide total selected into number to be displayed per page + 1 page if there is remainder
		PDPage page = new PDPage();
		document.addPage(page);

		// import and convert the badge image
		this.importBadgeImages();

		// Create a new font object selecting one of the PDF base fonts
		PDFont font = PDType1Font.HELVETICA_BOLD;

		// Start a new content stream which will "hold" the to be created content
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		// Initialize margins
		this.initializePDFTools(page);

		// 1.) insert the badge logo at the top of the page
		this.pos.setLocation(190, 720);
		contentStream.drawImage(this.pdfBadge, pos.x, pos.y);

		// 1.1.) Open up the content stream for text insertion
		contentStream.beginText();
		contentStream.setFont(font, 12);
		this.pos.setLocation(250, 760);
		contentStream.moveTextPositionByAmount(pos.x, pos.y);
		contentStream.drawString("Plainville Police Department");

		contentStream.setFont(font, 8);
		contentStream.moveTextPositionByAmount(0, -12);
		contentStream.drawString("19 Neal Ct");
		contentStream.moveTextPositionByAmount(0, -12);
		contentStream.drawString("Plainville, CT");
		contentStream.moveTextPositionByAmount(0, -12);
		contentStream.drawString("(860) 747-1616");
		contentStream.endText();

		// place the thumbnails on the screen
		// this.displayImages(contentStream);

		// decide which layout to use based on what is selected
		this.content = this.getFormat(this.format);

		drawTable(page, contentStream, 420, 50, this.content, format);

		// Make sure that the content stream is closed:
		contentStream.close();

		// Save the results and ensure that the document is properly closed:
		try {
			document.save("Hello World.pdf");
		} catch (COSVisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.close();

		return null;

	}

	/*
	 * getFormat - chooses how many images the user would like to print on one
	 * page
	 */
	private Img[][] getFormat(int formatNum) {
		Img[][] newContent;
		if (formatNum == 1)
		{
			newContent = new Img[][] { { null } };
			this.format = 1;
		} 
		else if (formatNum == 2)
		{
			newContent = new Img[][] { { null }, { null } };
			this.format = 2;
		}
		
		else if (formatNum == 4) 
		{
			newContent = new Img[][] { { null, null }, { null, null } };
			this.format = 4;
		} 
		else if (formatNum == 8)
		{
			newContent = new Img[][] { { null, null }, { null, null }, { null, null }, { null, null } };
			this.format = 8;
		}
		else {
			newContent = new Img[][] { { null } };
			this.format = 0;
			System.out.println("You have no selected images!");
		}

		return newContent;
	}

	
	/* drawTable - constructs the main contents of the PDF (images and dates) based on selected format
	 *      page - The page in which the contents are added
	 * contentStream - the stream which contents will be added to 
	 * @param y
	 *            the y-coordinate of the first row
	 * @param margin
	 *            the padding on left and right of table
	 * @param content
	 *            a 2d array containing the table data
	 * @throws IOException
	 */
	public void drawTable(PDPage page, PDPageContentStream contentStream, float y, float margin, Img[][] content, int formatLayout)
			throws IOException {
		int rows = content.length;
		int cols = content[0].length;
		float rowHeight = 20f;
		float tableWidth = page.findMediaBox().getWidth() - margin - margin;
		float tableHeight = rowHeight * rows;
		float colWidth = tableWidth / cols;
		float cellMargin = 5f;

		// draw the rows
		float nexty = y;
		for (int i = 0; i <= rows; i++) {
			// contentStream.drawLine(margin, nexty, margin + tableWidth,
			// nexty);
			nexty -= rowHeight;
		}

		// draw the columns
		float nextx = margin;
		for (int i = 0; i <= cols; i++) {
			// contentStream.drawLine(nextx, y, nextx, y - tableHeight);
			nextx += colWidth;
		}

		// now add the text
		// contentStream.setFont( PDType1Font.HELVETICA_BOLD , 12 );

		float textx = margin + cellMargin;
		float texty = y - 15;
		int imgCounter = 0;
		for (int i = 0; i < content.length; i++) {
			ThumbnailImg imgpic = null;
			for (int j = 0; j < content[i].length; j++) {
				
				try {
					imgpic = ComponentGenerator.generateThumbnailImg(selectedThumbnails.get(imgCounter).getFilePath(), 250);
				} catch (InvalidImgException e) {
					try {
						imgpic = ComponentGenerator.generateThumbnailImg("resources/blankimage.png", 250);
					} catch (InvalidImgException ie) {
						ie.printStackTrace();
					}
					e.printStackTrace();
				}
				//Otherwise attach a blank photo as a place holder
				
				
				//depending on what layout we have chosen will determine what scale size we use
				if(formatLayout == 0)
				{
					contentStream.beginText(); //Will never enter here, added check on screen before this
					contentStream.moveTextPositionByAmount(60, texty-12);
					contentStream.drawString("You have no images selected!");
					contentStream.endText();
				}
				else if(formatLayout == 1 || formatLayout == 2)
				{
					imgpic.resizeImage(Scalr.Method.ULTRA_QUALITY, 400);
				}
				else if(formatLayout == 4)
				{
					imgpic.resizeImage(Scalr.Method.ULTRA_QUALITY, 250);
				}
				else if(formatLayout == 8)
				{
					imgpic.resizeImage(Scalr.Method.ULTRA_QUALITY, 150);
				}

				texty = y;

				//convert to a pdf readable image
				PDXObjectImage tempPDFImage = null;
				tempPDFImage = new PDJpeg(this.document, imgpic.getImage());

				// add the image to the pdf file
				contentStream.drawImage(tempPDFImage, textx, texty);

				// Add the date below the image
				contentStream.beginText();
				contentStream.moveTextPositionByAmount(textx + margin, texty - 12);
				contentStream.drawString(imgpic.getDate());
				contentStream.endText();

				// increment variables
				textx += colWidth;
				//y -= (imgpic.getImage().getHeight() + 40);
				imgCounter++;
			}
			y -= (imgpic.getImage().getHeight() + 40);
			//texty -= (imgpic.getImage().getHeight() + 40);
			textx = margin + cellMargin;
		}
	}

	
	private Point nextLine(Point p, PDPageContentStream contentStream) {
		p.setLocation(p.getX(), p.getY() - 15);
		return p;
	}

	private void displayImages(PDPageContentStream contentStream) {

		BufferedImage tempImage = null;
		PDXObjectImage tempPDFImage = null;

		for (int i = 0; i < selectedThumbnails.size(); i++) {
			try {
				tempImage = selectedThumbnails.get(i).getImage();
				tempPDFImage = new PDJpeg(this.document, tempImage);
				// add the image to the pdf file
				contentStream.drawImage(tempPDFImage, 10, tempImage.getHeight()
						+ (i * 100));
			} catch (IOException e) {
				System.out.println("error - file, "
						+ selectedThumbnails.get(i).getFilePath()
						+ ", could not be printed.");
				e.printStackTrace();
			}
		}

	}

	/*
	 * initializeMargins - set up the margins to keep content on page
	 */
	private void initializePDFTools(PDPage page) {
		PDRectangle mediabox = page.findMediaBox();
		float margin = 72;
		float width = mediabox.getWidth() - margin;
		float startX = mediabox.getLowerLeftX() + margin;
		float startY = mediabox.getUpperRightY() - margin;

		this.pos = new Point(0, 0);
	}



	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
