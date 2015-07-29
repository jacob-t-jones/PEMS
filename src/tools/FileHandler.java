// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// FileHandler.java

package tools;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import exceptions.*;
import gui.*;
import gui.components.img.*;

public class FileHandler 
{

	private OSType os;
	private ArrayList<File> peripheralFiles;
	
	public FileHandler()
	{
		this.os = this.retrieveOS();
		this.peripheralFiles = new ArrayList<File>();
		//this.retrievePeripheralFiles();
	}
	
	public enum OSType
	{
		WINDOWS, OSX, OTHER
	}
	
	/* getOS - returns "os", an OSType enum value representing the operating system that the JVM is currently being run on
	 */
	public OSType getOS()
	{
		return this.os;
	}
	
	/* getPeripheralThumbnails - creates and returns an ArrayList of ThumbnailImg objects representing all of the image files currently on external media devices
	 * 				      size - the size of the new thumbnails
	 *                   mouse - the MouseListener for the new thumbnails
	 */
	public ArrayList<ThumbnailImg> getPeripheralThumbnails(int size, MouseListener mouse)
	{
		ArrayList<ThumbnailImg> peripheralThumbnails = new ArrayList<ThumbnailImg>();
		for (int i = 0; i < this.peripheralFiles.size(); i++)
		{
			try 
			{
				ThumbnailImg newThumbnail = ComponentGenerator.generateThumbnailImg(this.peripheralFiles.get(i).getPath(), size);
				newThumbnail.addMouseListener(mouse);
				peripheralThumbnails.add(newThumbnail);
			} 
			catch (InvalidImgException e) 
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return peripheralThumbnails;
	}
	
	public boolean copyFiles(boolean delete, String caseNum, ArrayList<ThumbnailImg> selectedThumbnails)
	{
    	for (int i = 0; i < selectedThumbnails.size(); i++)
    	{
			Path currentPath = Paths.get(selectedThumbnails.get(i).getFilePath());
			Path casesPath = Paths.get("cases/" + caseNum + "/" + caseNum + " (" + i + ")" + selectedThumbnails.get(i).getFileExt());
			Path backupsPath = Paths.get("backups/" + caseNum + "/" + caseNum + " (" + i + "-" + 0 + ")" + selectedThumbnails.get(i).getFileExt());
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
				return false;
			}
    	}
    	return true;
	}
	
	/* createCase - attempts to create directories in both "cases" and "backups" for the specified case number, returns a boolean value indicating success
	 *    caseNum - the case number to try
	 */
	public boolean createCase(String caseNum) 
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
			return false;
		}
		return true;
	}
	
	/* caseExists - returns a boolean value indicating whether or not a given case has already been created
	 *    caseNum - the case number to check
	 */
	public boolean caseExists(String caseNum)
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
	
	/* validCaseNum - returns a boolean value indicating whether or not a given case number is valid (only letters and numbers, at least one character, no more than ten characters)
	 *      caseNum - the case number to check
	 */
	public boolean validCaseNum(String caseNum)
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
	
	/* retrieveOS - determines what operating system PEMS is currently being run on, and returns a correponding OSType enum value
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
	
	/* retrievePeripheralFiles - OS dependent method that attempts to load all image files located on currently attached peripheral devices into memory
	 */
	private void retrievePeripheralFiles()
	{
		if (this.os == OSType.WINDOWS)
		{
			for (int i = 0; i < File.listRoots().length; i++)
			{
				File currentFile = File.listRoots()[i];
				if (currentFile.isDirectory() && !currentFile.isHidden() && currentFile.getTotalSpace() < 128000000000L)
				{
					this.retrievePeripheralFiles(currentFile);
				}
			}
		}
		else if (this.os == OSType.OSX)
		{
			File drives = new File("/Volumes/");
			for (int i = 0; i < drives.listFiles().length; i++)
			{
				File currentFile = drives.listFiles()[i];
				if (currentFile.isDirectory() && !currentFile.isHidden() && currentFile.getTotalSpace() < 128000000000L)
				{
					this.retrievePeripheralFiles(currentFile);
				}
			}
		}
	}
	
	/* retrievePeripheralFiles - recursive implementation of the method, used to trace through directories on the peripheral devices and find valid image files
	 *               directory - the directory currently being looked at
	 */
	private void retrievePeripheralFiles(File directory)
	{
		for (int i = 0; i < directory.listFiles().length; i++)
		{
			File currentFile = directory.listFiles()[i];
			if (!currentFile.isHidden())
			{
				if (currentFile.isDirectory())
				{
					this.retrievePeripheralFiles(currentFile);
				}
				else if (currentFile.getName().contains("."))
				{
					String currentExt = currentFile.getName().substring(currentFile.getName().indexOf('.')).toLowerCase();
					if (currentExt.equals(".png") || currentExt.equals(".jpg") || currentExt.equals(".jpeg"))
					{
						this.peripheralFiles.add(currentFile);
					}
				}
			}
		}
	}
	
}