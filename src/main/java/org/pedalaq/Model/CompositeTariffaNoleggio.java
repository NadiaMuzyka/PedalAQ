package org.pedalaq.Model;

import java.util.ArrayList;
import java.util.List;

public class CompositeTariffaNoleggio implements InterfacciaTariffaNoleggio {

    private ArrayList<InterfacciaTariffaNoleggio> tariffe;

    public void aggiungiTariffa(InterfacciaTariffaNoleggio tariffa) {

        this.tariffe.add(tariffa);
    }

    public CompositeTariffaNoleggio(TariffaNoleggioStandard tariffaStandard) {
        this.aggiungiTariffa(tariffaStandard);
    }

    @Override
    public double calcolaCosto() {
        double min_costo = 99999999; //decidere una tariffa di default
        for (InterfacciaTariffaNoleggio tariffa : tariffe){
            min_costo = Math.min(tariffa.calcolaCosto(), min_costo);
        }
        return min_costo;
    }

    @Override
    public int getPuntiRichiesti(){
        return 0;
    }
}
