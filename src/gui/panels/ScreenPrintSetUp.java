package gui.panels;

import gui.FrameManager;
import gui.Thumbnail;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	
	public ScreenPrintSetUp(FrameManager manager, ArrayList<Thumbnail> selectedThumbnails)
	{
		this.manager = manager;
		this.selectedThumbnails = selectedThumbnails;
		this.mainContainer = Box.createVerticalBox();
		this.buttonsContainer = Box.createHorizontalBox();
		this.populateButtonsContainer();
		this.populateMainContainer();
		this.add(this.mainContainer);
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
