package org.pedalaq.Model;

import jakarta.persistence.*;
import org.pedalaq.Model.Abbonamento;

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
        this.CF = CF; //TODO fare il controllo del codice fiscale
    }

    public void setPosizione(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public boolean controllaAbbonamento(){
        if (!this.abbonamentoAttivo.validaAbbonamento()){
            this.abbonamentoAttivo = null;
            return false;
        }return true;
    }
}
