package br.com.ufs.examples.room.context.servicegroup;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;

public class ServiceGroup {
    private Service[] services;

    public ServiceGroup(String apikey, String cbroker, String entity_type, String resource) throws Exception {
        Service[] service = {new Service(apikey, cbroker, entity_type, resource)};
        this.services = service;

//        Gson gson = new Gson();
//        String json = gson.toJson(this);
//
//        runPostRequest("http://localhost:4041/iot/services", json);
    }

    public static class Service {
        private String apikey;

        private String cbroker;

        private String entity_type;

        private String resource;

        public Service(String apikey, String cbroker, String entity_type, String resource) {
            this.apikey = apikey;
            this.cbroker = cbroker;
            this.entity_type = entity_type;
            this.resource = resource;
        }

        public String getApiKey() {
            return apikey;
        }

        public void setApiKey(String apikey) {
            this.apikey = apikey;
        }

        public String getCbroker() {
            return cbroker;
        }

        public void setCbroker(String cbroker) {
            this.cbroker = cbroker;
        }

        public String getEntity_type() {
            return entity_type;
        }

        public void setEntity_type(String entity_type) {
            this.entity_type = entity_type;
        }

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
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
