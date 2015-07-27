// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// FileHandler.java

package tools;
import java.io.*;
import java.nio.file.*;

public class FileHandler 
{

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
	
}