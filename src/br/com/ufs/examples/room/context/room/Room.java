package br.com.ufs.examples.room.context.room;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import com.google.api.client.http.*;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

public class Room {
    private String id;
    private String type;
    private MaxCapacity maxCapacity;
    private Name name;
    private Occupation occupation;
    private Temperature temperature;

    public Room(String id, int maxCapacity, String name, int occupation, double temperature) throws Exception {
        this.id = id;
        this.type = type = "Room";
        this.maxCapacity = new MaxCapacity(maxCapacity);
        this.name = new Name(name);
        this.occupation = new Occupation(occupation);
        this.temperature = new Temperature(temperature);

//        Gson gson = new Gson();
//        String json = gson.toJson(this);
//
//        runPostRequest("http://localhost:1026/v2/entities", json);
    }

    public Occupation getOccupation() throws Exception {
        String occupationString = "";
        Gson gson = new Gson();
        occupationString = runGetRequest("http://localhost:1026/v2/entities/"+ this.id);

        return gson.fromJson(occupationString, Room.class).occupation;
    }

    public void setOccupation(int occupation) throws Exception {
        this.occupation = new Occupation(occupation);
        String requestBody = "{\"actionType\":\"APPEND\",\"entities\":[{\"id\":\"" + this.id + "\",\"occupation\":{\"type\":\"Integer\", \"value\":" + occupation + "}}]}";
        String json = "{\"id\":\"urn:ngsi-ld:AC:001\",\"type\":\"AirConditioner\",\"mode\":{\"type\":\"Text\",\"value\":\"normal\",\"metadata\":{\"TimeInstant\":{\"type\":\"DateTime\",\"value\":\"2019-06-13T16:46:44.00Z\"}}}}\n";
        runPostRequest("http://localhost:1026/v2/op/update", json);
    }

    public Temperature getTemperature() throws Exception {
        String temperatureString = "";
        Gson gson = new Gson();
        temperatureString = runGetRequest("http://localhost:1026/v2/entities/"+ this.id);

        return gson.fromJson(temperatureString, Room.class).temperature;
    }

    public void setTemperature(double temperature) throws Exception {
        this.temperature = new Temperature(temperature);
        String requestBody = "{\"actionType\":\"APPEND\",\"entities\":[{\"id\":\"" + this.id + "\",\"temperature\":{\"type\":\"Float\", \"value\":" + temperature + "}}]}";
        runPostRequest("http://localhost:1026/v2/op/update", requestBody);
    }

    // romario
    public String getId() {
        return this.id;
    }
    public Temperature getTemp() {
        return this.temperature;
    }
    public Occupation getOcc() {
        return this.occupation;
    }
    // romario

    public static class Name {
        private String type = "Text";

        private String value;

        private Name(String value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class MaxCapacity {
        private String type = "Integer";

        private int value;

        private MaxCapacity(int value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class Occupation {
        private String type = "Integer";

        private int value;

        public Occupation(int value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }


    public static class Temperature {
        private String type = "Float";

        private double value;

        public Temperature(double value) {
            this.value = value;
        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
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
}
