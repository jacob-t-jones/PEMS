package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class DrawRect extends JComponent
{
	private Point initPoint;
	
	public DrawRect(Graphics graphics, Point initPoint) {
		this.initPoint = initPoint;
	}

	public void paint(Graphics g, Point initPoint)
	{
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillRect(initPoint.x, initPoint.y-75, 15, 5);
		g.fillRect(initPoint.x, initPoint.y-75, 5, 15);
	}
	
	public void removeComp(Graphics g){
		super.paintComponent(g);
		super.removeAll();
		g.clearRect(0, 0, getWidth(), getHeight());
	}

}
