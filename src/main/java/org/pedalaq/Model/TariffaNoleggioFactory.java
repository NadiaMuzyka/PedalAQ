package org.pedalaq.Model;


import java.util.ArrayList;
import java.util.List;

//Classe singleton
public class TariffaNoleggioFactory {

    private static TariffaNoleggioFactory instance = null;

    private TariffaNoleggioFactory() {
        instance = this;
    }

    public static TariffaNoleggioFactory getInstance() {

        if (instance == null) {
            instance = new TariffaNoleggioFactory();
        }
        return instance;
    }

    public double creaCompositeTariffa(TariffaNoleggioStandard tariffa, int punti) {

        CompositeTariffaNoleggio compositeTariffa = new CompositeTariffaNoleggio(tariffa);

        //Simuliamo il prelievo del db
        List<InterfacciaTariffaNoleggio> tariffe = new ArrayList<InterfacciaTariffaNoleggio>();

        for (InterfacciaTariffaNoleggio interf : tariffe){
            if(punti > interf.getPuntiRichiesti()) compositeTariffa.aggiungiTariffa(interf);
        }

        return compositeTariffa.calcolaCosto();

    }
}
