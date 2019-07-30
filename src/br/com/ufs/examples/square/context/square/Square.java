package br.com.ufs.examples.square.context.square;

import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.entity.Entity;


/**
 * This class is used to concept proof for the Orion Framework.
 * To this only purpose, the project developed in Mariana Martins undergraduate thesis was used.
 * Your project was modified to use the framework.
 *
 * @author Romario Bispo, Mariana Martins.
 * @version %I%, %G%
 * @since 1.0
 * @see br.com.ufs.examples.square.context.socket.SquareExample
 * */


public class Square extends Entity {

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
