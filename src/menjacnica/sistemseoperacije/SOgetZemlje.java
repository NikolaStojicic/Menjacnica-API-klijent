package menjacnica.sistemseoperacije;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import domain.Zemlja;
import menjacnica.util.URLConnectionUtil;

public class SOgetZemlje {
	public static String[] izvrsi(String url, LinkedList<Zemlja> listaZemlji) {
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
}
