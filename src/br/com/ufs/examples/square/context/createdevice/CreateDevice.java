package com.java.createdevice;

public class CreateDevice {

	
	/*
'{
  "devices": [
    {
      "device_id": "lamp001",
      "entity_name": "urn:ngsi-ld:Lamp:001",
      "entity_type": "Lamp",
      "protocol": "PDI-IoTA-UltraLight",
      "transport": "HTTP",
      "endpoint": "http://iot-sensors:3001/iot/lamp001",
      "commands": [
        {"name": "on","type": "command"},
        {"name": "off","type": "command"}
       ],
       "attributes": [
        {"object_id": "s", "name": "state", "type":"Text"},
        {"object_id": "l", "name": "luminosity", "type":"Integer"},
        {"object_id": "lo", "name": "location", "type":"geo:point"}
       ],
       "static_attributes": [
         {"name":"refStore", "type": "Relationship","value": "urn:ngsi-ld:Store:001"}
      ]
    }
  ]
}
'
	 */
	private Device[] devices;
	
	public CreateDevice (Device[] devices) {
		this.devices = devices;
	}
	
	public static class Device {
		
		private String device_id;
		private String entity_name;
		private String entity_type;
		private String protocol;
		private String transport;
		private String endpoint;
		private Command[] commands;
		private Attribute[] attributes;
		private StaticAttribute[] static_attributes;
		
				
		public Device(String device_id, String entity_name, String entity_type, String protocol, String transport,
				String endpoint, Command[] commands, Attribute[] attributes, StaticAttribute[] static_attributes) {
			this.device_id = device_id;
			this.entity_name = entity_name;
			this.entity_type = entity_type;
			this.protocol = protocol;
			this.transport = transport;
			this.endpoint = endpoint;
			this.commands = commands;
			this.attributes = attributes;
			this.static_attributes = static_attributes;
		}
		
		
		
		public Device(String device_id, String entity_name, String entity_type, String protocol, String transport,
				String endpoint, Command[] commands, Attribute[] attributes) {
			this.device_id = device_id;
			this.entity_name = entity_name;
			this.entity_type = entity_type;
			this.protocol = protocol;
			this.transport = transport;
			this.endpoint = endpoint;
			this.commands = commands;
			this.attributes = attributes;
		}
		
		public Device(String device_id, String entity_name, String entity_type, Attribute[] attributes) {
			this.device_id = device_id;
			this.entity_name = entity_name;
			this.entity_type = entity_type;
			this.attributes = attributes;
		}
		
		public Device(String device_id, String entity_name, String entity_type, Attribute[] attributes, StaticAttribute[] static_attributes) {
			this.device_id = device_id;
			this.entity_name = entity_name;
			this.entity_type = entity_type;
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
		public String getProtocol() {
			return protocol;
		}
		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}
		public String getTransport() {
			return transport;
		}
		public void setTransport(String transport) {
			this.transport = transport;
		}
		public String getEndpoint() {
			return endpoint;
		}
		public void setEndpoint(String endpoint) {
			this.endpoint = endpoint;
		}
		public Command[] getCommand() {
			return commands;
		}
		public void setCommand(Command[] commands) {
			this.commands = commands;
		}
		public Attribute[] getAttribute() {
			return attributes;
		}
		public void setAttribute(Attribute[] attributes) {
			this.attributes = attributes;
		}
		public StaticAttribute[] getStatic_attribute() {
			return static_attributes;
		}
		public void setStatic_attribute(StaticAttribute[] static_attributes) {
			this.static_attributes = static_attributes;
		}
		
		
		
	}
	
	public static class Command {
		
		private String name;
		private String type;
		
		
		public Command(String name) {
			this.name = name;
			type = "command";
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

	public Device[] getDevice() {
		return devices;
	}

	public void setDevice(Device[] devices) {
		this.devices = devices;
	}
	
}
