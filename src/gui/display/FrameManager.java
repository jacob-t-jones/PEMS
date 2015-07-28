// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// FrameManager.java

package gui.display;

import javax.swing.*;
import gui.display.start.*;
import gui.window.*;
import tools.*;

public class FrameManager {

	private Config configuration;
	private FileHandler fileHandler;
	private MainWindow mainWindow;
	private DialogueWindow dialogueWindow;

	public FrameManager() {
		this.configuration = new Config();
		
		this.fileHandler = new FileHandler();
		
		this.mainWindow = new MainWindow("PEMS (Police Evidence Management System) Version 0.1", this,
				new StartPanel(this));
		
	}

	/*
	 * getConfiguration - returns the global instance of the Config class
	 */
	public Config getConfiguration() {
		return this.configuration;
	}

	/*
	 * getFileHandler - returns the global instance of the FileHandler class
	 */
	public FileHandler getFileHandler() {
		return this.fileHandler;
	}

	/*
	 * getMainWindow - returns the global instance of MainWindow, the primary
	 * JFrame used by the application
	 */
	public MainWindow getMainWindow() {
		return this.mainWindow;
	}

	/*
	 * openDialogue - initializes a new instance of DialogueWindow based on the
	 * passed in parameters title - the title of the dialogue panel - the panel
	 * to display in the dialogue width - the width of the dialogue height - the
	 * height of the dialogue
	 */
	public void openDialogue(String title, JPanel panel, int width, int height) {
		this.dialogueWindow = new DialogueWindow(title, this, panel, width, height);
	}

	/*
	 * closeDialogue - hides and disposes of the global instance of
	 * DialogueWindow
	 */
	public void closeDialogue() {
		this.dialogueWindow.setVisible(false);
		this.dialogueWindow.dispose();
	}

}