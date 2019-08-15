package br.com.ufs.builtindevices.hybrid.Lamp;

import br.com.ufs.iotaframework.iota.IoTA;
import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.entity.Entity;

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

    public Lamp(Attrs state, Attrs luminosity, Attrs location, Attrs count, Attrs number) {
        this.state = state;
        this.luminosity = luminosity;
        this.location = location;
        this.count = count;
        this.number = number;
    }

    public String getState() {
        return this.state.getValue();
    }

    public void setState(String state, IoTA iota) {
        this.state.setValue(state);
        iota.sendMeasure("lamp"+number.getValue(), "s|"+state);
    }

    public int getLuminosity() {
        return Integer.parseInt(luminosity.getValue());
    }

    public void setLuminosity(int luminosity, IoTA iota) {
        this.luminosity.setValue(String.valueOf(luminosity));
        iota.sendMeasure("lamp"+number.getValue(), "l|"+luminosity);

    }

    public String getLocation() {
        return this.location.getValue();
    }

    public void setLocation(String location, IoTA iota) {

        this.location.setValue(location);
        iota.sendMeasure("lamp"+number.getValue(), "lo|"+location);
    }

    public int getCount() {
        return Integer.parseInt(count.getValue());
    }

    public void setCount(int count, IoTA iota) {
        this.count.setValue(String.valueOf(count));
        iota.sendMeasure("lamp"+number.getValue(), "c|"+count);

    }

    public int getNumber() {
        return Integer.parseInt(number.getValue());
    }

    public void setNumber(int number, IoTA iota) {
        this.number.setValue(String.valueOf(number));
        iota.sendMeasure("lamp"+this.number.getValue(), "n|"+number);

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
