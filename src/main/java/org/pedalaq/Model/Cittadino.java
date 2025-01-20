package org.pedalaq.Model;

import jakarta.persistence.*;
import java.util.regex.*;
import org.pedalaq.Model.Abbonamento;
import org.pedalaq.Services.Config;

@Entity
public class Cittadino extends Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cognome;
    private String CF;
    private double lat = 0;
    private double lng = 0;
    private float saldo = 0;
    @OneToOne
    @JoinColumn(name = "abbonamento_attivo_id")
    private Abbonamento abbonamentoAttivo;

    public Abbonamento getAbbonamentoAttivo() {
        return abbonamentoAttivo;
    }

    public void setAbbonamentoAttivo(Abbonamento abbonamentoAttivo) {
        this.abbonamentoAttivo = abbonamentoAttivo;
    }

    // Costruttore vuoto obbligatorio
    public Cittadino() {}

    public Cittadino(String nome, String cognome, String CF) {
        this.nome = nome;
        this.cognome = cognome;
        this.CF = CF;
    }

    public void setPosizione(double lat, double lng){
        this.lat = Config.LAT;
        this.lng = Config.LNG;  //per simulare la localizzazione
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public float getSaldo() {
        return saldo;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCF() {
        return CF;
    }

    public boolean controllaAbbonamento(){
        if (this.abbonamentoAttivo == null) { //Se è null va gestito
            return false;
        } else if (!this.abbonamentoAttivo.validaAbbonamento()) { //se è scaduto si setta a null
            this.abbonamentoAttivo = null;
            return false;
        }
        else return true; //altrimenti è valido
    }

    //METODO per il controllo del codice fiscale
    //dato un codice fiscale fornito come argomento ritorna "true" se valido, altrimenti "false"
    public static boolean isValidCodiceFiscale(String codiceFiscale) {
        // Controlla se il formato del codice fiscale è corretto
        if (codiceFiscale == null || !codiceFiscale.matches("^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$")) {
            return false;
        }

        // Calcola il carattere di controllo
        char expectedCheckChar = calculateCheckCharacter(codiceFiscale.substring(0, 15));
        char actualCheckChar = codiceFiscale.charAt(15);

        // Confronta il carattere di controllo calcolato con quello fornito
        return expectedCheckChar == actualCheckChar;
    }

    private static char calculateCheckCharacter(String codiceFiscalePartial) {
        // Tabella dei valori pari e dispari secondo le regole del codice fiscale
        int[] oddValues = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13};
        int[] evenValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5};

        int sum = 0;

        for (int i = 0; i < codiceFiscalePartial.length(); i++) {
            char c = codiceFiscalePartial.charAt(i);
            if (i % 2 == 0) { // Posizioni dispari (0-based)
                sum += oddValues[c - '0'];
            } else { // Posizioni pari
                sum += evenValues[c - '0'];
            }
        }

        // Determina il carattere di controllo
        return (char) ('A' + (sum % 26));
    }

}
