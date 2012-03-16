package no.ntnu.g44.models;

public class AttendanceStatus {

	private String username;
	private int eventID;
	private AttendanceStatusType status;
	
	public AttendanceStatus(String username, int eventID, AttendanceStatusType status){
		this.username = username;
		this.eventID = eventID;
		this.status = status;
	}
}
