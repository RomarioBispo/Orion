package br.com.ufs.iotaframework.devices;

/**
 * This class help to represent a Attribute in JSON format as java Object.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see Device
 */
public class Attribute {
    private String object_id;
    private String name;
    private String type;

    /**
     * Mapping for protocol parameters to entity attributes.
     *
     * @param object_id protocol parameter to be mapped.
     * @param name attribute name to publish.
     * @param type attribute type to publish.
     */
    public Attribute(String object_id, String name, String type) {
        this.object_id = object_id;
        this.name = name;
        this.type = type;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
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
}
