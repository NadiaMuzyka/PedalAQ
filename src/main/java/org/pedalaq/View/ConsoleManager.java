package org.pedalaq.View;
import org.pedalaq.Controller.NoleggioVeicoloHandler;
import org.pedalaq.Model.*;
import org.pedalaq.Services.HibernateUtil;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
//ConsoleManager si occupa solo di input/output.
public class ConsoleManager {
    private final Scanner scanner;

    public ConsoleManager() {
        this.scanner = new Scanner(System.in);
    }

    //dato per scontato che sono già state selezionate la città ed il cittadino che usa l'applicazione
    // METODO GET CITTADINO BY ID dal database
    //Citta citta_selezionata = new Citta();
    // METODO GET CITTA BY ID dal database
    //Cittadino utente_loggato = new Cittadino();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public void start(Citta citta_selezionata, Cittadino utente_loggato) {
        System.out.println("Benvenuto!");
        boolean running = true;

        while (running) {
            showMenu();
            int choice = readInt("Seleziona la tua scelta: ");
            switch (choice) {
                case 1:
                    //passo 1 del controllore
                    //System.out.println("Esegui operazione 1...");
                    double raggio = readDouble("Inserisci la distanza massima alla quale vuoi noleggiare il veicolo (Km): ");

                    //QUI SI SCEGLIE LO STALLO
                    Stallo stallo_sel = null;
                    while(stallo_sel == null){

                        for (Stallo stallo : NoleggioVeicoloHandler.visualizzaListaStalli(raggio,
                                citta_selezionata,utente_loggato)) {
                            System.out.println(stallo.getId()+ ") " + stallo.getDescrizione() + " con "
                                    + (long) stallo.getVeicolidisp_Stallo().size() + " veicoli disponibili, "
                                    + "distante " +this.TruncateString(String.valueOf(Citta.calculateDistance(utente_loggato.getLat(),utente_loggato.getLng(),
                                    stallo.getLat(),stallo.getLon()))) + " Km da te");
                        }
                        Long id_stallo = readLong("Inserire il codice numerico dello stallo desiderato: ");
                        stallo_sel = citta_selezionata.stallo_by_id(id_stallo);
                        if(stallo_sel == null){
                            System.out.println("!!!Inserire il codice di uno stallo tra quelli disponibili!!!");
                        }
                    }

                    //ricerca lineare sull'array di stalli della città


                    //PASSO 2 DEL CASO D'USO
                    Veicolo veicolo_sel = null;
                    while(veicolo_sel == null){
                        for (Veicolo veicolo : NoleggioVeicoloHandler.getVeicoli(stallo_sel)) {
                        System.out.println(veicolo.getId()+ ") " + veicolo.displayveicolo());
                        }
                        Long id_veicolo = readLong("Inserire il codice numerico del veicolo desiderato: ");
                        veicolo_sel = NoleggioVeicoloHandler.selezionaveicolo(stallo_sel, id_veicolo);
                        if(veicolo_sel == null){
                            System.out.println("!!!Inserire il codice di un veicolo tra quelli disponibili!!!");
                        }
                    }

                    //PASSO 3 SI CREA LA PRENOTAZIONE
                    Prenotazione nuova_prenotazione = NoleggioVeicoloHandler.prenotaVeicolo
                                                        (veicolo_sel,stallo_sel,utente_loggato);
                    if(nuova_prenotazione != null) {
                        System.out.println("codice prenotazione: " + nuova_prenotazione.getId() +
                                            "\ncodice veicolo: " + veicolo_sel.getId() +
                                            "\ncodice sblocco veicolo: " + veicolo_sel.getCodiceSblocco() +
                                            "\nscadenza prenotazione: " + nuova_prenotazione.getScadenza().format(formatter) +
                                            "\n\nProcedi al veicolo e completa il noleggio");
                        //session.save(nuova_prenotazione);  TODO DA METTERE QUESTO SALVATAGGIO
                    }
                    else{
                        System.out.println("Errore prenotazione"); //TODO gestire in qualche modo questo errore
                    }
                    //DOVREBBE FINIRE QUI IL PUNTO 3
                    break;
                case 2:
                    //PASSO 4 DEL CONTROLLORE
                    Prenotazione prenotazione_noleggio = null;
                    while(prenotazione_noleggio == null){
                        Long id_prenotazione = readLong("Per completare il noleggio inserire il codice della prenotazione: ");
                        prenotazione_noleggio = HibernateUtil.findByParameter(
                                Prenotazione.class,"Id",id_prenotazione);
                        if(prenotazione_noleggio == null) {
                            System.out.println("!!!Inserire il codice della prenotazione effettuata!!!");
                        } else if (!(prenotazione_noleggio.controllaPrenotazione())) {
                            System.out.println("!!!Prenotazione scaduta!!!\nRieffettuare la prenotazione del veicolo");
                        } else if (!(prenotazione_noleggio.getCittadino().getId() == utente_loggato.getId())) {
                            System.out.println("!!!Inserire il codice della propria prenotazione!!!");
                        } else {
                            Veicolo veicolo_noleggio = HibernateUtil.findByParameter(
                                    Veicolo.class,"Id",prenotazione_noleggio.getVeicolo().getId());
                            Stallo stallo_partenza = veicolo_noleggio.getStallo();
                            if(NoleggioVeicoloHandler.noleggiaVeicolo(prenotazione_noleggio,stallo_partenza)){ //TODO aggiungere stallo partenza
                                System.out.println("Noleggio iniziato, il veicolo e' sbloccato");
                            }
                            else{
                                System.out.println("Errore nel noleggio"); //TODO da gestire
                            }
                        }
                    }

//                    Veicolo veicolo_noleggio = HibernateUtil.findByParameter(
//                            Veicolo.class,"Id",prenotazione_noleggio.getVeicolo().getId());
//                    Stallo stallo_partenza = veicolo_noleggio.getStallo();
//                    if(NoleggioVeicoloHandler.noleggiaVeicolo(prenotazione_noleggio,stallo_partenza)){ //TODO aggiungere stallo partenza
//                        System.out.println("Noleggio iniziato, il veicolo e' sbloccato");
//                    }
//                    else{
//                        System.out.println("Errore nel noleggio"); //TODO da gestire
//                    }
                    break;
                case 3:
                    System.out.println("Uscita dal programma.");
                    running = false;
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Prenota un veicolo");
        System.out.println("2. Effettua il noleggio di un veicolo (ho gia' effettuato la prenotazione):");
        //-Dfile.encoding=UTF-8 nella VM options per sistemare i caratteri non visibili
        System.out.println("3. Esci");
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Input non valido. Inserisci un numero intero.");
            scanner.next(); // Scarta l'input non valido
        }
        return scanner.nextInt();
    }

    private long readLong(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextLong()) {
            System.out.println("Input non valido. Inserisci un numero intero.");
            scanner.next(); // Scarta l'input non valido
        }
        return scanner.nextLong();
    }

    private Double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Input non valido. Inserisci un numero.");
            scanner.next(); // Scarta l'input non valido
        }
        return scanner.nextDouble();
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    public String TruncateString(String input) {
        int decimalPlaces = 3;
        try {
            double number = Double.parseDouble(input); // Parse input as double
            return String.format("%." + decimalPlaces + "f", number); // Format with precision
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric input: " + input);
        }
    }


}
