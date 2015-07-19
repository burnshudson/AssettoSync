import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.net.*;
import java.io.*;

public class AssettoSync {
	private static ArrayList<Car> carList = new ArrayList<>();
	private static ArrayList<Track> trackList = new ArrayList<>();
	private static boolean isServer = false;
	private static String serverAddress = "";
	private static int serverPort = 1337;
	
	private static int numCarsSynced = 0;
	private static int numTracksSynced = 0;
	private static int numCarsAltered = 0;
	private static int numTracksAltered = 0;
	
	
	private static boolean debugMode = true;
	
	
	public static String assettoRootFolder;
	
	//0: string server IP address
	//1: integer port number
	//2: string 'true' or 'false' act as server
	public static void main(String[] args) {
		
		if (args.length > 1) {
			serverAddress = args[0];
			serverPort = Integer.parseInt(args[1]);
							
			if (args[2] == "true")
				isServer = true;
		}
		else
		{
			System.err.println("Arguments must be in the form of [serverIP] [serverPort]");
			if(!debugMode)
				System.exit(1);
		}

		assettoRootFolder = System.getProperty("user.dir");
		
		if(debugMode)
			assettoRootFolder = "E:\\Steam\\steamapps\\common\\assettocorsa";
		
		
		initializeLocal();
		
		
		
		
		
		if(debugMode)
		{
			isServer = true;
			
			System.out.println(getSingleCarFolderChecksum("sareni_camaro_gt3"));
			//getSingleCarFolderFilesChecksum("sareni_camaro_gt3");

			System.out.println();
			System.out.println();
			

			System.out.println(getSingleTrackFolderChecksum("blackwood"));
			//getSingleTrackFolderFilesChecksum("blackwood");
		}
		System.out.println();
		System.out.println();
		System.out.println("AssettoSync complete!");
		System.out.println("    " + numCarsSynced + " cars synced");
		System.out.println("    " + numCarsAltered + " cars updated to match");
		System.out.println("    " + numTracksSynced + " tracks synced");
		System.out.println("    " + numTracksAltered + " tracks updated to match");
		
		
		if(isServer)
			Sockets.startServer(serverPort);
		
		System.out.println("Code finished.");
	}
	
	
	private static void initializeLocal()
	{
		System.out.println("Getting local car list...");
		carList = setCarList();
		System.out.println("Done.");
		System.out.println("Getting track list...");
		trackList = setTrackList();
		System.out.println("Done.");
	}
	
	private static void replaceLocalCarWithServerCar(String name)
	{
		
	}
	
	private static void replaceServerCarWithLocalCar(String name)
	{
		
	}
	
	
	
//Checksum calls
	
	private static String getSingleTrackFolderChecksum(String name)
	{
		Track tempTrack = getTrackByName(name);
		System.out.println("Calculating track folder checksum....");
		return tempTrack.getTotalChecksum();
	}
	
	private static void getSingleTrackFolderFilesChecksum(String name)
	{
		Track tempTrack = getTrackByName(name);
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
	
	private static String getSingleCarFolderChecksum(String name)
	{	
		Car temp = getCarByName(name);
		System.out.println("Calculating car folder checksum....");
		return temp.getTotalChecksum();
	}
	
	private static void getSingleCarFolderFilesChecksum(String name)
	{
		Car temp = getCarByName(name);
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
	}
	
	
//Track and car info calls
	
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
