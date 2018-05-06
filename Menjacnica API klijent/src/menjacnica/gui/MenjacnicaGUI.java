package menjacnica.gui;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import menjacnica.Log;
import menjacnica.Valuta;
import menjacnica.Zemlja;
import menjacnica.gui.kontroler.GUIKotroler;
import menjacnica.util.URLConnectionUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MenjacnicaGUI() {
		String[] nizNazivaZemlji = GUIKotroler.getZemlje("http://free.currencyconverterapi.com/api/v3/countries");
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
		comboBox.setModel(new DefaultComboBoxModel(nizNazivaZemlji));
		comboBox.setBounds(42, 76, 146, 20);
		contentPane.add(comboBox);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(nizNazivaZemlji));
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

		JButton btnKonvertuj = new JButton("Konvertuj");
		btnKonvertuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// ZahtevUrl je u formatu EUR_RSD, uzima vrednosti iz comboBoxova
				String zahtevUrl = GUIKotroler.getSkraceniNaziv((comboBox.getSelectedItem().toString())) + "_"
						+ GUIKotroler.getSkraceniNaziv((comboBox_1.getSelectedItem().toString()));
				// Valuta dobija vrednost jsonObjekta koji se dobija upitom q=RSD_EUR
				Valuta valuta = GUIKotroler.getKonverzija(zahtevUrl);
				// Kontrola da li je nadjena valuta
				if (valuta != null) {
					// Vrsi konverziju i upisuje u odgovarajuci textbox
					izvrsiKonverziju(valuta.getVal());
					// Cuva log
					GUIKotroler.upmatiLog(zahtevUrl, valuta.getVal(), "data/log.json");
				} else
					JOptionPane.showMessageDialog(contentPane, "Nije pronadjena konverzija za: " + zahtevUrl, "ERROR",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		btnKonvertuj.setBounds(170, 212, 89, 23);
		contentPane.add(btnKonvertuj);
	}

	private void izvrsiKonverziju(double val) {
		try {
			double iznosIz = Double.parseDouble(textField.getText());
			textField_1.setText(String.valueOf(iznosIz * val));
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(contentPane, "Nije broj.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
