package no.ntnu.g44.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

import no.ntnu.g44.models.Event;

public class CalendarPanel extends JPanel implements MouseWheelListener {
	String[] weekdays = new String[]{"Monday","Tuesday","Wednesday",
			"Thursday","Friday","Saturday","Sunday"};
	private float startHour = 8f;
	private float hoursShown = 8f;
	int textPos = 40;
	int margin = 10;
	int topArea = margin+textPos;
	int pixlsPerHour = (int) ((getHeight() -topArea)/hoursShown);
	Event[] events = new Event[3];

	public CalendarPanel() {
		addMouseWheelListener(this);
		events = new Event[]{
				new Event("test",null, new Date(2012,3,14,10,15),
						new Date(2012,3,14,12,6), null,null)};
	}

	/**Offset to make room for displaying times	 */
	private int leftOffset = 50;
	private int dayWidth;
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2));
		paintGrid(g2d);
		paintHours(g2d);
		paintFrame(g2d);
		paintEvents(g2d);
	}

	private void paintFrame(Graphics2D g2d) {
		g2d.drawRect(0, 0, getWidth(), getHeight());
	}

	private void paintHours(Graphics2D g2d) {
		int height = getHeight() - topArea;
		//Split all the full hours and show time
		g2d.setStroke(new BasicStroke(1));
		pixlsPerHour = (int) (height/hoursShown);
		int partialHourOffset = (int) ((startHour - Math.floor(startHour))*pixlsPerHour);
		for (int i = 0; i < hoursShown; i++) {
			int ypos =  i*pixlsPerHour + topArea+partialHourOffset;
			g2d.drawLine(0, ypos, getWidth(), ypos);
			int hour = (int)(Math.ceil(startHour)+i);
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

			int ypos = (int) (i*pixlsPerHour + partialHourOffset+
					topArea+0.5*pixlsPerHour);

			g2d.drawLine(leftOffset, ypos, getWidth(), ypos);

		}

		//Set back to old stroke.
		g2d.setStroke(s);

	}



	private void paintGrid(Graphics2D g2d){
		int width = getWidth() - leftOffset;
		dayWidth = Math.round(width/7f);
		g2d.setColor(Color.black);
		//Seperate the weekdays
		for(int i = 0; i<= 7; i++){
			int xPos = i*dayWidth+leftOffset;
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


	@SuppressWarnings("deprecation")
	private void paintEvents(Graphics2D g2d){
		g2d.setStroke(new BasicStroke(2));
		int eventMargin = 15;
		int roundCorners = 30;
		for(Event e:events){
			Date startTime = e.getEventStartTime();
			int startY = (int) (((startTime.getHours()-this.startHour)+
							startTime.getMinutes()/60) * pixlsPerHour) + topArea;
			Date endTime = e.getEventEndTime();
			int endY = (int) (((endTime.getHours()-this.startHour)+
					endTime.getMinutes()/60) * pixlsPerHour) + topArea;
			
			int startX = startTime.getDay() *dayWidth + leftOffset + eventMargin;
			int endX = startTime.getDay() *dayWidth +dayWidth+ leftOffset - eventMargin;
			
			g2d.drawRoundRect(startX,startY,endX - startX , endY - startY,roundCorners,roundCorners);
			g2d.setPaint(Color.white);
			g2d.fill(new RoundRectangle2D.Double(
					startX,startY,endX - startX , endY - startY,roundCorners,roundCorners));
		
			//Draw the text
			g2d.setPaint(Color.black);
			Font prefont = g2d.getFont();
			Font f = new Font("Helvetica",Font.BOLD, 12);
			g2d.setFont(f);
			g2d.drawString(e.getEventTitle(), startX+15, startY+20);
			g2d.setFont(prefont); //reset font
			g2d.drawLine(startX, startY+30, endX, startY+30);
		
		}
	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		startHour += 0.5f*e.getPreciseWheelRotation();
		startHour = Math.max(0, Math.min(startHour,24-hoursShown));
		System.out.println("startTime = "+startHour);
		repaint();		
	}


}
