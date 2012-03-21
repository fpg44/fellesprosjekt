package no.ntnu.g44.serverAndSession;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
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
import no.ntnu.g44.net.co.SimpleConnection;
import nu.xom.Document;

public class Server{

	public int PORT;
	private Connection server;
	private boolean lookForIncommingDatagramPackets = true;
	private DatabaseHandler dbHandler;
	private XmlSerializer xmlSerializer;
	private Project project;
	private Thread connectAcceptor;
	
	ArrayList<ConnectionToAClient> connections = new ArrayList<>();

	
	public static void main(String[] args) {
		new Server(5545,"localhost","3306","project","root","");
	}
	
	/**
	 * 
	 * @param ServerPort : The Port-number the server is listening to 
	 * @param databaseIP : The IPAddress that the database have ('localhost' if ran locally)
	 * @param databasePORT : The Port-number to the database(locally is 3306)
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
		
		
		this.connectAcceptor = new Thread(){
			private boolean stop;

			@Override
			public void run() {
				while(!stop){
					try {
						connections.add(new ConnectionToAClient(server.accept()));
					} catch (SocketTimeoutException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void interrupt() {
				stop = true;
				super.interrupt();
			}
		};
		
		
		connectAcceptor.start();
			
	}
	
		
	class ConnectionToAClient{
		private Connection conToClient;
		private boolean listening;

		public ConnectionToAClient(Connection con) {
			this.conToClient = con;
			new Thread(){
				public void run() {
					listen();
				};
			}.start();
		}
		
		public void listen(){
			this.listening = true;
			while(listening){
				
				try {
					tryPacketParse(conToClient);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void stopListening(){
			listening = false;
		}
		public void push(String msg){
			try {
				conToClient.send(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	

	/**
	 * When some changes are done in the calendar-application, this will notify the other online users.
	 */
	private  void notfyOnlineListeners(String msg,ConnectionToAClient exception){
		for (ConnectionToAClient con: connections) {
			con.push(msg);
		}
	}
	
	private void tryPacketParse(Connection con) throws ConnectException, IOException {
		
			
			String message = con.receive();
			Log.writeToLog("Server.java received a message", "lololol");
			System.out.println(message);
			//notfyOnlineListeners(message, null);
			
			
			//This is the parser part where you read the incomming string and chooses what to do
			if(message.equals("hello")){
				System.out.println(":::" + getDataFromDatabase().toString());
				con.send("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\r\n" + 
						"<project>\r\n" + 
						"     <person>\r\n" + 
						"          <name>Andreas L&#xFFFD;ve Selvik</name>\r\n" + 
						"          <username>lion</username>\r\n" + 
						"          <email/>\r\n" + 
						"          <date-of-birth>Mar 20, 2012</date-of-birth>\r\n" + 
						"     </person>\r\n" + 
						"     <person>\r\n" + 
						"          <name>Anders Eldhuset</name>\r\n" + 
						"          <username>anderse</username>\r\n" + 
						"          <email/>\r\n" + 
						"          <date-of-birth>Mar 20, 2012</date-of-birth>\r\n" + 
						"     </person>\r\n" + 
						"     <person>\r\n" + 
						"          <name>Jeppe Eriksen</name>\r\n" + 
						"          <username>jeppee</username>\r\n" + 
						"          <email/>\r\n" + 
						"          <date-of-birth>Mar 20, 2012</date-of-birth>\r\n" + 
						"     </person>\r\n" + 
						"     <person>\r\n" + 
						"          <name>Anders Dahlin</name>\r\n" + 
						"          <username>andersd</username>\r\n" + 
						"          <email/>\r\n" + 
						"          <date-of-birth>Mar 20, 2012</date-of-birth>\r\n" + 
						"     </person>\r\n" + 
						"     <person>\r\n" + 
						"          <name>Robing Tordly</name>\r\n" + 
						"          <username>robing</username>\r\n" + 
						"          <email/>\r\n" + 
						"          <date-of-birth>Mar 20, 2012</date-of-birth>\r\n" + 
						"     </person>\r\n" + 
						"     <event>\r\n" + 
						"          <event-id>-1</event-id>\r\n" + 
						"          <title>potato</title>\r\n" + 
						"          <owner>andersd</owner>\r\n" + 
						"          <event-start>?20:2;2012-23_13!</event-start>\r\n" + 
						"          <event-end>?20:2;2012-23_15!</event-end>\r\n" + 
						"          <location>hot</location>\r\n" + 
						"          <room>OTHER</room>\r\n" + 
						"          <participants>\r\n" + 
						"               <person>jeppee</person>\r\n" + 
						"               <person>andersd</person>\r\n" + 
						"          </participants>\r\n" + 
						"     </event>\r\n" + 
						"</project>");
			}
//				
//			else if(message.equals(""))
//				
//			else if(message.equals(""))
//				
//			else if(message.equals(""))
//				
//			else if(message.equals(""))
				
		
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
			project.addEvent(event, false);
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
