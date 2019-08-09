package br.com.ufs.builtindevices.hybrid.Lamp;

import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.entity.Entity;
import br.com.ufs.orionframework.orion.Orion;

/**
 * This class is used to concept proof for the Orion Framework.
 * To this only purpose, the project developed in Mariana Martins undergraduate thesis was used.
 * Your project was modified to use the framework.
 *
 * @author Romario Bispo, Mariana Martins.
 * @version %I%, %G%
 * @since 1.0
 * @see br.com.ufs.examples.square.context.socket.SquareExample
 * */

public class Lamp extends Entity {

    private Attrs state;
    private Attrs luminosity;
    private Attrs location;
    private Attrs count;
    private Attrs number;
    private Orion orion = new Orion();

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

    public Lamp(Attrs state, Attrs luminosity, Attrs location, Attrs count, Attrs number, Orion orion) {
        this.state = state;
        this.luminosity = luminosity;
        this.location = location;
        this.count = count;
        this.number = number;
        this.orion = orion;
    }

    public Orion getOrion() {
        return orion;
    }

    public void setOrion(Orion orion) {
        this.orion = orion;
    }

    public String getState() {
        return this.state.getValue();
    }

    public void setState(String state) {
        this.state.setValue(state);
        orion.updateAttributeData(this.id, "state", new Attrs(state, "Text"));
    }

    public int getLuminosity() {
        return Integer.parseInt(luminosity.getValue());
    }

    public void setLuminosity(int luminosity) {
        this.luminosity.setValue(String.valueOf(luminosity));
        orion.updateAttributeData(this.id, "luminosity", new Attrs(String.valueOf(luminosity),"Integer"));

    }

    public String getLocation() {
        return this.location.getValue();
    }

    public void setLocation(String location) {

        this.location.setValue(location);
        orion.updateAttributeData(this.id, "location", new Attrs(location,"geo:point"));
    }

    public int getCount() {
        return Integer.parseInt(count.getValue());
    }

    public void setCount(int count) {
        this.count.setValue(String.valueOf(count));
        orion.updateAttributeData(this.id, "count", new Attrs(String.valueOf(count),"Integer"));

    }

    public int getNumber() {
        return Integer.parseInt(number.getValue());
    }

    public void setNumber(int number) {
        this.number.setValue(String.valueOf(number));
        orion.updateAttributeData(this.id, "number", new Attrs(String.valueOf(number),"Integer"));

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
