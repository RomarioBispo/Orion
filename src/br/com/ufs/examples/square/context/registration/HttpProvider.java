package com.java.registration;

public class HttpProvider {
	private String url;

	public HttpProvider() {
		url = "http://orion:1026/v1";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
