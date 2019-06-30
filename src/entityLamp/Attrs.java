package entityLamp;


/**
 * This class is used to help to represent a object java as JSON on a NGSIv2 form.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see Lamp
 */
public class Attrs {
    private String value;
    private String type;


    public Attrs(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }



}