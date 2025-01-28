package org.pedalaq;

import jakarta.transaction.UserTransaction;
import org.hibernate.NonUniqueResultException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.pedalaq.Model.*;
import org.pedalaq.Services.HibernateUtil;
import org.pedalaq.View.ConsoleManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
        //System.out.println(citta_sel + "aaa");
        System.out.println(citta_sel.getTariffa_standard());
        //ATTUALMENTE NON PRESENTE CONTROLLO SULL'ABBONAMENTO
        //Cittadino loggato = HibernateUtil.getcittadinofromcf("MZYNDA02P55A345F");
        Cittadino loggato = HibernateUtil.findByParameter(Cittadino.class,"CF","MZYNDA02P55A345F");
        //System.out.println(loggato + "bbb");
        ConsoleManager consoleManager = new ConsoleManager();
        consoleManager.start(citta_sel,loggato);

    }
}