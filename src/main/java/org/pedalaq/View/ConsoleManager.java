package org.pedalaq.View;
import java.util.Scanner;
//ConsoleManager si occupa solo di input/output.
public class ConsoleManager {
    private final Scanner scanner;

    public ConsoleManager() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Benvenuto! Seleziona un'opzione:");
        boolean running = true;

        while (running) {
            showMenu();
            int choice = readInt("Inserisci la tua scelta: ");
            switch (choice) {
                case 1:
                    System.out.println("Esegui operazione 1...");
                    break;
                case 2:
                    System.out.println("Esegui operazione 2...");
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
        System.out.println("1. Operazione 1");
        System.out.println("2. Operazione 2");
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

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }
}
