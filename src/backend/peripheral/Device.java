// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Device.java

package backend.peripheral;
import java.io.*;
import java.util.*;
import backend.exceptions.*;

/** Class used to represent a peripheral device detected by PEMS.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class Device 
{
	
	private File file;
	private ArrayList<PeripheralFile> files;

	/** Constructs an object representing an external device detected by this program.
	 * 
	 *  @param file The <code>File</code> object representing this peripheral device
	 */
	public Device(File file)
	{
		this.file = file;
		this.files = this.retrieveFiles(this.file, new ArrayList<PeripheralFile>());
	}
	
	/** Returns an <code>ArrayList</code> of <code>PeripheralFile</code> objects representing all of the potential evidence files detected on this device.
	 * 
	 *  @return <code>ArrayList</code> of <code>PeripheralFile</code> objects representing all of the potential evidence files detected on this device
	 */
	public ArrayList<PeripheralFile> getFiles()
	{
		return this.files;
	}
	
	/** Recursive method used to scan this device for potential evidence files, in the form of valid jpeg or png images.
	 * 
	 *  @param directory the directory within the device that is currently being scanned
	 *  @param files <code>ArrayList</code> of <code>PeripheralFile</code> objects representing all of the evidence files detected thus far
	 *  @return the same <code>ArrayList</code> that was passed in, plus any additional files detected within the passed in directory
	 */
	private ArrayList<PeripheralFile> retrieveFiles(File directory, ArrayList<PeripheralFile> files)
	{
		for (int i = 0; i < directory.listFiles().length; i++)
		{
			File currentFile = directory.listFiles()[i];
			if (currentFile.canRead() && !currentFile.isHidden())
			{
				if (currentFile.isDirectory())
				{
					this.retrieveFiles(currentFile, files);
				}
				else if (currentFile.getName().contains("."))
				{
					String currentExt = currentFile.getName().substring(currentFile.getName().indexOf('.')).toLowerCase();
					if (currentExt.equals(".png") || currentExt.equals(".jpg") || currentExt.equals(".jpeg"))
					{
						try 
						{
							files.add(new PeripheralFile(this, currentFile.getPath()));
						} 
						catch (InvalidFileException e) 
						{
							System.out.println(e.getMessage());
							e.printStackTrace();
						}
					}
				}
			}
		}
		return files;
	}
	
}