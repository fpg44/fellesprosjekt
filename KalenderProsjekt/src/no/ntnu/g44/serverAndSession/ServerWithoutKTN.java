package no.ntnu.g44.serverAndSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import no.ntnu.g44.database.DatabaseHandler;
import no.ntnu.g44.models.AttendanceStatus;
import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Notification;
import no.ntnu.g44.models.Person;
import no.ntnu.g44.models.Project;
import no.ntnu.g44.models.Room;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class ServerWithoutKTN {
	
	private Thread connector;
	private ServerSocket server;
	private int PORT;
	private ArrayList<ConnectionToAClient> connections = new ArrayList<>();
	private DatabaseHandler dbHandler;
	private BufferedReader br;
	private XmlSerializer xmlSerializer;
	private Project project;
	
	public static void main(String[] args) {
		new Server(5545,"localhost","3306","project","root","admin");
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
	public ServerWithoutKTN(int ServerPort, String databaseIP, String databasePORT, String databaseName, String databaseUsername, String databasePassword){
		this.PORT = ServerPort;
		
		try {
			dbHandler = new DatabaseHandler();
			dbHandler.connectToDatabase(databaseIP, databasePORT, databaseName, databaseUsername, databasePassword);
			server = new ServerSocket(PORT);
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
			
		}
		
		this.connector = new Thread(){
			private boolean stop;
			
			@Override
			public void run(){
				while(!stop){
					try{
						connections.add(new ConnectionToAClient(server.accept()));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void interrupt(){
				stop = true;
				super.interrupt();
			}
		};
		this.connector.start();
	}
	
	class ConnectionToAClient{
		private Socket connectionToClient;
		private boolean listening;
		private PrintWriter writer;
		
		public ConnectionToAClient(Socket socket){
			this.connectionToClient = socket;
			
			try{
			
				writer = new PrintWriter(connectionToClient.getOutputStream(), true);
			
			}catch(IOException e){
				e.printStackTrace();
			}
			
			new Thread(){
				public void run(){
					listen();
				};
			}.start();
		}
		
		public void listen(){
			this.listening = true;
			
			while(listening){
				try{
					
					tryPacketParse(connectionToClient);
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		public void stopListening(){
			listening = false;
		}
		
		public void push(String msg, Socket exception){
			if(connectionToClient == exception){
				return;
			}
			try{
				writer.write(msg);
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void notifyOnlineListeners(String msg, Socket exception){
		for(ConnectionToAClient con : connections){
			con.push(msg, exception);
		}
	}
	
	private void tryPacketParse(Socket con) throws ConnectException, IOException, ValidityException, ParseException, ParsingException {

		br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String message = br.readLine();
		PrintWriter pw = new PrintWriter(con.getOutputStream());

		//This is the parser part where you read the incomming string and chooses what to do
		if(message.equals("get all")){
			pw.write(xmlSerializer.toXml(getDataFromDatabase()).toXML());
			
		}
		else if(message.startsWith("insert event")){
			notifyOnlineListeners(message, con);
			
			message = message.replaceFirst("insert event", "");
			Event e = xmlSerializer.toEvent(message);
			insert( e );
			
		}

		else if(message.startsWith("insert notification")){
			notifyOnlineListeners(message, con);
			
			message = message.replaceFirst("insert notification", "");
			Notification notification = xmlSerializer.toNotification(message);
			insert( notification );
		}

		else if(message.startsWith("insert attends_at")){
			notifyOnlineListeners(message, con);
			
			message = message.replaceFirst("insert attends_at", "");
			AttendanceStatus status = xmlSerializer.toAttendanceStatus(message);
			insert( status );
		}
		
		else if(message.startsWith("update event")){
			notifyOnlineListeners(message, con);
			
			message = message.replaceFirst("update event", "");
			Event e = xmlSerializer.toEvent(message);
			update( e );
		}

		else if(message.startsWith("update notification")){
			notifyOnlineListeners(message, con);
			
			message = message.replaceFirst("update notification", "");
			Notification notification = xmlSerializer.toNotification(message);
			update( notification );
		}

		else if(message.startsWith("update attends_at")){
			notifyOnlineListeners(message, con);
			
			message = message.replaceFirst("update attends_at", "");
			AttendanceStatus status = xmlSerializer.toAttendanceStatus(message);
			update( status );
		}

		else if(message.startsWith("delete event")){
			notifyOnlineListeners(message, con);
			
			message = message.replaceFirst("delete event", "");
			Event e = xmlSerializer.toEvent(message);
			delete( e );

		}

		else if(message.startsWith("delete notification")){
			notifyOnlineListeners(message, con);
			
			message = message.replaceFirst("delete notification", "");
			Notification notification = xmlSerializer.toNotification(message);
			delete( notification );
		}

		else if(message.startsWith("delete attends_at")){
			notifyOnlineListeners(message, con);
			
			message = message.replaceFirst("delete attends_at", "");
			AttendanceStatus status = xmlSerializer.toAttendanceStatus(message);
			delete( status );
		}
	}
	
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
