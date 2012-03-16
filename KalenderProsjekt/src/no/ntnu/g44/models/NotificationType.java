package no.ntnu.g44.models;

public enum NotificationType {
	INVITATION("New event invitation"),
	CHANGED("Event changed"),
	CANCELLED("Event cancelled"),
	DECLINER("Participant declined");
	
	private String msg;
	
	private NotificationType(String msg){
		this.msg = msg;
	}
	
	public String getMsg(){
		return msg;
	}
}
