package org.pedalaq.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BICI")
public class Bici extends Veicolo{
}
