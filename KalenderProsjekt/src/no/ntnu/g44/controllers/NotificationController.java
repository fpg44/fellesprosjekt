package no.ntnu.g44.controllers;

import java.util.ArrayList;

import no.ntnu.g44.models.Notification;
import no.ntnu.g44.models.NotificationType;

public class NotificationController {
	
	ArrayList unseenNotifications;
	
	public NotificationController() {
		unseenNotifications = new ArrayList();

		unseenNotifications.add(new Notification(NotificationType.EVENT_CANCELLED));
		unseenNotifications.add(new Notification(NotificationType.EVENT_INVITATION));
		unseenNotifications.add(new Notification(NotificationType.EVENT_INVITATION));
		unseenNotifications.add(new Notification(NotificationType.EVENT_INVITATION));
		unseenNotifications.add(new Notification(NotificationType.EVENT_CANCELLED));
		unseenNotifications.add(new Notification(NotificationType.EVENT_CANCELLED));
	}
	
	public ArrayList<Notification> getUnseenNotifications() {
		return unseenNotifications;
	}
}
