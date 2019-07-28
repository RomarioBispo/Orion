package com.java.notificationresponse;

import br.com.ufs.orionframework.entity.Entity;

public class NotificationResponse extends Entity {
//{"subscriptionId":"5c62c795872e10d82d0d1bc4","data":[{"id":"SmartLamp02","type":"Lamp","state":{"type":"Text","value":"off",
//	"metadata":{"TimeInstant":{"type":"ISO8601","value":"2019-02-12T13:18:13.716Z"}}}}]}
	private String subscriptionId;
    private Data[] data;
    
    public NotificationResponse() {
    	
    }

	@Override
	public String getType() {
		return null;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public void setType(String s) {

	}

	@Override
	public void setId(String s) {

	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Data[] getData() {
		return data;
	}

	public void setData(Data[] data) {
		this.data = data;
	}

	public static class Data{
    	private String id;
    	private String type;
    	private State state;
    	private Location location;
    	private Number number;
    	private Count count;
    	private Hour hour;

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

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}

		public Number getNumber() {
			return number;
		}

		public void setNumber(Number number) {
			this.number = number;
		}

		public Count getCount() {
			return count;
		}

		public void setCount(Count count) {
			this.count = count;
		}

		public Hour getHour() {
			return hour;
		}

		public void setHour(Hour hour) {
			this.hour = hour;
		}
	}
    
    public static class State {
    	private String type;
        private String value;
        private Metadata metadata;
        
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public Metadata getMetadata() {
			return metadata;
		}
		public void setMetadata(Metadata metadata) {
			this.metadata = metadata;
		}
        
    }
    
    public static class Location{
    	private String type;
    	private String value;
    	private Metadata metadata;
    	
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public Metadata getMetadata() {
			return metadata;
		}
		public void setMetadata(Metadata metadata) {
			this.metadata = metadata;
		}
    }
    
    public static class Number{
    	private String type;
    	private String value;
    	private Metadata metadata;
    	
    	public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public Metadata getMetadata() {
			return metadata;
		}
		public void setMetadata(Metadata metadata) {
			this.metadata = metadata;
		}
    	
    	
    }
    
    public static class Count{
    	private String type;
    	private String value;
    	private Metadata metadata;
    	
    	public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public Metadata getMetadata() {
			return metadata;
		}
		public void setMetadata(Metadata metadata) {
			this.metadata = metadata;
		}
    }
    
    public static class Hour{
    	private String type;
    	private int value;
    	private Metadata metadata;
    	
    	public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public Metadata getMetadata() {
			return metadata;
		}
		public void setMetadata(Metadata metadata) {
			this.metadata = metadata;
		}
    }
    
    public static class Metadata{
    	private TimeInstant timeinstant;

		public TimeInstant getTimeinstant() {
			return timeinstant;
		}
		public void setTimeinstant(TimeInstant timeinstant) {
			this.timeinstant = timeinstant;
		}
    	
    }
    
    public static class TimeInstant{
    	private String type;
    	private String value;
    	
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
    	
    }
    
}
