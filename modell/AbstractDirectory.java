/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modell;

import java.io.File;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Tronico
 */
public abstract class AbstractDirectory extends TreeItem {

    protected File file;

    public AbstractDirectory(File file) {

        super(file.getName(), new ImageView(new Image("/resource/OrdnerRootIcon.png")));
        this.file = file;
    }

    @Override
    public boolean isLeaf() {
        if (file.isFile()) {
            return true;
        }

        boolean subfolderExists = false;
        File[] fileListe = file.listFiles();
        if (fileListe != null) {
            for (File f : fileListe) {
                if (f.isDirectory()) {
                    subfolderExists = true;
                }
            }
        }
        return !subfolderExists;
    }
}
