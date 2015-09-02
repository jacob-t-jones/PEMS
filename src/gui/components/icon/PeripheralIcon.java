// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// PeripheralIcon.java

package gui.components.icon;
import org.imgscalr.*;
import backend.exceptions.*;
import backend.peripheral.*;

/** Subclass of <code>ImgIcon</code> modified for use with the <code>PeripheralFile</code> class.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class PeripheralIcon extends ImgIcon
{
	
	private PeripheralFile parentFile;

	/** Calls the constructor for the parent class, populates instance fields.
	 * 
	 *  @param parentFile the instance of <code>PeripheralFile</code> that this icon is associated with
	 *  @param size the size that this icon should be shrunk down to
	 *  @throws InvalidFileException if the image contained within this component can not be read into memory
	 */
	public PeripheralIcon(PeripheralFile parentFile, int size) throws InvalidFileException 
	{
		super(parentFile.getFilePath(), Scalr.Method.SPEED, size);
		this.parentFile = parentFile;
	}
	
	/** Returns the <code>PeripheralFile</code> object associated with this class.
	 * 
	 *  @return the <code>PeripheralFile</code> object associated with this class
	 */
	public PeripheralFile getParentFile()
	{
		return this.parentFile;
	}
	
}
