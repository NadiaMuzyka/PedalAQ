package org.pedalaq.Model;

import java.time.LocalDate;

public class TariffaNoleggioStandard implements InterfacciaTariffaNoleggio {
     private float costoAlMinuto;
     private LocalDate dataInizio;
     private LocalDate dataFine;

    @Override
    public float calcolaCosto(Citta citta) {
        return 0;
    }
}
