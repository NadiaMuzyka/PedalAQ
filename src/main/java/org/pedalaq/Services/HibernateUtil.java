package org.pedalaq.Services;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
}