package gui;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class DrawRect extends JComponent
{
	private Point initPoint;
	private Point mousePoint;
	
	public DrawRect(Graphics graphics, Point initPoint, Point mousePoint) {
		this.initPoint = initPoint;
		this.mousePoint = mousePoint;
	}

	public void paint(Graphics g, Point initPoint, Point mousePoint)
	{
		super.paintComponent(g);
		g.fillRect(initPoint.x, initPoint.y, mousePoint.x-initPoint.x, mousePoint.y-initPoint.y);
	}
	

}
