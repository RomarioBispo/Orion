package br.com.ufs.orionframework.entity;
/**
 * This abstract class is used to help to represent a object java as JSON on a NGSIv2 form.
 * Any entity defined by developer must extends this class.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */
public abstract class Entity{

    public String type;
    public String id;

    public abstract String getType();

    public abstract String getId();

    public abstract void setType(String type);

    public abstract void setId(String id);

}
