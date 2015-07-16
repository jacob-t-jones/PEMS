package gui.panels;
// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenStart.java

import gui.ComponentGenerator;
import gui.FrameManager;
import gui.Thumbnail;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import tools.ImageEditor;

public class ScreenEdit extends JPanel{
	
	private FrameManager manager;
	
	private ImageEditor imgEditor;
	private ArrayList<JLabel> selectedLabels;
	private ArrayList<JLabel> labels;
	private String directoryName;
	private Box selBox;
	private Box stageBox;
	private Box buttonsBox;
	private JButton nextButton;
	private JButton prevButton;
	
	private int selectedImagePlace;
	private JLabel displayLabel;
	private ArrayList<JLabel> selected;
	
	public ScreenEdit(FrameManager manager, ArrayList<Thumbnail> labels, ArrayList<JLabel> selectedLabels)
	{
		this.manager = manager;
		this.imgEditor = new ImageEditor();
		this.directoryName = "/Users/andrewrottier/Documents/Pictures/";
		this.selectedImagePlace = 0;
		this.selectedLabels = selectedLabels;
		
		this.stageBox = Box.createVerticalBox();
		this.selBox = Box.createHorizontalBox();
		//this.selBox.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.initializeSelectedImages();
		
		this.addActions();
		
		this.manager.setResizable(true);
		this.manager.maximizeFrame();
	}
	
	private void initializeSelectedImages(){
		//this.constructLabel("Click an image to edit it");
		this.displaySelectedImages();
	}
		
	private void displaySelectedImages()
	{
		//add selected images to the screen
		selectedImagePlace = 0;
			
		for(int i = 0; i < 15; i++){
			try
			{
				this.selBox.setAlignmentX(CENTER_ALIGNMENT);
				selBox.add(this.selected.get(this.selectedImagePlace));
				selectedImagePlace++;
			}
			catch(Exception e){} 
			
		}
		//this.add(this.selBox);
		stageBox.add(this.selBox);
		this.add(this.stageBox);
		revalidate();
		repaint();
		return;
	}
		
	
	
	/* addActions - turns each picture into a button
	 */
	private void addActions(){
		
		for(int i = 0; i < selectedLabels.size(); i++)
		{
			final JLabel currentLabel = this.selectedLabels.get(i);
			currentLabel.addMouseListener(new MouseListener()
			{
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) 
				{
					//label needs to be linked to an image..
					
				}

				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(java.awt.event.MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(java.awt.event.MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(java.awt.event.MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
		}
	}

	
	

}
