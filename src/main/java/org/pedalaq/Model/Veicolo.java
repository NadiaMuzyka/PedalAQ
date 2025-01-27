package org.pedalaq.Model;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pedalaq.Services.HibernateUtil;

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
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_veicolo")
    protected List<Accessorio> accessori;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_veicolo")
    protected List<Prenotazione> prenotazioni;
    @ManyToOne
    @JoinColumn(name = "id_stallo")
    private Stallo stallo;

    public Stallo getStallo() {
        return stallo;
    }

    public void setStallo(Stallo stallo) {
        this.stallo = stallo;
    }

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
        //System.out.println("Bloccato1");
        if (Objects.equals(this.stato, "Libero"))
        {
            this.stato = "Prenotato";
            //System.out.println("Prenotato");
            this.aggiornaVeicolo();
            return true;
        }
        return false;
    }


    public boolean NoleggiaVeicolo(){
        //System.out.println("Bloccato1");
        if (Objects.equals(this.stato, "Prenotato"))
        {
            this.stato = "Noleggiato";
            //System.out.println("Noleggiato");
            this.aggiornaVeicolo();
            return true;
        }
        return false;
    }


    private void aggiornaVeicolo(){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(this);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Effettua il rollback in caso di errore
            }
            throw new RuntimeException("Errore durante il salvataggio della prenotazione: " + e.getMessage(), e);
        }
    }

    public String displayveicolo(){
        return "";
    }

}
