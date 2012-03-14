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
		ArrayList<Person> persons = new ArrayList<Person>();

		try{
			ResultSet rsP = stmt.executeQuery("SELECT blabalba FROM Account");
			ResultSet rsE = stmt.executeQuery("SELECT blabalba FROM Event");

			if(rsP.first() == false || rsE.first() == false){
				return null;
			}
			do{
				do{
					
					//create persons and add them to array
					Person person = new Person(rsP.getString(1), rsP.getString(2));
					persons.add(person);
					
				}while(rsP.next());
				
				//create new event and add all the persons involved
				Date date = rsE.getTimestamp(2);
				Event event = new Event(rsE.getString(1), 
										persons, 
										date,
										rsE.getString(3), 
										new Room(rsE.getString(4)));
				//add this event to array
				events.add(event);
				
				//clear the person array for the next event
				persons.clear();
				
			}while(rsE.next());

		}catch(Exception e){
			e.printStackTrace();
		}

		return events;
	}
}
