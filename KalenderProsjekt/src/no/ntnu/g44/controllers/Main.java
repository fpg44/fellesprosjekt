package no.ntnu.g44.controllers;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import no.ntnu.g44.gui.Login;
import 
no.ntnu.g44.gui.MainFrame;
import no.ntnu.g44.models.Project;
import no.ntnu.g44.serverAndSession.FileStorage;

public class Main {
	public static Project currentProject = null;
	public static MainFrame currentMainFrame;
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		//TODO: Create new login here
		//MainFrame should be created in Project constructor
		Login.login();
		
	}
	
	public static void onLogin(String username){
		try {
			currentProject = new FileStorage().load(new File("project.xml"));
		} catch (IllegalArgumentException | IOException | ParseException e) {
			e.printStackTrace();
			System.out.println("No project file, creating blank project");
			currentProject = new Project();
		}
		//currentProject = new Project();
		currentMainFrame = new MainFrame();
	}

}
