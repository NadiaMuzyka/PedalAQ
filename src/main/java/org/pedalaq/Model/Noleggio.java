package org.pedalaq.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Noleggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime inizioCorsa;
    private LocalDateTime fineCorsa;
    @ManyToOne
    @JoinColumn(name = "id_stallo_partenza")
    private Stallo stalloPartenza;
    @OneToOne
    @JoinColumn(name = "id_prenotazione")
    private Prenotazione prenotazione;

    public Stallo getStalloPartenza() {
        return stalloPartenza;
    }

    public void setStalloPartenza(Stallo stalloPartenza) {
        this.stalloPartenza = stalloPartenza;
    }

    public Noleggio(Prenotazione prenotazione, Stallo stalloPartenza) {
        this.inizioCorsa = LocalDateTime.now();
        //TODO il noleggio dovrebbe cambiare anche lo stato del veicolo
        //TODO il noleggio ha lo stallo di partenza e di arrivo
        this.prenotazione = prenotazione;
        this.stalloPartenza = stalloPartenza;

    }
}
