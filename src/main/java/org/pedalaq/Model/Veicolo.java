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
    protected double lat; //TODO vedere dove settare questi parametri
    protected double lon;
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

    public String getVeicoloType(){
        return this.getClass().getSimpleName();
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


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
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
            this.stato = "Prenotato";
            this.aggiornaVeicolo();
            return true;
        }
        return false;
    }


    public boolean NoleggiaVeicolo(){
        if (Objects.equals(this.stato, "Prenotato"))
        {
            this.stato = "Noleggiato";
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

    public String displayveicolotype(){
        return "";
    }


    public boolean rendiDisponibile(){
        this.stato = "Libero";
        return true;
    }

    public Noleggio findnoleggioattivo() {
        for (Prenotazione p : this.prenotazioni){
            if(p.getNoleggio().getFineCorsa() == null){
                return p.getNoleggio();
            }
        }
        return null;
    }


    public void addprenotazione(Prenotazione prenotazione) {
        this.prenotazioni.add(prenotazione);
    }
}
