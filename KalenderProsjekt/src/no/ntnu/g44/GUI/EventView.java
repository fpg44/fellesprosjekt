package no.ntnu.g44.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Calendar;

import no.ntnu.g44.models.Event;

public class EventView {

	private Event event;
	private int eventMargin = 15;
	private int roundCorners = 30;
	private float startHour;
	private int dayWidth;
	private int pixlsPerHour;
	private float offsetY;
	private int offsetX;

	public EventView(Event e) {
		this.event = e;
	}
	
	public void set( float startHour, int hourHeight, int dayWidth, int offsetX, int offsetY){
		this.startHour = startHour;
		this.dayWidth = dayWidth;
		this.pixlsPerHour = hourHeight;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public void paint(Graphics2D g2d, boolean selected){


		Calendar startTime = Calendar.getInstance();
		startTime.setTime(event.getEventStartTime());

		Calendar endTime = Calendar.getInstance();
		endTime.setTime(event.getEventEndTime());


		int startY = (int) ((((startTime.get(Calendar.HOUR_OF_DAY)-startHour)+
				startTime.get(Calendar.MINUTE)/60f) * pixlsPerHour) + offsetY);

		int endY = (int) ((((endTime.get(Calendar.HOUR_OF_DAY)-startHour)+
				endTime.get(Calendar.MINUTE)/60f) * pixlsPerHour) + offsetY);

		int startX = (startTime.get(Calendar.DAY_OF_WEEK)  +8 ) %7 *dayWidth + offsetX + eventMargin;
		int endX = (startTime.get(Calendar.DAY_OF_WEEK) + 8)%7 *dayWidth +dayWidth+ offsetX - eventMargin;

		g2d.drawRoundRect(startX,startY,endX - startX , endY - startY,roundCorners,roundCorners);
		g2d.setPaint(selected ? Color.red : Color.white);
		g2d.fill(new RoundRectangle2D.Double(
				startX,startY,endX - startX , endY - startY,roundCorners,roundCorners));

		//Draw the text
		g2d.setPaint(Color.black);
		Font prefont = g2d.getFont();
		Font f = new Font("Helvetica",Font.BOLD, 12);
		g2d.setFont(f);
		g2d.drawString(event.getEventTitle(), startX+15, startY+20);

		g2d.drawLine(startX, startY+30, endX, startY+30);

		f = new Font("Helvetica",Font.PLAIN, 12);
		g2d.setFont(f);

		g2d.drawString("Location: "+ event.getLocation(), startX+10, startY+50);

		g2d.setFont(prefont); //reset font
	}
	
	public boolean isAtPosition(int x, int y){
		int x0 = getStartX();
		int x1 = getEndX();
		if(x < x0 || x > x1) return false;
		
		int y0 = getStartY();
		int y1 = getEndY();
		if(y<y0 || y>y1) return false;
				
		return true;


	}
	
	private int  getStartY(){
		Calendar startTime = getStartTime();		
		int startY = (int) ((((startTime.get(Calendar.HOUR_OF_DAY)-startHour)+
				startTime.get(Calendar.MINUTE)/60f) * pixlsPerHour) + offsetY);
		return startY;
	}

	private int  getEndY(){
		Calendar endTime = getEndTime();
		
		int endY = (int) ((((endTime.get(Calendar.HOUR_OF_DAY)-startHour)+
				endTime.get(Calendar.MINUTE)/60f) * pixlsPerHour) + offsetY);
		return endY;
	}
	
	private int  getStartX(){
		Calendar startTime = getStartTime();		
		int startX = (startTime.get(Calendar.DAY_OF_WEEK)  +8 ) %7 *dayWidth + 
				offsetX + eventMargin;

		return startX;
	}
	
	private int  getEndX(){
		Calendar startTime = getStartTime();
		
		int endX = (startTime.get(Calendar.DAY_OF_WEEK) + 8)%7 *dayWidth +dayWidth+ offsetX - eventMargin;

		return endX;
	}
	
	
	private Calendar getStartTime(){
		Calendar startTime = Calendar.getInstance();
		startTime.setTime(event.getEventStartTime());
		return startTime;
	}
	
	private Calendar getEndTime(){
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(event.getEventEndTime());
		return endTime;
	}
}
