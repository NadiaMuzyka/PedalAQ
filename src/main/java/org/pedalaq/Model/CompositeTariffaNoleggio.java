package org.pedalaq.Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class CompositeTariffaNoleggio implements InterfacciaTariffaNoleggio {

    private ArrayList<InterfacciaTariffaNoleggio> tariffe = new ArrayList<>();

    public void aggiungiTariffa(InterfacciaTariffaNoleggio tariffa) {
        this.tariffe.add(tariffa);
    }

    public CompositeTariffaNoleggio(TariffaNoleggioStandard tariffaStandard) {
        this.aggiungiTariffa(tariffaStandard);
        //System.out.println("vai a fanculo" + tariffaStandard);
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

    @Override
    public LocalDate getDataFine() {
        return null;
    }


}
