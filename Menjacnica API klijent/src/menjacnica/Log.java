package menjacnica;

public class Log {
	private String datumVreme;
	private String izValuta;
	private String uValuta;
	private double kurs;
	public String getDatumVreme() {
		return datumVreme;
	}
	public void setDatumVreme(String datumVreme) {
		this.datumVreme = datumVreme;
	}
	public String getIzValuta() {
		return izValuta;
	}
	public void setIzValuta(String izValuta) {
		this.izValuta = izValuta;
	}
	public String getuValuta() {
		return uValuta;
	}
	public void setuValuta(String uValuta) {
		this.uValuta = uValuta;
	}
	public double getKurs() {
		return kurs;
	}
	public void setKurs(double kurs) {
		this.kurs = kurs;
	}
}
