package org.pedalaq.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class TariffaNoleggioPromozione implements InterfacciaTariffaNoleggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float costoAlMinuto;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String codice;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tariffa")
    private List<Noleggio> noleggi;


    //Questo metodo non dovrebbe incorporare solo la variabilità del costo orario, ma anche la possibilità
    //di avere uno sconto di un valore flat se il costo totale supera una certa cifra
    //vedere se aggiungere attributi

    @Override
    public double calcolaCosto(Citta citta, double tempo) {
        double costo = tempo * this.costoAlMinuto;
        if(tempo > 60){  //Possiamo mettere che se usa il veicolo per più di 1 ora si ha uno sconto flat
            //oppure una riduzione del costo orario
            costo = costo - 10;  //questo 10 può essere messo come attributo della tariffa noleggio
        }
        return costo;
    }
}
