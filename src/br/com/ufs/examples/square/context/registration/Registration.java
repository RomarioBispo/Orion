package com.java.registration;

public class Registration {

/*
 * curl -iX POST \
  'http://localhost:1026/v2/registrations' \
  -H 'Content-Type: application/json' \
  -H 'fiware-service: openiot' \
  -H 'fiware-servicepath: /' \
  -d '{
  "description": "Lamp Commands",
  "dataProvided": {
    "entities": [
      {
        "id": "urn:ngsi-ld:Lamp:001","type": "Lamp"
      }
    ],
    "attrs": [ "on", "off" ]
  },
  "provider": {
    "http": {"url": "http://orion:1026/v1"},
    "legacyForwarding": true
  }
}'
 */
	private String description;
	private DataProvided dataProvided;
	private Provider provider;
	
	public Registration(String description, DataProvided dataProvided, Provider provider) {
		this.description = description;
		this.dataProvided = dataProvided;
		this.provider = provider;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DataProvided getDataProvided() {
		return dataProvided;
	}

	public void setDataProvided(DataProvided dataProvided) {
		this.dataProvided = dataProvided;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	
	
}
