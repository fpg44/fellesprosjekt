package no.ntnu.g44.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.print.attribute.standard.DateTimeAtCompleted;

import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Notification;
import no.ntnu.g44.models.NotificationType;
import no.ntnu.g44.models.Person;
import no.ntnu.g44.models.Room;


public class DatabaseHandler {

	private Connection con = null;
	private Statement stmt = null;

	public DatabaseHandler(){

	}
	/**
	 * 
	 * @param IP - The IPAddress to the database (use 'localhost' if the database is locally)
	 * @param PORT - The port to the given database (standard: 3306)
	 * @param databaseName - 
	 * @param user - Username to the database
	 * @param password - Password to the database
	 * @throws Exception - Throws exception if it can't connect to the database
	 */
	public void connectToDatabase(String IP, String PORT, String databaseName, String user, String password) throws Exception{

		String url = "jdbc:mysql://" + IP + ":" + PORT + "/" + databaseName;

		con = getConnection( url, user, password );
		stmt = con.createStatement();
		con.setAutoCommit(true); //this makes sure you dont have to commit after an SQL query
	}

	public Connection getConnection(String url, String user, String password) throws Exception{

		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){
			System.out.println("Failed to load sql-driver");
		}

		return DriverManager.getConnection(url, user, password);
	}

	public void closeConnection(){
		try{
			if(con != null){
				con.close();
			}
		}catch( SQLException e){
			System.out.println("Failed to close database connection");
		}
	}

	/**
	 * 
	 * @return An ArrayList<Event> with all the events
	 */
	public ArrayList<Event> getEventsFromDatabase(){
		ArrayList<Event> events = new ArrayList<Event>();
		ArrayList<String> persons = new ArrayList<String>();
		int EVENT_ID;

		try{

			//get all the events with all their attributes
			ResultSet rsE = stmt.executeQuery("SELECT * FROM Event");

			//Sets the pointer to the first line in the ResultSet, return if ResultSet is empty
			if(rsE.first() == false){
				return null;
			}

			//ITERATES THE EVENT RESULTSET
			do{

				//gets the event_id from the currently iterating event
				EVENT_ID = rsE.getInt(1);

				//get all the persons associated each event
				ResultSet rsP = stmt.executeQuery("SELECT name, username FROM Account WHERE event.event_id='" + EVENT_ID + "'");

				//Sets the pointer to the first line in the ResultSet, return if ResultSet is empty
				if(rsP.first() == false){
					return null;
				}

				//ITERATES THE PERSON RESULTSET
				do{

					//Create persons and add them to array
					Person person = new Person(rsP.getString(1), rsP.getString(2));
					persons.add(person.getUsername());

				}while(rsP.next());

				rsP = null;	//clear the ResultSet for next iterate

				String owner = null;

				//Finds the owner of the Event
				for(int i = 0; i<persons.size(); i++){
					if(persons.get(i).equals(rsE.getString(2))){

						//If owner shall not be in the persons list, change to owner = persons.remove(i);
						owner = persons.get(i);
					}

				}
				
				/////////////////////			
				/*	RESULTSET INDEX:
				 * 1 : event_id
				 * 2 : owner_username
				 * 3 : time_start
				 * 4 : time_end
				 * 5 : title
				 * 6 : location
				 * 7 : room_name
				 */
				/////////////////////

				//convert from java.sql.Timestamp to java.util.Date
				Date dateStart = rsE.getTimestamp(3);		//date start
				Date dateEnd = rsE.getTimestamp(4);			//date end

				//create new event and add all the persons involved
				Event event = new Event(EVENT_ID,			//event_id
						rsE.getString(5),					//eventTitle
						owner,								//owner_username
						persons,							//persons
						dateStart, dateEnd,					//date start, end
						rsE.getString(6),					//location 
						new Room(rsE.getString(7)));		//room

				//add this event to array
				events.add(event);

			}while(rsE.next());

			rsE = null; //clear the ResultSet

		}catch(Exception e){

			e.printStackTrace();
			
		}

		return events;
	}

	/**
	 * 
	 * @param events 
	 */
	public void updateEvents(ArrayList<Event> events){

		for(Event e : events){
			try {

				stmt.executeUpdate("UPDATE event SET" +
						"event_id = '" + e.getEventID() + "', " +
						"owner_username = '" + e.getEventOwner().getUsername() + "', " +
						"time_start = '" + e.getEventStartTime() + "', " +
						"time_end = '" + e.getEventEndTime() + "', " +
						"title = '" + e.getEventTitle() + "', " +
						"location = '" + e.getLocation() + "' ," +
						"room_name = '" + e.getRoom().getRoomName() + "'"
				);

			} catch (SQLException e1) {

				e1.printStackTrace();

			}
		}
	}
	
	/**
	 * 
	 * @return all the persons from the database
	 */
	public ArrayList<Person> getPersons(){
		ArrayList<Person> persons = new ArrayList<Person>();
		
		try{
			
			ResultSet rs = stmt.executeQuery("SELECT username, name FROM account");
			
			if(rs.first() == false){
				return null;
			}
			
			do{
				
				persons.add(new Person(rs.getString(1), rs.getString(2)));
				
			}while(rs.next());
			
			rs = null;
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		return persons;
	}
	
	/**
	 * 
	 * @return all the rooms from the database
	 */
	public ArrayList<Room> getRooms(){
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		try{
			ResultSet rs = stmt.executeQuery("SELECT name FROM room");
			
			if(rs.first() == false){
				return null;
			}
			
			do{
				
				rooms.add(new Room(rs.getString(1)));
				
			}while(rs.next());
			
			rs = null;
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		return rooms;
	}
	
	/**
	 * 
	 * @return all the notifications from the database
	 */
	public ArrayList<Notification> getNotifications(){
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		
		try{
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM notification");
			
			if(rs.first() == false){
				return null;
			}
			
			do{
				Notification n = new Notification(rs.getInt(1), rs.getInt(2), NotificationType.valueOf(rs.getString(3)));
				
				notifications.add(n);
				
			}while(rs.next());
			
			rs = null;
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		return notifications;
	}
}
