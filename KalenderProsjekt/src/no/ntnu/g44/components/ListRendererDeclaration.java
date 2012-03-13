package no.ntnu.g44.components;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ListRendererDeclaration extends JPanel{

	private static JFrame frame;
	private DefaultListModel<String> model ;
	private JList<String> list;
	private ListRenderer renderer;

	public static void main(String[] args) {

		frame = new JFrame();
		frame.setContentPane(new ListRendererDeclaration());
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(500, 200, 200, 200);

	}

	public ListRendererDeclaration(){

		//initialize objects
		model = new DefaultListModel<String>();
		list = new JList<String>(model);
		renderer = new ListRenderer();

		//add some items to model
		putItemsInModel();
		
		//setup list cell renderer and the necessary listeners
		list.setCellRenderer(renderer);
		list.addMouseListener(renderer.getHandler(list));  
		list.addMouseMotionListener(renderer.getHandler(list)); 
		
		//add component to panel
		add(list);
	}
	
	public void putItemsInModel(){	
		model.addElement("Ola Normann");
		model.addElement("Kari Normann");
		model.addElement("Kong Harald");
	}
}
