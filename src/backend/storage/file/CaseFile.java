// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// CaseFile.java

package backend.storage.file;
import java.nio.file.*;
import backend.exceptions.*;
import backend.storage.*;

/** Class used to represent a case evidence file read in from one of the managed local directories.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class CaseFile 
{
	
	private Case parentCase;
	private String filePath;
	private String fileName;
	private String fileExt;
	private String fileType;
	private int fileIndex;
	
	/** Initializes and populates instance fields.
	 * 
	 *  @param parentCase the instance of <code>Case</code> that this file is attached to
	 *  @param filePath <code>String</code> value containing the full file path associated with this file
	 *  @throws InvalidFileException if there is an error in reading this file into memory
	 */
	public CaseFile(Case parentCase, String filePath) throws InvalidFileException 
	{
		this.parentCase = parentCase;
		this.filePath = filePath;
		this.fileName = this.retrieveFileName();
		this.fileExt = this.retrieveFileExt();
		this.fileType = this.retrieveFileType();
		this.fileIndex = this.retrieveFileIndex();
	}
	
	/** Returns a <code>String</code> value containing the full file path associated with this file.
	 * 
	 *  @return <code>String</code> value containing the full file path associated with this file
	 */
	public String getFilePath()
	{
		return this.filePath;
	}
	
	/** Returns a <code>String</code> value containing the file name associated with this file.
	 * 
	 *  @return <code>String</code> value containing the file name associated with this file
	 */
	public String getFileName()
	{
		return this.fileName;
	}
	
	/** Returns a <code>String</code> value containing the file extension associated with this file.
	 * 
	 *  @return <code>String</code> value containing the file extension associated with this file
	 */
	public String getFileExt()
	{
		return this.fileExt;
	}
	
	/** Returns a <code>String</code> value containing the file type associated with this file.
	 * 
	 *  @return <code>String</code> value containing the file type associated with this file
	 */
	public String getFileType()
	{
		return this.fileType;
	}
	
	/** Returns an <code>int</code> value representing the index of this file within its parent case.
	 * 
	 *  @return <code>int</code> value representing the index of this file within its parent case
	 */
	public int getFileIndex()
	{
		return this.fileIndex;
	}
	
	/** Returns the instance of <code>Case</code> associated with this file.
	 * 
	 *  @return the instance of <code>Case</code> associated with this file
	 */
	protected Case getParentCase()
	{
		return this.parentCase;
	}
	
	/** Determines the name of this file.
	 * 
	 *  @return the file name, including the file extension, pulled from the full path
	 */
	private String retrieveFileName()
	{
		return Paths.get(this.filePath).getFileName().toString();
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
	
	/** Determines the type of this file.
	 * 
	 *  @return the file type pulled from the file extension
	 *  @throws InvalidFileException if the file type can not be determined, or the file is not a valid image (png or jpeg)
	 */
	private String retrieveFileType() throws InvalidFileException
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
			throw new InvalidFileException("Invalid file type");
		}
	}
	
	/** Determines the index of this file within its parent case.
	 * 
	 *  @return the file index pulled from the file name
	 *  @throws InvalidFileException if the name of the file is not properly formatted for use by PEMS
	 */
	private int retrieveFileIndex() throws InvalidFileException
	{
		if (this.fileName.indexOf('(') >= 0 && this.fileName.indexOf(')') >= 0)
		{
			if (this.fileName.indexOf('-') >= 0)
			{
				return Integer.parseInt(this.fileName.substring(this.fileName.indexOf('(') + 1, this.fileName.indexOf('-')));
			}
			else
			{
				return Integer.parseInt(this.fileName.substring(this.fileName.indexOf('(') + 1, this.fileName.indexOf(')')));
			}
		}
		else
		{
			throw new InvalidFileException("Invalid file name format");
		}
	}

}