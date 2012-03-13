package no.ntnu.g44.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import no.ntnu.g44.components.ListRenderer;

public class MainFrame extends JPanel{
	MouseListener listener = new MouseListener();
	Insets insets;
	JFrame ramme;
	JButton newEvent = new JButton("New Event");
	JButton editEvent = new JButton("Edit Event");
	JButton deleteEvent = new JButton("Delete Event");
	JButton arrowButton = new JButton("^   ^   ^   ^   ^   ^   ^   ^");
	JTextField searchField = new JTextField("Search...");
	DefaultListModel personellModel = new DefaultListModel();
	DefaultListModel kalendarModel = new DefaultListModel();
	JList personellList = new JList(personellModel);
	JList kalendarPersons = new JList(kalendarModel);
	JScrollPane personellScroll = new JScrollPane(personellList);
	JScrollPane kalendarScroll = new JScrollPane(kalendarPersons);
	JComboBox notifBox = new JComboBox();
	JButton backArrow = new JButton(" < < ");
	JButton todayButton = new JButton(" Today ");
	JButton nextArrow = new JButton(" > > ");
	CalendarPanel calendar = new CalendarPanel();
	JLabel weeknumber = new JLabel("UKE 12");
	ListRenderer renderer = new ListRenderer();
	
	public MainFrame(){
		personellList.setCellRenderer(renderer);
		personellList.addMouseListener(renderer.getHandler(personellList));  
		personellList.addMouseMotionListener(renderer.getHandler(personellList)); 
		
		fillModel();
		fillModel();
		fillModel();
		fillModel();
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		addMouseMotionListener(listener);
		ramme = new JFrame("Test");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ramme.setBounds(0, 0, (int)dim.getWidth()-60, (int)dim.getHeight()-60);
		ramme.setExtendedState(Frame.MAXIMIZED_BOTH);
		ramme.getContentPane().add(this);
		ramme.setVisible(true);
		ramme.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		insets = ramme.getInsets();
		
		addMouseMotionListener(listener);
		editEvent.setVisible(true);
		newEvent.setVisible(true);
		deleteEvent.setVisible(true);
		arrowButton.setVisible(true);
		arrowButton.addActionListener(listener);
		searchField.setVisible(true);
		calendar.setVisible(true);
		//calendar.setBackground(Color.GRAY);
		personellList.setVisible(true);
		kalendarPersons.setVisible(true);
		personellScroll.setVisible(true);
		kalendarScroll.setVisible(true);
		backArrow.setVisible(true);
		
		todayButton.setVisible(true);
		nextArrow.setVisible(true);
		notifBox.setVisible(true);
		weeknumber.setVisible(true);
		
		add(personellScroll);
		add(kalendarScroll);
		add(newEvent);
		add(editEvent);
		add(deleteEvent);
		add(arrowButton);
		add(searchField);
		add(calendar);
		add(backArrow);
		add(todayButton);
		add(nextArrow);
		add(notifBox);
		add(weeknumber);
		//add(personellList);
		//add(kalendarPersons);
		resizing();
	}
	@Override
	public void repaint(){
		resizing();
	}
	public void resizing(){
		if(insets == null){
			return;
		}
		newEvent.setLocation( 12,  16);
		newEvent.setSize((getWidth() -36) / 8,( getHeight() - 56) / 12);
		newEvent.addMouseMotionListener(listener);
		
		editEvent.setSize(newEvent.getSize());
		editEvent.setLocation(newEvent.getX(), newEvent.getY() + newEvent.getHeight());
		
		deleteEvent.setLocation(newEvent.getX(), editEvent.getY() + editEvent.getHeight());
		deleteEvent.setSize(newEvent.getSize());
		
		calendar.setSize(newEvent.getWidth() * 7, getHeight() - 35 - 16 - 12 - 12);
		calendar.setLocation(editEvent.getX() + editEvent.getWidth() + 12, 35 + 16 + 12);
		
		backArrow.setSize(calendar.getWidth() / 9, 35);
		backArrow.setLocation(calendar.getX() + (calendar.getWidth() / 3), 16);
		
		todayButton.setSize(backArrow.getSize());
		todayButton.setLocation(backArrow.getX() + backArrow.getWidth(), backArrow.getY());
		
		nextArrow.setSize(backArrow.getSize());
		nextArrow.setLocation(todayButton.getX() + todayButton.getWidth(), todayButton.getY());
		
		notifBox.setSize(backArrow.getX() - calendar.getX() - 12, backArrow.getHeight());
		notifBox.setLocation(calendar.getX(), newEvent.getY());
		notifBox.setBackground(Color.getHSBColor((float)0.4, (float)0.2, (float) 0.95));
		notifBox.addItem(new String("There is no notifications"));
		
		weeknumber.setSize(newEvent.getWidth(), backArrow.getHeight());
		weeknumber.setLocation(calendar.getX() + calendar.getWidth() - newEvent.getWidth(), 16);
		
		kalendarScroll.setSize(newEvent.getWidth(), (int)((getHeight() - 40 - 32 -(newEvent.getHeight() * 3.5)))/2);
		kalendarScroll.setLocation(newEvent.getX(), deleteEvent.getY() + deleteEvent.getHeight() + 24);
		
		arrowButton.setSize(newEvent.getWidth(), 22);
		arrowButton.setLocation(newEvent.getX(), kalendarScroll.getY() + kalendarScroll.getHeight() + 1);
		
		personellScroll.setSize(newEvent.getWidth(), kalendarScroll.getHeight());
		personellScroll.setLocation(newEvent.getX(), kalendarScroll.getY() + kalendarScroll.getHeight() + 24);
		
		//kalendarPersons.setSize(newEvent.getWidth(), (int)((getHeight() - 40 - 32 -(newEvent.getHeight() * 3.5)))/2);
		//kalendarPersons.setLocation(newEvent.getX(), deleteEvent.getY() + deleteEvent.getHeight() + 24);
		
		//personellList.setSize(newEvent.getWidth(), kalendarPersons.getHeight());
		//personellList.setLocation(newEvent.getX(), kalendarPersons.getY() + kalendarPersons.getHeight() + 24);
		
		searchField.setSize(newEvent.getWidth(), newEvent.getHeight() /2);
		searchField.setLocation(newEvent.getX(), personellScroll.getY() + personellScroll.getHeight());
		
		
		
	}
	
	
	public static void main(String[] args){
		MainFrame panel = new MainFrame();
	}
	public void fillModel(){
		personellModel.addElement("Kari");
		personellModel.addElement("Per");
		personellModel.addElement("Andreas");
		personellModel.addElement("Per-Olav");
	}
	public class MouseListener implements MouseMotionListener, ActionListener{
		public void mouseDragged(MouseEvent e) {
		}
		public void mouseMoved(MouseEvent e) {
			if(e.getSource() == newEvent){
				newEvent.setText("Bиииии!");
			}
			else{
				newEvent.setText("New event");
			}
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(personellList.getSelectedValue() != null){
				Object o = personellList.getSelectedValue();
				personellModel.removeElement(o);
				kalendarModel.addElement(o);			
			}
		}
	}
}
