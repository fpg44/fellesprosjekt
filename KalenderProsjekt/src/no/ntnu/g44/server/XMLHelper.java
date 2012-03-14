package no.ntnu.g44.server;

import java.util.ArrayList;

import no.ntnu.g44.models.Event;
import nu.xom.Document;
import nu.xom.Element;

public class XMLHelper {

	public XMLHelper(){	
		
	}
	
	protected Document parseEvents(ArrayList<Event> events){
		
		Element element = new Element("something");
		Document document = new Document(element);
		
		return document;
	}
}
