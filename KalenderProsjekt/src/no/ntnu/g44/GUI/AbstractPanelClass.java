package no.ntnu.g44.gui;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Person;
import no.ntnu.g44.models.Room;

public abstract class AbstractPanelClass {
	protected Event event;

	protected JPanel panel;
	protected JFrame frame;

	protected Person eventOwner;
	protected Date startTime, endTime;
	protected Room room;
	protected String customLocation;

	protected JLabel staticPersonNameLabel, staticEventTitleLabel, staticStartTimeLabel, staticEndTimeLabel, staticLocationLabel;
	protected JLabel personNameLabel, eventTitleLabel, startTimeLabel, endTimeLabel, locationLabel; 
	protected JTextArea textArea;

	protected JButton okButton;
	
	protected GridBagConstraints c;
	
	protected void setPanelLayout() {
		
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 0, 5, 10);
		c.ipadx = 5;
		c.ipady = 5;

		//Add the static labels
		c.gridx = 0;
		c.gridy = 0;
		panel.add(staticPersonNameLabel, c);

		c.gridx = 0;
		c.gridy = 1;
		panel.add(staticStartTimeLabel, c);

		c.gridx = 0;
		c.gridy = 2;
		panel.add(staticEndTimeLabel, c);

		c.gridx = 0;
		c.gridy = 3;
		panel.add(staticLocationLabel, c);

		//Add the non-static labels
		c.gridx = 1;
		c.gridy = 0;
		panel.add(personNameLabel, c);
		
		c.gridx = 1;
		c.gridy = 1;
		panel.add(startTimeLabel, c);

		c.gridx = 1;
		c.gridy = 2;
		panel.add(endTimeLabel, c);

		c.gridx = 1;
		c.gridy = 3;
		panel.add(locationLabel, c);

		//Add the textarea
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		textArea.setEditable(false);
		panel.add(textArea, c);
		
	}
	
	public void init(Event event) {
		this.event = event;
		this.eventOwner = event.getEventOwner();
		this.startTime = event.getEventStartTime();
		this.endTime = event.getEventEndTime();
		this.room = event.getRoom();
		this.customLocation = event.getLocation();

		staticPersonNameLabel = new JLabel("Event owner:");
		staticEventTitleLabel = new JLabel("Event title:");
		staticStartTimeLabel = new JLabel("Start time:");
		staticEndTimeLabel = new JLabel("End time:");
		staticLocationLabel = new JLabel("Location:");

		personNameLabel = new JLabel(eventOwner.getName());
		eventTitleLabel = new JLabel(event.getEventDescription());
		startTimeLabel = new JLabel(startTime.toString());
		endTimeLabel = new JLabel(endTime.toString());
		locationLabel = new JLabel(event.getLocation());
		
		textArea = new JTextArea();
		panel.setLayout(new GridBagLayout());
	}
	
	/**
	 * Closes the window.
	 */
	protected void closeWindow() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
}
