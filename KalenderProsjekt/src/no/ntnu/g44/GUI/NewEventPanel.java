package no.ntnu.g44.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NewEventPanel extends JPanel {

	// information about the event--the left side of our prototype
	private JPanel eventInformationPanel;
	private JLabel ownerLabel;
	private JLabel eventOwner;				// the person organizing the event
	private JLabel eventStartLabel;
	private JSpinner eventStartTime;
	private JLabel eventEndLabel;
	private JSpinner eventEndTime;
	private JLabel locationLabel;
	private JComboBox location;
	private JLabel customLocationLabel;
	private JTextField customLocation;
	private JLabel eventDescriptionLabel;
	private JScrollPane eventDescriptionScrolling;
	private JTextArea eventDescription;
	
	// (invited) participants--the right side of our prototype
	private JPanel participantsPanel;
	private JLabel addPersonLabel;
	private JTextField searchField;
	private JList searchList;
	private JLabel invitedPersonsLabel;
	private JList invitedList;
	
	// 'Save Event' and 'Cancel' buttons
	private JPanel buttonPanel;
	private JButton saveButton;
	private JButton cancelButton;
	
	public NewEventPanel() {
		eventInformationPanel = new JPanel();
		ownerLabel = new JLabel("Arranged by");
		this.eventOwner = new JLabel("Ola Nordmann");
		eventStartLabel = new JLabel("From");
		eventStartTime = new JSpinner();
		eventEndLabel = new JLabel("To");
		eventEndTime = new JSpinner();
		locationLabel = new JLabel("Location");
		location = new JComboBox();
		customLocationLabel = new JLabel("Custom location");
		customLocation = new JTextField(20);		// 20 columns
		eventDescriptionLabel = new JLabel("Description");
		eventDescription = new JTextArea(4, 20);	// 4 rows, 20 columns
		
		// add some padding between the edge of the JTextArea and entered text
		eventDescription.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		// put the description in a JScrollPane so scrollbars are added rather
		// than the textearea resizing itself when a lot of text is entered
		eventDescriptionScrolling = new JScrollPane(eventDescription);
	
		participantsPanel = new JPanel();
		addPersonLabel = new JLabel("Find person");
		searchField = new JTextField();
		searchList = new JList();
		invitedPersonsLabel = new JLabel("Invited persons");
		invitedList = new JList();
	
		buttonPanel = new JPanel();
		saveButton = new JButton("Save Event");
		cancelButton = new JButton("Cancel");
		
		eventInformationPanel.setBorder(BorderFactory.createTitledBorder(
				"Event information"));
		eventInformationPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		eventInformationPanel.add(ownerLabel, c);
		c.gridy = 1;
		eventInformationPanel.add(eventStartLabel, c);
		c.gridy = 2;
		eventInformationPanel.add(eventEndLabel, c);
		c.gridy = 3;
		eventInformationPanel.add(locationLabel, c);
		c.gridy = 4;
		eventInformationPanel.add(customLocationLabel, c);
		c.gridy = 5;
		eventInformationPanel.add(eventDescriptionLabel, c);
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1;
		c.gridy = 0;
		eventInformationPanel.add(this.eventOwner, c);
		c.gridy = 1;
		eventInformationPanel.add(eventStartTime, c);
		c.gridy = 2;
		eventInformationPanel.add(eventEndTime, c);
		c.gridy = 3;
		eventInformationPanel.add(location, c);
		c.gridy = 4;
		eventInformationPanel.add(customLocation, c);
		c.gridy = 5;
		eventInformationPanel.add(eventDescriptionScrolling, c);
		
		participantsPanel.setBorder(BorderFactory.createTitledBorder(
				"Add or remove participants"));
		
		// add margin
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		// add contents
		add(eventInformationPanel);
	}
	
	// for testing purposes
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new NewEventPanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
