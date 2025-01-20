package org.pedalaq.Controller;

import org.pedalaq.Model.*;

import java.util.ArrayList;
import java.util.List;

public class NoleggioVeicoloHandler {

    public List<Stallo> visualizzaListaStalli(double lat, double lon, double raggio, Citta citta, Cittadino cittadino) {

        cittadino.setPosizione(lat, lon);

        List<Stallo> stalli = new ArrayList<Stallo>();

        stalli = citta.getStalliRaggio(lat, lon, raggio);

        return stalli;
    }

    public ArrayList<Veicolo> getVeicoli(Stallo stallo) {

        ArrayList<Veicolo> veicoli = new ArrayList<Veicolo>();

        veicoli = stallo.getVeicoliStallo();

        return veicoli;
    }

    public Prenotazione prenotaVeicolo(Veicolo veicolo, Stallo stallo, Cittadino cittadino) {

        //Se il cittadino ha un abbonamento attivo
        if (cittadino.controllaAbbonamento()) {

            //Se il veicolo è stato bloccato con successo
            if (stallo.bloccaVeicolo(veicolo)) {

                return new Prenotazione(veicolo, cittadino);
            }

        }
        return null;
    }

    public boolean noleggiaVeicolo(Prenotazione prenotazione) {


        if(prenotazione.controllaPrenotazione()){
            Noleggio noleggio = new Noleggio(prenotazione);

            return true;
        }

        return false;
    }

}
