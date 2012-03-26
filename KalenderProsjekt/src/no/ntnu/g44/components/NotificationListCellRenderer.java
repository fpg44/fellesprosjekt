package no.ntnu.g44.components;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import no.ntnu.g44.models.Notification;

public class NotificationListCellRenderer extends JLabel implements ListCellRenderer {

	JLabel textLabel;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		setText(((Notification) value).getMessage());
		/*
		if(index > 0) {
		}

		else {
			setText(value.toString());
		}

*/
		setOpaque(true);

		return this;
	}

}
