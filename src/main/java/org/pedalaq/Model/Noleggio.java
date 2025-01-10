package org.pedalaq.Model;

import java.time.LocalDateTime;

public class Noleggio {

    private LocalDateTime inizioCorsa;
    private LocalDateTime fineCorsa;
    private Prenotazione prenotazione;
    private TariffaNoleggioStandard tariffaNoleggio;

    public Noleggio(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }
}
