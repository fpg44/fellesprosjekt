package Package;

import Database.DatabaseHandler;
import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.co.Connection;
import no.ntnu.fp.net.co.ConnectionImpl;

public class Server{

	public int PORT;
	private Connection connection, server;
	private boolean lookForIncommingDatagramPackets = true;
	private DatabaseHandler dbHandler;

	/**
	 * 
	 * @param ServerPort : The Port-number the server is listening to
	 * @param databaseIP : The IPAddress that the database have ('localhost' if ran locally)
	 * @param databasePORT : The Port-number to the database
	 * @param databaseName : The name of the database
	 * @param databaseUsername : The username for the database
	 * @param databasePassword : the password for the database
	 */
	public Server(int ServerPort, String databaseIP, String databasePORT, String databaseName, String databaseUsername, String databasePassword){
		this.PORT = ServerPort;
		
		dbHandler = new DatabaseHandler();
		
		try{
			dbHandler.connectToDatabase(databaseIP, databasePORT, databaseName, databaseUsername, databasePassword);			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		server = new ConnectionImpl(PORT);
		
		try{
			
			connection = server.accept();
			startIncommingDatagramPacketParser();
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}

	private  void notfyOnlineListeners(){
		
	}
	
	private void startIncommingDatagramPacketParser() throws Exception{
		
		while(lookForIncommingDatagramPackets){
			
			String message = connection.receive();
			Log.writeToLog("Server.java received a message", "lololol");
			
			
			//This is the parser part where you read the incomming string and chooses what to do
//			if(message.equals(""))
//				
//			else if(message.equals(""))
//				
//			else if(message.equals(""))
//				
//			else if(message.equals(""))
//				
//			else if(message.equals(""))
				
		}
	}
	
	protected void stopIncommingDatagramPacketParser(boolean b){
		this.lookForIncommingDatagramPackets = b;
	}
}
