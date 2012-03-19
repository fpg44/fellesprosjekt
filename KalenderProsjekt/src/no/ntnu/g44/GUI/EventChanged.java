package no.ntnu.g44.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	JTextField oldName = new JTextField();
	JTextField oldStart = new JTextField();
	JTextField oldEnd = new JTextField();
	JTextField oldLocation = new JTextField();
	JLabel newNameLabel = new JLabel("Name:");
	JLabel newStartLabel = new JLabel("Starttime:");
	JLabel newEndLabel = new JLabel("Endtime:");
	JLabel newLocationLabel = new JLabel("Location:");
	JTextField newName = new JTextField();
	JTextField newStart = new JTextField();
	JTextField newEnd = new JTextField();
	JTextField newLocation = new JTextField();
	
	public EventChanged(Event e){
		newEvent = e;
		oldEvent = new Event();
		ramme = new JFrame("Event changed");
		ramme.setVisible(true);
		ramme.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ramme.getContentPane().add(this);
		oldName.setText(oldEvent.getEventOwnerString());
		oldName.setEditable(false);
		oldStart.setText(oldEvent.getEventStartTime().toGMTString());
		oldStart.setEditable(false);
		oldEnd.setText(oldEvent.getEventStartTime().toGMTString());
		oldEnd.setEditable(false);
		oldLocation.setText(oldEvent.getLocation());
		oldLocation.setEditable(false);
		//setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		oldLabel.setLocation(6, 6);
		oldLabel.setSize(oldLabel.getPreferredSize());
		add(oldLabel);
		oldNameLabel.setLocation(oldLabel.getX(), oldLabel.getY() + oldLabel.getHeight() + 8);
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
		add(oldLocationLabel);
		oldName.setLocation(oldStartLabel.getX() + oldStartLabel.getWidth() + 4, oldNameLabel.getY());
		oldName.setSize(125, (int) oldName.getPreferredSize().getHeight());
		add(oldName);
		
	}
	public static void makeEventChangedPanel(Event e){
		EventChanged panel = new EventChanged(e);
	}
	public static void main(String[] lool){
		makeEventChangedPanel(new Event());
	}
}
