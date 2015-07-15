// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Thumbnail.java

import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class Thumbnail 
{

	private BufferedImage image;
	private Path location;
	
	public Thumbnail(BufferedImage image, Path location)
	{
		this.image = image;
		this.location = location;
	}
	
	public BufferedImage getImage()
	{
		return this.image;
	}
	
	public void setImage(BufferedImage image)
	{
		//this.
	}
	
}
