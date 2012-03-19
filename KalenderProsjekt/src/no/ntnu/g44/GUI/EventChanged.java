package no.ntnu.g44.gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import no.ntnu.g44.models.Event;

public class EventChanged extends JPanel{
	Event newEvent;
	Event oldEvent;
	JFrame ramme;
	JLabel oldLabel = new JLabel("Old:");
	JLabel newLabel = new JLabel("New:");
	JLabel oldNameLabel = new JLabel("Name:");
	JLabel oldStartLabel = new JLabel("Starttime:");
	JLabel oldEndLabel = new JLabel("Endtime:");
	JLabel oldLocationLabel = new JLabel("Location:");
	
	public EventChanged(Event e){
		newEvent = e;
		oldEvent = new Event();
		ramme = new JFrame("Event changed");
		ramme.setVisible(true);
		ramme.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ramme.getContentPane().add(this);
		//setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		oldLabel.setLocation(6, 12);
		oldLabel.setSize(oldLabel.getPreferredSize());
		add(oldLabel);
		oldNameLabel.setLocation(oldLabel.getX(), oldLabel.getY() + oldLabel.getHeight() + 10);
		oldNameLabel.setSize(oldNameLabel.getPreferredSize());
		add(oldNameLabel);
		oldStartLabel.setLocation(oldLabel.getX(), oldNameLabel.getY() + oldNameLabel.getHeight() + 6);
		oldStartLabel.setSize(oldStartLabel.getPreferredSize());
		add(oldStartLabel);
		oldEndLabel.setLocation(oldLabel.getX(), oldStartLabel.getY() + oldStartLabel.getHeight() + 6);
		oldEndLabel.setSize(oldEndLabel.getPreferredSize());
		add(oldEndLabel);
		oldLocationLabel.setLocation(oldLabel.getX(), oldEndLabel.getY() + oldEndLabel.getHeight() + 6);
		oldLocationLabel.setSize(oldLocationLabel.getPreferredSize());
		
		
	}
	public static void makeEventChangedPanel(Event e){
		EventChanged panel = new EventChanged(e);
	}
	public static void main(String[] lool){
		makeEventChangedPanel(new Event());
	}
}
