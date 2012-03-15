package no.ntnu.g44.controllers;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import no.ntnu.g44.gui.MainFrame;
import no.ntnu.g44.models.Project;
import no.ntnu.g44.serverAndSession.FileStorage;

public class Main {
	public static Project currentProject = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		//TODO: Create new login here
		//MainFrame should be created in Project constructor
		currentProject = new Project();
		new MainFrame();
	}
	
	public static void onLogin(String username){
		try {
			currentProject = new FileStorage().load(new File("project.xml"));
		} catch (IllegalArgumentException | IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
