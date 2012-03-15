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
import java.util.Date;
import java.util.Iterator;

import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Person;
import no.ntnu.g44.models.Project;
import no.ntnu.g44.models.Room;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

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

		Iterator it = aProject.personIterator();
		while (it.hasNext()) {
			Person aPerson = (Person)it.next();
			Element element = personToXml(aPerson);
			root.appendChild(element);
		}

		Iterator<Event> eventIt = aProject.eventIterator();
		while (eventIt.hasNext()) {
			Event event = eventIt.next();
			Element element = eventToXml(event);
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

		return aProject;
	}

	public Person toPerson(String xml) throws java.io.IOException, java.text.ParseException, nu.xom.ParsingException {
		nu.xom.Builder parser = new nu.xom.Builder(false);
		nu.xom.Document doc = parser.build(xml, "");
		return assemblePerson(doc.getRootElement());
	}

	private Element personToXml(Person aPerson) {
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
		Element element = new Element("person");
		Element name = new Element("name");
		name.appendChild(aPerson.getName());
		Element email = new Element("email");
		email.appendChild(aPerson.getEmail());
		Element dateOfBirth = new Element("date-of-birth");
		if(aPerson.getDateOfBirth() != null){
			dateOfBirth.appendChild(format.format(aPerson.getDateOfBirth()));
		}
		element.appendChild(name);
		element.appendChild(email);
		element.appendChild(dateOfBirth);
		return element;
	}

	private Person assemblePerson(Element personElement) throws ParseException {
		String name = null, email = null;
		Date date = null;
		Element element = personElement.getFirstChildElement("name");
		if (element != null) {
			name = element.getValue();
		}
		element = personElement.getFirstChildElement("email");
		if (element != null) {
			email = element.getValue();
		}
		element = personElement.getFirstChildElement("date-of-birth");
		if (element != null) {
			date = parseDate(element.getValue());
		}
		return new Person(name, email, date);
	}

	/**
	 * TODO: handle this one to avoid duplicate code
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	private Date parseDate(String date) throws ParseException {
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
		return format.parse(date);
	}

	protected Document toXml(ArrayList<Event> events){

		Element root = new Element("project");

		for(Event event : events){
			Element element = eventToXml(event);
			root.appendChild(element);
		}

		return new Document(root);
	}

	protected Element eventToXml(Event event){

		Element element = new Element("event");

		Element id = new Element("event-id");
		id.appendChild(String.valueOf(event.getEventID()));
		element.appendChild(id);

		Element title = new Element("title");
		title.appendChild(event.getEventTitle());
		element.appendChild(title);

		Element eventOwner = new Element("owner");
		if(event.getEventOwner() != null){
			eventOwner.appendChild(event.getEventOwner().getUsername());
			element.appendChild(eventOwner);
		}
		Element eventStart = new Element("event-start");
		eventStart.appendChild(event.getEventStartTime().toString());
		element.appendChild(eventStart);

		Element eventEnd = new Element("event-end");
		eventEnd.appendChild(event.getEventEndTime().toString());
		element.appendChild(eventEnd);

		Element location = new Element("location");
		location.appendChild(event.getLocation());
		element.appendChild(location);

		if(event.getRoom() != null){
			Element room = new Element("room");
			room.appendChild(event.getRoom().getRoomName());
			element.appendChild(room);
		}
		if(event.getParticipants() != null){
			Element participants = new Element("participants");
			for(Person p : event.getParticipants()){
				Element personElem = new Element("person");
				personElem.appendChild(p.getUsername());
				participants.appendChild(personElem);
			}
			element.appendChild(participants);
		}
		return element;
	}

	public Event toEvent(String xml) throws java.io.IOException, java.text.ParseException, nu.xom.ParsingException {
		nu.xom.Builder parser = new nu.xom.Builder(false);
		nu.xom.Document doc = parser.build(xml, "");
		return assembleEvent(doc.getRootElement());
	}

	private Event assembleEvent(Element eventElement) throws ParseException {
		int id  = -1;
		String title = null, location = null;
		ArrayList<String> participants = new ArrayList<String>();
		Room room = null;
		String owner_username = null;
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
			eventStartDate = parseDate(element.getValue());
		}

		element = eventElement.getFirstChildElement("event-end");
		if (element != null){
			eventEndDate = parseDate(element.getValue());
		}

		element = eventElement.getFirstChildElement("location");
		if (element != null){
			location = element.getValue();
		}

		element = eventElement.getFirstChildElement("room");
		if (element != null){
			room = new Room(element.getValue());
		}

		element = eventElement.getFirstChildElement("participants");
		if( element != null ){
			Elements children = element.getChildElements("participant");
			for ( int i = 0; i< children.size(); i++){
				participants.add(String.valueOf(children.get(i).getFirstChildElement("participant")));				
			}
		}
		
		return new Event(id, title, owner_username, participants, eventStartDate, eventEndDate, location, room);
	}
	
	public String getPersonByUsername(String username){
		
		return null;

	}
}

