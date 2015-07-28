// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Window.java

package gui.window;

import java.awt.*;
import javax.swing.*;
import gui.display.*;

public class Window extends JFrame {

	private FrameManager manager;

	public Window(String title, FrameManager manager, JPanel panel) {
		super(title);
		super.getContentPane().add(panel);
		super.pack();
		this.manager = manager;
	}

	/*
	 * setBounds - resizes the JFrame using the width and height percentages
	 * specified in the parameters, positions it at the center of the screen
	 * width - the new width of the JFrame height - the new height of the JFrame
	 */
	public void setBounds(int width, int height) {
		super.setBounds(this.widthToPixels(width) / 2, this.heightToPixels(height) / 2, this.widthToPixels(width),
				this.heightToPixels(height));
	}

	/*
	 * setMaximized - maximizes the JFrame
	 */
	public void setMaximized() {
		super.setResizable(true);
		super.setExtendedState(MAXIMIZED_BOTH);
	}

	/*
	 * getManager - returns the instance of FrameManager associated with this
	 * JFrame
	 */
	protected FrameManager getManager() {
		return this.manager;
	}

	/*
	 * widthToPixels - converts a width percentage value to its equivalent pixel
	 * value percent - the value to convert to pixels
	 */
	private int widthToPixels(double percent) {
		return (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * (percent / 100.0));
	}

	/*
	 * heightToPixels - converts a height percentage value to its equivalent
	 * pixel value percent - the value to convert to pixels
	 */
	private int heightToPixels(double percent) {
		return (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * (percent / 100.0));
	}

}
