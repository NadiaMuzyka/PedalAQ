package org.pedalaq.Model;

import com.sun.jna.platform.win32.WinBase;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Abbonamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    @ManyToOne
    @JoinColumn(name = "id_tariffa_abbonamento")
    private TariffaAbbonamento tariffaAbbonamento;

    public TariffaAbbonamento getTariffaAbbonamento() {
        return tariffaAbbonamento;
    }

    public void setTariffaAbbonamento(TariffaAbbonamento tariffaAbbonamento) {
        this.tariffaAbbonamento = tariffaAbbonamento;
    }

    public Abbonamento() {}

    public Abbonamento(LocalDate dataInizio, LocalDate dataFine, TariffaAbbonamento tariffaAbbonamento) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.tariffaAbbonamento = tariffaAbbonamento;
    }

    public boolean validaAbbonamento(){
        //confronto tra la data di Inizio e quella di Fine
        //Non viene considerato il tempo in quanto sono di tipo local date
        //Possiamo pensare che se l'abbonamento scade il giorno stesso sia ancora valido
        int comparisonResult = LocalDate.now().compareTo(this.dataFine);
        //il metodo restituisce >0 se sono l'abbonamento è scaduto
        if(comparisonResult > 0){
            return false; //NON VALIDO
        }
        return true;    //VALIDO
    }
}
