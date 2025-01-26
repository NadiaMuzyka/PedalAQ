package org.pedalaq.Services;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.pedalaq.Model.*;

import javax.persistence.NoResultException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Properties password = HibernateUtil.getPassword();
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration()
                    .configure("hibernate.cfg.xml") // Specifica il file di configurazione
                    .addProperties(password)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    //Recupero password da config.properties
    private static Properties getPassword() {
        Properties properties = new Properties();
        try (InputStream input = HibernateUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("File config.properties non trovato nel classpath!");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il caricamento delle proprietà: " + e.getMessage(), e);
        }
        return properties;
    }

    public static void saveprenotazione_bloccaveicolo(Prenotazione prenotazione, Veicolo veicolo) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(veicolo); //merge è l'update
            session.merge(prenotazione);
            session.getTransaction().commit();
        }

    }

    public static void savenoleggio_noleggiaaveicolo(Noleggio noleggio, Veicolo veicolo, Prenotazione prenotazione) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(veicolo); //merge è l'update
            //session.delete(prenotazione); //TODO da sistemare l'integrità referenziale
            session.save(noleggio);
            session.getTransaction().commit();
        }

    }

//    public static Citta getcittafromnome(String nome) {
//        Citta citta_sel = new Citta();
//        //SessionFactory sessionFactory1 = HibernateUtil.getSessionFactory();
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        try (Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            Query<Citta> querycitta = session.createQuery("from Citta where nome = :nome", Citta.class);
//            querycitta.setParameter("nome", nome);
//            //Citta citta_sel = new Citta();
//            //session.getTransaction().commit();
//            try {
//                citta_sel = querycitta.getSingleResult();
//                session.getTransaction().commit();
////              System.out.println();
//            } catch (NoResultException e) {
//                session.getTransaction().commit();
//                //System.out.println("No match found.");
//            } catch (NonUniqueResultException e) {
//                session.getTransaction().commit();
//                //System.out.println("Multiple matches found.");
//            }
//        }
//        finally {
//            //sessionFactory.close();
//        }
//        return citta_sel;
//    }


    //qui ci sarebbe solo da checkare l'abbonamento dopo l'ist
//    public static Cittadino getcittadinofromcf(String CF) {
//        Cittadino cittadino_sel = new Cittadino();
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        try (Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            Query<Cittadino> querylogin = session.createQuery("from Cittadino where CF = :CF", Cittadino.class);
//            querylogin.setParameter("CF", CF);
//
//            try {
//                cittadino_sel = querylogin.getSingleResult();
////                System.out.println(loggato.getCognome() + " " +
////                        loggato.getNome() + " " + loggato.getCF() + " " +
////                        loggato.getAbbonamentoAttivo());
//                //2)	Il sistema verifica che l’utente abbia associato un abbonamento attivo al account.
//                //L'oggetto cittadino contiene l'oggetto "abbonamento"
//                //basta lanciare il metodo
//                if (cittadino_sel.controllaAbbonamento()) //abbiamo un true se è valido e non scaduto
//                {
//                    //qui è il caso di successo
//                    //System.out.println("Abbonamento valido");
//                }
//                session.getTransaction().commit();
//                //else System.out.println("Abbonamento non valido");//se è scaduto o non presente
//            } catch (NoResultException e) {
//                session.getTransaction().commit();
//                //System.out.println("No match found.");
//            } catch (NonUniqueResultException e) {
//                session.getTransaction().commit();
//                //System.out.println("Multiple matches found.");
//            }
//        }
//        finally {
//            //sessionFactory.close();
//        }
//        return cittadino_sel;
//    }

//    public static Prenotazione getprenotazionefromid(Long id) {
//        Prenotazione prenotazione_sel = new Prenotazione();
//        //SessionFactory sessionFactory1 = HibernateUtil.getSessionFactory();
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        try (Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            Query<Prenotazione> queryprenotazione = session.createQuery("from Prenotazione where id = :id", Prenotazione.class);
//            queryprenotazione.setParameter("id", id);
//            //Citta citta_sel = new Citta();
//            //session.getTransaction().commit();
//            try {
//                prenotazione_sel = queryprenotazione.getSingleResult();
//                session.getTransaction().commit();
////              System.out.println();
//            } catch (NoResultException e) {
//                session.getTransaction().commit();
//                //System.out.println("No match found.");
//            } catch (NonUniqueResultException e) {
//                session.getTransaction().commit();
//                //System.out.println("Multiple matches found.");
//            }
//        }
//        finally {
//            //sessionFactory.close();
//        }
//        return prenotazione_sel;
//    }

    public static Prenotazione getprenotazionefromidveicolo(Long id_veicolo) {
        Prenotazione prenotazione_sel = new Prenotazione();
        //SessionFactory sessionFactory1 = HibernateUtil.getSessionFactory();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Prenotazione> queryprenotazione = session.createQuery("from Prenotazione where id = :id_veicolo", Prenotazione.class);
            queryprenotazione.setParameter("id_veicolo", id_veicolo);
            //Citta citta_sel = new Citta();
            //session.getTransaction().commit();
            try {
                prenotazione_sel = queryprenotazione.getSingleResult();
                session.getTransaction().commit();
//              System.out.println();
            } catch (NoResultException e) {
                session.getTransaction().commit();
                //System.out.println("No match found.");
            } catch (NonUniqueResultException e) {
                session.getTransaction().commit();
                //System.out.println("Multiple matches found.");
            }
        }
        finally {
            //sessionFactory.close();
        }
        return prenotazione_sel;
    }

        /**
         * Metodo generalizzato per eseguire una query Hibernate con un parametro dinamico.
         *
         * @param entityClass  La classe dell'entità da interrogare.
         * @param paramName    Il nome del parametro nella query.
         * @param paramValue   Il valore del parametro.
         * @param <T>          Il tipo generico dell'entità.
         * @return L'oggetto risultato della query o null se non trovato.
         */
        public static <T> T findByParameter(Class<T> entityClass, String paramName, Object paramValue) {
            T result = null;
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                // Inizia la transazione
                session.beginTransaction();

                // Crea la query HQL dinamica
                String hql = "from " + entityClass.getSimpleName() + " where " + paramName + " = :" + paramName;

                // Crea la query
                Query<T> query = session.createQuery(hql, entityClass);

                // Imposta il parametro
                query.setParameter(paramName, paramValue);

                // Ottieni il risultato
                result = query.uniqueResult();

                // Esegui il commit della transazione
                session.getTransaction().commit();
            } catch (Exception e) {
                // Rollback in caso di errore
//                if (session.getTransaction() != null) {
//                    session.getTransaction().rollback();
//                }
//                e.printStackTrace();
            }

            return result;
        }


    //DA USARE CON
    //String paramName = "CF";
//            String paramValue = "MZYNDA02P55A345F";
//
//            // Esegui la query generalizzata
//            Cittadino cittadino = HibernateQueryUtil.findByParameter(session,
//                                  Cittadino.class, paramName, paramValue);




}