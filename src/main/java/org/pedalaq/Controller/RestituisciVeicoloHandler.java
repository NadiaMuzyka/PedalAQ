package org.pedalaq.Controller;

import org.pedalaq.Model.*;
import org.pedalaq.Services.HibernateUtil;

import java.util.List;

public class RestituisciVeicoloHandler {

    public List<Stallo> listaStalliRestituzione(double raggio,Citta citta, Cittadino cittadino) {
        return citta.getStalliRaggioParcheggio(cittadino.getLat(), cittadino.getLng(),raggio);
    }

    public boolean selezionaStallo(Stallo stallo, Veicolo veicolo) {

        return stallo.verificaVeicolo(veicolo);
    }

    public double selezionaPagamento(Noleggio noleggio, Cittadino cittadino, Citta citta) {

        int punti = cittadino.getPuntiClassifica();
        return noleggio.calcolaCosto(punti, citta);
    }

    public boolean paga(int puntiUtilizzabili, double totale, Noleggio noleggio, Stallo stalloArrivo, Cittadino cittadino, Veicolo veicolo) {

        if(cittadino.sottraiSaldo(totale, puntiUtilizzabili)){
            Stallo stalloPartenza = noleggio.getStalloPartenza();

            if(stalloPartenza.rimuoviVeicolo(veicolo)){
                stalloArrivo.aggiungiVeicolo(veicolo);
                noleggio.setStalloArrivo(stalloArrivo);

            }else return false;

            return true;
        }
        return false;
    }

    //per il menu dinamico controllo se il cittadino ha almeno un noleggio non terminato
    //si prende quella con la scadenza maggiore
    public static boolean menurestituzione(Cittadino cittadino) {
        Long num_noleggi_in_corso = HibernateUtil.countByParameterIsNull(Noleggio.class, "fineCorsa");
        return (num_noleggi_in_corso>0);
    }




}
