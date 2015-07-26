// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ThumbnailImg.java

package gui.img;
import java.awt.*;
import javax.swing.*;
import exceptions.*;
 
public class ThumbnailImg extends BaseImg
{

	public ThumbnailImg(String filePath, int size) throws InvalidImgException 
	{
		super(filePath);
		super.resizeImage(size);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		super.setIcon(new ImageIcon(super.getImage()));
	}

}