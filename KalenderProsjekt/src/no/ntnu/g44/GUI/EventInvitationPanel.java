package no.ntnu.g44.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;
import java.text.ParseException;
import java.util.Date;
import no.ntnu.g44.controllers.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import no.ntnu.g44.models.*;

public class EventInvitationPanel extends AbstractPanelClass {
	
	//Cusom variables for this panel
	JLabel attendingLabel;
	JButton yesButton, noButton, cancelButton;
	
	public EventInvitationPanel(Event event) {
		frame = new JFrame();
		panel = new JPanel();
		frame.getContentPane().add(panel);
		
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
	
//	//For testing purposes
//	public static void main(String[] args) {
//
//		Person person = new Person("Jeppe Eriksen", "jeppeer@gmail.com");
//		Event newEvent = new Event(1, "MIXER! \n�rets event! Sykt kult! blalbalbalba", person, null, new Date(2012,3,15,11,15),
//				new Date(2012,3,15,13,6), "G138" ,null);
//		EventInvitationPanel e = new EventInvitationPanel(newEvent);
//	}
	
	private Event getEvent(){
		return this.event;
	}
	
	class ButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == yesButton) {
				try {
					//Removes the Notification from the notification Array in currentProject
					Main.currentProject.removeNotification(Main.currentProject.getNotification(getEvent().getEventID()));

					Main.currentProject.getStatus(getEvent().getEventID(), Main.currentProject.getLoggedInPerson().getUsername()).setStatus(AttendanceStatusType.ATTENDING);
					Main.currentProject.updateAttendanceStatus(Main.currentProject.getStatus(getEvent().getEventID(), Main.currentProject.getLoggedInPerson().getUsername()));
				} catch (ConnectException e1) {
					
					e1.printStackTrace();
					
				} catch (IOException e1) {
					
					e1.printStackTrace();
					
				} catch (ParseException ef) {
					// TODO Auto-generated catch block
					ef.printStackTrace();
				}
//				closeWindow();
				Main.currentMainFrame.notificationFuck();
				frame.dispose();
				
			}
			else if (e.getSource() == noButton) {
				
				//Participant declined, all users will get notified
				Main.currentProject.getNotification(getEvent().getEventID()).setType(NotificationType.PARTICIPANT_DECLINED);
				
				//henter statusen
				AttendanceStatus s = Main.currentProject.getStatus(getEvent().getEventID(), Main.currentProject.getLoggedInPerson().getUsername());
				
				//setter statusen
				s.setStatus(AttendanceStatusType.NOT_ATTENDING);
				try {
					//oppdaterer statusen
					Main.currentProject.updateAttendanceStatus(s);
				} catch (ConnectException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ParseException ef) {
					// TODO Auto-generated catch block
					ef.printStackTrace();
				}
				
//				closeWindow();
				Main.currentMainFrame.notificationFuck();
				frame.dispose();
			}
			else if (e.getSource() == cancelButton) {
				closeWindow();
				frame.dispose();
			}
		}
	}
}
