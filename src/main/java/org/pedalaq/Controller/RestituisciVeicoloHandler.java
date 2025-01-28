package org.pedalaq.Controller;

import org.pedalaq.Model.*;
import org.pedalaq.Services.HibernateUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class RestituisciVeicoloHandler {

    public static List<Noleggio> mostraNoleggiAttivi(Cittadino cittadino) {
        return cittadino.getNoleggiAttivi();
    }

    //devo controllare se il noleggio appartiene al cittadino
    public static Veicolo selezionaveicolo_ric(Cittadino cittadino, Long id_veicolo) {
        Veicolo veicolo_sel = HibernateUtil.findByParameter(Veicolo.class, "id", id_veicolo);

        //servono 2 passaggi per ottenere il noleggio dell'id del veicolo
        Noleggio noleggio_sel = veicolo_sel.findnoleggioattivo();
//        System.out.println(noleggio_sel);
//        System.out.println(veicolo_sel);
//        System.out.println(noleggio_sel.getCittadino());
//        System.out.println(cittadino);
        if (veicolo_sel != null && noleggio_sel != null && noleggio_sel.getCittadino().getId() == cittadino.getId()){
            return veicolo_sel;// se sia il veicolo esiste, che il noleggio associato non è terminato
            //e il noleggio è del cittadino, allora ritorna il veicolo
        }
        else {
            return null;
        }
    }


    public static List<Stallo> listaStalliRestituzione(double raggio,Citta citta, Cittadino cittadino) {
        return citta.getStalliRaggioParcheggio(cittadino.getLat(), cittadino.getLng(),raggio);
    }

    //semplicemente ritorna true se lo stallo è corretto (il veicolo è nell'intorno dello stallo)
    public static boolean selezionaStallo(Stallo stallo, Veicolo veicolo) {
        return true;
        //return stallo.verificaVeicolo(veicolo);
    }

    public static double selezionaPagamento(Noleggio noleggio, Cittadino cittadino, Citta citta) {

        int punti = cittadino.getPuntiClassifica();
        return noleggio.calcolaCosto(punti, citta);
    }

    public static boolean paga(int puntiDaUtilizzare, double totale, Noleggio noleggio, Stallo stalloArrivo, Cittadino cittadino, Veicolo veicolo) {

        if(cittadino.sottraiSaldo(totale, puntiDaUtilizzare)) {
            Stallo stalloPartenza = noleggio.getStalloPartenza();

            if(stalloPartenza.rimuoviVeicolo(veicolo)){
                stalloArrivo.aggiungiVeicolo(veicolo);
                noleggio.aggiornaNoleggio(stalloArrivo, LocalDateTime.now());

                HibernateUtil.saveOrUpdateWithTransaction(veicolo);
                HibernateUtil.saveOrUpdateWithTransaction(stalloPartenza);
                HibernateUtil.saveOrUpdateWithTransaction(stalloArrivo);
                HibernateUtil.saveOrUpdateWithTransaction(noleggio);

            }else return false;

            return true;
        }
        return false;
    }

    //per il menu dinamico controllo se il cittadino ha almeno un noleggio non terminato
    //si prende quella con la scadenza maggiore
    public static boolean menurestituzione(Cittadino cittadino) {
//        Long num_noleggi_in_corso = HibernateUtil.countByParameterIsNull(Noleggio.class, "fineCorsa");
//        return (num_noleggi_in_corso>0);
        return cittadino.hasactivenoleggio();
    }




}
