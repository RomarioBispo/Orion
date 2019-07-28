package br.com.ufs.examples.room.context.device;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;

public class AirConditioner {
    private Device[] devices;

    public AirConditioner(String device_id, String entity_name) throws Exception {
        /*
            - A mesma lógica do sensor de movimento é aplicada aqui.
            - São criados os atributos e os atributos estáticos
            - Por fim, é realizada uma requisição POST para o registro do Ar-condicionado no IoT-A.
          */
        Device.Attribute[] attributes = {new Device.Attribute("t", "temperature", "Float"),
                                         new Device.Attribute("s", "state", "Text"),
                                         new Device.Attribute("m", "mode", "Text")};
        Device.StaticAttribute[] staticAttributes = {new Device.StaticAttribute("refRoom", "Relationship", "urn:ngsi-ld:Room:001")};
        Device[] deviceList = {new Device(device_id, entity_name, "AirConditioner", "America/Belem", attributes, staticAttributes)};
        devices = deviceList;

//        Gson gson = new Gson();
//        String json = gson.toJson(this);
//
//        runPostRequest("http://localhost:4041/iot/devices", json);
    }
    public Device[] getDevices(){
        return this.devices;
    }

    public String getState() throws Exception {
        String stateString = "";
        Gson gson = new Gson();
        stateString = runGetRequest("http://localhost:1026/v2/entities/"+ this.devices[0].getEntity_name() +"?attrs=state");

        return gson.fromJson(stateString, Entity.class).state.getValue();
    }

    public double getTemperature() throws Exception {
        String temperatureString = "";
        Gson gson = new Gson();
        temperatureString = runGetRequest("http://localhost:1026/v2/entities/"+ this.devices[0].getEntity_name() +"?attrs=temperature");

        return gson.fromJson(temperatureString, Entity.class).temperature.getValue();
    }

    public String getMode() throws Exception {
        String modeString = "";
        Gson gson = new Gson();
        modeString = runGetRequest("http://localhost:1026/v2/entities/"+ this.devices[0].getEntity_name() +"?attrs=mode");

        return gson.fromJson(modeString, Entity.class).mode.getValue();
    }

    private static void runPostRequest(String url, String requestBody) throws Exception {
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

    public static class Entity {
        private String id;
        private String type;
        private State state;
        private Temperature temperature;
        private Mode mode;

        public State getState() {
            return state;
        }

        public Temperature getTemperature() {
            return temperature;
        }
        public Mode getMode(){return mode;}
    }

    public static class State {
        private String type;
        private String value;

        public String getValue() {
            return value;
        }

    }

    public static class Temperature {
        private String type;
        private double value;

        public double getValue() {
            return value;
        }

    }

    public static class Mode {
        private String type;
        private String value;

        public String getValue() {
            return value;
        }

    }
}
