package br.com.ufs.orionframework.genericnotification;

import java.util.List;


/**
 * GenericNotification implements a generic notification
 * which represents a JSON format object in Orion sintax.
 * This class will help to deserialize publish-subscribe notifications object in server-side.
 *
 * @author Romario Bispo.
 */
public class GenericNotification<T> {

    private List<T> data;

    public GenericNotification() {
    }

    private String subscriptionId;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public GenericNotification(List<T> data, String subscriptionId) {
        this.data = data;
        this.subscriptionId = subscriptionId;
    }

}
