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
public class Resources {
    
    private static MailTableViewController mailTableViewController;
    private static MailTreeViewController mailTreeViewController;
    private static Resources instanceOfResources;
    
    private Resources(MailTableViewController mailTableViewController){
        Resources.mailTableViewController = mailTableViewController;
    }
    private Resources(MailTreeViewController mailTreeViewController){
        Resources.mailTreeViewController = mailTreeViewController;
    }
    
    
    public static Resources getInstance(MailTableViewController mailTableViewController){
        if (instanceOfResources == null){
            if(mailTableViewController == null) throw new IllegalArgumentException("Versuch Resources zu initialisieren schlug fehl(MailTableViewController war \"null\")");
            instanceOfResources = new Resources(mailTableViewController);
        }else if(Resources.mailTableViewController == null){
            Resources.mailTableViewController = mailTableViewController;
        }
        return instanceOfResources;
    }
    
    public static Resources getInstance(MailTreeViewController mailTreeViewController){
        if (instanceOfResources == null){
            if(mailTreeViewController == null) throw new IllegalArgumentException("Versuch Resources zu initialisieren schlug fehl(MailTreeViewController war \"null\")");
            instanceOfResources = new Resources(mailTreeViewController);
        }else if(Resources.mailTreeViewController == null){
            Resources.mailTreeViewController = mailTreeViewController;
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
