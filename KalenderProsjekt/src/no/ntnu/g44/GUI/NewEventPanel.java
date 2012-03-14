package no.ntnu.g44.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
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

import no.ntnu.g44.models.Person;

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
	private JScrollPane eventDescriptionScroller;
	private JTextArea eventDescription;
	
	// (invited) participants--the right side of our prototype
	private JPanel participantsPanel;
	private JTextField searchField;
	private JList<Person> searchList;
	private JScrollPane searchListScroller;
	private JLabel invitedPersonsLabel;
	private JList<Person> invitedList;
	private JScrollPane invitedListScroller;
	// this model should contain all Person objects
	private DefaultListModel<Person> personsModel;
	// this ArrayList should be filled with the names of all persons
	private ArrayList<String> persons;
	// this model should contain the persons to invite
	private DefaultListModel<Person> participantsModel;
	
	// 'Save Event' and 'Cancel' buttons
	private JPanel buttonPanel;
	private JButton saveButton;
	private JButton cancelButton;
	
	public NewEventPanel(Person owner) {
		eventInformationPanel = new JPanel();
		ownerLabel = new JLabel("Arranged by");
		eventOwner = new JLabel(owner.getName());
		eventStartLabel = new JLabel("From");
		eventStartTime = new JSpinner();
		eventEndLabel = new JLabel("To");
		eventEndTime = new JSpinner();
		locationLabel = new JLabel("Location");
		location = new JComboBox();
		customLocationLabel = new JLabel("Custom location");
		customLocation = new JTextField(20);		// 20 columns
		// customLocation should only be enabled when 'Other' is selected
		// in the location JComboBox
		customLocation.setEnabled(false);
		eventDescriptionLabel = new JLabel("Description");
		eventDescription = new JTextArea(4, 20);	// 4 rows, 20 columns
		
		// add some padding between the edge of the JTextArea and entered text
		eventDescription.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		// put the description in a JScrollPane so scrollbars are added rather
		// than the textearea resizing itself when a lot of text is entered
		eventDescriptionScroller = new JScrollPane(eventDescription);
	
		participantsPanel = new JPanel();
		personsModel = new DefaultListModel<Person>();
		participantsModel = new DefaultListModel<Person>();
		searchField = new JTextField("Search", 20);	// 20 columns
		searchList = new JList<Person>(personsModel);
		searchListScroller = new JScrollPane(searchList);
		invitedPersonsLabel = new JLabel("Invited persons");
		invitedList = new JList<Person>(participantsModel);
		invitedListScroller = new JScrollPane(invitedList);
		
		populatePersonsModel();
		populatePersonsArray();
	
		buttonPanel = new JPanel();
		saveButton = new JButton("Save Event");
		cancelButton = new JButton("Cancel");
		
		eventInformationPanel.setBorder(BorderFactory.createTitledBorder(
				"Event information"));
		eventInformationPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = c.gridy = 0;
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
		eventInformationPanel.add(eventOwner, c);
		c.gridy = 1;
		eventInformationPanel.add(eventStartTime, c);
		c.gridy = 2;
		eventInformationPanel.add(eventEndTime, c);
		c.gridy = 3;
		eventInformationPanel.add(location, c);
		c.gridy = 4;
		eventInformationPanel.add(customLocation, c);
		c.gridy = 5;
		eventInformationPanel.add(eventDescriptionScroller, c);
		
		participantsPanel.setBorder(BorderFactory.createTitledBorder(
				"Add or remove participants"));
		participantsPanel.setLayout(new GridBagLayout());
		c.anchor = GridBagConstraints.WEST;
		c.gridx = c.gridy = 0;
		participantsPanel.add(searchField, c);
		c.gridy = 1;
		participantsPanel.add(searchList, c);
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1;
		c.gridy = 0;
		participantsPanel.add(invitedPersonsLabel, c);
		c.gridy = 1;
		participantsPanel.add(invitedList, c);
		
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		
		setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); // add padding
		setLayout(new GridBagLayout());
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = c.gridy = 0;
		add(eventInformationPanel, c);
		c.gridx = 1;
		add(participantsPanel, c);
		c.anchor = GridBagConstraints.EAST;
		c.gridx = c.gridy = 1;
		add(buttonPanel, c);
	}
	
	// for testing purposes
	public static void main(String[] args) {
		Person person = new Person("Foo Bar", "foobar");
		JFrame frame = new JFrame();
		NewEventPanel panel = new NewEventPanel(person);
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	private void populatePersonsModel() {
		String[] names = {	"Anders Andersen", "Bjørn Bjørnson",
							"Charlie Cleese", "Dolly Delta" };
		for (String name : names) {
			String username = name.toLowerCase().replace(" ", "");
			personsModel.addElement(new Person(name, username));
		}
	}
	
	private void populatePersonsArray() {
//		for (int i = 0; i < personsModel.getSize(); i++)
//			if (personsModel.get(i).getName() != null)
//				persons.add(personsModel.get(i).getName());
	}
}
