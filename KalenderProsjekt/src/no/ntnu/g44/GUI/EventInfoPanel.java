package no.ntnu.g44.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Date;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import no.ntnu.g44.controllers.Main;
//import no.ntnu.g44.models.AttendanceHelper;
import no.ntnu.g44.models.AttendanceStatus;
import no.ntnu.g44.models.AttendanceStatusType;
import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Person;

public class EventInfoPanel extends JPanel{
	
	JFrame frame;
	DefaultListModel<Person> participantsListModel = new DefaultListModel<Person>();
	JList<Person> participantsList = new JList<Person>(participantsListModel);
	JScrollPane participantsListScroller = new JScrollPane(participantsList);
	Event event;
	

	public EventInfoPanel(Event event, JFrame frame){
		this.event = event;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//		JFrame frame = new JFrame(event.getEventDescription());
		this.frame = frame;
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(this);
		participantsList.setCellRenderer(new colourListCellRenderer());
		JLabel ownerLabel = new JLabel("Event owner:");
		JLabel startLabel = new JLabel("Start time:");
		JLabel endLabel = new JLabel("End time:");
		JLabel locationLabel = new JLabel("Location:");
		JLabel messageLabel = new JLabel("Info:");
		JTextField ownerField = new JTextField(event.getEventOwnerString());
		JTextField startField = new JTextField();
		JTextField endField = new JTextField();
		JTextField locationField = new JTextField(event.getLocation());
		JTextField messageField = new JTextField();
		
		ownerField.setEditable(false);
		startField.setEditable(false);
		endField.setEditable(false);
		locationField.setEditable(false);
		messageField.setEditable(false);
		
		//Adding a JList with all the participants of this event
		for (Person person : event.getParticipants()) {
			participantsListModel.addElement(person);
		}
		
		setLayout(null);
		
		//Some comments would have been nice here!
		ownerLabel.setLocation(8, 12);
		ownerLabel.setSize(ownerLabel.getPreferredSize());
		startLabel.setLocation(ownerLabel.getX(), ownerLabel.getY() + ownerLabel.getHeight() + 4);
		startLabel.setSize(ownerLabel.getSize());
		endLabel.setLocation(ownerLabel.getX(), startLabel.getY() + startLabel.getHeight() + 4);
		endLabel.setSize(ownerLabel.getSize());
		locationLabel.setLocation(ownerLabel.getX(), endLabel.getY() + endLabel.getHeight() + 4);
		locationLabel.setSize(ownerLabel.getSize());
		messageLabel.setLocation(ownerLabel.getX(), locationLabel.getY() + locationLabel.getHeight() + 4);
		messageLabel.setSize(messageLabel.getPreferredSize());
//		ownerField.setText(event.getEventOwnerString());
		ownerField.setLocation(ownerLabel.getX() + ownerLabel.getWidth() + 4, ownerLabel.getY());
		ownerField.setSize(250, (int) ownerField.getPreferredSize().getHeight());
		Date date = event.getEventStartTime();
		startField.setText(event.getEventStartTime().toGMTString());
		startField.setLocation(ownerField.getX(), startLabel.getY());
		startField.setSize(ownerField.getSize());
		endField.setText(event.getEventEndTime().toGMTString());
		endField.setLocation(ownerField.getX(), endLabel.getY());
		endField.setSize(ownerField.getSize());
		locationField.setText(event.getLocation());
		locationField.setLocation(ownerField.getX(), locationLabel.getY());
		locationField.setSize(ownerField.getSize());
		messageField.setText(event.getEventDescription());
		messageField.setLocation(ownerLabel.getX(), messageLabel.getY() + messageLabel.getHeight() + 4);
		messageField.setSize(locationField.getX() + locationField.getWidth() - locationLabel.getX(), ownerField.getHeight() * 3);
		participantsListScroller.setLocation(ownerField.getX() + ownerField.getWidth() + 4, ownerLabel.getY());
		participantsListScroller.setSize((int) participantsListScroller.getPreferredSize().getWidth(), messageField.getY() + messageField.getHeight() - 12);
		
		add(ownerLabel);
		add(startLabel);
		add(endLabel);
		add(locationLabel);
		add(messageLabel);
		add(ownerField);
		add(startField);
		add(endField);
		add(locationField);
		add(messageField);
		add(participantsListScroller);
		
		frame.setSize(participantsListScroller.getX() + participantsListScroller.getWidth() + 8 + 12, messageField.getY() + messageField.getHeight() + 45);
		frame.setLocation((int)(dim.getWidth() - frame.getWidth()) / 2, (int)(dim.getHeight() - frame.getHeight()) / 2);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle("Show Event");
		
	}
	/*
	public static void makeInfoPanel(Event event){
		EventInfoPanel infoPanel = new EventInfoPanel(event);
	}
	*/
	public class colourListCellRenderer extends DefaultListCellRenderer {
	     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	         Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         
	         AttendanceStatus status = Main.currentProject.getStatus(
	        		 event.getEventID(), ((Person) value).getUsername());
	         
	         if (status != null)
	        	 c.setBackground(AttendanceStatusType.getColor(
	        			 status.getStatus()));
	         
	         return c;
	     }
	}
}
