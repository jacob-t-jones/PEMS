[PEMS (Police Evidence Management System)](https://github.com/jacob-t-jones/PEMS)
----------

PEMS is a simple application specifically designed for use by law enforcement officers, allowing them to easily manage, edit, 
store, and backup evidence files acquired during investigations. Written in Java, the program is almost completely GUI based 
and provides basic image manipulation capabilities, an easy to use photo importation interface, and the ability to backup 
evidence files both locally and remotely. Although written and designed with the Plainville Police Department in mind, it can 
be modified to fit the needs of most law enforcement agencies.

Installation
----------

<p>Those who have been granted permission to install and use PEMS have two options for installation:</p>

1. The source code provided on GitHub can be downloaded and compiled on the machine in question. Uses who choose this 
   option should be aware that PEMS was written and compiled using Java Development Kit Version 1.7.0 Update 67 (JDK 7u67).
	
2. An executable file can be provided for installion on the machine in question. This is the simpler option, and leaves
   far less room for error.

Changelog
----------

* Version 0.1
	
..* Initial beta release
		
Licensing
----------

At the moment, PEMS is a closed source project, and its source code is not available to the public. All three external
libraries utilized within the project, however, are open source, and fall under the Apache License Version 2.0. As is
required by law, a copy of said license has been included within this project, and can be found in the APACHE file in
the project's home directory.
		
Documentation
----------

All code written for this project has been documented in detail through the use of Javadocs. This documentation is included 
with the project source code, and can be found in the "/docs/" directory.

Libraries Incorporated
----------

	imgscalr (Version 4.2)
	http://www.thebuzzmedia.com/software/imgscalr-java-image-scaling-library/
	
		* Distributed under the Apache License Version 2.0
		* Provides many of the basic image editing capabilities (resizing, rotating, brightening, etc.) offered by PEMS

	Sanselan (Version 0.97)
	https://commons.apache.org/proper/commons-imaging/
	
		* Distributed under the Apache License Version 2.0
		* Used to import images from memory and retrieve their metadata

	PDFBox (Version 1.8.1)
	https://pdfbox.apache.org/index.html

		* Distributed under the Apache License Version 2.0 
		* Utilized to generate and modify PDF files during the PEMS printing procedure 
		
Resources Utilized
----------

	add.png
	
		* Taken from the Tango! project set
		* Released into the public domain by its creators
		* Can be found at https://commons.wikimedia.org/wiki/File:List-add.svg
		
	delete.png
	
		* Taken from the Tango! project set
		* Released into the public domain by its creators
		* Can be found at https://commons.wikimedia.org/wiki/File:Edit-delete.svg
		
	folder.png
		
		* Taken from the Tango! project set
		* Released into the public domain by its creators
		* Can be found at https://commons.wikimedia.org/wiki/File:Folder.svg
		
	harddrive.png
	
		* Taken from the Tango! project set
		* Released into the public domain by its creators
		* Can be found at https://commons.wikimedia.org/wiki/File:Drive-removable-media.svg
		
	next.png
	
		* Taken from the Tango! project set
		* Released into the public domain by its creators
		* Can be found at https://commons.wikimedia.org/wiki/File:Go-next.svg
		
	printer.png
	
		* Taken from the Tango! project set
		* Released into the public domain by its creators
		* Can be found at https://commons.wikimedia.org/wiki/File:Printer.svg
		
	refresh.png
	
		* Taken from the Tango! project set
		* Released into the public domain by its creators
		* Can be found at https://commons.wikimedia.org/wiki/File:View-refresh.svg

Credits
----------

	Jacob Jones
	https://github.com/jacob-t-jones
	https://www.linkedin.com/pub/jacob-jones/b7/650/2a3
	
		* Lead Programmer
		* Responsibilities included (but were not limited to):
			- Code for backend image importation and case creation
			- General planning and design implementation
			- Class hierarchy and project organization
			- Error and exception handling
			- File system creation and organization
			- Development of the graphical user interface
			- Javadoc creation
		
	Andrew Rottier
	https://github.com/AndrewRot
	https://www.linkedin.com/pub/andrew-rottier/b4/307/68a
	
		* Lead Programmer
		* Responsibilities included (but were not limited to):
			- Development of the graphical user interface
			- Installation of externeral libraries 
			- General planning and design implementation
			- Implementation of the printing interface and procedure
			- Initial code commenting
	
	Kevin Ross
	https://github.com/rosskPCS
	https://www.linkedin.com/pub/kevin-ross/12/499/7b6
		
		* Project Manager
		* Responsibilities included (but were not limited to):
			- Project conception
			- Beta testing and bug detection
			- Application installation and support for the Plainville Police Department
			
Contact
----------

Any questions, comments, or concerns regarding PEMS can be referred to Jacob Jones (jacob.theodore.jones@gmail.com) or Kevin
Ross (rossk@plainvilleschools.org).