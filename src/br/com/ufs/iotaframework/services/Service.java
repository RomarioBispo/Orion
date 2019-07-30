package br.com.ufs.iotaframework.services;


/**
 * This class help to represent a Service in JSON format as java Object.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 *
 */

public class Service {
    private String apikey;
    private String token;
    private String cbroker;
    private String entity_type;
    private String resource;

    /**
     * Create a Service object.
     *
     * @param apikey It is a key used for devices belonging to this service.
     * @param token If authentication/authorization system is configured, IoT Agent works as user when it publishes information.
     * That token allows that other components to verify the identity of IoT Agent. Depends on authentication and authorization system.
     * @param cbroker Context Broker endpoint assigned to this service, it must be a real uri.
     * @param entity_type Entity type used in entity publication (overload default).
     * @param resource Path in IoTAgent.
     */
    public Service(String apikey, String token, String cbroker, String entity_type, String resource) {
        this.apikey = apikey;
        this.token = token;
        this.cbroker = cbroker;
        this.entity_type = entity_type;
        this.resource = resource;
    }


    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCbroker() {
        return cbroker;
    }

    public void setCbroker(String cbroker) {
        this.cbroker = cbroker;
    }

    public String getEntity_type() {
        return entity_type;
    }

    public void setEntity_type(String entity_type) {
        this.entity_type = entity_type;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
