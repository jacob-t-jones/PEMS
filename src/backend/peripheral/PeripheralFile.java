// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// PeripheralFile.java

package backend.peripheral;
import java.io.*;
import java.nio.file.*;
import backend.exceptions.*;

/** Class representing an evidence image file detected on a connected external device.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class PeripheralFile 
{
	
	private Device parentDevice;
	private String filePath;
	private String fileExt;
	
	/** Initializes and populates instance fields.
	 * 
	 *  @param parentDevice <code>Device</code> object representing the peripheral device this file is located on
	 *  @param filePath the full path for this file
	 *  @throws InvalidFileException if there is an error in reading this file into memory
	 *  @throws NullPointerException if any parameters are null
	 */
	public PeripheralFile(Device parentDevice, String filePath) throws InvalidFileException
	{
		if (parentDevice == null || filePath == null)
		{
			throw new NullPointerException();
		}
		this.parentDevice = parentDevice;
		this.filePath = filePath;
		this.fileExt = this.retrieveFileExt();
	}
	
	/** Returns a <code>String</code> containing the path for this file.
	 * 
	 *  @return <code>String</code> containing the path for this file.
	 */
	public String getFilePath()
	{
		return this.filePath;
	}
	
	/** Returns a <code>String</code> value containing the file extension associated with this file.
	 * 
	 *  @return <code>String</code> value containing the file extension associated with this file
	 */
	public String getFileExt()
	{
		return this.fileExt;
	}
	
	/** Deletes this file from the peripheral device it was found on.
	 */
	public void deleteFile() 
	{
		try 
		{
			Files.delete(Paths.get(this.filePath));
			this.parentDevice.getFiles().remove(this);
		}
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/** Determines the extension of this file.
	 * 
	 *  @return the file extension, including the preceding period, pulled from the full path
	 *  @throws InvalidFileException if the file does not have an extension
	 */
	private String retrieveFileExt() throws InvalidFileException
	{
		if (this.filePath.indexOf('.') >= 0)
		{
			return this.filePath.substring(this.filePath.indexOf('.'));
		}
		else
		{
			throw new InvalidFileException("Unable to determine file extension");
		}
	}

}