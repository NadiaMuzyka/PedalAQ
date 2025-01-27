package org.pedalaq.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

public interface InterfacciaTariffaNoleggio {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;

    public double calcolaCosto();

    public int getPuntiRichiesti();

    public LocalDate getDataFine();
}
