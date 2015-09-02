// PEMS (Police Evidence Management System) Version 0.1
// Copyright 2015 - Jacob Jones and Andrew Rottier
// PEMS.java

package runtime;
import javax.swing.*;
import gui.display.*;

/** Class used to open the PEMS graphical user interface at program runtime. This is where the <code>main</code> method is found.
 * 
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */
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