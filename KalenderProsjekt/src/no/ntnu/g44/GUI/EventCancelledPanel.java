package no.ntnu.g44.gui;


import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Person;
import no.ntnu.g44.models.Room;

/**
 * Class that represents a cancelled event. Is called when a user gets a
 * notification of a cancelled event.
 * @author JeppeE
 *
 */
public class EventCancelledPanel extends JPanel{

	private Event event;

	private JPanel eventCancelledLabelPanel;
	private JFrame frame;

	private Person eventOwner;
	private Date startTime, endTime;
	private Room room;
	private String customLocation;

	private JLabel staticPersonNameLabel, staticEventTitleLabel, staticStartTimeLabel, staticEndTimeLabel, staticLocationLabel;
	private JLabel personNameLabel, eventTitleLabel, startTimeLabel, endTimeLabel, locationLabel; 
	private JTextArea textArea;

	private JButton okButton;

	/**
	 * GUI panel for event cancelled notification
	 * @param The event that is cancelled
	 */
	public EventCancelledPanel(Event event) {

		frame = new JFrame();
		eventCancelledLabelPanel = new JPanel();
		frame.getContentPane().add(eventCancelledLabelPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
		locationLabel = new JLabel();
		if (event.getRoom() != null) {
			locationLabel.setText(event.getRoom().getRoomName());
		}
		else {
			locationLabel.setText(customLocation);
		}

		okButton = new JButton();
		okButton.setText("OK");
		okButton.addActionListener(new ButtonListener());
		okButton.addKeyListener(new ButtonListener());

		textArea = new JTextArea("This event has been cancelled");

		eventCancelledLabelPanel.setBorder(BorderFactory.createTitledBorder("Event cancelled"));
		eventCancelledLabelPanel.setLayout(new GridBagLayout());

		setPanelLayout();

		frame.pack();
		okButton.requestFocusInWindow();
		frame.setVisible(true);
	}

	/**
	 * Sets the layout for the panel
	 */
	private void setPanelLayout() {

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 0, 5, 5);
		c.ipadx = 5;
		c.ipady = 5;

		//Add the static labels
		c.gridx = 0;
		c.gridy = 0;
		eventCancelledLabelPanel.add(staticPersonNameLabel, c);

		c.gridx = 0;
		c.gridy = 1;
		eventCancelledLabelPanel.add(staticEventTitleLabel, c);

		c.gridx = 0;
		c.gridy = 2;
		eventCancelledLabelPanel.add(staticStartTimeLabel, c);

		c.gridx = 0;
		c.gridy = 3;
		eventCancelledLabelPanel.add(staticEndTimeLabel, c);

		c.gridx = 0;
		c.gridy = 4;
		eventCancelledLabelPanel.add(staticLocationLabel, c);

		//Add the non-static labels
		c.gridx = 1;
		c.gridy = 0;
		eventCancelledLabelPanel.add(personNameLabel, c);

		c.gridx = 1;
		c.gridy = 1;
		eventCancelledLabelPanel.add(eventTitleLabel, c);

		c.gridx = 1;
		c.gridy = 2;
		eventCancelledLabelPanel.add(startTimeLabel, c);

		c.gridx = 1;
		c.gridy = 3;
		eventCancelledLabelPanel.add(endTimeLabel, c);

		c.gridx = 1;
		c.gridy = 4;
		eventCancelledLabelPanel.add(locationLabel, c);

		//Add the textarea
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		c.ipadx = 10;
		c.ipady = 10;
		textArea.setEditable(false);
		//Sets the font in TextArea
		String test = textArea.getFont().toString();
		Font font = new Font(test, Font.PLAIN, 20);
		textArea.setFont(font);
		eventCancelledLabelPanel.add(textArea, c);

		c.gridx = 1;
		c.gridy = 6;
		c.anchor = GridBagConstraints.EAST;
		eventCancelledLabelPanel.add(okButton, c);

	}

	//For testing purposes
	public static void main(String[] args) {

		Person person = new Person("Jeppe Eriksen", "jeppeer@gmail.com");
		Event newEvent = new Event(1, "TestEvent", person, null, new Date(2012,3,15,11,15),
				new Date(2012,3,15,13,6), "G138" ,null);
		EventCancelledPanel ec = new EventCancelledPanel(newEvent);
	}
	/**
	 * Closes the window.
	 */
	private void closeWindow() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	class ButtonListener implements ActionListener, KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER){
				closeWindow();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == okButton) {
				closeWindow();
			}
		}
	}
}
