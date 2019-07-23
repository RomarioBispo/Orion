package br.com.ufs.orionframework.orion;
import br.com.ufs.iotaframework.iota.IoTA;
import br.com.ufs.orionframework.genericnotification.GenericNotification;
import br.com.ufs.orionframework.server.TCPServer;
import br.com.ufs.orionframework.typeadapter.IntTypeAdapter;
import com.google.gson.*;
import br.com.ufs.orionframework.entity.Entity;
import br.com.ufs.orionframework.httprequests.HttpRequests;
import br.com.ufs.orionframework.registrations.DataProvided;
import br.com.ufs.orionframework.registrations.DataProvider;
import br.com.ufs.orionframework.registrations.Entities;
import br.com.ufs.orionframework.registrations.ForwardingInformation;
import br.com.ufs.orionframework.registrations.Http;
import br.com.ufs.orionframework.registrations.Provider;
import br.com.ufs.orionframework.registrations.Registrations;
import br.com.ufs.orionframework.subscription.*;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Orion make all NGSIv2 operations by using objects java.
 * Your objective is to let a little bit easier to developer do FIWARE applications using java.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */

public class Orion<T> {

    private String url;
    private String listener;
    private Boolean debugMode;

    private static final String ENTITIES_ENDPOINT = "/v2/entities/";
    private static final String ATTRS_ENDPOINT = "/attrs/";
    private static final String VALUE_ENDPOINT = "/value/";
    private static final String SUBSCRIPTIONS_ENDPOINT = "/v2/subscriptions/";
    private static final String TYPES_ENDPOINT = "/v2/types/";
    private static final String BATCH_ENDPOINT = "/v2/op/";
    private static final String REGISTRATIONS_ENDPOINT = "/v2/registrations/";
    private static final Logger LOGGER = Logger.getLogger(Orion.class.getName());


    /**
     * Instantiate a Orion object.
     * debugMode is an Boolean which tells to enable the debug mode, showing the JSON files sent or received. (Default is false. To enable, set your value)
     *
     * @param url An IP address + Port String from where the Orion is running.
     * @param listener An IP address + Port from where the server is listening the changes that was subscribed.
     *
     */
    public Orion (String url, String listener) {
        this.url = url;
        this.listener = listener;
        this.debugMode = false;
    }

    /**
     * Instantiate a Orion object running at localhost:1026 and listener running at 172.18.1.1:4041
     *
     */
    public Orion () {
        this.url = "http://localhost:1026";
        this.listener = "http://172.18.1.1:40041";
        this.debugMode = false;
    }

    public Boolean getDebugMode() {
        return debugMode;
    }

    public void setDebugMode(Boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * Create a entity on Orion (require a IP + port from Orion Context Broker)
     *
     * @param  obj  An given object representing the entity. The object follows
     * the JSON entity representation format (described in a “JSON Entity Representation” section) on NGSIv2 docs.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void createEntity(Object obj) {

        String json;

        Gson gson = new Gson();
        json = gson.toJson(obj);

        HttpRequests http = new HttpRequests();
        try {
            http.runPostRequest(this.url + ENTITIES_ENDPOINT, json);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and the entity was not created, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        shoWDebug(json);

    }

    /**
     * Retrieves a list of entities that match different criteria
     *
     * @param criteria can be defined by id, entitytype, pattern matching (either id or entitytype).
     * @param obj an object representing the entity.
     * @return  An Array with all objects (EntityId and entitytype) registered on orion.
     * the JSON entity representation format (described in a “JSON Entity Representation” section) on NGSIv2 docs.
     */
    public ArrayList<Object> listEntities(String criteria, Object obj){

        String json = "";

        Gson gson = new GsonBuilder().registerTypeAdapter(int.class, new IntTypeAdapter()).create();

        HttpRequests http = new HttpRequests();
        ArrayList<Object> objects = new ArrayList<>();
        try {
            if (criteria.isEmpty()){
                json  = http.runGetRequest(this.url + ENTITIES_ENDPOINT);
            }else {
                json = http.runGetRequest(this.url + ENTITIES_ENDPOINT + "?" + criteria);
            }
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and was possible retrieve the entities, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);

        }

        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(json);
        JsonArray jsonArray = element.getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++){
            json = gson.toJson(jsonArray.get(i));
            objects.add(gson.fromJson(json, (Type) obj.getClass()));
        }

        shoWDebug(json);

        return objects;
    }

    /**
     * Given an Id and object that represents the entity required, returns an object updated from Orion.
     *
     * @param  entityId  an identifier from entity
     * @param  obj the object that represents the entity
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @return a object updated from entity
     */
    public Object retrieveEntity(String entityId, Object obj){

        String json = "";

        HttpRequests http = new HttpRequests();
        try {
            json  = http.runGetRequest(this.url + ENTITIES_ENDPOINT + entityId);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and the entity was not retrieved, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        Gson gson = new Gson();

        shoWDebug(json);

       return (gson.fromJson(json, obj.getClass()));
    }


    /**
     * Delete the entity given id of the entity.
     *
     * @param  entityId  Id of the entity to be deleted.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void removeEntity(String entityId){

        HttpRequests http = new HttpRequests();
        try {
            http.runDeleteRequest(this.url + ENTITIES_ENDPOINT + entityId);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and the entity was not removed, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

    }


    /**
     * similar to retrieve the whole entity. this operation must return only one entity element.
     * This operation retrieve only attribute fields from entity, so, id and type not come from the request.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param  obj the object that represents the entity
     * @see #retrieveEntity(String, Object)
     * @return a object representing the entity
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public Object retrieveEntityAttributes(String entityId, Object obj){

        String json = "";

        HttpRequests http = new HttpRequests();
        try {
            json = http.runGetRequest(this.url + ENTITIES_ENDPOINT + entityId + ATTRS_ENDPOINT);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and the entity attributes was not retrieved, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        Gson gson = new Gson();

        shoWDebug(json);

        return (gson.fromJson(json, obj.getClass()));
    }


    /**
     * Replace attributes for the entity, given your id and object representing the new attributes.
     * Change id and entitytype for entity is not allowed.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param obj object representing the new attributes for the entity.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void replaceAllEntitiesAttributes(String entityId, Object obj){

        String json;

        Gson gson = new Gson();
        json = gson.toJson(obj);

        JsonObject o = new JsonParser().parse(json).getAsJsonObject();
        o.remove("id");
        o.remove("type");

        json = o.toString();

        HttpRequests http = new HttpRequests();

        try {
            http.runPutRequest(this.url + ENTITIES_ENDPOINT + entityId + "/attrs", json);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and the entity attributes was not replaced, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        shoWDebug(json);

    }


    /**
     * the entity attributes are updated (if they previously exist) or appended
     * (if they don’t previously exist) with the ones in the payload.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param obj object representing the attributes to append or update
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void updateOrAppendEntityAttributes(String entityId, Object obj){

        String json;

        Gson gson = new Gson();
        json = gson.toJson(obj);

        JsonObject o = new JsonParser().parse(json).getAsJsonObject();
        o.remove("id");
        o.remove("type");

        json = o.toString();

        HttpRequests http = new HttpRequests();
        try {
            http.runPostRequest(this.url + ENTITIES_ENDPOINT + entityId + "/attrs", json);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and the entity attributes was not updated or appended, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        shoWDebug(json);

    }



    /**
     * The entity attributes are updated with the ones in the object.
     * The attributes must already exists before send this update.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param obj an object representing the attributes to update.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void updateExistingEntityAttributes(String entityId, Object obj){

        String json;

        Gson gson = new Gson();
        json = gson.toJson(obj);

        JsonObject o = new JsonParser().parse(json).getAsJsonObject();
        o.remove("id");
        o.remove("type");

        json = o.toString();

        HttpRequests http = new HttpRequests();
        try {
            http.runPostRequest(this.url + ENTITIES_ENDPOINT + entityId + ATTRS_ENDPOINT, json);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and was not possible update existing attributes for the entity, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        shoWDebug(json);
    }


    /**
     * Return the object with the attribute data of the attribute.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param attrName Name of the attribute to be retrieved.
     * @param obj object that represents the attribute
     * @return a object with the attribute data of the attribute.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public Object getAttributeData(String entityId, String attrName, Object obj){

        String json = "";

        HttpRequests http = new HttpRequests();
        try {
            json = http.runGetRequest(this.url + ENTITIES_ENDPOINT + entityId + ATTRS_ENDPOINT + attrName);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and the attribute data was not retrieved, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        Gson gson = new Gson();

        shoWDebug(json);

        return(gson.fromJson(json, obj.getClass()));

    }


    /**
     * Replace previous attribute data by the one in the object.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param attrName Name of the attribute to be retrieved.
     * @param obj object that represents the attribute to update.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void updateAttributeData(String entityId, String attrName, Object obj){

        String json;

        Gson gson = new Gson();
        json = gson.toJson(obj);

        HttpRequests http = new HttpRequests();
        try {
            http.runPutRequest(this.url + ENTITIES_ENDPOINT + entityId + ATTRS_ENDPOINT + attrName, json);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and the attribute data was not updated, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        shoWDebug(json);

    }

    /**
     * Removes an entity attribute.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param attrName Name of the attribute to be retrieved.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void removeSingleAttribute(String entityId, String attrName){

        HttpRequests http = new HttpRequests();
        try {
            http.runDeleteRequest(this.url + ENTITIES_ENDPOINT + entityId + ATTRS_ENDPOINT + attrName);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and the attribute was not removed, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

    }


    /**
     * Returns the value property with the value of the attribute.
     *
     * @param  entityId  Id of the entity to be retrieved.
     * @param attrName Name of the attribute to be retrieved.
     * @param obj object that represents the attribute to update.
     * @return value of the given attribute.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public Object getAttributeValue(String entityId, String attrName, Object obj){

        String json = "";

        HttpRequests http = new HttpRequests();
        try {
            json = http.runGetRequest(this.url + ENTITIES_ENDPOINT + entityId + ATTRS_ENDPOINT + attrName + VALUE_ENDPOINT);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and the attribute value was not retrieved, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        Gson gson = new Gson();

        shoWDebug(json);

        return (gson.fromJson(json, obj.getClass()));

    }


    /**
     * given a object representing a new attribute, this operation turn it a new attribute value.
     *
     * @param  entityId  Id of the entity to be retrieved
     * @param attrName Name of the attribute to be retrieved.
     * @param obj object that represents the attribute to update.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void updateAttributeValue(String entityId, String attrName, Object obj) {

        String json;

        Gson gson = new Gson();
        json = gson.toJson(obj);

        HttpRequests http = new HttpRequests();
        try {
            http.runPutRequest(this.url + ENTITIES_ENDPOINT + entityId + ATTRS_ENDPOINT + attrName + VALUE_ENDPOINT, json);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and was not possible update the attribute value please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        shoWDebug(json);

    }

    /**
     * this operation return the entity types from Orion.
     *
     * @param obj object that represents the attribute to update.
     * @return a list of entity types object.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public List<Object> listEntityTypes(Object obj) {

        String json = "";
        Gson gson = new Gson();

        HttpRequests http = new HttpRequests();
        try {
            json = http.runGetRequest(this.url + TYPES_ENDPOINT);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and was not possible list the entity types, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        shoWDebug(json);
        return (gson.fromJson(json, (Type) obj.getClass()));

    }

    /**
     * this operation return information about entity types from Orion.
     *
     * @param entitytype a type for the entity.
     * @param obj object that represents the attribute to update.
     * @return information about the given entitytype.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public Object retrieveEntityType(String entitytype, Object obj){

        String json = "";
        Gson gson = new Gson();
        HttpRequests http = new HttpRequests();

        try {
            json = http.runGetRequest(this.url + TYPES_ENDPOINT + entitytype);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and was not possible retrieve the entity type, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        shoWDebug(json);
        return (gson.fromJson(json, (Type) obj.getClass()));
    }

    /**
     * this operation returns a list of all the subscriptions present in the system.
     * @throws  Exception for http requests.
     * @return a list of all the subscriptions present in the system.
     */
    public Object listSubscriptions(){

        String json = "";
        Gson gson = new Gson();
        HttpRequests http = new HttpRequests();

        try {
            json  = http.runGetRequest(this.url + SUBSCRIPTIONS_ENDPOINT);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and was not possible to list subscriptions, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        shoWDebug(json);
        Subscription [] subs = gson.fromJson(json, Subscription[].class);

        return subs;

    }

    /**
     * this operation creates a new subscription on Orion.
     *
     * @param subscription an given subscription object which represents a subscription on JSON format from Orion.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void createSubscriptions(Subscription subscription){

        String json;
        Gson gson = new Gson();
        json = gson.toJson(subscription);
        HttpRequests http = new HttpRequests();

        try {
            http.runPostRequest(this.url + SUBSCRIPTIONS_ENDPOINT, json);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and was not possible create the subscription, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        shoWDebug(json);

    }

    /**
     * this operation returns a subscription given the subscriptionId
     *
     * @param subscriptionId an given id from subscription.
     * @return a subscription that has the subscriptionId given.
     */
    public Object retrieveSubscription(String subscriptionId) {
        return null;
    }



    /**
     * this operation cancels subscription given a subscriptionId.
     *
     * @param subscriptionId When a subscription is created, a message is received on url defined on subscription
     * This url contains a Location header which holds the subscription ID: a 24 digit hexadecimal number.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void deleteSubscription(String subscriptionId){

        HttpRequests http = new HttpRequests();
        try {
            http.runDeleteRequest(this.url + SUBSCRIPTIONS_ENDPOINT + subscriptionId);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and was not possible remove the subscription, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

    }

    /**
     * this operation update the subscription (Only the fields included in the request are updated in the subscription)
     *
     * @param subscriptionId
     * @param subscription
     */
    public void updateSubscription(String subscriptionId, Object subscription) {
        /**
         * This method is not implemented yet.
         * This feature will be implemented in the future.
         */
    }

    /**
     * this operation create a simple subscription on orion.
     * this subscription reacts (trigger notifications) to any changes on attributes.
     * this subscription expires at 2040-04-05T14:00:00Z.
     *
     * @param id an given id from entity. (can be a regex)
     * @param type an given type from entity.
     * @param port an given port from where the server is listening the notifications.
     * @param ip an given ip from where the server is running.
     * @param expires an given boolean, if false, the subscription has not expires time, if true, has expire time
     * defined at 2040-04-05T14:00:00Z.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void createSimpleSubscription(String id, String type, int port, String ip, Boolean expires){

        String json;

        Entities entities = new Entities(id,type);

        List<Entities> entitiesList = new ArrayList<>();
        entitiesList.add(entities);

        Subject subject = new Subject(entitiesList);

        List<String> conditions = new ArrayList<>();
        Condition condition = new Condition(conditions);

        List<String> attrs = new ArrayList<>();

        Http http = new Http("http://" + ip + ":" + port);
        Notification notification = new Notification(http, attrs);

        Subscription sub = new Subscription("A generic subscription",subject, condition,
                notification, "2040-04-05T14:00:00Z", 1);

        Gson gson = new Gson();
        json = gson.toJson(sub);

        if (!expires) {
            JsonObject o = new JsonParser().parse(json).getAsJsonObject();
            o.remove("expires");
            json = o.toString();
        }

        HttpRequests httpRequest = new HttpRequests();
        try {
            httpRequest.runPostRequest(this.url + SUBSCRIPTIONS_ENDPOINT, json);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and was not possible create the subscription, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        shoWDebug(json);
    }

    /**
     * this operation subscribe yourself on Orion to receive notifications about changed values
     *
     * @param port an given port from where the server is listening.
     * @param ip an given IP address from where the server is running.
     * @return a Future<String> value, to retrieve the entity value, you must use this method and call getSubscriptionUpdate method after.
     * @see #getSubscriptionUpdate
     */
    public Future<String> subscribeAndListen(String id, String type, int port, String ip){

        Orion orion = new Orion();
        orion.createSimpleSubscription(id, type, port, ip, false);

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<String> future = null;
        try {
            future = executor.submit( new TCPServer(port, ip));
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and was not possible make a subscribe and listen, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        executor.shutdown();

        return future;
    }

    /**
     * this operation receive the Future<String> which came from subscribeAndListen and turn on in a entity Object.
     *
     * @param f a future object holding the notification payload which came from subscribeAndListen.
     * @param obj a entity object.
     * @return a entity Object filled with informations from notification.
     * @see #subscribeAndListen
     */
    public Object getSubscriptionUpdate(Future<String> f, Object obj) throws ExecutionException, InterruptedException {

        String json;

        Gson gson = new Gson();
        GenericNotification notification = gson.fromJson(f.get(), GenericNotification.class);

        json = gson.toJson(notification.getData().get(0));

        shoWDebug(json);

        return gson.fromJson(json, obj.getClass());

    }


    /**
     * this operation lists all the context provider registrations present in the system.
     * 
     * @return a List of registrations objects
     */
    public void listRegistrations() {
                
        String json;
        Gson gson = new gson();

        HttpRequests httpRequests = new HttpRequests();
        
        json = httpRequests.runGetRequest(this.url + REGISTRATIONS_ENDPOINT);
        
        return gson.fromJson(json, Registrations[].class);
    }



    /**
     *  this operation creates a new context provider registration.
     *  Typically used for binding context sources as providers of certain data.
     *
     * @param entity a entity which you want to make a registration
     * @param attrs a attributes list 
     * @param a url from the provider
     * @return a registrationId to use on update, delete and retrieve operations. (Note: the subscriptionId is generated by entity id concatenated with _Registration)
     * Example: entityId = "test", so registrationId = "teste_Registration".
     */
    public String createRegistration(Entity entity, List<String> attrs, String providerUrl) {
        
        String registrationId = entity.getId() + "_Registration";
        String description = "A generic " + entity.getType() + " Context Source";

        List<Entities> entityList = new ArrayList<>();
        Entities entities = new Entities(entity.getId(), entity.getType());
        entityList.add(entities);
        DataProvided dataProvided = new DataProvided(entityList, attrs);
        
        Http http = new Http(providerUrl);
        Provider provider = new Provider(http, "all");

        String expires = "2017-10-31T12:00:00";
        String status = "active";

        // Registrations registrations = new Registrations(registrationId, description, dataProvided, provider, expires, status, new ForwardingInformation());
        Registrations registrations = new Registrations(registrationId, description, dataProvided, provider);

        Gson gson = new Gson();
        String json = gson.toJson(registrations);

        HttpRequests httpRequests = new HttpRequests();
        httpRequests.runPostRequest(this.url + REGISTRATIONS_ENDPOINT, json);
        
        return registrations.getId();
    }

    /**
     * this operation return the Registration given your registrationId
     *
     * @param registrationId
     * @return a registration object
     */
    public Registrations retrieveRegistration(String registrationId) {
        
        String json;
        Gson gson = new gson();

        HttpRequests httpRequests = new HttpRequests();
        
        json = httpRequests.runGetRequest(this.url + REGISTRATIONS_ENDPOINT + registrationId);
        
        return gson.fromJson(json, Registrations.class);
    }


    /**
     * this operation delete the Registration given your registrationId
     *
     * @param registrationId
     */
    public void deleteRegistration(String registrationId) {
        HttpRequests httpRequests = new HttpRequests();
        try {
            httpRequests.runDeleteRequest(this.url + REGISTRATIONS_ENDPOINT + registrationId);
        } catch (Exception e ) {
            showStackTrace(e);
        }

    }

    /**
     * this operation update the Registration given your registrationId
     *
     * @param registrationId an given registrationId 
     * @param registrationUpdate a entire Registration object which fields to updated.
     */
    public void updateRegistration(String registrationId, Registrations registrationUpdate) {
       
        String json;
        Gson gson = new Gson();

        json = gson.toJson(registrationUpdate);
        
        HttpRequests httpRequests = new HttpRequests();
        httpRequests.runPatchRequest(this.url + REGISTRATIONS_ENDPOINT + registrationId, json);
    }

    /**
     * This operation allows to create, update and/or delete several entities in a single batch operation.
     * Given the entities object that represents the entities
     *
     * @throws Exception to http requests.
     * @param batchEntities a batch object containing all entities to be updated on orion.
     */
    public void batchUpdate(Object batchEntities){

        String json;

        Gson gson = new Gson();
        json = gson.toJson(batchEntities);

        HttpRequests http = new HttpRequests();
        try {
            http.runPostRequest(this.url + BATCH_ENDPOINT + "update",json);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred and was not possible update the entities, please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }
        shoWDebug(json);
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

    /**
     * This operation show on console a JSON file to debug purposes.
     * To use this feature, you only need to set the debugMode attribute to true on orion.
     * @param json a JSON to be showed on screen, to debug purposes.
     */
    public void shoWDebug(String json) {
        if(this.debugMode)
            LOGGER.info(json);
    }

    /**
     * This operation show on console the stack trace.
     * To use this feature, you only need to set the debugMode attribute to true on orion.
     * @param e a exception to show your stack trace on console.
     *
     */
    public void showStackTrace (Exception e){
        if(this.debugMode){
            LOGGER.log(Level.INFO, e.getMessage(), e);
        }

    }

}


