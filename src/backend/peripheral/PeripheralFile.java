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
	
	/** Initializes and populates instance fields.
	 * 
	 *  @param parentDevice <code>Device</code> object representing the peripheral device this file is located on
	 *  @param filePath the full path for this file
	 *  @throws InvalidFileException if there is an error in reading this file into memory
	 */
	public PeripheralFile(Device parentDevice, String filePath) throws InvalidFileException
	{
		this.parentDevice = parentDevice;
		this.filePath = filePath;
	}
	
	/** Returns a <code>String</code> containing the path for this file.
	 * 
	 *  @return <code>String</code> containing the path for this file.
	 */
	public String getFilePath()
	{
		return this.filePath;
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

}