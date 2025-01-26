package org.pedalaq.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BICIELETTRICA")
public class BiciElettrica extends Veicolo{
    private int batteria;

    @Override
    public String displayveicolo() {
        return "Bici Elettrica batteria al " + this.getBatteria() + "%";
    }

    public BiciElettrica() {
    }

    public BiciElettrica(int batteria) {

        this.batteria = batteria;
    }

    public int getBatteria() {
        return batteria;
    }

    public void setBatteria(int batteria) {
        this.batteria = batteria;
    }


}
