package org.pedalaq;

import org.hibernate.NonUniqueResultException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.pedalaq.Model.*;
import org.pedalaq.Services.HibernateUtil;

import javax.persistence.NoResultException;
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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


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
                //System.out.println(citta.getNome());
                for (Stallo stalloacoppito : citta.getStalli()) {
                    //System.out.println(stalloacoppito.getDescrizione()); //FUNZIONANTE
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
            Cittadino loggato = new Cittadino();
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
                    //System.out.println("Abbonamento valido");
                }
                //else System.out.println("Abbonamento non valido");//se è scaduto o non presente
            } catch (NoResultException e) {
                //System.out.println("No match found.");
            } catch (NonUniqueResultException e) {
                //System.out.println("Multiple matches found.");
            }
            Bici bici1 = new Bici();
            bici1.setStato("Libero");
            bici1.setCodiceSblocco("AA22CC");
            BiciElettrica biciel1 = new BiciElettrica();
            biciel1.setStato("Libero");
            biciel1.setCodiceSblocco("AA22CC");
            biciel1.setBatteria(78);
            Monopattino mono1 = new Monopattino();
            mono1.setStato("Libero");
            mono1.setCodiceSblocco("AA22CC");
            mono1.setBatteria(99);


            Citta AQ = cittaList.get(0);
            Stallo stallo1 = AQ.getStalli().get(0);
            stallo1.addVeicolo(bici1);
            stallo1.addVeicolo(biciel1);
            stallo1.addVeicolo(mono1);

            session.save(bici1);
            session.save(biciel1);
            session.save(mono1);
            session.save(stallo1);


            session.getTransaction().commit();
            //TESTING
            //La prima query dal DB prende la città e a cascata carica
            //i suoi stalli
            //i suoi veicoli
            //i relativi accessori
            //le sue tariffe (tabella unica per i 2 tipi)
            //i noleggi collegati alle tariffe
            //le relative prenotazioni
            //se lasciamo così una query che prende una città caricherà tutto ciò a cui è correlata
//            Citta AQ = cittaList.get(0);
            //test localizzazione
//            System.out.println("Posizione cittadino Lat: "+ loggato.getLat()+ " Lng: "+loggato.getLng());
//            for (Stallo stallo: AQ.getStalli()){
//                System.out.println("Posizione STALLO Lat: "+ stallo.getLat()+ " Lng: "+stallo.getLon());
//            }
//            System.out.println("Stalli localizzati:");
//            List<Stallo> stallilocalizzati = AQ.getStalliRaggio(loggato.getLat(),loggato.getLng(),300);
//            for (Stallo stallo: stallilocalizzati){
//                System.out.println("Posizione STALLO LOCALIZZATO Lat: "+ stallo.getLat()+ " Lng: "+stallo.getLon() +
//                                    " "+ stallo.getDescrizione());
//            }
            //System.out.println(AQ.getStalliRaggio(loggato.getLat(),loggato.getLng(),1000));


















            // Commit della transazione
            //session.getTransaction().commit();

            //System.out.println("Salvataggio effettuato con successo!");
        } finally {
            sessionFactory.close();
        }
    }
}