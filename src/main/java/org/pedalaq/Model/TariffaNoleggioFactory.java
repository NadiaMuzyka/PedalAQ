package org.pedalaq.Model;


import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.pedalaq.Services.HibernateUtil;

import javax.persistence.NoResultException;
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

    public double creaCompositeTariffa(TariffaNoleggioStandard tariffa, int punti, Citta citta) {

        CompositeTariffaNoleggio compositeTariffa = new CompositeTariffaNoleggio(tariffa);

        List<InterfacciaTariffaNoleggio> tariffe_promo = citta.getTariffe_promo_attive();

        for (InterfacciaTariffaNoleggio interf : tariffe_promo){
            if(punti > interf.getPuntiRichiesti()) compositeTariffa.aggiungiTariffa(interf);
        }

        return compositeTariffa.calcolaCosto();

    }
}
