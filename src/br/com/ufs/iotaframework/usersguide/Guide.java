package br.com.ufs.iotaframework.usersguide;

import br.com.ufs.builtindevices.actuator.bell.Bell;
import br.com.ufs.iotaframework.devices.*;
import br.com.ufs.iotaframework.iota.IoTA;
import br.com.ufs.iotaframework.services.Service;
import br.com.ufs.iotaframework.services.ServiceGroup;
import br.com.ufs.orionframework.orion.Orion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Guide {
    public static void main(String[] args) throws Exception {

        // creating a IoT-A and submit a service group.

        IoTA iota = new IoTA("localhost",4041);
        List<Service> serviceList = new ArrayList<>();

        Service service = new Service("4jggokgpepnvsb2uv4s40d59ov","testToken", "http//:orion:1026","Thing", "/iot/d");
        serviceList.add(service);

        ServiceGroup serviceGroup = new ServiceGroup(serviceList);
        iota.createService(serviceGroup);

        // retrieving a service list with filters or without filters.
        ServiceGroup sg = iota.retrieveService(1,0,"");
        sg = iota.retrieveAllServices();

        // updating a service
        Service s = new Service("4jggokgpepnvsb2uv4s40d59ov","testToken", "http//:orion:1026","Test", "/iot/d");
        serviceList.add(s);
        sg.setServices(serviceList);
        iota.updateService("4jggokgpepnvsb2uv4s40d59ov", "/iot/d", sg.getServices().get(1));
        sg = iota.retrieveAllServices();

        // removing a service
        iota.removeService("4jggokgpepnvsb2uv4s40d59ov", "/iot/d");

        // creating a device
        List<Attribute> attributeList = new ArrayList<>();
        Attribute attr1 = new Attribute("a","attr1","int");
        Attribute state = new Attribute("s","state","Text");
        attributeList.add(attr1);
        attributeList.add(state);

        List<StaticAttribute> staticAttributeList = new ArrayList<>();
        StaticAttribute staticAttribute = new StaticAttribute("refStore", "Relationship", "urn:ngsi-ld:Store:002");
        staticAttributeList.add(staticAttribute);

        List<Command> commandList = new ArrayList<>();
        Command command = new Command("ring");
        commandList.add(command);

        List<Device> deviceList = new ArrayList<>();
        Device device =  new Device("bell001", "urn:ngsi-ld:Bell:001",
                "Bell", "PDI-IoTA-UltraLight", "HTTP", "http://iot-sensors:3001/iot/bell001", attributeList, staticAttributeList, commandList
        );
        deviceList.add(device);
        DeviceList devices = new DeviceList(deviceList);

        iota.createDevice(devices);

        Orion orion = new Orion();

        String[] attrs = {"ring"};

        orion.createRegistration("urn:ngsi-ld:Bell:001", "Bell", Arrays.asList(attrs), "http://orion:1026/v1");

        orion.setDebugMode(true);
        iota.setDebugMode(true);
        TestComand testComand = new TestComand(new OnCommand(""));
        orion.updateExistingEntityAttributes("urn:ngsi-ld:Bell:001", testComand);
        // retrieving the devices
        DeviceList dl = iota.retrieveAllDevices();

        Device d = iota.retrieveDevice("bell001");
        iota.setDebugMode(false);

        Bell b = (Bell) orion.retrieveEntity("urn:ngsi-ld:Bell:001", new Bell());

        d.setEntity_type("Bell");
        iota.updateDevice("bell001", d);

        d = iota.retrieveDevice("bell001");

//        iota.sendMeasure();

        // removing a device is not working
//        iota.removeDevice("device_id");

    }
}
