package no.ntnu.g44.serverAndSession;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.ArrayList;
//import no.ntnu.fp.net.admin.Log;
import no.ntnu.g44.database.DatabaseHandler;
import no.ntnu.g44.models.AttendanceStatus;
import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Notification;
import no.ntnu.g44.models.NotificationType;
import no.ntnu.g44.models.Person;
import no.ntnu.g44.models.Project;
import no.ntnu.g44.models.Room;
import no.ntnu.g44.net.co.Connection;
import no.ntnu.g44.net.co.ConnectionImpl;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

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
		//		new Server(5545, "mysql.stud.ntnu.no", "3306", "andereld_fp_gr44",
		//				"andereld_fp_gr44", "gruppe44");
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

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public void stopListening(){
			listening = false;
		}
		public void push(String msg, Connection exception){
			if(conToClient == exception) return;
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
	private void notifyOnlineListeners(String msg, Connection exception){
		for (ConnectionToAClient con: connections) {
			con.push(msg, exception);
		}
	}

	/**
	 * open a connection with a client and speaks with it
	 */
	private void tryPacketParse(Connection con) throws ConnectException, IOException, ValidityException, ParseException, ParsingException {


		String message = con.receive();
//		Log.writeToLog("Server.java received a message", "lololol");

		//This is the parser part where you read the incomming string and chooses what to do
		if(message.equals("get all")){
			con.send(xmlSerializer.toXml(getDataFromDatabase()).toXML());
		}
		else if(message.startsWith("insert event")){
			notifyOnlineListeners(message, con);
			
			message = message.replaceFirst("insert event", "");
			Event e = xmlSerializer.toEvent(message);
			insert( e );
			
		}

		else if(message.startsWith("insert notification")){
			message = message.replaceFirst("insert notification", "");
			Notification notification = xmlSerializer.toNotification(message);
			insert( notification );

			//Send the notification to all online clients
			String msg = xmlSerializer.notificationToXml(notification).toXML();
			notifyOnlineListeners("notification" + msg, con);
			
//			//If notification is an Invitation
			if(notification.getType() == NotificationType.EVENT_INVITATION){
				notifyOnlineListeners("notification" + xmlSerializer.notificationToXml(notification).toXML(), con);
			}
		}

		else if(message.startsWith("insert attends_at")){
			notifyOnlineListeners(message, con);
			message = message.replaceFirst("insert attends_at", "");
			AttendanceStatus status = xmlSerializer.toAttendanceStatus(message);
			insert( status );
		}
		
		else if(message.startsWith("update event")){
			message = message.replaceFirst("update event", "");
			Event e = xmlSerializer.toEvent(message);
			update( e );
		}

		else if(message.startsWith("update notification")){
			message = message.replaceFirst("update notification", "");
			Notification notification = xmlSerializer.toNotification(message);
			update( notification );

			//CREATES A NOTIFICATION
			if(notification.getType() == NotificationType.PARTICIPANT_DECLINED){

				//Send notification to all online users
				notifyOnlineListeners("notification" + message, con);

				//updates the notification into the database
				update( notification );
			}
		}

		else if(message.startsWith("update attends_at")){
			message = message.replaceFirst("update attends_at", "");
			AttendanceStatus status = xmlSerializer.toAttendanceStatus(message);
			update( status );
		}

		else if(message.startsWith("delete event")){
			message = message.replaceFirst("delete event", "");
			Event e = xmlSerializer.toEvent(message);
			delete( e );

		}

		else if(message.startsWith("delete notification")){
			message = message.replaceFirst("delete notification", "");
			Notification notification = xmlSerializer.toNotification(message);
			delete( notification );
		}

		else if(message.startsWith("delete attends_at")){
			message = message.replaceFirst("delete attends_at", "");
			AttendanceStatus status = xmlSerializer.toAttendanceStatus(message);
			delete( status );
		}
	}

	private void stopIncommingDatagramPacketParser(boolean b){
		this.lookForIncommingDatagramPackets = b;
	}

	/**
	 * @return returns a project file with all information from the database
	 */
	protected Project getDataFromDatabase() throws ConnectException, IOException{
		project = new Project();

		ArrayList<Person> persons = dbHandler.getPersons();
		if(persons != null){
			for(Person person : persons){
				project.addPerson(person);
			}			
		}

		ArrayList<Event> events = dbHandler.getEvents();
		if(events != null){
			for(Event event : events){
				project.addEvent(event, false);
			}			
		}

		ArrayList<Room> rooms = dbHandler.getRooms();
		if(rooms != null){
			for(Room room : rooms){
				project.addRoom(room);
			}			
		}

		ArrayList<Notification> notifications = dbHandler.getNotifications();
		if(notifications != null){
			for(Notification notification: notifications){
				project.addNotification(notification, false);
			}			
		}

		ArrayList<AttendanceStatus> attendanceStatus = dbHandler.getAttendanceStatus();
		if(attendanceStatus != null){
			for(AttendanceStatus status : attendanceStatus){
				project.addAttendanceStatus(status, false);
			}
		}

		return project;
	}

	private void updateAll(Project p){
		ArrayList<Event> events = p.getEventList();
		dbHandler.updateEvents(events);

		ArrayList<Notification> notifications = p.getNotificationList();
		dbHandler.updateNotifications(notifications);

		ArrayList<AttendanceStatus> status = p.getAttendanceStatusList();
		dbHandler.updateAttendanceStatus(status);
	}

	private void update(Event event){
		dbHandler.updateEvent(event);
	}

	private void update(Notification notification){
		dbHandler.updateNotification(notification);
	}

	private void update(AttendanceStatus status){
		dbHandler.updateAttendanceStatus(status);
	}

	private void insert(Event event){
		dbHandler.newEvent(event);
	}

	private void insert(Notification notif){
		dbHandler.newNotification(notif);
	}

	private void insert(AttendanceStatus status){
		dbHandler.newAttendanceStatus(status);
	}

	private void delete(Event event){
		dbHandler.deleteEvent(event);
	}

	private void delete(Notification notif){
		dbHandler.deleteNotification(notif);
	}

	private void delete(AttendanceStatus status){
		dbHandler.deleteAttendanceStatus(status);
	}
}
