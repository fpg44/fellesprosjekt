package no.ntnu.g44.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import no.ntnu.g44.models.Event;

public class EventChanged extends JPanel{
	Event newEvent;
	Event oldEvent;
	JFrame ramme;
	
	public EventChanged(Event e){
		ramme = new JFrame("Event changed");
		ramme.setVisible(true);
		
	}
	public static void makeEventChangedPanel(Event e){
		EventChanged panel = new EventChanged(e);
	}
	public static void main(String[] lool){
		makeEventChangedPanel(new Event());
	}
}
