// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// PeripheralManager.java

package backend;
import java.io.*;
import java.util.*;
import backend.peripheral.*;

/** Global class used to detect and import evidence files located on peripheral devices (cameras, USB drives, etc.).
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
public class PeripheralManager 
{
	
	private OSType os;
	private ArrayList<Device> devices;
	
	/** Populates instance fields by scanning the computer for external media devices.
	 */
	public PeripheralManager()
	{
		this.os = this.retrieveOS();
		this.devices = this.retrieveDevices();
	}
	
	/** Values used to represent the current operating system.
	 */
	public enum OSType
	{
		WINDOWS, OSX, OTHER
	}
	
	/** Returns an <code>OSType</code> enum value representing the operating system that the JVM is currently running on.
	 * 
	 *  @return <code>OSType</code> enum value representing the operating system that the JVM is currently running on
	 */
	public OSType getOS()
	{
		return this.os;
	}
	
	/** Returns an <code>ArrayList</code> of <code>Device</code> objects representing the peripheral devices currently connected to the computer.
	 * 
	 *  @return <code>ArrayList</code> of <code>Device</code> objects representing the peripheral devices currently connected to the computer
	 */
	public ArrayList<Device> getDevices()
	{
		return this.devices;
	}
	
	/** Determines which operating system is currently being used by pulling the <i>os.name</i> system property.
	 * 
	 *  @return <code>OSType.WINDOWS</code>, <code>OSType.OSX</code>, or <code>OSType.OTHER</code> (self explanatory)
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
	
	/** Operating system dependent method that scans the system for and stores information about any external devices.
	 * 
	 *  @return <code>ArrayList</code> of <code>Device</code> objects representing the currently connected peripheral devices
	 */
	private ArrayList<Device> retrieveDevices()
	{
		ArrayList<Device> devices = new ArrayList<Device>();
		if (this.os == OSType.WINDOWS)
		{
			for (int i = 0; i < File.listRoots().length; i++)
			{
				File currentFile = File.listRoots()[i];
				if (this.validateDevice(currentFile))
				{
					devices.add(new Device(currentFile));
				}
			}
		}
		else if (this.os == OSType.OSX)
		{
			File drives = new File("/volumes/");
			for (int i = 0; i < drives.listFiles().length; i++)
			{
				File currentFile = drives.listFiles()[i];
				if (this.validateDevice(currentFile))
				{
					devices.add(new Device(currentFile));
				}
			}
		}
		return devices;
	}
	
	/** Returns a <code>boolean</code> value indicating whether or not the passed in <code>File</code> is a valid peripheral device.
	 * 
	 *  @param file the <code>File</code> in question
	 *  @return <code>true</code> if the passed in <code>File</code> is a valid peripheral device; <code>false</code> if otherwise
	 */
	private boolean validateDevice(File file)
	{
		if (file.canRead() && file.isDirectory() && !file.isHidden() && file.getTotalSpace() < 128000000000L)
		{
			return true;
		}
		return false;
	}
	
}