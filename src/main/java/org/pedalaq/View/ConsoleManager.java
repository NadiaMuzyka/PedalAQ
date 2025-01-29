package org.pedalaq.View;
import org.pedalaq.Controller.NoleggioVeicoloHandler;
import org.pedalaq.Controller.RestituisciVeicoloHandler;
import org.pedalaq.Model.*;
import org.pedalaq.Services.DistanceUtil;
import org.pedalaq.Services.HibernateUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
            showMenu(utente_loggato);
            int choice = readInt("Seleziona la tua scelta: ");
            switch (choice) {
                case 1:
                    //passo 1 del controllore
                    //System.out.println("Esegui operazione 1...");
                    double raggio = readDouble("\nInserisci la distanza massima alla quale vuoi noleggiare il veicolo (Km): ");
                    //long id = 1;   //TODO DA TOGLIERE
                    //System.out.println(citta_selezionata.stallo_by_id(id).getVeicoli() + "aaa");
                    //QUI SI SCEGLIE LO STALLO
                    Stallo stallo_sel = null;
                    while(stallo_sel == null){
                        List<Stallo> stalli;
                        stalli = NoleggioVeicoloHandler.visualizzaListaStalli(raggio,
                                citta_selezionata, utente_loggato);
                        if(stalli.isEmpty()){
                            System.out.println("Nessuno stallo presente nel raggio indicato, " +
                                    "verranno visualizzati tutti gli stalli");
                            stalli = NoleggioVeicoloHandler.visualizzaListaStalli(999999,
                                    citta_selezionata, utente_loggato);
                        }
                        for (Stallo stallo : stalli) {
                            System.out.println(stallo.getId()+ ") " + stallo.getDescrizione() + " con "
                                    + (long) stallo.getVeicolidisp_Stallo().size() + " veicoli disponibili, "
                                    + "distante " +this.TruncateString(String.valueOf(DistanceUtil.calculateDistance(utente_loggato.getLat(),utente_loggato.getLng(),
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
                        System.out.println("\n ----------------- " +
                                            "Codice prenotazione: " + nuova_prenotazione.getId() +
                                            " -----------------" +
                                            "\nCodice veicolo: " + veicolo_sel.getId() +
                                            "\nCodice sblocco veicolo: " + veicolo_sel.getCodiceSblocco() +
                                            "\nScadenza prenotazione: " + nuova_prenotazione.getScadenza().format(formatter) +
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
                    if(NoleggioVeicoloHandler.menunoleggio(utente_loggato)){
                        Prenotazione prenotazione_noleggio = null;
                        while(prenotazione_noleggio == null) {
                            Long id_prenotazione = readLong("Per completare il noleggio inserire il codice della prenotazione: ");
//                            prenotazione_noleggio = HibernateUtil.findByParameter(
//                                    Prenotazione.class, "Id", id_prenotazione);
                            prenotazione_noleggio = utente_loggato.prenotazione_by_id(id_prenotazione);
                            //TODO prenderlo dal db crea una inconsistenza in ram e il menù dinamico non viene corretto

                            if (prenotazione_noleggio == null) {
                                System.out.println("!!!Inserire il codice della prenotazione effettuata!!!");
                            } else if (!(prenotazione_noleggio.getCittadino().getId() == utente_loggato.getId())) {
                                System.out.println("!!!Inserire il codice della propria prenotazione!!!");
                            } else if (!(prenotazione_noleggio.controllaPrenotazione())) {
                                System.out.println("!!!Prenotazione scaduta!!!\nRieffettuare la prenotazione del veicolo");
                            } else {
                                //PRESO L'ID DEL VEICOLO, TORNO ALLO STALLO, POI ALLA CITTà E POI DEVO RISCENDERE
                                Veicolo veicolo_noleggio_temp = HibernateUtil.findByParameter(
                                        Veicolo.class, "Id", prenotazione_noleggio.getVeicolo().getId());
                                Stallo stallo_partenza_temp = veicolo_noleggio_temp.getStallo();
                                //System.out.println("Stallo temp " + stallo_partenza_temp);
                                Stallo stallo_partenza = citta_selezionata.stallo_by_id(stallo_partenza_temp.getId()); //STALLO VERO
                                //System.out.println("Stallo true" + stallo_partenza);
                                //Veicolo veicolo_noleggio = stallo_partenza.veicolo_by_id(veicolo_noleggio_temp.getId());
                                //TODO questo metodo usa un accesso al db, va cambiato
                                if (NoleggioVeicoloHandler.noleggiaVeicolo(prenotazione_noleggio, stallo_partenza, utente_loggato)) {
                                    System.out.println("Noleggio iniziato, il veicolo e' sbloccato");
                                } else {
                                    System.out.println("Errore nel noleggio"); //TODO da gestire
                                }
                            }
                        }
                    }
                    else System.out.println("\n!!!Effettua una prenotazione prima di effettuare il noleggio!!!");
                    break;
                case 3:
                    if(RestituisciVeicoloHandler.menurestituzione(utente_loggato)) {
                        //PASSO 2.0
                        Noleggio noleggio_sel = null;
                        Veicolo veicolo_ric = null;
                        while(veicolo_ric == null){
                            List<Noleggio> noleggi_att= RestituisciVeicoloHandler.mostraNoleggiAttivi(utente_loggato);
                            System.out.println("I tuoi veicoli attualmente noleggiati sono: ");
                            for (Noleggio noleggio : noleggi_att) {  //TODO con 1 veicolo solo ne escono 2 sul display
                                Veicolo veicolo = noleggio.getPrenotazione().getVeicolo();
                                Duration duration = Duration.between(noleggio.getInizioCorsa(),LocalDateTime.now());
                                long ore = duration.toHours();
                                long minuti = duration.toMinutes() % 60;
                                System.out.println(veicolo.getId()+ ") " + veicolo.displayveicolo() + " durata noleggio: "
                                        + ore + ":" + minuti);
                            }
                            Long id_veicolo = readLong("Inserire il codice numerico del veicolo da restituire: ");
                            veicolo_ric = RestituisciVeicoloHandler.selezionaveicolo_ric(utente_loggato, id_veicolo);
                            if(veicolo_ric == null){
                                System.out.println("!!!Inserire il codice di un veicolo tra quelli noleggiati!!!");
                            }
                            else{
                                Stallo stallo_partenza = citta_selezionata.stallo_by_id(veicolo_ric.getStallo().getId());
                                veicolo_ric = stallo_partenza.veicolo_by_id(veicolo_ric.getId());
                                noleggio_sel = veicolo_ric.findnoleggioattivo();
                                noleggio_sel = utente_loggato.noleggio_by_id(noleggio_sel.getId());
                            }
                        }
                        //PASSO 2.1
                        double raggio_ric = readDouble("Inserisci la distanza massima alla quale vuoi " +
                                "effettuare la restituzione del veicolo noleggiato (Km): ");
                        //QUI SI SCEGLIE LO STALLO
                        Stallo stallo_rest = null;
                        while(stallo_rest == null){
                            for (Stallo stallo : RestituisciVeicoloHandler.listaStalliRestituzione(raggio_ric,
                                    citta_selezionata,utente_loggato)) {
                                System.out.println(stallo.getId()+ ") " + stallo.getDescrizione() + " con "
                                        + (long)(stallo.getMaxPosti() - stallo.getVeicolidisp_Stallo().size())  + " posti disponibili per la restituzione, "
                                        + "distante " +this.TruncateString(String.valueOf(DistanceUtil.calculateDistance(utente_loggato.getLat(),utente_loggato.getLng(),
                                        stallo.getLat(),stallo.getLon()))) + " Km da te");
                            }
                            Long id_stallo = readLong("Inserire il codice numerico dello stallo desiderato per la riconsegna: ");
                            stallo_rest = citta_selezionata.stallo_by_id(id_stallo);
                            if(stallo_rest == null){
                                System.out.println("!!!Inserire il codice di uno stallo tra quelli disponibili!!!");
                            }
                        }
                        //PASSO 2.2 posso procedere solo se il veicolo è all'interno dello stallo
                        if(RestituisciVeicoloHandler.selezionaStallo(stallo_rest, veicolo_ric)){
                            double Totale = RestituisciVeicoloHandler.selezionaPagamento(noleggio_sel,utente_loggato,citta_selezionata);
                            System.out.println("Totale noleggio: " + Totale + " $");
                            long punti_da_usare = 99999999;
                            //System.out.println("Hai " + utente_loggato.getPuntiUtilizzabili() + " punti");
                            while(punti_da_usare > utente_loggato.getPuntiUtilizzabili()) {
                                punti_da_usare = readLong("Inseririre il numero di punti da utilizzare (disponibili: " +
                                        + utente_loggato.getPuntiUtilizzabili() + "): ");
                            }

                            if(RestituisciVeicoloHandler.paga((int) punti_da_usare,
                                    Totale,noleggio_sel,stallo_rest,utente_loggato,veicolo_ric)){
                                System.out.println("Pagamento con successo!");
                            }
                            else {
                                System.out.println("Pagamento con un error!");
                            }
                        }
                    }
                    else System.out.println("\n!!!Inizia un noleggio prima di effettuare la restituzione del veicolo!!!");
                    break;
                case 4:
                    System.out.println("Uscita dal programma.");
                    running = false;
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    //MENU DINAMICO SULL'UTENTE
    private void showMenu(Cittadino utente_loggato) {
        System.out.println("\nMenu:");
        System.out.println("1. Prenota un veicolo");
        if(NoleggioVeicoloHandler.menunoleggio(utente_loggato)){  //TODO non funziona appena effettui il noleggio
            //todo con una sola prenotazione senza riavviare il programma (inconsistenza in ram?)
            System.out.println("2. Effettua il noleggio di un veicolo (ho gia' effettuato la prenotazione):");
        }
        //todo non va se ho appena restituito un veicolo
        //System.out.println(utente_loggato.getNoleggi());
        if(RestituisciVeicoloHandler.menurestituzione(utente_loggato)){
            System.out.println("3. Effettua la restituzione di un veicolo");
        }
        System.out.println("4. Esci");
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
