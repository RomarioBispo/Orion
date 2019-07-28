package br.com.ufs.examples.room.context.subscription;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;

public class Subscription {
    private String description;
    private Subject subject;
    private Notification notification;
    private String expires;
    private int throttling;

    public Subscription(String entityID, String url) throws Exception {
        this.description = "Motion:001 Subscription";
        this.subject = new Subject(entityID);
        this.notification = new Notification(new Http(url), "count");
        this.expires = expires;
        this.throttling = throttling;

//        Gson gson = new Gson();
//        String json = gson.toJson(this);
//
//        runPostRequest("http://localhost:1026/v2/subscriptions", json);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public int getThrottling() {
        return throttling;
    }

    public void setThrottling(int throttling) {
        this.throttling = throttling;
    }

    public static class Subject {
        private Entity[] entities;

        private Condition condition;

        public Subject(String entityID) {
            Entity[] entity = {new Entity(entityID, "Motion")};
            this.entities = entity;
            String[] attrs = {"count"};
            this.condition = new Condition(attrs);
        }

        public Entity[] getEntities() {
            return entities;
        }

        public void setEntities(Entity[] entities) {
            this.entities = entities;
        }

        public Condition getCondition() {
            return condition;
        }

        public void setCondition(Condition condition) {
            this.condition = condition;
        }
    }

    public static class Entity {
        private String id;

        private String type;

        public Entity(String id, String type) {
            this.id = id;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Condition {
        private String[] attrs;

        public Condition(String[] attrs) {
            this.attrs = attrs;
        }


        public String[] getAttrs() {
            return attrs;
        }

        public void setAttrs(String[] attrs) {
            this.attrs = attrs;
        }
    }

    public static class Notification {
        private Http http;

        private String[] attrs;

        public Notification(Http http, String attrs) {
            this.http = http;
            String[] attr = {attrs};
            this.attrs = attr;
        }

        public Http getHttp() {
            return http;
        }

        public void setHttp(Http http) {
            this.http = http;
        }

        public String[] getAttrs() {
            return attrs;
        }

        public void setAttrs(String[] attrs) {
            this.attrs = attrs;
        }
    }

    public static class Http {
        private String url;

        public Http(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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
}
