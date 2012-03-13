package models;

public class Notification {
	
	private String message;
	private NotificationType type;
	
	/**
	 * Create a new <code>Notification</code> with the specified message and type. <br><br>
	 *
	 * @param message - the message to be displayed in a view <br><br>
	 * @param type - the type of notification, e.g. NotificationType.EVENT_INVITATION
	 */
	public Notification(String message, NotificationType type) {
		this.message = message;
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public NotificationType getType() {
		return type;
	}
}
