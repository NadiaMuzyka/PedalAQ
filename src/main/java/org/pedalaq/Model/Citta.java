package org.pedalaq.Model;

import java.util.ArrayList;

public class Citta {
    private String nome;
    private double lat;
    private double lon;
    private ArrayList<Stallo> stalli;
    private TariffaNoleggioStandard tariffaNoleggioAttiva;

    public ArrayList<Stallo> getStalliRaggio(double latitudine, double longitudine, double raggio) {

        return verificaStalli(latitudine, longitudine, raggio);
    }

    private ArrayList<Stallo> verificaStalli(double latitudine, double longitudine, double raggio) {
        //TODO da calcolare tutti i stalli che rientrano in un certo raggio. Da metterli in stalli.

        return stalli;
    }

    public Citta(){
        this.nome = "L'Aquila";
        this.lat = 42.3634408;
        this.lon = 13.3445664;
        //TODO decidere se applicare lo strategy pattern su TariffaNoleggio
    }


}
