// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// FileHandler.java

package tools;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.imageio.*;
import gui.components.img.*;

public class FileHandler 
{

	private OSType os;
	private ArrayList<File> peripheralFiles;
	
	public FileHandler()
	{
		this.os = this.retrieveOS();
		this.peripheralFiles = this.retrievePeripheralFiles();
	}
	
	public enum OSType
	{
		WINDOWS, OSX, OTHER
	}
	
	public enum CaseCreationResult
	{
		CASE_ALREADY_EXISTS, DIRECTORY_CREATION_FAILED, INVALID_CASE_NUMBER, SUCCESS
	}
	
	public enum CopyFilesResult
	{
		COPY_FAILED, SUCCESS
	}
	
	public enum DeleteFileResult
	{
		DELETION_FAILED, SUCCESS
	}
	
	public enum SaveFileResult
	{
		SAVE_FAILED, SUCCESS
	}
	
	/* getOS - returns "os", an OSType enum value indicating which operating system the JVM is currently running on
	 */
	public OSType getOS()
	{
		return this.os;
	}

	/* getPeripheralFiles - returns "peripheralFiles", an ArrayList of File objects, each one referencing an image file detected on an external device
	 */
	public ArrayList<File> getPeripheralFiles()
	{
		return this.peripheralFiles;
	}
	
	/* createCase - attempts to initialize a case with the specified case number by creating directories for it in the file system, and returns a CaseCreationResult enum value indicating whether or not this operation was successful 
	 *    caseNum - the case number to try
	 */
	public CaseCreationResult createCase(String caseNum) 
	{
		if (!this.isValidCaseNum(caseNum))
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
				Files.createDirectory(Paths.get("cases/" + caseNum + "/"));
				Files.createDirectory(Paths.get("backups/" + caseNum + "/"));
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
	
	/* copyFiles - copies image files from their original location on a peripheral drive to the managed folder on the local file system
	 *    delete - boolean value indicating whether the original copies of the image files should be deleted or retained
	 *   caseNum - the case that the image files will be added to
	 */
	public CopyFilesResult copyFiles(boolean delete, String caseNum, ArrayList<ThumbnailImg> selected)
	{
		int fileIndex = 0;
		File caseDirectory = new File("backups", "/" + caseNum + "/");
		for (int i = 0; i < caseDirectory.list().length; i++)
		{
			String currentFileName = caseDirectory.list()[i];
			int currentFileIndex = Integer.parseInt(currentFileName.substring(currentFileName.indexOf('(') + 1, currentFileName.indexOf('-')));
			if (currentFileIndex > fileIndex)
			{
				fileIndex = currentFileIndex;
			}
		}
    	for (int i = 0; i < selected.size(); i++)
    	{
    		fileIndex++;
			Path currentPath = Paths.get(selected.get(i).getFilePath());
			Path casesPath = Paths.get("cases/" + caseNum + "/" + caseNum + " (" + fileIndex + ")" + selected.get(i).getFileExt());
			Path backupsPath = Paths.get("backups/" + caseNum + "/" + caseNum + " (" + fileIndex + "-" + 0 + ")" + selected.get(i).getFileExt());
			try 
			{
				Files.copy(currentPath, casesPath, StandardCopyOption.REPLACE_EXISTING);
				Files.copy(currentPath, backupsPath, StandardCopyOption.REPLACE_EXISTING);
				if (delete)
				{
					Files.delete(currentPath);
				}
			} 
			catch (IOException e) 
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
				return CopyFilesResult.COPY_FAILED;
			}
    	}
    	return CopyFilesResult.SUCCESS;
	}
	
	public DeleteFileResult deleteFile(EditedImg img) 
	{
		try 
		{
			Files.delete(Paths.get(img.getFilePath()));
		}
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			return DeleteFileResult.DELETION_FAILED;
		}
		return DeleteFileResult.SUCCESS;
	}
	
	public SaveFileResult saveFile(String caseNum, EditedImg img)
	{
	    int imageNum = Integer.parseInt(img.getFileName().substring(img.getFileName().indexOf('(') + 1, img.getFileName().indexOf(')')));
		int versionNum = 0;
		while (Files.exists(Paths.get("backups/" + caseNum + "/" +  caseNum + " (" + imageNum + "-" + versionNum + ")" + img.getFileExt())))
		{
			versionNum++;
		}
		try 
		{
			ImageIO.write(img.getImage(), img.getFileType(), new File("backups/" + caseNum + "/" + caseNum + " (" + imageNum + "-" + versionNum + ")" + img.getFileExt()));
		    ImageIO.write(img.getImage(), img.getFileType(), new File("cases/" + caseNum + "/" + caseNum + " (" + imageNum + ")" + img.getFileExt()));
		} 
		catch (IOException e) 
		{
		    System.out.println(e.getMessage());
		    e.printStackTrace();
		    return SaveFileResult.SAVE_FAILED;
		}
		return SaveFileResult.SUCCESS;
	}
	
	public void refreshPeripheralFiles()
	{
		this.peripheralFiles = this.retrievePeripheralFiles();
	}
	
	/* retrieveOS - determines which operating system is currently being used by pulling the os.name system property value
	 */
	private OSType retrieveOS()
	{
		String osPropertyValue = System.getProperty("os.name").toLowerCase();
		if (osPropertyValue.indexOf("win") >= 0)
		{
			return OSType.WINDOWS;
		}
		else if (osPropertyValue.indexOf("mac") >= 0)
		{
			return OSType.OSX;
		}
		else
		{
			return OSType.OTHER;
		}
	}
	
	/* retrievePeripheralFiles - operating system dependent method that populates "peripheralFiles" by reading image files from external devices
	 *                         * computer is running Windows => the root directory is searched for external drives
	 *                         * computer is running Mac OSX => the "/volumes/" directory is searched for external drives
	 */
	private ArrayList<File> retrievePeripheralFiles()
	{
		ArrayList<File> peripheralFiles = new ArrayList<File>();
		if (this.os == OSType.WINDOWS)
		{
			for (int i = 0; i < File.listRoots().length; i++)
			{
				File currentFile = File.listRoots()[i];
				if (currentFile.canRead() && currentFile.isDirectory() && !currentFile.isHidden() && currentFile.getTotalSpace() < 128000000000L)
				{
					peripheralFiles = this.retrievePeripheralFiles(currentFile, peripheralFiles);
				}
			}
		}
		else if (this.os == OSType.OSX)
		{
			File drives = new File("/volumes/");
			for (int i = 0; i < drives.listFiles().length; i++)
			{
				File currentFile = drives.listFiles()[i];
				if (currentFile.canRead() && currentFile.isDirectory() && !currentFile.isHidden() && currentFile.getTotalSpace() < 128000000000L)
				{
					peripheralFiles = this.retrievePeripheralFiles(currentFile, peripheralFiles);
				}
			}
		}
		return peripheralFiles;
	}
	
	/* retrievePeripheralFiles - recursive version of the above method that traces through the passed in directory in search of valid image files (.png, .jpg, etc.)
	 *               directory - the file system directory to search
	 *         peripheralFiles - the current list of valid image files on peripheral devices
	 */
	private ArrayList<File> retrievePeripheralFiles(File directory, ArrayList<File> peripheralFiles)
	{
		for (int i = 0; i < directory.listFiles().length; i++)
		{
			File currentFile = directory.listFiles()[i];
			if (currentFile.canRead() && !currentFile.isHidden())
			{
				if (currentFile.isDirectory())
				{
					this.retrievePeripheralFiles(currentFile, peripheralFiles);
				}
				else if (currentFile.getName().contains("."))
				{
					String currentExt = currentFile.getName().substring(currentFile.getName().indexOf('.')).toLowerCase();
					if (currentExt.equals(".png") || currentExt.equals(".jpg") || currentExt.equals(".jpeg"))
					{
						peripheralFiles.add(currentFile);
					}
				}
			}
		}
		return peripheralFiles;
	}
	
	/* caseExists - returns a boolean value indicating whether or not a case with the specified number already exists in the file system
	 *    caseNum - the case number to try
	 */
	private boolean caseExists(String caseNum)
	{
		if (Files.isDirectory(Paths.get("cases/" + caseNum + "/")) || Files.isDirectory(Paths.get("backups/" + caseNum + "/")))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/* isValidCaseNum - returns a boolean value indicating whether or not a given case number is valid (at least one character, no more than ten characters, only letters and numbers)
	 *        caseNum - the case number to try
	 */
	private boolean isValidCaseNum(String caseNum)
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