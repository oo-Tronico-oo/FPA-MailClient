/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import control.MailTableViewController;
import control.MailTreeViewController;

/**
 *
 * @author Tronico
 */
public class ControllerInstanzen {
    
    private static MailTableViewController mailTableViewController;
    private static MailTreeViewController mailTreeViewController;
    private static ControllerInstanzen instanceOfResources;
    
    private ControllerInstanzen(MailTableViewController mailTableViewController){
        ControllerInstanzen.mailTableViewController = mailTableViewController;
    }
    private ControllerInstanzen(MailTreeViewController mailTreeViewController){
        ControllerInstanzen.mailTreeViewController = mailTreeViewController;
    }
    
    
    public static ControllerInstanzen getInstance(MailTableViewController mailTableViewController){
        if (instanceOfResources == null){
            if(mailTableViewController == null) throw new IllegalArgumentException("Versuch Resources zu initialisieren schlug fehl(MailTableViewController war \"null\")");
            instanceOfResources = new ControllerInstanzen(mailTableViewController);
        }else if(ControllerInstanzen.mailTableViewController == null){
            ControllerInstanzen.mailTableViewController = mailTableViewController;
        }
        return instanceOfResources;
    }
    
    public static ControllerInstanzen getInstance(MailTreeViewController mailTreeViewController){
        if (instanceOfResources == null){
            if(mailTreeViewController == null) throw new IllegalArgumentException("Versuch Resources zu initialisieren schlug fehl(MailTreeViewController war \"null\")");
            instanceOfResources = new ControllerInstanzen(mailTreeViewController);
        }else if(ControllerInstanzen.mailTreeViewController == null){
            ControllerInstanzen.mailTreeViewController = mailTreeViewController;
        }
        return instanceOfResources;
    }
    
    public static MailTableViewController getMailTableViewController(){
        return mailTableViewController;
    }
    
    public static MailTreeViewController getMailTreeViewController(){
        return mailTreeViewController;
    }
}
