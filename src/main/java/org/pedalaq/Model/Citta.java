package org.pedalaq.Model;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class Citta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double lat;
    private double lon;
    /*
    private ArrayList<Stallo> stalli;
    private TariffaNoleggioStandard tariffaNoleggioAttiva;
    private static final double EARTH_RADIUS_KM = 6371.01;
    // Raggio della Terra in chilometri

    public ArrayList<Stallo> getStalliRaggio(double latitudine, double longitudine, double raggio) {

        return verificaStalli(latitudine, longitudine, raggio);
    }

    private ArrayList<Stallo> verificaStalli(double latitudine, double longitudine, double raggio) {//IL RAGGIO è IN KM
        //TODO da calcolare tutti i stalli che rientrano in un certo raggio. Da metterli in stalli.

        // Stallo Location
        /*
        Stallo Stallotest = new Stallo();
        Stallotest.setLat(42.1256317);
        Stallotest.setLon(10.1256317);
        stalli.add(Stallotest);
        *'/
        ArrayList<Stallo> stalli_in_raggio = new ArrayList<Stallo>();
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


*/

    public Citta(){}




    public Citta(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }


}
