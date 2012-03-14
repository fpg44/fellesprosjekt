package no.ntnu.g44.models;

import java.util.Date;
import java.util.ArrayList;

/**
 * Class representing an event
 * @author JeppeE
 *
 */
public class Event {
	
	private String eventTitle, location;
	private ArrayList<Person> participants;
	private Date eventStartTime;
	private Date eventEndTime;
	private Room room;

	/**
	 * Creates a new Event
	 * @param eventTitle
	 * @param participants
	 * @param eventTime
	 * @param location
	 * @param room
	 */
	public Event(String eventTitle, ArrayList<Person> participants, 
			Date eventStartTime, Date eventEndTime, String location, Room room) {
		this.eventTitle = eventTitle;
		this.participants = participants;
		this.eventStartTime = eventStartTime;
		this.eventEndTime = eventEndTime;
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

	public Date getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(Date eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public Date getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(Date eventEndTime) {
		this.eventEndTime = eventEndTime;
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
