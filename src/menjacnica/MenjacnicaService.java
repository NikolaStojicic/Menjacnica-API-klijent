package menjacnica;

import java.util.LinkedList;

import domain.Valuta;
import domain.Zemlja;
import menjacnica.sistemseoperacije.SOgetKonverzija;
import menjacnica.sistemseoperacije.SOgetSkraceniNaziv;
import menjacnica.sistemseoperacije.SOgetZemlje;
import menjacnica.sistemseoperacije.SOzapamtiLog;

public class MenjacnicaService {

	private LinkedList<Zemlja> listaZemlji = new LinkedList<Zemlja>();

	public LinkedList<Zemlja> getListaZemlji() {
		return listaZemlji;
	}

	public void setListaZemlji(LinkedList<Zemlja> listaZemlji) {
		this.listaZemlji = listaZemlji;
	}

	public String[] getZemlje(String url) {
		return SOgetZemlje.izvrsi(url, listaZemlji);
	}

	public Valuta getKonverzija(String url, String zahtevUrl) {
		return SOgetKonverzija.izvrsi(url, zahtevUrl);
	}

	public void zapamtiLog(String zahtevUrl, double valuta, String path) {
		SOzapamtiLog.izvrsi(zahtevUrl, valuta, path);
	}

	public String getSkraceniNaziv(String punoIme) {
		return SOgetSkraceniNaziv.izvrsi(punoIme, listaZemlji);
	}

	public String izvrsiKonverziju(double iznosIz, double val) {
		return String.valueOf(iznosIz * val);
	}
}
