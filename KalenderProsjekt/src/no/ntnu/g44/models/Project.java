package no.ntnu.g44.models;

import java.beans.EventSetDescriptor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import no.ntnu.g44.serverAndSession.FileStorage;
import no.ntnu.g44.serverAndSession.Storage;

/**
 * The <code>Project</code> class is a list of zero or more {@link Person} objects.
 * 
 * @author Thomas &Oslash;sterlie
 * @version $Revision: 1.9 $ - $Date: 2005/02/22 07:53:33 $
 *
 */
public class Project implements PropertyChangeListener {
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
	private ArrayList<AttendanceStatus> attendanceStatus;
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
//		addDummyRooms();
		attendanceStatus = new ArrayList<AttendanceStatus>();
		
		storage = new FileStorage();
	}
	
//	private void addDummyRooms() {
//		String[] roomNames = { "P123", "P298", "G193", "F123" };
//		for (String name : roomNames)
//			addRoom(new Room(name));
//	}
	
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
		return attendanceStatus.iterator();
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
		return attendanceStatus;
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
			if(save){
				String cuPath = new File(".").getAbsolutePath();
				storage.save(new URL("file://"+cuPath+"/project.xml"), this);				
			}
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addRoom(Room room){
		roomList.add(room);
		room.addPropertyChangeListener(this);
		propChangeSupp.firePropertyChange("room", null, room);
	}
	
	public void addNotification(Notification notification){
		notificationList.add(notification);
		notification.addPropertyChangeListener(this);
		propChangeSupp.firePropertyChange("notification", null, notification);
	}
	
	public void addAttendanceStatus(AttendanceStatus status){
		attendanceStatus.add(status);
		status.addPropertyChangeListener(this);
		propChangeSupp.firePropertyChange("status", null, status);
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

	
	public void removeEvent(Event event){
		int i = eventList.indexOf(event);
		eventList.remove(event);
		event.removePropertyChangeListener(this);
		propChangeSupp.firePropertyChange("event",event,i);
		try {
			String cuPath = new File(".").getAbsolutePath();
			storage.save(new URL("file://"+cuPath+"/project.xml"), this);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
