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

        // Creazione della SessionFactory
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Citta citta_sel = HibernateUtil.getcittafromnome("L'Aquila");
        //System.out.println(citta_sel + "aaa");
        Cittadino loggato = HibernateUtil.getcittadinofromcf("MZYNDA02P55A345F");
        //System.out.println(loggato + "bbb");
        ConsoleManager consoleManager = new ConsoleManager();
        consoleManager.start(citta_sel,loggato);
        //sessionFactory.close();
    }
}