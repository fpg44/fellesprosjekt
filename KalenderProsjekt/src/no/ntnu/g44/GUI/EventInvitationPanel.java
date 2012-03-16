package no.ntnu.g44.gui;

import java.awt.GridBagConstraints;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Person;

public class EventInvitationPanel extends AbstractPanelClass {
	
	//Cusom variables for this panel
	JLabel attendingLabel;
	JButton yesButton, noButton, cancelButton;
	
	public EventInvitationPanel(Event event) {
		frame = new JFrame();
		panel = new JPanel();
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		init(event);
		setPanelLayout();
		
		//Custom for this panel
		textArea.setText(event.getEventDescription());
		
		attendingLabel = new JLabel("Are you attending this event?");
		yesButton = new JButton("Yes");
		noButton = new JButton("No");
		cancelButton = new JButton("Cancel");
		
		//Add border to the panel
		panel.setBorder(BorderFactory.createTitledBorder("Event invitation"));
		
		//Add ekstra labels and buttons to this panel
		c.gridx = 0;
		c.gridy = 6;
		panel.add(attendingLabel, c);
		
		
		c.gridx = 1;
		c.gridy = 7;
		c.anchor = GridBagConstraints.EAST;
		panel.add(yesButton, c);
		
		c.gridx = 1;
		c.gridy = 7;
		c.anchor = GridBagConstraints.WEST;
		panel.add(noButton, c);
		
		frame.pack();
		frame.setVisible(true);
		
		
		
	}
	
	//For testing purposes
	public static void main(String[] args) {

		Person person = new Person("Jeppe Eriksen", "jeppeer@gmail.com");
		Event newEvent = new Event(1, "MIXER! \nÅrets event! Sykt kult! blalbalbalba", person, null, new Date(2012,3,15,11,15),
				new Date(2012,3,15,13,6), "G138" ,null);
		EventInvitationPanel e = new EventInvitationPanel(newEvent);
	}

}
