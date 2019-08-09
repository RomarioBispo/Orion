package br.com.ufs.builtindevices.sensor.motion;

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

public class Motion extends Entity {
    private Attrs count;
    private Orion orion;

    public Motion() {

    }

    public Motion (Orion orion) {
        this.orion = orion;
    }

    public Orion getOrion() {
        return orion;
    }

    public void setOrion(Orion orion) {
        this.orion = orion;
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

    public int getCount() {
        return Integer.parseInt(count.getValue());
    }

    public void setCount(int count) {
        this.count.setValue(String.valueOf(count));
        orion.updateAttributeData(this.id, "count", new Attrs(String.valueOf(count),"Integer"));
    }
}
