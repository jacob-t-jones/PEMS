// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Config.java

import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.List;

public class Config 
{
	
	private String departmentName;
	
	public Config()
	{
		this.parseConfigFile();
	}
	
	public String getDepartmentName()
	{
		return this.departmentName;
	}
	
	private void parseConfigFile()
	{
		List<String> configFile = null;
		try
		{
			configFile = Files.readAllLines(Paths.get("resources/config.pems"), Charset.defaultCharset());
		}
		catch (Exception e)
		{
			System.out.println("Error - Unable to read configuration file");
			return;
		}
		for (int i = 0; i < configFile.size(); i++)
		{
			String line = configFile.get(i);
			if (line.contains("departmentname"))
			{
				this.departmentName = line.substring(line.indexOf("value") + 7, line.indexOf("\"", line.indexOf("value") + 7));
			}
		}
	}
	
}
