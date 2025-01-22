package org.pedalaq.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("PROMO")
public class TariffaNoleggioPromozione extends InterfacciaTariffaNoleggio{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float costoAlMinuto;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String codice;
    @OneToMany
    @JoinColumn(name = "id_tariffa")
    private List<Noleggio> noleggi;

    @Override
    public float calcolaCosto(Citta citta) {
        return 0;
    }
}
