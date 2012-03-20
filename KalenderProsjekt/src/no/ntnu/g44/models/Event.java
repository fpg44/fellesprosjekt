package no.ntnu.g44.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import no.ntnu.g44.controllers.Main;

/**
 * Class representing an event
 * @author JeppeE
 *
 */
public class Event {
	
	private String eventDescription, location;
	private ArrayList<Person> participants;
	private ArrayList<String> participantsStrings;
	private Date eventStartTime;
	private Date eventEndTime;
	private Room room;
	private String roomString;
	private Person eventOwner;
	private String eventOwnerString;
	private int eventID;
	private boolean hasOldEvent = false;
	private String oldEventString;
	
	
	public String getRoomString() {
		return roomString;
	}
	public void setRoomString(String roomString) {
		this.roomString = roomString;
	}
	/**
	 * This member variable provides functionality for notifying of changes to
	 * the <code>Group</code> class.
	 */
	private PropertyChangeSupport propChangeSupp;
	/** this is for testing
	 * creates a new Event
	 * no parameters
	 */
	public Event(){
		eventDescription = "desc";
		location = "somewhere";
		participants = new ArrayList<Person>();
		eventStartTime = new Date();
		eventEndTime = new Date();
		room = Room.OTHER;
		eventOwner = new Person("TestPerson", "tester69");
		eventID = 0;
		participants.add(eventOwner);
	}
	/** this should be deleted
	 * Creates a new Event
	 * @param eventDescription
	 * @param participants
	 * @param eventStartDate
	 * @param eventEndDate
	 * @param location
	 * @param room
	 */
	@Deprecated
	public Event(String eventDescription, ArrayList<Person> participants, 
			Date eventStartTime, Date eventEndTime, String location, Room room) {
		this.eventDescription = eventDescription;
		this.participants = participants;
		this.eventStartTime = eventStartTime;
		this.eventEndTime = eventEndTime;
		this.location = location;
		this.room = room;
		propChangeSupp = new PropertyChangeSupport(this);
	}
	
	/**	This is the one used within the application (the right one)
	 * @param event_ID
	 * @param eventTitle
	 * @param eventOwner
	 * @param participants
	 * @param eventStartTime
	 * @param eventEndTime
	 * @param location
	 * @param room
	 */
	public Event(int eventID, String eventTitle, Person eventOwner, ArrayList<Person> participants, 
			Date eventStartTime, Date eventEndTime, String location, Room room) {
		this.eventID = eventID;
		this.eventDescription = eventTitle;
		this.eventOwner = eventOwner;
		this.participants = participants;
		this.eventStartTime = eventStartTime;
		this.eventEndTime = eventEndTime;
		this.location = location;
		this.room = room;
		propChangeSupp = new PropertyChangeSupport(this);
	}
	
	/**
	 * This constructor is for creating new events (because you don't know
	 * the eventID at that point). NOTE: The eventID is always -1 when using
	 * this constructor.
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
		this(-1, eventTitle, eventOwner, participants, eventStartTime,
				eventEndTime, location, room);
	}
	
	/**
	 * This is used when red from database and thrown after parsed to XML.
	 * This constructor will also prevent duplicates of objects like Person and Room
	 * when fetched from database to xml
	 * @param eventID
	 * @param eventTitle
	 * @param eventOwner
	 * @param participants
	 * @param eventStartTime
	 * @param eventEndTime
	 * @param location
	 * @param room
	 */
	public Event(int eventID, String eventTitle, String eventOwner, ArrayList<String> participants, 
			Date eventStartTime, Date eventEndTime, String location, String roomString){
		this.eventID = eventID;
		this.eventDescription = eventTitle;
		this.eventOwnerString = eventOwner;
		this.participantsStrings = participants;
		this.eventStartTime = eventStartTime;
		this.eventEndTime = eventEndTime;
		this.location = location;
		this.roomString = roomString;
		propChangeSupp = new PropertyChangeSupport(this);
		
	}
	
	public ArrayList<String> getParticipantsStrings() {
		return participantsStrings;
	}

	public void setParticipantsStrings(ArrayList<String> participantsStrings) {
		this.participantsStrings = participantsStrings;
	}

	public String getEventOwnerString() {
		if(eventOwner == null){
			return "";
		}
		return eventOwner.toString();
	}

	public void setEventOwnerString(String eventOwnerString) {
		this.eventOwnerString = eventOwnerString;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public void setEventOwner(Person eventOwner) {
		this.eventOwner = eventOwner;
	}

	public Person getEventOwner() {
		return eventOwner;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventTitle) {
		this.eventDescription = eventTitle;
	}

	public String getLocation() {
		if (room == Room.OTHER)
			return location;
		else{
			return "lol";
//			return room.getRoomName();
		}
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

	public boolean isInWeek(int weekNr) {
		if(eventStartTime != null){
			Calendar cal =new GregorianCalendar();
			cal.setTime(eventStartTime);
			return cal.get(Calendar.WEEK_OF_YEAR) == weekNr;			
		}
		return false;
	}
	
	public String getOldEventId(){
		if(hasOldEvent == true){
			return oldEventString;
		}
		return null;
	}
	public boolean isHasOldEvent() {
		return hasOldEvent;
	}
	public void setHasOldEvent(boolean hasOldEvent) {
		this.hasOldEvent = hasOldEvent;
	}
	public void setOldEvent(String oldEventString) {
		this.oldEventString = oldEventString;
	}
}
