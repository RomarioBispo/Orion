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
import br.com.ufs.orionframework.subscription.Subscription;
import br.com.ufs.orionframework.subscriptor.Subscriptor;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

public class ContextExample {

    private static Orion orion = new Orion();
    private static IoTA iota = new IoTA();
    private static Room room = new Room();
    private static AirConditioner airConditioner = new AirConditioner();
    private static Device motion;
    private static double initialTemperature;

    private ContextExample() throws Exception {

        // creating entidade no orion.
        Attrs name = new Attrs("Auditorio","Text");
        Attrs maxCapacity = new Attrs("50", "Integer");
        Attrs temperature = new Attrs("35.0","Float");
        Attrs occupation = new Attrs("0", "Integer");

        Room room = new Room ("urn:ngsi-ld:Room:001", "Room", maxCapacity, name , occupation, temperature);
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

        deviceList.add(new Device("ac001","123456","urn:ngsi-ld:AC:001",
                "AirConditioner","America/Belem", attributeList,staticAttributeList));

        DeviceList devices = new DeviceList(deviceList);

        iota.createDevice(devices);

        //Create Device
        List<Attribute> attributeListMotion = new ArrayList<>();
        attributeListMotion.add(new Attribute("c", "count", "Integer"));

        List<Device> deviceListMotion = new ArrayList<>();
        motion = new Device("motion001", "123456", "urn:ngsi-ld:Motion:001", "Motion",
                "America/Belem", attributeListMotion, staticAttributeList);

        deviceListMotion.add(motion);

        DeviceList motionDevices = new DeviceList(deviceListMotion);

        iota.createDevice(motionDevices);

    }


    public static void manageContextOnFramework(Motion motion, Orion orion) {

        int newOccupation;
        double newTemperature;

        System.out.println("M: "+airConditioner.getMode().getValue() + " | S: " + airConditioner.getState().getValue());
        int motionDetected = Integer.parseInt(motion.getCount().getValue());

        if (motionDetected !=0 && airConditioner.getMode().getValue().equals("turbo")){

            System.out.println("Chegaram mais " + motionDetected + " pessoas");

            newOccupation = Integer.parseInt(room.getOccupation().getValue()) + motionDetected;
            newTemperature = Double.parseDouble(room.getTemperature().getValue()) + (double) motionDetected*0.025;

            orion.updateAttributeData(room.getId(), "occupation", new Attrs(String.valueOf(newOccupation), "Integer"));
            orion.updateAttributeData(room.getId(),"temperature", new Attrs(String.valueOf(newTemperature),"Float"));
        }
        else if (motionDetected!=0 && airConditioner.getMode().getValue().equals("normal")) {
            System.out.println("sairam mais " + motionDetected + " pessoas");
            newOccupation = Integer.parseInt(room.getOccupation().getValue()) - motionDetected;
            orion.updateAttributeData(room.getId(), "occupation", new Attrs (String.valueOf(newOccupation), "Integer"));
        }
        if (Double.parseDouble(room.getTemperature().getValue()) > Double.parseDouble(airConditioner.getTemperature().getValue())) {
            newTemperature = Double.parseDouble(room.getTemperature().getValue()) - 1;
            orion.updateAttributeData(room.getId(),"temperature", new Attrs (String.valueOf(newTemperature), "Float"));
        }else {
            newTemperature = Double.parseDouble(room.getTemperature().getValue()) + 1;
            orion.updateAttributeData(room.getId(), "temperature", new Attrs(String.valueOf(newTemperature), "Float"));
        }

    }

      public static void manageContextOffFramework(double initialTemperature, Orion orion) {

        System.out.println("M: "+airConditioner.getMode().getValue() + " | S: " + airConditioner.getState().getValue());

        Double roomTemperature = Double.parseDouble(room.getTemperature().getValue());
        double newTemperature;
        if (roomTemperature < initialTemperature) {
            newTemperature = Double.parseDouble(airConditioner.getTemperature().getValue()) + 1;
            orion.updateAttributeData(room.getId(), "temperature", new Attrs(String.valueOf(newTemperature), "Float"));
        }else {
            newTemperature = Double.parseDouble(airConditioner.getTemperature().getValue()) - 1;
            orion.updateAttributeData(room.getId(), "temperature", new Attrs(String.valueOf(newTemperature), "Float"));
        }
      }

    public static void printContext() {

        room = (Room) orion.retrieveEntity("urn:ngsi-ld:Room:001", new Room());
        System.out.println("T: " + room.getTemperature().getValue() + " | " + "Occ: " + room.getOccupation().getValue());

    }

    public static void main(String[] args) throws Exception {

        ContextExample contextExample = new ContextExample();

        ServerSocket ss = new ServerSocket(40041, 1, InetAddress.getByName("172.18.1.1"));

        TimeUnit.SECONDS.sleep(5);

        room = (Room) orion.retrieveEntity("urn:ngsi-ld:Room:001", new Room());

        initialTemperature = Double.parseDouble(room.getTemperature().getValue());

        airConditioner = (AirConditioner) orion.retrieveEntity("urn:ngsi-ld:AC:001", new AirConditioner());

        Subscriptor subscriptor = new Subscriptor(40041, "172.18.1.1", ss);
        subscriptor.subscribeAndListen(en -> updateEntity((Motion) en), new Motion(), motion);
    }

    public static Motion updateEntity(Motion motion) {

        room = (Room) orion.retrieveEntity("urn:ngsi-ld:Room:001", new Room());
        airConditioner = (AirConditioner) orion.retrieveEntity("urn:ngsi-ld:AC:001", new AirConditioner());

        if (airConditioner.getState().getValue().equals("on")) {
            manageContextOnFramework(motion, orion);
        }else {
            manageContextOffFramework(initialTemperature, orion);
        }
        printContext();
        return motion;
    }
}
