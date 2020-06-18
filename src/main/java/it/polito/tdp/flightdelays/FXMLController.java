package it.polito.tdp.flightdelays;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.flightdelays.model.Adiacenza;
import it.polito.tdp.flightdelays.model.Airline;
import it.polito.tdp.flightdelays.model.Model;
import it.polito.tdp.flightdelays.model.Passeggero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private ComboBox<Airline> cmbBoxLineaAerea;

    @FXML
    private Button caricaVoliBtn;

    @FXML
    private TextField numeroPasseggeriTxtInput;

    @FXML
    private TextField numeroVoliTxtInput;

    @FXML
    void doCaricaVoli(ActionEvent event) {
    	this.txtResult.clear();
    	Airline a = this.cmbBoxLineaAerea.getValue();
    	if(a == null) {
    		this.txtResult.setText("Scegli una compagnia aerea");
    		return;
    	}
    	this.model.createGraph(a);
    	this.txtResult.appendText("Grafo creato con : \n");
    	this.txtResult.appendText(String.format("#Archi: %d\n#Vertici: %d\n",this.model.nArchi(),this.model.nVertici()));

    	List<Adiacenza> peggiori = this.model.peggiori();
    	for(int i =0 ;i<10;i++) {
    		this.txtResult.appendText(peggiori.get(i)+"\n");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	Integer K;
    	Integer V;
    	try {
    		K = Integer.parseInt(this.numeroPasseggeriTxtInput.getText());
    		V = Integer.parseInt(this.numeroVoliTxtInput.getText());
    		this.model.simula(K,V);
    		Collection<Passeggero> passeggeri = this.model.getPasseggeri();
    		for(Passeggero p : passeggeri) {
    			this.txtResult.appendText(p.toString()+"\n");
    		}
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci valori corretti");
    		return;
    	}
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert cmbBoxLineaAerea != null : "fx:id=\"cmbBoxLineaAerea\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert caricaVoliBtn != null : "fx:id=\"caricaVoliBtn\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert numeroPasseggeriTxtInput != null : "fx:id=\"numeroPasseggeriTxtInput\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'FlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.cmbBoxLineaAerea.getItems().addAll(this.model.getAirlines());
	}
}
