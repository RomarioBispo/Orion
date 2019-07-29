package br.com.ufs.examples.room.context.room;

import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.entity.Entity;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import com.google.api.client.http.*;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

public class Room extends Entity {
    private Attrs maxCapacity;
    private Attrs name;
    private Attrs occupation;
    private Attrs temperature;

    public Room () {

    }
    public Room(String id, String type, Attrs maxCapacity, Attrs name, Attrs occupation, Attrs temperature) throws Exception {
        this.id = id;
        this.type = type;
        this.maxCapacity = maxCapacity;
        this.name = name;
        this.occupation = occupation;
        this.temperature = temperature;
    }

    public Attrs getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Attrs maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Attrs getName() {
        return name;
    }

    public void setName(Attrs name) {
        this.name = name;
    }

    public Attrs getOccupation() {
        return occupation;
    }

    public void setOccupation(Attrs occupation) {
        this.occupation = occupation;
    }

    public Attrs getTemperature() {
        return temperature;
    }

    public void setTemperature(Attrs temperature) {
        this.temperature = temperature;
    }
//    public Occupation getOccupation() throws Exception {
//        String occupationString = "";
//        Gson gson = new Gson();
//        occupationString = runGetRequest("http://localhost:1026/v2/entities/"+ this.id);
//
//        return gson.fromJson(occupationString, Room.class).occupation;
//    }
//
//    public void setOccupation(int occupation) throws Exception {
//        this.occupation = new Occupation(occupation);
//        String requestBody = "{\"actionType\":\"APPEND\",\"entities\":[{\"id\":\"" + this.id + "\",\"occupation\":{\"type\":\"Integer\", \"value\":" + occupation + "}}]}";
//        String json = "{\"id\":\"urn:ngsi-ld:AC:001\",\"type\":\"AirConditioner\",\"mode\":{\"type\":\"Text\",\"value\":\"normal\",\"metadata\":{\"TimeInstant\":{\"type\":\"DateTime\",\"value\":\"2019-06-13T16:46:44.00Z\"}}}}\n";
//        runPostRequest("http://localhost:1026/v2/op/update", json);
//    }
//
//    public Temperature getTemperature() throws Exception {
//        String temperatureString = "";
//        Gson gson = new Gson();
//        temperatureString = runGetRequest("http://localhost:1026/v2/entities/"+ this.id);
//
//        return gson.fromJson(temperatureString, Room.class).temperature;
//    }
//
//    public void setTemperature(double temperature) throws Exception {
//        this.temperature = new Temperature(temperature);
//        String requestBody = "{\"actionType\":\"APPEND\",\"entities\":[{\"id\":\"" + this.id + "\",\"temperature\":{\"type\":\"Float\", \"value\":" + temperature + "}}]}";
//        runPostRequest("http://localhost:1026/v2/op/update", requestBody);
//    }

    @Override
    public String getType() {
        return this.type;
    }

    // romario
    public String getId() {
        return this.id;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

//    public Temperature getTemp() {
//        return this.temperature;
//    }
//    public Occupation getOcc() {
//        return this.occupation;
//    }
//    // romario

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
}
