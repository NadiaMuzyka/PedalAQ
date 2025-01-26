package org.pedalaq.Services;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.pedalaq.Model.*;

import javax.persistence.NoResultException;
import java.io.InputStream;
import java.util.List;
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

    public static Prenotazione getprenotazionefromidveicolo(Long id_veicolo) {
        //System.out.println("id_veicolo: " + id_veicolo);
        Prenotazione prenotazione_sel = new Prenotazione();
        //SessionFactory sessionFactory1 = HibernateUtil.getSessionFactory();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sql = "SELECT id FROM prenotazione WHERE id_veicolo = :cf"; // Esempio di query SQL
            Query query = session.createNativeQuery(sql);
            query.setParameter("cf", id_veicolo);  // Impostazione del parametro


            //Citta citta_sel = new Citta();
            //session.getTransaction().commit();
            try {
                //prenotazione_sel = query.getSingleResult();
                long id = (long) query.getSingleResult();
                //System.out.println(id); //OOOOOOK
                prenotazione_sel = HibernateUtil.findByParameter(Prenotazione.class,"id",id);
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
        Transaction transaction = null;
        T result = null;
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Inizia la transazione
            transaction = session.beginTransaction();
            // Crea la query HQL dinamica
            String hql = "from " + entityClass.getSimpleName() + " where " + paramName + " = :" + paramName;

            // Crea la query
            Query<T> query = session.createQuery(hql, entityClass);

            // Imposta il parametro
            query.setParameter(paramName, paramValue);

            // Ottieni il risultato
            result = query.uniqueResult();

            // Esegui il commit della transazione
            transaction.commit();
        } catch (Exception e) {
            // Rollback in caso di errore
//            if (transaction != null) {
//                transaction.rollback();
//            }
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Metodo generalizzato per salvare o aggiornare un'entità nel database con gestione di sessione e transazione.
     *
     * @param entity L'oggetto entità da salvare o aggiornare.
     * @param <T>    Il tipo generico dell'entità.
     * @return L'entità salvata o aggiornata.
     */
    public static <T> T saveOrUpdateWithTransaction(T entity) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Inizia la transazione
            transaction = session.beginTransaction();

            // Salva o aggiorna l'entità
            session.saveOrUpdate(entity);

            // Esegui il commit della transazione
            transaction.commit();
        } catch (Exception e) {
            // Rollback in caso di errore
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return entity;
    }

    /**
     * Metodo generalizzato per eliminare un'entità dal database con gestione di sessione e transazione.
     *
     * @param entity L'oggetto entità da eliminare.
     * @param <T>    Il tipo generico dell'entità.
     */
    public static <T> void deleteWithTransaction(T entity) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Inizia la transazione
            transaction = session.beginTransaction();

            // Elimina l'entità
            session.delete(entity);

            // Esegui il commit della transazione
            transaction.commit();
        } catch (Exception e) {
            // Rollback in caso di errore
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    public static <T> T findByParameterString(Class<T> entityClass, String paramName, String paramValue) {
        Transaction transaction = null;
        T result = null;
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Inizia la transazione
            transaction = session.beginTransaction();
            // Crea la query HQL dinamica
            String hql = "from " + entityClass.getSimpleName() + " where " + paramName + " = :" + paramName;

            // Crea la query
            Query<T> query = session.createQuery(hql, entityClass);

            // Imposta il parametro
            query.setParameter(paramName, paramValue);

            // Ottieni il risultato
            result = query.uniqueResult();

            // Esegui il commit della transazione
            transaction.commit();
        } catch (Exception e) {
            // Rollback in caso di errore
//            if (transaction != null) {
//                transaction.rollback();
//            }
            e.printStackTrace();
        }

        return result;
    }





}