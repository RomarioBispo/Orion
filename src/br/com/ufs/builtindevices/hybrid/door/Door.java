package br.com.ufs.builtindevices.hybrid.door;

import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.entity.Entity;
import br.com.ufs.orionframework.orion.Orion;


/**
 * A Smart Door is an electronic door which can be sent commands to be locked or unlocked remotely.
 * It can also report on its current state (OPEN, CLOSED or LOCKED),
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see
 */

public class Door extends Entity {
    private Attrs state;
    private Orion orion;

    public Door() {
    }

    public Door(Attrs state) {
        this.state = state;
    }

    public String getState() {
        return state.getValue();
    }

    public void setState(String state) {
        this.state.setValue(state);
        orion.updateAttributeData(this.id, "state", new Attrs(state, "Text"));
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
