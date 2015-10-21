// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Config.java

package backend;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

/** Global configuration class that allows easy access to the user settings stored in <i>config.pems</i>.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class Config 
{

	private String departmentName;
	private String addressLineOne;
	private String addressLineTwo;
	private String phoneNumber;
	private boolean setupStatus;
	private boolean persistence;
	
	/** Calls the method that parses through and stores the settings values from <i>config.pems</i>.
	 */
	public Config()
	{
		this.parseConfigFile();
	}

	/** Returns a <code>String</code> value containing the name of the police department utilizing PEMS.
	 * 
	 *  @return <code>String</code> value containing the name of the police department utilizing PEMS
	 */
	public String getDepartmentName()
	{
		return this.departmentName;
	}
	
	/** Returns a <code>String</code> value containing the first line of the address of the police department utilizing PEMS.
	 * 
	 *  @return <code>String</code> value containing the first line of the address of the police department utilizing PEMS
	 */
	public String getAddressLineOne()
	{
		return this.addressLineOne;
	}
	
	/** Returns a <code>String</code> value containing the second line of the address of the police department utilizing PEMS.
	 * 
	 *  @return <code>String</code> value containing the second line of the address of the police department utilizing PEMS
	 */
	public String getAddressLineTwo()
	{
		return this.addressLineTwo;
	}
	
	/** Returns a <code>String</code> value containing the phone number of the police department utilizing PEMS.
	 * 
	 *  @return <code>String</code> value containing the phone number of the police department utilizing PEMS
	 */
	public String getPhoneNumber()
	{
		return this.phoneNumber;
	}

	/** Returns a <code>boolean</code> value indicating whether or not the program has been set up.
	 * 
	 *  @return <code>true</code> if the program has already been set up; <code>false</code> if otherwise
	 */
	public boolean getSetupStatus()
	{
		return this.setupStatus;
	}
	
	/** Returns a <code>boolean</code> value indicating whether the program should exit normally or run in the background at all times.
	 * 
	 *  @return <code>true</code> if the program should persist on close; <code>false</code> if otherwise
	 */
	public boolean getPersistence()
	{
		return this.persistence;
	}
	
	/** Populates the instance fields of this class by reading and parsing <i>config.pems</i>.
	 */
	private void parseConfigFile()
	{
		List<String> configFile = null;
		try
		{
			configFile = Files.readAllLines(Paths.get("resources/config.pems"), Charset.defaultCharset());
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		for (int i = 0; i < configFile.size(); i++)
		{
			String line = configFile.get(i);
			if (line.contains("field") && line.contains("value"))
			{
				String value = line.substring(line.indexOf("value") + 7, line.indexOf("\"", line.indexOf("value") + 7));
				if (line.contains("departmentname"))
				{
					this.departmentName = value;
				}
				else if (line.contains("addresslineone"))
				{
					this.addressLineOne = value;
				}
				else if (line.contains("addresslinetwo"))
				{
					this.addressLineTwo = value;
				}
				else if (line.contains("phonenumber"))
				{
					this.phoneNumber = value;
				}
				else if (line.contains("setupstatus"))
				{
					this.setupStatus = Boolean.parseBoolean(value);
				}
				else if (line.contains("persistence"))
				{
					this.persistence = Boolean.parseBoolean(value);
				}
			}
		}
	}
	
}