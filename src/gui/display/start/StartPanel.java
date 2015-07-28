// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// StartPanel.java

package gui.display.start;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import org.imgscalr.*;

import exceptions.*;
import gui.*;
import gui.components.img.*;
import gui.display.*;
import gui.display.newcase.*;
import gui.display.print.PrintPanel;
import gui.display.print.PrintSetUpPanel;

public class StartPanel extends JPanel implements ActionListener {

	private FrameManager manager;
	private Box topContainer;
	private Box middleContainer;
	private Box bottomContainer;
	private Img logoImg;
	private Img bgImg;
	private JLabel titleLabel;
	private JLabel nameLabel;
	private JLabel creditLabel;
	private JButton newCaseButton;
	private JButton editCaseButton;
	private JButton settingsButton;

	public StartPanel(FrameManager manager) {
		this.manager = manager;
		this.topContainer = Box.createVerticalBox();
		this.middleContainer = Box.createHorizontalBox();
		this.bottomContainer = Box.createVerticalBox();
		this.populateTopContainer();
		this.populateMiddleContainer();
		this.populateBottomContainer();
		this.add(this.topContainer);
		this.add(this.middleContainer);
		this.add(this.bottomContainer);
	}

	/*
	 * actionPerformed - mandatory for any class implementing ActionListener,
	 * checks the source of the ActionEvent and executes the appropriate code e
	 * - the event in question 1. pushes NewCasePanel to the JFrame
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.newCaseButton) {
			this.manager.getMainWindow().pushPanel(new NewCasePanel(this.manager), "PEMS - Create New Case");
		} else if (e.getSource() == this.editCaseButton) {
			/*
			 * try { this.manager.getMainWindow().pushPanel(new
			 * EditCasePanel(this.manager), "PEMS - Edit Existing Case"); }
			 * catch (IOException e1) { e1.printStackTrace(); }
			 */
		} else if (e.getSource() == this.settingsButton) {
			this.manager.getMainWindow().pushPanel(new PrintPanel(this.manager, "CrimePhotos/"),
					"PEMS - PDF generator");
		}
	}

	/*
	 * paintComponent - override function used, in this case, to set a
	 * background image g - the current Graphics instance
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			this.bgImg = ComponentGenerator.generateImg("resources/background.png", CENTER_ALIGNMENT);
		} catch (InvalidImgException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return;
		}
		g.drawImage(this.bgImg.getImage(), 0, 0, null);
	}

	/*
	 * populateTopContainer - adds "logoImg", "titleLabel", and "nameLabel" to
	 * "topContainer"
	 */
	private void populateTopContainer() {
		this.titleLabel = ComponentGenerator.generateLabel("Police Evidence Management System",
				ComponentGenerator.TITLE_FONT, ComponentGenerator.TITLE_COLOR, CENTER_ALIGNMENT);
		this.nameLabel = ComponentGenerator.generateLabel(this.manager.getConfiguration().getDepartmentName(),
				ComponentGenerator.SUBTITLE_FONT, ComponentGenerator.SUBTITLE_COLOR, CENTER_ALIGNMENT);
		try {
			this.logoImg = ComponentGenerator.generateImg("resources/logo.png", CENTER_ALIGNMENT);
			this.logoImg.resizeImage(Scalr.Method.ULTRA_QUALITY, 200, 200);
			this.topContainer.add(this.logoImg);
		} catch (InvalidImgException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.topContainer.add(Box.createVerticalStrut(15));
		this.topContainer.add(this.titleLabel);
		this.topContainer.add(Box.createVerticalStrut(10));
		this.topContainer.add(this.nameLabel);
		this.topContainer.add(Box.createVerticalStrut(25));
	}

	/*
	 * populateMiddleContainer - adds "newCaseButton", "editCaseButton", and
	 * "settingsButton" to "middleContainer"
	 */
	private void populateMiddleContainer() {
		this.newCaseButton = ComponentGenerator.generateButton("New Case", this);
		this.editCaseButton = ComponentGenerator.generateButton("Edit Case", this);
		this.settingsButton = ComponentGenerator.generateButton("Settings", this);
		this.middleContainer.add(this.newCaseButton);
		this.middleContainer.add(Box.createHorizontalStrut(120));
		this.middleContainer.add(this.editCaseButton);
		this.middleContainer.add(Box.createHorizontalStrut(120));
		this.middleContainer.add(this.settingsButton);
	}

	/*
	 * populateBottomContainer - adds "creditLabel" to "bottomContainer"
	 */
	private void populateBottomContainer() {
		this.creditLabel = ComponentGenerator.generateLabel("Copyright 2015 \u00a9 Jacob Jones and Andrew Rottier",
				ComponentGenerator.SMALL_TEXT_FONT, ComponentGenerator.STANDARD_TEXT_COLOR, CENTER_ALIGNMENT);
		this.bottomContainer.add(Box.createVerticalStrut(5));
		this.bottomContainer.add(this.creditLabel);
	}

}