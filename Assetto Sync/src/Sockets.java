import java.net.*;
import java.io.*;
public class Sockets {	
	private static ServerSocket initializeServerSocket(int port)
	{
		try {
			return new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void startServer(int port)
	{
		ServerSocket server = null;
		Socket client = null;
		BufferedReader in = null;
		PrintWriter out = null;
		
		try{
			System.out.println("Starting listener on port " + port);
		    server = new ServerSocket(port); 
		} catch (IOException e) {
		    System.out.println("Could not listen on port " + port);
		    return;
		}
		
		
		try{
			client = server.accept();
		} catch (IOException e) {
		    System.out.println("Accept failed: " + port);
		    return;
		}

		try{
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("Read failed");
			return;
		}
		
		while(true){
			try{
		        String line = in.readLine();
		        //Send data back to client
		        out.println(line);
      		} catch (IOException e) {
		        System.out.println("Read failed");
		        return;
      		}
		}
	}

	
}
