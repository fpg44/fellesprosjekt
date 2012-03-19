package no.ntnu.g44.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 * A ListCellRenderer for elements with a text-label and an ImageIcon
 * @author Robin
 */
public class ListRenderer extends JPanel implements ListCellRenderer{
	//public class ListRenderer extends JPanel implements ListCellRenderer{


	private static final Color HOVER_COLOR = new Color(194, 222, 211);
	private static final Color SELECTED_COLOR = new Color(194, 222, 242);
	protected static final ImageIcon iconRemove = new ImageIcon("src/no/ntnu/g44/components/x.png");
	protected static final ImageIcon iconRemoveMouseOver = new ImageIcon("src/no/ntnu/g44/components/x2.png");
	private JLabel removeLabel = new JLabel(iconRemove);
	private JLabel textLabel = new JLabel();
	private MouseAdapter handler;
	private int hoverIndex = -1;
	private JList l;
	private int i;
	private JButton button = new JButton("X");

	@Override
	public Component getListCellRendererComponent(final JList list, Object value, final int index, boolean isSelected, boolean hasFocus) {

		setLayout(new BorderLayout());
		add(new JLabel(((TestListRenderer)value).getTheName()), BorderLayout.WEST);
//		add(((TestListRenderer)value).getLabel(), BorderLayout.EAST);
		return this;
	}
}
