/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import application.Resources;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tronico
 */
public class MailRootViewController implements Initializable {

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu filter;

    @FXML
    private Menu help;

    @FXML
    private Menu file;

    @FXML
    private Menu edit;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuBarConfig();
        menuAction();
    }

    private void menuBarConfig() {
        file.getItems().add(0, new MenuItem("Exit"));
        edit.getItems().add(0, new MenuItem("Set Base Path"));
        filter.getItems().add(0, new MenuItem("Set Filter"));
        help.getItems().add(0, new MenuItem("About"));
    }

    private void menuAction() {

        file.getItems().get(0).setOnAction((e) -> menuHandle(e));
        edit.getItems().get(0).setOnAction((e) -> dialogSBP());
        filter.getItems().get(0).setOnAction((e) -> dialogFilter());
        help.getItems().get(0).setOnAction((e) -> dialogAbout());
    }

    private void menuHandle(ActionEvent e) {

        MenuItem item = (MenuItem) e.getSource();
        if (item.getText().equals("Set Base Path")) dialogAbout();
        System.out.println(item.getText() + " wurde gedrueckt!");
    }
    
    private void dialogFilter(){
        if(Resources.getMailTableViewController().getTableContent().isEmpty()){
            alert("Keine Mails vorhanden");
            return;
        }
        Stage newStage = new Stage();
        try {
            Parent pane = FXMLLoader.load(getClass().getResource("/view/MailFilter.fxml"));
            newStage.setScene(new Scene(pane));
            newStage.setTitle("Set mail Filter");
            newStage.getIcons().add(new Image("resource/Stage_icon.png"));
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MailRootViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void dialogSBP(){
        Stage newStage = new Stage();
        try {
            Parent pane = FXMLLoader.load(getClass().getResource("/view/SetBasePath.fxml"));
            newStage.setScene(new Scene(pane));
            newStage.setTitle("Set new base path");
            newStage.getIcons().add(new Image("resource/Stage_icon.png"));
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MailRootViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void dialogAbout(){
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("resource/Stage_icon.png"));
            dialog.setTitle("FPA-Mailclient");
            dialog.setHeaderText("About");
            dialog.setContentText("Author:\tNico Nauendorf\n\nVersion:\t1\n\nE-Mail:\ts814519@beuthochschule.de");
            dialog.showAndWait();
    }
    
    private void alert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("resource/Stage_icon.png"));
        alert.setTitle("Info");
        Label label = (Label) alert.getDialogPane().getChildren().get(1);
        label.setAlignment(Pos.CENTER);
        alert.setContentText(text);
        alert.show();
    }
}
