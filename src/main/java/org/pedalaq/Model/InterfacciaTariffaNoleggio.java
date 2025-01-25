package org.pedalaq.Model;

import jakarta.persistence.*;

public interface InterfacciaTariffaNoleggio {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;

    public float calcolaCosto(Citta citta);
}
