package org.pedalaq.Model;

import java.util.ArrayList;

public abstract class Veicolo {

    protected int id;
    protected String stato; //Lo stato può essere "Libero", "Prenotato", "Noleggiato"
    protected String codiceSblocco;
    protected ArrayList<Accessorio> accessori;

    public boolean bloccaVeicolo(){
        if (this.stato == "Libero"){
            this.stato = "Bloccato";
            return true;
        }
        return false;
    }
}
