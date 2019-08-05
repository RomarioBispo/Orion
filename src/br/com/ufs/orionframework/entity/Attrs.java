package br.com.ufs.orionframework.entity;


import br.com.ufs.orionframework.entitylamp.Lamp;

/**
 * This class is used to help to represent a entity attribute as java object representing the JSON on a NGSIv2 form.
 *
 * @author Mariana Martins, Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see Lamp
 */
public class Attrs {
    private String value;
    private String type;


    public Attrs(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }



}