package br.com.ufs.builtindevices.hybrid.airconditioner;

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
 */
public class AirConditioner extends Entity {
    private Attrs name;
    private Attrs state;
    private Attrs temperature;
    private Attrs mode;
    private Orion orion;

    public AirConditioner() {

    }

    public AirConditioner(Orion orion) {
        this.orion = orion;
    }

    public Orion getOrion() {
        return orion;
    }

    public void setOrion(Orion orion) {
        this.orion = orion;
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {

        this.name.setValue(name);
        orion.updateAttributeData(this.id, "name", new Attrs(name, "Text"));
    }

    public String getState() {
        return this.state.getValue();
    }

    public void setState(String state) {
        this.state.setValue(state);
        orion.updateAttributeData(this.id, "state", new Attrs(state, "Text"));
    }

    public Double getTemperature() {
        return Double.parseDouble(temperature.getValue());
    }

    public void setTemperature(Double temperature) {
        this.temperature.setValue(String.valueOf(temperature));
        orion.updateAttributeData(this.id, "temperature", new Attrs(String.valueOf(temperature), "Text"));

    }

    public String getMode() {
        return mode.getValue();
    }

    public void setMode(String mode) {
        this.mode.setValue(mode);
        orion.updateAttributeData(this.id, "mode", new Attrs(mode, "Text"));
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
