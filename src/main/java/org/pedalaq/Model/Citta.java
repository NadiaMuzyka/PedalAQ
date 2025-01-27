package org.pedalaq.Model;

import jakarta.persistence.*;
import org.pedalaq.Services.DistanceUtil;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Citta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double lat;
    private double lon;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_citta")
    private List<Stallo> stalli;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_citta")
    private TariffaNoleggioStandard tariffa_standard;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_citta")
    private List<TariffaNoleggioPromozione> tariffe_promo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public void addStallo(Stallo stallo) {
        this.stalli.add(stallo);
    }

    public List<Stallo> getStalli() {
        return stalli;
    }

    public void setStalli(List<Stallo> stalli) {
        this.stalli = stalli;
    }

    public List<Stallo> getStalliRaggioPrenotazione(double latitudine, double longitudine, double raggio) {
        ArrayList<Stallo> stalli_in_raggio = new ArrayList<>();
        for (Stallo stallo : stalli){
            double dist = DistanceUtil.calculateDistance(stallo.getLat(),stallo.getLon(),latitudine,longitudine);
            if(dist<raggio && !(stallo.getVeicolidisp_Stallo().isEmpty())){ //c'è anche un controllo sulla presenza dei veicoli
                //se la distanza è minore del raggio e ha dei veicoli disponibili allora lo aggiungo
                stalli_in_raggio.add(stallo);
            }
        }
        return stalli_in_raggio;
    }

//    private ArrayList<Stallo> verificaStalli(double latitudine, double longitudine, double raggio) {//IL RAGGIO è IN KM
//        ArrayList<Stallo> stalli_in_raggio = new ArrayList<>();
//        for (Stallo stallo : stalli){
//            double dist = DistanceUtil.calculateDistance(stallo.getLat(),stallo.getLon(),latitudine,longitudine);
//            if(dist<raggio && !(stallo.getVeicolidisp_Stallo().isEmpty())){ //c'è anche un controllo sulla presenza dei veicoli
//                //se la distanza è minore del raggio e ha dei veicoli disponibili allora lo aggiungo
//                stalli_in_raggio.add(stallo);
//            }
//        }
//        return stalli_in_raggio;
//    }

    public TariffaNoleggioStandard getTariffa_standard() {
        return tariffa_standard;
    }

    public Stallo stallo_by_id(Long id) {
        for (Stallo stallo : this.stalli) {
            if (stallo.getId().equals(id)) {
                return stallo;
            }
        }
        return null;
    }

    public Citta(){}

    public Citta(double lat, double lon,String nome){
        this.lat = lat;
        this.lon = lon;
        this.nome = nome;
        this.stalli = new ArrayList<>();
    }

    public List<Stallo> getStalliRaggioParcheggio(double latitudine, double longitudine, double raggio) {
        ArrayList<Stallo> stalli_in_raggio = new ArrayList<>();
        for (Stallo stallo : stalli){
            double dist = DistanceUtil.calculateDistance(stallo.getLat(),stallo.getLon(),latitudine,longitudine);
            if(dist<raggio && stallo.getVeicoli().size()<stallo.getMaxPosti()){ //c'è anche un controllo sulla presenza dei veicoli
                //se la distanza è minore del raggio e ha dei posti disponibili allora lo aggiungo
                stalli_in_raggio.add(stallo);
            }
        }
        return stalli_in_raggio;
    }


}
