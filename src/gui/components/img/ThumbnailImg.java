// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// ThumbnailImg.java

package gui.components.img;

import org.imgscalr.*;
import exceptions.*;

public class ThumbnailImg extends Img {

	public ThumbnailImg(String filePath, int size) throws InvalidImgException {
		super(filePath);
		this.resizeImage(size);
	}

	/*
	 * resizeImage - resizes "image" to the specified size using the SPEED
	 * setting size - the new size of the image
	 */
	public void resizeImage(int size) {
		super.resizeImage(Scalr.Method.SPEED, size);
	}

	/*
	 * resizeImage - resizes "image" to the specified width and height using the
	 * SPEED setting width - the new width of the image height - the new height
	 * of the image
	 */
	public void resizeImage(int width, int height) {
		super.resizeImage(Scalr.Method.SPEED, width, height);
	}

}