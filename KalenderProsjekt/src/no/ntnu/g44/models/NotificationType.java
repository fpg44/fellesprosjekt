package no.ntnu.g44.models;

public enum NotificationType {
	EVENT_INVITATION,
	EVENT_TIME_CHANGED,
	EVENT_CANCELLED,
	PARTICIPANT_DECLINED_INVITATION;
	
	public static NotificationType convertFromString(String type){
		
		switch(type){
		case "INVITATION" 	: return EVENT_INVITATION;
		case "CHANGED" 		: return EVENT_TIME_CHANGED;
		case "CANCELED" 	: return EVENT_CANCELLED;
		case "DECLINER" 	: return PARTICIPANT_DECLINED_INVITATION;
		default				: return null;
		}
	}
}
