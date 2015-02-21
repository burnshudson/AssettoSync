import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.io.File;

public class SingleFile {
	private String filename;
	private String filepath;
	private long filesize = -1;
	private String md5Checksum = "";

	private void setFilename(String input){
    	filename = input;
    }

    public String getFilename(){
        return filename;
    }
    
    private void setFilepath(String input){
    	filepath = input;
    }

    public String getFilepath(){
        return filepath;
    }
    
    public long getFilesize(){
    	if(filesize < 0)
    	{
    		File f = new File(filepath);
    		filesize = f.length();
    	}
        return filesize;
    }
    
    //MD5 checksum methods taken from http://www.rgagnon.com/javadetails/java-0416.html
    public String getmd5Checksum() throws Exception {
 	   if(md5Checksum == "")
 	   {
 		     byte[] b = createChecksum(filename);
 		     String result = "";
 		     for (int i=0; i < b.length; i++) {
 		       result +=
 		          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
 		      }
 		     md5Checksum = result;
 	   }
 	   return md5Checksum;
    }

	private static byte[] createChecksum(String filename) throws
    Exception
	{
	  InputStream fis =  new FileInputStream(filename);
	
	  byte[] buffer = new byte[1024];
	  MessageDigest complete = MessageDigest.getInstance("MD5");
	  int numRead;
	  do {
	   numRead = fis.read(buffer);
	   if (numRead > 0) {
	     complete.update(buffer, 0, numRead);
	     }
	   } while (numRead != -1);
	  fis.close();
	  return complete.digest();
	}


	public SingleFile(String filepath)
	{
		setFilepath(filepath);
		File f = new File(filepath);
		setFilename(f.getName());
	}


}
