package no.ntnu.g44.gui;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Person;

public class EventChanged extends JPanel{
	Event newEvent;
	Event oldEvent;
	JFrame ramme;
	JLabel oldLabel = new JLabel("Old");
	JLabel newLabel = new JLabel("New");
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
	JButton okButton = new JButton("OK");
	DefaultListModel<Person> personModel = new DefaultListModel<Person>();
	JList personList = new JList(personModel);
	JScrollPane personScroll = new JScrollPane(personList);
	
	public EventChanged(Event e){
		newEvent = e;
		oldEvent = e;
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
		
		newName.setText(newEvent.getEventOwnerString());
		newName.setEditable(false);
		newStart.setText(newEvent.getEventStartTime().toGMTString());
		newStart.setEditable(false);
		newEnd.setText(newEvent.getEventStartTime().toGMTString());
		newEnd.setEditable(false);
		newLocation.setText(newEvent.getLocation());
		newLocation.setEditable(false);
		for(int i = 0; i < newEvent.getParticipants().size(); i++){
			personModel.addElement(newEvent.getParticipants().get(i));
		}
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
		oldStart.setLocation(oldName.getX(), oldStartLabel.getY());
		oldStart.setSize(oldName.getSize());
		add(oldStart);
		oldEnd.setLocation(oldName.getX(), oldEndLabel.getY());
		oldEnd.setSize(oldName.getSize());
		add(oldEnd);
		oldLocation.setLocation(oldName.getX(), oldLocationLabel.getY());
		oldLocation.setSize(oldName.getSize());
		add(oldLocation);
		
		newLabel.setLocation(oldName.getX() + oldName.getWidth() + 10, oldLabel.getY());
		newLabel.setSize(newLabel.getPreferredSize());
		add(newLabel);
		newNameLabel.setLocation(newLabel.getX(), newLabel.getY() + newLabel.getHeight() + 8);
		newNameLabel.setSize(newNameLabel.getPreferredSize());
		add(newNameLabel);
		newStartLabel.setLocation(newLabel.getX(), newNameLabel.getY() + newNameLabel.getHeight() + 6);
		newStartLabel.setSize(newStartLabel.getPreferredSize());
		add(newStartLabel);
		newEndLabel.setLocation(newLabel.getX(), newStartLabel.getY() + newStartLabel.getHeight() + 6);
		newEndLabel.setSize(newEndLabel.getPreferredSize());
		add(newEndLabel);
		newLocationLabel.setLocation(newLabel.getX(), newEndLabel.getY() + newEndLabel.getHeight() + 6);
		newLocationLabel.setSize(newLocationLabel.getPreferredSize());
		add(newLocationLabel);
		newName.setLocation(newStartLabel.getX() + newStartLabel.getWidth() + 4, newNameLabel.getY());
		newName.setSize(125, (int) newName.getPreferredSize().getHeight());
		add(newName);
		newStart.setLocation(newName.getX(), newStartLabel.getY());
		newStart.setSize(newName.getSize());
		add(newStart);
		newEnd.setLocation(newName.getX(), newEndLabel.getY());
		newEnd.setSize(newName.getSize());
		add(newEnd);
		newLocation.setLocation(newName.getX(), newLocationLabel.getY());
		newLocation.setSize(newName.getSize());
		add(newLocation);
		personScroll.setLocation(newStart.getX() + newStart.getWidth() + 6, newLabel.getY());
		personScroll.setSize((int) personScroll.getPreferredSize().getWidth() + 10, newLocation.getHeight() + newLocation.getY() - 6);
		add(personScroll);
		okButton.setSize(okButton.getPreferredSize());
		okButton.setLocation(personScroll.getX() + personScroll.getWidth() - okButton.getWidth(), personScroll.getY() + personScroll.getHeight() + 6);
		add(okButton);
		ramme.setSize((int)personScroll.getX() + personScroll.getWidth() + 12 + 10, personScroll.getY() + personScroll.getHeight() + okButton.getHeight() + 18 + 35);
		
	}
	public static void makeEventChangedPanel(Event e){
		EventChanged panel = new EventChanged(e);
	}
	public static void main(String[] lool){
		makeEventChangedPanel(new Event());
	}
}
