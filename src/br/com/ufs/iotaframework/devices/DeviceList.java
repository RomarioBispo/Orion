package br.com.ufs.iotaframework.devices;

import java.util.List;

/**
 * This class help to represent a List of Devices in JSON format as java Object.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see Device
 */
public class DeviceList {
    private List<Device> devices;

    public DeviceList(List<Device> devices) {
        this.devices = devices;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
