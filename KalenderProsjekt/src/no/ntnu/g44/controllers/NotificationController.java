package no.ntnu.g44.controllers;

import java.util.ArrayList;

import no.ntnu.g44.models.Notification;
import no.ntnu.g44.models.NotificationType;
/**
 * 
 * @author JeppeE
 *
 */
public class NotificationController {
	
	ArrayList unseenNotifications;
	
	public NotificationController() {
		unseenNotifications = new ArrayList();
		
		//Hard coded notifications for testing purposes
		unseenNotifications.add(new Notification(123, 123, NotificationType.CANCELLED));
		unseenNotifications.add(new Notification(123, 123, NotificationType.INVITATION));
		unseenNotifications.add(new Notification(123, 123, NotificationType.DECLINER));
		unseenNotifications.add(new Notification(123, 123, NotificationType.CHANGED));
	}
	
	/**
	 * 
	 * @return An array with all the notifications of a user.
	 */
	public ArrayList<Notification> getUnseenNotifications() {
		return unseenNotifications;
	}
}
