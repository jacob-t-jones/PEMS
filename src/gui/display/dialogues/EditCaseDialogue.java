// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// EditCaseDialogue.java

package gui.display.dialogues;
import javax.swing.*;
import gui.display.*;

public class EditCaseDialogue extends JPanel
{
	
	private FrameManager manager;
	private String caseNum;

	public EditCaseDialogue(FrameManager manager, String caseNum)
	{
		this.manager = manager;
		this.caseNum = caseNum;
		this.revalidate();
		this.repaint();
	}
	
}