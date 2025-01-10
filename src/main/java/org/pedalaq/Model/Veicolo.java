package org.pedalaq.Model;

import java.util.ArrayList;

public abstract class Veicolo {

    protected int id;
    protected boolean stato;
    protected String codiceSblocco;
    protected ArrayList<Accessorio> accessori;

    public boolean bloccaVeicolo(){
        if (this.stato){
            this.stato = false;
            return true;
        }
        return false;
    }
}
