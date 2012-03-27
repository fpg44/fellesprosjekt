package no.ntnu.g44.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import no.ntnu.g44.controllers.Main;
import no.ntnu.g44.models.Person;

public class Login {
	
	static JFrame ramme;
	public static void login(){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		ramme = new JFrame("Login");
		JPanel panel = new JPanel();
		JButton loginButt = new JButton("Login");
		ramme.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel navnLabel = new JLabel("Name:");
		JLabel passLabel = new JLabel("Password:");
		final JTextField navnField = new JTextField();
		//navnField.addActionListener(l);
		JPasswordField passField = new JPasswordField();
		//passField.addActionListener(l);
		
		navnLabel.setLocation(12, 24);
		navnLabel.setSize((int)passLabel.getPreferredSize().getWidth(), (int) (navnLabel.getPreferredSize().getHeight() + 5));
		navnLabel.setVisible(true);
		panel.add(navnLabel);
		
		navnField.setLocation(12 + navnLabel.getWidth() + 8, 24);
		navnField.setSize(175, navnLabel.getHeight());
		navnField.setVisible(true);
		navnField.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					for(int i = 0; i < Main.currentProject.getPersonCount(); i++){
						if(navnField.getText().equals(Main.currentProject.getPerson(i).getUsername())){
							Main.onLogin(Main.currentProject.getPerson(i));
							ramme.dispose();
							return;
						}
					}
					JOptionPane.showMessageDialog(null, "Wrong username or password");
					}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		panel.add(navnField);
		
		passLabel.setLocation(12, 24 + navnLabel.getHeight() + 8);
		passLabel.setSize(navnLabel.getSize());
		passLabel.setVisible(true);
		panel.add(passLabel);
		
		passField.setLocation(12 + navnLabel.getWidth() + 8, 24 + navnLabel.getHeight() + 8);
		passField.setSize(navnField.getSize());
		passField.setVisible(true);
		panel.add(passField);
		
		loginButt.setLocation(12 + navnLabel.getWidth() + 8, 42 + navnLabel.getHeight()+16);
		loginButt.setSize(navnField.getSize());
		loginButt.setVisible(true);
		loginButt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < Main.currentProject.getPersonCount(); i++){
					if(navnField.getText().equals(Main.currentProject.getPerson(i).getUsername())){
						Main.onLogin(Main.currentProject.getPerson(i));
						ramme.dispose();
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "Wrong username or password");

			}
		});
		panel.add(loginButt);
		
		
		ramme.setSize(navnField.getWidth() + navnField.getX() + 8 + 25, passLabel.getY() + passLabel.getHeight() + 24 + 39);
		ramme.setLocation((int)(dim.getWidth() - ramme.getWidth())/ 2, (int) ((dim.getHeight() - ramme.getHeight())/2));
		ramme.setResizable(false);
		ramme.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ramme.setVisible(true);
	}

}
