package org.pedalaq.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class TariffaNoleggioStandard implements InterfacciaTariffaNoleggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double costoAlMinuto;
    private LocalDate dataInizio;
    @OneToOne
    @JoinColumn(name = "id_tariffa")
    private Citta citta;

    @Override
    public LocalDate getDataFine() {
        return dataFine;
    }

    private LocalDate dataFine;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tariffa")
    private List<Noleggio> noleggi;

    @Override
    public double calcolaCosto() {
        //TODO da implementare
        return costoAlMinuto;
    }

    @Override
    public int getPuntiRichiesti(){
        return 0;
    }

}
