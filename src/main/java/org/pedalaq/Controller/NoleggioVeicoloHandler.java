package org.pedalaq.Controller;

import org.pedalaq.Model.*;

import java.util.ArrayList;

public class NoleggioVeicoloHandler {

    //PUNTO 1 DEL CASO D'USO
    public ArrayList<Stallo> visualizzaListaStalli(double lat, double lon, double raggio, Citta citta, Cittadino cittadino) {

        cittadino.setPosizione(lat, lon);

        ArrayList<Stallo> stalli;

        stalli = (ArrayList<Stallo>) citta.getStalliRaggio(cittadino.getLat(), cittadino.getLng(), raggio);

        return stalli;
    }

    //PUNTO 2 DEL CASO D'USO
    public ArrayList<Veicolo> getVeicoli(Stallo stallo) {

        ArrayList<Veicolo> veicoli = new ArrayList<Veicolo>();

        veicoli = stallo.getVeicoliStallo();

        return veicoli;
    }

    //PUNTO 3 DEL CASO D'USO
    public Prenotazione prenotaVeicolo(Veicolo veicolo, Stallo stallo, Cittadino cittadino) {

        //Se il cittadino ha un abbonamento attivo
        if (cittadino.controllaAbbonamento()) {

            //Se il veicolo è stato bloccato con successo
            if (stallo.bloccaVeicolo(veicolo)) {

                return new Prenotazione(veicolo, cittadino);
            }
            else {
                //gestire qui il caso di veicolo non disponibile al blocco (già bloccato o noleggiato attraverso view
            }

        }
        return null; //gestire la situazione dove il cittadino è sprovvisto di abbonamento valido (attraverso view)
    }

    //PUNTO 4 DEL CASO D'USO
    public boolean noleggiaVeicolo(Prenotazione prenotazione) {

        if(prenotazione.controllaPrenotazione()){
            Noleggio noleggio = new Noleggio(prenotazione);
            return true;
        }

        return false;
    }

}
