package org.pedalaq.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Stallo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double lat;
    private double lon;
    private int maxPosti;
    private String descrizione;
    @OneToMany
    @JoinColumn(name = "id_stallo")
    private List<Veicolo> veicoli;

    public Stallo() {}

    public Stallo(double lat, double lon, int maxPosti) {
        this.lat = lat;
        this.lon = lon;
        this.maxPosti = maxPosti;
    }

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

        return (ArrayList<Veicolo>) this.veicoli;
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
        return (ArrayList<Veicolo>) this.veicoli;
    }
}
