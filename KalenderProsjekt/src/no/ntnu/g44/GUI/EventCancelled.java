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
	
	private JLabel staticPersonNameLabel, staticStartTimeLabel, staticEndTimeLabel, staticLocationLabel;
	private JLabel personNameLabel, startTimeLabel, endTimeLabel, locationLabel; 
	private JTextArea textArea;
	
	public EventCancelled(Event event) {
		init();
		
		/*
		staticPersonNameLabel.setText("Event Owner:");
		staticStartTimeLabel.setText("Start time:");
		staticEndTimeLabel.setText("End time:");
		staticLocationLabel.setText("Location:");
		
		personNameLabel.setText(eventOwner.getName());
		startTimeLabel.setText(startTime.toString());
		endTimeLabel.setText(endTime.toString());
		
		if (event.getRoom() != null) {
			locationLabel.setText(event.getRoom().getRoomName());
		}
		else {
			locationLabel.setText(customLocation);
		}
		*/
	}
	
	private void init() {
		this.event = event;
		this.eventOwner = event.getEventOwner();
		this.startTime = event.getEventStartTime();
		this.endTime = event.getEventEndTime();
		this.room = event.getRoom();
		this.customLocation = event.getLocation();
		
		staticPersonNameLabel = new JLabel("Event owner:");
		staticStartTimeLabel = new JLabel("Start time;");
		staticEndTimeLabel = new JLabel("End time:");
		staticLocationLabel = new JLabel("Location:");
		
		personNameLabel = new JLabel(eventOwner.getName());
		startTimeLabel = new JLabel(startTime.toString());
		endTimeLabel = new JLabel(endTime.toString());
		
		locationLabel = new JLabel();
		
		if (event.getRoom() != null) {
			locationLabel.setText(event.getRoom().getRoomName());
		}
		else {
			locationLabel.setText(customLocation);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		Person person = new Person("Jeppe Eriksen", "jeppeer@gmail.com");
		Event newEvent = new Event("TestEvent", person, null, new Date(2012,3,15,11,15),
				new Date(2012,3,15,13,6), "G138" ,null);
		EventCancelled panel = new EventCancelled(newEvent);
		
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
