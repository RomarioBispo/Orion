package com.java.subscription;

import com.google.gson.annotations.SerializedName;

public class Subscription {
	
	/*String msg = "{" + 
			"  \"description\": \"A subscription to get info about Room1\",\n" + 
			"  \"subject\": {\n" + 
			"    \"entities\": [\n" + 
			"      {\n" + 
			"        \"id\": \"Room1\",\n" + 
			"        \"type\": \"Room\"\n" + 
			"      }\n" + 
			"    ],\n" + 
			"    \"condition\": {\n" + 
			"      \"attrs\": [\n" + 
			"        \"pressure\"\n" + 
			"      ]\n" + 
			"    }\n" + 
			"  },\n" + 
			"  \"notification\": {\n" + 
			"    \"http\": {\n" + 
			"      \"url\": \"http://172.18.1.1:"+ "PPPOOOOORRRTTTT" +"/\"\n" + 
			"    },\n" + 
			"    \"attrs\": [\n" + 
			"      \"temperature\"\n" + 
			"    ]\n" + 
			"  },\n" + 
			"  \"expires\": \"2040-01-01T14:00:00.00Z\",\n" + 
			"  \"throttling\": 5\n" + 
			"}";*/
	/*

	"entities":
			{"id":"Room1",
			"type":"Room"},
		
	"notification":
		{"http":"http://172.18.1.1:",
		
"expires":			



  
    "entities": [
      {
        "id": "Room1",
        "type": "Room"
      }
    ],
    "condition": {
      "attrs": [
        "pressure"
      ]
    }
  },
  "notification": {
    "http": {
      "url": "http://localhost:1028/accumulate"
    },
    "attrs": [
      "temperature"
    ]
  },
  "expires": "2040-01-01T14:00:00.00Z",
  "throttling": 5
}
	 */
	
	
	
	
	
	/*
	 * curl -iX POST \
  --url 'http://localhost:1026/v2/subscriptions' \
  --header 'content-type: application/json' \
  --data '{
  "description": "Notify me of all product price changes",
  "subject": {
    "entities": [{"idPattern": ".*", "type": "Product"}],            IDPATTERN!!!!
    "condition": {
      "attrs": [ "price" ]
    }
  },
  "notification": {
    "http": {
      "url": "http://tutorial:3000/subscription/price-change"
    }
  }
}'
	 */
	
	
	/*
	 * curl -iX POST \
  --url 'http://localhost:1026/v2/subscriptions' \
  --header 'Content-Type: application/json' \
  --data '{
  "description": "Notify me of low stock in Store 001",
  "subject": {
    "entities": [{"idPattern": ".*","type": "InventoryItem"}],
    "condition": {
      "attrs": ["shelfCount"],
      "expression": {"q": "shelfCount<10;refStore==urn:ngsi-ld:Store:001"}        EXPRESSION!!!!
    }
  },
  "notification": {
    "http": {
      "url": "http://tutorial:3000/subscription/low-stock-store001"
    },
    "attrsFormat" : "keyValues"
  }
}'
	 */
	
	
	private String description;  //optional (max length 1024)
	private Subject subject;
	private Notification notification;
	private String expires; //optional (must be a date or empty string "")
	private int throttling; //Minimal period of time in seconds which must elapse between two consecutive notifications. It is optional (must be an integer)
	
	/*public Subscription(String description, Entities[] entities, Condition condition, 
			Notification notification, String expires, int throttling) {
		this.description = description;
		this.subject = new Subject(entities, condition);
		//this.subject = subject;
		this.notification = notification;
		this.expires = expires;
		this.throttling = throttling;
	}*/
	
	/*public Subscription(String description, Entities[] entities, Condition condition, 
			Notification notification, int throttling) {
		this.description = description;
		this.subject = new Subject(entities, condition);
		//this.subject = subject;
		this.notification = notification;
		this.throttling = throttling;
	}*/
	
	public Subscription(String description, EntitiesIdPattern[] entitiesIdPattern, Condition condition, 
			Notification notification, int throttling) {
		this.description = description;
		this.subject = new Subject(entitiesIdPattern, condition);
		//this.subject = subject;
		this.notification = notification;
		this.throttling = throttling;
	}
	
	public static class Subject{
		
		//private Entities[] entities;
		private EntitiesIdPattern[] entities;
		private Condition condition;
		
		/*public Subject(Entities[] entities, Condition condition) {
			this.entities = entities;
			this.condition = condition;
		}*/
		
		public Subject(EntitiesIdPattern[] entities, Condition condition) {
			this.entities = entities;
			this.condition = condition;
		}

		/*public Entities[] getEntities() {
			return entities;
		}

		public void setEntities(Entities[] entities) {
			this.entities = entities;
		}*/
		
		public EntitiesIdPattern[] getEntitiesIdPattern() {
			return entities;
		}

		public void setEntitiesIdPattern(EntitiesIdPattern[] entities) {
			this.entities = entities;
		}

		public Condition getCondition() {
			return condition;
		}

		public void setCondition(Condition condition) {
			this.condition = condition;
		}
			
	}
	
	public static class Entities {
		
		private String id;  //id or idPattern: one of them is mandatory (but both at the same time is not allowed). 
							//id must follow NGSIv2 restrictions for IDs. idPattern must be not empty and a valid regex.
		private String type; //type or typePattern: optional (but both at the same time is not allowed). type must 
							 //follow NGSIv2 restrictions for IDs. type must not be empty. typePattern must be a valid regex, and non-empty.

		public Entities(String id, String type) {
			this.id = id;
			this.type = type;
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
		
	}
	
	public static class EntitiesIdPattern {
		
		private String idPattern;  //id or idPattern: one of them is mandatory (but both at the same time is not allowed). 
							//id must follow NGSIv2 restrictions for IDs. idPattern must be not empty and a valid regex.
		private String type; //type or typePattern: optional (but both at the same time is not allowed). type must 
							 //follow NGSIv2 restrictions for IDs. type must not be empty. typePattern must be a valid regex, and non-empty.

		public EntitiesIdPattern(String idPattern, String type) {
			this.idPattern = idPattern;
			this.type = type;
		}
		
		public String getIdPattern() {
			return idPattern;
		}

		public void setIdPattern(String idPattern) {
			this.idPattern = idPattern;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		
	}
	
	public static class Condition{
		private String[] attrs;
		private Expression expression;
		
		/*
		 * condition: optional (but if present it must have a content, i.e. {} is not allowed)

				attrs: optional (but if present it must be a list; empty list is allowed)
				expression: optional (but if present it must have a content, i.e. {} is not allowed)
    					q: optional (but if present it must be not empty, i.e. "" is not allowed)
    					mq: optional (but if present it must be not empty, i.e. "" is not allowed)
    					georel: optional (but if present it must be not empty, i.e. "" is not allowed)
    					geometry: optional (but if present it must be not empty, i.e. "" is not allowed)
    					coords: optional (but if present it must be not empty, i.e. "" is not allowed)
    					
    					"condition\": {
      \"attrs\": [ \"temperature\" ],
      \"expression\": {
        \"q\": \"temperature>40\"
      }

		 */
		
		public Condition(String[] attrs) {
			this.attrs = attrs;
		}

		public Condition(String[] attrs, Expression expression) {
			this.attrs = attrs;
			this.expression = expression;
		}

		public String[] getAttrs() {
			return attrs;
		}

		public void setAttrs(String[] attrs) {
			this.attrs = attrs;
		}

		public Expression getExpression() {
			return expression;
		}

		public void setExpression(Expression expression) {
			this.expression = expression;
		}
		
	}
	
	public static class Expression {
		@SerializedName("q")
		private String q;

		public Expression(String q) {
			super();
			this.q = q;
		}
		public String getQ() {
			return q;
		}
		public void setQ(String q) {
			this.q = q;
		}
	}
	
	public static class Notification{
		private Http http;
		/*
		 * http: must be present if httpCustom is omitted, forbidden otherwise
					url: mandatory (must be a valid URL)
		 */
		private String[] attrs;
		
		/*
		 * 
    http: must be present if httpCustom is omitted, forbidden otherwise
        url: mandatory (must be a valid URL)
    httpCustom: must be present if http is omitted, forbidden otherwise
        url: mandatory (must be not empty)
        headers: optional (but if present it must have a content, i.e. {} is not allowed)
        qs: optional (but if present it must have a content, i.e. {} is not allowed)
        method: optional (but if present it must be a valid HTTP method)
        payload: optional (empty string is allowed)
    attrs: optional (but if present it must be a list; empty list is allowed)
    metadata: optional (but if present it must be a list; empty list is allowed)
    exceptAttrs: optional (but it cannot be present if attrs is also used; if present it must be a non-empty list)
    attrsFormat: optional (but if present it must be a valid attrs format keyword)

		 */
		
		public Notification(Http http, String[] attrs) {
			this.http = http;
			this.attrs = attrs;
		}
		
		public Http getHttp() {
			return http;
		}
		public void setHttp(Http http) {
			this.http = http;
		}
		public String[] getAttrs() {
			return attrs;
		}
		public void setAttrs(String[] attrs) {
			this.attrs = attrs;
		}
		
	}
	
	public static class Http{
		private String url;
		
		public Http(String url) {
			this.url = url;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

}