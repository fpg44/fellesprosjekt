package no.ntnu.g44.gui;

import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;

import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Person;

public class EventInvitationPanel extends AbstractPanelClass {
	
	public EventInvitationPanel(Event event) {
		frame = new JFrame();
		panel = new JPanel();
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		init(event);
		setPanelLayout();
		
		//Custom for this panel
//		textArea.setText(event.)
		
		frame.pack();
		frame.setVisible(true);
		
		
		
	}
	
	//For testing purposes
	public static void main(String[] args) {

		Person person = new Person("Jeppe Eriksen", "jeppeer@gmail.com");
		Event newEvent = new Event(1, "TestEvent", person, null, new Date(2012,3,15,11,15),
				new Date(2012,3,15,13,6), "G138" ,null);
		EventInvitationPanel e = new EventInvitationPanel(newEvent);
	}

}
