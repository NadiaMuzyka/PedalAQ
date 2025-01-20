package org.pedalaq.Model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TARIFFA_TYPE")
public abstract class InterfacciaTariffaNoleggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public abstract float calcolaCosto(Citta citta);
}
