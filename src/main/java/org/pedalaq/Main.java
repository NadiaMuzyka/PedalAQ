package org.pedalaq;

import org.pedalaq.Model.*;
import org.pedalaq.Services.HibernateUtil;
import org.pedalaq.View.ConsoleManager;


public class Main {
    public static void main(String[] args) {
        Citta citta_sel = HibernateUtil.findByParameter(Citta.class,"nome","L'Aquila");
        Cittadino loggato = HibernateUtil.findByParameter(Cittadino.class,"CF","MZYNDA02P55A345F");
        ConsoleManager consoleManager = new ConsoleManager();
        consoleManager.start(citta_sel,loggato);
    }
}