package org.pedalaq.Model;

import jakarta.persistence.*;
import org.pedalaq.Controller.NoleggioVeicoloHandler;
import org.pedalaq.Services.DistanceUtil;
import org.pedalaq.Services.HibernateUtil;

import java.time.LocalDateTime;
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
    @OneToMany(mappedBy = "stallo",fetch = FetchType.EAGER)
    //@JoinColumn(name = "id_stallo")
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

    public void addVeicolo(Veicolo veicolo) {
        this.veicoli.add(veicolo);
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
                Prenotazione prenotazione_da_controllare = HibernateUtil.getprenotazionefromidveicolo(veicolo.getId());
                if(!prenotazione_da_controllare.controllaPrenotazione())
                {//allora setta il veicolo come libero e aggiungilo all'array
                    //NoleggioVeicoloHandler.cancellaprenotazione(HibernateUtil.getprenotazionefromidveicolo(veicolo.getId()));
                    veicolo.setStato("Libero");
                    NoleggioVeicoloHandler.aggiornaveicolo(veicolo);
                    veicoli_disp.add(veicolo);
                }
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

    public boolean verificaVeicolo(Veicolo veicolo) {
        return this.verificaPosizione(veicolo.getLat(), veicolo.getLon());
    }

    private boolean verificaPosizione(double lat, double lon) {
        //distanza tra stallo e veicolo che deve essere minore di 15 metri
        double dist = DistanceUtil.calculateDistance(this.getLat(),this.getLon(),lat,lon);
        return (dist<0.015);//True se è vicino allo stallo
    }

    //TODO da implementare
    public boolean rimuoviVeicolo(Veicolo veicolo) {
        return true;
    }

    //TODO da implementare
    public boolean aggiungiVeicolo(Veicolo veicolo) {

        //TODO Aggiunge veicolo alla lista


        return veicolo.rendiDisponibile();
    }
}
