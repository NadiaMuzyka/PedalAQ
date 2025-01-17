package org.pedalaq.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Citta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double lat;
    private double lon;

    @OneToMany
    @JoinColumn(name = "id_citta")
    private List<Stallo> stalli;

    //private TariffaNoleggioStandard tariffaNoleggioAttiva; //da errore per il momento lasciare così
    private static final double EARTH_RADIUS_KM = 6371.01;
    // Raggio della Terra in chilometri


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

    public List<Stallo> getStalliRaggio(double latitudine, double longitudine, double raggio) {
        //TODO gestire il metodo in modo che possa anche visualizzare tutti gli stalli dellà citta se non ci sono argomenti al metodo

        if(raggio == 0){
            return stalli;
            }
        else{
            return verificaStalli(latitudine, longitudine, raggio);
            }
    }

    private ArrayList<Stallo> verificaStalli(double latitudine, double longitudine, double raggio) {//IL RAGGIO è IN KM


        // Stallo Location

        Stallo Stallotest = new Stallo();
        Stallotest.setLat(42.1256317);
        Stallotest.setLon(10.1256317);
        stalli.add(Stallotest);

        ArrayList<Stallo> stalli_in_raggio = new ArrayList<>();
        for (Stallo stallo : stalli){
            if(calculateDistance(stallo.getLat(),stallo.getLon(),latitudine,longitudine)<raggio){ //se la distanza è minore del raggio allora lo aggiungo
                stalli_in_raggio.add(stallo);
            }
        }

        return stalli_in_raggio;
    }

    // calcolo della distanza con la formula dell'emisenoverso
    public static double calculateDistance(Double lat1, Double lon1,Double lat2, Double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Calcolo la differenza delle latitudini e delle longitudini
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        // Applicao la formula dell'emisenoverso
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calcolo la distanza
        return EARTH_RADIUS_KM * c;
    }




    public Citta(){}

    public Citta(double lat, double lon,String nome){
        this.lat = lat;
        this.lon = lon;
        this.nome = nome;
        this.stalli = new ArrayList<>();
    }


}
