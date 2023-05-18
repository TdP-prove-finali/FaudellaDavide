package it.polito.tdp.FaudellaDavide;

import java.util.LinkedList;
import java.util.List;
import it.polito.tdp.FaudellaDavide.model.Libro;
import it.polito.tdp.FaudellaDavide.model.Model;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private TableColumn<Libro, Integer> clAnno1;

	@FXML
	private TableColumn<Libro, Integer> clAnno2;

	@FXML
	private TableColumn<Libro, Integer> clAnnoG;

	@FXML
	private TableColumn<Libro, String> clAutore1;

	@FXML
	private TableColumn<Libro, String> clAutore2;

	@FXML
	private TableColumn<Libro, String> clAutoreG;

	@FXML
	private TableColumn<Libro, Float> clPrezzo1;

	@FXML
	private TableColumn<Libro, Float> clPrezzo2;

	@FXML
	private TableColumn<Libro, Float> clPrezzoG;

	@FXML
	private TableColumn<Libro, String> clCopertina1;

	@FXML
	private TableColumn<Libro, String> clCopertina2;

	@FXML
	private TableColumn<Libro, Float> clRatingG;

	@FXML
	private TableColumn<Libro, String> clGenere1;

	@FXML
	private TableColumn<Libro, String> clGenere2;

	@FXML
	private TableColumn<Libro, Integer> clRecensioniG;

	@FXML
	private TableColumn<Libro, String> clTitolo1;

	@FXML
	private TableColumn<Libro, String> clTitolo2;

	@FXML
	private TableColumn<Libro, String> clTitoloG;

	@FXML
	private TableView<Libro> table1;

	@FXML
	private TableView<Libro> table2;

	@FXML
	private TableView<Libro> tableGenera;

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
		table2.setItems(FXCollections.observableArrayList(model.getDaLeggere()));
	}

	@FXML
	void doAggiungiListaGen(ActionEvent event) {
		txtErroriGen.setVisible(false);
		txtErroriGen.clear();
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
		tableGenera.setItems(FXCollections.observableArrayList(model.getParziale()));
		cmbLibroGen.valueProperty().setValue(null);
	}

	@FXML
	void doGenera(ActionEvent event) {
		txtErroriGen.setVisible(false);
		txtErroriGen.clear();
		tableGenera.getItems().clear();
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

		List<Libro> lista = model.doRicorsione(budget, rating, recensioni, annoIGen, annoFGen, copertina, genere);
		tableGenera.setItems(FXCollections.observableArrayList(lista));
		if (model.bestSeller(lista) == null) {
			txtErroriGen.setVisible(true);
			txtErroriGen.setText("Non sono stati trovati libri con questi\nparametri");
		} else {
			txtNumLib.setText("Numero Libri: " + lista.size());
			txtCostoTot.setText("Costo totale: " + String.format("%.2f", model.totale(lista)));
			txtBestSeller.setText("Best Seller: \n" + model.bestSeller(lista));
			txtNumLib.setVisible(true);
			txtCostoTot.setVisible(true);
			txtBestSeller.setVisible(true);
		}
	}

	@FXML
	void doResetGenera(ActionEvent event) {
		model.cancellaBest();
		tableGenera.getItems().clear();
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
		txtNumLib.setVisible(false);
		txtCostoTot.setVisible(false);
		txtBestSeller.setVisible(false);
		txtErroriGen.clear();
		txtErroriGen.setVisible(false);
	}

	@FXML
	void doResetLeggi(ActionEvent event) {
		txtErrori.clear();
		txtErrori.setVisible(false);
		model.cancellaLista();
		cmbTitoloLeggiRimuovi.getItems().clear();
		table2.getItems().clear();
	}

	@FXML
	void doResetRicerca(ActionEvent event) {
		txtErrori.clear();
		txtErrori.setVisible(false);
		cmbTitoloLeggi.getItems().clear();
		table1.getItems().clear();
		cmbAutore.valueProperty().setValue(null);
		cmbAnnoI.valueProperty().setValue(null);
		cmbAnnoF.valueProperty().setValue(null);
	}

	@FXML
	void doRicerca(ActionEvent event) {
		txtErrori.clear();
		txtErrori.setVisible(false);
		cmbTitoloLeggi.getItems().clear();
		table1.getItems().clear();
		String aut = cmbAutore.getValue();
		Integer annoI = cmbAnnoI.getValue();
		Integer annoF = cmbAnnoF.getValue();
		List<Libro> trovati = new LinkedList<>();

		// controllo input

		if (aut == null && annoI == null && annoF == null) {
			txtErrori.setText("Seleziona l'autore e/o il range di anni desiderato");
			txtErrori.setVisible(true);
			return;
		}

		if (aut != null && annoI == null && annoF == null) {
			trovati.addAll(model.cercaLibriAutore(aut));
		} else if (aut == null && annoI != null && annoF != null) {
			if (annoF < annoI) {
				txtErrori.setVisible(true);
				txtErrori.appendText("Seleziona un range di anni valido\n");
				return;
			}
			trovati.addAll(model.cercaLibriAnno(annoI, annoF));
		} else {
			if (annoF < annoI) {
				txtErrori.setVisible(true);
				txtErrori.appendText("Seleziona un range di anni valido\n");
				return;
			}
			trovati.addAll(model.cercaLibri(aut, annoI, annoF));
			System.out.print(trovati.size());
		}
		// se lista vuota dico che non ho trovato niente
		if (trovati.isEmpty() == true) {
			txtErrori.setVisible(true);
			txtErrori.appendText("Non sono stati trovati libri di \n" + aut + " nel periodo: " + annoI + "-" + annoF);
			return;
		}
		table1.setItems(FXCollections.observableArrayList(trovati));
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
		table2.setItems(FXCollections.observableArrayList(model.getDaLeggere()));
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
		assert clAnno1 != null : "fx:id=\"clAnno1\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clAnno2 != null : "fx:id=\"clAnno2\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clAnnoG != null : "fx:id=\"clAnnoG\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clAutore1 != null : "fx:id=\"clAutore1\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clAutore2 != null : "fx:id=\"clAutore2\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clAutoreG != null : "fx:id=\"clAutoreG\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clPrezzo1 != null : "fx:id=\"clPrezzo1\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clPrezzo2 != null : "fx:id=\"clPrezzo2\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clPrezzoG != null : "fx:id=\"clPrezzoG\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clCopertina1 != null : "fx:id=\"clRating1\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clCopertina2 != null : "fx:id=\"clRating2\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clRatingG != null : "fx:id=\"clRatingG\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clGenere1 != null : "fx:id=\"clRecensioni1\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clGenere2 != null : "fx:id=\"clRecensioni2\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clRecensioniG != null : "fx:id=\"clRecensioniG\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clTitolo1 != null : "fx:id=\"clTitolo1\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clTitolo2 != null : "fx:id=\"clTitolo2\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clTitoloG != null : "fx:id=\"clTitoloG\" was not injected: check your FXML file 'Scene.fxml'.";
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
		assert table1 != null : "fx:id=\"table1\" was not injected: check your FXML file 'Scene.fxml'.";
		assert table2 != null : "fx:id=\"table2\" was not injected: check your FXML file 'Scene.fxml'.";
		assert tableGenera != null : "fx:id=\"tableGenera\" was not injected: check your FXML file 'Scene.fxml'.";
		assert tb != null : "fx:id=\"tb\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtBestSeller != null : "fx:id=\"txtBestSeller\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtBudget != null : "fx:id=\"txtBudget\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtCostoTot != null : "fx:id=\"txtCostoTot\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtErrori != null : "fx:id=\"txtErrori\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtErroriGen != null : "fx:id=\"txtErroriGen\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtNumLib != null : "fx:id=\"txtNumLib\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtNumReceMin != null : "fx:id=\"txtNumReceMin\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtRatingMin != null : "fx:id=\"txtRatingMin\" was not injected: check your FXML file 'Scene.fxml'.";

		clTitoloG.setCellValueFactory(new PropertyValueFactory<Libro, String>("titolo"));
		clAutoreG.setCellValueFactory(new PropertyValueFactory<Libro, String>("autore"));
		clAnnoG.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("anno"));
		clPrezzoG.setCellValueFactory(new PropertyValueFactory<Libro, Float>("prezzo"));
		clRatingG.setCellValueFactory(new PropertyValueFactory<Libro, Float>("rating"));
		clRecensioniG.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("numRecensioni"));

		clTitolo1.setCellValueFactory(new PropertyValueFactory<Libro, String>("titolo"));
		clAutore1.setCellValueFactory(new PropertyValueFactory<Libro, String>("autore"));
		clAnno1.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("anno"));
		clPrezzo1.setCellValueFactory(new PropertyValueFactory<Libro, Float>("prezzo"));
		clCopertina1.setCellValueFactory(new PropertyValueFactory<Libro, String>("tipoCopertina"));
		clGenere1.setCellValueFactory(new PropertyValueFactory<Libro, String>("genere"));

		clTitolo2.setCellValueFactory(new PropertyValueFactory<Libro, String>("titolo"));
		clAutore2.setCellValueFactory(new PropertyValueFactory<Libro, String>("autore"));
		clAnno2.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("anno"));
		clPrezzo2.setCellValueFactory(new PropertyValueFactory<Libro, Float>("prezzo"));
		clCopertina2.setCellValueFactory(new PropertyValueFactory<Libro, String>("tipoCopertina"));
		clGenere2.setCellValueFactory(new PropertyValueFactory<Libro, String>("genere"));
	}

}
