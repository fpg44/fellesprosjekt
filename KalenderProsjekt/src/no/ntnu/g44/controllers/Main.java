package no.ntnu.g44.controllers;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import no.ntnu.g44.gui.Login;
import no.ntnu.g44.gui.MainFrame;
import no.ntnu.g44.models.Person;
import no.ntnu.g44.models.Project;
import no.ntnu.g44.serverAndSession.Client;
import no.ntnu.g44.serverAndSession.FileStorage;
import nu.xom.ParsingException;

public class Main {
	public static Project currentProject = null;
	public static MainFrame currentMainFrame;

	static final boolean usenet = false;
	/**
	 * @param args
	 */
	public static void main(String[] args) {


		if(usenet){
			try {
				currentProject = new Client("78.91.12.130", 5545).getProject();
			} catch (IOException | ParseException | ParsingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//TODO: Create new login here
		//MainFrame should be created in Project constructor
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
		Login.login();

	}

	public static void onLogin(Person user){
		currentProject.setLoggedInPerson(user);
		//currentProject = new Project();
		currentMainFrame = new MainFrame();
	}

}
