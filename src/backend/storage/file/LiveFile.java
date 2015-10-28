// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// LiveFile.java

package backend.storage.file;
import java.io.*;
import java.nio.file.*;

import javax.imageio.*;

import backend.exceptions.*;
import backend.storage.*;
import backend.storage.file.img.*;

/** Subclass of <code>CaseFile</code>, used to represent an evidence file stored in the live directory. By default this directory is located at <i>/cases/live/</i>.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class LiveFile extends CaseFile
{
	
	private boolean saved;
	
	/** Calls the constructor for <code>CaseFile</code> using the passed in values, initializes and populates instance fields.
	 * 
	 *  @param parentCase the instance of <code>Case</code> that this file is attached to
	 *  @param filePath <code>String</code> value containing the full file path associated with this file
	 *  @throws InvalidFileException if there is an error in reading this file into memory
	 */
	public LiveFile(Case parentCase, String filePath) throws InvalidFileException
	{
		super(parentCase, filePath);
		this.saved = true;
	}
	
	/** Values representing the possible results of calling <code>deleteFile</code>.
	 */
	public enum DeleteFileResult
	{
		DELETION_FAILED, SUCCESS
	}
	
	/** Values representing the possible results of calling <code>saveFile</code>.
	 */
	public enum SaveFileResult
	{
		SAVE_FAILED, SUCCESS
	}
	
	/** Returns an <code>Img</code> object representing the image stored within this file.
	 * 
	 *  @return <code>Img</code> object representing the image stored within this file
	 *  @throws InvalidFileException if there is an error reading the image into memory 
	 */
	public Img getImg() throws InvalidFileException
	{
		return new Img(this);
	}
	
	/** Returns a <code>boolean</code> value indicating whether or not this file has been saved since it was last modified.
	 * 
	 *  @return <code>true</code> if this file has been saved since it was last modified, <code>false</code> if otherwise
	 */
	public boolean getSaved()
	{
		return this.saved;
	}
	
	/** Sets the value within the <code>saved</code> instance field.
	 * 
	 *  @param saved the <code>boolean</code> value to set <code>saved</code> to
	 */
	public void setSaved(boolean saved)
	{
		this.saved = saved;
	}
	
	/** Deletes this file from its location in <i>/cases/live/</i>. Retains all previously saved versions stored in <i>/cases/backup/</i>.
	 * 
	 *  @return <code>DeleteFileResult</code> enum type indicating whether or not the delete operation was successful
	 */
	public DeleteFileResult deleteFile() 
	{
		try 
		{
			Files.delete(Paths.get(super.getFilePath()));
			super.getParentCase().removeFile(this);
		}
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			return DeleteFileResult.DELETION_FAILED;
		}
		return DeleteFileResult.SUCCESS;
	}

	/** Saves this file by overwriting the version stored in <i>/cases/live/</i> and adding to the versions stored in <i>/cases/backup/</i>.
	 * 
	 *  @param img <code>Img</code> object containing the image that will be saved within this file
	 *  @return <code>SaveFileResult</code> enum type indicating whether or not the save operation was successful
	 */
	public SaveFileResult saveFile(Img img)
	{
		try 
		{
			ImageIO.write(img.getImage(), this.getFileType(), new File("cases/live/" + this.getParentCase().getCaseNum() + "/" + this.getParentCase().getCaseNum() + " (" + this.getFileIndex() + ")" + this.getFileExt()));
			ImageIO.write(img.getImage(), this.getFileType(), new File("cases/backup/" + this.getParentCase().getCaseNum() + "/" + this.getParentCase().getCaseNum() + " (" + this.getFileIndex() + "-" + this.getParentCase().getCurrentVersionIndex(this.getFileIndex()) + ")" + this.getFileExt()));
			this.getParentCase().refreshFiles();
		} 
		catch (IOException e) 
		{
		    System.out.println(e.getMessage());
		    e.printStackTrace();
		    return SaveFileResult.SAVE_FAILED;
		}
		this.saved = true;
		return SaveFileResult.SUCCESS;
	}
	
}