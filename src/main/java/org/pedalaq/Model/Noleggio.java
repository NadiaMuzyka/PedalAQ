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
    @OneToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @PrimaryKeyJoinColumn
    private Prenotazione prenotazione;
    private TariffaNoleggioStandard tariffaNoleggio;

    public Noleggio(Prenotazione prenotazione) {

        //TODO il noleggio dovrebbe cambiare anche lo stato del veicolo
        this.prenotazione = prenotazione;
    }
}
