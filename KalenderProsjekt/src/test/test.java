package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;

public class test {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 359, 205);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setUndecorated(true);
		frame.getContentPane().setLayout(null);
		frame.setTitle("New invitation");
		
		JButton btnNewButton = new JButton("Yes");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(242, 112, 86, 41);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("No");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(136, 112, 86, 41);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Cancel");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(22, 112, 86, 41);
		frame.getContentPane().add(btnNewButton_2);
		
		textField = new JTextField();
		textField.setText("24.03.2012");
		textField.setBounds(76, 33, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setEditable(false);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(10, 36, 46, 14);
		frame.getContentPane().add(lblDate);
		
		JLabel lblTime = new JLabel("Time");
		lblTime.setBounds(10, 61, 46, 14);
		frame.getContentPane().add(lblTime);
		
		textField_1 = new JTextField();
		textField_1.setText("14:15 - 13:00");
		textField_1.setBounds(76, 64, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblGroupleader = new JLabel("Groupleader");
		lblGroupleader.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblGroupleader.setBounds(10, 11, 86, 14);
		frame.getContentPane().add(lblGroupleader);
		
		textField_2 = new JTextField();
		textField_2.setBounds(78, 8, 86, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblBalbalbalBalBal = new JLabel("<html>balbalbal bal bal ablabalb ala bal l ab ala bla</html>");
		lblBalbalbalBalBal.setBounds(187, 11, 141, 90);
		frame.getContentPane().add(lblBalbalbalBalBal);
		
		JLabel lblRoom = new JLabel("location");
		lblRoom.setBounds(10, 86, 46, 14);
		frame.getContentPane().add(lblRoom);
		
		textField_3 = new JTextField("room 302");
		textField_3.setBounds(76, 95, 86, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
	}
}
