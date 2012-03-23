package no.ntnu.g44.models;

import java.awt.Color;
import java.beans.EventSetDescriptor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import no.ntnu.g44.controllers.Main;
import no.ntnu.g44.serverAndSession.FileStorage;
import no.ntnu.g44.serverAndSession.Storage;
import no.ntnu.g44.serverAndSession.XmlSerializer;

/**
 * The <code>Project</code> class is a list of zero or more {@link Person} objects.
 * 
 * @author Thomas &Oslash;sterlie
 * @version $Revision: 1.9 $ - $Date: 2005/02/22 07:53:33 $
 *
 */
public class Project implements PropertyChangeListener {
	
	
	private Random generateID = new Random();
	
	private XmlSerializer xmlSerializer = new XmlSerializer();
	/**
	 * the one that is logged in.
	 */
	private Person personLoggedIn;
	/**
	 * The member variable storing all registered {@link Person} objects.
	 */
	private ArrayList<Person> personList;
	
	
	/**
	 * All events
	 */
	private ArrayList<Event> eventList; 
	
	/**
	 * All rooms
	 */
	private ArrayList<Room> roomList;
	
	/**
	 * All notifications
	 */
	private ArrayList<Notification> notificationList;
	
	/**
	 * All attendance statuses to the participants
	 */
	private ArrayList<AttendanceStatus> attendanceStatusList;
	/**
	 * This member variable provides functionality for notifying of changes to
	 * the <code>Project</code> class.
	 */
	private java.beans.PropertyChangeSupport propChangeSupp;
	
	private Storage storage;
	
	
	/**
	 * Default constructor.  Must be called to initialise the object's member variables.
	 */
	public Project() {
		personList = new ArrayList<Person>();
		eventList = new ArrayList<Event>();
		propChangeSupp = new PropertyChangeSupport(this);
		notificationList = new ArrayList<Notification>();
		roomList = new ArrayList<Room>();
		attendanceStatusList = new ArrayList<AttendanceStatus>();
		
		storage = new FileStorage();
	}
	
	/**
	 * Returns the number of {@linkplain #addPerson(Person) <code>Person</code> objects
	 * registered} with this class.
	 * 
	 * @return The number of {@link Person} objects in this class.
	 */
	public int getPersonCount() {
		return personList.size();
	}
	
	public int getEventCount(){
		return eventList.size();
	}
	
	/**
	 * Returns the {@link Person} object at the specified position in the list.
	 * 
	 * @param i Index of object to return.
	 * 
	 * @return The {@link Person} object at the specified position in the list.
	 */
	public Person getPerson(int i) {
		return personList.get(i);
	}
	
	public Event getEvent(int i){
		return eventList.get(i);
	}
	
	/**
	 * This is used when i.e viewing changed-event
	 * @param id
	 * @return the event with the given id
	 */
	public Event getEventById(int id){
		for(int i = 0; i<eventList.size(); i++){
			if(eventList.get(i).getEventID() == id){
				return eventList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Returns the index of the first occurrence of the specified object, or 
	 * -1 if the list does not contain this object.
	 * 
	 * @param obj Object to search for.
	 *
	 * @return The index in this list of the first occurrence of the specified element, 
	 * or -1 if this list does not contain this element.
	 */
	public int indexOf(Object obj) {
		return personList.indexOf(obj);
	}

	/**
	 * Returns an iterator over the elements in this list in proper sequence.<P>
	 *
	 * @return A {@link java.util.Iterator} over the elements in this list in proper sequence.
	 * 
	 * @see java.util.Iterator <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/util/Iterator.html">java.util.Iterator</a>.
	 */
	public Iterator<Person> personIterator() {
		return personList.iterator();
	}

	public Iterator<Event> eventIterator(){
		return eventList.iterator();
	}
	
	public Iterator<Notification> notificationIterator(){
		return notificationList.iterator();
	}
	
	public Iterator<Room> roomIterator(){
		return roomList.iterator();
	}
	
	public Iterator<AttendanceStatus> attendanseStaturIterator(){
		return attendanceStatusList.iterator();
	}
	
	/**
	 * WARNING: USE ONLY FOR READING!!
	 * @return the raw ArrayList
	 */
	public ArrayList<Event> getEventList(){
		return eventList;
	}
	
	public ArrayList<Person> getPersonList(){
		return personList;
	}
	
	public ArrayList<Notification> getNotificationList(){
		return notificationList;
	}
	
	public ArrayList<Room> getRoomList(){
		return roomList;
	}
	
	public ArrayList<AttendanceStatus> getAttendanceStatusList(){
		return attendanceStatusList;
	}
	
	/**
	 * Adds a new {@link Person} object to the <code>Project</code>.<P>
	 * 
	 * Calling this method will invoke the 
	 * <code>propertyChange(java.beans.PropertyChangeEvent)</code> method on 
	 * all {@linkplain
	 * #addPropertyChangeListener(java.beans.PropertyChangeListener) registered
	 * <code>PropertyChangeListener<code> objecs}.  The {@link java.beans.PropertyChangeEvent}
	 * passed with the {@link java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)}
	 * method has the following characteristics:
	 * 
	 * <ul>
	 * <li>the <code>getNewValue()</code> method returns the {@link Person} object added</li>
	 * <li>the <code>getOldValue()</code> method returns <code>null</code></li>
	 * <li>the <code>getSource()</code> method returns this {@link Project} object
	 * </ul>
	 * 
	 * @param person The {@link Person} object added.
	 * 
	 * @see java.beans.PropertyChangeListener <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeListener.html">java.beans.PropertyChangeListener</a>
	 * @see java.beans.PropertyChangeEvent <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeEvent.html">java.beans.PropertyChangeEvent</a>
	 */
	public void addPerson(Person person) {
		personList.add(person);
		person.addPropertyChangeListener(this);
		propChangeSupp.firePropertyChange("person", null, person);
	}

	public void addEvent(Event event, boolean save){
		eventList.add(event);
		event.addPropertyChangeListener(this);
		propChangeSupp.firePropertyChange("event", null, event);
		
		try {
			//true if the event shall be added to XML/database
			//false if the event only should be added to eventList
			if(save){
				
				//If Internet (A1, server and database is being used)
				if(Main.usenet){
					//Sender event til clienten
					Main.client.newEvent(xmlSerializer.eventToXml(event));
					
					//Sender alle statuser som tilhørere eventet til clienten
					for(AttendanceStatus status : getStatusToEvent(event)){
						Main.client.newAttendanceStatus(xmlSerializer.attendanceStatusToXml(status));
					}
				}
				//If only XML to file is being used
				else{
					String cuPath = new File(".").getAbsolutePath();
					storage.save(new URL("file://"+cuPath+"/project.xml"), this);					
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void addRoom(Room room){
		roomList.add(room);
		room.addPropertyChangeListener(this);
		propChangeSupp.firePropertyChange("room", null, room);
	}
	
	public void addNotification(Notification notification, boolean save) throws ConnectException, IOException{
		notificationList.add(notification);
		notification.addPropertyChangeListener(this);
		propChangeSupp.firePropertyChange("notification", null, notification);
		
		if(Main.usenet && save){
			Main.client.newNotification(xmlSerializer.notificationToXml(notification));
		}
	}
	
	public void addAttendanceStatus(AttendanceStatus status, boolean save) throws ConnectException, IOException{
		attendanceStatusList.add(status);
		status.addPropertyChangeListener(this);
		propChangeSupp.firePropertyChange("status", null, status);
		
		if(Main.usenet && save){
			Main.client.newAttendanceStatus(xmlSerializer.attendanceStatusToXml(status));
		}
	}
	
	/**
	 * Removes the specified {@link Person} object from the <code>Project</code>.<P>
	 * 
	 * Calling this method will invoke the 
	 * <code>propertyChange(java.beans.PropertyChangeEvent)</code> method on 
	 * all {@linkplain
	 * #addPropertyChangeListener(java.beans.PropertyChangeListener) registered
	 * <code>PropertyChangeListener<code> objecs}.  The {@link java.beans.PropertyChangeEvent}
	 * passed with the {@link java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)}
	 * method has the following characteristics:
	 * 
	 * <ul>
	 * <li>the <code>getNewValue()</code> method returns an {@link java.lang.Integer} object 
	 *     with the index of the removed element</li>
	 * <li>the <code>getOldValue()</code> method returns the {@link Person} object added</li>
	 * <li>the <code>getSource()</code> method returns this {@link Project} object
	 * </ul>
	 * 
	 * @param person The {@link Person} object to be removed.
	 * 
	 * @see java.beans.PropertyChangeListener <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeListener.html">java.beans.PropertyChangeListener</a>
	 * @see java.beans.PropertyChangeEvent <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeEvent.html">java.beans.PropertyChangeEvent</a>
	 */
	public void removePerson(Person person) {
		int i = personList.indexOf(person);
		Integer index = new Integer(i);
		personList.remove(person);
		person.removePropertyChangeListener(this);
		propChangeSupp.firePropertyChange("person", person, index);
	}

	public void removeAttendanceStatus(AttendanceStatus status){
		int i = attendanceStatusList.indexOf(status);
		Integer index = new Integer(i);
		attendanceStatusList.remove(status);
		status.removePropertyChangeListener(this);
		propChangeSupp.firePropertyChange("status", status, index);
		
	}
	
	public void removeNotification(Notification notification) throws ConnectException, IOException{
		int i = notificationList.indexOf(notification);
		notificationList.remove(notification);
		notification.removePropertyChangeListener(this);
		propChangeSupp.firePropertyChange("notification", notification, i);
		
		if(Main.usenet){
			Main.client.deleteNotification(xmlSerializer.notificationToXml(notification));
		}
	}
	
	public void removeEvent(Event event){
		int i = eventList.indexOf(event);
		eventList.remove(event);
		event.removePropertyChangeListener(this);
		propChangeSupp.firePropertyChange("event",event,i);
		
		try {
			
			String cuPath = new File(".").getAbsolutePath();
			storage.save(new URL("file://"+cuPath+"/project.xml"), this);
			
			if(Main.usenet){
				Main.client.deleteEvent(xmlSerializer.eventToXml(event));
			}
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void editEvent(Event event) throws ConnectException, IOException{
		if(Main.usenet){
			Main.client.updateEvent(xmlSerializer.eventToXml(event));
		}
	}
	
	public void editNotification(Notification notification) throws ConnectException, IOException{
		if(Main.usenet){
			Main.client.updateNotification(xmlSerializer.notificationToXml(notification));
		}
	}
	
	public void editAttendanceStatus(AttendanceStatus status) throws ConnectException, IOException{
		if(Main.usenet){
			Main.client.updateAttendanceStatus(xmlSerializer.attendanceStatusToXml(status));
		}
	}
	
	/**
	 * Add a {@link java.beans.PropertyChangeListener} to the listener list.
	 * 
	 * @param listener The {@link java.beans.PropertyChangeListener} to be added.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propChangeSupp.addPropertyChangeListener(listener);
	}
	
	/**
	 * Remove a {@link java.beans.PropertyChangeListener} from the listener list.
	 * 
	 * @param listener The {@link java.beans.PropertyChangeListener} to be removed.
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propChangeSupp.removePropertyChangeListener(listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		propChangeSupp.firePropertyChange(event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (super.equals(o))
			return true;

		if (o.getClass() != this.getClass())
			return false;
		
		Project aProject = (Project)o;
		
		if (aProject.getPersonCount() != getPersonCount())
			return false;
		
		Iterator it = this.personIterator();
		while (it.hasNext()) {
			Person aPerson = (Person)it.next();
			if (aProject.indexOf(aPerson) < 0)
				return false;
		}
		
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String s = "project:\n";
		Iterator it = this.personIterator();
		while (it.hasNext()) {
			s += it.next().toString() + "\n";
		}
		return s;
	}
	public void setLoggedInPerson(Person user){
		personLoggedIn = user;
	}
	public Person getLoggedInPerson(){
		return personLoggedIn;
	}
	
	public Room getOtherRoom(){
		
		for(int i = 0; i<roomList.size(); i++){
			if(roomList.get(i).getRoomName().equals("OTHER")){
				return roomList.get(i);
			}
		}
		return null;
	}
	
	public AttendanceStatus getStatus(int event_id, String username){
		for(AttendanceStatus status: attendanceStatusList){
			if(status.getEventID() == event_id && status.getUsername().equals(username)){
				return status;
			}
		}
		return null;
	}
	public static HashMap<String, AttendanceStatusType> colourmap = new HashMap<String, AttendanceStatusType>();
	public static Color getColor(Event e, Person p){
		if(colourmap.get(p.getUsername() + "" + e.getEventID()) == null)return AttendanceStatusType.getColor(AttendanceStatusType.UNANSWERED);
		System.out.println(colourmap.get(p.getUsername() + e.getEventID()));
		return AttendanceStatusType.getColor(colourmap.get(p.getUsername() + "" + e.getEventID()));
	}
	
	public static void updateStatus(Event e, Person p, AttendanceStatusType status){
		colourmap.put(p.getUsername() + "" + e.getEventID(), status);
	}
	public static void updateStatus(int eID, String username, AttendanceStatusType status){
		colourmap.put(username + "" + eID, status);
	}
	public static Collection<AttendanceStatusType> getAttendanceList(){
		return colourmap.values();
	}
	
	/**
	 * 
	 * @param event
	 * @return an ArrayList with all the attendance statuses associated with the input event
	 */
	public ArrayList<AttendanceStatus> getStatusToEvent(Event e){
		ArrayList<AttendanceStatus> list = new ArrayList<AttendanceStatus>();
		for(AttendanceStatus status : attendanceStatusList){
			if(status.getEventID() == e.getEventID()){
				list.add(status);
			}
		}
		return list;
	}
	
	public int generateID(){
		return generateID.nextInt(999999999) + 1;
	}
}
