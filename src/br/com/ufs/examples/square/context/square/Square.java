package com.java.square;

import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.entity.Entity;

public class Square extends Entity {
	
	/*
	 * 
    {
  "id": "Square01",
  "type": "Square",
  "name": {
    "value": "Praça Oliveira Belo",
    "type": "Text"
  },
  "location": {
    "value": "-10.936273, -37.061228",
    "type": "geo:point"
  },
  "radius": {
    "value": 45,
    "type": "Float"
  }
}
	 */

	private Attrs name;
	private Attrs location;
	private Attrs radius;
	
	
	public Square(String id, String type, Attrs name, Attrs location, Attrs radius) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.location = location;
		this.radius = radius;
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


	public Attrs getName() {
		return name;
	}


	public void setName(Attrs name) {
		this.name = name;
	}


	public Attrs getLocation() {
		return location;
	}


	public void setLocation(Attrs location) {
		this.location = location;
	}


	public Attrs getRadius() {
		return radius;
	}


	public void setRadius(Attrs radius) {
		this.radius = radius;
	}
	

}
