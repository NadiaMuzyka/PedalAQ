package org.pedalaq.Model;

import java.util.ArrayList;

public class Stallo {
    private double lat;
    private double lon;
    private int maxPosti;
    private String descrizione;
    private ArrayList<Veicolo> veicoli;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getMaxPosti() {
        return maxPosti;
    }

    public void setMaxPosti(int maxPosti) {
        this.maxPosti = maxPosti;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setVeicoli(ArrayList<Veicolo> veicoli) {
        this.veicoli = veicoli;
    }

    public ArrayList<Veicolo> getVeicoli(){

        return this.veicoli;
    }

    public boolean controllaPresenza(Veicolo veicolo){
        //tmp
        return true;
    }
    public boolean bloccaVeicolo(Veicolo veicolo){

        return veicolo.bloccaVeicolo();
    }


    public ArrayList<Veicolo> getVeicoliStallo() {
         //TODO i veicoli restituiti devono avere stato "Disponibile"
        return this.veicoli ;
    }
}
