package no.ntnu.g44.models;

import java.awt.Color;

public enum AttendanceStatusType{
	
	ATTENDING, NOT_ATTENDING, UNANSWERED;
	
	public static AttendanceStatusType getType(String type){
		
		switch( type ){
			case "ATTENDING" 		: return ATTENDING;
			case "NOT_ATTENDING"	: return NOT_ATTENDING;
			case "UNANSWERED" 		: return UNANSWERED;
			default 				: return null;
		}
	}
	
	public static Color getColor(AttendanceStatusType type){
		
		switch(type){
			case ATTENDING 		: return Color.green;
			case NOT_ATTENDING 	: return Color.red;
			case UNANSWERED 	: return new Color(255, 255, 0, 200);
//			case UNANSWERED 	: return Color.white;
			default 			: return null;
		}
	}
}
