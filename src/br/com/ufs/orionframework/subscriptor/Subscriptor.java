package br.com.ufs.orionframework.subscriptor;

import br.com.ufs.orionframework.entity.Entity;
import com.google.gson.Gson;
import br.com.ufs.orionframework.genericnotification.GenericNotification;
import br.com.ufs.orionframework.orion.Orion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private String entityId;
    private String type;
    private ServerSocket server;
    private Boolean debugMode;
    private static final Logger LOGGER = Logger.getLogger(Subscriptor.class.getName());


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

    }

    /**
     * Create a subscription on Orion
     *
     * @param updateFunction a function which does a update when came a notification.
     * @param en a entity object representing the entity
     */
    public void subscribe(Lambda updateFunction, Entity en) {

        Orion orion = new Orion();
        List<Lambda> updateFunctionList = new ArrayList<>();

       if (!this.subscriptions.containsKey(en.getId())) {
            orion.createSimpleSubscription(en.getId(), en.getType(), this.port, this.ip, false);
            updateFunctionList.add(updateFunction);
            subscriptions.put(en.getId(), updateFunctionList);
        }
        else { // caso o Id já exista no Map, então tem que colocar as funções em uma lista
            subscriptions.get(en.getId());
        }
    }


    /**
     * Create a subscription on Orion and listen to notifications.
     * When a notification come, aurn:ngsi-ld:Lamp:1 update function associated with entity is applied to entity values.
     *
     * @param updateFunction a function which does a update when came a notification.
     * @param obj the entity object which have to update.
     */
    public void subscribeAndListen(Lambda updateFunction, Entity obj) {

        Subscriptor sub = new Subscriptor(this.port, this.ip, this.entityId, this.type, this.server, obj);
        sub.subscribe(updateFunction, this.en);

        Thread t = new Thread(sub);
        t.start();
    }

    @Override
    public void run() {

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
        System.out.println(lastLine);
        notification = gson.fromJson(lastLine, GenericNotification.class);

        for (Object entities: notification.getData()) {

            json = gson.toJson(entities);
            this.en = gson.fromJson(json, this.en.getClass());

            List<Lambda> updateFunctionList = subscriptions.get(this.en.getId());
            for (Lambda fList: updateFunctionList)
                this.en = fList.lambdaUpdate(this.en);
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
