import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;


public class Track {
	private String name;
	private String path;
	
	private ArrayList<SingleFile> fileList = new ArrayList<>();;
	
	public Track(String trackName, String filePath)
	{
		name = trackName;
		path = filePath;
	}
	
	private void setFileList(File dir){
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				setFileList(file);
			} else {
				fileList.add(new SingleFile(file.getName(), file.getPath()));
			}
		}
	}
	
	
	public String getTotalChecksum(){
		
		BigInteger runningValue = null;
		for (SingleFile file : getFileList()) {
			if(runningValue == null)
			{
				try {
					runningValue = new BigInteger(file.getmd5Checksum(),16);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				try {
					runningValue = runningValue.add(new BigInteger(file.getmd5Checksum(),16)); 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return runningValue.toString(16);
		
	}
	
	public ArrayList<SingleFile> getFileList(){
		if(fileList.size() == 0)
		{
			File dir = new File(path);
			setFileList(dir);
		}
		return fileList;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPath(){
		return path;
	}
}
