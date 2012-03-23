package no.ntnu.g44.models;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;

public class AttendanceHelper {
	
	public static HashMap<String, Color> colourmap = new HashMap<String, Color>();
	
	public static Color getColor(Event e, Person p){
		return colourmap.get(p.getUsername() + "" + e.getEventID());
	}
	
	public static void updateStatus(Event e, Person p, AttendanceStatusType status){
		colourmap.put(p.getUsername() + "" + e.getEventID(), AttendanceStatusType.getColor(status));
	}
	
	public static void updateStatus(int eID, String username, AttendanceStatusType status){
		colourmap.put(username + "" + eID, AttendanceStatusType.getColor(status));
	}
	public static Collection<Color> getAttendanceList(){
		return colourmap.values();
	}
}
