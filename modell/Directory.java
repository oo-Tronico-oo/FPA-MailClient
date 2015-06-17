/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modell;

import java.io.File;
import javafx.collections.ObservableList;

/**
 *
 * @author Tronico
 */
public class Directory extends AbstractDirectory {

    private boolean isFirstTimeChildren = true;

    public Directory(File root) {
        super(root);
    }

    @Override
    public ObservableList getChildren() {
        ObservableList childList = super.getChildren();
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;
            if (file != null && file.isDirectory()) {
                File[] fileList = file.listFiles();

                if (fileList != null) {
                    for (File f : fileList) {
                        if (f.isDirectory()) {
                            childList.add(new Directory(f));
                        }
                    }
                }
            }
        }
        return childList;
    }

    public File getFile() {
        return file;
    }
}
