/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Message.Message;
import application.DateiVerwaltung;
import application.ControllerInstanzen;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modell.ContainsSenderMailFilter;
import modell.Directory;
import modell.Filter;
import modell.IsSubjectFilter;

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
        choiceBox1.getItems().addAll("Sender", "Subject");
        setChoiceBoxListener(choiceBox1, choiceBox2);
        addFilter.setOnAction((e) -> newFilterZeile());
        filterAnzahl = 1;
    }
    
    private void setChoiceBoxListener(ChoiceBox boxForListener, ChoiceBox boxForChange){
        boxForListener.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if(boxForListener.getItems().get((Integer) number2).equals("Sender")){
                    boxForChange.getItems().clear();
                    boxForChange.getItems().add("contains");
                }else{
                    boxForChange.getItems().clear();
                    boxForChange.getItems().add("is");
                }
            }
        });
    }
    
    private void newFilterZeile() {
        if (filterAnzahl < 5) {
            vBoxFilter.getChildren().add(newHBox());
            filterAnzahl++;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Maximale Anzahl an Filtern erreicht!");
            alert.show();
        }
    }

    private HBox newHBox() {
        HBox hb = new HBox();

        ChoiceBox<String> choiceBoxOne = new ChoiceBox<>();
        choiceBoxOne.getItems().addAll("Sender", "Subject");
        choiceBoxOne.setPrefWidth(100);

        ChoiceBox<String> choiceBoxTwo = new ChoiceBox<>();
        choiceBoxTwo.setPrefWidth(130);
        
        setChoiceBoxListener(choiceBoxOne, choiceBoxTwo);

        TextField filterTextFieldNew = new TextField();
        filterTextFieldNew.setPromptText("John.Dow@web.de");
        filterTextFieldNew.setPrefWidth(250);

        Button addButton = new Button();
        addButton.setText("+");
        addButton.setOnAction((e) -> newFilterZeile());

        hb.getChildren().addAll(choiceBoxOne, choiceBoxTwo, filterTextFieldNew, addButton);
        hb.setPrefHeight(30);
        hb.setSpacing(10);
        return hb;
    }

    private void filterAusgabe() {
//        int element = 0;

        List<Message> messageList = new ArrayList<>();

        TreeView<Directory> treeView = ControllerInstanzen.getMailTreeViewController().getNavBaum();
        TreeItem<Directory> treeItem = treeView.getSelectionModel().getSelectedItem();
        Directory folder = (Directory) treeItem;
        String path = folder.getFile().getPath();
        
        DateiVerwaltung dateiVerwaltung = new DateiVerwaltung(path);

        Filter filter = null;
        Iterator it = vBoxFilter.getChildren().iterator();
        while (it.hasNext()) {
            HBox hb = (HBox) it.next();
            String box1 = (String) ((ChoiceBox) hb.getChildren().get(0)).getValue();
            String box2 = (String) ((ChoiceBox) hb.getChildren().get(1)).getValue();
            String box3 = ((TextField) hb.getChildren().get(2)).getText();
            box3 = box3.trim();
            if (box1 == null || box2 == null || box3.isEmpty()) {
                alert();
                continue;
            }
            if (box1.equals("Sender") && box2.equals("contains")) {
                filter = new ContainsSenderMailFilter(dateiVerwaltung, box3);
            } else if (box1.equals("Subject") && box2.equals("is")) {
                filter = new IsSubjectFilter(dateiVerwaltung, box3);

            }
            if (filter != null) {
                messageList.addAll(filter.getMessages());
            }

//            System.out.println("Element at col, row: 0, " + element + " has value: " + box1);
//            System.out.println("Element at col, row: 1, " + element + " has value: " + box2);
//            System.out.println("Element at col, row: 2, " + element + " has value: " + box3);
//            System.out.println();
//            element ++;
        }
        ControllerInstanzen.getMailTableViewController().updateTabelle(messageList);
        ((Stage) buttonFilter.getScene().getWindow()).close();
    }

    private void alert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("resource/Stage_icon.png"));
        alert.setTitle("Info");
        Label label = (Label) alert.getDialogPane().getChildren().get(1);
        label.setAlignment(Pos.CENTER);
        alert.setContentText("Ein Filter wurde nicht vollständig ausgefüllt und daher ausgelassen");
        alert.show();
    }
}
