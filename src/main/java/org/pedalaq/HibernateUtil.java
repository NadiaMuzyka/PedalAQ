package org.pedalaq;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    public static Properties getPassword() {
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