import batchUpdate.BatchUpdate;
import entity.Entity;
import entityLamp.Attrs;
import entityLamp.Lamp;
import genericNotification.GenericNotification;
import myTesteEntity.MyEntity;
import orion.Orion;
import subscription.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws Exception {

        //First, you need to make a Orion instance
        Orion orion = new Orion();
        // You need to define a class .java which represents a entity, here we defined a class called Myentity with 5 attributes
        // Before instantiate a entity, we have to instantiate the entities attributes
        Attrs name = new Attrs("Praça Oliveira Belo","Text");
        Attrs location = new Attrs("-10.936245,-37.061224","geo:point");
        Attrs radius = new Attrs("45","Float");
        Attrs pressure = new Attrs("450","Float");
        Attrs temperature = new Attrs("22","Float");

        Entity square01 = new Entity("urn:ngsi-ld:Square:1","Square",name,location,radius);

        // After define the attributes, we can make a instance from entity
        MyEntity myEntity01 = new MyEntity("urn:ngsi-ld:Square:2","Square",name,location,radius,pressure,temperature);
        // And submit to orion using the method createEntity
//        orion.createEntity(square01);
//        orion.createEntity(myEntity01);

        //retrieving a entity from orion
        Entity square02 = (Entity) orion.retrieveEntity("urn:ngsi-ld:Square:1", square01);
        MyEntity myEntity02 = (MyEntity) orion.retrieveEntity("urn:ngsi-ld:Square:2", myEntity01);
        System.out.println("My entity attr4:"+ myEntity02.getAttribute4().getValue());

        //setting value and sending to orion by batch your update
        myEntity02.setAttribute4(new Attrs("55","Float"));
        List<Entity> batchEntities = new ArrayList<Entity>();
        batchEntities.add(myEntity02);
        BatchUpdate batchUpdate = new BatchUpdate(batchEntities);
        orion.batchUpdate(batchUpdate);

       // orion.removeSingleAttribute(myEntity02.getId(), "attribute5");

        myEntity02.setAttribute5((Attrs) orion.getAttributeData(myEntity02.getId(),"attribute5", myEntity02.getAttribute5()));
        System.out.println("Get attrs data:"+myEntity02.getAttribute4().getValue());
        System.out.println("Get attrs data Antes do update:"+myEntity02.getAttribute5().getValue());
        orion.updateAttributeData(myEntity02.getId(),"attribute5",new Attrs("200","Float"));
        myEntity02.setAttribute5((Attrs) orion.getAttributeData(myEntity02.getId(),"attribute5", myEntity02.getAttribute5()));
        System.out.println("Get attrs data depois do update:"+myEntity02.getAttribute5().getValue());
//        //removing a entity
//        orion.removeEntity("urn:ngsi-ld:Square:2");
//        myEntity02 = (MyEntity) orion.retrieveEntity("urn:ngsi-ld:Square:2", myEntity01);
//        System.out.println("My entity attr4:"+ myEntity02.getAttribute4().getValue());

//        //setting value and sending to orion by batch your update
//        square01.setAttribute3(new Attrs("50","Float"));
//        List<Entity> batchEntities = new ArrayList<Entity>();
//        batchEntities.add(square01);
//        BatchUpdate batchUpdate = new BatchUpdate(batchEntities);
//        orion.batchUpdate(batchUpdate);

        //orion.listEntities("");
        //retrieving updated entity and print the class update
        square02 = (Entity) orion.retrieveEntity("urn:ngsi-ld:Square:1", square01);
        System.out.println("After: "+square02.getAttribute3().getValue());


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

        GenericNotification<Entity> GNotify = new GenericNotification<Entity>(batchEntities, "");

        Future<String> f = myEntity01.subscribeAndListen(40041, "172.18.1.1");

        //setting value and sending to orion by batch your update
            myEntity01.setAttribute4(new Attrs("555", "Float"));
            batchEntities = new ArrayList<Entity>();
            batchEntities.add(myEntity01);
            batchUpdate = new BatchUpdate(batchEntities);
            orion.batchUpdate(batchUpdate);
            //setting value and sending to orion by batch your update
            myEntity01.setAttribute4(new Attrs("300", "Float"));
            batchEntities = new ArrayList<Entity>();
            batchEntities.add(myEntity01);
            batchUpdate = new BatchUpdate(batchEntities);
            orion.batchUpdate(batchUpdate);
            //setting value and sending to orion by batch your update
            myEntity01.setAttribute4(new Attrs("800", "Float"));
            batchEntities = new ArrayList<Entity>();
            batchEntities.add(myEntity01);
            batchUpdate = new BatchUpdate(batchEntities);
            orion.batchUpdate(batchUpdate);

            MyEntity myEntity03 = (MyEntity) myEntity01.getSubscriptionUpdate(f, myEntity01);

//            System.out.println(myEntity03.getAttribute4().getValue());

            MyEntity myEntity04 = (MyEntity) orion.retrieveEntityAttributes("urn:ngsi-ld:Square:2", myEntity02);
            System.out.println(myEntity04.getAttribute4().getValue());

            myEntity02.setAttribute4(new Attrs("800", "Integer"));
            orion.replaceAllEntitiesAttributes("urn:ngsi-ld:Square:2", myEntity02);

            myEntity04 = (MyEntity) orion.retrieveEntityAttributes("urn:ngsi-ld:Square:2", myEntity02);
            System.out.println(myEntity04.getAttribute4().getType());

            myEntity02.setAttribute4(new Attrs("800", "Float"));
            orion.updateOrAppendEntityAttributes("urn:ngsi-ld:Square:2", myEntity02);
            myEntity04 = (MyEntity) orion.retrieveEntityAttributes("urn:ngsi-ld:Square:2", myEntity02);
            System.out.println(myEntity04.getAttribute4().getType());

            //aparentemente o método patch não tá funcionando, tenho que ver isso.
            //ao substituir o patch request por um post parece que funcionou, vou investigar depois
            myEntity02.setAttribute3(new Attrs("800", "Integer"));
            orion.updateExistingEntityAttributes("urn:ngsi-ld:Square:2", myEntity02);
            myEntity04 = (MyEntity) orion.retrieveEntityAttributes("urn:ngsi-ld:Square:2", myEntity02);
            System.out.println(myEntity04.getAttribute3().getType());

    }

}
