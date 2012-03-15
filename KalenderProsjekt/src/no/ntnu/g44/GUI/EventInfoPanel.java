package no.ntnu.g44.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import no.ntnu.g44.models.Event;

public class EventInfoPanel extends JPanel{
	public EventInfoPanel(Event event){
		JFrame frame = new JFrame(event.getEventTitle());
		frame.getContentPane().add(this);
	}
	public static void main(String[] lol){
		
	}
	public static void makeInfoPanel(Event event){
		EventInfoPanel infoPanel = new EventInfoPanel(event);
	}
}
