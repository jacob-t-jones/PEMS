package gui.panels;

import gui.FrameManager;
import gui.Thumbnail;

import java.awt.Component;
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
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class ScreenPrintSetUp extends JPanel implements ActionListener, FocusListener
{
	private FrameManager manager;
	private ArrayList<Thumbnail> selectedThumbnails;
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
	
	public ScreenPrintSetUp(FrameManager manager, ArrayList<Thumbnail> selectedThumbnails) throws IOException
	{
		this.manager = manager;
		this.selectedThumbnails = selectedThumbnails;
		
		this.generatePDF();
		this.mainContainer = Box.createVerticalBox();
		this.buttonsContainer = Box.createHorizontalBox();
		this.populateButtonsContainer();
		this.populateMainContainer();
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
		contentStream.beginText();
		contentStream.setFont( font, 12 );
		
		contentStream.moveTextPositionByAmount( 100, 700 );
		contentStream.drawString( "Plainville Police Department" );
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
	
	
	
	private void populateMainContainer() {
		// TODO Auto-generated method stub
		
	}



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
