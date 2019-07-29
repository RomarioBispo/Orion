package br.com.ufs.examples.square.context.socket;

import br.com.ufs.iotaframework.devices.Attribute;
import br.com.ufs.iotaframework.devices.Device;
import br.com.ufs.iotaframework.devices.DeviceList;
import br.com.ufs.iotaframework.devices.StaticAttribute;
import br.com.ufs.iotaframework.iota.IoTA;
import br.com.ufs.iotaframework.services.Service;
import br.com.ufs.iotaframework.services.ServiceGroup;
import br.com.ufs.orionframework.entity.Attrs;
import br.com.ufs.orionframework.orion.Orion;
import br.com.ufs.orionframework.subscriptor.Subscriptor;
import com.java.Lamp.Lamp;
import com.java.square.Square;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SquareExample {

	private ServerSocket server; // A classe ServerSocket é responsável por esperar a conexão do cliente.
	private static String [] previousState = new String[16];
	private static Orion orion = new Orion();
	public SquareExample() throws Exception {
        this.server = new ServerSocket(0, 1, InetAddress.getByName("172.18.1.1")); // a porta ?
        // Essa classe depende da porta, tamanho da fila máxima e IP.
    }

    private static String getByLocation(String coords) throws IOException {
		// dadas as coordenadas, são retornadas as entidades próximas
    	URL url = new URL("http://localhost:1026/v2/entities?type=Lamp&georel=near;maxDistance:9&geometry=point&coords="+
				coords+"&options=keyValues");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET"); // Preparação da requisição (Setando o cabeçalho)
		con.setRequestProperty("User-Agent", "Java client");
		con.setRequestProperty("fiware-service", "openiot");
        con.setRequestProperty("fiware-servicepath", "/");
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { // se a requisição foi bem sucedida
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) { // armazene o resultado do GET
				response.append(inputLine);
			}
			in.close();

			return response.toString(); // retorna em formato String
		} else {
			System.out.println("GET request not worked"); // o request foi mal sucedido
			return null;
		}

	}

//    public static List<ResponseFromGET> stringToResponseFromGET(String response){
//    	// Cria uma lista de todas as lâmpadas retornadas
//    	List<ResponseFromGET> getAllLamps_list = new ArrayList<>();
//		// como a resposta retorna 15 lâmpadas, ela tá definindo o início do json e pegando
//		// uma a uma por meio do begin end.
//    	while(response.indexOf("{")>0) {
//			int begin = response.indexOf("{");
//			int end = response.indexOf("}"); // se eu entendi bem, esses comandos pulam uma parte do arquivo que não serve
//			Gson responseFromGet_gson = new Gson(); // Essa classe facilita o retorno de JSON, preenchendo a classe que tem o mesmo e atributos do objeto literal
//			getAllLamps_list.add(responseFromGet_gson.fromJson(response.substring(begin, end+1), ResponseFromGET.class));
//			response = response.substring(end+1); // acho que nessa parte está pulando uma parte da resposta da requisição que não serve
//		}
//    	return getAllLamps_list;
//    }

//    public static void printLamps() throws IOException {
//    	String response = SquareExample.getAll(); // Faz um get que retorna todas as lampadas
//    	List<ResponseFromGET> getAllLamps_list = SquareExample.stringToResponseFromGET(response); // Converte a resposta pra string
//
//		int k = 0;
//		System.out.println("------------------------");
//		for(int i = 0; i<3; i++) {
//			for(int j = 0; j<5; j++) {
//				System.out.print(getAllLamps_list.get(k).getLuminosity() + "  ");
//				k++;
//			}
//			System.out.println("");
//		}
//		System.out.println("------------------------");
//    }

	  public static void printLampsFramework() {
		List<Lamp> lampList = orion.listEntities("type=Lamp", new Lamp());
		int k = 0;
		String [] l = new String[16];
		  for (Lamp lamp: lampList){
			  Pattern p = Pattern.compile("\\d+");
			  Matcher m = p.matcher(lamp.getId());
			  while(m.find()) {
				  l[Integer.parseInt(m.group())-1] = lamp.getLuminosity().getValue();
			  }
		  }


		  System.out.println("------------------------");
		  for (int i = 0; i< 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(l[k] + "  ");
				k++;
			}
			System.out.println();
		}
		 System.out.println("------------------------");
	  }

//    public static void updateLuminosity(String id, String luminosity, int port) throws Exception {
//    	// preenche os objetos da classe
//    	AttributeUpdate attrLuminosity = new AttributeUpdate(luminosity);
//    	EntitieUpdate[] entitieLuminosity = {new EntitieUpdate(id,attrLuminosity)};
//    	UpdateLuminosity updateLuminosity = new UpdateLuminosity(entitieLuminosity);
//    	Gson gson = new Gson();
//        String json = gson.toJson(updateLuminosity); // por meio da classe gson, esse objeto é transformado em um JSON e enviado na requisição
//        URL url = new URL("http://localhost:1026/v2/op/update");
//        SquareExample.postRequest(port,json,url,"application/json","POST");
//
//    }
//
//    public static void updateLuminosityCount(String id, String luminosity, String count, int port) throws Exception {
//    	AttributeUpdate attrLuminosity = new AttributeUpdate(luminosity);
//    	AttributeUpdate attrCount = new AttributeUpdate(count);
//    	EntitieUpdate[] entitieLuminosityCount = {new EntitieUpdate(id,attrLuminosity,attrCount)};
//    	UpdateLuminosity updateLuminosityCount = new UpdateLuminosity(entitieLuminosityCount);
//    	Gson gson = new Gson();
//        String json = gson.toJson(updateLuminosityCount);
//        URL url = new URL("http://localhost:1026/v2/op/update");
//        SquareExample.postRequest(port,json,url,"application/json","POST");
//
//    }

    public static void initializeLamps(SquareExample app, int quantity) throws Exception {
    	for(int i = 0; i < quantity; i++){
			//System.out.println("método initialize lamps \n");
//			app.listen();
		}
    }

    public static void lampOffFramework(Lamp lamp, String[] previousState, Orion orion ) {

    	if (!lamp.getState().getValue().equals(previousState[Integer.parseInt(lamp.getNumber().getValue()) - 1])){
    		orion.updateAttributeData(lamp.getId(),"luminosity", new Attrs("0","Integer"));

    		// equivalente ao getbylocation
    		List<Lamp> lampList = orion.listEntities("type=Lamp&georel=near;maxDistance:9&geometry=point&coords="+
			lamp.getLocation().getValue(), lamp);

    		for (Lamp lamps : lampList) {
    			int count_int = Integer.parseInt(lamps.getCount().getValue()) + 1;
    			if (lamps.getState().equals("off") && !lamps.getId().equals(lamp.getId())){
    				//equivalente ao updateluminositycount
					orion.updateAttributeData(lamps.getId(), "luminosity", new Attrs("0", "Integer"));
					orion.updateAttributeData(lamps.getId(), "count", new Attrs(String.valueOf(count_int),"Integer"));
				} else if (lamps.getState().equals("on")) {
					//equivalente ao updateluminositycount
    				orion.updateAttributeData(lamps.getId(), "luminosity", new Attrs("3","Integer"));
					orion.updateAttributeData(lamps.getId(), "count", new Attrs(String.valueOf(count_int),"Integer"));
				}
			}
		}
	}
//    public static void lampOff(Data notificationData, String[] previousState, List<ResponseFromGET> getLamps, int port) throws Exception {
//    	// Se o estado anterior da entidade for diferente do estado atual (i.e. houve mudança no valor etc)
//    	if(!notificationData.getState().getValue().equals(previousState[Integer.parseInt(notificationData.getNumber().getValue())])) {
//			updateLuminosity(notificationData.getId(),"0", port);
//
//			String response_string = SquareExample.getByLocation(notificationData.getLocation().getValue());
//			getLamps = SquareExample.stringToResponseFromGET(response_string);
//
//			for(ResponseFromGET lamp : getLamps) {
//				int count_int = lamp.getCount() + 1; // não entendi
//				if(lamp.getState().equals("off") && !lamp.getId().equals(notificationData.getId()))
//					updateLuminosityCount(lamp.getId(),"0",String.valueOf(count_int), port);
//				//se tiver uma lampada X por perto com status on incrementa o contador da lampada X e aumenta a luminosidade da lampada X
//				else if (lamp.getState().equals("on"))
//					updateLuminosityCount(lamp.getId(),"3",String.valueOf(count_int), port);
//			}
//		}
//    }

    public static void lampOnFramework(Lamp lamp, String[] previousState, Orion orion) {
    	if (!lamp.getState().getValue().equals(previousState[Integer.parseInt(lamp.getNumber().getValue()) - 1])) {
			// equivalente ao getbylocation
			List<Lamp> lampList = orion.listEntities("type=Lamp&georel=near;maxDistance:9&geometry=point&coords="+
					lamp.getLocation().getValue(),lamp);

			for (Lamp lamps: lampList){
				if (!lamps.getId().equals(lamp.getId())){
					int count_int = Integer.parseInt(lamps.getCount().getValue()) - 1;

					if (count_int == 0 && lamps.getState().getValue().equals("on")) {
						orion.updateAttributeData(lamps.getId(), "count", new Attrs(String.valueOf(count_int),"Integer"));
						orion.updateAttributeData(lamps.getId(), "luminosity", new Attrs("2","Integer"));
					} else {
						orion.updateAttributeData(lamps.getId(), "luminosity", new Attrs(lamps.getLuminosity().getValue(),"Integer"));
						orion.updateAttributeData(lamps.getId(), "count", new Attrs(String.valueOf(count_int),"Integer"));
					}
				}
			}

			String luminosity;
			//Se uma lampada volta pra ON mas tem uma OFF no raio
			if(!lamp.getCount().getValue().equals("0")){
				luminosity = "3";
			}else {
				luminosity = "2";
			}
				//Se uma lampada volta pra ON e não tem uma OFF no raio
			orion.updateAttributeData(lamp.getId(), "luminosity", new Attrs(luminosity, "Integer"));

		}
	}

//    public static void lampOn(Data notificationData, String[] previousState, List<ResponseFromGET> getLamps, int port) throws Exception {
//    	// se o estado atual for diferente do estado anterior, ou seja, se houve alteração
//    	if (!notificationData.getState().getValue().equals(previousState[Integer.parseInt(notificationData.getNumber().getValue())])) {
//			// retorna as lâmpadas próximas dada a geolocalização
//			String response_string = SquareExample.getByLocation(notificationData.getLocation().getValue());
//			getLamps = SquareExample.stringToResponseFromGET(response_string);
//
//			for(ResponseFromGET lamp : getLamps) {
//				if(lamp.getTimeinstant()!=null && !lamp.getId().equals(notificationData.getId())) {
//					int count_int = lamp.getCount() - 1;
//					// se não houver alguma lâmpada ao redor que estiver com avaria, inicializa a lâmpada
//					// com o valor default de luminosidade.
//					// caso contrário, ou seja, se tiver alguma lâmpada com avaria próxima, mantém a luminosidade
//					if(count_int==0 && lamp.getState().equals("on"))
//						updateLuminosityCount(lamp.getId(),"2",String.valueOf(count_int), port);
//					else
//						updateLuminosityCount(lamp.getId(),lamp.getLuminosity(),String.valueOf(count_int), port);
//				}
//			}
//			String luminosity;
//			//Se uma lampada volta pra ON mas tem uma OFF no raio
//			if(!notificationData.getCount().getValue().equals("0"))
//				luminosity = "3";
//			//Se uma lampada volta pra ON e não tem uma OFF no raio
//			else
//				luminosity = "2";
//			updateLuminosity(notificationData.getId(),luminosity, port);
//		}
//    }

    public static void main(String[] args) throws Exception {

		// criando entidade no orion
		Attrs name = new Attrs("Praça Oliveira Belo","Text");
		Attrs location = new Attrs("-10.936245,-37.061224","geo:point");
		Attrs radius = new Attrs("45","Float");
		Square square01 = new Square("urn:ngsi-ld:Square:1", "Square", name, location, radius);
		orion.createEntity(square01);

		// criando service group
    	IoTA iota = new IoTA("localhost", 4041);
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

        int quantity = 16;
        // Cria as 15 lâmpadas no orion com os atributos criados acima
		// posso colocar uma chamada bloqueante esperando a primeira notificação :D
		List<Device> deviceList = new ArrayList<>();
		for(int i = 0; i<quantity;i++) {
        	deviceList.add(new Device("lamp"+(i+1),"123456","urn:ngsi-ld:Lamp:"+(i+1),"Lamp","Sergipe/Aracaju", attributeList,staticAttributeList));
        }

		DeviceList devices = new DeviceList(deviceList);
		iota.createDevice(devices);

		ServerSocket ss = new ServerSocket(40041, 1, InetAddress.getByName("172.18.1.1"));
		Subscriptor subscriptor = new Subscriptor(40041, "172.18.1.1", ".*", "Lamp", ss, deviceList.get(0));

		List<Lamp> mylist = orion.listEntities("type=Lamp",new Lamp());

		for (Lamp aux: mylist) {
			previousState[Integer.parseInt(aux.getNumber().getValue()) - 1] = aux.getState().getValue();
		}

		subscriptor.subscribeAndListen(en -> updateEntity((Lamp) en), new Lamp());

//
//        while(true) {
//        	String response = app.listen(); // aceita o pedido de conexão e lê o JSON da entrada.
//        	gson = new Gson();
//        	notification = gson.fromJson(response, NotificationResponse.class); // dado o JSON obtido no app.listen, este, é convertido para um objeto java
//        	for(Data notificationData : notification.getData()) {
//        		System.out.println("***"+notificationData.getId() +" "+ notificationData.getState().getValue()+"***");
//
//        		if(notificationData.getState().getValue().equals("off"))
//        			lampOff(notificationData,previousState,getLamps,app.getPort());
//
//        		else if (notificationData.getState().getValue().equals("on"))
//        			lampOn(notificationData,previousState,getLamps,app.getPort());
//
//        		previousState[Integer.parseInt(notificationData.getNumber().getValue())] = notificationData.getState().getValue();
//        	}
//        	SquareExample.printLamps();
//        }
    }

	public static Lamp updateEntity(Lamp l) {
		if (l.getState().getValue().equals("off")){
			lampOffFramework(l, previousState, orion);
		}else {
			lampOnFramework(l, previousState, orion);
		}
		previousState[Integer.parseInt(l.getNumber().getValue()) - 1] = l.getState().getValue();
		System.out.println("***"+l.getId() +" "+ l.getState().getValue()+"***");
		printLampsFramework();
		return l;
	}
}












