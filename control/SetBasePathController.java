/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tronico
 */
public class SetBasePathController implements Initializable {

    @FXML
    private Button cancel;

    @FXML
    private Button ok;

    @FXML
    private TextField textField;
    
    private final File file = new File("src\\application", "settings.txt");
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        textField.setText(oldPath());
        cancel.setOnAction((e) -> ((Stage) cancel.getScene().getWindow()).close());
        ok.setOnAction((e) -> okButton());
    }
    
    private void okButton(){
        File f = new File(textField.getText());
        if (f.exists()) save();
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("resource/Stage_icon.png"));
            alert.setTitle("Path fail");
            Label label = (Label)alert.getDialogPane().getChildren().get(1);
            label.setAlignment(Pos.CENTER);
            alert.setContentText("Dieses Verzeichnis existiert nicht");
            alert.show();
        }
    }
    
    private String oldPath(){
        
        BufferedReader br = null;
        String path = "";
        
        try {
            br = new BufferedReader(new FileReader(file));
            path = br.readLine();
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SetBasePathController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SetBasePathController.class.getName()).log(Level.SEVERE, null, ex);
            try {
                br.close();
            } catch (IOException ex1) {
                Logger.getLogger(SetBasePathController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return path;
    }
    
    private void save(){
        
        BufferedWriter bw;
        
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(textField.getText());
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(SetBasePathController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ((Stage) cancel.getScene().getWindow()).close();
    }
}
