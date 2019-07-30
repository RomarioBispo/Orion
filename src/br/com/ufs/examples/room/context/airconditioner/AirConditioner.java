package br.com.ufs.examples.room.context.airconditioner;

import br.com.ufs.orionframework.entity.Attrs;

/**
 * This class is used to concept proof for the Orion Framework.
 * To this only purpose, the project developed in Felipe Matheus undergraduate thesis was used.
 * Your project was modified to use the framework.
 *
 * @author Romario Bispo, Felipe Matheus.
 * @version %I%, %G%
 * @since 1.0
 * @see br.com.ufs.examples.room.context.context.ContextExample;
 */
public class AirConditioner {
    private Attrs name;
    private Attrs state;
    private Attrs temperature;
    private Attrs mode;

    public Attrs getName() {
        return name;
    }

    public void setName(Attrs name) {
        this.name = name;
    }

    public Attrs getState() {
        return state;
    }

    public void setState(Attrs state) {
        this.state = state;
    }

    public Attrs getTemperature() {
        return temperature;
    }

    public void setTemperature(Attrs temperature) {
        this.temperature = temperature;
    }

    public Attrs getMode() {
        return mode;
    }

    public void setMode(Attrs mode) {
        this.mode = mode;
    }


}
