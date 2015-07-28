// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// Config.java

package tools;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class Config {

	private String departmentName;
	private boolean setupStatus;
	private boolean persistence;

	public Config() {
		this.parseConfigFile();
	}

	/*
	 * getDepartmentName - returns the name of the police department specified
	 * in the config file
	 */
	public String getDepartmentName() {
		return this.departmentName;
	}

	/*
	 * getSetupStatus - returns a boolean value indicating whether or not the
	 * program has been set up
	 */
	public boolean getSetupStatus() {
		return this.setupStatus;
	}

	/*
	 * getPersistence - returns a boolean value indicating whether the program
	 * should exit normally or run in the background at all times
	 */
	public boolean getPersistence() {
		return this.persistence;
	}

	/*
	 * parseConfigFile - parses config.pems line by line and puts acquired
	 * values into their respective instance fields
	 */
	private void parseConfigFile() {
		List<String> configFile = null;
		try {
			configFile = Files.readAllLines(Paths.get("resources/config.pems"), Charset.defaultCharset());
		} catch (IOException e) {
			System.out.println("Error - Unable to read configuration file");
			e.printStackTrace();
			return;
		}
		for (int i = 0; i < configFile.size(); i++) {
			String line = configFile.get(i);
			if (line.contains("field") && line.contains("value")) {
				String value = line.substring(line.indexOf("value") + 7, line.indexOf("\"", line.indexOf("value") + 7));
				if (line.contains("departmentname")) {
					this.departmentName = value;
				} else if (line.contains("setupstatus")) {
					this.setupStatus = Boolean.parseBoolean(value);
				} else if (line.contains("persistence")) {
					this.persistence = Boolean.parseBoolean(value);
				}
			}
		}
	}

}