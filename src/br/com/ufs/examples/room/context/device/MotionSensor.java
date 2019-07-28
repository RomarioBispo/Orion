package br.com.ufs.examples.room.context.device;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;

public class MotionSensor {
    private Device[] devices;

    public MotionSensor(String device_id, String entity_name) throws Exception {
        /*
            - Para registrar um sensor/atuador no IoT-A é necessário informar seus atributos e seus atributos estáticos
            - Neste caso, é criado um dispositivo com o atributo count que é do tipo inteiro, então assim que for feita uma requisição
            utilizando o protocolo ultralight, o nome da variável é reduzida a seu object_id, reduzindo, assim, seu payload e por fim,
            implicando em uma requisição mais "leve";
            - Os atributos estáticos definidos são necessários para indicar o relacionamento entre a entidade do orion e o dispositivo no IoT-A
               que, neste caso, significa que o dispositivo registrado está "dentro" da sala com urn definido em value
            - Por fim, a lista de dispositivos é informada, temos só um nesse caso, mas poderia ser uma lista, todos eles possuirão os mesmos atributos
            tanto estáticos, quando os atributos comuns.

        */
        Device.Attribute[] attributes = {new Device.Attribute("c", "count", "Integer")};
        Device.StaticAttribute[] staticAttributes = {new Device.StaticAttribute("refRoom", "Relationship", "urn:ngsi-ld:Room:001")};
        Device[] deviceList = {new Device(device_id, entity_name, "Motion", "America/Belem", attributes, staticAttributes)};
        devices = deviceList;

//        Gson gson = new Gson();
//        String json = gson.toJson(this);
//
//        runPostRequest("http://localhost:4041/iot/devices", json);
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
