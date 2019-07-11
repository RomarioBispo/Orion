package br.com.ufs.orionframework.subscription;
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
public class Condition {
    public Condition(List<String> attrs) {
        this.attrs = attrs;
    }

    private List<String> attrs;

    public List<String> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<String> attrs) {
        this.attrs = attrs;
    }
}
