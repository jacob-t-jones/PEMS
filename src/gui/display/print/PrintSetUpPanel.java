// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenStart.java
package gui.display.print;

import exceptions.InvalidImgException;
import gui.*;
<<<<<<< HEAD:src/gui/panels/ScreenPrintSetUp.java
import gui.img.BaseImg;
import gui.img.ThumbnailImg;
=======
import gui.components.img.Img;
import gui.display.FrameManager;
import gui.display.editimg.EditImgPanel;
>>>>>>> origin/master:src/gui/display/print/PrintSetUpPanel.java

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.pdfbox.PDFBox;
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

import pdftools.contentPosition;
import tools.ImageEditor;

public class PrintSetUpPanel extends JPanel implements ActionListener, FocusListener
{
	private FrameManager manager;
	private ArrayList<Thumbnail> selectedThumbnails;
	private EditImgPanel currentScreen;
	private Box container;
	private Box widthContainer;
	private Box heightContainer;
	private JLabel widthLabel;
	private JLabel heightLabel;
	private int originalWidth;
	private int originalHeight;
	private Component mainContainer;
	private Box buttonsContainer;
<<<<<<< HEAD:src/gui/panels/ScreenPrintSetUp.java
	private BufferedImage logoImage; /// was baseimg
=======
	private Img logoImage;
>>>>>>> origin/master:src/gui/display/print/PrintSetUpPanel.java
	private PDXObjectImage pdfBadge;
	private PDDocument document;
	private Point pos; 
	//private ThumbnailImg logoThumb;

	public PrintSetUpPanel(FrameManager manager, ArrayList<Thumbnail> selectedThumbnails) throws IOException
	{
		this.manager = manager;
		this.selectedThumbnails = selectedThumbnails;
		this.populateButtonsContainer();
		this.populateMainContainer();
		
		this.generatePDF();
		this.mainContainer = Box.createVerticalBox();
		this.buttonsContainer = Box.createHorizontalBox();
		
		this.add(this.mainContainer);
	}
	
	/* importImages - reads all necessary images into memory
	 */
	private void importBadgeImages()
	{
<<<<<<< HEAD:src/gui/panels/ScreenPrintSetUp.java

		try 
		{
			this.logoImage = ImageEditor.resizeImage((ImageIO.read(new File("resources/templogo.png"))), 55, 55);
			//this.logoImage = (ImageIO.read(new File("resources/templogo.png")));

		} 
		catch (IOException e) {
=======
		try {
			this.logoImage = new Img("resources/logo.png");
		} catch (InvalidImgException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    try 
	    {      
	    	this.logoImage.setImage(ImageIO.read(new File("resources/logo.png")));
	    	this.logoImage.resizeImage(Scalr.Method.ULTRA_QUALITY, 30);
	    	this.pdfBadge = new PDJpeg(this.document, logoImage.getImage());
	    } 
	    catch (IOException e)
	    {
>>>>>>> origin/master:src/gui/display/print/PrintSetUpPanel.java
			System.out.println("Error - Unable to import badge image");
			e.printStackTrace();
		} 
		try 
		{
			this.pdfBadge = new PDJpeg(this.document, this.logoImage);
		} 
		catch (IOException e) {
			System.out.println("Error - Unable to import badge image");
			e.printStackTrace();
		}
		
	 
	}
	
	/* generatePDF - generates a PDF document for police evidence
	 */
	private PDDocument generatePDF() throws IOException{
		System.out.println("Creating PDF");
		// Create a document and add a page to it
		document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);
		
		//import and convert the badge image
		this.importBadgeImages();
		
		// Create a new font object selecting one of the PDF base fonts
		PDFont font = PDType1Font.HELVETICA_BOLD;

		// Start a new content stream which will "hold" the to be created content
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		
		//Initialize margins
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
		contentStream.drawString("19 Neal Ct, Plainville, CT, (860) 747-1616");
		contentStream.moveTextPositionByAmount(0, -12);
		contentStream.drawString("Plainville, CT");
		//this.pos = this.nextLine(pos, contentStream);
		contentStream.moveTextPositionByAmount(0, -12);
		contentStream.drawString("(860) 747-1616");
		contentStream.endText();
		
		//place the thumbnails on the screen
		this.displayImages(contentStream);

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
	
	private Point nextLine(Point p, PDPageContentStream contentStream)
	{
		p.setLocation(p.getX(), p.getY()-15);
		return p;
	}
	
	
	private void displayImages(PDPageContentStream contentStream) {
		
		BufferedImage tempImage = null;
		PDXObjectImage tempPDFImage = null;
		
		for(int i = 0; i < selectedThumbnails.size(); i++)
	    {
	    	try 
	    	{
	    		tempImage = selectedThumbnails.get(i).getImage();
	    		tempPDFImage = new PDJpeg(this.document, tempImage);
	    		//add the image to the pdf file
	    		contentStream.drawImage(tempPDFImage, 10, tempImage.getHeight()+(i*100));
	    	} 
	    	catch (IOException e) 
	    	{   
	    		System.out.println("error - file, " + selectedThumbnails.get(i).getFilePath() + ", could not be printed.");
	    		e.printStackTrace();
	    	}
	    }
		
	}

	/* initializeMargins - set up the margins to keep content on page 
	 */
	private void initializePDFTools(PDPage page) {
		PDRectangle mediabox = page.findMediaBox();
	    float margin = 72;
	    float width = mediabox.getWidth() - margin;
	    float startX = mediabox.getLowerLeftX() + margin;
	    float startY = mediabox.getUpperRightY() - margin;
	    
	    this.pos = new Point(0, 0);
	}

	//Use to insert an image of the pdf generated
	private void populateMainContainer() {
		
	}


	//select how many images per page you would like
	private void populateButtonsContainer() {
		// TODO Auto-generated method stub
		
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
