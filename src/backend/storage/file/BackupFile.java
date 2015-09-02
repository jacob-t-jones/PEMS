// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// BackupFile.java

package backend.storage.file;
import backend.exceptions.*;
import backend.storage.*;

/** Subclass of <code>CaseFile</code>, used to represent an evidence file stored in the backup directory. By default this directory is located at <i>/cases/backup/</i>.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class BackupFile extends CaseFile
{
	
	private int versionIndex;

	/** Calls the constructor for <code>CaseFile</code> using the passed in values, initializes and populates <code>versionIndex</code>.
	 * 
	 *  @param parentCase the instance of <code>Case</code> that this file is attached to
	 *  @param filePath <code>String</code> value containing the full file path associated with this file
	 *  @throws InvalidFileException if there is an error in reading this file into memory
	 */
	public BackupFile(Case parentCase, String filePath) throws InvalidFileException
	{
		super(parentCase, filePath);
		this.versionIndex = this.retrieveVersionIndex();
	}
	
	/** Returns an <code>int</code> value representing the backup version index for this file.
	 * 
	 *  @return <code>int</code> value representing the backup version index for this file
	 */
	public int getVersionIndex()
	{
		return this.versionIndex;
	}
	
	/** Determines the version index of this backup file relative to all of the other saved and stored versions.
	 * 
	 *  @return the version index pulled from the file name
	 *  @throws InvalidFileException if the name of the file is not properly formatted for use by PEMS
	 */
	private int retrieveVersionIndex() throws InvalidFileException
	{
		if (super.getFileName().indexOf('(') >= 0 && super.getFileName().indexOf('-') >= 0 && super.getFileName().indexOf(')') >= 0)
		{
			return Integer.parseInt(super.getFileName().substring(super.getFileName().indexOf('-') + 1, super.getFileName().indexOf(')')));
		}
		else
		{
			throw new InvalidFileException("Invalid file name format");
		}
	}
	
}