package br.com.ufs.examples.square.simulation.sensor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

import br.com.ufs.examples.square.simulation.ultralight.AttrsUltraLight;
import br.com.ufs.iotaframework.iota.IoTA;
import br.com.ufs.examples.square.simulation.ultralight.UltraLight;
public class SensorSimulator {
	
	private ServerSocket server;
    
    public SensorSimulator() throws Exception {
        this.server = new ServerSocket(0, 1, InetAddress.getByName("172.18.1.1"));
    }

    public static void postRequest(int port, String msg, URL url, String content_type, String requestMethod) throws IOException {
    	
    	HttpURLConnection httpURLConnection = null;
    	DataOutputStream dataOutputStream = null;
    	byte[] postData = msg.getBytes(StandardCharsets.UTF_8);
    	try {
    	    httpURLConnection = (HttpURLConnection) url.openConnection(); // preparando a conexão (cabecalhos)
    	    httpURLConnection.setRequestProperty("Content-Type", content_type);
    	    httpURLConnection.setRequestProperty("fiware-service", "openiot");
    	    httpURLConnection.setRequestProperty("fiware-servicepath", "/");
    	    httpURLConnection.setRequestMethod(requestMethod);
    	    httpURLConnection.setDoInput(true); // significa que vai usar a conexão como input
    	    httpURLConnection.setDoOutput(true); // significa que vai usar a conexão como output
    	    dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
    	    dataOutputStream.write(postData);
    	    StringBuilder content;
    	    try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) { // pegando os dados que foram enviados pela conexão
                    content.append(line);
                    content.append(System.lineSeparator()); // assim como o comando "diz", é um separador de linhas
                }
            }
    	} catch (IOException exception) {
    	    exception.printStackTrace();
    	}  finally {
    	    if (dataOutputStream != null) {
    	        try {
    	            dataOutputStream.flush();
    	            dataOutputStream.close();
    	        } catch (IOException exception) {
    	            exception.printStackTrace();
    	        }
    	    }
    	    if (httpURLConnection != null) {
    	    	httpURLConnection.disconnect();
    	    }
    	}
    	
    }

    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }
    
    public int getPort() {
        return this.server.getLocalPort();
    }
    
    public static void initializeLamps(int port, IoTA iota) throws Exception {
    	int idLampada = 1;
     	for(int i = 0; i<4;i++) {
     		double loc1 = -10.936173-0.000072*i;
     		
     		for(int j = 0; j<4;j++, idLampada++) {
     			double loc2 = -37.061368+0.000072*j;
//     			String response = app.listen();
     			AttrsUltraLight[] condicoesIniciais = {new AttrsUltraLight("l","2"), //luminosity = 2
						new AttrsUltraLight("s","on"),								 //state = on
						new AttrsUltraLight("lo",loc1+","+loc2), 					 //location = (-10.936173,-37.061368) é a lâmpada nº1
						new AttrsUltraLight("c","0"),								 //count = 0 (nenhuma lâmpada apagada em um raio de 9m)
						new AttrsUltraLight("n",String.valueOf(idLampada))};         //número da Lâmpada ("id")
     			
     			UltraLight ultralight = new UltraLight(condicoesIniciais);

     			System.out.println("Initialize Lamps Ultralight: \n"+UltraLight.toStringMsg(ultralight));
     			iota.sendMeasure("localhost:7896","/iot/d","4jggokgpepnvsb2uv4s40d59ov","lamp"+idLampada,UltraLight.toStringMsg(ultralight));
//     	     	URL urlUpdate = new URL("http://localhost:7896/iot/d?k=4jggokgpepnvsb2uv4s40d59ov&i=lamp"+idLampada);
//     	     	SensorSimulator.postRequest(port,UltraLight.toStringMsg(ultralight),urlUpdate,"text/plain","POST");
     			try{
     	    	    Thread.sleep(1000*5);
     	    	}
     	    	catch(InterruptedException ex){
     	    	    Thread.currentThread().interrupt();
     	    	}
     		}	
    	}
    }
    
    public static void main(String[] args) throws Exception {

    	SensorSimulator app = new SensorSimulator();

    	IoTA iota = new IoTA("localhost",4041);
    	iota.setDebugMode(true);
    	
    	try{
    	    Thread.sleep(1000*6);  //testar para definir o tempo em cada ambiente novo
    	}
    	catch(InterruptedException ex){
    	    Thread.currentThread().interrupt();
    	}
    	
    	initializeLamps(app.getPort(), iota);
     	
     	
     	try{
    	    Thread.sleep(1000*7); //testar para definir o tempo em cada ambiente novo
    	}
    	catch(InterruptedException ex){
    	    Thread.currentThread().interrupt();
    	}
     	
    	AttrsUltraLight[] attr_off = {new AttrsUltraLight("s","off")};
    	AttrsUltraLight[] attr_on = {new AttrsUltraLight("s","on")};
    	
    	
    	UltraLight ultralight_off = new UltraLight(attr_off);
    	UltraLight ultralight_on = new UltraLight(attr_on);

    	int quantity = 15;
    	
    	while(true) {
    		int randomNum = ThreadLocalRandom.current().nextInt(1, quantity+1);
    		boolean randomOff = ThreadLocalRandom.current().nextBoolean();
    		if(randomOff) {
				iota.sendMeasure("localhost:7896","/iot/d","4jggokgpepnvsb2uv4s40d59ov","lamp"+randomNum, UltraLight.toStringMsg(ultralight_off));
//    			SensorSimulator.postRequest(app.getPort(),UltraLight.toStringMsg(ultralight_off),
//        				new URL("http://localhost:7896/iot/d?k=4jggokgpepnvsb2uv4s40d59ov&i=lamp"+randomNum),"text/plain","POST");
    		}
    		else {
				iota.sendMeasure("localhost:7896","/iot/d","4jggokgpepnvsb2uv4s40d59ov","lamp"+randomNum, UltraLight.toStringMsg(ultralight_on));
//    			SensorSimulator.postRequest(app.getPort(),UltraLight.toStringMsg(ultralight_on),
//        				new URL("http://localhost:7896/iot/d?k=4jggokgpepnvsb2uv4s40d59ov&i=lamp"+randomNum),"text/plain","POST");
    		}
    		
    		try{
        	    Thread.sleep(1000*7); //testar para definir o tempo em cada ambiente novo
        	}
        	catch(InterruptedException ex){
        	    Thread.currentThread().interrupt();
        	}
    	}
    	    	
    }

}
