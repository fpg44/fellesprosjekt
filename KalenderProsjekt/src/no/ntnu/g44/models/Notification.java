package no.ntnu.g44.models;

public class Notification {
	
	private String message, username;
	private NotificationType type;
	private int notificationID, eventID;
	
	/**
	 * Create a new <code>Notification</code> with the specified message and type. <br><br>
	 *
	 * @param message - the message to be displayed in a view <br><br>
	 * @param type - the type of notification, e.g. NotificationType.EVENT_INVITATION
	 */
	public Notification(NotificationType type) {
		this.type = type;
		this.message = setMessage(type);
	}
	/**
	 * The notification constructor with all fields
	 * @param notificationID
	 * @param username
	 * @param eventID
	 * @param Message
	 * @param type
	 */
	public Notification(int notificationID, String username, int eventID,  String Message, NotificationType type){
		this.notificationID = notificationID;
		this.username = username;
		this.eventID = eventID;
		this.message = message;
		this.type = type;
	}

	/**
	 * @return The message that is to be displayed in the notification
	 * area in MainFrame
	 */
	public String getMessage() {
		return message;
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
		else if (type == NotificationType.EVENT_TIME_CHANGED) return "Event time changed";
		else return "Participant declined invitation";
	}
	@Override
	public String toString() {
		return message;
	}
}
