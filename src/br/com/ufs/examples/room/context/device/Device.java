package br.com.ufs.examples.room.context.device;

public class Device {
    private String device_id;

    private String entity_name;

    private String entity_type;

    private String timezone;

    private Attribute[] attributes;

    private StaticAttribute[] static_attributes;

    public Device(String device_id, String entity_name, String entity_type, String timezone, Attribute[] attributes, StaticAttribute[] static_attributes) {
        this.device_id = device_id;
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

    public Attribute[] getAttributes() {
        return attributes;
    }

    public void setAttributes(Attribute[] attributes) {
        this.attributes = attributes;
    }

    public StaticAttribute[] getStatic_attributes() {
        return static_attributes;
    }

    public void setStatic_attributes(StaticAttribute[] static_attributes) {
        this.static_attributes = static_attributes;
    }

    public static class Attribute {
        private String object_id;

        private String name;

        private String type;

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

    public static class StaticAttribute {
        private String name;

        private String type;

        private String value;

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
}
