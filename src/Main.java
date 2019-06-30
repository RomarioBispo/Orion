import entityLamp.Attrs;
import entityLamp.Lamp;
import orion.Orion;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Hello World!");

        Orion orion = new Orion();

        Attrs name = new Attrs("Praça Oliveira Belo","Text");
        Attrs location = new Attrs("-10.936245,-37.061224","geo:point");
        Attrs radius = new Attrs("45","Float");
        Lamp square01 = new Lamp("urn:ngsi-ld:Square:1","Square",name,location,radius);

        //orion.createEntity(square01);
        //orion.listEntities("");
        Lamp square02 = (Lamp) orion.retrieveEntity("urn:ngsi-ld:Square:1", square01);

        System.out.println(square02.getId());


        //orion.createSubscriptions();



    }
}
