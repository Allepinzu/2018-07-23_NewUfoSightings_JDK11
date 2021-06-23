package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare al branch master_turnoB per turno B

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtAnno;

    @FXML
    private Button btnSelezionaAnno;

    @FXML
    private ComboBox<String> cmbBoxForma;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private TextField txtT1;

    @FXML
    private TextField txtAlfa;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	model.creaGrafo(Integer.parseInt(this.txtAnno.getText()), this.cmbBoxForma.getValue());
    	this.txtResult.clear();
    	this.txtResult.appendText(model.VA()+"\n");
    	this.txtResult.appendText(model.stampaVicini());
    }

    @FXML
    void doSelezionaAnno(ActionEvent event) {
    	
        try{
      	  int n = Integer.parseInt(this.txtAnno.getText());
        
        if(n<1910||n>2015) {
      	  this.txtResult.setText("Inserisci un anno valido");
        }
        else {
      	  this.cmbBoxForma.getItems().addAll(model.listaForme(n));
      	  
        }
        
        }catch(NumberFormatException ne) {
          	  this.txtResult.setText("Inserisci un anno valido");
          	  
          	  
            }
    }

    @FXML
    void doSimula(ActionEvent event) {
    model.init(Integer.parseInt(this.txtAnno.getText()),this.cmbBoxForma.getValue(),Integer.parseInt(this.txtAlfa.getText() ));
      model.run();
      this.txtResult.clear();
      this.txtResult.appendText(this.model.pericolo());
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnSelezionaAnno != null : "fx:id=\"btnSelezionaAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert cmbBoxForma != null : "fx:id=\"cmbBoxForma\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtAlfa != null : "fx:id=\"txtAlfa\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
