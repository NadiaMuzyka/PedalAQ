package org.pedalaq.Model;

import java.time.LocalDate;

public class TariffaNoleggioPromozione implements InterfacciaTariffaNoleggio{

    private float costoAlMinuto;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String codice;

    @Override
    public float calcolaCosto(Citta citta) {
        return 0;
    }
}
