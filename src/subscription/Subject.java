package subscription;
import java.util.List;


/**
 * This class is used to help to represent a object java as JSON. In this case, used to represent a subscription
 * on a NGSIv2 form.
 *
 * @see Subscription
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */
public class Subject {
    private List<Entities> entities;
    public List<Entities> getEntities() {
        return entities;
    }

    public void setEntities(List<Entities> entities) {
        this.entities = entities;
    }

}
