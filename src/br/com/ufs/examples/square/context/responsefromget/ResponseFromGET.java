package com.java.responsefromget;

public class ResponseFromGET {
/*
 * [{"id":"SmartLamp02","type":"Lamp","TimeInstant":"2019-02-12T16:13:07.00Z","location":"-10.891191,-37.214734","luminosity":"2","off_info":" ","off_status":"UNKNOWN",
				"on_info":" ","on_status":"UNKNOWN","state":"off","on":"","off":""},
{"id":"SmartLamp01","type":"Lamp","on":"","off":""},
{"id":"SmartLamp03","type":"Lamp","on":"","off":""}]
 */
	private String id;
	private String type;
	private String TimeInstant;
	private String location;
	private String luminosity;
	private String off_info;
	private String off_status;
	private String on_info;
	private String on_status;
	private String state;
	private String on;
	private String off;
	private int count;
	private int number;
	
	public ResponseFromGET() {
//		this.id = "";
//		this.type = "";
//		this.TimeInstant = "";
//		this.location = "";
//		this.luminosity = "";
//		this.off_info = "";
//		this.off_status = "";
//		this.on_info = "";
//		this.on_status = "";
//		this.state = "";
//		this.on = "";
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTimeinstant() {
		return TimeInstant;
	}
	public void setTimeinstant(String timeinstant) {
		this.TimeInstant = timeinstant;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLuminosity() {
		return luminosity;
	}
	public void setLuminosity(String luminosity) {
		this.luminosity = luminosity;
	}
	public String getOff_info() {
		return off_info;
	}
	public void setOff_info(String off_info) {
		this.off_info = off_info;
	}
	public String getOff_status() {
		return off_status;
	}
	public void setOff_status(String off_status) {
		this.off_status = off_status;
	}
	public String getOn_info() {
		return on_info;
	}
	public void setOn_info(String on_info) {
		this.on_info = on_info;
	}
	public String getOn_status() {
		return on_status;
	}
	public void setOn_status(String on_status) {
		this.on_status = on_status;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOn() {
		return on;
	}
	public void setOn(String on) {
		this.on = on;
	}
	public String getOff() {
		return off;
	}
	public void setOff(String off) {
		this.off = off;
	}
	public String getTimeInstant() {
		return TimeInstant;
	}
	public void setTimeInstant(String timeInstant) {
		TimeInstant = timeInstant;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
