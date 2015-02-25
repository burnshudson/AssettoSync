import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AssettoSync {
	private static ArrayList<Car> carList = new ArrayList<>();
	private static ArrayList<Track> trackList = new ArrayList<>();
	
	 
	
	public static String assettoRootFolder;
	
	public static void main(String[] args) {
		assettoRootFolder = "E:\\Steam\\steamapps\\common\\assettocorsa";
		
		
		System.out.println(System.getProperty("user.dir"));
		System.out.println("Getting car list...");
		carList = setCarList();
		System.out.println("Done.");
		
		
		
		Car temp = getCarByName("sareni_camaro_gt3");
		
		System.out.println("Loading car folder checksum....");
		System.out.println("   Car folder checksum: " + temp.getTotalChecksum());
		
		System.out.println();
		System.out.println();
		System.out.println("Individual file checksums:");
		for (SingleFile file : temp.getFileList()) {   
		    try {
				System.out.println(file.getLocalFilepath()+ " " + file.getFilesize()/1024 +"kb");
				System.out.println("  checksum: "+ file.getmd5Checksum());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println();
		}
		
		System.out.println("Getting track list...");
		trackList = setTrackList();
		System.out.println("Done.");
		
		
		
		Track tempTrack = getTrackByName("blackwood");
		
		System.out.println("Loading track folder checksum....");
		System.out.println("   Track folder checksum: " + tempTrack.getTotalChecksum());
		
		System.out.println();
		System.out.println();
		System.out.println("Individual file checksums:");
		for (SingleFile file : tempTrack.getFileList()) {   
		    try {
				System.out.println(file.getLocalFilepath()+ " " + file.getFilesize()/1024 +"kb");
				System.out.println("  checksum: "+ file.getmd5Checksum());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println();
		}
	}
	
	
	private static Car getCarByName(String name){
		System.out.println("Searching for car: " + name);
		
		for(int i=0;i<carList.size();i++){
	        if(carList.get(i).getName().equalsIgnoreCase(name)){
	        	System.out.println("Found car.");
	            return carList.get(i);
	        }
		}
		return null;
	}
	
	private static Track getTrackByName(String name){
		System.out.println("Searching for track: " + name);
		
		for(int i=0;i<trackList.size();i++){
	        if(trackList.get(i).getName().equalsIgnoreCase(name)){
	        	System.out.println("Found track.");
	            return trackList.get(i);
	        }
		}
		return null;
	}
	

	private static ArrayList<Car> setCarList() {
		ArrayList<Car> carListTemp = new ArrayList<>();
		
		File dir = new File(assettoRootFolder + "/content/cars");
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				Car temp = new Car(file.getName(), file.getPath());
				carListTemp.add(temp);
			} else {
				//Item found not in expected car format, ignore.
				//TODO: Maybe handle this?
			}
		}
		return carListTemp;
	}
	
	private static ArrayList<Track> setTrackList() {
		ArrayList<Track> trackListTemp = new ArrayList<>();
		
		File dir = new File(assettoRootFolder + "/content/tracks");
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				Track temp = new Track(file.getName(), file.getPath());
				trackListTemp.add(temp);
			} else {
				//Item found not in expected track format, ignore.
				//TODO: Maybe handle this?
			}
		}
		return trackListTemp;
	}
	
	
	/*
	* This is recursive directory searching based on 
	* http://www.avajava.com/tutorials/lessons/how-do-i-recursively-display-all-files-and-directories-in-a-directory.html
	* This is referenced in other directory searching functions in my code.
	*
	*public static void displayDirectoryContents(File dir, int curDepth) {
	*	try {
	*		File[] files = dir.listFiles();
	*		for (File file : files) {
	*			if (file.isDirectory()) {
	*				System.out.println(new String(new char[curDepth]).replace("\0", "    ") +
	*						"directory:" + file.getCanonicalPath());
	*				displayDirectoryContents(file,curDepth+1);
	*			} else {
	*				System.out.println(new String(new char[curDepth]).replace("\0", "    ") +
	*							"    file:" + file.getCanonicalPath());
	*			}
	*		}
	*	} catch (IOException e) {
	*		e.printStackTrace();
	*	}
	*}
	*/
	
}
