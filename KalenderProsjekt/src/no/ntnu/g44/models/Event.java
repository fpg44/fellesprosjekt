package no.ntnu.g44.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
	private Person eventOwner;
	
	
	/**
	 * This member variable provides functionality for notifying of changes to
	 * the <code>Group</code> class.
	 */
	private PropertyChangeSupport propChangeSupp;

	/**
	 * Creates a new Event
	 * @param eventTitle
	 * @param participants
	 * @param eventStartDate
	 * @param eventEndDate
	 * @param location
	 * @param room
	 */
	@Deprecated
	public Event(String eventTitle, ArrayList<Person> participants, 
			Date eventStartTime, Date eventEndTime, String location, Room room) {
		this.eventTitle = eventTitle;
		this.participants = participants;
		this.eventStartTime = eventStartTime;
		this.eventEndTime = eventEndTime;
		this.location = location;
		this.room = room;
	}
	
	/**
	 * 
	 * @param eventTitle
	 * @param eventOwner
	 * @param participants
	 * @param eventStartTime
	 * @param eventEndTime
	 * @param location
	 * @param room
	 */
	public Event(String eventTitle, Person eventOwner, ArrayList<Person> participants, 
			Date eventStartTime, Date eventEndTime, String location, Room room) {
		this.eventTitle = eventTitle;
		this.eventOwner = eventOwner;
		this.participants = participants;
		this.eventStartTime = eventStartTime;
		this.eventEndTime = eventEndTime;
		this.location = location;
		this.room = room;
		propChangeSupp = new PropertyChangeSupport(this);
	}
	
	public Person getEventOwner() {
		return eventOwner;
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
	
	/**
	 * Add a {@link java.beans.PropertyChangeListener} to the listener list.
	 * 
	 * @param listener The {@link java.beans.PropertyChangeListener} to be added.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propChangeSupp.addPropertyChangeListener(listener);
	}
	
	/**
	 * Remove a {@link java.beans.PropertyChangeListener} from the listener list.
	 * 
	 * @param listener The {@link java.beans.PropertyChangeListener} to be removed.
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propChangeSupp.removePropertyChangeListener(listener);
	}
}
