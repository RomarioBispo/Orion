package br.com.ufs.examples.room.simulation.Class;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.java.device.AirConditioner;

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

    public Class() throws Exception {
        this.students = ThreadLocalRandom.current().nextInt(10, 51);
        this.occupation = 0;
        this.realTime = ThreadLocalRandom.current().nextInt(30, duration - 10);
        timeSpent = 0;
        runPostRequest("http://localhost:7896/iot/d?k=6NUB3eD0YERJml1btYssPOa1qY&i=ac001", "s|off|t|20.0|m|normal");
    }

    // romario
    public void setOccupation(int occupation) throws Exception {
        String requestBody = "{\"actionType\":\"update\",\"entities\":[{\"id\":\"" + "urn:ngsi-ld:Room:001" + "\",\"occupation\":{\"type\":\"Integer\", \"value\":" + occupation + "}}]}";
        runJsonPostRequest("http://localhost:1026/v2/op/update", requestBody);
    }

    public void setTemperature(double temperature) throws Exception {
        String requestBody = "{\"actionType\":\"APPEND\",\"entities\":[{\"id\":\"" + "urn:ngsi-ld:Room:001" + "\",\"temperature\":{\"type\":\"Float\", \"value\":" + temperature + "}}]}";
        runJsonPostRequest("http://localhost:1026/v2/op/update", requestBody);
    }

    private static String runGetRequest(String url) throws Exception {
        HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        JsonFactory JSON_FACTORY = new JacksonFactory();
        HttpRequestFactory requestFactory =
                HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));
        GenericUrl operationUrl = new GenericUrl(url);
        HttpRequest request = requestFactory.buildGetRequest(operationUrl);
        HttpHeaders headers = request.getHeaders();
        headers.set("fiware-service", "openiot");
        headers.set("fiware-servicepath", "/");

        return request.execute().parseAsString();
    }

    public String getMode() throws Exception {
        String modeString = "";
        Gson gson = new Gson();
        modeString = runGetRequest("http://localhost:1026/v2/entities/"+ "urn:ngsi-ld:AC:001" +"?attrs=mode");
        String mode = gson.fromJson(modeString, AirConditioner.Entity.class).getMode().getValue();
        System.out.println(mode);
        return mode;
    }

    // romario

    private void arrivals() throws Exception {
        double temperature = 35.0;
        runPostRequest("http://localhost:7896/iot/d?k=6NUB3eD0YERJml1btYssPOa1qY&i=ac001", "m|turbo");

        int arrived = ThreadLocalRandom.current().nextInt(0, (students+1)/3);

        while(occupation + arrived < students && timeSpent + 15 < duration) {

            runPostRequest("http://localhost:7896/iot/d?k=6NUB3eD0YERJml1btYssPOa1qY&i=motion001", "c|"+occupation);
            occupation += arrived;
            setOccupation(occupation); // atualiza no broker

            if (getMode().equals("turbo")){
                temperature = temperature + arrived*0.025 - 2;
                setTemperature(temperature);
            }else {
                temperature = temperature + arrived*0.025 - 1;
                setTemperature(temperature);
            }

            TimeUnit.SECONDS.sleep(5);
            timeSpent += 5;
            arrived = ThreadLocalRandom.current().nextInt(0, (students+1)/3);
        }
        System.out.println("Chegaram "+ occupation +" pessoas em "+ timeSpent +" minutos");
    }
    private void departures() throws Exception {

        runPostRequest("http://localhost:7896/iot/d?k=6NUB3eD0YERJml1btYssPOa1qY&i=ac001", "m|normal");

        int left = ThreadLocalRandom.current().nextInt(0, occupation+1);

        while(occupation - left > 0) {
            runPostRequest("http://localhost:7896/iot/d?k=6NUB3eD0YERJml1btYssPOa1qY&i=motion001", "c|"+left);
            occupation -= left;
            setOccupation(occupation); // atualiza no broker
            timeSpent += 5;
            TimeUnit.SECONDS.sleep(5);

            System.out.println("Sairam "+left+" em 5 minutos");

            left = ThreadLocalRandom.current().nextInt(0, occupation+1);

            if(timeSpent + 10 >= duration || occupation - left <= 0) {
                runPostRequest("http://localhost:7896/iot/d?k=6NUB3eD0YERJml1btYssPOa1qY&i=motion001", "c|"+occupation);
                occupation = 0;
                setOccupation(occupation); // atualiza no broker
                timeSpent += 5;
                TimeUnit.SECONDS.sleep(5);
            }
        }
        System.out.println("Sairam todos em "+ timeSpent +" minutos");
    }

    private void idleTime(int maxTime) throws Exception {
        runPostRequest("http://localhost:7896/iot/d?k=6NUB3eD0YERJml1btYssPOa1qY&i=ac001", "m|normal");

        while(timeSpent < maxTime) {
            timeSpent += 5;
            runPostRequest("http://localhost:7896/iot/d?k=6NUB3eD0YERJml1btYssPOa1qY&i=motion001", "c|0");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(timeSpent + " minutos passados");
        }
    }

    public void startClass() throws Exception {
        System.out.println(students + " alunos sao esperados");
        System.out.println("Tempo real da aula: "+ realTime + " minutos");
        runPostRequest("http://localhost:7896/iot/d?k=6NUB3eD0YERJml1btYssPOa1qY&i=ac001", "s|on");
        System.out.println("Ar condicionado ligado");

        arrivals();

        idleTime(realTime);

        if(occupation > 0)
            departures();

        runPostRequest("http://localhost:7896/iot/d?k=6NUB3eD0YERJml1btYssPOa1qY&i=ac001", "s|off");
        System.out.println("Ar condicionado desligado");

        idleTime(duration);

        System.out.println("Fim de aula\n");
    }

    private void runPostRequest(String url, String requestBody) throws Exception {
        GenericUrl operationUrl = new GenericUrl(url);
        HttpRequest request = requestFactory.buildPostRequest(operationUrl, ByteArrayContent.fromString("text/plain", requestBody));
        HttpHeaders headers = request.getHeaders();
        headers.set("fiware-service", "openiot");
        headers.set("fiware-servicepath", "/");

        request.execute().parseAsString();
    }

    private static void runJsonPostRequest(String url, String requestBody) throws Exception {
        HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        JsonFactory JSON_FACTORY = new JacksonFactory();
        HttpRequestFactory requestFactory =
                HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));
        GenericUrl operationUrl = new GenericUrl(url);
        HttpRequest request = requestFactory.buildPostRequest(operationUrl, ByteArrayContent.fromString("application/json", requestBody));
        HttpHeaders headers = request.getHeaders();
        headers.set("fiware-service", "openiot");
        headers.set("fiware-servicepath", "/");

        request.execute().parseAsString();
    }
}