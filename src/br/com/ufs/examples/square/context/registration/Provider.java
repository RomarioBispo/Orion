package com.java.registration;

public class Provider {
	private boolean legacyForwarding;
	private HttpProvider http;
	
	public Provider(HttpProvider http) {
		legacyForwarding = true;
		this.http = http;
	}

	public boolean isLegacyForwarding() {
		return legacyForwarding;
	}

	public void setLegacyForwarding(boolean legacyForwarding) {
		this.legacyForwarding = legacyForwarding;
	}

	public HttpProvider getHttpProvider() {
		return http;
	}

	public void setHttpProvider(HttpProvider http) {
		this.http = http;
	}
	
	
}
