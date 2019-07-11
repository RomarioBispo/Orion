package br.com.ufs.orionframework.subscriptor;

import com.google.gson.Gson;
import br.com.ufs.orionframework.genericnotification.GenericNotification;
import br.com.ufs.orionframework.orion.Orion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Subscriptor make a subscription on Orion and listen notifications.
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */
public class Subscriptor implements Runnable {
    private Map<String, lambda> subscriptions = new HashMap<>();
    private int port;
    private String ip;
    private Object obj;
    private String entityId;
    private String type;
    private ServerSocket server;

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

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Subscriptor(int port, String ip, String entityId, String type, ServerSocket server) throws IOException {
        this.port = port;
        this.ip = ip;
        this.entityId = entityId;
        this.type = type;
        this.server = server;

    }

    /**
     * Create a subscription on Orion and listen to notifications.
     *
     * @param updateFunction a function which does a update when came a notification.
     * @param obj the entity object which have to update.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     */
    public void subscribe(lambda updateFunction, Object obj) throws Exception {

        // Essa atribuição tá desconexa, fazer no construtor
        this.obj = obj;

        Orion orion = new Orion();

        if (subscriptions.isEmpty()) {
            orion.createSimpleSubscription(this.entityId, this.type, this.port, this.ip, false);
            subscriptions.put(entityId, updateFunction);
        }
        else if (!subscriptions.containsKey(entityId)) {
            orion.createSimpleSubscription(this.entityId, this.type, this.port, this.ip, false);
            subscriptions.put(entityId, updateFunction);
        }
        else { // caso o Id já exista no Map, então tem que colocar as funções em uma lista
            subscriptions.get(entityId);
        }
    }

    public void subscribeAndListen(lambda updateFunction, Object obj) throws Exception {
        Subscriptor sub = new Subscriptor(this.port, this.ip, this.entityId, this.type, this.server);
        sub.subscribe(updateFunction, obj);
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
            e.printStackTrace();
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (!((data = in.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            lastLine = data;
        }

        System.out.println(lastLine);
        notification = gson.fromJson(json, GenericNotification.class);

        // pegar a entidade como objeto
        System.out.println(json);
        this.obj = gson.fromJson(json, this.obj.getClass());
        // aplicar a função
        lambda f = subscriptions.get(this.entityId);
        this.obj = f.lambdaUpdate(obj);
    }

    @FunctionalInterface
    public interface lambda {
        Object lambdaUpdate(Object object);
    }
}
