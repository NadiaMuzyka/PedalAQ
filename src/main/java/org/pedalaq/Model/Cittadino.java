package org.pedalaq.Model;

import jakarta.persistence.*;

import java.util.List;
import java.util.regex.*;
import org.pedalaq.Model.Abbonamento;
import org.pedalaq.Services.Config;
import org.pedalaq.Services.HibernateUtil;

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
    private double saldo = 0;
    private int puntiClassifica; //TODO vedere dove settare
    private int puntiUtilizzabili;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cittadino")
    private List<Noleggio> noleggiAttivi;
    @OneToOne
    @JoinColumn(name = "id_abbonamento_attivo")
    private Abbonamento abbonamentoAttivo;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cittadino")
    private List<Prenotazione> prenotazioni;

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

    public Long getId() {
        return id;
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

    public double getSaldo() {
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

    public List<Noleggio> getNoleggiAttivi() {
        return noleggiAttivi;
    }

    public int getPuntiClassifica() {
        return puntiClassifica;
    }

    public boolean controllaAbbonamento(){
        //System.out.println(this.abbonamentoAttivo);
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

    public Prenotazione prenotazione_by_id(Long id) {
        for (Prenotazione prenotazione : this.prenotazioni) {
            if (prenotazione.getId().equals(id)) {
                return prenotazione;
            }
        }
        return null;
    }

    public boolean sottraiSaldo(double totale, int puntiDaScalare){

        //Calcolo totale senza punti
        double tot = totale - (puntiDaScalare / Config.SCONTO);

        //Se ho abbastanza soldi pago
        if (tot <= this.saldo) {
            this.saldo = this.saldo - tot;

            //Aggiorno i punti
            this.puntiUtilizzabili -= puntiDaScalare;

            return true;
        }
        return false;
    }

    //controllo se ha almeno una prenotazione non associata ad un noleggio
    public boolean hasactiveprenotazione(){
        for (Prenotazione prenotazione : this.prenotazioni) {
            if(HibernateUtil.findByParameter(Prenotazione.class,"cittadino",this) != null
                && prenotazione.controllaPrenotazione()){
                return true;  //PRENOTAZIONE ASSOCIATA
            }
        }
        //System.out.println("Prenotazione non trovato");
        return false;
    }

    //controllo se ha almeno una prenotazione non associata ad un noleggio
    public boolean getveicolinoleggiati(){
        for (Prenotazione prenotazione : this.prenotazioni) {
            if(HibernateUtil.findByParameter(Noleggio.class,"prenotazione",prenotazione) == null
                    && prenotazione.controllaPrenotazione()){
                return true;  //PRENOTAZIONE ASSOCIATA
            }
        }
        //System.out.println("Prenotazione non trovato");
        return false;
    }

    //rimozione del noleggio attivo
    public void rimuoviNoleggioAttivo(Noleggio noleggio){
        this.noleggiAttivi.remove(noleggio);
    }

}
