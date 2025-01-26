package org.pedalaq.Controller;

import org.pedalaq.Model.*;
import org.pedalaq.Services.HibernateUtil;

import java.util.ArrayList;
import java.util.Objects;

public class NoleggioVeicoloHandler {

    //PUNTO 1 DEL CASO D'USO
    public static ArrayList<Stallo> visualizzaListaStalli(double raggio, Citta citta, Cittadino cittadino) {
        //cittadino.setPosizione(lat, lon);
        if(raggio <= 0){
            raggio = 0.000001;
        }
        ArrayList<Stallo> stalli;
        stalli = (ArrayList<Stallo>) citta.getStalliRaggio(cittadino.getLat(), cittadino.getLng(), raggio);
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

    public static Veicolo selezionaveicolo(Stallo stallo, Long id_veicolo) {
        Veicolo veicolo_sel = stallo.veicolo_by_id(id_veicolo);
        if (veicolo_sel != null && !Objects.equals(veicolo_sel.getStato(), "Libero")) {
            return null;
        }
        else {
            return veicolo_sel;
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
                Prenotazione p = new Prenotazione(veicolo, cittadino);
                HibernateUtil.saveprenotazione_bloccaveicolo(p,veicolo);
                //System.out.println(p.getVeicolo().getId());
                //p = HibernateUtil.getprenotazionefromidveicolo(p.getVeicolo().getId());
                return p;
            }
            else {
                //gestire qui il caso di veicolo non disponibile al blocco (già bloccato o noleggiato attraverso view
            }
        }
        return null; //gestire la situazione dove il cittadino è sprovvisto di abbonamento valido (attraverso view)
    }

    //PUNTO 4 DEL CASO D'USO
    public static boolean noleggiaVeicolo(Prenotazione prenotazione) {
        //controllo sulla scadenza della prenotazione
        if(prenotazione.controllaPrenotazione()){
            //se la prenotazione non è scaduta creiamo un noleggio
            Noleggio noleggio = new Noleggio(prenotazione);
            prenotazione.getVeicolo().NoleggiaVeicolo();
            HibernateUtil.savenoleggio_noleggiaaveicolo(noleggio, prenotazione.getVeicolo(), prenotazione);
            //SALVATAGGIO NOLEGGIO E CAMBIO DI STATO DEL VEICOLO
            return true;
        }
        //qui ci dovrebbe essere solo la prenotazione scaduta da gestire
        return false;
    }

    //GESTIONE CANCELLAZIONE PRENOTAZIONI
    public static boolean cancellaprenotazione(Prenotazione prenotazione) {
        HibernateUtil.deleteWithTransaction(prenotazione);
        return true;
    }

    //GESTIONE AGGIORNAMENTO VEICOLI
    public static boolean aggiornaveicolo(Veicolo veicolo) {
        HibernateUtil.saveOrUpdateWithTransaction(veicolo);
        return true;
    }



}
