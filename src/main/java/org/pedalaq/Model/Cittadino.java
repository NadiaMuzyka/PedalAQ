package org.pedalaq.Model;

public class Cittadino extends Utente {

    private String nome;
    private String cognome;
    private String CF;
    private double lat;
    private double lng;
    private float saldo;
    private Abbonamento abbonamentoAttivo;


    public void setPosizione(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public boolean controllaAbbonamento(){
        if (!this.abbonamentoAttivo.validaAbbonamento()){
            this.abbonamentoAttivo = null;
            return false;
        }return true;
    }
}
