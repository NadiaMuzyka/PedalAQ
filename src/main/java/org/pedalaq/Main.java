package org.pedalaq;

import org.pedalaq.Model.*;
import org.pedalaq.Services.HibernateUtil;
import org.pedalaq.View.ConsoleManager;


public class Main {
    public static void main(String[] args) {

        /*   ESEMPIO DI MAIN FINALE
        public class Main {
            public static void main(String[] args) {
                ConsoleManager consoleManager = new ConsoleManager();
                consoleManager.start();
                }
        }
         */

        // Per il test e la ricreazione del db
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        try (Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            session.getTransaction().commit();
//        }
//        TariffaNoleggioPromozione tariffa = new TariffaNoleggioPromozione();
//
//        HibernateUtil.saveOrUpdateWithTransaction(tariffa);

        //Prenotazione prenotazione_da_controllare = HibernateUtil.findByParameter(Prenotazione.class, "id", 1);
        //Citta citta_sel = HibernateUtil.getcittafromnome("L'Aquila");
        Citta citta_sel = HibernateUtil.findByParameter(Citta.class,"nome","L'Aquila");
        //long id = 1;
        //System.out.println(citta_sel.stallo_by_id(id).getVeicoli() + "aaa");
        //System.out.println(citta_sel.stallo_by_id(id));
        //System.out.println(citta_sel.getStalli());
        //System.out.println(citta_sel.getTariffa_standard());
        //ATTUALMENTE NON PRESENTE CONTROLLO SULL'ABBONAMENTO
        //Cittadino loggato = HibernateUtil.getcittadinofromcf("MZYNDA02P55A345F");
        Cittadino loggato = HibernateUtil.findByParameter(Cittadino.class,"CF","MZYNDA02P55A345F");
        //System.out.println(loggato.getNoleggi());
        ConsoleManager consoleManager = new ConsoleManager();
        consoleManager.start(citta_sel,loggato);

    }
}