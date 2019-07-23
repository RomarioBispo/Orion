package br.com.ufs.orionframework.registrations;

/**
 * This class is used to help to represent a registration object as JSON attribute on registration class.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see Registrations
 */

public class Provider {
    private Http http;
    private String supportedForwardingMode;
    private Boolean legacyForwarding;

    public Provider(Http http, String supportedForwardingMode, Boolean legacyForwarding) {
        this.http = http;
        this.supportedForwardingMode = supportedForwardingMode;
        this.legacyForwarding = legacyForwarding;
    }

    public Boolean getLegacyForwarding() {
        return legacyForwarding;
    }

    public void setLegacyForwarding(Boolean legacyForwarding) {
        this.legacyForwarding = legacyForwarding;
    }

    public Http getHttp() {
        return this.http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    public String getSupportedForwardingMode() {
        return this.supportedForwardingMode;
    }

    public void setSupportedForwardingMode(String supportedForwardingMode) {
        this.supportedForwardingMode = supportedForwardingMode;
    }

}