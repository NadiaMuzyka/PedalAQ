package org.pedalaq.Controller;

import org.pedalaq.Model.*;
import org.pedalaq.Services.HibernateUtil;

import java.util.ArrayList;

public class NoleggioVeicoloHandler {

    //PUNTO 1 DEL CASO D'USO
    public static ArrayList<Stallo> visualizzaListaStalli(double raggio, Citta citta, Cittadino cittadino) {
        //cittadino.setPosizione(lat, lon);
        ArrayList<Stallo> stalli;
        stalli = (ArrayList<Stallo>) citta.getStalliRaggio(cittadino.getLat(), cittadino.getLng(), raggio);
        return stalli;
    }

    //PUNTO 2 DEL CASO D'USO
    //todo l'accesso al db per controllare le prenotazioni scadute
    public static ArrayList<Veicolo> getVeicoli(Stallo stallo) {
        ArrayList<Veicolo> veicoli = new ArrayList<>();
        veicoli = stallo.getVeicolidisp_Stallo(); //gli passiamo solo i veicoli disponibili per l'uso
        return veicoli;
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

}
