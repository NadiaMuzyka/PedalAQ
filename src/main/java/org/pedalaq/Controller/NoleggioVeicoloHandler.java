package org.pedalaq.Controller;

import org.pedalaq.Model.Citta;
import org.pedalaq.Model.Cittadino;
import org.pedalaq.Model.Stallo;
import org.pedalaq.Model.Veicolo;

import java.util.ArrayList;

public class NoleggioVeicoloHandler {

    public ArrayList<Stallo> visualizzaListaStalli(double lat, double lon, double raggio, Citta citta, Cittadino cittadino) {

        cittadino.setPosizione(lat, lon);

        ArrayList<Stallo> stalli = new ArrayList<Stallo>();

        stalli = citta.getStalliRaggio(lat, lon, raggio);

        return stalli;
    }

    public ArrayList<Veicolo> getVeicoli(Stallo stallo) {

        ArrayList<Veicolo> veicoli = new ArrayList<Veicolo>();

        veicoli = stallo.getVeicoliStallo();

        return veicoli;
    }


}
