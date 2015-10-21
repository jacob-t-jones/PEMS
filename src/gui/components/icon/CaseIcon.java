// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// CaseIcon.java

package gui.components.icon;
import org.imgscalr.*;
import backend.exceptions.*;
import backend.storage.file.*;

/** Subclass of <code>ImgIcon</code> modified for use with the <code>LiveFile</code> class.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class CaseIcon extends ImgIcon
{
	
	private LiveFile parentFile;
	
	/** Calls the constructor for the parent class, populates instance fields.
	 * 
	 *  @param parentFile the instance of <code>LiveFile</code> that this icon is associated with
	 *  @throws InvalidFileException if the image contained within this component can not be read into memory
	 */
	public CaseIcon(LiveFile parentFile) throws InvalidFileException 
	{
		super(parentFile.getFilePath());
		this.parentFile = parentFile;
	}
	
	/** Calls the constructor for the parent class, populates instance fields.
	 * 
	 *  @param parentFile the instance of <code>LiveFile</code> that this icon is associated with
	 *  @param size the size that this icon should be shrunk down to
	 *  @throws InvalidFileException if the image contained within this component can not be read into memory
	 */
	public CaseIcon(LiveFile parentFile, int size) throws InvalidFileException 
	{
		super(parentFile.getFilePath(), Scalr.Method.SPEED, size);
		this.parentFile = parentFile;
	}
	
	/** Returns the <code>LiveFile</code> object associated with this class.
	 * 
	 *  @return the <code>LiveFile</code> object associated with this class
	 */
	public LiveFile getParentFile()
	{
		return this.parentFile;
	}
	
}