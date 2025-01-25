package org.pedalaq.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class TariffaNoleggioStandard implements InterfacciaTariffaNoleggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float costoAlMinuto;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tariffa")
    private List<Noleggio> noleggi;

    @Override
    public float calcolaCosto(Citta citta) {
        return 0;
    }
}
