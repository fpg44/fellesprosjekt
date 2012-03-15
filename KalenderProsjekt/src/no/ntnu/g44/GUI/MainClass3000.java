package no.ntnu.g44.gui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainClass3000 {
	public static void login(){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame ramme = new JFrame("Login");
		JPanel panel = new JPanel();
		ramme.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel navnLabel = new JLabel("Name:");
		JLabel passLabel = new JLabel("Password:");
		JTextField navnField = new JTextField();
		//navnField.addActionListener(l);
		JTextField passField = new JTextField();
		//passField.addActionListener(l);
		
		navnLabel.setLocation(12, 24);
		navnLabel.setSize((int)passLabel.getPreferredSize().getWidth(), (int) (navnLabel.getPreferredSize().getHeight() + 5));
		navnLabel.setVisible(true);
		panel.add(navnLabel);
		
		navnField.setLocation(12 + navnLabel.getWidth() + 8, 24);
		navnField.setSize(175, navnLabel.getHeight());
		navnField.setVisible(true);
		panel.add(navnField);
		
		passLabel.setLocation(12, 24 + navnLabel.getHeight() + 8);
		passLabel.setSize(navnLabel.getSize());
		passLabel.setVisible(true);
		panel.add(passLabel);
		
		passField.setLocation(12 + navnLabel.getWidth() + 8, 24 + navnLabel.getHeight() + 8);
		passField.setSize(navnField.getSize());
		passField.setVisible(true);
		panel.add(passField);
		
		ramme.setSize(navnField.getWidth() + navnField.getX() + 8 + 25, passLabel.getY() + passLabel.getHeight() + 24 + 35);
		ramme.setLocation((int)(dim.getWidth() - ramme.getWidth())/ 2, (int) ((dim.getHeight() - ramme.getHeight())/2));
		ramme.setResizable(false);
		ramme.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ramme.setVisible(true);
	}
	public static void main(String[] lol){
		login();
	}
}
