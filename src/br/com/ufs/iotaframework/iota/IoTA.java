package br.com.ufs.iotaframework.iota;

import br.com.ufs.iotaframework.devices.Device;
import br.com.ufs.iotaframework.devices.DeviceList;
import br.com.ufs.iotaframework.services.Service;
import br.com.ufs.iotaframework.services.ServiceGroup;
import br.com.ufs.orionframework.httprequests.HttpRequests;
import com.google.gson.Gson;

import java.util.logging.Level;
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
    private Boolean debugMode;
    private static final String SERVICES_ENDPOINT = "/iot/services/";
    private static final String DEVICES_ENDPOINT = "/iot/devices/";
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
        this.debugMode = false;
    }

    public IoTA() {
        this.ip = "localhost";
        this.port = 4041;
        this.url = "http://" + this.ip + ":" + this.port;
        this.debugMode = false;
    }

    public Boolean getDebugMode() {
        return debugMode;
    }

    public void setDebugMode(Boolean debugMode) {
        this.debugMode = debugMode;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
        this.url = "http://" + this.ip + ":" + this.port;
    }

    public int getPort() {
        return this.port;
    }
    public void setPort(int port) {
        this.port = port;
        this.url = "http://" + this.ip + ":" + this.port;
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
          LOGGER.warning("A error may be occurred and the service group was not created, please please check your parameters or set debugMode to true to more details");
          showStackTrace(e);
        }

        shoWDebug(json);
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
            LOGGER.warning("A error may be occurred on retrieve the services, please please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }
        shoWDebug(json);
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
            LOGGER.warning("A error may be occurred on retrieve the services, please please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }
        shoWDebug(json);
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
            LOGGER.warning("A error may be occurred on update the service, please please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        shoWDebug(json);
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
            LOGGER.warning("A error may be occurred on update the service, please please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
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
            LOGGER.warning("A error may be occurred on device creation, please please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }
        shoWDebug(json);
    }


    /**
     * retrieve devices previously created.
     *
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @return a device list.
     * @see DeviceList
     */
    public DeviceList retrieveAllDevices() {
        String json = "";
        Gson gson = new Gson();

        HttpRequests http = new HttpRequests();
        try {
            json = http.runGetRequest(this.url + DEVICES_ENDPOINT);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred on retrieving the devices, please please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }
        shoWDebug(json);
        return gson.fromJson(json, DeviceList.class);
    }

    /**
     * Given a device id, returns a device
     *
     * @param device_id  An id from device.
     * @return a Device object.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @see DeviceList
     */
    public Device retrieveDevice(String device_id) {

        String json="";

        Gson gson = new Gson();
        HttpRequests http = new HttpRequests();

        try {
            json = http.runGetRequest(this.url + DEVICES_ENDPOINT + device_id);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred on retrieving the device, please please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        shoWDebug(json);
        return gson.fromJson(json, Device.class);
    }


    /**
     * Given a device id, update a device.
     *  If you want modify only a field, you can do it,
     *  except field protocol (this field, if provided it is removed from request).
     *
     * @param device_id  An id from device.
     * @param device an object representing the update.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @see DeviceList
     */
    public void updateDevice(String device_id, Device device){

        String json;

        Gson gson = new Gson();
        json = gson.toJson(device);

        HttpRequests http = new HttpRequests();

        try {
            http.runPutRequest(this.url +  DEVICES_ENDPOINT + device_id, json);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred on updating the devices, please please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

        shoWDebug(json);
    }

    /**
     * Given a device id, delete a device. If specific device is not found, we work as deleted.
     *
     * @param device_id  An id from device.
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @see DeviceList
     */
    public void removeDevice(String device_id){
        HttpRequests http = new HttpRequests();
        try {
            http.runDeleteRequest(this.url + DEVICES_ENDPOINT + device_id);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred on deleting the devices, please please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }
    }
    /**
     * This operation send devices measures.
     *
     * @param apikey An apikey used for validate the measure was sent.
     * @param device_id  An id from device.
     * @param payload a text payload using http ultralight sintax.
     * @param url a url (ip + port)
     * @throws Exception for http requests (bad request, forbidden, etc.)
     * @see DeviceList
     */
    public void sendMeasure(String url, String resource, String apikey, String device_id, String payload) {
        url = "http://" + url + resource + "?" + "k="+apikey + "&i=" + device_id;

        HttpRequests http = new HttpRequests();
        try {
            http.runUltralightPostRequest(url, payload);
        } catch (Exception e) {
            LOGGER.warning("A error may be occurred on sending measures, please please check your parameters or set debugMode to true to more details");
            showStackTrace(e);
        }

    }
    public void objectToPayloadUltralight() {

    }

    /**
     * This operation show on console a JSON file to debug purposes.
     * To use this feature, you only need to set the debugMode attribute to true on orion.
     * @param json a JSON to be showed on screen, to debug purposes.
     */
    public void shoWDebug(String json) {
        if(this.debugMode)
            LOGGER.info(json);
    }

    /**
     * This operation show on console the stack trace.
     * To use this feature, you only need to set the debugMode attribute to true on orion.
     * @param e a exception to show your stack trace on console.
     *
     */
    public void showStackTrace (Exception e){
        if(this.debugMode){
            LOGGER.log(Level.INFO, e.getMessage(), e);
        }

    }
}
