package no.ntnu.g44.components;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JList;
import java.awt.GridBagConstraints;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


import java.awt.Component;

public class TestListRenderer extends JPanel implements MouseListener{

	JLabel label;
	DefaultListModel model = new DefaultListModel<>();
	JList list = new JList(model);
	String name;
	
	public TestListRenderer(String name) {
		this.name = name;
		setBackground(Color.LIGHT_GRAY);
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblName = new JLabel(name);
		add(lblName, BorderLayout.WEST);
		
		label = new JLabel("");
		label.addMouseListener(this);
		label.setIcon(new ImageIcon("D:\\Dropbox\\Workspace\\fellesprosjekt\\KalenderProsjekt\\src\\no\\ntnu\\g44\\components\\x.png"));
		add(label, BorderLayout.EAST);
	}
	public void setIcon(ImageIcon i){
		label.setIcon(i);
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		label.setIcon(new ImageIcon("D:\\Dropbox\\Workspace\\fellesprosjekt\\KalenderProsjekt\\src\\no\\ntnu\\g44\\components\\x2.png"));
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		label.setIcon(new ImageIcon("D:\\Dropbox\\Workspace\\fellesprosjekt\\KalenderProsjekt\\src\\no\\ntnu\\g44\\components\\x.png"));
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	public String getTheName(){
		return this.name;
	}
	public JLabel getLabel(){
		return this.label;
	}
}
