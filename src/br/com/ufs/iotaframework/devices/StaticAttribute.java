package br.com.ufs.iotaframework.devices;


/**
 * This class help to represent a static_attribute in JSON format as java Object.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see Device
 */
public class StaticAttribute {
    private String name;
    private String type;
    private String value;

    /**
     * Attributes published as defined.
     *
     * @param name attribute name to publish.
     * @param type attribute type to publish.
     * @param value attribute value to publish.
     */
    public StaticAttribute(String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
