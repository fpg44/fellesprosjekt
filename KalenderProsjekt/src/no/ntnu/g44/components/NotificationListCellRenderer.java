package no.ntnu.g44.components;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.Renderer;

import sun.font.TextLabel;

import no.ntnu.g44.models.Notification;

public class NotificationListCellRenderer implements ListCellRenderer {
	
	JLabel textLabel;
	DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		textLabel = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		textLabel.setText(((Notification) value).getMessage());
		return null;
	}

}
