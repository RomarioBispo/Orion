package br.com.ufs.builtindevices.actuator.bell;

import br.com.ufs.orionframework.entity.Entity;

/**
 * This class is a representation of a bell actuator.
 * A Bell can be sent a command to activate and ring for a short period
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see
 */
public class Bell extends Entity {

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
