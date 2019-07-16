package br.com.ufs.iotaframework.iota;

import br.com.ufs.iotaframework.devices.DeviceList;
import br.com.ufs.iotaframework.services.Service;
import br.com.ufs.iotaframework.services.ServiceGroup;
import br.com.ufs.orionframework.httprequests.HttpRequests;
import com.google.gson.Gson;

import java.util.logging.Logger;

/**
 * IoTA covers IoT-Agent operations provisioning.
 * Your objective is to let a little bit easier to developer do FIWARE applications using java.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */

public class IoTA {
    private String ip;
    private int port;
    private String url;
    private static final String SERVICES_ENDPOINT = "/iot/services";
    private static final String DEVICES_ENDPOINT = "/iot/devices";
    private static final Logger LOGGER = Logger.getLogger(IoTA.class.getName());

    /**
     * Creates a IoTA object.
     *
     * @param ip An IP address from where the IoTA is running.
     * @param port An port from where the IoTA is running.
     */
    public IoTA(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.url = "http://" + this.ip + ":" + this.port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Create a service group
     *
     * @param  serviceGroup  An given object representing the service group. The object follows
     * the JSON entity representation format.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @see ServiceGroup
     */
    public void createService(ServiceGroup serviceGroup) throws Exception {

        String json;

        Gson gson = new Gson();

        json = gson.toJson(serviceGroup);

        HttpRequests http = new HttpRequests();

        try {
            http.runPostRequest(this.url + SERVICES_ENDPOINT, json);
        }
        catch(Exception e) {
          LOGGER.warning("A error may be occurred and the service group was not created, please check your parameters");
        }
    }

    /**
     * Retrieve a subservice or all subservices.
     *
     * @param limit In order to specify the maximum number of services (default is 20, maximum allowed is 1000).
     * @param offset In order to skip a given number of elements at the beginning (default is 0).
     * @param resource URI for the iotagent, return only services for this iotagent.
     * @return a Service Group instance.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @see ServiceGroup
     */
    public ServiceGroup retrieveService(int limit, int offset, String resource) throws Exception {

        String json = "" ;
        Gson gson = new Gson();

        HttpRequests http = new HttpRequests();

        try {
            json = http.runGetRequest(this.url + SERVICES_ENDPOINT + "?" + "limit=" + limit + "&offset=" + offset + "&resource=" + resource);
        }
        catch (Exception e) {
            LOGGER.warning("A error may be occurred on retrieve the services, please check your parameters");
        }
        return (gson.fromJson(json, ServiceGroup.class));
    }


    /**
     * Retrieve all subservices.
     *
     * @return a Service Group instance.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @see ServiceGroup
     */
    public ServiceGroup retrieveAllServices() {
        String json = "" ;
        Gson gson = new Gson();

        HttpRequests http = new HttpRequests();

        try {
            json = http.runGetRequest(this.url + SERVICES_ENDPOINT);
        }
        catch (Exception e) {
            LOGGER.warning("A error may be occurred on retrieve the services, please check your parameters");
        }
        return (gson.fromJson(json, ServiceGroup.class));
    }


    /**
     * If you want modify only a field, you can do it.
     * You cannot modify an element into an array field, but whole array. ("/*" is not allowed).
     *
     * @param apikey If you don't specify, apikey=" " is applied.
     * @param resource URI for service into iotagent.
     * @param service a service object representing the update
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @see ServiceGroup
     */
    public void updateService(String apikey, String resource, Service service) {

        String json;

        Gson gson = new Gson();
        json = gson.toJson(service);

        HttpRequests http = new  HttpRequests();
        try {
            http.runPutRequest(this.url+ SERVICES_ENDPOINT + "?" + "resource=" + resource + "&" + "apikey=" + apikey, json);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred on update the service, please check your parameters");
        }

    }


    /**
     * You remove a subservice into a service.
     * If Fiware-ServicePath is '/*' or '/#' remove service and all subservices.
     *
     * @param apikey If you don't specify, apikey=" " is applied.
     * @param resource URI for service into iotagent.
     * Remove devices in service/subservice.
     * This parameter is not valid when Fiware-ServicePath is '/*' or '/#'.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @see ServiceGroup
     */
    public void removeService(String apikey, String resource) {

        HttpRequests http = new  HttpRequests();
        try {
            http.runDeleteRequest(this.url+ SERVICES_ENDPOINT + "?" + "resource=" + resource + "&" + "apikey=" + apikey);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred on update the service, please check your parameters");
        }
    }


    /**
     * Creates a device on IoTA.
     *
     * @param devices a device list objects.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @see DeviceList
     */
    public void createDevice(DeviceList devices) {
        String json;

        Gson gson = new Gson();
        json = gson.toJson(devices);

        HttpRequests http = new HttpRequests();
        try {
            http.runPostRequest(this.url + DEVICES_ENDPOINT, json);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred on device creation, please check your parameters");
        }
    }


    /**
     * retrieve devices previously created.
     *
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @see DeviceList
     */
    public void retrieveAllDevices() {
        String json;
        HttpRequests http = new HttpRequests();
        try {
            json = http.runGetRequest(this.url + DEVICES_ENDPOINT);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred on retrieving the devices, please check your parameters");
        }
    }

    /**
     * Given a device id, returns a device
     *
     * @param device_id  An id from device.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @see DeviceList
     */
    public void retrieveDevice(String device_id) {

    }


    /**
     * Given a device id, update a device.
     *  If you want modify only a field, you can do it,
     *  except field protocol (this field, if provided it is removed from request).
     *
     * @param device_id  An id from device.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @see DeviceList
     */
    public void updretrieveAllDevicesateDevice(String device_id){

    }

    /**
     * Given a device id, delete a device. If specific device is not found, we work as deleted.
     *
     * @param device_id  An id from device.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @see DeviceList
     */
    public void deleteDevice(String device_id){

    }




}
