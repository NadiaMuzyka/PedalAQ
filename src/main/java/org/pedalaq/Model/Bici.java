package org.pedalaq.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BICI")
public class Bici extends Veicolo{

    public Bici() {
    }

    @Override
    public String displayveicolo() {
        return "Bici";
    }
}
