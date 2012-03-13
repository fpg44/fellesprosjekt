package models;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Class representing an event
 * @author JeppeE
 *
 */
public class Event {
	
	private String eventTitle, location;
	private ArrayList<Person> participants;
	private Date eventTime;
	private Room room;

	/**
	 * Creates a new Event
	 * @param eventTitle
	 * @param participants
	 * @param eventTime
	 * @param location
	 * @param room
	 */
	public Event(String eventTitle, ArrayList<Person> participants, Date eventTime, String location, Room room) {
		this.eventTitle = eventTitle;
		this.participants = participants;
		this.eventTime = eventTime;
		this.location = location;
		this.room = room;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ArrayList<Person> getParticipants() {
		return participants;
	}

	public void setParticipants(ArrayList<Person> participants) {
		this.participants = participants;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	/**
	 * Adds participant to this event
	 * @param participant
	 */
	public void addParticipant(Person participant) {
		participants.add(participant);
	}
	
	/**
	 * Removes participant from this event
	 * @param participant
	 */
	public void removeParticipant(Person participant) {
		if (participants.contains(participant)) {
			participants.remove(participant);
		}
	}
}
