package no.ntnu.g44.models;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;

public class AttendanceHelper {
	
	public static HashMap<String, AttendanceStatusType> colourmap = new HashMap<String, AttendanceStatusType>();
	
	public static Color getColor(Event e, Person p){
		if(colourmap.get(p.getUsername() + "" + e.getEventID()) == null)return null;
		return AttendanceStatusType.getColor(colourmap.get(p.getUsername() + "" + e.getEventID()));
	}
	
	public static void updateStatus(Event e, Person p, AttendanceStatusType status){
		colourmap.put(p.getUsername() + "" + e.getEventID(), status);
	}
	
	public static void updateStatus(int eID, String username, AttendanceStatusType status){
		colourmap.put(username + "" + eID, status);
	}
	public static Collection<AttendanceStatusType> getAttendanceList(){
		return colourmap.values();
	}
}
