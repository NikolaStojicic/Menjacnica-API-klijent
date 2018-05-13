package menjacnica.sistemseoperacije;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import domain.Log;

public class SOzapamtiLog {

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

	public static void izvrsi(String zahtevUrl, double valuta, String filepath) {
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
}
