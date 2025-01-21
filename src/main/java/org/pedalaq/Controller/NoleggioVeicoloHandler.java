package org.pedalaq.Controller;

import org.pedalaq.Model.*;

import java.util.ArrayList;

public class NoleggioVeicoloHandler {

    //PUNTO 1 DEL CASO D'USO
    public static ArrayList<Stallo> visualizzaListaStalli(double raggio, Citta citta, Cittadino cittadino) {
        //cittadino.setPosizione(lat, lon);
        ArrayList<Stallo> stalli;
        stalli = (ArrayList<Stallo>) citta.getStalliRaggio(cittadino.getLat(), cittadino.getLng(), raggio);
        return stalli;
        //Consolemanager.show_stalli()
    }

    //PUNTO 2 DEL CASO D'USO
    public static ArrayList<Veicolo> getVeicoli(Stallo stallo) {
        ArrayList<Veicolo> veicoli = new ArrayList<>();
        veicoli = stallo.getVeicolidisp_Stallo(); //gli passiamo solo i veicoli disponibili per l'uso
        //Consolemanager.show_veicoli(veicoli);
        return veicoli;
    }

    //PUNTO 3 DEL CASO D'USO
    public static Prenotazione prenotaVeicolo(Veicolo veicolo, Stallo stallo, Cittadino cittadino) {

        //Se il cittadino ha un abbonamento attivo
        if (cittadino.controllaAbbonamento()) {
            //Se il cittadino possiede un abbonamento valido
            //Consolemanager.abbonamento_presente()
            if (stallo.bloccaVeicolo(veicolo)) {
                //Se il veicolo è stato bloccato con successo
                return new Prenotazione(veicolo, cittadino);
                //Consolemanager.prenotazione_success()
            }
            else {
                //Consolemanager.veicolo_occupato()
                //gestire qui il caso di veicolo non disponibile al blocco (già bloccato o noleggiato attraverso view
            }
        }
        //Consolemanager.no_abbonamento()
        return null; //gestire la situazione dove il cittadino è sprovvisto di abbonamento valido (attraverso view)
    }

    //PUNTO 4 DEL CASO D'USO
    public static boolean noleggiaVeicolo(Prenotazione prenotazione) {
        //controllo sulla scadenza della prenotazione
        if(prenotazione.controllaPrenotazione()){
            //se la prenotazione non è scaduta creiamo un noleggio
            Noleggio noleggio = new Noleggio(prenotazione);
            //Consolemanager.noleggio_success()
            return true;
        }
        //qui ci dovrebbe essere solo la prenotazione scaduta da gestire
        //Consolemanager.prenotazione_expired()
        return false;
    }

}
