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
    //private Date data;//è necessario questo attributo?
    @OneToOne
    @JoinColumn(name = "id_veicolo")
    private Veicolo veicolo;
    @OneToOne
    @JoinColumn(name = "id_cittadino")
    private Cittadino cittadino;


    public Prenotazione(){}

    public Prenotazione(Veicolo veicolo, Cittadino cittadino) {
        this.scadenza = LocalDateTime.now().plusMinutes(10); //la prenotazione dura 10 minuti (spostabile nel config)
        veicolo.setStato("Prenotato"); //bisogna implementare un meccanismo che riporta lo stato a libero
        //sarebbe comodo inserirlo nel metodo che restituisce i veicoli liberi dello stallo
        this.veicolo = veicolo;
        this.cittadino = cittadino;
    }

    public Prenotazione getPrenotazioneby_veicolo(Veicolo veicolo) {
        //probabilmente ci va una query che prende la prenotazione dall'id del veicolo dal db e poi la ritorna
        //TODO FIXARE QUESTO METODO affinchè restituisca la prenotazione del veicolo inserito come argomento
        return new Prenotazione();
    }

    public Long getId() {
        return id;
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

//    public Date getData() {
//        return data;
//    }
//
//    public void setData(Date data) {
//        this.data = data;
//    }

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


