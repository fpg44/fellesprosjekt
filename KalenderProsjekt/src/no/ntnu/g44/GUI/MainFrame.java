package no.ntnu.g44.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import no.ntnu.g44.components.NotificationListCellRenderer;
import no.ntnu.g44.controllers.Main;
import no.ntnu.g44.models.AttendanceStatus;
import no.ntnu.g44.models.AttendanceStatusType;
import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Notification;
import no.ntnu.g44.models.NotificationType;
import no.ntnu.g44.models.Person;

public class MainFrame extends JPanel{
	ListeningClass listener = new ListeningClass();
	Insets insets;
	JFrame frame;
	Timer timer = new Timer();

	JPopupMenu popup = new JPopupMenu();
	JMenuItem item3 = new JMenuItem("Delete Event");
	JMenuItem item2 = new JMenuItem("Edit Event");
	JMenuItem item1 = new JMenuItem("New Event");
	//	JMenuItem item4 = new JMenuItem("Logout");

	JButton newEvent = new JButton("New Event");
	JButton editEvent = new JButton("Edit Event");
	JButton deleteEvent = new JButton("Delete Event");
	JButton arrowButton = new JButton("^ ^ ^");
	JButton removeButton = new JButton("X");
	JTextField searchField = new JTextField("Search...");
	DefaultListModel<Person> personnelModel = new DefaultListModel<Person>();
	ArrayList<Person> personnel = new ArrayList<Person>();
	DefaultListModel<Person> calendarModel = new DefaultListModel<Person>();
	JList personnelList = new JList(personnelModel);
	JList calendarPersons = new JList(calendarModel);
	JScrollPane personnelScroll = new JScrollPane(personnelList);
	JScrollPane calendarScroll = new JScrollPane(calendarPersons);
	private JComboBox<Notification> notifBox = new JComboBox<Notification>();
	JButton backArrow = new JButton(" < < ");
	JButton todayButton = new JButton(" Today ");
	JButton nextArrow = new JButton(" > > ");
	CalendarPanel calendar = new CalendarPanel();
	DefaultComboBoxModel<Notification> aModel = new DefaultComboBoxModel<Notification>();
	JMenuItem newAction;
	JMenuItem logoutAction;
	JMenuItem exitAction;

	int WEEK_NUMBER = 0;
	int currentWeekNumber = WEEK_NUMBER;
	int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	JLabel weeknumber = new JLabel("WEEK " + WEEK_NUMBER);

	//	public int countNotificationFuck(){
	//		int counter = 0;
	//		for(Notification n : Main.currentProject.getNotificationList()){
	//			if(Main.currentProject.getStatus(n.getEventID(), n.getPersonString()).getStatus() != AttendanceStatusType.ATTENDING){
	//				counter ++;
	//			}
	//		}
	//		return counter;
	//	}

	public MainFrame(){
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				calendar.repaint();
				WEEK_NUMBER = calendar.getWeekNumber();
				if(currentWeekNumber == 0)currentWeekNumber = WEEK_NUMBER;
				resizing();
				//				notificationFuck();
			}
		}, 0, 1000);

		fillModel();

		newEvent.addMouseMotionListener(listener);
		newEvent.addMouseListener(listener);

		item1.addActionListener(listener);
		item2.addActionListener(listener);
		item3.addActionListener(listener);
		//		item4.addActionListener(listener);
		popup.add(item1);
		popup.add(item2);
		popup.add(item3);
		//		popup.add(item4);

		//		notifBox.addMouseListener(listener);
		notifBox.setRenderer(new NotificationListCellRenderer());
		notifBox.setModel(aModel);
		notifBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				if((Notification)
						aModel.getSelectedItem()!= null && ((Notification)
						aModel.getSelectedItem())
						.getType() == NotificationType.EVENT_INVITATION){
					EventInvitationPanel eip = new EventInvitationPanel(Main.currentProject.getEventById(((Notification)aModel.getSelectedItem()).getEventID()));
					notifBox.setSelectedIndex(0);
				}
				
			}
		});
		

		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		addMouseMotionListener(listener);
		frame = new JFrame("Logged in as " + Main.currentProject.getLoggedInPerson());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0, (int)dim.getWidth()-60, (int)dim.getHeight()-60);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.getContentPane().add(this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		insets = frame.getInsets();

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu systemMenu = new JMenu("System");
		menuBar.add(systemMenu);
		newAction = new JMenuItem("New Event");
		logoutAction = new JMenuItem("Logout");
		exitAction = new JMenuItem("Exit");
		systemMenu.add(newAction);
		systemMenu.add(logoutAction);
		systemMenu.add(exitAction);
		newAction.addActionListener(listener);
		logoutAction.addActionListener(listener);
		exitAction.addActionListener(listener);

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
		calendarPersons.setCellRenderer(new colourListCellRenderer());
		backArrow.setVisible(true);
		backArrow.addActionListener(listener);
		nextArrow.setVisible(true);
		nextArrow.addActionListener(listener);
		notifBox.setVisible(true);
		weeknumber.setVisible(true);
		todayButton.addActionListener(listener);

		//		checkForNewNotifications();

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
		notificationFuck();
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

		editEvent.setSize(newEvent.getSize());
		editEvent.setLocation(newEvent.getX(), newEvent.getY() + newEvent.getHeight());
		if(calendar.getSelectedEvent() != null){
			if (calendar.getSelectedEvent().getEventOwner() == Main
					.currentProject.getLoggedInPerson()) {
				editEvent.setEnabled(true);
			} else {
				editEvent.setEnabled(false);
			}
		}
		else{
			editEvent.setEnabled(false);
		}

		deleteEvent.setLocation(newEvent.getX(), editEvent.getY() + editEvent.getHeight());
		deleteEvent.setSize(newEvent.getSize());
		if(calendar.getSelectedEvent() != null){
			if (calendar.getSelectedEvent().getEventOwner() == Main
					.currentProject.getLoggedInPerson()) {
				deleteEvent.setEnabled(true);
			} else {
				deleteEvent.setEnabled(false);
			}
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

		weeknumber.setText("Week number " + currentWeekNumber);
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
		//		checkForNewNotifications();
	}


	public void fillModel(){

		for(int i = 0; i < Main.currentProject.getPersonCount(); i++){
			personnel.add(Main.currentProject.getPerson(i));
		}
		personnelModel.removeAllElements();
		for(int i = 0; i < personnel.size(); i++){
			if(personnel.get(i) == Main.currentProject.getLoggedInPerson())continue;
			personnelModel.addElement(personnel.get(i));
		}

	}
	public void addPersons(){
		Object[] o = personnelList.getSelectedValues();
		for(int i = 0; i < o.length; i++){
			personnelModel.removeElement(o[i]);
			calendarModel.addElement((Person) o[i]);
		}
	}
	public void newEvent(){
		new NewEventPanel(Main.currentProject.getLoggedInPerson(), new JFrame());
	}
	public void deleteEvent(){
		if(calendar.getSelectedEvent() != null){
			if(JOptionPane.showConfirmDialog(null, "Are you uncertain?") == JOptionPane.NO_OPTION){
//				calendar.getSelectedEvent().expired = true;
				Event temp = Main.currentProject.getEventById(calendar.getSelectedEvent().getEventID());
				Main.currentProject.removeEvent(temp);
				temp.expired = true;
				Main.currentProject.addEvent(temp, true);
			}
			else{
				JOptionPane.showMessageDialog(null, "Let me know when you are certain.");
			}
		}
	}
	public void editEvent(){
		if (calendar.getSelectedEvent() == null)
			return;
		new EditEventPanel(calendar.getSelectedEvent(),
				Main.currentProject.getLoggedInPerson(), new JFrame());
	}
	public void logout(){
		Login.login();
		frame.dispose();
	}
	public void search(String search){
		String person;
		personnelModel.removeAllElements();
		for(int i = 0; i < personnel.size(); i++){
			if(personnel.get(i) == Main.currentProject.getLoggedInPerson()){
				continue;
			}
			person = personnel.get(i).getName().toLowerCase();
			if(person.startsWith(search) || person.equals(search) || search.equals("search...")){
				boolean b = true;
				for(int j = 0; j < calendarModel.getSize(); j++){
					if(calendarModel.getElementAt(j).getName().toLowerCase().equals(person)){
						b = false;
					}
				}
				if(b) personnelModel.addElement(personnel.get(i));
				continue;
			}
			else{
			}
		}
		if(personnelModel.size() > 0){
			personnelList.setSelectedIndex(0);
		}
	}


	public void notificationFuck(){
		//		notifBox.removeAllItems();
		//
		//		for(Notification n : Main.currentProject.getNotificationList()){
		//			notifBox.addItem(n);
		//		}
		aModel.removeAllElements();
		int count = aModel.getSize();
		String not = "You have " + count + " notifications";
		notifBox.addItem(new Notification(0, NotificationType.NO_NOTIFICATION, ""));
		aModel.getElementAt(0).getType().setText(not);
		for(Notification n : Main.currentProject.getNotificationList()){
			aModel.addElement(n);
		}

		if(notifBox.getItemCount() == 0){
			notifBox.setEnabled(false);
		}
		else{
			notifBox.setEnabled(true);
		}

	}

	public class ListeningClass implements MouseMotionListener, ActionListener, MouseListener, KeyListener{
		boolean shift = false;
		@Override
		public void mouseDragged(MouseEvent e) {
		}
		@Override
		public void mouseMoved(MouseEvent e) { }
		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource() == item1){
				newEvent();
			}
			if(e.getSource() == item2){
				editEvent();
			}
			if(e.getSource() == item3){
				deleteEvent();
			}
			if(e.getSource() == newAction){
				newEvent();
			}
			if(e.getSource() == logoutAction){
				logout();
			}
			if(e.getSource() == exitAction){
				System.exit(0);
			}
			if(e.getSource() == nextArrow){
				currentWeekNumber +=1;
				if (currentWeekNumber == 53) {
					currentWeekNumber = 1;
					currentYear++;
				}
				calendar.repaint();
			}
			if(e.getSource() == backArrow){
				currentWeekNumber -=1;
				if (currentWeekNumber == 0) {
					currentWeekNumber = 52;
					currentYear--;
				}
				calendar.repaint();
			}
			if(e.getSource() == todayButton){
				currentWeekNumber = WEEK_NUMBER;
				calendar.repaint();
			}
			resizing();
			if(e.getSource() == editEvent){
				editEvent();
			}
			if(e.getSource() == newEvent){
				newEvent();
			}
			if(e.getSource() == deleteEvent){
				deleteEvent();
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
				search(searchField.getText().toLowerCase());
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
				search(search);
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
			if(e.getClickCount() == 2 && e.getSource() == calendar){
				if(calendar.getSelectedEvent()==null)return;
				new EventInfoPanel(calendar.getSelectedEvent(), new JFrame());
				//				EventInfoPanel.makeInfoPanel(calendar.getSelectedEvent());
			}
			if(e.getClickCount() == 2 && e.getSource() == personnelList){
				if(personnelList.getSelectedValue() != null){
					addPersons();			
				}
			}
			if(e.getSource() == searchField){
				searchField.selectAll();
			}

			if (e.getSource() == newEvent) {
				newEvent();
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
			if(e.getSource() == calendar){
				if(e.getButton() == MouseEvent.BUTTON3){
					if(e.isPopupTrigger()){
						popup.show(calendar, e.getX(), e.getY());
					}
				}
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println(e.getID());

			if(e.getSource() == calendar){
				if(e.getButton() == MouseEvent.BUTTON3){
					if(e.isPopupTrigger()){
						popup.show(calendar, e.getX(), e.getY());
					}
				}


				resizing();
			}

		}
	}

	public class colourListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			Paint[] colours = new Paint[4];
			colours[0] = Color.blue;
			colours[1] = Color.green;
			colours[2] = Color.magenta;
			colours[3] = Color.orange;
			c.setBackground((Color) colours[index%4]);
			return c;
		}
	}

}
