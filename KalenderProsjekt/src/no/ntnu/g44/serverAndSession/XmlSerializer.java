/*
 * Created on Oct 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package no.ntnu.g44.serverAndSession;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import no.ntnu.g44.models.AttendanceStatus;
import no.ntnu.g44.models.AttendanceStatusType;
import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Notification;
import no.ntnu.g44.models.NotificationType;
import no.ntnu.g44.models.Person;
import no.ntnu.g44.models.Project;
import no.ntnu.g44.models.Room;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

/**
 * @author tho
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XmlSerializer {

	public XmlSerializer(){

	}

	public Document toXml(Project aProject) {
		Element root = new Element("project");

		Iterator<Person> personIt = aProject.personIterator();
		while (personIt.hasNext()) {
			Person aPerson = personIt.next();
			Element element = personToXml(aPerson);
			root.appendChild(element);
		}

		Iterator<Event> eventIt = aProject.eventIterator();
		while (eventIt.hasNext()) {
			Event event = eventIt.next();
			Element element = eventToXml(event);
			root.appendChild(element);
		}

		Iterator<Notification> notificationIt = aProject.notificationIterator();
		while(notificationIt.hasNext()){
			Notification notification = notificationIt.next();
			Element element = notificationToXml(notification);
			root.appendChild(element);
		}

		Iterator<Room> roomIt = aProject.roomIterator();
		while(roomIt.hasNext()){
			Room room = roomIt.next();
			Element element = roomToXml(room);
			root.appendChild(element);
		}

		Iterator<AttendanceStatus> attendanceStatusIt = aProject.attendanseStaturIterator();
		while(attendanceStatusIt.hasNext()){
			AttendanceStatus attendanceStatus = attendanceStatusIt.next();
			Element element = attendanceStatusToXml(attendanceStatus);
			root.appendChild(element);
		}

		return new Document(root);
	}

	public Project toProject(Document xmlDocument) throws ParseException {
		Project aProject = new Project();
		
		Element groupElement = xmlDocument.getRootElement();
		
		Elements personElements = groupElement.getChildElements("person");
		for (int i = 0; i < personElements.size(); i++) {
			Element childElement = personElements.get(i);
			aProject.addPerson(assemblePerson(childElement));
		}
		
		Elements eventElements = groupElement.getChildElements("event");
		for (int i = 0; i < eventElements.size(); i++){
			Element child = eventElements.get(i);
			aProject.addEvent(assembleEvent(child), false);
		}
		
		Elements notificationElements = groupElement.getChildElements("notification");
		for (int i = 0; i < notificationElements.size(); i++){
			Element child = notificationElements.get(i);
			aProject.addNotification(assembleNotification(child));
		}
		
		Elements roomElements = groupElement.getChildElements("room");
		for (int i = 0; i < roomElements.size(); i++){
			Element child = roomElements.get(i);
			aProject.addRoom(assembleRoom(child));
		}
		
		Elements attendanceStatusElements = groupElement.getChildElements("attendance-status");
		for (int i = 0; i < attendanceStatusElements.size(); i++){
			Element child = attendanceStatusElements.get(i);
			aProject.addAttendanceStatus(assembleAttendanceStatus(child));
		}
		return aProject;
	}
	
	/**
	 * TODO: handle this one to avoid duplicate code
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	private Date parseToDate(String date) throws ParseException {
//		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
//		return format.parse(date);
		String temp = "";
		int day = -1, month = -1, year = -1, hour = -1, min = -1;
		Calendar parsedDate = new GregorianCalendar();
		Date dato = new Date();
		
		for(int i = 0; i<date.length(); i++){
			temp += date.charAt(i);
			
			if(date.charAt(i) == '?'){
				temp = temp.substring(0, temp.length()-1);
			}
			else if(date.charAt(i) == ':'){
				temp = temp.substring(0, temp.length()-1);
				day = Integer.parseInt(temp);
				temp = "";
			}
			else if(date.charAt(i) == ';'){
				temp = temp.substring(0, temp.length()-1);
				month = Integer.parseInt(temp);
				temp = "";
			}
			else if(date.charAt(i) == '-'){
				temp = temp.substring(0, temp.length()-1);
				year = Integer.parseInt(temp);
				temp = "";
			}
			else if(date.charAt(i) == '_'){
				temp = temp.substring(0, temp.length()-1);
				min = Integer.parseInt(temp);
				temp = "";
			}
			else if(date.charAt(i) == '!'){
				temp = temp.substring(0, temp.length()-1);
				hour = Integer.parseInt(temp);
				temp = "";
				parsedDate.set(year, month, day, hour, min);
				dato = parsedDate.getTime();
			}
		}
		return dato;
	}
	
	private String parseToString(Date date){
		String time = "?";
		
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		int pm = 0;
//		System.out.println("pm" + c.get(c.PM) + "am" + c.get(c.AM) + "ampm" + c.get(c.AM_PM));
		if(c.get(c.AM) == c.get(c.AM_PM)){
			pm = 12;
		}
		time += c.get(c.DAY_OF_MONTH) + ":" + c.get(c.MONTH) + ";" + c.get(c.YEAR) + "-" + c.get(c.MINUTE) + "_" + (c.get(c.HOUR)+pm) + "!";
		
		return time;
	}

	public Person toPerson(String xml) throws java.io.IOException, java.text.ParseException, nu.xom.ParsingException {
		nu.xom.Builder parser = new nu.xom.Builder(false);
		nu.xom.Document doc = parser.build(xml, "");
		return assemblePerson(doc.getRootElement());
	}
	
	public Event toEvent(String xml) throws java.io.IOException, java.text.ParseException, nu.xom.ParsingException {
		nu.xom.Builder parser = new nu.xom.Builder(false);
		nu.xom.Document doc = parser.build(xml, "");
		return assembleEvent(doc.getRootElement());
	}
	
	public Notification toNotification(String xml) throws ValidityException, ParsingException, IOException{
		nu.xom.Builder parser = new nu.xom.Builder(false);
		nu.xom.Document doc = parser.build(xml, "");
		return assembleNotification(doc.getRootElement());
	}

	public Room toRoom(String xml) throws ValidityException, ParsingException, IOException{
		nu.xom.Builder parser = new nu.xom.Builder(false);
		nu.xom.Document doc = parser.build(xml, "");
		return assembleRoom(doc.getRootElement());
	}
	
	public AttendanceStatus toAttendanceStatus(String xml) throws ValidityException, ParsingException, IOException{
		nu.xom.Builder parser = new nu.xom.Builder(false);
		nu.xom.Document doc = parser.build(xml, "");
		return assembleAttendanceStatus(doc.getRootElement());
	}
	
	
//////////////////////////////
	
	private Element personToXml(Person aPerson) {
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
		Element element = new Element("person");
		Element name = new Element("name");
		name.appendChild(aPerson.getName());
		Element username = new Element("username");
		username.appendChild(aPerson.getUsername());
		Element email = new Element("email");
		email.appendChild(aPerson.getEmail());
		Element dateOfBirth = new Element("date-of-birth");
		if(aPerson.getDateOfBirth() != null){
			dateOfBirth.appendChild(format.format(aPerson.getDateOfBirth()));
		}
		element.appendChild(name);
		element.appendChild(username);
		element.appendChild(email);
		element.appendChild(dateOfBirth);
		return element;
	}

	protected Element eventToXml(Event event){		
		Element element = new Element("event");

		Element id = new Element("event-id");
		id.appendChild(String.valueOf(event.getEventID()));
		element.appendChild(id);

		Element title = new Element("title");
		title.appendChild(event.getEventDescription());
		element.appendChild(title);

		Element eventOwner = new Element("owner");
		if(event.getEventOwner() != null){
			eventOwner.appendChild(event.getEventOwner().getUsername());
			element.appendChild(eventOwner);
		}
		Element eventStart = new Element("event-start");
		eventStart.appendChild((parseToString(event.getEventStartTime())));
		element.appendChild(eventStart);

		Element eventEnd = new Element("event-end");
		eventEnd.appendChild((parseToString(event.getEventEndTime())));
		element.appendChild(eventEnd);

		Element location = new Element("location");
		location.appendChild(event.getLocation());
		element.appendChild(location);

		if(event.getRoom() != null){
			Element room = new Element("room");
			room.appendChild(event.getRoom().getRoomName());
			element.appendChild(room);
		}
		if(event.getParticipantsStrings() != null){
			Element participants = new Element("participants");
			for(String p : event.getParticipantsStrings()){
				Element personElem = new Element("person");
				personElem.appendChild(p);
				participants.appendChild(personElem);
			}
			element.appendChild(participants);
		}
		return element;
	}

	public Element notificationToXml(Notification notification){

		Element element = new Element("notification");
		
		Element notificatioID = new Element("notification-id");
		notificatioID.appendChild(String.valueOf(notification.getNotificationID()));
		element.appendChild(notificatioID);

		Element eventID = new Element("event-id");
		eventID.appendChild(String.valueOf(notification.getEventID()));
		element.appendChild(eventID);

		Element type = new Element("type");
		type.appendChild(notification.getType().toString());
		element.appendChild(type);

		return element;
	}

	public Element roomToXml(Room room){
		Element element = new Element("room");

		Element name = new Element("name");
		name.appendChild(room.getRoomName());
		element.appendChild(name);

		return element;
	}

	public Element attendanceStatusToXml(AttendanceStatus status){
		Element element = new Element("Attendance-status");

		Element username = new Element("username");
		username.appendChild(status.getUsername());
		element.appendChild(username);

		Element eventID = new Element("event-id");
		eventID.appendChild(String.valueOf(status.getEventID()));
		element.appendChild(eventID);

		Element type = new Element("type");
		type.appendChild(status.getStatus().toString());

		return element;

	}
	
	public AttendanceStatus assembleAttendanceStatus(Element e){
		String username = null;
		int id = -1;
		AttendanceStatusType type = null;

		Element element = e.getFirstChildElement("username");
		if(element != null){
			username = element.getValue();
		}

		element = e.getFirstChildElement("event-id");
		if(element != null){
			id = Integer.parseInt(element.getValue());
		}

		element = e.getFirstChildElement("type");
		if(element != null){
			type = AttendanceStatusType.getType(element.getValue());
		}

		return new AttendanceStatus(username, id, type);
	}
	
	public Room assembleRoom(Element e){
		String name = null;

		Element element = e.getFirstChildElement("name");
		if(element != null){
			if(element.getValue().equals("OTHER")){
				return Room.OTHER;
			}
			else{
				return new Room(element.getValue());
			}
		}

		return null;
	}
	
	public Notification assembleNotification(Element e){
		int eventID = -1, notificationID = -1;
		NotificationType type = null;

		Element element = e.getFirstChildElement("notification-id");
		if(element != null){
			notificationID = Integer.parseInt(element.getValue());
		}

		element = e.getFirstChildElement("event-id");
		if(element != null){
			eventID = Integer.parseInt(element.getValue());
		}

		element = e.getFirstChildElement("type");
		if(element != null){
			type = NotificationType.valueOf(element.getValue());
		}

		return new Notification(notificationID, eventID, type);
	}
	
	private Event assembleEvent(Element eventElement) throws ParseException {
		int id  = -1;
		String title = "", location = "";
		ArrayList<String> participants = new ArrayList<String>();
		//		Room room = null;
		String roomString = "";
		String owner_username = "";
		Date eventStartDate = null, eventEndDate = null;

		Element element = eventElement.getFirstChildElement("event-id");
		if (element != null) {
			id = Integer.parseInt(element.getValue());
		}

		element = eventElement.getFirstChildElement("title");
		if (element != null) {
			title = element.getValue();
		}

		element = eventElement.getFirstChildElement("owner");
		if (element != null) {
			owner_username = element.getValue();
		}

		element = eventElement.getFirstChildElement("event-start");
		if (element != null){
			eventStartDate = parseToDate(element.getValue());
		}

		element = eventElement.getFirstChildElement("event-end");
		if (element != null){
			eventEndDate = parseToDate(element.getValue());
		}

		element = eventElement.getFirstChildElement("location");
		if (element != null){
			location = element.getValue();
		}

		element = eventElement.getFirstChildElement("room");
		if (element != null){
			roomString = element.getValue();
		}

		element = eventElement.getFirstChildElement("participants");
		if( element != null ){
			Elements children = element.getChildElements("person");
			for ( int i = 0; i< children.size(); i++){
				participants.add(children.get(i).getValue());				
			}
		}
		return new Event(id, title, owner_username, participants, eventStartDate, eventEndDate, location, roomString);
	}
	
	private Person assemblePerson(Element personElement) throws ParseException {
		String name = null, email = null, username = null;
		Date date = null;
		Element element = personElement.getFirstChildElement("name");
		if (element != null) {
			name = element.getValue();
		}
		element = personElement.getFirstChildElement("username");
		if(element!=null){
			username = element.getValue();
		}
//		element = personElement.getFirstChildElement("email");
//		if (element != null) {
//			email = element.getValue();
//		}
//		element = personElement.getFirstChildElement("date-of-birth");
//		if (element != null && !element.getValue().equals("")) {
//			date = parseDate(element.getValue());
//		}
		return new Person(name, username);
	}
}

