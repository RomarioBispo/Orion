package br.com.ufs.orionframework.subscription;


/**
 * This class is used to help to represent a object java as JSON. In this case, used to represent a br.com.ufs.orionframework.subscription
 * on a NGSIv2 form.
 *
 * @see Subscription
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */
public class Entities {
    private String idPattern;
    private String type;

    public Entities(String idPattern, String type) {
        this.idPattern = idPattern;
        this.type = type;
    }

    public String getIdPattern() {
        return idPattern;
    }

    public void setIdPattern(String idPattern) {
        this.idPattern = idPattern;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
