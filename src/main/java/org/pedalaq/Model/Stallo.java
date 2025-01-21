package org.pedalaq.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        if(this.controllaPresenza(veicolo)){
            boolean status = veicolo.bloccaVeicolo();  //torna true se è "bloccabile"
            return status;
        }
        return false;
    }


    public Long getId() {
        return id;
    }

    public ArrayList<Veicolo> getVeicolidisp_Stallo() {
        ArrayList<Veicolo> veicoli_disp = new ArrayList<>();
        for (Veicolo veicolo : veicoli) {
            if(Objects.equals(veicolo.getStato(), "Libero")){
                veicoli_disp.add(veicolo);
            } else if (Objects.equals(veicolo.getStato(), "Prenotato")) {
                //controllo se la prenotazione è scaduta (probabilmente va spostato)
                //if(prenotazionescaduta){allora setta il veicolo come libero e aggiungilo all'array
                //veicoli_disp.add(veicolo);
            }
        }
        return veicoli_disp;
    }

    public Veicolo veicolo_by_id(Long id) {
        for (Veicolo veicolo : this.veicoli) {
            if (veicolo.getId().equals(id)) {
                return veicolo;
            }
        }
        return null;
    }
}
