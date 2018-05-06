package menjacnica.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import menjacnica.Valuta;
import menjacnica.Zemlja;
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

	LinkedList<Zemlja> listaZemlji = new LinkedList<Zemlja>();

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenjacnicaGUI frame = new MenjacnicaGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenjacnicaGUI() {
		String[] nizNazivaZemlji = getZemlje();
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
				String url = "http://free.currencyconverterapi.com/api/v3/convert?q=";
				String zahtevUrl = getSkraceniNaziv(comboBox.getSelectedItem().toString()) + "_"
						+ getSkraceniNaziv(comboBox_1.getSelectedItem().toString());
				url += zahtevUrl;
				try {
					String content = URLConnectionUtil.getContent(url);
					JsonParser jsonPraser = new JsonParser();
					JsonObject jsonObj = jsonPraser.parse(content).getAsJsonObject().getAsJsonObject("results")
							.getAsJsonObject(zahtevUrl);
					Gson gson = new GsonBuilder().create();
					Valuta valuta = gson.fromJson(jsonObj, Valuta.class);

					if (valuta != null)
						izvrsiKonverziju(valuta.getVal());
					else
						JOptionPane.showMessageDialog(contentPane, "Nije pronadjena konverzija za: " + zahtevUrl,
								"ERROR", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();
				}
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

	private String getSkraceniNaziv(String punoIme) {
		for (int i = 0; i < listaZemlji.size(); i++)
			if (listaZemlji.get(i).getName().equals(punoIme))
				return listaZemlji.get(i).getCurrencyId();
		return null;
	}

	private String[] getZemlje() {
		try {
			String content = URLConnectionUtil.getContent("http://free.currencyconverterapi.com/api/v3/countries");
			Gson gson = new GsonBuilder().create();
			JsonParser jsonPraser = new JsonParser();
			JsonObject jsonObj = jsonPraser.parse(content).getAsJsonObject().getAsJsonObject("results");
			for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
				Zemlja zemlja = gson.fromJson(entry.getValue(), Zemlja.class);
				listaZemlji.add(zemlja);
			}
			String[] nizNazivaZemlji = new String[listaZemlji.size()];
			for (int i = 0; i < listaZemlji.size(); i++)
				nizNazivaZemlji[i] = listaZemlji.get(i).getName();
			return nizNazivaZemlji;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
