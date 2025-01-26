package org.pedalaq.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MONOPATTINO")
public class Monopattino extends Veicolo{
    private int batteria;

    @Override
    public String displayveicolo() {
        return "Monopattino batteria al " + this.getBatteria() + "%";
    }

    public Monopattino() {
    }

    public int getBatteria() {
        return batteria;
    }

    public void setBatteria(int batteria) {
        this.batteria = batteria;
    }


}
