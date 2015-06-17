/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tronico
 */
public class MailFilterController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBox1;

    @FXML
    private Button buttonFilter;

    @FXML
    private TextField filterTextField;

    @FXML
    private Button addFilter;

    @FXML
    private ChoiceBox<String> choiceBox2;

    @FXML
    private VBox vBoxFilter;

    @FXML
    private Button buttonAbbrechen;

    private int filterAnzahl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        buttonAbbrechen.setOnAction((e) -> ((Stage) buttonAbbrechen.getScene().getWindow()).close());
        buttonFilter.setOnAction((e) -> filterAusgabe());
        ersteFilterZeile();
    }

    private void ersteFilterZeile() {
        filterTextField.setPromptText("John.Dow@web.de");
        choiceBox1.getItems().addAll("From", "Read", "Priority");
        choiceBox2.getItems().add("is");
        addFilter.setOnAction((e) -> newFilterZeile());
        filterAnzahl = 1;
    }

    private void newFilterZeile() {
        if (filterAnzahl < 5) {
            vBoxFilter.getChildren().add(newHBox());
            filterAnzahl++;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Maximale Anzahl an Filtern erreicht!");
            alert.show();
        }
    }

    private HBox newHBox() {
        HBox hb = new HBox();
        
        Label platzHalter1 = new Label();
        platzHalter1.setPrefWidth(10);
        Label platzHalter2 = new Label();
        platzHalter2.setPrefWidth(10);
        Label platzHalter3 = new Label();
        platzHalter3.setPrefWidth(10);
        
        ChoiceBox<String> choiceBoxOne = new ChoiceBox<>();
        choiceBoxOne.getItems().addAll("From", "Read", "Priority");
        choiceBoxOne.setPrefWidth(100);
        
        ChoiceBox<String> choiceBoxTwo = new ChoiceBox<>();
        choiceBoxTwo.getItems().add("is");
        choiceBoxTwo.setPrefWidth(130);
        
        TextField filterTextFieldNew = new TextField();
        filterTextFieldNew.setPromptText("John.Dow@web.de");
        filterTextFieldNew.setPrefWidth(250);
        
        Button b = new Button();
        b.setText("+");
        b.setOnAction((e) -> newFilterZeile());
        hb.setSpacing(10);
        
        hb.getChildren().addAll(choiceBoxOne, platzHalter1, choiceBoxTwo, platzHalter2, filterTextFieldNew, platzHalter3, b);
        hb.setPrefHeight(30);
        return hb;
    }

    private void filterAusgabe() {
        int element = 0;
        Iterator it = vBoxFilter.getChildren().iterator();
        while(it.hasNext()){
            HBox hb = (HBox)it.next();
            String box1 = (String) ((ChoiceBox)hb.getChildren().get(0)).getValue();
            String box2 = (String) ((ChoiceBox)hb.getChildren().get(2)).getValue();
            String box3 = ((TextField)hb.getChildren().get(4)).getText();
            
            System.out.println("Element at col, row: 0, " + element + " has value: " + box1);
            System.out.println("Element at col, row: 1, " + element + " has value: " + box2);
            System.out.println("Element at col, row: 2, " + element + " has value: " + box3);
            System.out.println();
            element ++;
        }
    }
}
