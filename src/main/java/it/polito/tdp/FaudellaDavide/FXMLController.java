package it.polito.tdp.FaudellaDavide;

import java.util.LinkedList;
import java.util.List;
import it.polito.tdp.FaudellaDavide.model.Libro;
import it.polito.tdp.FaudellaDavide.model.Model;

import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class FXMLController {

	private Model model;

	@FXML
	private Button btnAggiungiLeggi;

	@FXML
	private Button btnAggiungiListaGen;

	@FXML
	private Button btnGenera;

	@FXML
	private Button btnResetGenera;

	@FXML
	private Button btnResetLeggi;

	@FXML
	private Button btnResettaRicerca;

	@FXML
	private Button btnRicerca;

	@FXML
	private Button btnRimuoviListaLeggere;

	@FXML
	private ComboBox<Integer> cmbAnnoF;

	@FXML
	private ComboBox<Integer> cmbAnnoFGen;

	@FXML
	private ComboBox<Integer> cmbAnnoI;

	@FXML
	private ComboBox<Integer> cmbAnnoIGen;

	@FXML
	private ComboBox<String> cmbAutore;

	@FXML
	private ComboBox<String> cmbAutoreGen;

	@FXML
	private ComboBox<String> cmbCopertina;

	@FXML
	private ComboBox<String> cmbGenereLibro;

	@FXML
	private ComboBox<Libro> cmbLibroGen;

	@FXML
	private ComboBox<Libro> cmbTitoloLeggi;

	@FXML
	private ComboBox<Libro> cmbTitoloLeggiRimuovi;

	@FXML
	private AnchorPane n2;

	@FXML
	private Tab tab1;

	@FXML
	private Tab tab2;

	@FXML
	private TabPane tb;

	@FXML
	private Text txtBestSeller;

	@FXML
	private TextField txtBudget;

	@FXML
	private Text txtCostoTot;

	@FXML
	private TextArea txtErrori;

	@FXML
	private TextArea txtErroriGen;

	@FXML
	private TextArea txtGenerato;

	@FXML
	private TextArea txtLibri;

	@FXML
	private TextArea txtListaLeggere;

	@FXML
	private Text txtNumLib;

	@FXML
	private TextField txtNumReceMin;

	@FXML
	private TextField txtRatingMin;

	@FXML
	void doAggiornaCmbLibroGen(ActionEvent event) {
		cmbLibroGen.getItems().clear();
		String autore = cmbAutoreGen.getValue();
		cmbLibroGen.getItems().addAll(model.getLibriPerAutore(autore));
	}

	@FXML
	void doAggiungiLeggi(ActionEvent event) {
		txtErrori.clear();
		txtErrori.setVisible(false);
		if (cmbTitoloLeggi.getValue() == null) {
			txtErrori.setVisible(true);
			txtErrori.appendText("Scegliere un libro da aggiungere alla lista \n'Libri da leggere'\n");
			return;
		}
		boolean res = model.aggiungi(cmbTitoloLeggi.getValue());

		if (!res) {
			txtErrori.setVisible(true);
			txtErrori.appendText("Il libro è già presente nella lista 'Libri da leggere'\n");
			cmbTitoloLeggi.valueProperty().setValue(null);
			return;
		}

		cmbTitoloLeggiRimuovi.getItems().clear();
		cmbTitoloLeggiRimuovi.getItems().addAll(model.getDaLeggere());
		cmbTitoloLeggi.valueProperty().setValue(null);
		txtListaLeggere.setText(stringaLibro(model.getDaLeggere()));
	}

	@FXML
	void doAggiungiListaGen(ActionEvent event) {
		txtErroriGen.setVisible(false);
		txtErroriGen.clear();
		txtGenerato.setStyle("-fx-text-fill: black");
		String autore = cmbAutoreGen.getValue();
		if (autore == null) {
			txtErroriGen.setVisible(true);
			txtErroriGen.appendText("Seleziona un autore\n");
			return;
		}

		Libro libro = cmbLibroGen.getValue();
		if (libro == null) {
			txtErroriGen.setVisible(true);
			txtErroriGen.appendText("Seleziona un libro\n");
			return;
		}

		boolean res = model.aggiungiBest(libro);
		if (!res) {
			txtErroriGen.setVisible(true);
			txtErroriGen.appendText("Il libro è già presente nella lista\n");
			cmbLibroGen.valueProperty().setValue(null);
			return;
		}
		txtGenerato.setText(stringaLibro(model.getParziale()));
		cmbLibroGen.valueProperty().setValue(null);
	}

	@FXML
	void doGenera(ActionEvent event) {
		txtErroriGen.setVisible(false);
		txtErroriGen.clear();
		txtGenerato.clear();
		// txtGenerato.setText(stringaLibro(model.getBest()));
		float budget = 0;
		try {
			budget = Float.parseFloat(txtBudget.getText());
		} catch (NumberFormatException ex) {
			txtErroriGen.setVisible(true);
			txtErroriGen.appendText("Inserire un bugdet valido\n");
			return;
		}

		if (budget < 0) {
			txtErroriGen.setVisible(true);
			txtErroriGen.appendText("Inserire un bugdet maggiore di 0\n");
			return;
		}

		float rating = 0;
		try {
			rating = Float.parseFloat(txtRatingMin.getText());
		} catch (NumberFormatException ex) {
			txtErroriGen.setVisible(true);
			txtErroriGen.appendText("Inserire un rating valido\n");
			return;
		}

		if (rating < 0 || rating > 5) {
			txtErroriGen.setVisible(true);
			txtErroriGen.appendText("Inserire un rating compreso tra 0 e 5\n");
			return;
		}

		int recensioni = 0;
		try {
			recensioni = Integer.parseInt(txtNumReceMin.getText());
		} catch (NumberFormatException ex) {
			txtErroriGen.setVisible(true);
			txtErroriGen.appendText("Inserire un numero di recensioni \nvalido\n");
			return;
		}

		if (recensioni < 0) {
			txtErroriGen.setVisible(true);
			txtErroriGen.appendText("Inserire un numero di recensioni \nmaggiore di 0\n");
			return;
		}

		Integer annoIGen = cmbAnnoIGen.getValue();
		Integer annoFGen = cmbAnnoFGen.getValue();
		String copertina = cmbCopertina.getValue();
		String genere = cmbGenereLibro.getValue();

		if (annoIGen == null || annoFGen == null || copertina == null || genere == null) {
			if (annoIGen == null) {
				txtErroriGen.appendText("Seleziona anno iniziale\n");
			}
			if (annoFGen == null) {
				txtErroriGen.appendText("Seleziona anno finale\n");
			}
			if (copertina == null) {
				txtErroriGen.appendText("Seleziona il tipo di copertina\n");
			}
			if (genere == null) {
				txtErroriGen.appendText("Seleziona il genere del libro\n");
			}
			txtErroriGen.setVisible(true);
			return;
		}

		if (annoFGen < annoIGen) {
			txtErroriGen.setVisible(true);
			txtErroriGen.appendText("Seleziona un range di anni valido\n");
			return;
		}

		txtGenerato.setStyle("-fx-text-fill: black");
//		int prova = model.doRicorsione(budget, rating, recensioni, annoIGen, annoFGen, copertina, genere).size();
//		txtGenerato.appendText("" + prova);
		List<Libro> lista = model.doRicorsione(budget, rating, recensioni, annoIGen, annoFGen, copertina, genere);
		txtGenerato.appendText(stringaLibro(lista));
		txtNumLib.setText("Numero Libri: " + lista.size());
		txtCostoTot.setText("Costo totale: " + model.totale(lista));
		txtBestSeller.setText("Best Seller: " + model.bestSeller(lista));
	}

	@FXML
	void doResetGenera(ActionEvent event) {
		model.cancellaBest();
		txtGenerato.setText("Lista correttamente cancellata\n");
		txtBudget.clear();
		txtNumReceMin.clear();
		txtRatingMin.clear();
		cmbAnnoIGen.valueProperty().setValue(null);
		cmbAnnoFGen.valueProperty().setValue(null);
		cmbCopertina.valueProperty().setValue(null);
		cmbGenereLibro.valueProperty().setValue(null);
		cmbAutoreGen.valueProperty().setValue(null);
		cmbLibroGen.getItems().clear();
		txtNumLib.setText("Numero Libri: ");
		txtCostoTot.setText("Costo totale: ");
		txtBestSeller.setText("Best Seller: ");
		txtErroriGen.setVisible(false);
	}

	@FXML
	void doResetLeggi(ActionEvent event) {
		txtErrori.clear();
		txtErrori.setVisible(false);
		model.cancellaLista();
		cmbTitoloLeggiRimuovi.getItems().clear();
		txtListaLeggere.clear();
		txtListaLeggere.appendText("Lista correttamente resettata");
		;
	}

	@FXML
	void doResetRicerca(ActionEvent event) {
		txtErrori.clear();
		txtErrori.setVisible(false);
		cmbTitoloLeggi.getItems().clear();
		txtLibri.clear();
		cmbAutore.valueProperty().setValue(null);
		cmbAnnoI.valueProperty().setValue(null);
		cmbAnnoF.valueProperty().setValue(null);
	}

	@FXML
	void doRicerca(ActionEvent event) {
		txtErrori.clear();
		txtErrori.setVisible(false);
		cmbTitoloLeggi.getItems().clear();
		txtLibri.clear();
		String aut = cmbAutore.getValue();
		Integer annoI = cmbAnnoI.getValue();
		Integer annoF = cmbAnnoF.getValue();
		List<Libro> trovati = new LinkedList<>();

		// controllo input

		if (aut == null || annoI == null || annoF == null) {
			if (aut == null) {
				txtErrori.appendText("Seleziona un autore\n");
			}
			if (annoI == null) {
				txtErrori.appendText("Seleziona anno iniziale\n");
			}
			if (annoF == null) {
				txtErrori.appendText("Seleziona anno finale\n");
			}
			txtErrori.setVisible(true);
			return;
		}

		if (annoF < annoI) {
			txtErrori.setVisible(true);
			txtErrori.appendText("Seleziona un range di anni valido\n");
			return;
		}

		trovati.addAll(model.cercaLibri(aut, annoI, annoF));

		// se lista vuota dico che non ho trovato niente
		if (trovati.isEmpty() == true) {
			txtLibri.appendText("Non sono stati trovati libri di " + aut + " nel periodo: " + annoI + "-" + annoF);
			return;
		}
		txtLibri.appendText(stringaLibro(trovati));
		cmbTitoloLeggi.getItems().addAll(trovati);
	}

	@FXML
	void doRimuovi(ActionEvent event) {
		txtErrori.setVisible(false);
		txtErrori.clear();
		if (cmbTitoloLeggiRimuovi.getValue() == null) {
			txtErrori.setVisible(true);
			txtErrori.appendText("Scegliere un libro da rimuovere dalla lista \n'Libri da leggere'");
			return;
		}
		boolean res = model.rimuovi(cmbTitoloLeggiRimuovi.getValue());
		if (!res) {
			txtErrori.setVisible(true);
			txtErrori.appendText("Il libro non è presente nella lista 'Libri da leggere'\n");
			return;
		}
		cmbTitoloLeggiRimuovi.getItems().clear();
		cmbTitoloLeggiRimuovi.getItems().addAll(model.getDaLeggere());
		txtListaLeggere.setText(stringaLibro(model.getDaLeggere()));
	}

	private String stringaLibro(List<Libro> libri) {
		String stringa = "";
		for (Libro l : libri) {
			stringa += l.getTitolo() + ", " + l.getAnno() + "\n";
		}
		return stringa;
	}

	public void setModel(Model model) {
		this.model = model;
		cmbAutore.getItems().addAll(model.getAutori());
		cmbAutoreGen.getItems().addAll(model.getAutori());
		cmbAnnoI.getItems().addAll(model.getYears());
		cmbAnnoF.getItems().addAll(model.getYears());
		cmbAnnoIGen.getItems().addAll(model.getYears());
		cmbAnnoFGen.getItems().addAll(model.getYears());
		cmbCopertina.getItems().addAll(model.getCopertina());
		cmbGenereLibro.getItems().addAll(model.getGeneri());
	}

	@FXML
	void initialize() {
		assert btnAggiungiLeggi != null
				: "fx:id=\"btnAggiungiLeggi\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnAggiungiListaGen != null
				: "fx:id=\"btnAggiungiListaGen\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnGenera != null : "fx:id=\"btnGenera\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnResetGenera != null : "fx:id=\"btnResetGenera\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnResetLeggi != null : "fx:id=\"btnResetLeggi\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnResettaRicerca != null
				: "fx:id=\"btnResettaRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnRimuoviListaLeggere != null
				: "fx:id=\"btnRimuoviListaLeggere\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbAnnoF != null : "fx:id=\"cmbAnnoF\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbAnnoFGen != null : "fx:id=\"cmbAnnoFGen\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbAnnoI != null : "fx:id=\"cmbAnnoI\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbAnnoIGen != null : "fx:id=\"cmbAnnoIGen\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbAutore != null : "fx:id=\"cmbAutore\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbAutoreGen != null : "fx:id=\"cmbAutoreGen\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbCopertina != null : "fx:id=\"cmbCopertina\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbGenereLibro != null : "fx:id=\"cmbGenereLibro\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbLibroGen != null : "fx:id=\"cmbLibroGen\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbTitoloLeggi != null : "fx:id=\"cmbTitoloLeggi\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cmbTitoloLeggiRimuovi != null
				: "fx:id=\"cmbTitoloLeggiRimuovi\" was not injected: check your FXML file 'Scene.fxml'.";
		assert n2 != null : "fx:id=\"n2\" was not injected: check your FXML file 'Scene.fxml'.";
		assert tab1 != null : "fx:id=\"tab1\" was not injected: check your FXML file 'Scene.fxml'.";
		assert tab2 != null : "fx:id=\"tab2\" was not injected: check your FXML file 'Scene.fxml'.";
		assert tb != null : "fx:id=\"tb\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtBestSeller != null : "fx:id=\"txtBestSeller\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtBudget != null : "fx:id=\"txtBudget\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtCostoTot != null : "fx:id=\"txtCostoTot\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtErroriGen != null : "fx:id=\"txtErroriGen\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtGenerato != null : "fx:id=\"txtGenerato\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtLibri != null : "fx:id=\"txtLibri\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtListaLeggere != null
				: "fx:id=\"txtListaLeggere\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtNumLib != null : "fx:id=\"txtNumLib\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtNumReceMin != null : "fx:id=\"txtNumReceMin\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtRatingMin != null : "fx:id=\"txtRatingMin\" was not injected: check your FXML file 'Scene.fxml'.";

	}

}
