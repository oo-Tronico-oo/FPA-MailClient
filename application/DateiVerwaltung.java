/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import Message.Message;
import Message.MessageImportance;
import Message.MessageStakeholder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Tronico
 */
public class DateiVerwaltung {

    private List messageListe = new ArrayList();
    private String path;
    private JAXBContext context = null;

    public DateiVerwaltung(String path) {
        this.path = path;
        try {
            context = JAXBContext.newInstance(Message.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DateiVerwaltung.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public List getMessageListe() {
        messageListe.clear();
        File file = new File(path);
        File[] fileListe = file.listFiles();
        for(File f : fileListe){
            Message m = null;
            if(f.getName().endsWith(".xml"))m = xmlDateiLaden(f);
             if(m != null) messageListe.add(m);
        }
        return messageListe;
    }
    
    public void setPath(String path){
        this.path = path;
    }

    public void xmlDateiSpeichern(Message message) {
        Writer writer = null;
        try {
            File file = new File(path, message.getId() + ".xml");
            writer = new FileWriter(file);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(message, writer);
        } catch (IOException | JAXBException ex) {
            Logger.getLogger(DateiVerwaltung.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(DateiVerwaltung.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    

    private Message xmlDateiLaden(File file) {
        Reader reader = null;
        try {
            reader = new FileReader(file);
            
            Unmarshaller um = context.createUnmarshaller();
            Message message;
            message = (Message) um.unmarshal(reader);
            return message;
        } catch (JAXBException | FileNotFoundException ex) {
            Logger.getLogger(DateiVerwaltung.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(DateiVerwaltung.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    private Message xmlDateiLesen(File file) {
        Reader reader;
        Message message = new Message();
        try {
            reader = new FileReader(file);
            BufferedReader rb = new BufferedReader(reader);
            rb.readLine();
            rb.readLine();
            message.setId(content(rb.readLine()));
            message.setImportanceOfMessage(MessageImportance.valueOf(content(rb.readLine())));
            message.setReadStatus(Boolean.parseBoolean(content(rb.readLine())));
            message.setReceivedAt(LocalDateTime.parse(content(rb.readLine())));
            rb.readLine();
            rb.readLine();
            MessageStakeholder recip = new MessageStakeholder();
            recip.setMailAddress(content(rb.readLine()));
            recip.setName(content(rb.readLine()));
            message.getRecipients().add(recip);
            rb.readLine();
            rb.readLine();
            recip.setMailAddress(content(rb.readLine()));
            recip.setName(content(rb.readLine()));
            message.getRecipients().add(recip);
            rb.readLine();
            rb.readLine();
            rb.readLine();
            recip = new MessageStakeholder();
            recip.setMailAddress(content(rb.readLine()));
            recip.setName(content(rb.readLine()));
            message.setSender(recip);
            rb.readLine();
            message.setSubject(content(rb.readLine()));
            message.setText(content(rb.readLine()));
        } catch (IOException ex) {
            Logger.getLogger(DateiVerwaltung.class.getName()).log(Level.SEVERE, null, ex);
        }
            return message;
    }
    
    private String content(String string){
        String[] a = string.split(">");
        String[] b = a[1].split("<");
        return b[0];
    }
    
    
}
