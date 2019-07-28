package com.java.update;

public class EntitieUpdate {
//{\"id\":\"" + id + "\",\"" + attribute + "\":{}}
	private String id;
	private String type;
	private AttributeUpdate luminosity;
	private AttributeUpdate count;
	
	public EntitieUpdate(String id, AttributeUpdate luminosity) {
		this.id = id;
		type = "Lamp";
		this.luminosity = luminosity;
	}
	
	public EntitieUpdate(String id, AttributeUpdate luminosity, AttributeUpdate count) {
		this.id = id;
		type = "Lamp";
		this.count = count;
		this.luminosity = luminosity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AttributeUpdate getLuminosity() {
		return luminosity;
	}

	public void setLuminosity(AttributeUpdate lumonisity) {
		this.luminosity = lumonisity;
	}

	public AttributeUpdate getCount() {
		return count;
	}

	public void setCount(AttributeUpdate count) {
		this.count = count;
	}
	
	
}
