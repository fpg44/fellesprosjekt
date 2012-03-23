package no.ntnu.g44.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AttendanceStatus {

	private String username;
	private int eventID;
	private AttendanceStatusType status = AttendanceStatusType.UNANSWERED;
	private PropertyChangeSupport pcs;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public AttendanceStatusType getStatus() {
		return status;
	}

	public void setStatus(AttendanceStatusType status) {
		this.status = status;
	}

	public AttendanceStatus(String username, int eventID, AttendanceStatusType status){
		this.username = username;
		this.eventID = eventID;
		this.status = status;
		
		pcs = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
}
