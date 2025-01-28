package org.pedalaq.Controller;

import org.pedalaq.Model.*;
import org.pedalaq.Services.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NoleggioVeicoloHandler {

    //PUNTO 1 DEL CASO D'USO
    public static List<Stallo> visualizzaListaStalli(double raggio, Citta citta, Cittadino cittadino) {
        //cittadino.setPosizione(lat, lon);
        List<Stallo> stalli;
        if(raggio <= 0){
            raggio = 0.0000001;
        }
        if(raggio == 999999){//Caso particolare
            stalli = citta.getStalli();
        }
        else{
            stalli = citta.getStalliRaggioPrenotazione(cittadino.getLat(), cittadino.getLng(), raggio);
        }
        return stalli;
    }

    public static Stallo selezionastallo(Citta citta, Long id_stallo) {
        Stallo stallo_sel = citta.stallo_by_id(id_stallo);
        if(null != stallo_sel) {
            return null;
        }
        else {
            return stallo_sel;
        }
    }

    //PUNTO 2 DEL CASO D'USO
    public static ArrayList<Veicolo> getVeicoli(Stallo stallo) {
        ArrayList<Veicolo> veicoli = new ArrayList<>();
        veicoli = stallo.getVeicolidisp_Stallo(); //gli passiamo solo i veicoli disponibili per l'uso
        return veicoli;
    }

    //se il veicolo è effettivamente selezionabile lo ritorno
    public static Veicolo selezionaveicolo(Stallo stallo, Long id_veicolo) {
        Veicolo veicolo_sel = stallo.veicolo_by_id(id_veicolo);
        if (veicolo_sel != null && Objects.equals(veicolo_sel.getStato(), "Libero")) {
            return veicolo_sel; //Se esiste ed è libero è selezionabile
        }
        else {
            return null;
        }
    }

    //PUNTO 3 DEL CASO D'USO
    public static Prenotazione prenotaVeicolo(Veicolo veicolo, Stallo stallo, Cittadino cittadino) {
        //System.out.println("veicolo_sel + stallo_sel + utente_loggato");
        //Se il cittadino ha un abbonamento attivo
        if (cittadino.controllaAbbonamento()) {
            //System.out.println("Bloccato");
            //Se il cittadino possiede un abbonamento valido
            if (stallo.bloccaVeicolo(veicolo)) {
                //Se il veicolo è stato bloccato con successo
                Prenotazione p = new Prenotazione(veicolo, cittadino); //questa non ha l'id
                cittadino.addPrenotazione(p);//TODO controllare che salvi nel db
                HibernateUtil.saveprenotazione_bloccaveicolo(p,veicolo,cittadino);
                //Ora la riprendo dal db per poter mostrare all'utente il codice della prenotazione
                p = HibernateUtil.getprenotazionefromidveicolo(p.getVeicolo().getId()); //questa ha l'id
                return p;
            }
            else {
                //gestire qui il caso di veicolo non disponibile al blocco (già bloccato o noleggiato attraverso view)
            }
        }
        return null; //gestire la situazione dove il cittadino è sprovvisto di abbonamento valido (attraverso view)
    }

    //PUNTO 4 DEL CASO D'USO
    public static boolean noleggiaVeicolo(Prenotazione prenotazione, Stallo stalloPartenza ,Cittadino cittadino) {
        //controllo sulla scadenza della prenotazione
        if(prenotazione.controllaPrenotazione()){
            //se la prenotazione non è scaduta creiamo un noleggio
            Noleggio noleggio = new Noleggio(prenotazione, stalloPartenza,cittadino);
            //TODO aggiungere il noleggio al cittadino DA TESTARE
            cittadino.addNoleggioAttivo(noleggio);
            prenotazione.setNoleggio(noleggio);

            prenotazione.getVeicolo().NoleggiaVeicolo();
            HibernateUtil.savenoleggio_noleggiaaveicolo(noleggio, prenotazione.getVeicolo(), prenotazione);
            //SALVATAGGIO NOLEGGIO E CAMBIO DI STATO DEL VEICOLO
            prenotazione.getCittadino().addNoleggioAttivo(noleggio);
            return true;
        }
        //qui ci dovrebbe essere solo la prenotazione scaduta da gestire
        return false;
    }

    //GESTIONE AGGIORNAMENTO VEICOLI
    public static boolean aggiornaveicolo(Veicolo veicolo) {
        HibernateUtil.saveOrUpdateWithTransaction(veicolo);
        return true;
    }

    //per il menu dinamico controllo se il cittadino ha almeno una prenotazione non scaduta E NON ASSOCIATA AD UN NOLEGGIO
    //si prende quella con la scadenza maggiore
    public static boolean menunoleggio(Cittadino cittadino) {
        System.out.println(cittadino.hasactiveprenotazione());
        return cittadino.hasactiveprenotazione();
    }


}
