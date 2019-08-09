package br.com.ufs.examples.room.context.context;

import br.com.ufs.builtindevices.hybrid.airconditioner.AirConditioner;
import br.com.ufs.builtindevices.sensor.motion.Motion;
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

    public static void manageContextOnFramework(Motion motion) {

        int newOccupation;
        double newTemperature;

        System.out.println("M: "+airConditioner.getMode() + " | S: " + airConditioner.getState());
        int motionDetected = motion.getCount();

        if (motionDetected !=0 && airConditioner.getMode().equals("turbo")){

            System.out.println("Chegaram mais " + motionDetected + " pessoas");

            newOccupation = room.getOccupation() + motionDetected;
            newTemperature = room.getTemperature() + (double) motionDetected*0.025;

            room.setOccupation(newOccupation);
            room.setTemperature(newTemperature);
        }
        else if (motionDetected!=0 && airConditioner.getMode().equals("normal")) {
            System.out.println("sairam mais " + motionDetected + " pessoas");
            newOccupation = room.getOccupation() - motionDetected;
            room.setOccupation(newOccupation);

        }
        if (room.getTemperature() > airConditioner.getTemperature()){
            newTemperature = room.getTemperature() - 1;
            room.setTemperature(newTemperature);
        }else {
            newTemperature = room.getTemperature() + 1;
            room.setTemperature(newTemperature);
        }

    }

      public static void manageContextOffFramework(double initialTemperature) {

        System.out.println("M: "+airConditioner.getMode() + " | S: " + airConditioner.getState());

        Double roomTemperature = room.getTemperature();
        Double newTemperature;
        if (roomTemperature < initialTemperature) {
            newTemperature = airConditioner.getTemperature() + 1;
            room.setTemperature(newTemperature);
        }else {
            newTemperature = airConditioner.getTemperature() - 1;
            room.setTemperature(newTemperature);
        }
      }

    public static void printContext() {

        room = (Room) orion.retrieveEntity("urn:ngsi-ld:Room:001", new Room());
        System.out.println("T: " + room.getTemperature() + " | " + "Occ: " + room.getOccupation());

    }

    public static void main(String[] args) throws Exception {

        ContextExample contextExample = new ContextExample();

        ServerSocket ss = new ServerSocket(40041, 1, InetAddress.getByName("172.18.1.1"));

        TimeUnit.SECONDS.sleep(5);

        room = (Room) orion.retrieveEntity("urn:ngsi-ld:Room:001", new Room());

        initialTemperature = room.getTemperature();

        airConditioner = (AirConditioner) orion.retrieveEntity("urn:ngsi-ld:AC:001", new AirConditioner());

        Subscriptor subscriptor = new Subscriptor(40041, "172.18.1.1", ss);
        subscriptor.subscribeAndListen(en -> updateEntity((Motion) en), new Motion(), motion);
    }

    public static Motion updateEntity(Motion motion) {

        room = (Room) orion.retrieveEntity("urn:ngsi-ld:Room:001", new Room());
        room.setOrion(orion);

        airConditioner = (AirConditioner) orion.retrieveEntity("urn:ngsi-ld:AC:001", new AirConditioner());
        airConditioner.setOrion(orion);

        if (airConditioner.getState().equals("on")) {
            manageContextOnFramework(motion);
        }else {
            manageContextOffFramework(initialTemperature);
        }
        printContext();
        return motion;
    }
}
