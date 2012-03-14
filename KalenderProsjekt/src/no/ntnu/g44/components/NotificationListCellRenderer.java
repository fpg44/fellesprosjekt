package no.ntnu.g44.components;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.Renderer;

import sun.font.TextLabel;

import no.ntnu.g44.models.Notification;

public class NotificationListCellRenderer extends JLabel implements ListCellRenderer {

	JLabel textLabel;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		//		int selectedIndex = ((Integer)value).intValue();

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		if(index > 0){
			Notification not =((Notification)value);

			String s = not.getMessage(); 
			setText(s);
		}

		return this;
	}

}
