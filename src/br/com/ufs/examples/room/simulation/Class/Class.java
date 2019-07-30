package br.com.ufs.examples.room.simulation.Class;

import br.com.ufs.iotaframework.iota.IoTA;
import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.orion.Orion;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import br.com.ufs.examples.room.context.airconditioner.AirConditioner;

/**
 * This class is used to concept proof for the Orion Framework.
 * To this only purpose, the project developed in Felipe Matheus undergraduate thesis was used.
 * Your project was modified to use the framework.
 *
 * @author Romario Bispo, Felipe Matheus.
 * @version %I%, %G%
 * @since 1.0
 * @see br.com.ufs.examples.room.context.context.ContextExample;
 * */

public class Class {
    private int students;
    private int occupation;
    private int duration = 100;
    private int realTime;
    private int timeSpent;
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpRequestFactory requestFactory =
            HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));
    private static IoTA iota = new IoTA();
    private static Orion orion = new Orion();

    public Class() throws Exception {
        this.students = ThreadLocalRandom.current().nextInt(10, 51);
        this.occupation = 0;
        this.realTime = ThreadLocalRandom.current().nextInt(30, duration - 10);
        timeSpent = 0;
        iota.sendMeasure("localhost:7896", "/iot/d/","6NUB3eD0YERJml1btYssPOa1qY", "ac001", "s|off|t|20.0|m|normal");

    }

    private void arrivals() throws Exception {
        double temperature = 35.0;
        iota.sendMeasure("localhost:7896", "/iot/d/","6NUB3eD0YERJml1btYssPOa1qY", "ac001", "m|turbo");

        int arrived = ThreadLocalRandom.current().nextInt(0, (students+1)/3);

        while(occupation + arrived < students && timeSpent + 15 < duration) {

            iota.sendMeasure("localhost:7896", "/iot/d/","6NUB3eD0YERJml1btYssPOa1qY", "ac001", "c|"+occupation);

            occupation += arrived;
            orion.updateAttributeData("urn:ngsi-ld:Room:001","occupation", new Attrs(String.valueOf(occupation), "Integer"));

            AirConditioner airConditioner = (AirConditioner) orion.retrieveEntity("urn:ngsi-ld:AC:001", new AirConditioner());
            if (airConditioner.getMode().equals("turbo")){
                temperature = temperature + arrived*0.025 - 2;
                orion.updateAttributeData("urn:ngsi-ld:Room:001","temperature", new Attrs(String.valueOf(temperature), "Float"));
            }else {
                temperature = temperature + arrived*0.025 - 1;
                orion.updateAttributeData("urn:ngsi-ld:Room:001","temperature", new Attrs(String.valueOf(temperature), "Float"));
            }

            TimeUnit.SECONDS.sleep(5);
            timeSpent += 5;
            arrived = ThreadLocalRandom.current().nextInt(0, (students+1)/3);
        }
        System.out.println("Chegaram "+ occupation +" pessoas em "+ timeSpent +" minutos");
    }
    private void departures() throws Exception {

        iota.sendMeasure("localhost:7896", "/iot/d/","6NUB3eD0YERJml1btYssPOa1qY", "ac001", "m|normal");

        int left = ThreadLocalRandom.current().nextInt(0, occupation+1);

        while(occupation - left > 0) {
            iota.sendMeasure("localhost:7896", "/iot/d/","6NUB3eD0YERJml1btYssPOa1qY", "ac001", "c|"+left);

            occupation -= left;
            orion.updateAttributeData("urn:ngsi-ld:Room:001","occupation", new Attrs(String.valueOf(occupation), "Integer"));
            timeSpent += 5;
            TimeUnit.SECONDS.sleep(5);

            System.out.println("Sairam "+left+" em 5 minutos");

            left = ThreadLocalRandom.current().nextInt(0, occupation+1);

            if(timeSpent + 10 >= duration || occupation - left <= 0) {
                iota.sendMeasure("localhost:7896", "/iot/d/","6NUB3eD0YERJml1btYssPOa1qY", "ac001", "c|"+occupation);
                occupation = 0;
                orion.updateAttributeData("urn:ngsi-ld:Room:001","occupation", new Attrs(String.valueOf(occupation), "Integer"));
                timeSpent += 5;
                TimeUnit.SECONDS.sleep(5);
            }
        }
        System.out.println("Sairam todos em "+ timeSpent +" minutos");
    }

    private void idleTime(int maxTime) throws Exception {
        iota.sendMeasure("localhost:7896", "/iot/d/","6NUB3eD0YERJml1btYssPOa1qY", "ac001", "m|normal");

        while(timeSpent < maxTime) {
            timeSpent += 5;
            iota.sendMeasure("localhost:7896", "/iot/d/","6NUB3eD0YERJml1btYssPOa1qY", "ac001", "c|0");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(timeSpent + " minutos passados");
        }
    }

    public void startClass() throws Exception {

        System.out.println(students + " alunos sao esperados");
        System.out.println("Tempo real da aula: "+ realTime + " minutos");
        iota.sendMeasure("localhost:7896", "/iot/d/","6NUB3eD0YERJml1btYssPOa1qY", "ac001", "s|on");
        System.out.println("Ar condicionado ligado");

        arrivals();

        idleTime(realTime);

        if(occupation > 0)
            departures();

        iota.sendMeasure("localhost:7896", "/iot/d/","6NUB3eD0YERJml1btYssPOa1qY", "ac001", "s|off");
        System.out.println("Ar condicionado desligado");

        idleTime(duration);

        System.out.println("Fim de aula\n");
    }

}