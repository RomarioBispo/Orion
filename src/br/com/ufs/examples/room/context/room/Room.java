package br.com.ufs.examples.room.context.room;

import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.entity.Entity;

/**
 * This class is used to concept proof for the Orion Framework.
 * To this only purpose, the project developed in Felipe Matheus undergraduate thesis was used.
 * Your project was modified to use the framework.
 *
 * @author Romario Bispo, Felipe Matheus.
 * @version %I%, %G%
 * @since 1.0
 * @see br.com.ufs.examples.room.context.context.ContextExample;
 * */

public class Room extends Entity {
    private Attrs maxCapacity;
    private Attrs name;
    private Attrs occupation;
    private Attrs temperature;

    public Room () {

    }
    public Room(String id, String type, Attrs maxCapacity, Attrs name, Attrs occupation, Attrs temperature) throws Exception {
        this.id = id;
        this.type = type;
        this.maxCapacity = maxCapacity;
        this.name = name;
        this.occupation = occupation;
        this.temperature = temperature;
    }

    public Attrs getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Attrs maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Attrs getName() {
        return name;
    }

    public void setName(Attrs name) {
        this.name = name;
    }

    public Attrs getOccupation() {
        return occupation;
    }

    public void setOccupation(Attrs occupation) {
        this.occupation = occupation;
    }

    public Attrs getTemperature() {
        return temperature;
    }

    public void setTemperature(Attrs temperature) {
        this.temperature = temperature;
    }

    @Override
    public String getType() {
        return this.type;
    }

    // romario
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
