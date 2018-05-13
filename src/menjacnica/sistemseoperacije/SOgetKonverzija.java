package menjacnica.sistemseoperacije;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import domain.Valuta;
import menjacnica.util.URLConnectionUtil;

public class SOgetKonverzija {
	public static Valuta izvrsi(String zahtevUrl) {
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
