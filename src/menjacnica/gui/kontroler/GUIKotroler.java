package menjacnica.gui.kontroler;

import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import domain.Valuta;
import menjacnica.MenjacnicaService;
import menjacnica.gui.MenjacnicaGUI;

public class GUIKotroler {
	public static MenjacnicaService menjacnicaService = new MenjacnicaService();

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

	public static double getValue(String zahtevUrl) {
		Valuta valuta = GUIKotroler.menjacnicaService.getKonverzija(zahtevUrl);
		if (valuta != null) {
			return valuta.getVal();
		} else
			JOptionPane.showMessageDialog(null, "Nije pronadjena konverzija za: " + zahtevUrl, "ERROR",
					JOptionPane.ERROR_MESSAGE);
		return 0;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void napuniDrzave(JComboBox comboBox) {
		comboBox.setModel(new DefaultComboBoxModel(
				menjacnicaService.getZemlje("http://free.currencyconverterapi.com/api/v3/countries")));
	}

	public static String konvertujUValutu(JTextField textField, String zahtevUrl) {
		try {
			double val = getValue(zahtevUrl);
			double iz = Double.parseDouble(textField.getText());
			GUIKotroler.menjacnicaService.zapamtiLog(zahtevUrl, val);
			return menjacnicaService.izvrsiKonverziju(iz, val);
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "Nije broj!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes" })
	public static String napraviZahtevUrl(JComboBox comboBox, JComboBox comboBox_1) {
		return menjacnicaService.getSkraceniNaziv((comboBox.getSelectedItem().toString())) + "_"
				+ menjacnicaService.getSkraceniNaziv((comboBox_1.getSelectedItem().toString()));
	}
}
