package no.ntnu.g44.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AttendanceStatus {

	private String username;
	private int eventID;
	private AttendanceStatusType status;
	private PropertyChangeSupport pcs;
	
	public AttendanceStatus(String username, int eventID, AttendanceStatusType status){
		this.username = username;
		this.eventID = eventID;
		this.status = status;
		
		pcs = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}
}
