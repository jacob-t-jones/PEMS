// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// InvalidImgException.java

package exceptions;

public class InvalidImgException extends Exception {

	public InvalidImgException(String message) {
		super("InvalidImgException - " + message);
	}

	public InvalidImgException(String message, Throwable cause) {
		super(message, cause);
	}

}
