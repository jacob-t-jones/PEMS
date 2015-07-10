// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// PanelStart.java

import java.awt.*;
import javax.swing.*;

public class ScreenStart extends JPanel
{
	
	private FrameManager manager;
	private JLabel instructions;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int)screenSize.getWidth();
	int height = (int)screenSize.getHeight();

	
	public ScreenStart(FrameManager manager)
	{
		this.manager = manager;
		this.instructions = new JLabel();
		this.instructions.setText("Police Evidence Management System");
		this.instructions.setBounds(width/2, height/2, width, height);
		this.add(this.instructions);
	}
	
}
