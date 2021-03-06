package no.ntnu.g44.serverAndSession;

import java.awt.HeadlessException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.swing.JOptionPane;

import org.xml.sax.InputSource;

import no.ntnu.fp.net.cl.KtnDatagram.Flag;
import no.ntnu.g44.controllers.Main;
import no.ntnu.g44.models.AttendanceStatus;
import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Notification;
import no.ntnu.g44.models.Project;
import no.ntnu.g44.net.co.Connection;
import no.ntnu.g44.net.co.ConnectionImpl;
import no.ntnu.g44.net.co.SimpleConnection;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class Client {
	Connection connection;
	XmlSerializer xmlSerializer = new XmlSerializer();
	volatile boolean recieving = true;
	Thread reciever = new Thread(){

		public void run() {
			try {
				recieveLoop();
			} catch (ParseException | ParsingException e) {
				e.printStackTrace();
			}

		};
	};

	public static void main(String[] args) {
		String server = JOptionPane.showInputDialog("server ip", "78.91.11.32");
		Client c = new Client(server, 5545);
		try {
			//			c.updateEvent();
		} catch ( Exception e){
			e.printStackTrace();
		}
	}
	public Client(String serverIP, int serverPort){
		connection = new ConnectionImpl(6564);
		try {
			connection.connect(InetAddress.getByName(serverIP), serverPort);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void newEvent(Element e) throws ConnectException, IOException{

		connection.send("insert event" + e.toXML());
	}

	public void newNotification(Element e) throws ConnectException, IOException{

		connection.send("insert notification" + e.toXML());
	}

	public void newAttendanceStatus(Element e) throws ConnectException, IOException{

		connection.send("insert attends_at" + e.toXML());
	}

	public void updateEvent(Element e) throws ConnectException, IOException{

		connection.send("update event" + e.toXML());
	}

	public void updateNotification(Element e) throws ConnectException, IOException{

		connection.send("update notification" + e.toXML());
	}

	public void updateAttendanceStatus(Element e) throws ConnectException, IOException{

		connection.send("update attends_at" + e.toXML());
	}

	public void deleteEvent(Element e) throws ConnectException, IOException{

		connection.send("delete event" + e.toXML());
	}

	public void deleteNotification(Element e) throws ConnectException, IOException{

		connection.send("delete notification" + e.toXML());
	}

	public void deleteAttendanceStatus(Element e) throws ConnectException, IOException{

		connection.send("delete attends_at" + e.toXML());
	}
	public void startListenin(){
			reciever.start();
	}
	public Project getProject() throws ConnectException, IOException, ValidityException, ParseException, ParsingException{

		connection.send("get all");

		String xml = connection.receive();

		//Pen kode:
		return new XmlSerializer()
		.toProject(
				new Builder()
				.build(new ByteArrayInputStream(xml.getBytes("utf-8"))));
	}

	public void recieveLoop() throws ParseException, ParsingException{
		while(recieving){
			try {
				parseInput(connection.receive());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void parseInput(String message) throws IOException, ParseException, ParsingException {

		if(message.startsWith("insert notification")){
			message = message.replaceFirst("insert notification", "");
			Notification notif = xmlSerializer.toNotification(message);
			Main.currentProject.addNotification(notif, false);
			Main.currentMainFrame.notificationFuck();
		}
		
		else if(message.startsWith("insert event")){
			message = message.replaceFirst("insert event", "");
			Event e = xmlSerializer.toEvent(message);
			Main.currentProject.addEvent(e, false);
			Main.currentMainFrame.notificationFuck();
		}
		
		else if(message.startsWith("insert attends_at")){
			message = message.replaceFirst("insert attends_at", "");
			AttendanceStatus status = xmlSerializer.toAttendanceStatus(message);
			Main.currentProject.addAttendanceStatus(status, false);
			Main.currentMainFrame.notificationFuck();
		}
		
		else if(message.startsWith("update event")){
			message = message.replaceFirst("update event", "");
			Event e = xmlSerializer.toEvent(message);
			Main.currentProject.removeEvent(Main.currentProject.getEventById(e.getEventID()));
			Main.currentProject.addEvent(e, false);
			Main.currentMainFrame.notificationFuck();
		}
		else if(message.startsWith("update notification")){
			message = message.replaceFirst("update notification", "");
			Notification notification = xmlSerializer.toNotification(message);
			Main.currentProject.removeNotification(Main.currentProject.getNotificationByID(notification.getNotificationID()));
			Main.currentProject.addNotification(notification, false);
			Main.currentMainFrame.notificationFuck();
		}
		else if(message.startsWith("update attends_at")){
			message = message.replaceFirst("update attends_at", "");
			AttendanceStatus status = xmlSerializer.toAttendanceStatus(message);
			Main.currentProject.removeAttendanceStatus(Main.currentProject.getStatus(status.getEventID(), status.getUsername()));
			Main.currentProject.addAttendanceStatus(status, false);
			Main.currentMainFrame.notificationFuck();
		}
//		else if(message.startsWith("delete event")){
//			message = message.replaceFirst("delete event", "");
//			Event e = xmlSerializer.toEvent(message);
//			
//		}
//		else if(message.startsWith("delete notification")){
//			message = message.replaceFirst("delete notification", "");
//			Notification notification = xmlSerializer.toNotification(message);
//			
//		}
//		else if(message.startsWith("delete attends_at")){
//			message = message.replaceFirst("delete attends_at", "");
//			AttendanceStatus status = xmlSerializer.toAttendanceStatus(message);
//			
//		}

	}
	
	
	private void inputLoop() {
		while(true){
			try {
				connection.send(JOptionPane.showInputDialog("msg"));
			} catch (HeadlessException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

}
