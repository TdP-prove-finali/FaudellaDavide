package it.polito.tdp.FaudellaDavide.model;

public class Libro {
	private int numID;
	private float prezzo;
	private int rank;
	private String titolo;
	private int numRecensioni;
	private float rating;
	private String autore;
	private String tipoCopertina;
	private int anno;
	private String genere;

	public Libro(int numID, float prezzo, int rank, String titolo, int numRecensioni, float rating, String autore,
			String tipoCopertina, int anno, String genere) {
		super();
		this.numID = numID;
		this.prezzo = prezzo;
		this.rank = rank;
		this.titolo = titolo;
		this.numRecensioni = numRecensioni;
		this.rating = rating;
		this.autore = autore;
		this.tipoCopertina = tipoCopertina;
		this.anno = anno;
		this.genere = genere;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (numID != other.numID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return titolo + ", " + anno;
	}

	public int getNumID() {
		return numID;
	}

	public void setNumID(int numID) {
		this.numID = numID;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public int getNumRecensioni() {
		return numRecensioni;
	}

	public void setNumRecensioni(int numRecensioni) {
		this.numRecensioni = numRecensioni;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getTipoCopertina() {
		return tipoCopertina;
	}

	public void setTipoCopertina(String tipoCopertina) {
		this.tipoCopertina = tipoCopertina;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

}
