package no.ntnu.g44.models;

public enum NotificationType {
	EVENT_INVITATION("New event invitation"),
	EVENT_CHANGED("Event changed"),
	EVENT_CANCELLED("Event cancelled"),
	PARTICIPANT_DECLINED("Participant declined"),
	NO_NOTIFICATION("Notifications");
	
	private String msg;
	
	private NotificationType(String msg){
		this.msg = msg;
	}
	
	public String getMsg(){
		return msg;
	}
	public void setText(String msg){
		this.msg = msg;
	}
}
