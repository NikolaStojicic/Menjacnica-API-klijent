package menjacnica.gui.kontroler;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import menjacnica.Log;
import menjacnica.Valuta;
import menjacnica.Zemlja;
import menjacnica.gui.MenjacnicaGUI;
import menjacnica.util.URLConnectionUtil;

public class GUIKotroler {
	public static LinkedList<Zemlja> listaZemlji = new LinkedList<Zemlja>();

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

	public static LinkedList<Log> ucitajLog(String filepath) {
		LinkedList<Log> novaLista = new LinkedList<Log>();
		try {
			FileReader reader = new FileReader(filepath);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
			if (jsonArray == null)
				return null;
			for (int i = 0; i < jsonArray.size(); i++)
				novaLista.add(gson.fromJson(jsonArray.get(i), Log.class));
		} catch (FileNotFoundException e) {
			System.out.println("Kreiram fajl: data/log.json");
		}
		return novaLista;
	}

	public static void upmatiLog(String zahtevUrl, double valuta, String filepath) {
		LinkedList<Log> logList = ucitajLog(filepath);
		Log logData = new Log();
		logData.setIzValuta(zahtevUrl.split("_")[0]);
		logData.setuValuta(zahtevUrl.split("_")[1]);
		logData.setDatumVreme(new SimpleDateFormat("dd-MM-yyyy 'u' HH:mm:ss.SSSSS").format(new Date()));
		logData.setKurs(valuta);
		if (logList == null)
			logList = new LinkedList<Log>();
		logList.add(logData);
		try {
			FileWriter writer = new FileWriter(filepath);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(logList, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getSkraceniNaziv(String punoIme) {
		for (int i = 0; i < listaZemlji.size(); i++)
			if (listaZemlji.get(i).getName().equals(punoIme))
				return listaZemlji.get(i).getCurrencyId();
		return null;
	}

	public static String[] getZemlje(String url) {
		try {
			String content = URLConnectionUtil.getContent(url);
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

	public static Valuta getKonverzija(String zahtevUrl) {
		String url = "http://free.currencyconverterapi.com/api/v3/convert?q=";
		url += zahtevUrl;
		try {
			String content = URLConnectionUtil.getContent(url);
			JsonParser jsonPraser = new JsonParser();
			JsonObject jsonObj = jsonPraser.parse(content).getAsJsonObject().getAsJsonObject("results")
					.getAsJsonObject(zahtevUrl);
			Gson gson = new GsonBuilder().create();
			Valuta valuta = gson.fromJson(jsonObj, Valuta.class);
			return valuta;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
