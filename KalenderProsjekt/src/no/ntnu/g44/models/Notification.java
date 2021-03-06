package no.ntnu.g44.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import no.ntnu.g44.controllers.Main;

public class Notification {
	
	private String message;
	private NotificationType type;
	private int notificationID, eventID;
	private PropertyChangeSupport pcs;
	private String person;
	
	/**
	 * Create a new <code>Notification</code> with the specified message and type. <br><br>
	 *
	 * @param message - the message to be displayed in a view <br><br>
	 * @param type - the type of notification, e.g. NotificationType.EVENT_INVITATION
	 */
	/*
	public Notification(NotificationType type) {
		this.type = type;
		this.message = setMessage(type);
	}
	*/
	/**
	 * DO NOT USE THIS CONSTRUCTOR USE Notification(int, type)
	 * The notification constructor with all fields
	 * @param notificationID
	 * @param username
	 * @param eventID
	 * @param Message
	 * @param type
	 */
	public Notification(int notificationID, int eventID, NotificationType type, String person){
		this.notificationID = notificationID;
		this.eventID = eventID;
		this.type = type;
		this.person = person;
		
		pcs = new PropertyChangeSupport(this);
	}
	
	public void setType(NotificationType type) {
		pcs.firePropertyChange("notificationType", this.type, type);
		this.type = type;
	}

	public Notification(int eventId, NotificationType type, String person){
		this.notificationID = Main.currentProject.generateID();
		this.eventID = eventId;
		this.type = type;
		this.person = person;
		
		pcs = new PropertyChangeSupport(this);
	}

	public int getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(int notificationID) {
		this.notificationID = notificationID;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	/**
	 * @return The message that is to be displayed in the notification
	 * area in MainFrame
	 */
	public String getMessage() {
		return type.getMsg();
	}

	public NotificationType getType() {
		return type;
	}

	/**
	 * Sets the message that is to be displayed in the notification
	 * area in MainFrame
	 * @param NotficationType
	 * @return Message that is to be displayed
	 */
	public String setMessage(NotificationType type) {
		if(type == NotificationType.EVENT_CANCELLED) return "Event cancelled";
		else if (type == NotificationType.EVENT_INVITATION) return "New event invitation";
		else if (type == NotificationType.EVENT_CHANGED) return "Event time changed";
		else return "Participant declined invitation";
	}
	@Override
	public String toString() {
		return message;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
		
	}
	
	public void setPerson(String p){
		this.person = p;
	}
	
	public Person getPerson(){
		for(Person person : Main.currentProject.getPersonList()){
			if(person.getUsername().equals(this.person)){
				return person;
			}
		}
		return null;
	}
	
	public String getPersonString(){
		return this.person;
	}
}
