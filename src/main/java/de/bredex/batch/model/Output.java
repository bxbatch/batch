package de.bredex.batch.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Output {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int value;

    public long getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setValue(final int value) {
        this.value = value;
    }
}
