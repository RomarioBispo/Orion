package br.com.ufs.orionframework.registrations;
/**
 * This class is used to help to represent a registration object as JSON attribute on registration class.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see Registrations
 */

public class Entities {
    private String id;
    private String type;

    /**
     * This constructor creates a entities object as a JSON represented on NGSIv2 form.
     *
     * @param id Id or pattern of the affected entities. Both cannot be used at the same time, but one of them must be present.
     * @param type Type or pattern of the affected entities.
     */
    public Entities(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType(){
        return this.type;
    }
    public String getId(){
        return this.id;
    }
}