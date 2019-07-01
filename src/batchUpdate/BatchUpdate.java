package batchUpdate;

import entityLamp.Lamp;
import subscription.Entities;

import java.util.List;

/**
 * This class is used to help to represent a object java as JSON batchupdate on a NGSIv2 form.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */
public class BatchUpdate {
    private String actionType;
    private List<Lamp> entities;

    public BatchUpdate(List<Lamp> entities) {
        this.actionType = "append";
        this.entities = entities;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public List<Lamp> getEntities() {
        return entities;
    }

    public void setEntities(List<Lamp> entities) {
        this.entities = entities;
    }

}
