package no.ntnu.g44.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;

import no.ntnu.g44.controllers.Main;

/**
 * A class that represents a Room
 * @author JeppeE
 */

public class Room {
	
	// used as a 'custom location' flag
	public static final Room OTHER = new Room("OTHER");
	
	private String roomName;
	private PropertyChangeSupport pcs;
	
	/**
	 * This method should check the database for rooms which are not occupied
	 * within the given time range, and return an ArrayList<Room> of those.
	 */
	public static ArrayList<Room> getAvailableRooms(Date startTime, Date endTime) {
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		for (Room room : Main.currentProject.getRoomList())
			if (!room.isOccupied(startTime, endTime))
				rooms.add(room);
		
		/* this value indicates a custom location, and should always be 
		   included in the list */
		rooms.add(OTHER);
		
		return rooms;
	}
	
	/**
	 * Creates a new Room
	 * @param roomName
	 */
	public Room(String roomName) {
		this.roomName = roomName;
		
		pcs = new PropertyChangeSupport(this);
	}
	
	public void setRoomName(String name) {
		this.roomName = name;
	}
	
	public String getRoomName(){
		return roomName;
	}

	/**
	 * Checks whether this room is occupied within the given time interval.
	 * 
	 * @param startTime - a <code>Date</code> representing the beginning of the
	 * time interval
	 * 
	 * @param endTime - a <code>Date</code> representing the end of the time
	 * interval
	 * 
	 * @return Returns true if the room is occupied, false if not
	 */
	public boolean isOccupied(Date startTime, Date endTime) {
		for (Event event : Main.currentProject.getEventList()) {
			if (event.getRoom() != this)
				continue;
			
			if (event.getEventStartTime().before(endTime)
					&& event.getEventEndTime().after(startTime))
				return true;
		}
		return false;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}
	
	@Override
	public String toString() {
		return roomName;
	}
}
