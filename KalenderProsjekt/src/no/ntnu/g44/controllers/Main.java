package no.ntnu.g44.controllers;

import no.ntnu.g44.gui.MainFrame;
import no.ntnu.g44.models.Project;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		//TODO: Create new login here
		//MainFrame should be created in Project constructor
		new MainFrame();
	}
	
	public static void onLogin(String username){
		new Project();
	}

}
