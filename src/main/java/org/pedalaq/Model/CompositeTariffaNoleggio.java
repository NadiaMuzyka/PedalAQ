package org.pedalaq.Model;

import java.util.List;

public class CompositeTariffaNoleggio implements InterfacciaTariffaNoleggio {

    private List<InterfacciaTariffaNoleggio> tariffa;

    public void aggiungiTariffa(InterfacciaTariffaNoleggio tariffa) {
        this.tariffa.add(tariffa);
    }

    public CompositeTariffaNoleggio(TariffaNoleggioStandard tariffaStandard) {
        this.tariffa.add(tariffaStandard);
    }

    @Override
    public double calcolaCosto() {
        //TODO da implementare
        return 0;
    }

    @Override
    public int getPuntiRichiesti(){
        return 0;
    }
}
