// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// RegularImg.java

package gui.img;
import java.awt.*;
import exceptions.*;
 
public class RegularImg extends BaseImg
{

	public RegularImg(String filePath) throws InvalidImgException 
	{
		super(filePath);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(super.getImage(), 0, 0, null);
	}

}
