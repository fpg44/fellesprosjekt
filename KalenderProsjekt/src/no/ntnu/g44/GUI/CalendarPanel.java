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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JPanel;

import no.ntnu.g44.controllers.Main;
import no.ntnu.g44.models.Event;

public class CalendarPanel extends JPanel implements MouseWheelListener, MouseListener, PropertyChangeListener {
	String[] weekdays = new String[]{"Monday","Tuesday","Wednesday",
			"Thursday","Friday","Saturday","Sunday"};
	private float startHour = 8f;
	private float hoursShown = 8f;
	int textPos = 40;
	int margin = 10;
	int topArea = margin+textPos;
	int pixlsPerHour = (int) ((getHeight() -topArea)/hoursShown);	
	private Event selectedEvent = null;
	Calendar nowTime;

	public CalendarPanel() {
		addMouseWheelListener(this);
		addMouseListener(this);
		Main.currentProject.addPropertyChangeListener(this);
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
		paintDayText(g2d);
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
	}

	private void paintDayText(Graphics2D g2d){
		int width = getWidth() - leftOffset;
		Font prefont = g2d.getFont();
		Paint prepaint = g2d.getPaint();
		//paint background
		g2d.setPaint(getBackground());
		g2d.fill(new Rectangle2D.Double(0, 0, getWidth(),topArea));
		g2d.drawRect(0, 0, getWidth(),topArea);
		
		//repaint the line between days
		
		dayWidth = Math.round(width/7f);
		g2d.setColor(Color.black);
		for(int i = 0; i<= 7; i++){
			int xPos = i*dayWidth+leftOffset;
			g2d.drawLine(xPos, 0, xPos, topArea);
		}
		
		g2d.setPaint(prepaint);
		
		// Add date of day
		Font dateFont = new Font("Helvetica", Font.PLAIN, 12);
		g2d.setFont(dateFont);
		// the locale needs to be set (to "no" for Norway) in order for monday 
		// to be the first day of the week
		Calendar cal = Calendar.getInstance(new Locale("no"));
		cal.clear();
		cal.set(Calendar.YEAR, Main.currentMainFrame.currentYear);
		cal.set(Calendar.WEEK_OF_YEAR, Main.currentMainFrame.currentWeekNumber);
		for (int i = 0; i < 7; i++) {
			int xPos = Math.round(width * i / 7f) + margin + leftOffset;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			g2d.drawChars(dateFormat.format(cal.getTime()).toCharArray(),
					0, 10, xPos, textPos - 20);
			cal.add(Calendar.DATE, 1);
		}
		
		// Add name of day
		Font f = new Font("Helvetica",Font.BOLD, 16);
		g2d.setFont(f);
		for(int i = 0; i < 7; i++){
			int xPos = Math.round(width*i/7f) + margin+leftOffset;	
			g2d.drawChars(weekdays[i].toCharArray(), 0, weekdays[i].toCharArray().length, xPos, textPos );

		}
		g2d.drawLine(0, topArea, getWidth(), topArea);
		g2d.setFont(prefont); //reset font
		g2d.setPaint(prepaint);
	}


	@SuppressWarnings("deprecation")
	private void paintEvents(Graphics2D g2d){
		g2d.setStroke(new BasicStroke(2));

		for(Event e:Main.currentProject.getEventList()){
			if(!e.isInWeek(Main.currentMainFrame.currentWeekNumber)) continue;
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

	public int getWeekNumber(){
		if(nowTime == null)return 0;
		return nowTime.get(Calendar.WEEK_OF_YEAR);
	}
	private void paintNowLine(Graphics2D g2d){
		//		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		//		TimeZone timezone = registry.getTimeZone("America/Mexico_City");
		//		VTimeZone tz = timezone.getVTimeZone();

		//		TimeZone tz=TimeZone.getTimeZone("G");
		nowTime = Calendar.getInstance();
		//		nowTime.setTimeZone(tz);
		nowTime.setTime(new Date());

		int Y = (int) ((((nowTime.get(Calendar.HOUR_OF_DAY)-this.startHour)+
				nowTime.get(Calendar.MINUTE)/60f) * pixlsPerHour) + topArea);

		//The first day of week in java.util.Calendar is saturday, therefore +5
		int startX = (nowTime.get(Calendar.DAY_OF_WEEK) +5 )%7 *dayWidth + leftOffset;
		int endX = (nowTime.get(Calendar.DAY_OF_WEEK) +5 )%7 *dayWidth +dayWidth+ leftOffset;


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
		startHour += 0.5f*e.getWheelRotation();
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
		ArrayList<Event> events = Main.currentProject.getEventList();
		int len = events.size();
		for(int i = len-1;i>= 0;i--){
			EventView ev = new EventView(events.get(i));
			ev.set(startHour, pixlsPerHour, dayWidth, leftOffset,topArea);
			if(ev.isAtPosition(e.getX(), e.getY())){
				selectedEvent = events.get(i);
				Main.currentProject.getEventList().remove(selectedEvent);
				Main.currentProject.getEventList().add(selectedEvent);
				repaint();
				return;
			}
		}
		selectedEvent = null;
		repaint();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		repaint();
	}


}
