package com.java.update;


public class UpdateLuminosity {
	
	/*String requestBody = "{\"actionType\":\"APPEND\",\"entities\":[]}";*/
	private String actionType;
	private EntitieUpdate[] entities;

	
	public UpdateLuminosity(EntitieUpdate[] entities) {
		actionType = "APPEND";
		this.entities = entities;
	}


	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public EntitieUpdate[] getEntities() {
		return entities;
	}

	public void setEntities(EntitieUpdate[] entities) {
		this.entities = entities;
	}
	
	

}
