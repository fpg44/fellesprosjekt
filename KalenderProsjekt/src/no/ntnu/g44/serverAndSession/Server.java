package no.ntnu.g44.serverAndSession;

import java.util.ArrayList;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.g44.database.DatabaseHandler;
import no.ntnu.g44.models.AttendanceStatus;
import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Notification;
import no.ntnu.g44.models.Person;
import no.ntnu.g44.models.Project;
import no.ntnu.g44.models.Room;
import no.ntnu.g44.net.co.Connection;
import no.ntnu.g44.net.co.ConnectionImpl;

public class Server{

	public int PORT;
	private Connection connection, server;
	private boolean lookForIncommingDatagramPackets = true;
	private DatabaseHandler dbHandler;
	private XmlSerializer xmlSerializer;
	private Project project;

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
	
	/**
	 * 
	 * @return Project file with all information from the database
	 */
	protected Project getDataFromDatabase(){
		project = new Project();
		
		ArrayList<Person> persons = dbHandler.getPersons();
		for(Person person : persons){
			project.addPerson(person);
		}
		
		ArrayList<Event> events = dbHandler.getEventsFromDatabase();
		for(Event event : events){
			project.addEvent(event);
		}
		
		ArrayList<Room> rooms = dbHandler.getRooms();
		for(Room room : rooms){
			project.addRoom(room);
		}
		
		ArrayList<Notification> notifications = dbHandler.getNotifications();
		for(Notification notification: notifications){
			project.addNotification(notification);
		}
		
		ArrayList<AttendanceStatus> attendanceStatus = dbHandler.getAttendanceStatus();
		for(AttendanceStatus status : attendanceStatus){
			project.addAttendanceStatus(status);
		}
		
		return project;
	}
	
	protected void updateAll(){
		ArrayList<Event> events = project.getEventList();
		dbHandler.updateEvents(events);
		
		ArrayList<Notification> notifications = project.getNotificationList();
		dbHandler.updateNotifications(notifications);
		
		ArrayList<AttendanceStatus> status = project.getAttendanceStatusList();
		dbHandler.updateAttendanceStatus(status);
	}
	
	protected void update(Event event){
		dbHandler.updateEvent(event);
	}
	
	protected void update(Notification notification){
		dbHandler.updateNotification(notification);
	}
	
	protected void update(AttendanceStatus status){
		dbHandler.updateAttendanceStatus(status);
	}
	
	protected void insert(Event event){
		dbHandler.newEvent(event);
	}
	
	protected void insert(Notification notif){
		dbHandler.newNotification(notif);
	}
	
	protected void insert(AttendanceStatus status){
		dbHandler.newAttendanceStatus(status);
	}
	
	protected void delete(Event event){
		dbHandler.deleteEvent(event);
	}
	
	protected void delete(Notification notif){
		dbHandler.deleteNotification(notif);
	}
}
