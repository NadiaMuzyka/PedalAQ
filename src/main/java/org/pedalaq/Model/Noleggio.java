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

    public void setStalloArrivo(Stallo stalloArrivo) {
        this.stalloArrivo = stalloArrivo;
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

        Duration durata = Duration.between(this.inizioCorsa, this.fineCorsa);
        double totale  = costo * durata.toMinutes();
        return totale;
    }
}
