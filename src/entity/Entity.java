package entity;


import com.google.gson.Gson;
import entityLamp.Attrs;
import genericNotification.GenericNotification;
import orion.Orion;
import server.TCPServer;
import subscription.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * This class is used to help to represent a object java as JSON on a NGSIv2 form.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */
public class Entity{

    private String type;
    private String id;
    private Attrs attribute1;
    private Attrs attribute2;
    private Attrs attribute3;

    public Entity(String id, String type, Attrs attribute1, Attrs attribute2, Attrs attribute3) {
        this.type = type;
        this.id = id;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
    }

    public Attrs getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(Attrs attribute1) {
        this.attribute1 = attribute1;
    }

    public Attrs getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(Attrs attribute2) {
        this.attribute2 = attribute2;
    }

    public Attrs getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(Attrs attribute3) {
        this.attribute3 = attribute3;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    /**
     * this operation subscribe yourself on Orion to receive notifications about changed values
     *
     * @param port an given port from where the server is listening.
     * @param ip an given IP address from where the server is running.
     * @return the entity updated with values from notification.
     */
    public Future<String> subscribeAndListen(int port, String ip) throws Exception {

        //https://www.guj.com.br/t/duvida-com-future-e-callable/138534/4

        Orion orion = new Orion();
        orion.createSimpleSubscription(this.id, this.type, port, ip);

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<String> future = executor.submit( new TCPServer(port, ip));

        executor.shutdown();

        return future;

    }
}
