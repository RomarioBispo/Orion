package orion;
import com.google.gson.Gson;
import entity.Entity;
import entityLamp.Lamp;
import httpRequests.HttpRequests;
import subscription.Entities;
import subscription.Subscription;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Orion implements all NGSIv2 operations by using objects java.
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */

public class Orion<T> {

    private String url;
    private String listener;

    /**
     * Instantiate a Orion object.
     *
     * @param url An IP address + Port String from where the Orion is running.
     * @param listener An IP address + Port from where the server is listening the changes that was subscribed.
     */
    public Orion (String url, String listener) {
        this.url = url;
        this.listener = listener;
    }

    /**
     * Instantiate a Orion object running at localhost:1026 and listener running at http://localhost:4041
     *
     */
    public Orion () {
        this.url = "http://localhost:1026";
        this.listener = "http://172.18.1.1:40041";
    }

    /**
     * Create a entity on Orion (require a IP + port from Orion Context Broker)
     *
     * @param  obj  An given object representing the entity. The object follows
     * the JSON entity representation format (described in a “JSON Entity Representation” section) on NGSIv2 docs.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void createEntity(Object obj) throws Exception {

        String endpoint = "/v2/entities";
        String json = "";

        Gson gson = new Gson();
        json = gson.toJson(obj);

        HttpRequests http = new HttpRequests();
        http.runPostRequest(this.url + endpoint, json);

    }

    /**
     * Retrieves a list of entities that match different criteria
     *
     * @param criteria can be defined by id, type, pattern matching (either id or type).
     * @return  An Array with all objects (EntityId and type) registered on orion.
     * the JSON entity representation format (described in a “JSON Entity Representation” section) on NGSIv2 docs.
     */
    public Entity[] listEntities(String criteria) throws Exception {

        String json = "";
        String endpoint = "/v2/entities/";

        HttpRequests http = new HttpRequests();
        json  = http.runGetRequest(this.url+endpoint);

        Gson gson = new Gson();

        Entity [] entities = gson.fromJson(json, Entity[].class);

        return entities;

    }


    /**
     * Given an Id and object that represents the entity required, returns an object updated from Orion.
     *
     * @param  entityId  an identifier from entity
     * @param  obj the object that represents the entity
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @return      a object updated from entity
     */
    public Object retrieveEntity(String entityId, Object obj) throws Exception {

        String json = "";
        String endpoint = "/v2/entities/"+entityId;

        HttpRequests http = new HttpRequests();
        json  = http.runGetRequest(this.url + endpoint);

        Gson gson = new Gson();

       return (gson.fromJson(json, obj.getClass()));
    }


    /**
     * Delete the entity given id of the entity.
     *
     * @param  entityId  Id of the entity to be deleted.
     */
    public void removeEntity(String entityId) {

    }


    /**
     * similar to retrieve the whole entity. this operation must return only one entity element.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @see #retrieveEntity(String, Object)
     * @return a object representing the entity
     */
    public Object retrieveEntityAttributes(String entityId, Object obj) {
        return obj;
    }


    /**
     * Replace attributes for the entity, given your id and object representing the new attributes.
     * Change id and type for entity is not allowed.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param obj object representing the new attributes for the entity.
     */
    public void replaceAllEntitiesAttributes(String entityId, Object obj) {

    }


    /**
     * the entity attributes are updated (if they previously exist) or appended
     * (if they don’t previously exist) with the ones in the payload.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param obj object representing the attributes to append or update
     */
    public void updateOrAppendEntityAttributes(String entityId, Object obj) {

    }



    /**
     * The entity attributes are updated with the ones in the object.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param obj an object representing the attributes to update.
     */
    public void updateExistingEntityAttributes(String entityId, Object obj) {

    }


    /**
     * Return the object with the attribute data of the attribute.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param attrName Name of the attribute to be retrieved.
     * @param obj object that represents the attribute
     * @return a object with the attribute data of the attribute.
     */
    public Object getAttributeData(String entityId, String attrName, Object obj) {
        return obj;
    }


    /**
     * Replace previous attribute data by the one in the object.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param attrName Name of the attribute to be retrieved.
     * @param obj object that represents the attribute to update.
     */
    public void updateAttributeData(String entityId, String attrName, Object obj) {

    }

    /**
     * Removes an entity attribute.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param attrName Name of the attribute to be retrieved.
     */
    public void removeSingleAttribute(String entityId, String attrName) {

    }


    /**
     * Returns the value property with the value of the attribute.
     *
     * @param  entityId  Id of the entity to be retrieved.
     * @param attrName Name of the attribute to be retrieved.
     * @return value of the given attribute.
     */
    public Object getAttributeValue(String entityId, String attrName) {
        return null;
    }


    /**
     * given a object representing a new attribute, this operation turn it a new attribute value.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param attrName Name of the attribute to be retrieved.
     */
    public void updateAttributeValue(String entityId, String attrName, Object obj) {

    }

    /**
     * this operation return the entity types from Orion.
     *
     * @return a list of entity types.
     */
    public Object listEntityTypes() {
        return null;
    }

    /**
     * this operation return information about entity types from Orion.
     *
     * @param entitytype
     * @return information about the given type.
     */
    public String retrieveEntityType(String entitytype) {
        return "";
    }

    /**
     * this operation returns a list of all the subscriptions present in the system.
     * @throws  Exception for http requests.
     * @return a list of all the subscriptions present in the system.
     */
    public Object listSubscriptions() throws Exception {
        String json = "";
        String endpoint = "/v2/subscriptions";

        HttpRequests http = new HttpRequests();
        json  = http.runGetRequest(this.url + endpoint);

        Gson gson = new Gson();

        Subscription [] subs = gson.fromJson(json, Subscription[].class);

        return subs;

    }

    /**
     * this operation creates a new subscription on Orion.
     *
     * @param subscription
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void createSubscriptions(Subscription subscription) throws Exception {

        String json = "";
        String endpoint = "/v2/subscriptions";

        Gson gson = new Gson();
        json = gson.toJson(subscription);

        HttpRequests http = new HttpRequests();
        http.runPostRequest(this.url + endpoint, json);

    }

    /**
     * this operation returns a subscription given the subscriptionId
     *
     * @param subscriptionId
     * @return a subscription that has the subscriptionId given.
     */
    public Object retrieveSubscription(String subscriptionId) {
        return null;
    }



    /**
     * this operation cancels subscription given a subscriptionId.
     *
     * @param subscriptionId
     */
    public void deleteSubscription(String subscriptionId) {

    }

    /**
     * this operation update the subscription (Only the fields included in the request are updated in the subscription)
     *
     * @param subscriptionId
     * @param subscription
     */
    public void updateSubscription(String subscriptionId, Object subscription) {

    }


    /**
     * this operation lists all the context provider registrations present in the system.
     *
     */
    public void listRegistrations() {

    }



    /**
     *  this operation creates a new context provider registration.
     *  Typically used for binding context sources as providers of certain data.
     *
     * @param registration
     */
    public void createRegistration(Object registration) {

    }

    /**
     * this operation return the Registration given your registrationId
     *
     * @param registrationId
     * @return a registration object
     */
    public Object retrieveRegistration(String registrationId) {
        return null;
    }


    /**
     * this operation delete the Registration given your registrationId
     *
     * @param registrationId
     */
    public void deleteRegistration(String registrationId) {

    }

    /**
     * this operation delete the Registration given your registrationId
     *
     * @param registrationId
     * @param registration
     */
    public void updateRegistration(String registrationId, Object registration) {

    }

    /**
     * This operation allows to create, update and/or delete several entities in a single batch operation.
     * Given the entities object that represents the entities
     *
     * @param entities
     */
    public void batchUpdate(Object entities) {


    }

    /**
     * The response is an Array containing one object per matching entity, or an empty array [] if
     * no entities are found.
     *
     * @param entities
     */
    public Object batchQuery(Object entities) {
        return null;
    }


    /**
     * This operation is intended to consume a notification payload so that all the entity data included by such notification
     * is persisted, overwriting if necessary.
     *
     * @param entities
     */
    public Object batchNotify(Object entities) {
        return null;
    }

}


