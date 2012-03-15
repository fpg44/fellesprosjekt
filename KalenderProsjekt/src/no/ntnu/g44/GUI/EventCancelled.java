package no.ntnu.g44.gui;

import java.awt.Event;
import java.sql.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import no.ntnu.g44.models.Person;
import no.ntnu.g44.models.Room;

public class EventCancelled extends JPanel{
	
	private Event event;
	
	private Person eventOwner;
	private Date startTime, endTime;
	private Room room;
	private String cusomtLocation;
	
	private JLabel staticPersonNameLabel, saticStartTimeLabel, staticEndTimeLabel, staticLocationLabel;
	private JLabel personNameLabel, startTimeLabel, endTimeLabel, locationLabel; 
	private JTextArea textArea;
	
	public EventCancelled(Event event) {
		this.event = event;
		init();
		
		/*
		this.eventOwner = eventOwner;
		this.startTime = startTime;
		this.endTime = endTime;
		this.room = room;
		this.cusomtLocation = customLocation;
		*/
	}
	
	private void init() {
		String eventTitle = ((Event) event).getEventTitle();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Person person = new Person("Jeppe Eriksen", "jeppeer@gmail.com");
		Date startDate = 
	}
}
