package no.ntnu.g44.serverAndSession;

import java.util.ArrayList;

import no.ntnu.g44.models.Event;
import nu.xom.Document;
import nu.xom.Element;

public class XMLHelper {

	public XMLHelper(){	
		
	}
	
	protected Document parseEvents(ArrayList<Event> events){
		
		Element root = new Element("project");
		
		for(Event event : events){
			Element element = eventToXml(event);
			root.appendChild(element);
		}
		
		return new Document(root);
	}
}
