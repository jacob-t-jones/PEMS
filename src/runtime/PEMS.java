// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// PEMS.java

package runtime;
import javax.swing.*;
import gui.display.*;

public class PEMS 
{

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() 
		{    
			public void run()
			{
				@SuppressWarnings("unused")
				FrameManager frame = new FrameManager();  
			}
		}); 
	}
	
}