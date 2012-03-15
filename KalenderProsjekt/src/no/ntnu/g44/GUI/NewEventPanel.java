package no.ntnu.g44.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
import no.ntnu.g44.models.Room;

public class NewEventPanel extends JPanel {
	
	// our container window
	JFrame frame;
	
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
	private SearchListener searchListener;
	private JTextField searchField;
	private JList<Person> searchList;
	private JScrollPane searchListScroller;
	private JLabel invitedPersonsLabel;
	private JList<Person> invitedList;
	private JScrollPane invitedListScroller;
	private JLabel addPersonToParticipantsListLabel;
	private ImageIcon addPersonToParticipantsIcon;
	private JLabel removePersonFromParticipantsListLabel;
	private ImageIcon removePersonFromParticipantsIcon;
	private JPanel participantsButtonPanel;
	// this model should contain all Person objects
	private DefaultListModel<Person> personsModel;
	// this ArrayList should be filled with the names of all persons
	private ArrayList<String> persons;
	// this model should contain the persons to invite
	private DefaultListModel<Person> participantsModel;
	
	// 'Save Event' and 'Cancel' buttons
	private ButtonListener buttonListener;
	private JPanel buttonPanel;
	private JButton saveButton;
	private JButton cancelButton;
	
	/**
	 * A <code>NewEventPanel</code> provides the graphical interface for 
	 * creating a new Event. <br><br>
	 * 
	 * @param owner - the Person creating the Event <br><br>
	 * @param frame - the JFrame to contain this panel
	 */
	public NewEventPanel(Person owner, JFrame frame) {
		this.frame = frame;
		
		eventInformationPanel = new JPanel();
		ownerLabel = new JLabel("Arranged by");
		eventOwner = new JLabel(owner.getName());
		eventStartLabel = new JLabel("From");
		eventStartTime = new JSpinner();
		eventEndLabel = new JLabel("To");
		eventEndTime = new JSpinner();
		locationLabel = new JLabel("Location");
		location = new JComboBox<Room>();
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
		searchListener = new SearchListener();
		personsModel = new DefaultListModel<Person>();
		participantsModel = new DefaultListModel<Person>();
		searchField = new JTextField("Search", 20);	// 20 columns
		searchField.addMouseListener(searchListener);
		searchList = new JList<Person>(personsModel);
		searchListScroller = new JScrollPane(searchList);
		invitedPersonsLabel = new JLabel("Invited persons");
		invitedList = new JList<Person>(participantsModel);
		invitedListScroller = new JScrollPane(invitedList);
		addPersonToParticipantsIcon = new ImageIcon(getClass().getResource(
				"images/rightArrow.png"));
		addPersonToParticipantsListLabel = new JLabel(addPersonToParticipantsIcon);
		addPersonToParticipantsListLabel.addMouseListener(searchListener);
		removePersonFromParticipantsIcon = new ImageIcon(getClass().getResource(
				"images/removeIcon.png"));
		removePersonFromParticipantsListLabel = new JLabel(
				removePersonFromParticipantsIcon);
		removePersonFromParticipantsListLabel.addMouseListener(searchListener);
		participantsButtonPanel = new JPanel();
		participantsButtonPanel.setLayout(new BoxLayout(participantsButtonPanel,
				BoxLayout.PAGE_AXIS));
		participantsButtonPanel.add(addPersonToParticipantsListLabel);
		participantsButtonPanel.add(removePersonFromParticipantsListLabel);
		
		persons = new ArrayList<String>();
		populatePersonsModel();
		populatePersonsArray();
	
		buttonPanel = new JPanel();
		saveButton = new JButton("Save Event");
		cancelButton = new JButton("Cancel");
		buttonListener = new ButtonListener();
		saveButton.addActionListener(buttonListener);
		cancelButton.addActionListener(buttonListener);
		
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
		c.fill = GridBagConstraints.BOTH;
		participantsPanel.add(searchListScroller, c);
		c.fill = GridBagConstraints.NONE;		// NONE is the default value
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		participantsPanel.add(participantsButtonPanel, c);
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 2;
		c.gridy = 0;
		participantsPanel.add(invitedPersonsLabel, c);
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		participantsPanel.add(invitedListScroller, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		
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
		NewEventPanel panel = new NewEventPanel(person, frame);
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	private void populatePersonsModel() {
		String[] names = {	"Anders Andersen", "Bjørn Bjørnson",
							"Charlie Cleese", "Dolly Delta" };
		for (String name : names) {
			String username = name.toLowerCase().replace(" ", "_");
			personsModel.addElement(new Person(name, username));
		}
	}
	
	private void populatePersonsArray() {
		for (int i = 0; i < personsModel.getSize(); i++)
			if (personsModel.get(i) != null) {
				persons.add(personsModel.get(i).getName());
			}
	}
	
	private void closeWindow() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	
	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == cancelButton)
				closeWindow();
			else if (e.getSource() == saveButton) {
				// call some stuff to save the information in the DB
				
				closeWindow();
			}
		}
	}
	
	class SearchListener implements MouseListener, KeyListener {

		@Override
		public void keyTyped(KeyEvent e) { }

		@Override
		public void keyPressed(KeyEvent e) { }

		@Override
		public void keyReleased(KeyEvent e) { }

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == addPersonToParticipantsListLabel 
					&& searchList.getSelectedValue() != null) {
				addPersons();			
			} else if (e.getSource() == removePersonFromParticipantsListLabel
					&& invitedList.getSelectedValue() != null) {
				removePersons();
			} else if (e.getSource() == searchField) {
				searchField.selectAll();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) { }

		@Override
		public void mouseReleased(MouseEvent e) { }

		@Override
		public void mouseEntered(MouseEvent e) { }

		@Override
		public void mouseExited(MouseEvent e) { }
	}
	
	private void addPersons() {
		List<Person> persons = searchList.getSelectedValuesList();
		for (Person p : persons) {
			participantsModel.addElement(p);
			personsModel.removeElement(p);
		}
	}
	
	private void removePersons() {
		List<Person> persons = invitedList.getSelectedValuesList();
		for (Person p : persons) {
			participantsModel.removeElement(p);
			personsModel.addElement(p);
		}
	}
}
