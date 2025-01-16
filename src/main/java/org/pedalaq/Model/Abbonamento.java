package org.pedalaq.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Abbonamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dataInizio;
    private Date dataFine;
    @ManyToOne
    @JoinColumn(name = "tariffa_abbonamento_id")
    private TariffaAbbonamento tariffaAbbonamento;

    public TariffaAbbonamento getTariffaAbbonamento() {
        return tariffaAbbonamento;
    }

    public void setTariffaAbbonamento(TariffaAbbonamento tariffaAbbonamento) {
        this.tariffaAbbonamento = tariffaAbbonamento;
    }

    public Abbonamento() {}

    public Abbonamento(Date dataInizio, Date dataFine, TariffaAbbonamento tariffaAbbonamento) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.tariffaAbbonamento = tariffaAbbonamento;
    }

    public boolean validaAbbonamento(){
        //confronto tra date
        return true;
    }
}
