package no.ntnu.g44.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
		
		attendingLabel = new JLabel("Will you be attending this event?");
		yesButton = new JButton("Yes");
		noButton = new JButton("No");
		cancelButton = new JButton("Cancel");
		
		//Add border to the panel
		panel.setBorder(BorderFactory.createTitledBorder("Event invitation"));
		
		//Add ekstra labels and buttons to this panel
		c.gridx = 0;
		c.gridy = 6;
		panel.add(attendingLabel, c);
		
		c.fill = GridBagConstraints.NONE;
		
		//Add a new panel for the buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints bc = new GridBagConstraints();
		
		buttonPanel.add(yesButton, bc);
		buttonPanel.add(noButton, bc);
		buttonPanel.add(cancelButton, bc);
		
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
		panel.add(buttonPanel, c);
		
		//Add actionlistener for the buttons
		yesButton.addActionListener(new ButtonListener());
		noButton.addActionListener(new ButtonListener());
		cancelButton.addActionListener(new ButtonListener());
		
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
	
	class ButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == yesButton) {
				//Her må det legges til slik at eventet legges til på personen
				closeWindow();
			}
			else if (e.getSource() == noButton) {
				closeWindow();
			}
			else if (e.getSource() == cancelButton) {
				closeWindow();
			}
		}
	}
}
