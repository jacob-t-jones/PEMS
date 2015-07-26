// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// BaseImg.java

package gui.img;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.*;
import javax.imageio.*;
import javax.swing.*;
import org.imgscalr.*;
import exceptions.*;

public class BaseImg extends JLabel
{
	
	private BufferedImage image;
	private String filePath;
	private String fileName;
	private String fileExt;
	private String fileType;
	
	public BaseImg(String filePath) throws InvalidImgException
	{
		this.filePath = filePath;
		this.image = retrieveImage();
		this.fileName = retrieveFileName();
		this.fileExt = retrieveFileExt();
		this.fileType = retrieveFileType();
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	public BufferedImage getImage()
	{
		return this.image;
	}
	
	public String getFilePath()
	{
		return this.filePath;
	}
	
	public String getFileName()
	{
		return this.fileName;
	}
	
	public String getFileExt()
	{
		return this.fileExt;
	}
	
	public String getFileType()
	{
		return this.fileType;
	}
	
	public void addPadding(int size, int red, int green, int blue)
	{
		this.image = Scalr.pad(image, size, new Color(red, green, blue));
	}

	public void applyAntiAliasing()
	{
		this.image = Scalr.apply(image, Scalr.OP_ANTIALIAS);
	}
	
	public void brightenImage()
	{
		this.image = Scalr.apply(image, Scalr.OP_BRIGHTER);
	}

	public void cropImage(int x, int y, int width, int height)
	{
		this.image = Scalr.crop(image, x, y, width, height, Scalr.OP_ANTIALIAS);
	}
	
	public void darkenImage()
	{
		this.image = Scalr.apply(image, Scalr.OP_DARKER);
	}
	
	public void resizeImage(int size)
	{
		this.image = Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, size, Scalr.OP_ANTIALIAS);
	}

	public void resizeImage(int width, int height)
	{
		this.image = Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, width, height, Scalr.OP_ANTIALIAS);
	}

	public void rotateRight90()
	{
		this.image = Scalr.rotate(image, Scalr.Rotation.CW_90);
	}
	
	public void rotateRight180()
	{
		this.image = Scalr.rotate(image, Scalr.Rotation.CW_180);
	}

	public void rotateRight270()
	{
		this.image = Scalr.rotate(image, Scalr.Rotation.CW_270);
	}
	
	public void toGrayscale()
	{
		this.image = Scalr.apply(image, Scalr.OP_GRAYSCALE);
	}
	
	private BufferedImage retrieveImage() throws InvalidImgException
	{    
		try
		{
			return ImageIO.read(new File(this.filePath));
		}
		catch (IOException e)
		{
			throw new InvalidImgException("Unable to read image into memory", e);
		}
	}
	
	private String retrieveFileName()
	{
		return Paths.get(this.filePath).getFileName().toString();
	}
	
	private String retrieveFileExt() throws InvalidImgException
	{
		if (this.filePath.contains("."))
		{
			return this.filePath.substring(this.filePath.indexOf('.'), this.filePath.length());
		}
		else
		{
			throw new InvalidImgException("Unable to determine file extension");
		}
	}
	
	private String retrieveFileType() throws InvalidImgException
	{
		if (this.fileExt.equalsIgnoreCase(".png"))
		{
			return "png";
		}
		else if (this.fileExt.equalsIgnoreCase(".jpg") || this.fileExt.equalsIgnoreCase(".jpeg"))
		{
			return "jpg";
		}
		else 
		{
			throw new InvalidImgException("Invalid image file type");
		}
	}
	
}
