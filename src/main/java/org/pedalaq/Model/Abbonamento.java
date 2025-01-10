package org.pedalaq.Model;

import java.util.Date;

public class Abbonamento {
    private Date dataInizio;
    private Date dataFine;
    private Cittadino cittadino;
    private TariffaAbbonamento tariffaAbbonamento;

    public boolean validaAbbonamento(){
        //confronto tra date
        return true;
    }
}
