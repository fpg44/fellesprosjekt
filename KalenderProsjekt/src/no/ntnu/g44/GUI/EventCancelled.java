package no.ntnu.g44.gui;


import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Person;
import no.ntnu.g44.models.Room;

public class EventCancelled extends JPanel{
	
	private Event event;
	
	private Person eventOwner;
	private Date startTime, endTime;
	private Room room;
	private String customLocation;
	
	private JLabel staticPersonNameLabel, saticStartTimeLabel, staticEndTimeLabel, staticLocationLabel;
	private JLabel personNameLabel, startTimeLabel, endTimeLabel, locationLabel; 
	private JTextArea textArea;
	
	public EventCancelled(Event event) {
		this.event = event;
		
		init();
	}
	
	private void init() {
		this.eventOwner = event.getEventOwner();
		this.startTime = event.getEventStartTime();
		this.endTime = event.getEventEndTime();
		this.room = event.getRoom();
		this.customLocation = event.getLocation();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Person person = new Person("Jeppe Eriksen", "jeppeer@gmail.com");
		Event newEvent = new Event(1, "TestEvent", person, null, new Date(2012,3,15,11,15),
				new Date(2012,3,15,13,6), null,null);
		EventCancelled ec = new EventCancelled(newEvent);
		
		frame.getContentPane();
	}
}
