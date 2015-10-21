// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Case.java

package backend.storage;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import backend.exceptions.*;
import backend.peripheral.*;
import backend.storage.file.*;
import gui.components.icon.*;

/** Class used to represent a case read in from the managed directories on the local file system.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class Case 
{
	
	private String caseNum;
	private ArrayList<BackupFile> backupFiles;
	private ArrayList<LiveFile> liveFiles;
	
	/** Initializes and populates instance fields.
	 * 
	 *  @param caseNum the number of the case being constructed
	 *  @throws NullPointerException if the parameter is null
	 */
	public Case(String caseNum)
	{
		if (caseNum == null)
		{
			throw new NullPointerException();
		}
		this.caseNum = caseNum;
		this.backupFiles = this.retrieveBackupFiles();
		this.liveFiles = this.retrieveLiveFiles();
	}
	
	/** Values representing the possible results of calling <code>addFile</code>.
	 */
	public enum AddFileResult
	{
		ADD_FAILED, SUCCESS
	}
	
	/** Returns the case number associated with this case, formatted as a <code>String</code>.
	 * 
	 *  @return the case number associated with this case, formatted as a <code>String</code>
	 */
	public String getCaseNum()
	{
		return this.caseNum;
	}

	/** Converts all of the <code>LiveFile</code> objects associated with this class to <code>CaseIcon</code> objects.
	 * 
	 *  @param size the size to shrink the generated icons down to
	 *  @param mouse the <code>MouseListener</code> for the generated icons
	 *  @param alignmentX the horizontal alignment of the generated icons
	 *  @return an <code>ArrayList</code> of <code>CaseIcon</code> objects generated using <code>liveFiles</code>
	 *  @throws NullPointerException if any parameters are null
	 *  @throws IllegalArgumentException if <code>size</code> is zero or negative
	 */
	public ArrayList<CaseIcon> getCaseIcons(int size, MouseListener mouse, float alignmentX)
	{
		if (mouse == null)
		{
			throw new NullPointerException();
		}
		if (size <= 0)
		{
			throw new IllegalArgumentException();
		}
		ArrayList<CaseIcon> caseIcons = new ArrayList<CaseIcon>();
		for (int i = 0; i < this.liveFiles.size(); i++)
		{
			try 
			{
				CaseIcon currentIcon = new CaseIcon(this.liveFiles.get(i), size);
				currentIcon.addMouseListener(mouse);
				currentIcon.setAlignmentX(alignmentX);
				caseIcons.add(currentIcon);
			} 
			catch (InvalidFileException e) 
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return caseIcons;
	}
	
	/** Returns the highest file index within this case in order to determine what index to begin with when importing new evidence files.
	 * 
	 *  @return <code>int</code> value representing the next file index in line
	 */
	public int getCurrentFileIndex()
	{
		int index = 0;
		for (int i = 0; i < this.backupFiles.size(); i++)
		{
			if (this.backupFiles.get(i).getFileIndex() >= index)
			{
				index = this.backupFiles.get(i).getFileIndex();
			}
		}
		return index + 1;
	}
	
	/** Returns the highest version index associated with a given file in order to determine what index to begin with when saving an edited file.
	 *  
	 *  @param fileIndex the index within this case of the file being looked at
	 *  @return <code>int</code> value representing the next version index in line for the chosen file
	 *  @throws IllegalArgumentException if the specified index is negative
	 */
	public int getCurrentVersionIndex(int fileIndex)
	{
		if (fileIndex < 0)
		{
			throw new IllegalArgumentException();
		}
		int index = -1;
		for (int i = 0; i < this.backupFiles.size(); i++)
		{
			if (this.backupFiles.get(i).getFileIndex() == fileIndex)
			{
				if (this.backupFiles.get(i).getVersionIndex() >= index)
				{
					index = this.backupFiles.get(i).getVersionIndex();
				}
			}
		}
		return index + 1;
	}
	
	/** Adds the <code>PeripheralFile</code> object specified in the parameters to this case by copying said file to the directories managed by PEMS, and adding it to the global <code>ArrayLists</code>.
	 * 
	 *  @param file the <code>PeripheralFile</code> object to add to this case
	 *  @param delete <code>boolean</code> value indicating whether or not the original copy of the file should be deleted
	 *  @return <code>AddFileResult</code> enum type indicating whether or not the add file operation was successful
	 *  @throws NullPointerException if any parameters are null
	 */
	public AddFileResult addFile(PeripheralFile file, boolean delete)
	{
		if (file == null)
		{
			throw new NullPointerException();
		}
		Path currentPath = Paths.get(file.getFilePath());
		Path livePath = Paths.get("cases/live/" + this.caseNum + "/" + this.caseNum + " (" + this.getCurrentFileIndex() + ")" + file.getFileExt());
		Path backupPath = Paths.get("cases/backup/" + this.caseNum + "/" + this.caseNum + " (" + this.getCurrentFileIndex() + "-" + 0 + ")" + file.getFileExt());
		try
		{
			Files.copy(currentPath, livePath, StandardCopyOption.REPLACE_EXISTING);
			Files.copy(currentPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
			this.liveFiles.add(new LiveFile(this, livePath.toString()));
			this.backupFiles.add(new BackupFile(this, backupPath.toString()));
			if (delete)
			{
				file.deleteFile();
			}
		} 
		catch (IOException | InvalidFileException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			return AddFileResult.ADD_FAILED;
		}
		return AddFileResult.SUCCESS;
	}
	
	/** Removes the instance of <code>LiveFile</code> passed in as a parameter from the <code>livesFiles</code> <code>ArrayList</code>.
	 * 
	 *  @param file the file in question
	 *  @throws NullPointerException if the parameter is null
	 */
	public void removeFile(LiveFile file)
	{
		if (file == null)
		{
			throw new NullPointerException();
		}
		this.liveFiles.remove(file);
	}
	
	/** Refreshes the <code>backupFiles</code> and <code>liveFiles</code> <code>ArrayLists</code> by once again scanning the directories managed by PEMS.
	 */
	public void refreshFiles()
	{
		this.backupFiles = this.retrieveBackupFiles();
		this.liveFiles = this.retrieveLiveFiles();
	}
	
	/** Reads the backup files for this case from the local file system.
	 * 
	 *  @return an <code>ArrayList</code> of <code>BackupFile</code> objects representing all of the evidence files stored in the backup directory for this case
	 */
	private ArrayList<BackupFile> retrieveBackupFiles()
	{
		ArrayList<BackupFile> backupFiles = new ArrayList<BackupFile>();
		File currentDirectory = new File("cases/backup/" + this.caseNum + "/");
		for (int i = 0; i < currentDirectory.listFiles().length; i++)
		{
			if (this.validateFile(currentDirectory.listFiles()[i]))
			{
				try 
				{
					backupFiles.add(new BackupFile(this, currentDirectory.listFiles()[i].getPath()));
				} 
				catch (InvalidFileException e) 
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return backupFiles;
	}
	
	/** Reads the live files for this case from the local file system.
	 * 
	 *  @return an <code>ArrayList</code> of <code>LiveFile</code> objects representing all of the evidence files stored in the live directory for this case
	 */
	private ArrayList<LiveFile> retrieveLiveFiles()
	{
		ArrayList<LiveFile> liveFiles = new ArrayList<LiveFile>();
		File currentDirectory = new File("cases/live/" + this.caseNum + "/");
		for (int i = 0; i < currentDirectory.listFiles().length; i++)
		{			
			if (this.validateFile(currentDirectory.listFiles()[i]))
			{
				try 
				{
					liveFiles.add(new LiveFile(this, currentDirectory.listFiles()[i].getPath()));
				} 
				catch (InvalidFileException e) 
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return liveFiles;
	}
	
	/** Returns a <code>boolean</code> value indicating whether or not the passed in <code>File</code> object can be added to this case.
	 * 
	 *  @param file the <code>File</code> in question
	 *  @return <code>true</code> if the passed in <code>File</code> is valid; <code>false</code> if otherwise
	 */
	private boolean validateFile(File file)
	{
		boolean valid = false;
		if (file.canRead() && file.canWrite() && !file.isHidden() && !file.isDirectory())
		{
			String fileName = file.getName();
			if (fileName.indexOf('.') >= 0)
			{
				String fileExt = fileName.substring(fileName.indexOf('.'));
				if (fileExt.equalsIgnoreCase(".png") || fileExt.equalsIgnoreCase(".jpg") || fileExt.equalsIgnoreCase(".jpeg"))
				{
					valid = true;
				}
			}
		}
		return valid;
	}

}