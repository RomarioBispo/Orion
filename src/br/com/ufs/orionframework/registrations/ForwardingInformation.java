package br.com.ufs.orionframework.registrations;

/**
 * This class is used to help to represent a registration object as JSON attribute on registration class.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see Registrations
 */

public class ForwardingInformation {
    private int timesSent;
    private String lastForwarding;
    private String lastSuccess;
    private String LastFailure;


    public ForwardingInformation() {
    }


    public ForwardingInformation(int timesSent, String lastForwarding, String lastSuccess, String LastFailure) {
        this.timesSent = timesSent;
        this.lastForwarding = lastForwarding;
        this.lastSuccess = lastSuccess;
        this.LastFailure = LastFailure;
    }


    public int getTimesSent() {
        return this.timesSent;
    }

    public void setTimesSent(int timesSent) {
        this.timesSent = timesSent;
    }

    public String getLastForwarding() {
        return this.lastForwarding;
    }

    public void setLastForwarding(String lastForwarding) {
        this.lastForwarding = lastForwarding;
    }

    public String getLastSuccess() {
        return this.lastSuccess;
    }

    public void setLastSuccess(String lastSuccess) {
        this.lastSuccess = lastSuccess;
    }

    public String getLastFailure() {
        return this.LastFailure;
    }

    public void setLastFailure(String LastFailure) {
        this.LastFailure = LastFailure;
    }

}