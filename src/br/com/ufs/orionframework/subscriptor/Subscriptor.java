package br.com.ufs.orionframework.subscriptor;

import br.com.ufs.iotaframework.devices.Device;
import br.com.ufs.orionframework.entity.Entity;
import com.google.gson.Gson;
import br.com.ufs.orionframework.genericnotification.GenericNotification;
import br.com.ufs.orionframework.orion.Orion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Subscriptor make a subscription on Orion and listen notifications.
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */
public class Subscriptor implements Runnable {
    private Map<String, List<Lambda>> subscriptions = new HashMap<>();
    private int port;
    private String ip;
    private Entity en;
    private List<Entity> entityList;
    private List<Device> deviceList;
    private String entityId;
    private String type;
    private Device device;
    private ServerSocket server;
    private Boolean debugMode;
    private Entity model;

    private static final Logger LOGGER = Logger.getLogger(Subscriptor.class.getName());

    public List<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public Entity getModel() {
        return model;
    }

    public void setModel(Entity model) {
        this.model = model;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getEn() {
        return en;
    }

    public void setEn(Entity en) {
        this.en = en;
    }

    public Map<String, List<Lambda>> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Map<String, List<Lambda>> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    public Boolean getDebugMode() {
        return debugMode;
    }

    public void setDebugMode(Boolean debugMode) {
        this.debugMode = debugMode;
    }



    public Subscriptor(int port, String ip, String entityId, String type, ServerSocket server, Entity en) {
        this.port = port;
        this.ip = ip;
        this.entityId = entityId;
        this.type = type;
        this.server = server;
        this.en = en;
        this.debugMode = false;
        new Thread(this).start();

    }

    public Subscriptor(int port, String ip, String entityId, String type, ServerSocket server, List<Device> deviceList) {
        this.port = port;
        this.ip = ip;
        this.entityId = entityId;
        this.type = type;
        this.server = server;
        this.deviceList = deviceList;
        this.debugMode = false;
        new Thread(this).start();

    }

    public Subscriptor(int port, String ip, String entityId, String type, ServerSocket server) {
        this.port = port;
        this.ip = ip;
        this.entityId = entityId;
        this.type = type;
        this.server = server;
        this.deviceList = deviceList;
        this.debugMode = false;
        new Thread(this).start();

    }

    public Subscriptor(int port, String ip, ServerSocket server) {
        this.port = port;
        this.ip = ip;
        this.server = server;
        this.debugMode = false;
        new Thread(this).start();

    }


    /**
     * Create a subscription on Orion.
     * This operation put the Id, Function on a Hashmap.
     * If the id already is in the hashMap, the function is added to list.
     *
     * @param updateFunction a function which does a update when came a notification.
     * @param en a entity object representing the entity
     */
    public void subscribe(Lambda updateFunction, Entity en) {

        Orion orion = new Orion();
        List<Lambda> updateFunctionList = new ArrayList<>();

       if (!this.subscriptions.containsKey(en.getId())) {
            orion.createSimpleSubscription(".*", en.getType(), this.port, this.ip, false);
            updateFunctionList.add(updateFunction);
            subscriptions.put(en.getId(), updateFunctionList);
        }
        else {
            updateFunctionList = subscriptions.get(en.getId());
            updateFunctionList.add(updateFunction);
            subscriptions.put(en.getId(), updateFunctionList);
        }
    }

    /**
     * Create a subscription on Orion.
     * This operation put the Id, Function on a Hashmap.
     * If the id already is in the hashMap, the function is added to list.
     *
     * @param updateFunction a function which does a update when came a notification.
     * @param device a entity object representing the entity
     */
    public void subscribe(Lambda updateFunction, Device device) {

        Orion orion = new Orion();
        List<Lambda> updateFunctionList = new ArrayList<>();

        if (!this.subscriptions.containsKey(device.getEntity_name())) {
            orion.createSimpleSubscription(".*", device.getEntity_type(), this.port, this.ip, false);
            updateFunctionList.add(updateFunction);
            subscriptions.put(device.getEntity_name(), updateFunctionList);
        }
        else {
            updateFunctionList = subscriptions.get(device.getEntity_name());
            updateFunctionList.add(updateFunction);
            subscriptions.put(device.getEntity_name(), updateFunctionList);
        }
    }

    /**
     * Create a subscription on Orion.
     * This operation put the Id, Function on a Hashmap.
     * If the id already is in the hashMap, the function is added to list.
     * Note: this method overload subscribe, receiving instead of one single entity to a list of entities
     *
     * @param updateFunction a function which does a update when came a notification.
     * @param deviceList a entity list of object representing the lists
     * @see #subscribe(Lambda, Entity)
     */
    public void subscribe(Lambda updateFunction, List<Device> deviceList) {
        Orion orion = new Orion();

        for(Device entity: deviceList) {
            List<Lambda> updateFunctionList = new ArrayList<>();
            if (!this.subscriptions.containsKey(entity.getEntity_name())) {
                orion.createSimpleSubscription(".*", entity.getEntity_type(), this.port, this.ip, false);
                updateFunctionList.add(updateFunction);
                subscriptions.put(entity.getEntity_name(), updateFunctionList);
            }
            else {
                updateFunctionList = subscriptions.get(entity.getEntity_name());
                updateFunctionList.add(updateFunction);
                subscriptions.put(entity.getEntity_name(), updateFunctionList);
            }
        }
    }

    /**
     * Create a subscription on Orion and listen to notifications.
     * When a notification come, a update function associated with entity is applied to entity values.
     * Notice that method is unblocking using threads.
     *
     * @param updateFunction a function which does a update when came a notification.
     * @param model a object representing the Entity.
     */
    public void subscribeAndListen(Lambda updateFunction,Entity model) {

        subscribe(updateFunction, this.en);
        this.model = model;
    }

    /**
     * Create a subscription on Orion and listen to notifications.
     * When a notification come, a update function associated with entity is applied to entity values.
     * Notice that method is unblocking using threads.
     *
     * @param updateFunction a function which does a update when came a notification.
     * @param model a object representing the Entity.
     * @param device a device object which represents the entity.
     */
    public void subscribeAndListen(Lambda updateFunction,Entity model, Device device) {

        subscribe(updateFunction, device);
        this.model = model;
    }

    /**
     * Create a subscription on Orion and listen to notifications.
     * When a notification come, a update function associated with entity is applied to entity values.
     * Notice that method is unblocking using threads.
     *
     * @param updateFunction a function which does a update when came a notification.
     * @param model a object representing the Entity.
     */
    public void subscribeAndListen(Lambda updateFunction,Entity model, List<Device> deviceList) {
        subscribe(updateFunction, deviceList);
        this.model = model;

    }

    /**
     * Create a subscription on Orion and listen to notifications.
     * When a notification come, a update function associated with entity is applied to entity values.
     * Notice that method is blocking call, so the subscriber will be waiting until came a notification.
     *
     * @param updateFunction a function which does a update when came a notification.
     * @param model a object representing the Entity which want to retrieve.
     * @return a list of updated entities.
     */
    public <T> List<T> subscribeAndListenBlocking(Lambda updateFunction, Entity model){

        subscribe(updateFunction, this.en);
        this.model = model;
        String json = "";
        String data = null;
        String lastLine = null;
        Gson gson = new Gson();
        GenericNotification notification;

        Socket client = null;
        try {
            client = this.server.accept();
        } catch (IOException e) {
            showStackTrace(e);
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            showStackTrace(e);
        }

        while (true) {
            try {
                if (!((data = in.readLine()) != null)) break;
            } catch (IOException e) {
                showStackTrace(e);
            }
            lastLine = data;
        }

        shoWDebug(lastLine);

        notification = gson.fromJson(lastLine, GenericNotification.class);

        List<T> entityListUpdated = new ArrayList<>();

        for (Object entities: notification.getData()) {

            json = gson.toJson(entities);

            this.model = gson.fromJson(json, (Type) this.model.getClass());

            entityListUpdated.add((T) this.model);

            List<Lambda> updateFunctionList = subscriptions.get(this.en.getId());
            for (Lambda fList: updateFunctionList)
                this.model = fList.lambdaUpdate(this.model);
        }

        return entityListUpdated;
    }

    @Override
    public void run() {
        while(true) {

        String json = "";
        String data = null;
        String lastLine = null;
        Gson gson = new Gson();
        GenericNotification notification;

        Socket client = null;
        try {
            client = this.server.accept();
        } catch (IOException e) {
            showStackTrace(e);
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            showStackTrace(e);
        }

        while (true) {
            try {
                if (!((data = in.readLine()) != null)) break;
            } catch (IOException e) {
                showStackTrace(e);
            }
            lastLine = data;
        }

        shoWDebug(lastLine);
        notification = gson.fromJson(lastLine, GenericNotification.class);

        for (Object entities: notification.getData()) {

            json = gson.toJson(entities);

            this.model = gson.fromJson(json, (Type) this.model.getClass());

            List<Lambda> updateFunctionList = subscriptions.get(this.model.getId());
            for (Lambda fList: updateFunctionList)
                this.model = fList.lambdaUpdate(this.model);
        }
        }
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

    /**
     * This functional interface represents a lambda expression.
     * If you want to subscribe and listen, its possible send a function which receive a entity object and returns a entity object
     * to perform a update.
     *
     */
    @FunctionalInterface
    public interface Lambda{
        Entity lambdaUpdate(Entity entity);
    }

}
