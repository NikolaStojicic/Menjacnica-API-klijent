package menjacnica.sistemseoperacije;

import java.util.LinkedList;

import domain.Zemlja;

public class SOgetSkraceniNaziv {
	public static String izvrsi(String punoIme, LinkedList<Zemlja> listaZemlji) {
		for (int i = 0; i < listaZemlji.size(); i++)
			if (listaZemlji.get(i).getName().equals(punoIme))
				return listaZemlji.get(i).getCurrencyId();
		return null;
	}
}
