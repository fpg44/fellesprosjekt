package no.ntnu.g44.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import no.ntnu.g44.controllers.Main;
import no.ntnu.g44.models.Event;

public class EventInfoPanel extends JPanel{
	public EventInfoPanel(Event event){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame frame = new JFrame(event.getEventTitle());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.setSize(200, 150);
		frame.setLocation((int)(dim.getWidth() - frame.getWidth()) / 2, (int)(dim.getHeight() - frame.getHeight()) / 2);
		frame.setVisible(true);
		
	}
	public static void makeInfoPanel(Event event){
		EventInfoPanel infoPanel = new EventInfoPanel(event);
	}
}
