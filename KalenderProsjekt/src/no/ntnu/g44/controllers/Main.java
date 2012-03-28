package no.ntnu.g44.controllers;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import no.ntnu.g44.gui.Login;
import no.ntnu.g44.gui.MainFrame;
import no.ntnu.g44.models.Person;
import no.ntnu.g44.models.Project;
import no.ntnu.g44.serverAndSession.Client;
import no.ntnu.g44.serverAndSession.ClientWithoutKTN;
import no.ntnu.g44.serverAndSession.FileStorage;
import nu.xom.ParsingException;

public class Main {
	public static Project currentProject = null;
	public static MainFrame currentMainFrame;
	public static Client client;
//	public static ClientWithoutKTN client;

	//Set to 'true' when you want to use A1 and A2 from the KTN-part of the assigment.
	//'False' indicates that you want to use xml instead of database.
	public static final boolean usenet = false;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if(usenet){
			try {
				client = new Client("78.91.8.199", 5545);
//				client = new ClientWithoutKTN("78.91.8.199", 5545);
				System.out.println("C: connected");
				currentProject = client.getProject();
				System.out.println("C: project received");
				client.startListenin();//Start listening for server pushes after we've gotten the project.
				System.out.println("C: is listening to the server calls");
				
			} catch (IOException | ParseException | ParsingException e) {
				e.printStackTrace();
				System.exit(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//TODO: Create new login here
		//MainFrame should be created in Project constructor
		if(!usenet){
			try {
				currentProject = new FileStorage().load(new File("project.xml"));
				if(!usenet){
					currentProject = new FileStorage().load(new File("project.xml"));
				}
				
			} catch (IllegalArgumentException | IOException | ParseException e) {
				e.printStackTrace();
				System.out.println("No project file, creating blank project");
				currentProject = new Project();
			}			
		}
		Login.login();

	}

	public static void onLogin(Person user){
		currentProject.setLoggedInPerson(user);
		//currentProject = new Project();
		currentMainFrame = new MainFrame();
	}

}
