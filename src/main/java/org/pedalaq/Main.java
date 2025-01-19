package org.pedalaq;

import jakarta.persistence.criteria.CriteriaDelete;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.hibernate.query.Query;

import org.pedalaq.Model.*;

import javax.persistence.NoResultException;
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
            //Citta LAquila = new Citta(42.3634408, 13.3445664, "L'Aquila");

            //Crea una nuova tariffa
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//FORMATO DATA NEL DB
            LocalDate date1 = LocalDate.of(2025, 1, 1);
            LocalDate date2 = LocalDate.of(2024, 12, 31);
            TariffaAbbonamento tariffadefault = new TariffaAbbonamento(5.00, date1, date2);

            //Crea un nuovo abbonamento
            LocalDate dateabb1 = LocalDate.of(2025, 1, 18);
            LocalDate dateabb2 = LocalDate.of(2024, 1, 28);
            //creo un abbonamento con la tariffa di default
            Abbonamento abbonamentoNadia = new Abbonamento(dateabb1, dateabb2, tariffadefault);
            newCittadino.setAbbonamentoAttivo(abbonamentoNadia); //setto l'abbonamento a Nadia

            //provo a creare uno stallo e ad inserirlo nella città
            //Stallo stallocoppito = new Stallo(42.3668,13.3359,10);
            //System.out.println(stallocoppito);
            //fin qui funziona, ora provo ad aggiungerlo alla città
            //LAquila.addStallo(stallocoppito);

            //Salvataggio degli oggetti creati nel DB
//            session.save(newCittadino);
//            session.save(stallocoppito);
//            session.save(LAquila);//RISULTA NECESSARIO CHE SIANO SALVATI PRIMA GLI STALLI E POI LA CITTA NEL DB
//            session.save(tariffadefault);
//            session.save(abbonamentoNadia);

            //TENTO IL RECUPERO DI UNA CITTà ED I RELATIVI STALLI DAL DB
            Query querycitta = session.createQuery("from Citta");
            List<Citta> cittaList = querycitta.list();
            for (Citta citta : cittaList) {
                System.out.println(citta.getNome());
                for (Stallo stalloacoppito : citta.getStalli()) {
                    System.out.println(stalloacoppito.getDescrizione()); //FUNZIONANTE
                }
            }

            // Creo una query per caricare tutti i cittadini del in DB in un array
            Query query = session.createQuery("from Cittadino");
            // Esecuzione della query e caricamento dei risultati in una Lista (non so perchè non ci sta arraylist)
            List<Cittadino> people = query.list();

            // Conversione della lista Hibernate in un ArrayList
            //ArrayList<Cittadino> allcittadini = new ArrayList<>(people);

            // Stampa dei risultati
//            System.out.println("I cittadini registrati sono:");
//            for (Cittadino cit : allcittadini) {
//                System.out.println(cit.getCognome()+" "+cit.getNome()+" "+cit.getCF()+" "+cit.getAbbonamentoAttivo());
//            }

            //QUERY DA USARE PER LO SCENARIO
            //possiamo pensare ad un "login" molto semplice come l'inserimento del codice fiscale
            Query<Cittadino> querylogin = session.createQuery("from Cittadino where CF = :cf", Cittadino.class);
            querylogin.setParameter("cf", "MZYNDA02P55A345F"); //questo lo prenderemo dall'input su console
            Cittadino loggato;
            //1)	L’utente apre l’applicazione dove ha già effettuato in precedenza il login.
            //SI SUPPONE CHE IL CITTADINO SIA GIà LOGGATO
            //QUINDI è PRESENTE IN MEMORIA L'OGGETTO CITTADINO
            try {
                loggato = querylogin.getSingleResult();
//                System.out.println(loggato.getCognome() + " " +
//                        loggato.getNome() + " " + loggato.getCF() + " " +
//                        loggato.getAbbonamentoAttivo());
                //2)	Il sistema verifica che l’utente abbia associato un abbonamento attivo al account.
                //L'oggetto cittadino contiene l'oggetto "abbonamento"
                //basta lanciare il metodo
                if (loggato.controllaAbbonamento()) //abbiamo un true se è valido e non scaduto
                {
                    //qui è il caso di successo
                    System.out.println("Abbonamento valido");
                }
                else System.out.println("Abbonamento non valido");//se è scaduto o non presente
            } catch (NoResultException e) {
                System.out.println("No match found.");
            } catch (NonUniqueResultException e) {
                System.out.println("Multiple matches found.");
            }

            // Commit della transazione
            session.getTransaction().commit();

            //System.out.println("Salvataggio effettuato con successo!");
        } finally {
            sessionFactory.close();
        }
    }
}