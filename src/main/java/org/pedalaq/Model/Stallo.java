package org.pedalaq.Model;

import java.util.ArrayList;

public class Stallo {
    private double lat;
    private double lng;
    private int maxPosti;
    private String descrizione;
    private ArrayList<Veicolo> veicoli;

    public ArrayList<Veicolo> getVeicoli(){

        return this.veicoli;
    }

    public boolean controllaPresenza(Veicolo veicolo){
        //tmp
        return true;
    }
    public boolean bloccaVeicolo(Veicolo veicolo){
        //tmp
        return true;
    }

    public ArrayList<Veicolo> getVeicoliStallo(){

        //TODO i veicoli restituiti devono avere stato "Disponibile"
        return this.veicoli ;
    }
}
