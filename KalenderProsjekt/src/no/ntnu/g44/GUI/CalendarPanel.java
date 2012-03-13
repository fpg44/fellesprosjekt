package no.ntnu.g44.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class CalendarPanel extends JPanel {
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
	}
	
	private void painGrid(Graphics2D g2d){
		for(int i = 0; i<= 7; i++){
			int xPos = Math.round(getWidth()*i/7f);
			g2d.drawLine(xPos, 0, xPos, getHeight());
		}
	}
}
