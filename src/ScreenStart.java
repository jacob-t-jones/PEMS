// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// PanelStart.java

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
		this.instructions.setBounds(100, 100, 100, 100);
		this.add(this.instructions);
	}
	
}
