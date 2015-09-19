// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// StorageManager.java

package backend;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import backend.storage.*;
import backend.storage.Case.*;
import gui.components.icon.*;

/** Global file management class used to handle all communication with and modification to the local file system.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class StorageManager 
{
	
	private ArrayList<Case> cases;
	
	/** Calls the method that populates the <code>cases</code> <code>ArrayList</code>.
	 */
	public StorageManager()
	{
		this.cases = this.retrieveCases();
	}
	
	/** Values representing the possible results of calling <code>createCase</code>.
	 */
	public enum CaseCreationResult
	{
		CASE_ALREADY_EXISTS, DIRECTORY_CREATION_FAILED, INVALID_CASE_NUMBER, SUCCESS
	}
	
	/** Returns a <code>Case</code> <code>ArrayList</code> populated with all of the cases currently managed by PEMS.
	 * 
	 *  @return <code>Case</code> <code>ArrayList</code> populated with all of the cases currently managed by PEMS 
	 */
	public ArrayList<Case> getCases()
	{
		return this.cases;
	}
	
	/** Accepts an <code>ArrayList</code> of <code>PeripheralIcon</code> objects, and attempts to add the files associated with them to the case specified in the parameters.
	 * 
	 *  @param delete <code>boolean</code> value indicating whether or not the original copies of the files to be added should be deleted
	 *  @param caseNum <code>String</code> containing the number of the case the files should be added to
	 *  @param selected <code>ArrayList</code> of <code>PeripheralIcon</code> objects representing the files to be added
	 *  @return <code>AddFileResult</code> enum type indicating whether or not the add files operation was successful
	 */
	public AddFileResult addFiles(boolean delete, String caseNum, ArrayList<PeripheralIcon> selected)
	{
		int caseIndex = 0;
		for (int i = 0; i < this.cases.size(); i++)
		{
			if (this.cases.get(i).getCaseNum().equalsIgnoreCase(caseNum))
			{
				caseIndex = i;
			}
		}
		for (int i = 0; i < selected.size(); i++)
		{
			AddFileResult result = this.cases.get(caseIndex).addFile(selected.get(i).getParentFile(), delete);
			if (result == AddFileResult.ADD_FAILED)
			{
				return AddFileResult.ADD_FAILED;
			}
		}
		return AddFileResult.SUCCESS;
	}
	
	/** Creates a case with the specified case number by making directories for it in the local file system. Also initializes a matching <code>Case</code> object and adds it to the <code>cases</code> <code>ArrayList</code>.
	 * 
	 *  @param caseNum the case number for the case being initialized
	 *  @return <code>CaseCreationResult</code> enum type indicating whether or not the case creation operation was successful
	 */
	public CaseCreationResult createCase(String caseNum) 
	{
		if (!this.validateCaseNum(caseNum))
		{
			return CaseCreationResult.INVALID_CASE_NUMBER;
		}
		else if (this.caseExists(caseNum))
		{
			return CaseCreationResult.CASE_ALREADY_EXISTS;
		}
		else
		{
			try 
			{
				Files.createDirectory(Paths.get("cases/backup/" + caseNum + "/"));
				Files.createDirectory(Paths.get("cases/live/" + caseNum + "/"));
				this.cases.add(new Case(caseNum));
			} 
			catch (IOException e) 
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
				return CaseCreationResult.DIRECTORY_CREATION_FAILED;
			}
			return CaseCreationResult.SUCCESS;
		}
	}

	/** Generates an <code>ArrayList</code> of cases by scanning the managed folders on the current device.
	 * 
	 *  @return an <code>ArrayList</code> of <code>Case</code> objects representing all of the cases stored in the managed directories (<i>/cases/backup/</i> and <i>/cases/live/</i>) 
	 */
	private ArrayList<Case> retrieveCases()
	{
		ArrayList<Case> cases = new ArrayList<Case>();
		File directory = new File("cases/backup/");
		for (int i = 0; i < directory.listFiles().length; i++)
		{
			File currentFile = directory.listFiles()[i];
			if (currentFile.canRead() && currentFile.canWrite() && currentFile.isDirectory() && !currentFile.isHidden())
			{
				cases.add(new Case(currentFile.getName()));
			}
		}
		return cases;
	}
	
	/** Returns a <code>boolean</code> value indicating whether or not a case with the passed in case number already exists.
	 * 
	 *  @param caseNum the case number in question
	 *  @return <code>true</code> if a case with the passed in case number already exists; <code>false</code> if otherwise
	 */
	private boolean caseExists(String caseNum)
	{
		for (int i = 0; i < this.cases.size(); i++)
		{
			if (this.cases.get(i).getCaseNum().equalsIgnoreCase(caseNum))
			{
				return true;
			}
		}
		return false;
	}
	
	/** Returns a <code>boolean</code> value indicating whether or not the passed in case number is valid.
	 * 
	 *  @param caseNum the case number in question
	 *  @return <code>true</code> if the passed in case number is valid; <code>false</code> if otherwise
	 */
	private boolean validateCaseNum(String caseNum)
	{
		if (caseNum.length() <= 0 || caseNum.length() > 10)
		{
			return false;
		}
		for (int i = 0; i < caseNum.length(); i++)
		{
			int charVal = (int)caseNum.charAt(i);
			if (charVal < 48 || charVal > 122 || (charVal > 57 && charVal < 65) || (charVal > 90 && charVal < 97))
			{
				return false;
			}
		}
		return true;
	}

}