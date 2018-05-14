package menjacnica.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import menjacnica.gui.kontroler.GUIKotroler;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenjacnicaGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes" })
	public MenjacnicaGUI() {
		setResizable(false);
		setTitle("Menjacnica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblIzValuteZemlje = new JLabel("Iz valute zemlje:");
		lblIzValuteZemlje.setBounds(32, 53, 146, 14);
		contentPane.add(lblIzValuteZemlje);

		JComboBox comboBox = new JComboBox();

		comboBox.setBounds(42, 76, 146, 20);
		contentPane.add(comboBox);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(246, 76, 146, 20);
		contentPane.add(comboBox_1);

		JLabel lblUValutuZemlje = new JLabel("U valutu zemlje:");
		lblUValutuZemlje.setBounds(236, 53, 146, 14);
		contentPane.add(lblUValutuZemlje);

		JLabel label = new JLabel("Iz valute zemlje:");
		label.setBounds(32, 125, 146, 14);
		contentPane.add(label);

		JLabel label_1 = new JLabel("U valutu zemlje:");
		label_1.setBounds(236, 125, 146, 14);
		contentPane.add(label_1);

		textField = new JTextField();
		textField.setBounds(42, 148, 146, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(246, 148, 146, 20);
		contentPane.add(textField_1);

		GUIKotroler.napuniDrzave(comboBox_1);
		GUIKotroler.napuniDrzave(comboBox);

		JButton btnKonvertuj = new JButton("Konvertuj");
		btnKonvertuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String z = GUIKotroler.napraviZahtevURL(comboBox, comboBox_1);
				textField_1.setText(GUIKotroler.konvertujUValutu(textField, z));
			}
		});
		btnKonvertuj.setBounds(170, 212, 89, 23);
		contentPane.add(btnKonvertuj);
	}
}
