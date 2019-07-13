package br.com.ufs.orionframework.usersguide;

import br.com.ufs.orionframework.batchupdate.BatchUpdate;
import br.com.ufs.orionframework.entity.Entity;
import br.com.ufs.orionframework.entitylamp.Attrs;
import br.com.ufs.orionframework.genericnotification.GenericNotification;
import br.com.ufs.orionframework.mytesteentity.MyEntity;
import br.com.ufs.orionframework.orion.Orion;
import br.com.ufs.orionframework.subscription.*;
import br.com.ufs.orionframework.subscriptor.Subscriptor;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {

        //First, you need to make a Orion instance
        Orion orion = new Orion();
        // You need to define a class .java which represents a entity, here we defined a class called Myentity with 5 attributes
        // Before instantiate a entity, we have to instantiate the entities attributes
        Attrs name = new Attrs("Praca Oliveira Belo","Text");
        Attrs location = new Attrs("-10.936245,-37.061224","geo:point");
        Attrs radius = new Attrs("45","Float");
        Attrs pressure = new Attrs("450","Float");
        Attrs temperature = new Attrs("22","Float");

        // After define the attributes, we can make a instance from entity
        MyEntity myEntity01 = new MyEntity("urn:ngsi-ld:Square:1","Square",name,location,radius,pressure,temperature);
        MyEntity myEntity02 = new MyEntity("urn:ngsi-ld:Square:2","Square",name,location,radius,pressure,temperature);
        // And submit to orion using the method createEntity
//        orion.createEntity(myEntity01);
//        orion.createEntity(myEntity02);

        //retrieving a entity from orion
        MyEntity myEntity03 = (MyEntity) orion.retrieveEntity("urn:ngsi-ld:Square:2", myEntity01);
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
//        BatchUpdate batchupdate = new BatchUpdate(batchEntities);
//        orion.batchupdate(batchupdate);

        //orion.listEntities("");
        //retrieving updated entity and print the class update
//        square02 = (MyEntity) orion.retrieveEntity("urn:ngsi-ld:Square:1", square01);
//        System.out.println("After: "+ ((MyEntity) square02).getAttribute3().getValue());

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

//        Future<String> f = myEntity01.subscribeAndListen(40041, "172.18.1.1");

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

//          MyEntity myEntity03 = (MyEntity) myEntity01.getSubscriptionUpdate(f, myEntity01);
//          System.out.println(myEntity03.getAttribute4().getValue());

            // put the update logic on mylambda1 and mylambda2
            /*
            Aparentemente não consigo mandar uma função do tipo do usuário, devido ao fato que o outro lado espera outro tipo de função
            Este, não pode ser alterado, e portanto, tenho que consultar o professor quanto a esse método.
            * */
            lambdaf mylambda1 = (en) -> {
                if(Integer.parseInt(en.getAttribute4().getValue()) > 100) {
                    System.out.println("maior que 100");
                    System.out.println(en.getAttribute5().getValue());
                } else {
                    System.out.println("menor que 100");
                }
                return en;
            };
            lambdaf mylambda2 = (en) -> {
                if(Integer.parseInt(en.getAttribute4().getValue()) > 100) {
                    System.out.println("maior que 100");
                    System.out.println(en.getAttribute5().getValue());
                } else {
                    System.out.println("menor que 100");
                }
                return en;
            };

            System.out.println(Entity.class);
            System.out.println(MyEntity.class);

            ServerSocket ss = new ServerSocket(40041, 1, InetAddress.getByName("172.18.1.1"));
            Subscriptor subscriptor1 = new Subscriptor(40041, "172.18.1.1","urn:ngsi-ld:Square:1", "Square", ss, myEntity01);
            subscriptor1.subscribeAndListen( en -> updateEntity((MyEntity) en), myEntity01);
            System.out.println("olhaaa");
            Subscriptor subscriptor2 = new Subscriptor(40041, "172.18.1.1","urn:ngsi-ld:Square:2", "Square", ss, myEntity02);
            subscriptor2.subscribeAndListen(en -> updateEntity((MyEntity) en), myEntity02);

            MyEntity myEntity04 = (MyEntity) orion.retrieveEntityAttributes("urn:ngsi-ld:Square:2", myEntity02);
            System.out.println(myEntity04.getAttribute4().getValue());

            myEntity01.setAttribute4(new Attrs("800", "Integer"));
            orion.replaceAllEntitiesAttributes("urn:ngsi-ld:Square:2", myEntity01);

            myEntity02.setAttribute4(new Attrs("800", "Integer"));
            orion.replaceAllEntitiesAttributes("urn:ngsi-ld:Square:2", myEntity02);

            myEntity04 = (MyEntity) orion.retrieveEntityAttributes("urn:ngsi-ld:Square:2", myEntity02);
            System.out.println(myEntity04.getAttribute4().getType());

            myEntity01.setAttribute4(new Attrs("800", "Float"));
            orion.updateOrAppendEntityAttributes("urn:ngsi-ld:Square:2", myEntity01);

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
