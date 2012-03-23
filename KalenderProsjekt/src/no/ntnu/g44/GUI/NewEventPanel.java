package no.ntnu.g44.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
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
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;

import no.ntnu.g44.controllers.Main;
import no.ntnu.g44.models.AttendanceHelper;
import no.ntnu.g44.models.AttendanceStatus;
import no.ntnu.g44.models.AttendanceStatusType;
import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Person;
import no.ntnu.g44.models.Room;

public class NewEventPanel extends JPanel {

	// our container window
	JFrame frame;

	// information about the event--the left side of our prototype
	private JPanel eventInformationPanel;
	private JLabel ownerLabel;
	private JLabel eventOwnerName;			// the person organizing the event
	private Person eventOwner;
	private JLabel eventStartLabel;
	private JSpinner eventStartTime;
	private SpinnerDateModel startTimeModel;
	private JLabel eventEndLabel;
	private JSpinner eventEndTime;
	private SpinnerDateModel endTimeModel;
	private RoomListener roomListener;
	private JLabel locationLabel;
	private JComboBox<Room> location;
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
	// this HashMap should be filled with the usernames and names of all persons
	private HashMap<String, String> persons;
	// this model should contain the persons to invite
	private DefaultListModel<Person> participantsModel;

	// 'Save Event' and 'Cancel' buttons
	private ButtonListener buttonListener;
	private JPanel buttonPanel;
	private JButton saveButton;
	private JButton cancelButton;

	// the old (original) Event, in the case of an update
	private Event oldEvent = null;

	/**
	 * A <code>NewEventPanel</code> provides the graphical interface for 
	 * creating a new Event. <br><br>
	 * 
	 * @param owner - the Person creating the Event <br><br>
	 * @param frame - the JFrame to contain this panel
	 * @param oldEvent - this parameter is passed when updating an existing
	 * Event; in this case, the original Event is deleted
	 */
	public NewEventPanel(Person owner, JFrame frame, Event oldEvent) {
		this(owner, frame);
		if (oldEvent != null) {
			this.oldEvent = oldEvent;
			eventStartTime.setValue(oldEvent.getEventStartTime());
			eventEndTime.setValue(oldEvent.getEventEndTime());
			location.addItem(oldEvent.getRoom());
			location.setSelectedItem(oldEvent.getRoom());
			customLocation.setText(oldEvent.getLocation());
			eventDescription.setText(oldEvent.getEventDescription());
			for (Person person : oldEvent.getParticipants()) {
				if (person == this.eventOwner)
					continue;
				if (!participantsModel.contains(person)
						&& personsModel.contains(person)) {
					participantsModel.addElement(person);
					personsModel.removeElement(person);
				}
			}
		}
	}

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
		eventOwner = owner;
		eventOwnerName = new JLabel(owner.getName());

		eventStartLabel = new JLabel("From");
		eventStartTime = new JSpinner();
		startTimeModel = new SpinnerDateModel();
		startTimeModel.setCalendarField(Calendar.DAY_OF_WEEK_IN_MONTH);
		eventStartTime.setModel(startTimeModel);
		JSpinner.DateEditor startEd = new JSpinner.DateEditor(eventStartTime,
				"yyyy-MM-dd HH:mm");
		setCustomCaret(startEd);
		eventStartTime.setEditor(startEd);

		eventEndLabel = new JLabel("To");
		eventEndTime = new JSpinner();
		endTimeModel = new SpinnerDateModel();
		endTimeModel.setCalendarField(Calendar.DAY_OF_WEEK_IN_MONTH);
		eventEndTime.setModel(endTimeModel);
		JSpinner.DateEditor endEd = new JSpinner.DateEditor(eventEndTime,
				"yyyy-MM-dd HH:mm");
		setCustomCaret(endEd);
		eventEndTime.setEditor(endEd);
		// set the endtime to current epoch time + 3600000 milliseconds (1 hour)
		Date endTime = new Date(new Date().getTime() + 3600000);
		endTime.setTime(endTime.getTime());
		eventEndTime.setValue(endTime);

		locationLabel = new JLabel("Location");
		location = new JComboBox<Room>();
		customLocationLabel = new JLabel("Custom location");
		customLocation = new JTextField(20);		// 20 columns
		/* customLocation should only be enabled when 'OTHER' is selected
		   in the location JComboBox */
		customLocation.setEnabled(false);
		eventDescriptionLabel = new JLabel("Description");
		eventDescription = new JTextArea(4, 20);	// 4 rows, 20 columns
		roomListener = new RoomListener();
		startTimeModel.addChangeListener(roomListener);
		endTimeModel.addChangeListener(roomListener);
		location.addActionListener(roomListener);
		// fire an event to have 'location' populated with available rooms
		roomListener.stateChanged(new ChangeEvent(endTimeModel));

		// add some padding between the edge of the JTextArea and entered text
		eventDescription.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		/* put the description in a JScrollPane so scrollbars are added rather
		   than the textearea resizing itself when a lot of text is entered */
		eventDescriptionScroller = new JScrollPane(eventDescription);

		participantsPanel = new JPanel();
		searchListener = new SearchListener();
		personsModel = new DefaultListModel<Person>();
		participantsModel = new DefaultListModel<Person>();
		invitedPersonsLabel = new JLabel("Invited persons");

		invitedList = new JList<Person>(participantsModel);
		invitedList.setCellRenderer(new ParticipantsRenderer());
		invitedList.addMouseListener(searchListener);
		invitedList.addKeyListener(searchListener);
		invitedListScroller = new JScrollPane(invitedList);

		searchField = new JTextField("Search", 20);	// 20 columns
		searchField.addMouseListener(searchListener);
		searchField.addKeyListener(searchListener);


		searchList = new JList<Person>(personsModel);
		searchList.addMouseListener(searchListener);
		searchList.addKeyListener(searchListener);
		searchListScroller = new JScrollPane(searchList);

		addPersonToParticipantsIcon = new ImageIcon(getClass().getResource(
				"images/rightArrow.png"));
		addPersonToParticipantsListLabel = new JLabel(
				addPersonToParticipantsIcon);
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

		persons = new HashMap<String, String>();
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
		eventInformationPanel.add(eventOwnerName, c);
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
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.gridx = c.gridy = 1;
		add(buttonPanel, c);

		frame.setTitle("New Event");
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}

	private void populatePersonsModel() {
		ArrayList<Person> persons = Main.currentProject.getPersonList();
		for (Person person : persons)
			if (person != Main.currentProject.getLoggedInPerson())
				personsModel.addElement(person);
	}

	private void populatePersonsArray() {
		for (int i = 0; i < personsModel.getSize(); i++)
			if (personsModel.get(i) != null) {
				Person p = personsModel.get(i);
				persons.put(p.getUsername(), p.getName());
			}
	}

	private void removeParticipants() {
		List<Person> persons = invitedList.getSelectedValuesList();
		for (Person person : persons) {
			if (participantsModel.contains(person)
					&& !personsModel.contains(person)) {
				participantsModel.removeElement(person);
				personsModel.addElement(person);
			}
		}
	}

	private void addParticipants() {
		List<Person> persons = searchList.getSelectedValuesList();
		for (Person person : persons) {
			if (!participantsModel.contains(person)
					&& personsModel.contains(person)) {
				participantsModel.addElement(person);
				personsModel.removeElement(person);
			}
		}
	}

	private Event createEvent() {
		String eventOwner = this.eventOwner.getUsername();
		String eventTitle = new String(eventDescription.getText());
		ArrayList<String> participants = new ArrayList<String>();
		for (int i = 0; i < participantsModel.getSize(); i++)
			participants.add(participantsModel.get(i).getUsername());
		participants.add(eventOwner);
		Date eventStartTime = startTimeModel.getDate();
		Date eventEndTime = endTimeModel.getDate();
		String location = customLocation.getText();
		String roomName = ((Room) this.location.getSelectedItem()).getRoomName();
		int id = Main.currentProject.generateID();
		return new Event(id, eventTitle, eventOwner, participants,
				eventStartTime, eventEndTime, location, roomName);
	}

	private void closeWindow() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	private void setCustomCaret(JSpinner.DateEditor ed) {
		/* This is a hack to set the default value to change in the 'To' and
		   'From' fields to the hour, not year. */

		ed.getTextField().setCaret(new DefaultCaret() {  

			private boolean diverted = false;

			@Override
			public void setDot(int dot) {
				diverted = (dot == 0);
				if (diverted) {
					/* setting the dot (caret) value to 12 places it in the 
					   middle of the hour field (HH), assuming the date format
					   is ISO-8601 (yyyy-MM-dd HH:mm) */
					dot = 12;
				}
				super.setDot(dot);
			}

			@Override
			public void moveDot(int dot) {
				if (diverted) {
					super.setDot(0);
					diverted = false;
				}
				super.moveDot(dot);
			}  
		});
	}

	class RoomListener implements ActionListener, ChangeListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == location) {
				Room room = (Room) location.getSelectedItem();
				if (room != null && room.getRoomName().equals("OTHER")) {
					customLocation.setEnabled(true);
				} else {
					customLocation.setText("");
					customLocation.setEnabled(false);
				}
			}
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == startTimeModel
					|| e.getSource() == endTimeModel) {
				Date start = startTimeModel.getDate();
				Date end = endTimeModel.getDate();
				ArrayList<Room> rooms = Room.getAvailableRooms(start, end);

				location.removeAllItems();
				for (Room room : rooms)
					location.addItem(room);
			}
		}
	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == cancelButton)
				closeWindow();
			else if (e.getSource() == saveButton) {
				if (oldEvent != null)
					oldEvent.expired = true;

				Event event = createEvent();

				// save the new Event
				Main.currentProject.addEvent(event, true);

				// create Notifications for all participants
				for (Person person : event.getParticipants()) {
					try {
						// initialize to AttendanceStatusType.UNANSWERED
						if (person.getUsername().equals(
								event.getEventOwnerString())) {
							Main.currentProject.addAttendanceStatus(
									new AttendanceStatus(person.getUsername(),
											event.getEventID(),
											AttendanceStatusType.ATTENDING),
											true);
						} else {
							Main.currentProject.addAttendanceStatus(
									new AttendanceStatus(person.getUsername(),
											event.getEventID(),
											AttendanceStatusType.UNANSWERED),
											true);							
						}
					} catch (ConnectException ex) {
						ex.printStackTrace();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				// the owner of the event should be ATTENDING
				Main.currentProject.getStatus(event.getEventID(),
						event.getEventOwnerString()).setStatus(
								AttendanceStatusType.ATTENDING);
			}

			closeWindow();
		}
	}

	class SearchListener implements MouseListener, KeyListener {

		@Override
		public void keyTyped(KeyEvent e) { }

		@Override
		public void keyReleased(KeyEvent e) { }

		@Override
		public void keyPressed(KeyEvent e) {
			boolean shiftKeyPressed = false;

			if ((e.getSource() == searchList || e.getSource() == searchField)
					&& e.getKeyChar() == KeyEvent.VK_ENTER) {
				addParticipants();
			} else if (e.getSource() == invitedList
					&& (e.getKeyCode() == KeyEvent.VK_BACK_SPACE
					|| e.getKeyCode() == KeyEvent.VK_DELETE)) {
				removeParticipants();
			} else if (e.getSource() == searchField) {
				if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
					shiftKeyPressed = true;
					return;
				}

				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					int index = searchList.getLeadSelectionIndex();

					if (index == personsModel.getSize() - 1) {
						if (shiftKeyPressed) {
							searchList.addSelectionInterval(index, 0);
							return;
						} else {
							searchList.setSelectedIndex(0);
							return;
						}
					}

					if (shiftKeyPressed) {
						searchList.addSelectionInterval(index, index + 1);
						return;
					} else {
						searchList.setSelectedIndex(index + 1);
						return;
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_UP) {
					int index = searchList.getSelectedIndex();

					if (index == 0) {
						if (shiftKeyPressed) {
							searchList.addSelectionInterval(index,
									personsModel.size() - 1);
							return;
						} else {
							searchList.setSelectedIndex(personsModel.size()-1);
							return;
						}
					}

					if (shiftKeyPressed) {
						searchList.addSelectionInterval(index, index - 1);
						return;
					} else {
						searchList.setSelectedIndex(index - 1);
						return;
					}
				}

				String query = searchField.getText();
				if (Character.isLetter(e.getKeyChar())
						|| e.getKeyChar() == '-') {
					query += e.getKeyChar();
				} else if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE
						&& query.length() > 0) {
					query = query.substring(0, query.length() - 1);
				}

				query = query.toLowerCase();
				personsModel.clear();
				for (Map.Entry<String, String> entry : persons.entrySet()) {
					String name = entry.getValue().toLowerCase();
					if (name.startsWith(query) || name.equals(query)) {
						Person p = Person.findPersonByUsername(entry.getKey());
						if (!participantsModel.contains(p)
								&& !personsModel.contains(p))
							personsModel.addElement(p);
					}
				}

				if (personsModel.size() > 0) {
					searchList.setSelectedIndex(0);
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) { }

		@Override
		public void mousePressed(MouseEvent e) { }

		@Override
		public void mouseReleased(MouseEvent e) { 
			if (e.getSource() == addPersonToParticipantsListLabel 
					&& searchList.getSelectedValue() != null) {
				addParticipants();			
			} else if (e.getSource() == removePersonFromParticipantsListLabel
					&& invitedList.getSelectedValue() != null) {
				removeParticipants();
			} else if (e.getSource() == searchField) {
				searchField.selectAll();
			} else if (e.getSource() == searchList && e.getClickCount() == 2) {
				addParticipants();
			} else if (e.getSource() == invitedList && e.getClickCount() == 2) {
				removeParticipants();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) { }

		@Override
		public void mouseExited(MouseEvent e) { }
	}

	protected class ParticipantsRenderer extends DefaultListCellRenderer {

		@Override
		public Component getListCellRendererComponent(
				JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {

			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);

			setText(value.toString());
			
			 if (oldEvent != null && !isSelected)
				 setBackground(AttendanceHelper.getColor(oldEvent,
						 (Person) value));

			return this;
		}
	}
}
