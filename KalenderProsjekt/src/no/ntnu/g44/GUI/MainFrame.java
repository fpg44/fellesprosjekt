package no.ntnu.g44.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import no.ntnu.g44.components.ListRenderer;

public class MainFrame extends JPanel{
	MouseListening listener = new MouseListening();
	Insets insets;
	JFrame frame;
	JButton newEvent = new JButton("New Event");
	JButton editEvent = new JButton("Edit Event");
	JButton deleteEvent = new JButton("Delete Event");
	JButton arrowButton = new JButton("^   ^   ^   ^   ^   ^   ^   ^");
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
	
	public MainFrame(){
		calendarPersons.setCellRenderer(renderer);
		calendarPersons.addMouseListener(renderer.getHandler(calendarPersons));  
		calendarPersons.addMouseMotionListener(renderer.getHandler(calendarPersons)); 
		
		fillModel();
		fillModel();
		fillModel();
		fillModel();
		notifBox.addItem(new String("There is no notifications"));
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
		newEvent.setVisible(true);
		deleteEvent.setVisible(true);
		arrowButton.setVisible(true);
		arrowButton.addActionListener(listener);
		searchField.setVisible(true);
		searchField.addMouseListener(listener);
		searchField.addKeyListener(listener);
		calendar.setVisible(true);
		//calendar.setBackground(Color.GRAY);
		personnelList.setVisible(true);
		personnelList.addMouseListener(listener);
		personnelList.addKeyListener(listener);
		calendarPersons.setVisible(true);
		personnelScroll.setVisible(true);
		calendarScroll.setVisible(true);
		backArrow.setVisible(true);
		
		todayButton.setVisible(true);
		nextArrow.setVisible(true);
		notifBox.setVisible(true);
		weeknumber.setVisible(true);
		
		add(personnelScroll);
		add(calendarScroll);
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
		//add(personnelList);
		//add(calendarPersons);
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
		
		
		weeknumber.setSize(newEvent.getWidth(), backArrow.getHeight());
		weeknumber.setLocation(calendar.getX() + calendar.getWidth() - newEvent.getWidth(), 16);
		
		calendarScroll.setSize(newEvent.getWidth(), (int)((getHeight() - 40 - 32 -(newEvent.getHeight() * 3.5)))/2);
		calendarScroll.setLocation(newEvent.getX(), deleteEvent.getY() + deleteEvent.getHeight() + 24);
		
		arrowButton.setSize(newEvent.getWidth(), 22);
		arrowButton.setLocation(newEvent.getX(), calendarScroll.getY() + calendarScroll.getHeight() + 1);
		
		personnelScroll.setSize(newEvent.getWidth(), calendarScroll.getHeight());
		personnelScroll.setLocation(newEvent.getX(), calendarScroll.getY() + calendarScroll.getHeight() + 24);
		
		//calendarPersons.setSize(newEvent.getWidth(), (int)((getHeight() - 40 - 32 -(newEvent.getHeight() * 3.5)))/2);
		//calendarPersons.setLocation(newEvent.getX(), deleteEvent.getY() + deleteEvent.getHeight() + 24);
		
		//personnelList.setSize(newEvent.getWidth(), calendarPersons.getHeight());
		//personnelList.setLocation(newEvent.getX(), calendarPersons.getY() + calendarPersons.getHeight() + 24);
		
		searchField.setSize(newEvent.getWidth(), newEvent.getHeight() /2);
		searchField.setLocation(newEvent.getX(), personnelScroll.getY() + personnelScroll.getHeight());
		
		
		
	}
	
	
	public static void main(String[] args){
		MainFrame panel = new MainFrame();
	}
	public void fillModel(){
		personnel.add("Kari");
		personnel.add("Per");
		personnel.add("Andreas");
		personnel.add("Per-Olav");
		personnelModel.removeAllElements();
		for(int i = 0; i < personnel.size(); i++){
			personnelModel.addElement(personnel.get(i));
		}
		
	}
	public class MouseListening implements MouseMotionListener, ActionListener, MouseListener, KeyListener{
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
			if(personnelList.getSelectedValue() != null){
				Object o[] = personnelList.getSelectedValues();
				for(int i = 0; i < o.length; i++){
					personnelModel.removeElement(o[i]);
					calendarModel.addElement(o[i]);
				}			
			}
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if((e.getSource() == personnelList || e.getSource() == searchField)&& e.getKeyChar() == KeyEvent.VK_ENTER){
				Object o[] = personnelList.getSelectedValues();
				for(int i = 0; i < o.length; i++){
					personnelModel.removeElement(o[i]);
					calendarModel.addElement(o[i]);
				}
				
			}
			if(e.getSource() == searchField){
				personnelModel.removeAllElements();
				for(int i = 0; i < personnel.size(); i++){
					if(personnel.get(i).length() < searchField.getText().length()+1){
						continue;
					}
					if(e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_CONTROL || e.getKeyChar() == KeyEvent.VK_SHIFT){
						if(personnel.get(i).startsWith(searchField.getText())){
							personnelModel.addElement(personnel.get(i));
							continue;
						}
					}
					if(personnel.get(i).startsWith(searchField.getText() + e.getKeyChar())){
						personnelModel.addElement(personnel.get(i));
					}
				}
			}
			
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2 && e.getSource() == personnelList){
				if(personnelList.getSelectedValue() != null){
					Object o = personnelList.getSelectedValue();
					personnelModel.removeElement(o);
					calendarModel.addElement(o);			
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
			// TODO Auto-generated method stub
			
		}
	}
}
