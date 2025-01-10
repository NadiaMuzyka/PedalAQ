package org.pedalaq.Model;

import java.time.LocalDateTime;
import java.util.Date;

public class Prenotazione {

    private LocalDateTime scadenza;
    private Date data;
    private Veicolo veicolo;
    private Cittadino cittadino;

    public Prenotazione(Veicolo veicolo, Cittadino cittadino) {
        this.veicolo = veicolo;
        this.cittadino = cittadino;
    }

    public boolean controllaPrenotazione() {

        //TODO controllare che la prenotazione sia ancora valida
        return true;
    }
}
