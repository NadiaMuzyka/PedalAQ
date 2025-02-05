package org.pedalaq.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class TariffaNoleggioPromozione implements InterfacciaTariffaNoleggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float costoAlMinuto;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private int puntiRichiesti;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tariffa")
    private List<Noleggio> noleggi;

    public LocalDate getDataFine() {
        return dataFine;
    }

    //Questo metodo non dovrebbe incorporare solo la variabilità del costo orario, ma anche la possibilità
    //di avere uno sconto di un valore flat se il costo totale supera una certa cifra
    //vedere se aggiungere attributi

    @Override
    public double calcolaCosto() {
        return costoAlMinuto;
    }

    @Override
    public int getPuntiRichiesti(){
        return this.puntiRichiesti;
    }
}
