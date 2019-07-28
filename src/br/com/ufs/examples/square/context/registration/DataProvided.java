package com.java.registration;

public class DataProvided {
	private EntitieProvider[] entities;
	private String[] attrs;
	
	public DataProvided(EntitieProvider[] entities, String[] attrs) {
		super();
		this.entities = entities;
		this.attrs = attrs;
	}

	public EntitieProvider[] getEntitieProvider() {
		return entities;
	}

	public void setEntitieProviders(EntitieProvider[] entities) {
		this.entities = entities;
	}

	public String[] getAttrs() {
		return attrs;
	}

	public void setAttrs(String[] attrs) {
		this.attrs = attrs;
	}
	
}
