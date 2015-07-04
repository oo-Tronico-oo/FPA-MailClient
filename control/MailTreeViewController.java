/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import application.FolderSelectionObservable;
import application.ControllerInstanzen;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modell.Directory;

/**
 * FXML Controller class
 *
 * @author Tronico
 */
public class MailTreeViewController implements Initializable {

    @FXML
    private TreeView<Directory> navBaum;
    
    private final File file = new File("src\\application", "settings.txt");
    
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Directory root = new Directory(new File(getPath()));
        navBaum.setRoot(root);
        navBaum.getRoot().setExpanded(true);
        FolderSelectionObservable.getInstance(navBaum);
        ControllerInstanzen.getInstance(this);
    }
    
    public TreeView<Directory> getNavBaum(){
        return navBaum;
    }
    
    private String getPath(){
        
        BufferedReader br = null;
        String path = "";
        
        try {
            br = new BufferedReader(new FileReader(file));
            path = br.readLine();
            br.close();
        } catch (FileNotFoundException ex) {
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("resource/Stage_icon.png"));
            alert.setTitle("settings fail");
            Label label = (Label)alert.getDialogPane().getChildren().get(1);
            label.setAlignment(Pos.CENTER);
            alert.setContentText("Die Settingsdatei fehlt. Erzeuge sie durch setzen des Rootpath über Menuebar --> Edit --> Set Base Path");
            alert.setY(Toolkit.getDefaultToolkit().getScreenSize().height - 250);
            alert.setX(Toolkit.getDefaultToolkit().getScreenSize().width - 400);
            alert.show();
            
            return ("src");
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
}
