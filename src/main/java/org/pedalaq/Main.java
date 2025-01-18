package org.pedalaq;

import jakarta.persistence.criteria.CriteriaDelete;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.hibernate.query.Query;




import org.pedalaq.Model.Abbonamento;
import org.pedalaq.Model.Citta;
import org.pedalaq.Model.Cittadino;
import org.pedalaq.Model.TariffaAbbonamento;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

            // Crea una nuova città
            Citta LAquila = new Citta(42.3634408, 13.3445664,"L'Aquila");

            //Crea una nuova tariffa
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//FORMATO DATA NEL DB
            LocalDate date1 = LocalDate.of(2025, 1, 1);
            LocalDate date2 = LocalDate.of(2024, 12, 31);
            TariffaAbbonamento tariffadefault = new TariffaAbbonamento(5.00,date1,date2);

            //Crea un nuovo abbonamento
            LocalDate dateabb1 = LocalDate.of(2025, 1, 18);
            LocalDate dateabb2 = LocalDate.of(2024, 1, 28);
            //creo un abbonamento con la tariffa di default
            Abbonamento abbonamentoNadia = new Abbonamento(dateabb1,dateabb2,tariffadefault);
            newCittadino.setAbbonamentoAttivo(abbonamentoNadia); //setto l'abbonamento a Nadia

            //Salvataggio degli oggetti creati nel DB
//            session.save(newCittadino);
//            session.save(LAquila);
//            session.save(tariffadefault);
//            session.save(abbonamentoNadia);

            // Creo una query per caricare tutti i cittadini del in DB in un array
            Query query = session.createQuery("from Cittadino");
            // Esecuzione della query e caricamento dei risultati in una Lista (non so perchè non ci sta arraylist)
            List<Cittadino> people = query.list();

            // Commit della transazione
            session.getTransaction().commit();

            // Conversione della lista Hibernate in un ArrayList
            ArrayList<Cittadino> allcittadini = new ArrayList<>(people);

            // Stampa dei risultati
            System.out.println("I cittadini registrati sono:");
            for (Cittadino cit : allcittadini) {
                System.out.println(cit.getCognome()+" "+cit.getNome()+" "+cit.getCF());
            }


            //System.out.println("Salvataggio effettuato con successo!");
        } finally {
            sessionFactory.close();
        }
    }
}