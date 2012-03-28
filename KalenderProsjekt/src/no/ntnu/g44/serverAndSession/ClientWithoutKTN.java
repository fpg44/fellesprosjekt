package no.ntnu.g44.serverAndSession;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.swing.JOptionPane;

import no.ntnu.g44.controllers.Main;
import no.ntnu.g44.models.AttendanceStatus;
import no.ntnu.g44.models.Event;
import no.ntnu.g44.models.Notification;
import no.ntnu.g44.models.Project;
import no.ntnu.g44.net.co.Connection;
import no.ntnu.g44.net.co.ConnectionImpl;
import nu.xom.Builder;
import nu.xom.Element;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class ClientWithoutKTN {
	private Socket connection;
	private XmlSerializer xmlSerializer = new XmlSerializer();
	volatile boolean recieving = true;
	private BufferedReader br;
	private PrintWriter pw;

	Thread reciever = new Thread(){

		public void run() {
			try {
				recieveLoop();
			} catch (ParseException | ParsingException e) {
				e.printStackTrace();
			}

		};
	};

	public ClientWithoutKTN(String serverIP, int serverPort){
		try {

			connection = new Socket(serverIP, serverPort);
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			pw = new PrintWriter(connection.getOutputStream());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void newEvent(Element e) throws ConnectException, IOException{

		pw.write("insert event" + e.toXML());
	}

	public void newNotification(Element e) throws ConnectException, IOException{

		pw.write("insert notification" + e.toXML());
	}

	public void newAttendanceStatus(Element e) throws ConnectException, IOException{

		pw.write("insert attends_at" + e.toXML());
	}

	public void updateEvent(Element e) throws ConnectException, IOException{

		pw.write("update event" + e.toXML());
	}

	public void updateNotification(Element e) throws ConnectException, IOException{

		pw.write("update notification" + e.toXML());
	}

	public void updateAttendanceStatus(Element e) throws ConnectException, IOException{

		pw.write("update attends_at" + e.toXML());
	}

	public void deleteEvent(Element e) throws ConnectException, IOException{

		pw.write("delete event" + e.toXML());
	}

	public void deleteNotification(Element e) throws ConnectException, IOException{

		pw.write("delete notification" + e.toXML());
	}

	public void deleteAttendanceStatus(Element e) throws ConnectException, IOException{

		pw.write("delete attends_at" + e.toXML());
	}
	public void startListenin(){
		reciever.start();
	}
	public Project getProject() throws ConnectException, IOException, ValidityException, ParseException, ParsingException, InterruptedException{

		pw.write("get all");
		System.out.println("C: requested the project from the server");
		
		while(!br.ready()){
			Thread.sleep(1000);
			System.out.println("lol " + connection.getInetAddress());
		}
		String xml = br.readLine();
		System.out.println("C: project received!");
		return new XmlSerializer().toProject(new Builder().build(new ByteArrayInputStream(xml.getBytes("utf-8"))));
	}

	public void recieveLoop() throws ParseException, ParsingException{
		while(recieving){
			try {

				while(br.ready()){}

				parseInput(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void parseInput(String message) throws IOException, ParseException, ParsingException {

		if(message.startsWith("<project>")){

		}
		else if(message.startsWith("insert notification")){
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
				pw.write(JOptionPane.showInputDialog("msg"));
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
}
