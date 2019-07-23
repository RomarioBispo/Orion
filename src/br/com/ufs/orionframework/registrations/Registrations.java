package br.com.ufs.orionframework.registrations;

/**
 * This class is used to represent a registration object as JSON attribute on registration class.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */
public class Registrations {
    private String id;
    private String description;
    private DataProvided dataProvided;
    private Provider provider;
    private String expires;
    private String status;
    private ForwardingInformation forwardingInformation;


    public Registrations(String id, String description, DataProvided dataProvided, Provider provider, String expires, String status, ForwardingInformation forwardingInformation) {
        this.id = id;
        this.description = description;
        this.dataProvided = dataProvided;
        this.provider = provider;
        this.expires = expires;
        this.status = status;
        this.forwardingInformation = forwardingInformation;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataProvided getDataProvided() {
        return this.dataProvided;
    }

    public void setDataProvided(DataProvided dataProvided) {
        this.dataProvided = dataProvided;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getExpires() {
        return this.expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ForwardingInformation getForwardingInformation() {
        return this.forwardingInformation;
    }

    public void setForwardingInformation(ForwardingInformation forwardingInformation) {
        this.forwardingInformation = forwardingInformation;
    }
    

}