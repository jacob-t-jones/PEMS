import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.List;

public class Config 
{
	
	private List<String> configFile;
	private String departmentName;
	
	public Config()
	{
		try
		{
			this.configFile = Files.readAllLines(Paths.get("/resources/config.pems"), Charset.defaultCharset());
		}
		catch(Exception e)
		{
			System.out.println("Error - Unable to read configuration file");
			return;
		}
		this.readConfigFile();
	}
	
	public void readConfigFile()
	{
		for (int i = 0; i < configFile.size(); i++)
		{
			String line = configFile.get(i);

		}
	}
	
	public String getDepartmentName()
	{
		return this.departmentName;
	}
	
	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}
	
}
