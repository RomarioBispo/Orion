package br.com.ufs.orionframework.subscription;

/**
 * This class is used to help to represent a object java as JSON. In this case, used to represent a br.com.ufs.orionframework.subscription
 * on a NGSIv2 form.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */
public class Subscription {
    private String id;
    private String description;
    private Subject subject;
    private Condition condition;
    private Notification notification;
    private String expires;
    private int throttling;



    public Subscription() {

    }

    /**
     *
     * @param description A free text used by the client to describe the subscription.
     * @param subject An object that describes the subject of the subscription.
     * @param condition Condition to trigger notifications.
     * @param notification An object that describes the notification to send when the subscription is triggered.
     * @param expires Subscription expiration date in ISO8601 format.
     * @param throttling Minimal period of time in seconds which must elapse between two consecutive notifications.
     */
    public Subscription(String description, Subject subject, Condition condition, Notification notification, String expires, int throttling) {
        this.description = description;
        this.subject = subject;
        this.condition = condition;
        this.notification = notification;
        this.expires = expires;
        this.throttling = throttling;
    }

    public String getId() {
        return id;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public int getThrottling() {
        return throttling;
    }

    public void setThrottling(int throttling) {
        this.throttling = throttling;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
