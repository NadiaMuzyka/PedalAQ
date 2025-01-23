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
    @OneToOne
    @JoinColumn(name = "id_prenotazione")
    private Prenotazione prenotazione;

    public Noleggio(Prenotazione prenotazione) {
        this.inizioCorsa = LocalDateTime.now();
        //TODO il noleggio dovrebbe cambiare anche lo stato del veicolo
        this.prenotazione = prenotazione;
    }
}
