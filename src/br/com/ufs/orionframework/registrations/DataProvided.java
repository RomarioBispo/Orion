package br.com.ufs.orionframework.registrations;

import java.util.List;

/**
 * This class is used to help to represent a registration object as JSON attribute on registration class.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see Registrations
 */
public class DataProvided {
    private List<Entities> entities;
    private List<String> attrs;


    public DataProvided(List<Entities> entities, List<String> attrs) {
        this.entities = entities;
        this.attrs = attrs;
    }

    public List<Entities> getEntities() {
        return this.entities;
    }

    public void setEntities(List<Entities> entities) {
        this.entities = entities;
    }

    public List<String> getAttrs() {
        return this.attrs;
    }

    public void setAttrs(List<String> attrs) {
        this.attrs = attrs;
    }
    

}