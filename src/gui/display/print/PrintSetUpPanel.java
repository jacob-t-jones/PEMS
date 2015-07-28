// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenStart.java
package gui.display.print;

import exceptions.InvalidImgException;
import gui.*;
import gui.components.img.Img;
import gui.display.FrameManager;
import gui.display.editimg.EditImgPanel;

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
import org.apache.pdfbox.pdfviewer.*;
import org.imgscalr.Scalr;

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
	private Img logoImage; /// was baseimg /buff
	//private Img logoImage;
	private PDXObjectImage pdfBadge;
	private PDDocument document;
	private Point pos; 
	private Img[][] content;

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
		
	    try 
	    {      
	    	this.logoImage = ComponentGenerator.generateImg("resources/logo.png", CENTER_ALIGNMENT); 
	    			//setImage(ImageIO.read(new File("resources/logo.png")));
	    	
	    	this.logoImage.resizeImage(Scalr.Method.ULTRA_QUALITY, 50);
	    	this.pdfBadge = new PDJpeg(this.document, logoImage.getImage());
	    } 
	    catch (IOException | InvalidImgException e)
	    {
			System.out.println("Error - Unable to import badge image");
			e.printStackTrace();
		} 
		try 
		{
			this.pdfBadge = new PDJpeg(this.document, this.logoImage.getImage());
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
		contentStream.drawString("19 Neal Ct");
		contentStream.moveTextPositionByAmount(0, -12);
		contentStream.drawString("Plainville, CT");
		//this.pos = this.nextLine(pos, contentStream);
		contentStream.moveTextPositionByAmount(0, -12);
		contentStream.drawString("(860) 747-1616");
		contentStream.endText();
		
		//place the thumbnails on the screen
		this.displayImages(contentStream);
		
		//decide which layout to use based on what is selected
		content = this.getFormat();
		
		
		drawTable(page, contentStream, 700, 100, content);
		
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
	

	/* getFormat - chooses how many images the user would like to print on one page
	 */
	private Img[][] getFormat() {
		if(selectedThumbnails.size() == 1){
				Img[][] content = {{null}} ;
		}
		else if(selectedThumbnails.size() == 2){
			try {
				Img[][] content = {{new Img(null)},  
								   {new Img(null)}};
			} catch (InvalidImgException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(selectedThumbnails.size() == 3){
			try {
				Img[][] content = {{new Img(null)},  
						   	       {new Img(null)},
							       {new Img(null)}};
			} catch (InvalidImgException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(selectedThumbnails.size() == 4){
			try {
				Img[][] content = {{new Img(null), new Img(null)},  
								   {new Img(null), new Img(null)}};
			} catch (InvalidImgException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(selectedThumbnails.size() == 8){
			try {
				Img[][] content = {{new Img(null), new Img(null)},  
						           {new Img(null), new Img(null)},
								   {new Img(null), new Img(null)},  
				                   {new Img(null), new Img(null)}};
			} catch (InvalidImgException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * @param page
	 * @param contentStream
	 * @param y the y-coordinate of the first row
	 * @param margin the padding on left and right of table
	 * @param content a 2d array containing the table data
	 * @throws IOException
	 */
	public  void drawTable(PDPage page, PDPageContentStream contentStream, float y, float margin, Img[][] content) throws IOException {
	    int rows = content.length;
	    int cols = content[0].length;
	    float rowHeight = 20f;
	    float tableWidth = page.findMediaBox().getWidth() - margin - margin;
	    float tableHeight = rowHeight * rows;
	    float colWidth = tableWidth/(float)cols;
	    float cellMargin=5f;

	    //draw the rows
	    float nexty = y ;
	    for (int i = 0; i <= rows; i++) {
	        contentStream.drawLine(margin, nexty, margin+tableWidth, nexty);
	        nexty-= rowHeight;
	    }

	    //draw the columns
	    float nextx = margin;
	    for (int i = 0; i <= cols; i++) {
	        contentStream.drawLine(nextx, y, nextx, y-tableHeight);
	        nextx += colWidth;
	    }

	    //now add the text        
	    //contentStream.setFont( PDType1Font.HELVETICA_BOLD , 12 );        

	    float textx = margin+cellMargin;
	    float texty = y-15;        
	    for(int i = 0; i < content.length; i++){
	        for(int j = 0 ; j < content[i].length; j++){
	            BufferedImage pic = content[i][j].getImage();
	            contentStream.beginText();
	            contentStream.moveTextPositionByAmount(textx,texty);
	            
	            PDXObjectImage tempPDFImage = null;
	            tempPDFImage = new PDJpeg(this.document, pic);
	    		//add the image to the pdf file
	    		contentStream.drawImage(tempPDFImage, textx, texty);
	            
	            //contentStream.drawImage(pic);
	            //System.out.println(i + "  coord  x:" + textx + " y:" + texty + "   content: "+ pic);
	            contentStream.endText();
	            textx += colWidth;
	        }
	        texty-=rowHeight;
	        textx = margin+cellMargin;
	    }
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
