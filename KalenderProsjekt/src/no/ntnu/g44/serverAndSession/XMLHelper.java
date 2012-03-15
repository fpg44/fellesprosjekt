package no.ntnu.g44.serverAndSession;

import java.util.ArrayList;

import no.ntnu.g44.models.Event;
import nu.xom.Document;
import nu.xom.Element;

public class XMLHelper {

	private XmlSerializer xml;
	
	public XMLHelper(){	
		
		xml = new XmlSerializer();
		
	}
	
	protected Document parseEvents(ArrayList<Event> events){
		
		Element root = new Element("project");
		
		for(Event event : events){
			Element element = xml.eventToXml(event);
			root.appendChild(element);
		}
		
		return new Document(root);
	}
}
