/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.util.Observable;
import javafx.scene.control.TreeView;
import modell.Directory;

/**
 *
 * @author Tronico
 */
public class FolderSelectionObservable extends Observable {
    
    private static TreeView<Directory> treeView;
    private static FolderSelectionObservable instanceOfFSO;
    
    private FolderSelectionObservable(TreeView<Directory> treeView){
        FolderSelectionObservable.treeView =  treeView;
        treeView.getSelectionModel().selectedItemProperty().addListener((e) -> handleAusgewaehlt());
    }
    
    public static FolderSelectionObservable getInstance(TreeView<Directory> treeView){
        if (instanceOfFSO == null){
            instanceOfFSO = new FolderSelectionObservable(treeView);
        }
        return instanceOfFSO;
    }
    
    private void handleAusgewaehlt(){
        treeView.getSelectionModel().getSelectedItem().setExpanded(true);
        setChanged();
        notifyObservers(treeView);
    }
}
