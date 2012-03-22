package no.ntnu.g44.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import no.ntnu.g44.controllers.Main;
import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Person;

public class EditEventPanel extends JPanel {
	
	// our container window
	JFrame frame;
	
	// the Event to be edited
	Event originalEvent;
	
	// the Person who owns the Event; this is required to create a new Event
	Person eventOwner;
	
	/* upon saving, a new Event is created rather than the old one being 
	   overwritten; this NewEventPanel creates that new Event */
	NewEventPanel newEventPanel;
	
	// headings to seperate the original and new events
	JLabel originalEventHeading;
	JLabel newEventHeading;
	
	// components for displaying the details of the original Event
	JPanel originalEventPanel;
	JPanel originalEventInfoPanel;
	JLabel ownerLabel;
	JLabel ownerNameLabel;
	JLabel fromLabel;
	JTextField fromDateField;
	JLabel toLabel;
	JTextField toDateField;
	JLabel locationLabel;
	JTextField locationField;
	JLabel descriptionLabel;
	JTextArea descriptionText;
	JScrollPane descriptionTextScroller;
	JPanel originalEventParticipantsPanel;
	JList<Person> participantsList;
	JScrollPane participantsListScroller;
	DefaultListModel<Person> participantsListModel;
	
	/**
	 * An <code>EditEventPanel</code> provides the interface for editing an
	 * existing event. <br><br>
	 * 
	 * @param originalEvent - the Event to be edited <br><br>
	 * @param eventOwner - the Person editing this Event; must be the Event's
	 * owner <br><br>
	 * @param frame - the JFrame to contain the EditEventPanel
	 */
	public EditEventPanel(Event originalEvent, Person eventOwner, JFrame frame){
		this.frame = frame;
		this.eventOwner = eventOwner;
		this.originalEvent = originalEvent;
		
		ownerLabel = new JLabel("Arranged by");
		ownerNameLabel = new JLabel(originalEvent.getEventOwnerString());
		fromLabel = new JLabel("From");
		fromDateField = new JTextField(originalEvent.getEventStartTime()
				.toString());
		fromDateField.setEditable(false);
		toLabel = new JLabel("To");
		toDateField = new JTextField(originalEvent.getEventEndTime()
				.toString());
		toDateField.setEditable(false);
		locationLabel = new JLabel("Location");
		locationField = new JTextField(originalEvent.getLocation());
		locationField.setEditable(false);
		descriptionLabel = new JLabel("Description");
		descriptionText = new JTextArea(originalEvent.getEventDescription());
		descriptionText.setEditable(false);
		descriptionTextScroller = new JScrollPane(descriptionText);
		
		participantsListModel = new DefaultListModel<Person>();
		for (Person person : originalEvent.getParticipants())
			participantsListModel.addElement(person);
		participantsList = new JList<Person>(participantsListModel);
		participantsListScroller = new JScrollPane(participantsList);
		
		originalEventParticipantsPanel = new JPanel();
		originalEventParticipantsPanel.setBorder(BorderFactory.createTitledBorder(
				"Participants"));
		originalEventParticipantsPanel.add(participantsListScroller);
		
		originalEventInfoPanel = new JPanel(new GridBagLayout());
		originalEventInfoPanel.setBorder(BorderFactory.createTitledBorder(
				"Event information"));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = c.gridy = 0;
		originalEventInfoPanel.add(ownerLabel, c);
		c.gridy = 1;
		originalEventInfoPanel.add(fromLabel, c);
		c.gridy = 2;
		originalEventInfoPanel.add(toLabel, c);
		c.gridy = 3;
		originalEventInfoPanel.add(locationLabel, c);
		c.gridy = 4;
		originalEventInfoPanel.add(descriptionLabel, c);
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1;
		c.gridy = 0;
		originalEventInfoPanel.add(ownerNameLabel, c);
		c.gridy = 1;
		originalEventInfoPanel.add(fromDateField, c);
		c.gridy = 2;
		originalEventInfoPanel.add(toDateField, c);
		c.gridy = 3;
		originalEventInfoPanel.add(locationField, c);
		c.gridy = 4;
		originalEventInfoPanel.add(descriptionTextScroller, c);
		
		originalEventParticipantsPanel = new JPanel();
		originalEventParticipantsPanel.setBorder(BorderFactory
				.createTitledBorder("Participants"));
		originalEventParticipantsPanel.add(participantsListScroller);
		
		originalEventPanel = new JPanel(new GridBagLayout());
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = c.gridy = 0;
		originalEventPanel.add(originalEventInfoPanel, c);
		c.gridx = 1;
		originalEventPanel.add(originalEventParticipantsPanel, c);
		
		newEventPanel = new NewEventPanel(
				Main.currentProject.getLoggedInPerson(), frame, originalEvent);
		
		setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); // add padding
		
		Font headingFont = new Font("Helvetica", Font.BOLD, 18);
		originalEventHeading = new JLabel("Original event");
		originalEventHeading.setFont(headingFont);
		newEventHeading = new JLabel("New (edited) event");
		newEventHeading.setFont(headingFont);
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(originalEventHeading);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(originalEventPanel);
		add(Box.createRigidArea(new Dimension(0, 40)));
		add(newEventHeading);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(newEventPanel);
		
		frame.setTitle("Edit Event");
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}
}