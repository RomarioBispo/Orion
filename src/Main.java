import batchUpdate.BatchUpdate;
import entityLamp.Attrs;
import entityLamp.Lamp;
import orion.Orion;
import subscription.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        Orion orion = new Orion();

        //creating a entity
        Attrs name = new Attrs("Praça Oliveira Belo","Text");
        Attrs location = new Attrs("-10.936245,-37.061224","geo:point");
        Attrs radius = new Attrs("45","Float");
        Lamp square01 = new Lamp("urn:ngsi-ld:Square:1","Square",name,location,radius);
        //orion.createEntity(square01);

        //retrieving a entity from orion
        Lamp square02 = (Lamp) orion.retrieveEntity("urn:ngsi-ld:Square:1", square01);

        System.out.println("Before:"+ square02.getRadius().getValue());

        //setting value and sending to orion by batch your update
        square01.setRadius(new Attrs("50","Float"));
        List<Lamp> batchEntities = new ArrayList<Lamp>();
        batchEntities.add(square01);
        BatchUpdate batchUpdate = new BatchUpdate(batchEntities);
        orion.batchUpdate(batchUpdate);

        //orion.listEntities("");
        //retrieving updated entity and print the class update
        square02 = (Lamp) orion.retrieveEntity("urn:ngsi-ld:Square:1", square01);
        System.out.println("After: "+square02.getRadius().getValue());


        // creating a subscription

        Entities entities = new Entities(".*", "Square");
        List<Entities> entitiesList = new ArrayList<Entities>();
        entitiesList.add(entities);
        Subject subject = new Subject(entitiesList);

        List<String> conditions = new ArrayList<String>();
        conditions.add( "temperature");
        Condition condition = new Condition(conditions);

        List<String> attrs = new ArrayList<String>();
        attrs.add("temperature");
        attrs.add("humidity");

        Http http = new Http("http://172.18.1.1:40041");
        Notification notification = new Notification(http, attrs);

        Subscription sub = new Subscription("One subscription to rule them all",subject, condition,
                notification, "2020-04-05T14:00:00Z", 5);

        orion.createSubscriptions(sub);

        Subscription [] subscriptionslist = (Subscription[]) orion.listSubscriptions();







    }
}
