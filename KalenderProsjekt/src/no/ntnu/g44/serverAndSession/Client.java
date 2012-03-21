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

import no.ntnu.g44.models.Project;
import no.ntnu.g44.net.co.Connection;
import no.ntnu.g44.net.co.ConnectionImpl;
import no.ntnu.g44.net.co.SimpleConnection;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class Client {
	Connection connection;
	
	public static void main(String[] args) {
		String server = JOptionPane.showInputDialog("server ip", "78.91.11.32");
		Client c = new Client(server, 5545);
		try {
			c.getProject();
		} catch ( IOException
				| ParseException | ParsingException e) {
			// TODO Auto-generated catch block
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
	
	public Project getProject() throws ConnectException, IOException, ValidityException, ParseException, ParsingException{
			
		connection.send("hello");
		String xml = connection.receive();
		
		
		//Pen kode:
		return new XmlSerializer()
				.toProject(
						new Builder()
						.build(new ByteArrayInputStream(xml.getBytes("utf-8"))));
				
	}
	
	public void recieveLoop(){
		while(true){
			try {
				System.out.println(connection.receive());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
