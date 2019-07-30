package br.com.ufs.orionframework.usersguide;

import br.com.ufs.orionframework.batchupdate.BatchUpdate;
import br.com.ufs.orionframework.entity.Entity;
import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.genericnotification.GenericNotification;
import br.com.ufs.orionframework.mytesteentity.MyEntity;
import br.com.ufs.orionframework.orion.Orion;
import br.com.ufs.orionframework.registrations.Registrations;
import br.com.ufs.orionframework.subscription.*;
import br.com.ufs.orionframework.subscriptor.Subscriptor;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        //First, you need to make a Orion instance
        Orion orion = new Orion();

        /**
         * You need to define a class .java which represents a entity, here we defined a class called Myentity with 5 attributes.
         * Before instantiate a entity, we have to instantiate the entities attributes
         * */
        Attrs name = new Attrs("Praca Oliveira Belo", "Text");
        Attrs location = new Attrs("-10.936245,-37.061224", "geo:point");
        Attrs radius = new Attrs("45", "Float");
        Attrs pressure = new Attrs("450", "Float");
        Attrs temperature = new Attrs("22", "Float");

        // After define the attributes, we can make a instance from entity
        MyEntity myEntity01 = new MyEntity("urn:ngsi-ld:Square:1", "Square", name, location, radius, pressure, temperature);
        MyEntity myEntity02 = new MyEntity("urn:ngsi-ld:Square:2", "Square", name, location, radius, pressure, temperature);

        // And submit to orion using the method createEntity
        orion.createEntity(myEntity01);
        orion.createEntity(myEntity02);

        //retrieving a entity from orion
        MyEntity myEntity03 = (MyEntity) orion.retrieveEntity("urn:ngsi-ld:Square:2", myEntity01);
        System.out.println("My entity attr4:" + myEntity03.getAttribute4().getValue());

        //setting value attribute and sending to orion by batch update
        myEntity02.setAttribute4(new Attrs("55", "Float"));
        List<Entity> batchEntities = new ArrayList<>();

        batchEntities.add(myEntity02);
        BatchUpdate batchUpdate = new BatchUpdate(batchEntities);
        orion.batchUpdate(batchUpdate);

        // removing a attribute from orion.
        // give the entity id and the name from attribute, in this case the attribute name is "attribute5"
        orion.removeSingleAttribute(myEntity02.getId(), "attribute5");

        // setting a atribute and sending to orion the update
//        myEntity02.setAttribute5((Attrs) orion.getAttributeData(myEntity02.getId(),"attribute5", myEntity02.getAttribute5()));
//        System.out.println("Get attrs data:"+myEntity02.getAttribute4().getValue());
//        System.out.println("Get attrs data Antes do update:"+myEntity02.getAttribute5().getValue());
//        orion.updateAttributeData(myEntity02.getId(),"attribute5",new Attrs("200","Float"));
//        myEntity02.setAttribute5((Attrs) orion.getAttributeData(myEntity02.getId(),"attribute5", myEntity02.getAttribute5()));
//        System.out.println("Get attrs data depois do update:"+myEntity02.getAttribute5().getValue());

        //removing a entity
//        orion.removeEntity("urn:ngsi-ld:Square:2");
//        myEntity02 = (MyEntity) orion.retrieveEntity("urn:ngsi-ld:Square:2", myEntity01);
//        System.out.println("My entity attr4:"+ myEntity02.getAttribute4().getValue());

//        //setting value and sending to orion by batch your update
//        myEntity01.setAttribute3(new Attrs("50", "Float"));
//        batchEntities.add(myEntity01);
//        BatchUpdate batchupdate = new BatchUpdate(batchEntities);
//        orion.batchUpdate(batchupdate);

//        orion.listEntities("");
//        retrieving updated entity and print the class update
//        myEntity03 = (MyEntity) orion.retrieveEntity("urn:ngsi-ld:Square:1", myEntity03);
//        System.out.println(myEntity03.getAttribute5().getValue());

        // creating a subscription
        // this line means you want to all entities from the square type using the regex ".*" to entityid
        Entities entities = new Entities(".*", "Square");
        List<Entities> entitiesList = new ArrayList<>();
        entitiesList.add(entities);
        Subject subject = new Subject(entitiesList);

        // defining the triggers from notification, here we define a temperature (Changes) trigger.
        List<String> conditions = new ArrayList<>();
        conditions.add("temperature");
        Condition condition = new Condition(conditions);

        // defining the notification payload, here the notification payload bring the temperature and humidity attributes
        List<String> attrs = new ArrayList<>();
        attrs.add("temperature");
        attrs.add("humidity");

        // defining the url from the server which receives the notifications
        Http http = new Http("http://172.18.1.1:40041");
        Notification notification = new Notification(http, attrs);

        Subscription sub = new Subscription("One subscription to rule them all", subject, condition,
                notification, "2020-04-05T14:00:00Z", 5);

        // sending to orion
        orion.createSubscriptions(sub);

        Subscription[] subscriptionslist = (Subscription[]) orion.listSubscriptions();

        GenericNotification<Entity> GNotify = new GenericNotification<>(batchEntities, "");

        // instead of create a subscription, you can use the createSimpleSubscription method
        // here was created a subscription which triggers any attribute for the entity and brings the payload with all attributes from entity
        // additionally, the subscription don't have expiration date if false value is passed as parameter, otherwise expires at 2040.
        orion.createSimpleSubscription("urn:ngsi-ld:Square:1", "Square", 40041, "172.18.1.1", false);

        //setting value and sending to orion by batch your update
        myEntity01.setAttribute4(new Attrs("555", "Float"));
        batchEntities = new ArrayList<>();
        batchEntities.add(myEntity01);
        batchUpdate = new BatchUpdate(batchEntities);
        orion.batchUpdate(batchUpdate);

        //setting value and sending to orion by batch your update
        myEntity01.setAttribute4(new Attrs("300", "Float"));
        batchEntities = new ArrayList<>();
        batchEntities.add(myEntity01);
        batchUpdate = new BatchUpdate(batchEntities);
        orion.batchUpdate(batchUpdate);

        //setting value and sending to orion by batch your update
        myEntity01.setAttribute4(new Attrs("800", "Float"));
        batchEntities = new ArrayList<>();
        batchEntities.add(myEntity01);
        batchUpdate = new BatchUpdate(batchEntities);
        orion.batchUpdate(batchUpdate);

        // creating a subscriptor, this method subscribeAndListen listen send the notifications to ip + port from the especified entity
        ServerSocket ss = new ServerSocket(40041, 1, InetAddress.getByName("172.18.1.1"));

        Subscriptor subscriptor1 = new Subscriptor(40041, "172.18.1.1", "urn:ngsi-ld:Square:1", "Square", ss, myEntity01);
        subscriptor1.subscribeAndListen(en -> updateEntity((MyEntity) en), new MyEntity());
//        System.out.println(subscriptor1.getModel().getId());
        Subscriptor subscriptor2 = new Subscriptor(40041, "172.18.1.1", "urn:ngsi-ld:Square:2", "Square", ss, myEntity02);
        subscriptor2.subscribeAndListen(en -> updateEntity((MyEntity) en), new MyEntity());

//        subscriptor1.subscribeAndListenBlocking(en -> updateEntity((MyEntity) en), myEntity01);

        // retrieving the entity from Orion.
        MyEntity myEntity04 = (MyEntity) orion.retrieveEntityAttributes("urn:ngsi-ld:Square:2", myEntity02);
        System.out.println(myEntity04.getAttribute4().getValue());

        myEntity01.setAttribute4(new Attrs("800", "Integer"));
        orion.replaceAllEntitiesAttributes("urn:ngsi-ld:Square:2", myEntity01);

        myEntity02.setAttribute4(new Attrs("800", "Integer"));
        orion.replaceAllEntitiesAttributes("urn:ngsi-ld:Square:2", myEntity02);

        myEntity04 = (MyEntity) orion.retrieveEntityAttributes("urn:ngsi-ld:Square:2", myEntity02);
        System.out.println(myEntity04.getAttribute4().getType());

        // updating the entity attribute, if attribute doesn't exists in entity, is appended.
        myEntity01.setAttribute4(new Attrs("800", "Float"));
        orion.updateOrAppendEntityAttributes("urn:ngsi-ld:Square:2", myEntity01);

        myEntity02.setAttribute4(new Attrs("800", "Float"));
        orion.updateOrAppendEntityAttributes("urn:ngsi-ld:Square:2", myEntity02);
        myEntity04 = (MyEntity) orion.retrieveEntityAttributes("urn:ngsi-ld:Square:2", myEntity02);
        System.out.println(myEntity04.getAttribute4().getType());

        // update the existing entity attributes, if don't exists throws a exception
        myEntity02.setAttribute3(new Attrs("800", "Integer"));
        orion.updateExistingEntityAttributes("urn:ngsi-ld:Square:2", myEntity02);
        myEntity04 = (MyEntity) orion.retrieveEntityAttributes("urn:ngsi-ld:Square:2", myEntity02);
        System.out.println(myEntity04.getAttribute3().getType());

        // retrieving entities by type
        List<MyEntity> mylist = orion.listEntities("type=Square", myEntity01);
        System.out.println("Aqui:" + mylist.get(0).getAttribute5().getValue());

        // using registrations operations
        List<String> attrs1 = new ArrayList<>();
        attrs1.add("attribute1");
        attrs1.add("attribute2");

        orion.createRegistration(myEntity01, attrs1, "http://iot-sensors:3001/iot/Square01");

        List<Registrations> registrationsList = orion.listRegistrations();
        System.out.println("1: " + registrationsList.get(0).getId());

//        orion.deleteRegistration(registrationsList.get(0).getId());

        Registrations registrations = orion.retrieveRegistration(registrationsList.get(0).getId());
        System.out.println("2: " + registrations.getId());
        orion.setDebugMode(true);

    }
    public interface lambdaf{
        MyEntity lambdaUpdate(MyEntity entity);
    }

    public static MyEntity updateEntity(MyEntity en) {
        if (Integer.parseInt(en.getAttribute4().getValue()) > 100) {
            en.setAttribute5(new Attrs("0", "Float"));
        }else {
            System.out.println("preguica");
        }

        return en;
    }

}
