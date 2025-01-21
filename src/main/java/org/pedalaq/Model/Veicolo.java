package org.pedalaq.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public abstract class Veicolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String stato; //Lo stato può essere "Libero", "Prenotato", "Noleggiato"
    protected String codiceSblocco;
    @OneToMany
    @JoinColumn(name = "id_veicolo")
    protected List<Accessorio> accessori;

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getCodiceSblocco() {
        return codiceSblocco;
    }

    public void setCodiceSblocco(String codiceSblocco) {
        this.codiceSblocco = codiceSblocco;
    }

    public List<Accessorio> getAccessori() {
        return accessori;
    }

    public void setAccessori(List<Accessorio> accessori) {
        this.accessori = accessori;
    }

    public boolean bloccaVeicolo(){
        if (Objects.equals(this.stato, "Libero"))
        {
            this.stato = "Bloccato";
            return true;
        }
        return false;
    }
}
