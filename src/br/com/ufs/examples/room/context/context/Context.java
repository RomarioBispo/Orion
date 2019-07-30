//package br.com.ufs.examples.room.context.context;
//
//import com.google.api.client.http.*;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.JsonObjectParser;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.gson.Gson;
//import com.java.accumulator.Accumulator;
//import com.java.device.AirConditioner;
//import com.java.device.MotionSensor;
//import com.java.notification.CountNotification;
//import com.java.room.Room;
//import com.java.room.Room.Temperature;
//import com.java.room.Room.Occupation;
//import com.java.servicegroup.ServiceGroup;
//import com.java.subscription.Subscription;
//import java.util.concurrent.TimeUnit;
//
//public class Context {
//    private Room room;
//    private ServiceGroup serviceGroup;
//    private MotionSensor motionSensor;
//    private AirConditioner airConditioner;
//    private Subscription subscription;
//    private Accumulator accumulator;
//    private int motionDetected;
//
//    private Context() throws Exception {
//        /*
//            - Inicia o servidor
//            - Cria a entidade da sala no broker
//            - Cria o Grupo de Serviços para o IoT-A
//            - Cria os sensores e atuadores no IoT-A
//
//        */
//
//        Gson gson = new Gson();
//
//        this.accumulator = new Accumulator(40041, "172.18.1.1");
//
//        this.room = new Room("urn:ngsi-ld:Room:001", 50, "Auditorio", 0, 35.0);
//        String json = gson.toJson(this.room);
//        runPostRequest("http://localhost:1026/v2/entities", json);
//
//        this.serviceGroup = new ServiceGroup("6NUB3eD0YERJml1btYssPOa1qY", "http://localhost:1026", "Thing", "/iot/d");
//        json = gson.toJson(this.serviceGroup);
//        runPostRequest("http://localhost:4041/iot/services", json);
//
//        this.motionSensor = new MotionSensor("motion001", "urn:ngsi-ld:Motion:001");
//        json = gson.toJson(this.motionSensor);
//        runPostRequest("http://localhost:4041/iot/devices", json);
//
//        this.airConditioner = new AirConditioner("ac001", "urn:ngsi-ld:AC:001");
//        json = gson.toJson(this.airConditioner);
//        runPostRequest("http://localhost:4041/iot/devices", json);
//
//        this.subscription = new Subscription("urn:ngsi-ld:Motion:001", "http://172.18.1.1:40041/");
//        json = gson.toJson(this.subscription);
//        runPostRequest("http://localhost:1026/v2/subscriptions", json);
//    }
//    //romario
//
//    public void setMode() throws Exception {
////        //instancia um novo JSONObject
////        JSONObject my_obj = new JSONObject();
////
////        //preenche o objeto com os campos: titulo, ano e genero
////        my_obj.put("actionType", "APPEND");
////        my_obj.put("entities", my_obj.put("id", "urn:ngsi-ld:AC:001"
////        ), my_obj.put("mode", my_obj.put("type","Text")));
////        my_obj.put("genero", "Ação");
////
////        //serializa para uma string e imprime
////        String json_string = my_obj.toString();
////        System.out.println("objeto original -> " + json_string);
////        System.out.println();
////
//
////        {"id":"urn:ngsi-ld:AC:001","type":"AirConditioner","mode":{"type":"Text","value":"turbo","metadata":{"TimeInstant":{"type":"DateTime","value":"2019-06-13T01:11:24.00Z"}}}}
//        String requestBody = "{\"actionType\":\"APPEND\",\"entities\":[{\"id\":\"" + "urn:ngsi-ld:AC:001" + "\",\"mode\":{\"type\":\"Text\", \"value\":" + "normal" + "}}]}";
//        runPostRequest("http://localhost:1026/v2/op/update", requestBody);
//    }
//
//    public double getTemperature() throws Exception {
//        String temperatureString = "";
//        Gson gson = new Gson();
//        temperatureString = runGetRequest("http://localhost:1026/v2/entities/"+ this.airConditioner.getDevices()[0].getEntity_name() +"?attrs=temperature");
//
//        return gson.fromJson(temperatureString, AirConditioner.Entity.class).getTemperature().getValue();
//    }
//
//    public String getMode() throws Exception {
//        String modeString = "";
//        Gson gson = new Gson();
//        modeString = runGetRequest("http://localhost:1026/v2/entities/"+ this.airConditioner.getDevices()[0].getEntity_name() +"?attrs=mode");
//
//        return gson.fromJson(modeString, AirConditioner.Entity.class).getMode().getValue();
//    }
//
//    public Room.Temperature getTemperature(Context context) throws Exception {
//        String temperatureString = "";
//        Gson gson = new Gson();
//        temperatureString = runGetRequest("http://localhost:1026/v2/entities/"+ context.room.getId());
//
//        return gson.fromJson(temperatureString, Room.class).getTemp();
//    }
//
//    public void setTemperature(double temperature, Context context) throws Exception {
//        Temperature temp = new Temperature(temperature);
//        String requestBody = "{\"actionType\":\"APPEND\",\"entities\":[{\"id\":\"" + context.room.getId() + "\",\"temperature\":{\"type\":\"Float\", \"value\":" + temperature + "}}]}";
//        runPostRequest("http://localhost:1026/v2/op/update", requestBody);
//    }
//
//    public Occupation getOccupation(Context context) throws Exception {
//        String occupationString = "";
//        Gson gson = new Gson();
//        occupationString = runGetRequest("http://localhost:1026/v2/entities/"+ context.room.getId());
//
//        return gson.fromJson(occupationString, Room.class).getOcc();
//    }
//
//    public void setOccupation(int occupation, Context context) throws Exception {
//        Occupation occ = new Occupation(occupation);
//        String requestBody = "{\"actionType\":\"APPEND\",\"entities\":[{\"id\":\"" + this.room.getId() + "\",\"occupation\":{\"type\":\"Integer\", \"value\":" + occupation + "}}]}";
//        runPostRequest("http://localhost:1026/v2/op/update", requestBody);
//    }
//    public static void manageContextOn (Context context) throws Exception {
////        if(context.motionDetected != 0 && context.airConditioner.getMode().equals("turbo")) {
//        if(context.motionDetected != 0 && context.getMode().equals("turbo")) {
////            context.room.setOccupation(context.room.getOccupation().getValue() + context.motionDetected);
//            context.setOccupation(context.getOccupation(context).getValue() + context.motionDetected, context);
////            context.room.setTemperature(context.room.getTemperature().getValue() + (double)context.motionDetected * 0.025);
//            context.setTemperature(context.getTemperature(context).getValue() + (double)context.motionDetected * 0.025, context);
//            //System.out.println("Temperatura: "+context.room.getTemperature().getValue() + (double)context.motionDetected * 0.025);
//        }
////        else if(context.motionDetected != 0 && context.airConditioner.getMode().equals("normal")) {
//        else if(context.motionDetected != 0 && context.getMode().equals("normal")) {
////            context.room.setOccupation(context.room.getOccupation().getValue() - context.motionDetected);
//            context.setOccupation(context.getOccupation(context).getValue() - context.motionDetected, context);
//        }
////        if(context.room.getTemperature().getValue() > context.airConditioner.getTemperature()) {
//        if(context.getTemperature(context).getValue() > context.getTemperature()) {
////            context.room.setTemperature(context.room.getTemperature().getValue() - 1);
//            context.setTemperature(context.getTemperature(context).getValue() - 1, context);
//        }
//        else {
////            context.room.setTemperature(context.room.getTemperature().getValue() + 1);
//            context.setTemperature(context.getTemperature(context).getValue() + 1, context);
//        }
//    }
//    public static void manageContextOff (Context context, double initialTemperature) throws Exception {
////        if(context.room.getTemperature().getValue() < initialTemperature) {
//        if(context.getTemperature(context).getValue() < initialTemperature) {
//            context.setTemperature(context.getTemperature(context).getValue() + 1, context);
//        }
//        else {
////            context.room.setTemperature(context.room.getTemperature().getValue() - 1);
//            context.setTemperature(context.getTemperature(context).getValue() - 1, context);
//        }
//    }
//    public static void printContext(Context context) throws Exception {
////        System.out.println("Temperatura " + context.room.getTemperature().getValue());
//        System.out.println("Temperatura " + context.getTemperature(context).getValue());
////        System.out.println("Ocupacao " + context.room.getOccupation().getValue());
//        System.out.println("Ocupacao " + context.getOccupation(context).getValue());
//    }
//    public static void main(String[] args) throws Exception {
//        /*
//            - Instância da classe context, que, por sua vez, realiza a instância de todos os elementos do ambiente (server, entidades etc)
//         */
//        Context context = new Context();
//        double initialTemperature = context.room.getTemperature().getValue(); // não entendi isso
//        Gson gson = new Gson();
//        System.out.println("Aguardando conexão pelo simulador");
//        // não entendi o pq colocar esse método aqui, aparentemente não altera o funcionamento do app
//        context.accumulator.listen(); // fica aguardando o método arrivals do simulator enviar medidas do sensor de presença.
//
//        while(true) {
//            System.out.println("entrei");
//            CountNotification countNotification = gson.fromJson(context.accumulator.listen(), CountNotification.class);
//            for(CountNotification.Data notificationData: countNotification.getData()){
//                context.motionDetected = notificationData.getCount().getValue();
//                //System.out.println("Quantidade de pessoas detectadas: \n"+context.motionDetected);
//            }
//
//            // criar um método manageContextOn e managecontextOff
//            if(context.airConditioner.getState().equals("on")) {
//                context.setMode();
////                System.out.println(runGetRequest("http://localhost:1026/v2/entities/"));
//                //manageContextOn(context);
//                // Se o Ar-condicionado estiver ligado e chegaram pessoas e seu modo é turbo
////                if(context.motionDetected != 0 && context.airConditioner.getMode().equals("turbo")) {
////                    context.room.setOccupation(context.room.getOccupation().getValue() + context.motionDetected);
////                    context.room.setTemperature(context.room.getTemperature().getValue() + (double)context.motionDetected * 0.025);
////                    //System.out.println("Temperatura: "+context.room.getTemperature().getValue() + (double)context.motionDetected * 0.025);
////                }
////                else if(context.motionDetected != 0 && context.airConditioner.getMode().equals("normal")) {
////                    context.room.setOccupation(context.room.getOccupation().getValue() - context.motionDetected);
////                }
////                if(context.room.getTemperature().getValue() > context.airConditioner.getTemperature()) {
////                    context.room.setTemperature(context.room.getTemperature().getValue() - 1);
////                }
////                else {
////                    context.room.setTemperature(context.room.getTemperature().getValue() + 1);
////                }
//            }
//            else {
//                  //manageContextOff(context, initialTemperature);
////                if(context.room.getTemperature().getValue() < initialTemperature) {
////                    context.room.setTemperature(context.room.getTemperature().getValue() + 1);
////                }
////                else {
////                    context.room.setTemperature(context.room.getTemperature().getValue() - 1);
////                }
//            }
//            printContext(context);
//            // criar método print context ou algo do tipo que faz esses prints
////            System.out.println("Temperatura " + context.room.getTemperature().getValue());
////            System.out.println("Ocupacao " + context.room.getOccupation().getValue());
//        }
//    }
//
//    private static void runPostRequest(String url, String requestBody) throws Exception {
//        HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
//        JsonFactory JSON_FACTORY = new JacksonFactory();
//        HttpRequestFactory requestFactory =
//                HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));
//        GenericUrl operationUrl = new GenericUrl(url);
//        HttpRequest request = requestFactory.buildPostRequest(operationUrl, ByteArrayContent.fromString("application/json", requestBody));
//        HttpHeaders headers = request.getHeaders();
//        headers.set("fiware-service", "openiot");
//        headers.set("fiware-servicepath", "/");
//
//        request.execute().parseAsString();
//    }
//
//    private static String runGetRequest(String url) throws Exception {
//        HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
//        JsonFactory JSON_FACTORY = new JacksonFactory();
//        HttpRequestFactory requestFactory =
//                HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));
//        GenericUrl operationUrl = new GenericUrl(url);
//        HttpRequest request = requestFactory.buildGetRequest(operationUrl);
//        HttpHeaders headers = request.getHeaders();
//        headers.set("fiware-service", "openiot");
//        headers.set("fiware-servicepath", "/");
//
//        return request.execute().parseAsString();
//    }
//}
