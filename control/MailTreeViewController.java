/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import application.FolderSelectionObservable;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import modell.Directory;

/**
 * FXML Controller class
 *
 * @author Tronico
 */
public class MailTreeViewController implements Initializable {

    @FXML
    private TreeView<Directory> navBaum;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Directory root = new Directory(new File("..//de.bht.fpa.mail.s814519_Teil6"));
        navBaum.setRoot(root);
        navBaum.getRoot().setExpanded(true);
        FolderSelectionObservable.getInstance(navBaum);       
    }
}
