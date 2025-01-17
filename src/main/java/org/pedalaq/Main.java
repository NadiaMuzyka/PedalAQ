package org.pedalaq;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.pedalaq.Model.Citta;
import org.pedalaq.Model.Cittadino;

public class Main {
    public static void main(String[] args) {
        // Creazione della SessionFactory
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml") // Specifica il file di configurazione
                .addAnnotatedClass(Cittadino.class) // Registra la classe Entity
                .buildSessionFactory();

        // Creazione della Session
        try (Session session = sessionFactory.openSession()) {
            // Avvia una transazione
            session.beginTransaction();

            // Crea un nuovo utente
            Cittadino newCittadino = new Cittadino("Nadia", "Muzyka", "MZYNDA02P55A345F");

            //nuova città
            Citta LAquila = new Citta(42.3634408, 13.3445664);
            // Salva l'utente nel database
            session.save(newCittadino);
            session.save(LAquila);

            // Commit della transazione
            session.getTransaction().commit();

            System.out.println("Cittadino salvato con successo!");
        } finally {
            sessionFactory.close();
        }
    }
}