package br.com.ufs.iotaframework.devices;


import java.util.List;

/**
 * This class help to represent a Device in JSON format as java Object.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */
public class Device {

    private String device_id;
    private String protocol;
    private String entity_name;
    private String entity_type;
    private String timezone;
    private List<Attribute> attributes;
    private List<StaticAttribute> static_attributes;
    private List<Command> commands;


    /**
     * Create a Device object.
     *
     * @param device_id Unique identifier into a service.
     * @param protocol Protocol assigned to device.
     * @param entity_name Entity name used for entity publication (overload default).
     * @param entity_type Entity type used for entity publication (overload entity_type defined in service).
     * @param timezone Not used in this version.
     * @param attributes Mapping for protocol parameters to entity attributes.
     * @param static_attributes Attributes published as defined.
     * @param commands Attributes working as commands.
     */
    public Device(String device_id, String protocol, String entity_name, String entity_type, String timezone, List<Attribute> attributes, List<StaticAttribute> static_attributes, List<Command> commands) {
        this.device_id = device_id;
        this.protocol = protocol;
        this.entity_name = entity_name;
        this.entity_type = entity_type;
        this.timezone = timezone;
        this.attributes = attributes;
        this.static_attributes = static_attributes;
        this.commands = commands;
    }

    public Device(String device_id, String protocol, String entity_name, String entity_type, String timezone, List<Attribute> attributes) {
        this.device_id = device_id;
        this.protocol = protocol;
        this.entity_name = entity_name;
        this.entity_type = entity_type;
        this.timezone = timezone;
        this.attributes = attributes;
    }

    public Device(String device_id, String protocol, String entity_name, String entity_type, String timezone, List<Attribute> attributes, List<StaticAttribute> static_attributes) {
        this.device_id = device_id;
        this.protocol = protocol;
        this.entity_name = entity_name;
        this.entity_type = entity_type;
        this.timezone = timezone;
        this.attributes = attributes;
        this.static_attributes = static_attributes;
    }


    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public String getEntity_type() {
        return entity_type;
    }

    public void setEntity_type(String entity_type) {
        this.entity_type = entity_type;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<StaticAttribute> getStatic_attributes() {
        return static_attributes;
    }

    public void setStatic_attributes(List<StaticAttribute> static_attributes) {
        this.static_attributes = static_attributes;
    }
    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }
}
