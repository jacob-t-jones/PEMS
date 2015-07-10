// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ScreenStart.java

import java.awt.*;
import javax.swing.*;

public class ScreenStart extends JPanel
{
	
	private FrameManager manager;
	private JLabel instructions;
	
	public ScreenStart(FrameManager manager)
	{
		this.manager = manager;
		this.instructions = new JLabel();
		this.instructions.setText("Police Evidence Management System");
		this.instructions.setBounds(manager.widthToPixels(50), manager.heightToPixels(10), 100, 100);
		this.add(this.instructions);
	}
	
}
