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
public class EventCancelledPanel extends AbstractPanelClass {

	private JButton okButton;

	/**
	 * GUI panel for event cancelled notification
	 * @param The event that is cancelled
	 */
	public EventCancelledPanel(Event event) {

		frame = new JFrame();
		panel = new JPanel();
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		init(event);
		setPanelLayout();
		
		//Custom for this panel
		
		//Add border to the panel
		panel.setBorder(BorderFactory.createTitledBorder("Event cancelled"));
		
		//Add the OK-button and actionlisteners
		okButton = new JButton();
		okButton.setText("OK");
		okButton.addActionListener(new ButtonListener());
		okButton.addKeyListener(new ButtonListener());

		//Sets the font in TextArea
		textArea.setText("This event has been cancelled");
		String findFont = textArea.getFont().toString();
		Font font = new Font(findFont, Font.PLAIN, 20);
		textArea.setFont(font);
		panel.add(textArea, c);

		c.gridx = 1;
		c.gridy = 6;
		c.anchor = GridBagConstraints.EAST;
		panel.add(okButton, c);

		frame.pack();
		okButton.requestFocusInWindow();
		frame.setVisible(true);
	}

	//For testing purposes
	public static void main(String[] args) {

		Person person = new Person("Jeppe Eriksen", "jeppeer@gmail.com");
		Event newEvent = new Event(1, "TestEvent", person, null, new Date(2012,3,15,11,15),
				new Date(2012,3,15,13,6), "G138" ,null);
		EventCancelledPanel ec = new EventCancelledPanel(newEvent);
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
