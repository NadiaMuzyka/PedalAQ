package org.pedalaq.Model;

import java.time.LocalDateTime;

public class Noleggio {

    private LocalDateTime inizioCorsa;
    private LocalDateTime fineCorsa;
    private Prenotazione prenotazione;
    private TariffaNoleggioStandard tariffaNoleggio;

    public Noleggio(Prenotazione prenotazione) {

        //TODO il noleggio dovrebbe cambiare anche lo stato del veicolo
        this.prenotazione = prenotazione;
    }
}
