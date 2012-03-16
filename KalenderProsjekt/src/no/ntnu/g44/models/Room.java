package no.ntnu.g44.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;

/**
 * A class that represents a Room
 * @author JeppeE
 */

public class Room {
	
	private String roomName;
	private boolean isOccupied;
	private PropertyChangeSupport pcs;
	
	/**
	 * This method should check the database for rooms which are not occupied
	 * within the given time range, and return an ArrayList<Room> of those.
	 */
	
	public Room(String roomName, boolean isOccupied){
		this.roomName = roomName;
		this.isOccupied = isOccupied;
		
		pcs = new PropertyChangeSupport(this);
	}
	
	public static ArrayList<Room> getAvailableRooms(Date startTime, Date endTime) {
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		// MOCK CONTENT
		String[] roomNames = { "P123", "P298", "G193", "F123" };
		for (String name : roomNames)
			rooms.add(new Room(name));
		// END MOCK CONTENT
		
		return rooms;
	}
	
	/**
	 * Creates a new Room
	 * @param roomName
	 */
	public Room(String roomName) {
		this.roomName = roomName;
	}
	
	public void setRoomName(String name) {
		this.roomName = name;
	}
	
	public String getRoomName(){
		return roomName;
	}

	/**
	 * @return Returns whether this room is occupied or not 
	 */
	public boolean isOccupied() {
		return isOccupied;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}
}
