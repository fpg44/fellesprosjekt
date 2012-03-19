package no.ntnu.g44.controllers;

import java.util.ArrayList;

import no.ntnu.g44.models.Notification;
import no.ntnu.g44.models.NotificationType;

/**
 * @author JeppeE
 */
public class NotificationController {
	
	ArrayList<Notification> unseenNotifications;
	
	public NotificationController() {
		unseenNotifications = new ArrayList();
		
		//Notifications for testing purposes
		addNotification(new Notification(123, 123, NotificationType.CANCELLED));
		addNotification(new Notification(123, 123, NotificationType.INVITATION));
		addNotification(new Notification(123, 123, NotificationType.DECLINER));
		addNotification(new Notification(123, 123, NotificationType.CHANGED));
	}
	
	/**
	 * @return An array with all the notifications of a user.
	 */
	public ArrayList<Notification> getUnseenNotifications() {
		return unseenNotifications;
	}
	
	/**
	 * Removes the specified item from the notification list
	 * @param noti - The notification to be removed
	 */
	public void removeNotification(Notification noti) {
		unseenNotifications.remove(noti);
	}
	
	/**
	 * Adds a notification to the notification list
	 * @param noti - the notification to be added
	 */
	public void addNotification(Notification noti) {
		unseenNotifications.add(noti);
	}
}
