package br.com.ufs.examples.room.context.context;

import br.com.ufs.examples.room.context.airconditioner.AirConditioner;
import br.com.ufs.examples.room.context.motion.Motion;
import br.com.ufs.examples.room.context.room.Room;
import br.com.ufs.iotaframework.devices.Attribute;
import br.com.ufs.iotaframework.devices.Device;
import br.com.ufs.iotaframework.devices.DeviceList;
import br.com.ufs.iotaframework.devices.StaticAttribute;
import br.com.ufs.iotaframework.iota.IoTA;
import br.com.ufs.iotaframework.services.Service;
import br.com.ufs.iotaframework.services.ServiceGroup;
import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.orion.Orion;
import br.com.ufs.orionframework.subscriptor.Subscriptor;

import javax.naming.Context;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ContextExample {

    private static Orion orion = new Orion();
    private static IoTA iota = new IoTA();
    private static Room room = new Room();
    private static AirConditioner airConditioner = new AirConditioner();
    private static double initialTemperature;

    private ContextExample() throws Exception {

        // creating entidade no orion.
        Attrs name = new Attrs("Auditorio","Text");
        Attrs maxCapacity = new Attrs("50", "Integer");
        Attrs temperature = new Attrs("35.0","Float");
        Attrs occupation = new Attrs("0", "Integer");

        Room room = new Room ("urn:ngsi-ld:Room:001", "Room", name, maxCapacity, temperature, occupation);
        orion.createEntity(room);

        // creating service group
        Service service = new Service("6NUB3eD0YERJml1btYssPOa1qY","","http://orion:1026","Thing","/iot/d");
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(service);
        ServiceGroup serviceGroup = new ServiceGroup(serviceList);
        iota.createService(serviceGroup);

        //Create Device
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute("t","temperature","Float"));
        attributeList.add(new Attribute("s","state","Text"));
        attributeList.add(new Attribute("m", "mode", "Text"));

        List<StaticAttribute> staticAttributeList = new ArrayList<>();
        staticAttributeList.add(new StaticAttribute("refRoom", "Relationship", "urn:ngsi-ld:Room:001"));

        List<Device> deviceList = new ArrayList<>();

        // TENHO QUE TROCAR O PROTOCOL PARA IOTA ULTRALIGHT NO DE MARIANA ?????
        // OLHAR TAMBÉM A CRIAÇÃO DO GRUPO DE SERVICOS, APARENTEMENTE TEM UM ANINHAMENTO DE LISTAS QUE É DESNECESSÁRIO.

        deviceList.add(new Device("ac001","123456","urn:ngsi-ld:AC:001",
                "AirConditioner","America/Belem", attributeList,staticAttributeList));

        DeviceList devices = new DeviceList(deviceList);

        iota.createDevice(devices);

        //Create Device
        List<Attribute> attributeListMotion = new ArrayList<>();
        attributeList.add(new Attribute("c", "count", "Integer"));

        List<Device> deviceListMotion = new ArrayList<>();
        deviceListMotion.add(new Device("motion001", "123456", "urn:ngsi-ld:Motion:001", "Motion",
                "America/Belem", attributeListMotion, staticAttributeList));

        orion.createSimpleSubscription("urn:ngsi-ld:Motion:001", "Room", 40041, "172.18.1.1", false);
    }


    public static void manageContextOnFramework(Motion motion, AirConditioner airConditioner, Room room, Orion orion) {

        int novaOcupacao;
        double novaTemperatura;

        room = (Room) orion.retrieveEntity("urn:ngsi-ld:Room:001", new Room());
        airConditioner = (AirConditioner) orion.retrieveEntity("urn:ngsi-ld:AC:001", new AirConditioner());

        int motionDetected = Integer.parseInt(motion.getCount().getValue());
        if (motionDetected !=0 && airConditioner.getMode().getValue().equals("turbo")){

            novaOcupacao = Integer.parseInt(room.getOccupation().getValue()) + motionDetected;
            novaTemperatura = Integer.parseInt(room.getTemperature().getValue()) + (double)motionDetected*0.025;

            orion.updateAttributeData(room.getId(), "occupation", new Attrs(String.valueOf(novaOcupacao), "Integer"));
            orion.updateAttributeData(room.getId(),"temperature", new Attrs(String.valueOf(novaTemperatura),"Float"));
        }
        else if (motionDetected!=0 && airConditioner.getMode().getValue().equals("normal")) {
            novaOcupacao = Integer.parseInt(room.getOccupation().getValue()) - motionDetected;
            orion.updateAttributeData(room.getId(), "temperature", new Attrs (String.valueOf(novaOcupacao), "Integer"));
        }
        if (Double.parseDouble(room.getTemperature().getValue()) > Double.parseDouble(airConditioner.getTemperature().getValue())) {
            novaTemperatura = Double.parseDouble(room.getTemperature().getValue()) - 1;
            orion.updateAttributeData(room.getId(),"temperature", new Attrs (String.valueOf(novaTemperatura), "Float"));
        }else {
            novaTemperatura = Double.parseDouble(room.getTemperature().getValue()) - 1;
            orion.updateAttributeData(room.getId(), "temperature", new Attrs(String.valueOf(novaTemperatura), "Float"));
        }
    }
//    public static void manageContextOn (ContextExample context) throws Exception {
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

      public static void manageContextOffFramework(AirConditioner airConditioner, Room room, double initialTemperature, Orion orion) {

        room = (Room) orion.retrieveEntity("urn:ngsi-ld:Room:001", new Room());
        airConditioner = (AirConditioner) orion.retrieveEntity("urn:ngsi-ld:AC:001", new AirConditioner());

        Double roomTemperature = Double.parseDouble(room.getTemperature().getValue());
        double novaTemperatura;
        if (roomTemperature < initialTemperature) {
            novaTemperatura = Double.parseDouble(airConditioner.getTemperature().getValue()) + 1;
            orion.updateAttributeData(room.getId(), "temperature", new Attrs(String.valueOf(novaTemperatura), "Float"));
        }else {
            novaTemperatura = Double.parseDouble(airConditioner.getTemperature().getValue()) - 1;
            orion.updateAttributeData(room.getId(), "temperature", new Attrs(String.valueOf(novaTemperatura), "Float"));
        }
      }

//    public static void manageContextOff (ContextExample context, double initialTemperature) throws Exception {
////        if(context.room.getTemperature().getValue() < initialTemperature) {
//        if(context.getTemperature(context).getValue() < initialTemperature) {
//            context.setTemperature(context.getTemperature(context).getValue() + 1, context);
//        }
//        else {
////            context.room.setTemperature(context.room.getTemperature().getValue() - 1);
//            context.setTemperature(context.getTemperature(context).getValue() - 1, context);
//        }
//    }

    public static void printContext(Room room) {
        room = (Room) orion.retrieveEntity("urn:ngsi-ld:Room:001", new Room());
        System.out.println("Temperatura: " + room.getTemperature().getValue());
        System.out.println("Ocupacao: " + room.getOccupation().getValue());
    }
//    public static void printContext(ContextExample context) throws Exception {
////        System.out.println("Temperatura " + context.room.getTemperature().getValue());
//        System.out.println("Temperatura " + context.getTemperature(context).getValue());
////        System.out.println("Ocupacao " + context.room.getOccupation().getValue());
//        System.out.println("Ocupacao " + context.getOccupation(context).getValue());
//    }

    public static void main(String[] args) throws Exception {

        ContextExample contextExample = new ContextExample();

        room = (Room) orion.retrieveEntity("urn:ngsi-ld:Room:001", new Room());
        initialTemperature = Double.parseDouble(room.getTemperature().getValue());

        airConditioner = (AirConditioner) orion.retrieveEntity("urn:ngsi-ld:AC:001", new AirConditioner());

        ServerSocket ss = new ServerSocket(40041, 1, InetAddress.getByName("172.18.1.1"));
        Subscriptor subscriptor = new Subscriptor(40041, "172.18.1.1", ".*", "AirConditioner", ss, new Motion());

        subscriptor.subscribeAndListen(en -> updateEntity((Motion) en), new Motion());

//        while(true) {
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
    }

    public static Motion updateEntity(Motion motion) {
        if (airConditioner.getState().getValue().equals("on")){
            manageContextOnFramework(motion, airConditioner, room, orion);
        }else {
            manageContextOffFramework(airConditioner, room, initialTemperature, orion);
        }
        printContext(room);
        return motion;
    }

}
