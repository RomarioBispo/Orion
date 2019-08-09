package br.com.ufs.examples.room.context.room;

import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.entity.Entity;
import br.com.ufs.orionframework.orion.Orion;

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
    private Orion orion;

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

    public Orion getOrion() {
        return orion;
    }

    public void setOrion(Orion orion) {
        this.orion = orion;
    }

    public int getMaxCapacity() {
        return Integer.parseInt(maxCapacity.getValue());
    }

    public void setMaxCapacity(int maxCapacity) {

        this.maxCapacity.setValue(String.valueOf(maxCapacity));
        orion.updateAttributeData(this.id,"maxCapacity", new Attrs(String.valueOf(maxCapacity),"Integer"));

    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.setValue(name);
        orion.updateAttributeData(this.id,"name", new Attrs(name,"Text"));

    }

    public int getOccupation() {
        return Integer.parseInt(occupation.getValue());
    }

    public void setOccupation(int occupation) {
        this.occupation.setValue(String.valueOf(occupation));
        orion.updateAttributeData(this.id,"occupation", new Attrs(String.valueOf(occupation),"Integer"));
    }

    public Double getTemperature() {
        return Double.parseDouble(this.temperature.getValue());
    }

    public void setTemperature(Double temperature) {
        this.temperature.setValue(String.valueOf(temperature));
        orion.updateAttributeData(this.id,"temperature", new Attrs(String.valueOf(temperature),"Float"));

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
