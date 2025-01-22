package org.pedalaq.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "VEICOLO_TYPE")
public abstract class Veicolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String stato; //Lo stato può essere "Libero", "Prenotato", "Noleggiato"
    protected String codiceSblocco;
    @OneToMany
    @JoinColumn(name = "id_veicolo")
    protected List<Accessorio> accessori;

    public Veicolo() {

    }

    public String getStato() {
        return stato;
    }

    public Long getId() {
        return id;
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
        System.out.println("Bloccato1");
        if (Objects.equals(this.stato, "Libero"))
        {
            this.stato = "Bloccato";
            System.out.println("Bloccato");
            return true;
        }
        return false;
    }


}
