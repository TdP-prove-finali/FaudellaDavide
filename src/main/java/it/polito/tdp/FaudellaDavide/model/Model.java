package it.polito.tdp.FaudellaDavide.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import it.polito.tdp.FaudellaDavide.db.LibriDAO;

public class Model {

	LibriDAO dao = new LibriDAO();
	List<Libro> daLeggere = new LinkedList<>();
	List<Libro> best = new ArrayList<>();;
	List<Libro> parziale = new ArrayList<>();
	List<Libro> libriParametri = new LinkedList<>();
	List<Libro> tuttiLibri = getAllBooks();

	public List<Libro> cercaLibri(String aut, int annoI, int annoF) {
		List<Libro> libri = new LinkedList<>();
		for (Libro l : tuttiLibri) {
			if (l.getAutore().equals(aut) && l.getAnno() <= annoF && l.getAnno() >= annoI) {
				libri.add(l);
			}
		}
		return libri;
	}

	public List<Libro> cercaLibriAutore(String aut) {
		List<Libro> libri = new LinkedList<>();
		for (Libro l : tuttiLibri) {
			if (l.getAutore().equals(aut)) {
				libri.add(l);
			}
		}
		return libri;
	}

	public List<Libro> cercaLibriAnno(int annoI, int annoF) {
		List<Libro> libri = new LinkedList<>();
		for (Libro l : tuttiLibri) {
			if (l.getAnno() <= annoF && l.getAnno() >= annoI) {
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

	int flag = 0;

	public List<Libro> doRicorsione(float budget, float rating, int numero, int annoI, int annoF, String copertina,
			String genere) {
		flag = 0;
		libriParametri = dao.getBooksParametri(rating, numero, annoI, annoF, copertina, genere);
		best = new ArrayList<>();

		if (budget > totale(libriParametri)) {
			best = new ArrayList<>(libriParametri);
			return best;
		}

		cerca(parziale, budget);
		return best;
	}

	private void cerca(List<Libro> parziale, float budget) {
		if (totale(best) == budget) {
			flag = 1;
		}

		float diffB = budget - totale(best);
		float diffP = budget - totale(parziale);

		if (diffB > diffP) { // parziale si avvicina di più al budget da spendere
			best = new ArrayList<>(parziale);
		}
		if (diffB == diffP) { // se è uguale prendo quello con più stelle medie
			if (stelleMaggiori(best, parziale) == true) {
				best = new ArrayList<>(parziale);
			}
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
		if (flag == 1) { // se siamo gia al budget desiderato e cerchiamo altre combinazioni magari con
							// più stelle
			// se ne troviamo alcune che non sono esattamente uguali al budget le scartiamo
			// già qui
			if (totale != budget) {
				return false;
			}
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
		Libro bestSeller = null;
		if (toCalc.size() > 0) {
			bestSeller = toCalc.get(0);
		} else {
			return null;
		}
		for (Libro l : toCalc) {
			if (l.getRank() < bestSeller.getRank()) {
				bestSeller = l;
			}
		}
		return bestSeller;
	}

	private boolean stelleMaggiori(List<Libro> best2, List<Libro> parziale2) {
		float mediaStelleBest = 0;
		float mediaStelleParz = 0;
		for (Libro l : best2) {
			mediaStelleBest += l.getRating();
		}
		for (Libro l : parziale2) {
			mediaStelleParz += l.getRating();
		}
		mediaStelleBest /= best2.size();
		mediaStelleParz /= parziale2.size();

		if (mediaStelleParz > mediaStelleBest) {
			return true;
		} else {
			return false;
		}

	}
}
