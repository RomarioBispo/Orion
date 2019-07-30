//package com.java.socket;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.InetAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//import br.com.ufs.iotaframework.services.ServiceGroup;
//import br.com.ufs.orionframework.entity.Attrs;
//import br.com.ufs.orionframework.orion.Orion;
//import com.google.gson.Gson;
//import com.java.createdevice.CreateDevice;
//import com.java.createdevice.CreateDevice.Attribute;
//import com.java.createdevice.CreateDevice.Device;
//import com.java.createdevice.CreateDevice.StaticAttribute;
//import com.java.notificationresponse.NotificationResponse;
//import com.java.notificationresponse.NotificationResponse.Data;
//import com.java.responsefromget.ResponseFromGET;
//import com.java.square.Square;
//import com.java.subscription.Subscription;
//import com.java.subscription.Subscription.Condition;
//import com.java.subscription.Subscription.EntitiesIdPattern;
//import com.java.subscription.Subscription.Http;
//import com.java.subscription.Subscription.Notification;
//import com.java.update.AttributeUpdate;
//import com.java.update.EntitieUpdate;
//import com.java.update.UpdateLuminosity;
//
//
//
//public class MyServerSocket {
//
//	private ServerSocket server; // A classe ServerSocket é responsável por esperar a conexão do cliente.
//
//    public MyServerSocket() throws Exception {
//        this.server = new ServerSocket(0, 1, InetAddress.getByName("172.18.1.1")); // a porta ?
//        // Essa classe depende da porta, tamanho da fila máxima e IP.
//    }
//
//    private String listen() throws Exception {
//        String data = null;
//        String data2 = null;
//        Socket client = this.server.accept(); // Aceita o pedido de conexão
//
//        BufferedReader in = new BufferedReader( //Faz a leitura do que é enviado
//                new InputStreamReader(client.getInputStream()));
//        while ( (data = in.readLine()) != null ) {
//        	data2 = data;
//        }
//        return data2;
//    }
//
//    public static void postRequest(int port, String msg, URL url, String content_type, String requestMethod) throws IOException {
//
//    	HttpURLConnection httpURLConnection = null;
//    	DataOutputStream dataOutputStream = null;
//    	byte[] postData = msg.getBytes(StandardCharsets.UTF_8);
//    	try {
//    	    httpURLConnection = (HttpURLConnection) url.openConnection(); // preparação da conexão (cabeçalho etc)
//    	    httpURLConnection.setRequestProperty("Content-Type", content_type);
//    	    httpURLConnection.setRequestProperty("fiware-service", "openiot");
//    	    httpURLConnection.setRequestProperty("fiware-servicepath", "/");
//    	    httpURLConnection.setRequestMethod(requestMethod);
//    	    httpURLConnection.setDoInput(true);
//    	    httpURLConnection.setDoOutput(true);
//    	    dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
//    	    dataOutputStream.write(postData);
//    	    StringBuilder content;
//    	    try (BufferedReader in = new BufferedReader(
//                    new InputStreamReader(httpURLConnection.getInputStream()))) {
//
//                String line;
//                content = new StringBuilder();
//
//                while ((line = in.readLine()) != null) {
//                    content.append(line); // verificar o comportamento se tirar isso
//                    content.append(System.lineSeparator()); // Armazena o que foi enviado pela conexão
//                }
//            }
//    	} catch (IOException exception) {
//    	    exception.printStackTrace();
//    	}  finally {
//    	    if (dataOutputStream != null) {
//    	        try {
//    	            dataOutputStream.flush();
//    	            dataOutputStream.close();
//    	        } catch (IOException exception) {
//    	            exception.printStackTrace();
//    	        }
//    	    }
//    	    if (httpURLConnection != null) {
//    	    	httpURLConnection.disconnect();
//    	    }
//    	}
//
//    }
//
//
//    private static String getByLocation(String coords) throws IOException {
//		// dadas as coordenadas, são retornadas as entidades próximas
//    	URL url = new URL("http://localhost:1026/v2/entities?type=Lamp&georel=near;maxDistance:9&geometry=point&coords="+
//				coords+"&options=keyValues");
//		HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//		con.setRequestMethod("GET"); // Preparação da requisição (Setando o cabeçalho)
//		con.setRequestProperty("User-Agent", "Java client");
//		con.setRequestProperty("fiware-service", "openiot");
//        con.setRequestProperty("fiware-servicepath", "/");
//		int responseCode = con.getResponseCode();
//		if (responseCode == HttpURLConnection.HTTP_OK) { // se a requisição foi bem sucedida
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					con.getInputStream()));
//			String inputLine;
//			StringBuffer response = new StringBuffer();
//
//			while ((inputLine = in.readLine()) != null) { // armazene o resultado do GET
//				response.append(inputLine);
//			}
//			in.close();
//
//			return response.toString(); // retorna em formato String
//		} else {
//			System.out.println("GET request not worked"); // o request foi mal sucedido
//			return null;
//		}
//
//	}
//    // Faz um get que retorna todas as entidades lâmpadas
//    private static String getAll() throws IOException {
//		URL url = new URL("http://localhost:1026/v2/entities?type=Lamp&options=keyValues");
//		HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//		con.setRequestMethod("GET"); // preparação da requisição HTTP
//		con.setRequestProperty("User-Agent", "Java client"); // Definição do cabeçalho da requisição
//		con.setRequestProperty("fiware-service", "openiot");
//        con.setRequestProperty("fiware-servicepath", "/");
//		int responseCode = con.getResponseCode();
//
//		if (responseCode == HttpURLConnection.HTTP_OK) { // Se o Get foi realizado com sucesso
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					con.getInputStream()));
//			String inputLine;
//			StringBuffer response = new StringBuffer();
//
//			while ((inputLine = in.readLine()) != null) { // Armazena o resultado
//				response.append(inputLine);
//			}
//			in.close();
//
//			return response.toString(); // Retorna o resultado como string
//		} else { // A requisição não foi bem sucedida
//			System.out.println("GET request not worked");
//			return null;
//		}
//
//	}
//
//    public InetAddress getSocketAddress() {
//        return this.server.getInetAddress();
//    }
//
//    public int getPort() {
//        return this.server.getLocalPort();
//    }
//
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
//
//    public static void printLamps() throws IOException {
//    	String response = MyServerSocket.getAll(); // Faz um get que retorna todas as lampadas
//    	List<ResponseFromGET> getAllLamps_list = MyServerSocket.stringToResponseFromGET(response); // Converte a resposta pra string
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
//
//    public static void updateLuminosity(String id, String luminosity, int port) throws Exception {
//    	// preenche os objetos da classe
//    	AttributeUpdate attrLuminosity = new AttributeUpdate(luminosity);
//    	EntitieUpdate[] entitieLuminosity = {new EntitieUpdate(id,attrLuminosity)};
//    	UpdateLuminosity updateLuminosity = new UpdateLuminosity(entitieLuminosity);
//    	Gson gson = new Gson();
//        String json = gson.toJson(updateLuminosity); // por meio da classe gson, esse objeto é transformado em um JSON e enviado na requisição
//        URL url = new URL("http://localhost:1026/v2/op/update");
//        MyServerSocket.postRequest(port,json,url,"application/json","POST");
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
//        MyServerSocket.postRequest(port,json,url,"application/json","POST");
//
//    }
//
//    public static void initializeLamps(MyServerSocket app, int quantity) throws Exception {
//    	for(int i = 0; i < quantity; i++){
//			//System.out.println("método initialize lamps \n");
//			app.listen();
//		}
//    }
//    public static void lampOff(Data notificationData, String[] previousState, List<ResponseFromGET> getLamps, int port) throws Exception {
//    	// Se o estado anterior da entidade for diferente do estado atual (i.e. houve mudança no valor etc)
//    	if(!notificationData.getState().getValue().equals(previousState[Integer.parseInt(notificationData.getNumber().getValue())])) {
//			updateLuminosity(notificationData.getId(),"0", port);
//
//			String response_string = MyServerSocket.getByLocation(notificationData.getLocation().getValue());
//			getLamps = MyServerSocket.stringToResponseFromGET(response_string);
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
//
//    public static void lampOn(Data notificationData, String[] previousState, List<ResponseFromGET> getLamps, int port) throws Exception {
//    	// se o estado atual for diferente do estado anterior, ou seja, se houve alteração
//    	if (!notificationData.getState().getValue().equals(previousState[Integer.parseInt(notificationData.getNumber().getValue())])) {
//			// retorna as lâmpadas próximas dada a geolocalização
//			String response_string = MyServerSocket.getByLocation(notificationData.getLocation().getValue());
//			getLamps = MyServerSocket.stringToResponseFromGET(response_string);
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
//
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
//
//    public static void main(String[] args) throws Exception {
//		System.out.println("Criando o socket tcp...");
//		MyServerSocket app = new MyServerSocket();
//
//
//
//        //Service Group
//		/* A criação do grupo de serviços é feita por:
//			- Instância do objeto servicos
//			- Instância do objeto servicegroup
//			- Serialização do objeto java para um objeto JSON
//			- Realização de um post request para o IoT-Agent com a criação do grupo de serviços
//		*/
//		System.out.println("Criando o grupo de serviços");
//        Services[] services = {new Services("4jggokgpepnvsb2uv4s40d59ov","http://orion:1026","Thing","/iot/d")};
//        ServiceGroup servicegroup = new ServiceGroup(services);
//        Gson gson = new Gson();
//        String json = gson.toJson(servicegroup);
//        URL url = new URL("http://localhost:4041/iot/services");
//        MyServerSocket.postRequest(app.getPort(),json,url,"application/json","POST");
//
//        //Square
//		System.out.println("Criando a entidade no OCB");
//        Attrs name = new Attrs("Praça Oliveira Belo","Text");
//        Attrs location = new Attrs("-10.936245,-37.061224","geo:point");
//        Attrs radius = new Attrs("45","Float");
//        Square square01 = new Square("urn:ngsi-ld:Square:1","Square",name,location,radius);
//        gson = new Gson();
//        json = gson.toJson(square01);
//        url = new URL("http://localhost:1026/v2/entities");
//        MyServerSocket.postRequest(app.getPort(),json,url,"application/json","POST");
//
//        //Create Devices
//		System.out.println("Criando os dispositivos no IoT-A");
//		// cria os atributos e os static atributos que cada lâmpada deve possuir no IoT-A
//        Attribute[] attributes = {new Attribute("s","state","Text"), new Attribute("l","luminosity","Integer"),new Attribute("lo","location","geo:point"),
//        		new Attribute("c", "count", "Integer"), new Attribute("n", "number", "Integer")};
//        StaticAttribute[] static_attributes = {new StaticAttribute("refSquare", "Relationship", "urn:ngsi-ld:Square:1")};
//
//        int quantity = 15;
//        // Cria as 15 lâmpadas no orion com os atributos criados acima
//        for(int i = 0; i<quantity;i++) {
//        	Device[] device = {new Device("lamp"+(i+1),"urn:ngsi-ld:Lamp:"+(i+1),"Lamp",attributes,static_attributes)};
//            CreateDevice createDevice = new CreateDevice(device);
//            Gson gsonDevice = new Gson();
//            String jsonDevice = gsonDevice.toJson(createDevice);
//            URL urlLamp = new URL("http://localhost:4041/iot/devices");
//            System.out.println("Postando os devices");
//            MyServerSocket.postRequest(app.getPort(),jsonDevice,urlLamp,"application/json","POST");
//        }
//
//
//        //Subscription
//		System.out.println("Criando a subscrição");
//        EntitiesIdPattern[] entitiesIdPattern = {new EntitiesIdPattern(".*", "Lamp")};
//    	Subscription subscription = new Subscription("Mudança de estado das lampadas",
//				entitiesIdPattern, new Condition(new String[] {"state"}),
//				new Notification(new Http("http://172.18.1.1:"+ app.getPort()),
//				new String[] {"state","location","number","count"}) , 0);
//    	gson = new Gson();
//        json = gson.toJson(subscription);
//        url = new URL("http://localhost:1026/v2/subscriptions");
//        MyServerSocket.postRequest(app.getPort(),json,url,"application/json","POST");
//
//        // esse app.listen é pq quando é feita a requisição da subscrição, é enviada uma msg ao server.
//		System.out.println("primeiro initialize lamps: \n"+app.listen());
//
//        /*
//        	- Este método initializeLamps é uma chamada bloqueante, ou seja, depois que é criado o grupo de serviços, devices etc.
//        	- O método fica em espera pela conexão (que vem do lado do sensorSimulator). Assim, quando a mesma é estabelecida
//        	- Este método não precisa de retorno, apenas os valores são colocados no IoT-A como estado inicial.
//        	- Assim, quando entrar no while, essas informações serão lidas novamente
//        */
//		System.out.println("InitializeLamps");
//        initializeLamps(app, quantity);
//
//    	String[] previousState = new String[quantity+1];
//    	String response_string = MyServerSocket.getAll(); // retorna todos os keyvalues das entidades to tipo lâmpada
//		List<ResponseFromGET> getLamps =  MyServerSocket.stringToResponseFromGET(response_string);
//    	for(ResponseFromGET aux : getLamps)
//    		previousState[aux.getNumber()] = aux.getState(); // atribui o estado atual vindo do getall como estado anterior para todas as lâmpadas
//
//    	NotificationResponse notification = new NotificationResponse();
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
//        	MyServerSocket.printLamps();
//        }
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
