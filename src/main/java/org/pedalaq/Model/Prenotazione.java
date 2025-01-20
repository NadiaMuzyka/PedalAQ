package org.pedalaq.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime scadenza;
    private Date data;//è necessario questo attributo?
    @OneToOne
    @JoinColumn(name = "id_veicolo")
    private Veicolo veicolo;
    @OneToOne
    @JoinColumn(name = "id_cittadino")
    private Cittadino cittadino;


    public Prenotazione(){}

    public Prenotazione(Veicolo veicolo, Cittadino cittadino) {
        this.veicolo = veicolo;
        this.cittadino = cittadino;
    }

    public boolean controllaPrenotazione() {
        //confronto tra la data di Inizio e quella di Fine
        //Non viene considerato il tempo in quanto sono di tipo local date
        //Possiamo pensare che se l'abbonamento scade il giorno stesso sia ancora valido
        int comparisonResult = LocalDateTime.now().compareTo(this.scadenza);
        //il metodo restituisce >0 se sono l'abbonamento è scaduto
        if(comparisonResult > 0){
            return false; //NON VALIDO
        }
        return true;    //VALIDO
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


