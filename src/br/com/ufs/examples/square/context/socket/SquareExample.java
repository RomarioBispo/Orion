package br.com.ufs.examples.square.context.socket;

import br.com.ufs.builtindevices.hybrid.Lamp.Lamp;
import br.com.ufs.examples.square.context.square.Square;
import br.com.ufs.iotaframework.devices.Attribute;
import br.com.ufs.iotaframework.devices.Device;
import br.com.ufs.iotaframework.devices.DeviceList;
import br.com.ufs.iotaframework.devices.StaticAttribute;
import br.com.ufs.iotaframework.iota.IoTA;
import br.com.ufs.iotaframework.services.Service;
import br.com.ufs.iotaframework.services.ServiceGroup;
import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.orion.Orion;
import br.com.ufs.orionframework.subscription.Subscription;
import br.com.ufs.orionframework.subscriptor.Subscriptor;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to concept proof for the Orion Framework.
 * To this only purpose, the project developed in Mariana Martins undergraduate thesis was used.
 * Your project was modified to use the framework.
 *
 * @author Romario Bispo, Mariana Martins.
 * @version %I%, %G%
 * @since 1.0
 * @see br.com.ufs.examples.square.context.socket.SquareExample
 * */


public class SquareExample {

	private static Orion orion = new Orion();
    private static IoTA iota = new IoTA("localhost", 4041, "localhost:7896", "/iot/d/","4jggokgpepnvsb2uv4s40d59ov");
    private static int quantity = 15;

	private static String [] previousState = new String[quantity+1];

	public static void printLampsFramework() {
		List<Lamp> lampList = orion.listEntities("type=Lamp", new Lamp());
		int k = 0;
		String [] l = new String[quantity];
		  for (Lamp lamp: lampList){
			  Pattern p = Pattern.compile("\\d+");
			  Matcher m = p.matcher(lamp.getId());
			  while(m.find()) {
				  l[Integer.parseInt(m.group())-1] = String.valueOf(lamp.getLuminosity());
			  }
		  }
		  System.out.println("------------------------");
		  for (int i = 0; i< 3; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(l[k] + "  ");
				k++;
			}
			System.out.println();
		}
		 System.out.println("------------------------");
	  }


	// wait the initial values from IoT-A.
    public static void initializeLamps(ServerSocket ss) throws Exception {
	  	// listen the firsts initial conditions, creating a subscription, when read the first notification for the lamps, exclude the subscriptions.
	  	System.out.println("Initializing lamps");
		orion.createSimpleSubscription(".*", "Lamp", 40041, "172.18.1.1", false);

	  	List<Subscription> subscriptionList = orion.listSubscriptions();

    	for(int i = 0; i < quantity + 1; i++){
    		orion.listenNotification(ss);
		}

    	for (Subscription sub: subscriptionList) {
			orion.deleteSubscription(sub.getId());
		}

    	System.out.println("Lamps initialized");

    }

    public static void lampOffFramework(Lamp lamp, String[] previousState) {

    	if (!lamp.getState().equals(previousState[lamp.getNumber()])){

            lamp.setLuminosity(0, iota);
    		// equivalente ao getbylocation
    		List<Lamp> lampList = orion.listEntities("type=Lamp&georel=near;maxDistance:9&geometry=point&coords="+
			lamp.getLocation(), lamp);

    		for (Lamp lamps : lampList) {
                int count_int = lamps.getCount() + 1;
    			if (lamps.getState().equals("off") && !lamps.getId().equals(lamp.getId())){
    				//equivalente ao updateluminositycount
					lamps.setLuminosity(0, iota);
					lamps.setCount(count_int, iota);
				} else if (lamps.getState().equals("on")) {
					//equivalente ao updateluminositycount
    				lamps.setLuminosity(3, iota);
					lamps.setCount(2, iota);
				}
			}
		}
	}

    public static void lampOnFramework(Lamp lamp, String[] previousState) {
    	if (!lamp.getState().equals(previousState[lamp.getNumber()])) {
			// equivalente ao getbylocation
			List<Lamp> lampList = orion.listEntities("type=Lamp&georel=near;maxDistance:9&geometry=point&coords="+
					lamp.getLocation(),lamp);

			for (Lamp lamps: lampList){
				if (!lamps.getId().equals(lamp.getId())){
					int count_int = lamps.getCount() - 1;

					if (count_int == 0 && lamps.getState().equals("on")) {
						lamps.setLuminosity(2, iota);
						lamps.setCount(count_int, iota);
					} else {

						lamps.setLuminosity(lamps.getLuminosity(), iota);
						lamps.setCount(count_int, iota);
					}
				}
			}

			int luminosity;
			//Se uma lampada volta pra ON mas tem uma OFF no raio
			if(lamp.getCount() != 0){
				luminosity = 3;
			}else {
				luminosity = 2;
			}
				//Se uma lampada volta pra ON e não tem uma OFF no raio
			lamp.setLuminosity(luminosity, iota);

		}
	}

    public static void main(String[] args) throws Exception {

		// criando entidade no orion
		Attrs name = new Attrs("Praça Oliveira Belo","Text");
		Attrs location = new Attrs("-10.936245,-37.061224","geo:point");
		Attrs radius = new Attrs("45","Float");
		Square square01 = new Square("urn:ngsi-ld:Square:1", "Square", name, location, radius);
		orion.createEntity(square01);

		// criando service group
		Service service = new Service("4jggokgpepnvsb2uv4s40d59ov","","http://orion:1026","Thing","/iot/d");
		List<Service> serviceList = new ArrayList<>();
		serviceList.add(service);
		ServiceGroup serviceGroup = new ServiceGroup(serviceList);
		iota.createService(serviceGroup);

        //Create Devices
		List<Attribute> attributeList = new ArrayList<>();
		attributeList.add(new Attribute("s","state","Text"));
		attributeList.add(new Attribute("l","luminosity","Integer"));
		attributeList.add(new Attribute("lo","location","geo:point"));
		attributeList.add(new Attribute("c", "count", "Integer"));
		attributeList.add(new Attribute("n", "number", "Integer"));

		List<StaticAttribute> staticAttributeList = new ArrayList<>();
		staticAttributeList.add(new StaticAttribute("refSquare", "Relationship", "urn:ngsi-ld:Square:1"));

		ServerSocket ss = new ServerSocket(40041, 1, InetAddress.getByName("172.18.1.1"));

		List<Device> deviceList = new ArrayList<>();
		for(int i = 0; i<quantity;i++) {
			deviceList.add(new Device("lamp"+(i+1),"123456","urn:ngsi-ld:Lamp:"+(i+1),"Lamp","Sergipe/Aracaju", attributeList,staticAttributeList));
		}

		DeviceList devices = new DeviceList(deviceList);
		iota.createDevice(devices);

		initializeLamps(ss);

		Subscriptor subscriptor = new Subscriptor(40041, "172.18.1.1", ss, deviceList);

		List<Lamp> mylist = orion.listEntities("type=Lamp", new Lamp());

		for (Lamp aux: mylist) {
			previousState[aux.getNumber()] = aux.getState();
		}
		List<String> conditionsList = new ArrayList<>();
		conditionsList.add("state");
		subscriptor.subscribeAndListen(en -> updateEntity((Lamp) en), new Lamp(), conditionsList, deviceList);

    }

	public static Lamp updateEntity(Lamp l) {

		System.out.println("***"+l.getId() +" "+ l.getState()+"***");

		if (l.getState().equals("off")){
			lampOffFramework(l, previousState);
		}else {
			lampOnFramework(l, previousState);
		}
		previousState[l.getNumber()] = l.getState();
		printLampsFramework();
		return l;
	}
}












