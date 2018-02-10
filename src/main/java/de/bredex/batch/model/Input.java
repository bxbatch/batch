package de.bredex.batch.model;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Input {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double value;

    public Input() {
        value = new Random().nextDouble();
    }

    public long getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setValue(final double value) {
        this.value = value;
    }
}
