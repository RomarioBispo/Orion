package com.java.registration;

public class EntitieProvider {
	private String id;
	private String type;
	
	
	public EntitieProvider(String id, String type) {
		this.id = id;
		this.type = type;
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
	
	
}
