package no.ntnu.g44.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

public class CalendarPanel extends JPanel implements MouseWheelListener {
	String[] weekdays = new String[]{"Monday","Tuesday","Wednesday",
			"Thursday","Friday","Saturday","Sunday"};
	private float startTime = 8f;
	private float hoursShown = 8f;
	int textPos = 40;
	int margin = 10;
	int topArea = margin+textPos;
	
	
	public CalendarPanel() {
		addMouseWheelListener(this);
	}
	
	/**Offset to make room for displaying times	 */
	private int leftOffset = 50;
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2));
		paintGrid(g2d);
		paintHours(g2d);
		paintFrame(g2d);
	}
	
	private void paintFrame(Graphics2D g2d) {
		g2d.drawRect(0, 0, getWidth(), getHeight());
	}

	private void paintHours(Graphics2D g2d) {
		int height = getHeight() - topArea;
		//Split all the full hours and show time
		
		int hourGap = (int) (height/hoursShown);
		int partialHourOffset = (int) ((startTime - Math.floor(startTime))*hourGap);
		
		for (int i = 0; i < hoursShown; i++) {
			int ypos =  i*hourGap + topArea+partialHourOffset;
			g2d.drawLine(0, ypos, getWidth(), ypos);
			int hour = (int)(startTime+i);
			g2d.drawString((hour<10?"0":"")+hour+":00",
					10, ypos+15);
		}
		
		//Split all half-hours
		
		//Set thinner line, but save last stroke type
		Stroke s = g2d.getStroke();
		g2d.setStroke(
				new BasicStroke(1, 
						BasicStroke.CAP_BUTT, 
						BasicStroke.JOIN_BEVEL, 
						1, new float[]{9}, 0));
		
		for (int i = 0; i < hoursShown; i++) {
			
			int ypos = (int) (i*hourGap + partialHourOffset+
								topArea+0.5*hourGap);
			
			g2d.drawLine(leftOffset, ypos, getWidth(), ypos);
			
		}
		
		//Set back to old stroke.
		g2d.setStroke(s);
		
	}

	private void paintGrid(Graphics2D g2d){
		int width = getWidth() - leftOffset;
		
		g2d.setColor(Color.black);
		//Seperate the weekdays
		for(int i = 0; i<= 7; i++){
			int xPos = Math.round(width*i/7f)+leftOffset;
			g2d.drawLine(xPos, 0, xPos, getHeight());
		}
		
		
		//Add day text
		Font prefont = g2d.getFont();
		Font f = new Font("Helvetica",Font.BOLD, 16);
		g2d.setFont(f);
		for(int i = 0; i < 7; i++){
			int xPos = Math.round(width*i/7f) + margin+leftOffset;	
			g2d.drawChars(weekdays[i].toCharArray(), 0, weekdays[i].toCharArray().length, xPos, textPos );
			
		}
		g2d.drawLine(0, topArea, getWidth(), topArea);
		g2d.setFont(prefont); //reset font
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		startTime += 0.5f*e.getPreciseWheelRotation();
		startTime = Math.max(0, Math.min(startTime,24-hoursShown));
		System.out.println("startTime = "+startTime);
		repaint();		
	}
	
	
}
