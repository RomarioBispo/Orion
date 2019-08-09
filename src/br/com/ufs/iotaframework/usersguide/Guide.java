package br.com.ufs.iotaframework.usersguide;

import br.com.ufs.iotaframework.devices.*;
import br.com.ufs.iotaframework.iota.IoTA;
import br.com.ufs.iotaframework.services.Service;
import br.com.ufs.iotaframework.services.ServiceGroup;

import java.util.ArrayList;
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
        Attribute attribute = new Attribute("source_data","attr_name","int");
        attributeList.add(attribute);

        List<StaticAttribute> staticAttributeList = new ArrayList<>();
        StaticAttribute staticAttribute = new StaticAttribute("refStore", "Relationship", "urn:ngsi-ld:Store:002");
        staticAttributeList.add(staticAttribute);

        List<Command> commandList = new ArrayList<>();
        Command command = new Command("ring");
        commandList.add(command);

        List<Device> deviceList = new ArrayList<>();
        Device device =  new Device("device_id", "12345", "entity_name",
                "entity_type", "America/Santiago",attributeList, staticAttributeList, commandList
        );
        deviceList.add(device);
        DeviceList devices = new DeviceList(deviceList);

        iota.createDevice(devices);

        // retrieving the devices
        DeviceList dl = iota.retrieveAllDevices();

        Device d = iota.retrieveDevice("device_id");

        d.setEntity_type("entity_type01");
        iota.updateDevice("device_id", d);

        d = iota.retrieveDevice("device_id");

//        iota.sendMeasure();

        // removing a device is not working
//        iota.removeDevice("device_id");

    }
}
