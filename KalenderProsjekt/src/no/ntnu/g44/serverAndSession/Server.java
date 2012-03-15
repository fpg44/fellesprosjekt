package no.ntnu.g44.serverAndSession;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.g44.database.DatabaseHandler;
import no.ntnu.g44.net.co.Connection;
import no.ntnu.g44.net.co.ConnectionImpl;
import nu.xom.Document;

public class Server{

	public int PORT;
	private Connection connection, server;
	private boolean lookForIncommingDatagramPackets = true;
	private DatabaseHandler dbHandler;
	private XmlSerializer xmlSerializer;

	/**
	 * 
	 * @param ServerPort : The Port-number the server is listening to (locally is 3306)
	 * @param databaseIP : The IPAddress that the database have ('localhost' if ran locally)
	 * @param databasePORT : The Port-number to the database
	 * @param databaseName : The name of the database
	 * @param databaseUsername : The username for the database
	 * @param databasePassword : the password for the database
	 */
	public Server(int ServerPort, String databaseIP, String databasePORT, String databaseName, String databaseUsername, String databasePassword){
		this.PORT = ServerPort;
		
		dbHandler = new DatabaseHandler();
		xmlSerializer = new XmlSerializer();
		
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

	/**
	 * When some changes are done in the calendar-application, this will notify the other online users.
	 */
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
	
	//returns a document from an arraylist with events from the database
	protected Document getAllEvents(){ 
		return xmlSerializer.toXml(dbHandler.getEventsFromDatabase());
	}
}
