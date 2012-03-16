package no.ntnu.g44.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import no.ntnu.g44.models.Event;

public class EventInfoPanel extends JPanel{
	public EventInfoPanel(Event event){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame frame = new JFrame(event.getEventDescription());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(this);
		JLabel ownerLabel = new JLabel("Event owner:");
		JLabel startLabel = new JLabel("Start time:");
		JLabel endLabel = new JLabel("End time:");
		JLabel locationLabel = new JLabel("Location:");
		JLabel messageLabel = new JLabel("Info:");
		JTextField ownerField = new JTextField();
		JTextField startField = new JTextField();
		JTextField endField = new JTextField();
		JTextField locationField = new JTextField();
		JTextField messageField = new JTextField();
		ownerField.setEditable(false);
		startField.setEditable(false);
		endField.setEditable(false);
		locationField.setEditable(false);
		messageField.setEditable(false);
		setLayout(null);
		
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
		ownerField.setText(event.getEventOwnerString());
		ownerField.setLocation(ownerLabel.getX() + ownerLabel.getWidth() + 4, ownerLabel.getY());
		ownerField.setSize(175, (int) ownerField.getPreferredSize().getHeight());
		Date date = event.getEventStartTime();
		startField.setText(event.getEventStartTime().toString());
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
		
		frame.setSize(ownerField.getX() + ownerField.getWidth() + 8 + 14, messageField.getY() + messageField.getHeight() + 45);
		frame.setLocation((int)(dim.getWidth() - frame.getWidth()) / 2, (int)(dim.getHeight() - frame.getHeight()) / 2);
		frame.setResizable(false);
		frame.setVisible(true);
		
	}
	public static void makeInfoPanel(Event event){
		EventInfoPanel infoPanel = new EventInfoPanel(event);
	}
}
