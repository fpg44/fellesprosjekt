package no.ntnu.g44.serverAndSession;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.swing.JOptionPane;

import no.ntnu.g44.models.Project;
import no.ntnu.g44.net.co.Connection;
import no.ntnu.g44.net.co.ConnectionImpl;
import no.ntnu.g44.net.co.SimpleConnection;

public class Client {
	Connection connection;
	
	public static void main(String[] args) {
		String server = JOptionPane.showInputDialog("server ip", "localhost");
		new Client(server, 5545);
	}
	public Client(String serverIP, int serverPort){
		connection = new ConnectionImpl(6564);
		try {
			connection.connect(InetAddress.getByName(serverIP), serverPort);
			connection.send("halla!!!");
			new Thread(){
				public void run() {
					recieveLoop();
				};
			}.start();
			new Thread(){
				public void run() {
					inputLoop();
				}

				
			}.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
