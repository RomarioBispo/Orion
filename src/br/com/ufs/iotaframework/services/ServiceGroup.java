package br.com.ufs.iotaframework.services;

import java.util.List;

/**
 * This class help to represent a Service group in JSON format as java Object.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see Service
 */
public class ServiceGroup {
        List<Service> services;

    /**
     * Creates a list from services objects, called service group.
     *
     */
    public ServiceGroup(List<Service> services) {

            this.services = services;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

}
