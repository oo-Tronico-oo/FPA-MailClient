/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modell;

import Message.Message;
import application.DateiVerwaltung;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Tronico
 */
public class IsSubjectFilter extends Filter {

    public IsSubjectFilter(DateiVerwaltung dateiVerwaltung, String filterCriteria) {
        super(dateiVerwaltung, filterCriteria);
    }

    @Override
    public List getMessages() {
        Iterator it = dateiverwaltung.getMessageListe().iterator();
        List<Message> messageList = new ArrayList<>();
        while(it.hasNext()){
            Message m = (Message)it.next();
            if(m.getSubject().equals(filterCriteria))messageList.add(m);
        }
        if(messageList.isEmpty())return null;
        return messageList;
    }
    
}
