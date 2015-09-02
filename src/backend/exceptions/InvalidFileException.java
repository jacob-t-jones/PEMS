// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// InvalidFileException.java

package backend.exceptions;

/** Custom <case>exception</case> thrown when there is an issue with reading cases from the file system.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class InvalidFileException extends Exception 
{

	/** Used when this <code>exception</code> is thrown by itself.
	 * 
	 *  @param message the message provided when this <code>exception</code> was thrown
	 */
	public InvalidFileException(String message) 
	{
		super("InvalidFileException - " + message);
	}

	/** Used when this <code>exception</code> is thrown as a result of another <code>exception</code> being thrown.
	 * 
	 *  @param message the message provided when this <code>exception</code> was thrown
	 *  @param cause <code>Throwable</code> object represeting the <code>exception</code> that was originally thrown
	 */
	public InvalidFileException(String message, Throwable cause) 
	{
		super(message, cause);
	}

}