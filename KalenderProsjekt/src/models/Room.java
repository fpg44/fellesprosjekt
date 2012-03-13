package models;

/**
 * A class that represents a Room
 * @author JeppeE
 */

public class Room {
	
	private String roomName;
	private boolean isOccupied;
	
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
}