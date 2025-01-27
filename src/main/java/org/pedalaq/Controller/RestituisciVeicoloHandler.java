package org.pedalaq.Controller;

import org.pedalaq.Model.*;

import java.util.ArrayList;
import java.util.List;

public class RestituisciVeicoloHandler {

    public List<Stallo> listaStalliRestituzione(double raggio, double lat, double lon, Citta citta) {
        return citta.getStalliDisponibili(lat, lon, raggio);
    }

    public boolean selezionaStallo(Stallo stallo, Veicolo veicolo) {

        return stallo.verificaVeicolo(veicolo);
    }

    public double selezionaPagamento(Noleggio noleggio, Cittadino cittadino, Citta citta) {

        int punti = cittadino.getPuntiClassifica();
        return noleggio.calcolaCosto(punti, citta);
    }

    public boolean paga(int puntiUtilizzabili, double totale, Noleggio noleggio, Stallo stalloArrivo, Cittadino cittadino) {

        if(cittadino.sottraiSaldo(totale, puntiUtilizzabili)){
            Stallo stalloPartenza = noleggio.getStalloPartenza();


        }
        return true;
    }
}
