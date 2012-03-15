package no.ntnu.g44.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.JPanel;

import sun.util.resources.TimeZoneNames_en;

import no.ntnu.g44.models.Event;

public class CalendarPanel extends JPanel implements MouseWheelListener, MouseListener {
	String[] weekdays = new String[]{"Monday","Tuesday","Wednesday",
			"Thursday","Friday","Saturday","Sunday"};
	private float startHour = 8f;
	private float hoursShown = 8f;
	int textPos = 40;
	int margin = 10;
	int topArea = margin+textPos;
	int pixlsPerHour = (int) ((getHeight() -topArea)/hoursShown);
	Event[] testEvents = new Event[3];
	
	private Event selectedEvent = null;

	public CalendarPanel() {
		addMouseWheelListener(this);
		addMouseListener(this);
		testEvents = new Event[]{
				new Event("Ting 15.",null, new Date(2012,3,15,11,15),
					new Date(2012,3,15,13,6), null,null),
					
				new Event("haha 16",null, new Date(2012,3,16,10,15),
					new Date(2012,3,16,45,6), null,null),
					
				new Event("hoho 14",null, new Date(2012,3,14,10,15),
					new Date(2012,3,14,12,6), null,null),
					
				new Event("test",null, new Date(2012,3,17,10,15),
					new Date(2012,3,17,12,6), null,null),
					
				new Event("test",null, new Date(2012,3,18,10,15),
					new Date(2012,3,18,12,6), null,null),
					
				new Event("test",null, new Date(2012,3,19,10,15),
					new Date(2012,3,19,12,6), null,null),
					
				new Event("test",null, new Date(2012,3,20,10,15),
					new Date(2012,3,20,12,6), null,null),
		};
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
		paintNowLine(g2d);
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
		
		for(Event e:testEvents){
			EventView ev = new EventView(e);
			ev.set(startHour,pixlsPerHour,dayWidth, leftOffset,topArea);
			ev.paint(g2d, selectedEvent == e);

		}
	}
	
	/**
	 * @return the selected event. Or null if none is selected.
	 */
	public Event getSelectedEvent(){
		return selectedEvent;
	}
	
	
	private void paintNowLine(Graphics2D g2d){
//		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
//		TimeZone timezone = registry.getTimeZone("America/Mexico_City");
//		VTimeZone tz = timezone.getVTimeZone();
		
//		TimeZone tz=TimeZone.getTimeZone("G");
		Calendar nowTime = Calendar.getInstance();
//		nowTime.setTimeZone(tz);
		nowTime.setTime(new Date());
		System.out.println(nowTime.getTime());
		int Y = (int) ((((nowTime.get(Calendar.HOUR_OF_DAY)-this.startHour)+
				nowTime.get(Calendar.MINUTE)/60f) * pixlsPerHour) + topArea);
		
		int startX = (nowTime.get(Calendar.DAY_OF_WEEK) + 5 )%7 *dayWidth + leftOffset;
		int endX = (nowTime.get(Calendar.DAY_OF_WEEK) + 5 )%7 *dayWidth +dayWidth+ leftOffset;

		
		Paint p = g2d.getPaint();
		Stroke s = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2));
		g2d.setPaint(Color.red);
		
		g2d.drawLine(startX, Y, endX, Y);
		
		//reset
		g2d.setStroke(s);
		g2d.setPaint(p);
	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		startHour += 0.5f*e.getPreciseWheelRotation();
		startHour = Math.max(0, Math.min(startHour,24-hoursShown));
		System.out.println("startTime = "+startHour);
		
		repaint();		
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		for(Event event : testEvents){
			EventView ev = new EventView(event);
			ev.set(startHour, pixlsPerHour, dayWidth, leftOffset,topArea);
			if(ev.isAtPosition(e.getX(), e.getY())){
				selectedEvent = event;
				repaint();
				return;
			}
		}
		selectedEvent = null;
		repaint();
	}


}
