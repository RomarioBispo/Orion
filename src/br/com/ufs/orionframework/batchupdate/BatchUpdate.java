package br.com.ufs.orionframework.batchupdate;

import br.com.ufs.orionframework.entity.Entity;

import java.util.List;

/**
 * This class is used to help to represent a object java as JSON batchupdate payload on a NGSIv2 form.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */
public class BatchUpdate {
    private String actionType;
    private List<Entity> entities;

    /**
     * This constructor instantiate a batch update object with a given list of entities.
     * Here, we set up a actionType field as "append" maps to POST /v2/entities (if the entity does not already exist) or POST /v2/entities/<id>/attrs
     * (if the entity already exists).
     * @param entities
     */
    public BatchUpdate(List<Entity> entities) {
        this.actionType = "append";
        this.entities = entities;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

}
