/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modell;

import application.DateiVerwaltung;
import java.util.List;

/**
 *
 * @author Tronico
 */
public abstract class Filter {
    protected DateiVerwaltung dateiverwaltung;
    protected String filterCriteria;
    
    public Filter(DateiVerwaltung dateiVerwaltung, String filterCriteria){
        this.dateiverwaltung = dateiVerwaltung;
        this.filterCriteria = filterCriteria;
    }
    
    public abstract List getMessages();
}
