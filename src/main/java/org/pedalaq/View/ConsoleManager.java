package org.pedalaq.View;
import org.hibernate.type.descriptor.jdbc.internal.DelayedStructJdbcType;
import org.pedalaq.Controller.NoleggioVeicoloHandler;
import org.pedalaq.Model.*;
import org.pedalaq.Services.HibernateUtil;

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
                    int raggio = readInt("Inserisci la distanza massima alla quale vuoi noleggiare il veicolo (Km): ");
                    for (Stallo stallo : NoleggioVeicoloHandler.visualizzaListaStalli(raggio,
                                        citta_selezionata,utente_loggato)) {
                        System.out.println(stallo.getId()+ ") " + stallo.getDescrizione() + " distante "
                                + this.TruncateString(String.valueOf(Citta.calculateDistance(utente_loggato.getLat(),utente_loggato.getLng(),
                                stallo.getLat(),stallo.getLon()))) + " Km da te");
                    }
                    //QUI SI SCEGLIE LO STALLO
                    Long id_stallo = readLong("Inserire il codice numerico dello stallo desiderato: ");
                    //ricerca lineare sull'array di stalli della città
                    Stallo stallo_sel = citta_selezionata.stallo_by_id(id_stallo);
                    //PASSO 2 DEL CASO D'USO
                    for (Veicolo veicolo : NoleggioVeicoloHandler.getVeicoli(stallo_sel)) {
                        System.out.println(veicolo.getId()+ ") "); //TODO DA AGGIUNGERE TESTO
                    }
                    Long id_veicolo = readLong("Inserire il codice numerico del veicolo desiderato: ");

                    Veicolo veicolo_sel = stallo_sel.veicolo_by_id(id_veicolo);
                    //PASSO 3 SI CREA LA PRENOTAZIONE
                    //System.out.println(veicolo_sel);
                    //System.out.println(stallo_sel);
                    //System.out.println(utente_loggato);
                    Prenotazione nuova_prenotazione = NoleggioVeicoloHandler.prenotaVeicolo
                                                        (veicolo_sel,stallo_sel,utente_loggato);
                    if(nuova_prenotazione != null) {
                        System.out.println("codice prenotazione:" + nuova_prenotazione.getId() +
                                            "\ncodice veicolo: " + veicolo_sel.getId() +
                                            "\ncodice sblocco veicolo: " + veicolo_sel.getCodiceSblocco() +
                                            "\nscadenza prenotazione: " + nuova_prenotazione.getScadenza() +
                                            "\nProcedi al veicolo e completa il noleggio");
                        //session.save(nuova_prenotazione);  TODO DA METTERE QUESTO SALVATAGGIO
                    }
                    else{
                        System.out.println("Errore prenotazione"); //TODO gestire in qualche modo questo errore
                    }
                    //DOVREBBE FINIRE QUI IL PUNTO 3
                    break;
                case 2:
                    //PASSO 4 DEL CONTROLLORE
                    Long id_prenotazione = readLong("Per completare il noleggio inserire il codice della prenotazione: ");
                    //ricerco la prenotazione tra quelle del cittadino
                    //Prenotazione prenotazione_noleggio = utente_loggato.prenotazione_by_id(id_prenotazione);
                    Prenotazione prenotazione_noleggio = HibernateUtil.getprenotazionefromid(id_prenotazione);
                    if(NoleggioVeicoloHandler.noleggiaVeicolo(prenotazione_noleggio)){
                        System.out.println("Noleggio iniziato, il veicolo è sbloccato");
                    }
                    else{
                        System.out.println("Errore nel noleggio"); //TODO da gestire
                    }

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
        System.out.println("2. Effettua il noleggio di un veicolo (ho già effettuato la prenotazione):");
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
