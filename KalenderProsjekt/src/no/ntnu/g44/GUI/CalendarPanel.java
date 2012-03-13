package no.ntnu.g44.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class CalendarPanel extends JPanel {
	String[] weekdays = new String[]{"Monday","Tuesday","Wednesday",
			"Thursday","Friday","Saturday","Sunday"};
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		paintGrid(g2d);
	}
	
	private void paintGrid(Graphics2D g2d){
		g2d.setColor(Color.black);
		//Seperate the weekdays
		for(int i = 0; i<= 7; i++){
			int xPos = Math.round(getWidth()*i/7f);
			g2d.drawLine(xPos, 0, xPos, getHeight());
		}
		int yPos = 40;
		int margin = 10;
		//Add day text
		for(int i = 0; i < 7; i++){
			
			int xPos = Math.round(getWidth()*i/7f) + margin;
			
			
		
			
			g2d.drawChars(weekdays[i].toCharArray(), 0, weekdays[i].toCharArray().length, xPos, yPos );
			
		}
		
		g2d.drawLine(0, yPos+margin, getWidth(), yPos+margin);
	}
}
