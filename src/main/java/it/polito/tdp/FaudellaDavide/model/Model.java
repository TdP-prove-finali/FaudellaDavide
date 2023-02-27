package it.polito.tdp.FaudellaDavide.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import it.polito.tdp.FaudellaDavide.db.LibriDAO;

public class Model {

	LibriDAO dao = new LibriDAO();
	List<Libro> daLeggere = new LinkedList<>();
	List<Libro> best;
	List<Libro> parziale = new ArrayList<>();
	List<Libro> libriParametri = new LinkedList<>();

	public List<Libro> cercaLibri(String aut, int annoI, int annoF) {
		List<Libro> libri = new LinkedList<>();
		for (Libro l : getAllBooks()) {
			if (l.getAutore().equals(aut) && l.getAnno() <= annoF && l.getAnno() >= annoI) {
				libri.add(l);
			}
		}
		return libri;
	}

	public boolean aggiungi(Libro l) {
		if (daLeggere.contains(l)) {
			return false;
		}
		daLeggere.add(l);
		return true;
	}

	public boolean rimuovi(Libro l) {
		if (daLeggere.contains(l)) {
			daLeggere.remove(l);
			return true;
		} else {
			return false;
		}
	}

	public void cancellaLista() {
		daLeggere.clear();
	}

	public List<Libro> getDaLeggere() {
		return daLeggere;
	}

	public boolean aggiungiBest(Libro l) {
		if (parziale.contains(l)) {
			return false;
		}
		parziale.add(l);
		return true;
	}

	public void cancellaBest() {
		best.clear();
		parziale.clear();
	}

	public List<Libro> getBest() {
		return best;
	}

	public List<String> getAutori() {
		return dao.getAllAuthors();
	}

	public List<Integer> getYears() {
		return dao.getAllYears();
	}

	public List<String> getGeneri() {
		return dao.getAllGenres();
	}

	public List<String> getCopertina() {
		return dao.getAllCoverTypes();
	}

	public List<Libro> getAllBooks() {
		return dao.getAllBooks();
	}

	public List<Libro> getLibriPerAutore(String autore) {
		return dao.getLibriPerAutore(autore);
	}

	public List<Libro> getParziale() {
		return parziale;
	}

	public boolean isStronger(Libro li) {
		for (Libro l : libriParametri) {
			if (l.getTitolo().equals(li.getTitolo())) {
				if (l.getRank() < li.getRank()) {
					return false;
				}
			}
		}
		return true;
	}

	public List<Libro> doRicorsione(float budget, float rating, int numero, int annoI, int annoF, String copertina,
			String genere) {
		libriParametri = dao.getBooksParametri(rating, numero, annoI, annoF, copertina, genere);
		best = new ArrayList<>();
		cerca(parziale, budget);
		return best;
	}

	private void cerca(List<Libro> parziale, float budget) {
		if (totale(best) == budget) {
			return;
		}
		float diffB = budget - totale(best);
		float diffP = budget - totale(parziale);
		if (diffB > diffP) {
			best = new ArrayList<>(parziale);
		}
		for (Libro l : libriParametri) {
			if (isValid(parziale, l, budget)) {
				parziale.add(l);
				cerca(parziale, budget);
				parziale.remove(parziale.size() - 1);
			}
		}
	}

	private boolean isValid(List<Libro> parziale, Libro li, float budget) {
		float totale = li.getPrezzo();
		for (Libro l : parziale) {
			totale += l.getPrezzo();
		}
		if (totale <= budget && !parziale.contains(li)) {
			for (Libro l : libriParametri) {
				if (l.getTitolo().equals(li.getTitolo())) {
					if (isStronger(li)) {
						return true;
					} else {
						return false;
					}
				}

			}
			return true;
		}
		return false;
	}

	public float totale(List<Libro> toCalc) {
		float somma = 0;
		for (Libro l : toCalc) {
			somma += l.getPrezzo();
		}
		return somma;
	}

	public Libro bestSeller(List<Libro> toCalc) {
		Libro bestSeller = toCalc.get(0);
		for (Libro l : toCalc) {
			if (l.getRank() < bestSeller.getRank()) {
				bestSeller = l;
			}
		}
		return bestSeller;
	}
}
