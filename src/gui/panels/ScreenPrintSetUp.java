// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenStart.java
package gui.panels;

import gui.FrameManager;
import gui.img.ThumbnailImg;

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

import pdftools.contentPosition;

public class ScreenPrintSetUp extends JPanel implements ActionListener, FocusListener
{
	private FrameManager manager;
	private ArrayList<ThumbnailImg> selectedThumbnails;
	private ScreenEdit currentScreen;
	private Box container;
	private Box widthContainer;
	private Box heightContainer;
	private JLabel widthLabel;
	private JLabel heightLabel;
	private int originalWidth;
	private int originalHeight;
	private Component mainContainer;
	private Box buttonsContainer;
	private BufferedImage logoImage;
	private PDXObjectImage pdfBadge;
	private PDDocument document;
	private Point pos; //replace with regular type point?
	
<<<<<<< Updated upstream
	public ScreenPrintSetUp(FrameManager manager, ArrayList<Thumbnail> selectedThumbnails) throws IOException
=======
	public ScreenPrintSetUp(FrameManager manager, ArrayList<ThumbnailImg> selectedThumbnails)
>>>>>>> Stashed changes
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
	private void importImages()
	{
		this.logoImage = null;
	    try 
	    {      
	    	this.logoImage = ImageIO.read(new File("resources/logo.png"));
	    	this.pdfBadge = new PDJpeg(this.document, logoImage);
	    } 
	    catch (IOException e)
	    {
			System.out.println("Error - Unable to import badge image");
			e.printStackTrace();
			return;
	    }
	    
	    
	}
	
	private PDDocument generatePDF() throws IOException{
		System.out.println("Creating PDF");
		// Create a document and add a page to it
		document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage( page );
		
		//import and convert the badge image
		this.importImages();
		
		// Create a new font object selecting one of the PDF base fonts
		PDFont font = PDType1Font.HELVETICA_BOLD;

		// Start a new content stream which will "hold" the to be created content
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		// Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
		contentStream.drawImage(pdfBadge, 300, 100);
		
		//Initialize margins
		this.initializePDFTools(page);
		
		//Open up the content stream for text insertion
		contentStream.beginText();
		contentStream.setFont( font, 12 );
		
		this.pos.setLocation(20, 20);
		contentStream.moveTextPositionByAmount(pos.x, pos.y);
		contentStream.drawString( "Plainville Police Department" );
		//contentStream.newLineAtOffset();
		contentStream.drawString("19 Neal Ct, Plainville, CT, (860) 747-1616");
		contentStream.endText();

		// Make sure that the content stream is closed:
		contentStream.close();

		// Save the results and ensure that the document is properly closed:
		try {
			document.save( "Hello World.pdf");
		} catch (COSVisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.close();
		
		return null;
		
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
