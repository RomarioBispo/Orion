package com.java.Lamp;

import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.entity.Entity;

public class Lamp extends Entity {

    private Attrs state;
    private Attrs luminosity;
    private Attrs location;
    private Attrs count;
    private Attrs number;

    public Lamp(String id, String type, Attrs state, Attrs luminosity, Attrs location, Attrs count, Attrs number) {

        this.id = id;
        this.type = type;
        this.state = state;
        this.luminosity = luminosity;
        this.location = location;
        this.count = count;
        this.number = number;
    }

    public Lamp() {
    }

    public Attrs getState() {
        return state;
    }

    public void setState(Attrs state) {
        this.state = state;
    }

    public Attrs getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(Attrs luminosity) {
        this.luminosity = luminosity;
    }

    public Attrs getLocation() {
        return location;
    }

    public void setLocation(Attrs location) {
        this.location = location;
    }

    public Attrs getCount() {
        return count;
    }

    public void setCount(Attrs count) {
        this.count = count;
    }

    public Attrs getNumber() {
        return number;
    }

    public void setNumber(Attrs number) {
        this.number = number;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
