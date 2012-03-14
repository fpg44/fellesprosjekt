package no.ntnu.g44.models;

public class Notification {
	
	private String message;
	private NotificationType type;
	
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
	
}
