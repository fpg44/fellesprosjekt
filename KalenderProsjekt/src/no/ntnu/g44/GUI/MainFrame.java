package no.ntnu.g44.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.NoFixedFacet;

import no.ntnu.g44.components.ListRenderer;
import no.ntnu.g44.components.NotificationListCellRenderer;
import no.ntnu.g44.models.NotificationType;
import no.ntnu.g44.models.Notification;
import no.ntnu.g44.controllers.Main;
import no.ntnu.g44.controllers.NotificationController;

public class MainFrame extends JPanel{
	ListeningClass listener = new ListeningClass();
	Insets insets;
	JFrame frame;
	JButton newEvent = new JButton("New Event");
	JButton editEvent = new JButton("Edit Event");
	JButton deleteEvent = new JButton("Delete Event");
	JButton arrowButton = new JButton("^ ^ ^");
	JButton removeButton = new JButton("X");
	JTextField searchField = new JTextField("Search...");
	DefaultListModel personnelModel = new DefaultListModel();
	ArrayList<String> personnel = new ArrayList<String>();
	DefaultListModel calendarModel = new DefaultListModel();
	JList personnelList = new JList(personnelModel);
	JList calendarPersons = new JList(calendarModel);
	JScrollPane personnelScroll = new JScrollPane(personnelList);
	JScrollPane calendarScroll = new JScrollPane(calendarPersons);
	JComboBox notifBox = new JComboBox();
	JButton backArrow = new JButton(" < < ");
	JButton todayButton = new JButton(" Today ");
	JButton nextArrow = new JButton(" > > ");
	CalendarPanel calendar = new CalendarPanel();
	JLabel weeknumber = new JLabel("UKE 12");
	ListRenderer renderer = new ListRenderer();
	NotificationListCellRenderer notifRender = new NotificationListCellRenderer();
	
	//Used by actionListener to check if the list of notifications is empty
	NotificationController notificationController = new NotificationController();
	ArrayList unseenNotifications = notificationController.getUnseenNotifications();

	public MainFrame(){
//		calendarPersons.setCellRenderer(renderer);
//		calendarPersons.addMouseListener(renderer.getHandler(calendarPersons));  
//		calendarPersons.addMouseMotionListener(renderer.getHandler(calendarPersons)); 

		fillModel();
		fillModel();
		fillModel();
		fillModel();

		//notifBox.addItem(new String("There is no notifications"));
//		notifBox.setSelectedIndex(0);
		checkForNewNotifications();
		notifBox.addActionListener(new ListeningClass());
		notifBox.setRenderer(notifRender);
		
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		addMouseMotionListener(listener);
		frame = new JFrame("Test");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0, (int)dim.getWidth()-60, (int)dim.getHeight()-60);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.getContentPane().add(this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		insets = frame.getInsets();

		addMouseMotionListener(listener);
		editEvent.setVisible(true);
		editEvent.addActionListener(listener);
		newEvent.setVisible(true);
		deleteEvent.setVisible(true);
		deleteEvent.addActionListener(listener);
		arrowButton.setVisible(true);
		arrowButton.addActionListener(listener);
		removeButton.setVisible(true);
		removeButton.addActionListener(listener);
		searchField.setVisible(true);
		searchField.addMouseListener(listener);
		searchField.addKeyListener(listener);
		calendar.setVisible(true);
		calendar.addMouseListener(listener);
		//calendar.setBackground(Color.GRAY);
		personnelList.setVisible(true);
		personnelList.addMouseListener(listener);
		personnelList.addKeyListener(listener);
		personnelScroll.setVisible(true);
		calendarScroll.setVisible(true);
		backArrow.setVisible(true);

		nextArrow.setVisible(true);
		notifBox.setVisible(true);
		weeknumber.setVisible(true);

		add(personnelScroll);
		add(calendarScroll);
		add(newEvent);
		add(editEvent);
		add(deleteEvent);
		add(arrowButton);
		add(removeButton);
		add(searchField);
		add(calendar);
		add(backArrow);
		add(todayButton);
		add(nextArrow);
		add(notifBox);
		add(weeknumber);
		//add(personnelList);
		//add(calendarPersons);
		resizing();
	}
	@Override
	public void repaint(){
		resizing();
	}

	/**
	 * Checks for new notifications and puts them in 'notifBox'
	 */
	public void checkForNewNotifications() {
		int notifCounter = 0;

		if (!unseenNotifications.isEmpty()) {
			for (int i = 0; i < unseenNotifications.size(); i++) {
				notifCounter++;
			}
			notifBox.addItem(new String ("You have " + notifCounter + " notifications."));
			for (int i = 0; i < unseenNotifications.size(); i++) {
				notifBox.addItem((unseenNotifications.get(i)));
			}
		}
		
		else {
			notifBox.addItem(new String ("There is no new notifications"));
		}
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
		if(calendar.getSelectedEvent() != null){
			editEvent.setEnabled(true);
		}
		else{
			editEvent.setEnabled(false);
		}

		deleteEvent.setLocation(newEvent.getX(), editEvent.getY() + editEvent.getHeight());
		deleteEvent.setSize(newEvent.getSize());
		if(calendar.getSelectedEvent() != null){
			deleteEvent.setEnabled(true);
		}
		else{
			deleteEvent.setEnabled(false);
		}

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


		weeknumber.setSize(newEvent.getWidth(), backArrow.getHeight());
		weeknumber.setLocation(calendar.getX() + calendar.getWidth() - newEvent.getWidth(), 16);

		calendarScroll.setSize(newEvent.getWidth(), (int)((getHeight() - 40 - 32 -(newEvent.getHeight() * 3.5)))/2);
		calendarScroll.setLocation(newEvent.getX(), deleteEvent.getY() + deleteEvent.getHeight() + 24);

		arrowButton.setSize((newEvent.getWidth() / 2)-1, 22);
		arrowButton.setLocation(newEvent.getX(), calendarScroll.getY() + calendarScroll.getHeight() + 1);
		
		removeButton.setSize(arrowButton.getSize());
		removeButton.setLocation(newEvent.getX() + arrowButton.getWidth() + 2, arrowButton.getY());

		personnelScroll.setSize(newEvent.getWidth(), calendarScroll.getHeight());
		personnelScroll.setLocation(newEvent.getX(), calendarScroll.getY() + calendarScroll.getHeight() + 24);

		//calendarPersons.setSize(newEvent.getWidth(), (int)((getHeight() - 40 - 32 -(newEvent.getHeight() * 3.5)))/2);
		//calendarPersons.setLocation(newEvent.getX(), deleteEvent.getY() + deleteEvent.getHeight() + 24);

		//personnelList.setSize(newEvent.getWidth(), calendarPersons.getHeight());
		//personnelList.setLocation(newEvent.getX(), calendarPersons.getY() + calendarPersons.getHeight() + 24);

		searchField.setSize(newEvent.getWidth(), newEvent.getHeight() /2);
		searchField.setLocation(newEvent.getX(), personnelScroll.getY() + personnelScroll.getHeight());

	}


	public void fillModel(){
		personnel.add("Kari");
		personnel.add("Per");
		personnel.add("Andreas");
		personnel.add("Per-Olav");
		personnel.add("Anders");
		personnel.add("Karl");
		personnel.add("Fridtjof");
		personnel.add("Bjarne");
		personnel.add("john");
		personnel.add("Beate");
		personnel.add("Karl-Ove");
		personnelModel.removeAllElements();
		for(int i = 0; i < personnel.size(); i++){
			personnelModel.addElement(personnel.get(i));
		}

	}
	public void addPersons(){
		Object o[] = personnelList.getSelectedValues();
		for(int i = 0; i < o.length; i++){
			personnelModel.removeElement(o[i]);
			calendarModel.addElement(o[i]);
		}
	}
	public class ListeningClass implements MouseMotionListener, ActionListener, MouseListener, KeyListener{
		boolean shift = false;
		public void mouseDragged(MouseEvent e) {
		}
		public void mouseMoved(MouseEvent e) {
			if(e.getSource() == newEvent){
				newEvent.setText("BØØØØØ!");
			}
			else{
				newEvent.setText("New event");
			}
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!unseenNotifications.isEmpty()){
				if (e.getSource() == notifBox) {
					if (notifBox.getSelectedIndex() == 0) {
						System.out.println("Nothing happens");
					}
					//Her skal vi legge inn kall pŒ forskjellige dialogbokser som kommer
					//som f¿lge av man trykker pŒ en notifikasjon.
					else if (((Notification) notifBox.getSelectedItem()).getType() == NotificationType.EVENT_CANCELLED){
						System.out.println("This event has been cancelled");
						//Inne i disse metoden mŒ vi legge til slik at en notifikasjon
						//blir borte om man besvarer eventet. Om man ikke svarer
						//skal notifikasjonen fortsatt v¾re i listen
					}
					else if (((Notification) notifBox.getSelectedItem()).getType() == NotificationType.EVENT_INVITATION) {
						System.out.println("You have a new event invitation");
					}
					else if (((Notification) notifBox.getSelectedItem()).getType() == NotificationType.EVENT_TIME_CHANGED) {
						System.out.println("This event have been changed");
					}
					else if (((Notification) notifBox.getSelectedItem()).getType() == NotificationType.PARTICIPANT_DECLINED_INVITATION){
						System.out.println("A participant has declined your invitation");
					}
					notifBox.setSelectedIndex(0);
				}
			}
			if(e.getSource() == deleteEvent){
				if(calendar.getSelectedEvent() != null){
					System.out.println("delete");
					Main.currentProject.removeEvent(calendar.getSelectedEvent());
				}
			}
			if(e.getSource() == editEvent){
				if(calendar.getSelectedEvent() != null){
					//edit
				}
			}
			if(e.getSource() == arrowButton && personnelList.getSelectedValue() != null){
				addPersons();			
			}
			else if(e.getSource() == removeButton && calendarPersons.getSelectedValue() != null){
				calendarModel.removeElement(calendarPersons.getSelectedValue());
			}
		}
		@Override
		public void keyPressed(KeyEvent e) {
			
			if((e.getSource() == personnelList || e.getSource() == searchField)&& e.getKeyChar() == KeyEvent.VK_ENTER){
				addPersons();
				return;
			}
			if(e.getSource() == searchField){
				if(e.getKeyCode() == KeyEvent.VK_SHIFT){
					shift = true;
					return;
				}
				//arrow
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					int index = personnelList.getLeadSelectionIndex();
					if(index == personnelModel.getSize() -1){
						if(shift){
							personnelList.addSelectionInterval(index, 0);
							return;
						}
						personnelList.setSelectedIndex(0);
						return;
					}
					if(shift){
						personnelList.addSelectionInterval(index, index+1);
						return;
					}
					personnelList.setSelectedIndex(index + 1);
					return;
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
					int index = personnelList.getSelectedIndex();
					if(index == 0){
						if(shift){
							personnelList.addSelectionInterval(index, personnelModel.size() -1);
							return;
						}
						personnelList.setSelectedIndex(personnelModel.size() -1);
						return;
					}
					if(shift){
						personnelList.addSelectionInterval(index, index -1);
						return;
					}
					personnelList.setSelectedIndex(index -1);
					return;
				}
				//search
				String search = searchField.getText();
				if(Character.isLetter(e.getKeyChar()) || e.getKeyChar() == '-'){
					search += e.getKeyChar();
				}
				else if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE && search.length() > 0){
					search = search.substring(0, search.length() -1);
				}
				search = search.toLowerCase();
				String person;
				personnelModel.removeAllElements();
				for(int i = 0; i < personnel.size(); i++){
					person = personnel.get(i).toLowerCase();
					if(person.startsWith(search) || person.equals(search)){
						personnelModel.addElement(personnel.get(i));
						continue;
					}
					else{
					}
				}
				if(personnelModel.size() > 0){
					personnelList.setSelectedIndex(0);
				}
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_SHIFT){
				shift = false;
			}
		}
		@Override
		public void keyTyped(KeyEvent e) {

		}
		@Override
		public void mouseClicked(MouseEvent e) {
			
			if(e.getClickCount() == 2 && e.getSource() == personnelList){
				if(personnelList.getSelectedValue() != null){
					addPersons();			
				}
			}
			if(e.getSource() == searchField){
				searchField.selectAll();
			}

		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getSource() == calendar){
				if(e.getButton() == MouseEvent.BUTTON3){
					Main.currentProject.removeEvent(calendar.getSelectedEvent());
				}
				resizing();
			}
		}
	}
}
