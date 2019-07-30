package br.com.ufs.orionframework.entitylamp;


import br.com.ufs.orionframework.entity.Attrs;

/**
 * This class is used to help to represent a JSON entity (FIWARE format) as object java.
 * Here, to easy the developer's life. We've made a built-in Lamp entity, defined by: id, entitytype, name, location, radius.
 * On other hand, the developer can create your own entity java and use orion to send, update etc.
 *
 * @author Mariana Martins
 * @version %I%, %G%
 * @since 1.0
 * @see br.com.ufs.orionframework.entity.Entity
 * @see br.com.ufs.orionframework.orion.Orion
 */
public class Lamp {

    private String id;
    private String type;
    private Attrs name;
    private Attrs location;
    private Attrs radius;

    /**
     * Create a Square containing Lamps.
     *
     * @param  id  An given id for the entity.
     * @param type An given entitytype for the entity.
     * @param name An given name for the entity.
     * @param location An given localition for the entity.
     * @param radius An give radius for the entity, means the distance between center and other point.
     */
    public Lamp(String id, String type, Attrs name, Attrs location, Attrs radius) {
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

