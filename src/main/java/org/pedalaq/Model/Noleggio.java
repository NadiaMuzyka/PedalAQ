package org.pedalaq.Model;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
public class Noleggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime inizioCorsa;
    private LocalDateTime fineCorsa;
    @ManyToOne
    @JoinColumn(name = "id_cittadino")
    private Cittadino cittadino;
    @ManyToOne
    @JoinColumn(name = "id_stallo_partenza")
    private Stallo stalloPartenza;
    @ManyToOne
    @JoinColumn(name = "id_stallo_arrivo")
    private Stallo stalloArrivo;
    @OneToOne
    @JoinColumn(name = "id_prenotazione")
    private Prenotazione prenotazione;

    public Noleggio(){}

    public Stallo getStalloPartenza() {
        return stalloPartenza;
    }

    public void setStalloPartenza(Stallo stalloPartenza) {
        this.stalloPartenza = stalloPartenza;
    }

    public void aggiornaNoleggio(Stallo stalloArrivo, LocalDateTime fineCorsa, String veicoloType) {
        this.stalloArrivo = stalloArrivo;
        this.fineCorsa = fineCorsa;

        Duration durata = Duration.between(this.inizioCorsa, LocalDateTime.now());

        this.cittadino.aggiornaPunti(durata.toMinutes(), veicoloType);

    }

    public Prenotazione getPrenotazione() {
        return prenotazione;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getInizioCorsa() {
        return inizioCorsa;
    }

    public void setInizioCorsa(LocalDateTime inizioCorsa) {
        this.inizioCorsa = inizioCorsa;
    }

    public LocalDateTime getFineCorsa() {
        return fineCorsa;
    }

    public void setFineCorsa(LocalDateTime fineCorsa) {
        this.fineCorsa = fineCorsa;
    }

    public Cittadino getCittadino() {
        return cittadino;
    }

    public void setCittadino(Cittadino cittadino) {
        this.cittadino = cittadino;
    }

    public Stallo getStalloArrivo() {
        return stalloArrivo;
    }

    public void setPrenotazione(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }

    public Noleggio(Prenotazione prenotazione, Stallo stalloPartenza, Cittadino cittadino) {
        this.inizioCorsa = LocalDateTime.now();
        //TODO il noleggio dovrebbe cambiare anche lo stato del veicolo
        //TODO il noleggio ha lo stallo di partenza e di arrivo
        this.prenotazione = prenotazione;
        this.stalloPartenza = stalloPartenza;
        this.cittadino = cittadino;

    }

    public double calcolaCosto(int punti, Citta citta) {
        TariffaNoleggioFactory tnf = TariffaNoleggioFactory.getInstance();

        double costo = tnf.creaCompositeTariffa(citta.getTariffa_standard(), punti, citta);
        Duration durata = Duration.between(this.inizioCorsa, LocalDateTime.now());
        //Duration durata = Duration.between(this.inizioCorsa, this.fineCorsa);
        //System.out.println("durata: " + durata);
        double totale  = costo * durata.toMinutes();
        return Math.round(totale*100.00)/100.00;
    }


}
