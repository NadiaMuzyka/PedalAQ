package org.pedalaq.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private LocalDateTime scadenza;
    private Date data;
    private Veicolo veicolo;
    private Cittadino cittadino;


    public Prenotazione(){}

    public Prenotazione(Veicolo veicolo, Cittadino cittadino) {
        this.veicolo = veicolo;
        this.cittadino = cittadino;
    }

    public boolean controllaPrenotazione() {

        //TODO controllare che la prenotazione sia ancora valida
        return true;
    }

    public LocalDateTime getScadenza() {
        return scadenza;
    }

    public void setScadenza(LocalDateTime scadenza) {
        this.scadenza = scadenza;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }

    public Cittadino getCittadino() {
        return cittadino;
    }

    public void setCittadino(Cittadino cittadino) {
        this.cittadino = cittadino;
    }
}
