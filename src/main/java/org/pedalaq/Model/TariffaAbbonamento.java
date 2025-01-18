package org.pedalaq.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class TariffaAbbonamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double costo;
    private LocalDate dataInizio;
    private LocalDate dataFine;

    public TariffaAbbonamento(Double costo, LocalDate dataInizio, LocalDate dataFine) {
        //this.id = id;
        this.costo = costo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public TariffaAbbonamento() {}
}

