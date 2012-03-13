package Components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.w3c.dom.events.EventTarget;
import org.w3c.dom.views.AbstractView;

/**
 * A ListCellRenderer for elements with a text-label and an ImageIcon
 * @author Robin
 */
public class ListRenderer extends JPanel implements ListCellRenderer{

	private static final Color HOVER_COLOR = new Color(194, 222, 211);
	private static final Color SELECTED_COLOR = new Color(194, 222, 242);
	protected static final ImageIcon iconRemove = new ImageIcon("src/Components/x.png");
	protected static final ImageIcon iconRemoveMouseOver = new ImageIcon("src/Components/x2.png");
	private JLabel removeLabel = new JLabel(iconRemove);
	private JLabel textLabel = new JLabel();
	private MouseAdapter handler;
	private int hoverIndex = -1;

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		//Sets the text to be shown in the list
		textLabel.setText(value.toString());

		//Add some standard layout
		setLayout(new BorderLayout());

		//Add text-label and remove-icon
		add(textLabel, BorderLayout.WEST);
		add(removeLabel, BorderLayout.EAST);

		//The colors to the selected items in the list
		if (isSelected) {
			setBackground(SELECTED_COLOR);
			setForeground(list.getSelectionForeground());
		}

		//The colors to the unselected items
		else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		setOpaque(true);

		//Sets the background-color to hover or normal
		if (!isSelected) {  
			
			//if the hoverIndex is the same as the list index, an item is being hovered
			//else set the background to default
			setBackground(index == hoverIndex ? HOVER_COLOR : list.getBackground());  
		}

		//Sets the remove-icon to "mouse over" or "normal"
		//If the hoverIndex is the same as the list index, an item is being hovered
		setIcon(index == hoverIndex ? iconRemoveMouseOver : iconRemove);

		return this;
	}

	/**
	 * Sets the image to the rightmost label
	 * @param ImageIcon
	 */
	public void setIcon(ImageIcon i){
		removeLabel.setIcon(i);
	}

	/**
	 * Initializes the handler for hover-effect
	 * @param list
	 * @return a HoverMouseHandler initialized with a JList
	 */
	public MouseAdapter getHandler(JList list){
		if(handler == null){
			handler = new HoverMouseHandler(list);
		}
		return handler;
	}

	/**
	 * A class that handles the mouse listener to the hover-effect in ListRenderer
	 * @author Robin
	 *
	 */
	private class HoverMouseHandler extends MouseAdapter{

		private final JList list;

		public HoverMouseHandler(JList list){
			this.list = list;
		}

		@Override
		public void mouseExited(MouseEvent e){
			setHoverIndex(-1);
		}

		/*
		Checks the location to the mouse and checks if the list contains an element at that point
		If so, the hoverIndex is set to the list index at that position, 
		else the index is set to -1 (indicates no hovering effect) 
		*/
		@Override
		public void mouseMoved(MouseEvent e){
			int index = list.locationToIndex(e.getPoint());
			setHoverIndex(list.getCellBounds(index, index).contains(e.getPoint()) ? index : -1);
		}

		
		//Checks what element the mouse is hovering over
		private void setHoverIndex(int index){
			if(hoverIndex == index){
				return;
			}
			hoverIndex = index;
			list.repaint();
		}
	}
}