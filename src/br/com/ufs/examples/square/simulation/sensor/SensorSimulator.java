package br.com.ufs.examples.square.simulation.sensor;

import java.util.concurrent.ThreadLocalRandom;

import br.com.ufs.examples.square.simulation.ultralight.AttrsUltraLight;
import br.com.ufs.iotaframework.iota.IoTA;
import br.com.ufs.examples.square.simulation.ultralight.UltraLight;

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

public class SensorSimulator {
    
    public static void initializeLamps(IoTA iota) throws Exception {
    	int idLampada = 1;
     	for(int i = 0; i<3;i++) {
     		double loc1 = -10.936173-0.000072*i;
     		
     		for(int j = 0; j<5;j++, idLampada++) {
     			double loc2 = -37.061368+0.000072*j;
     			AttrsUltraLight[] condicoesIniciais = {new AttrsUltraLight("l","2"), //luminosity = 2
						new AttrsUltraLight("s","on"),								 //state = on
						new AttrsUltraLight("lo",loc1+","+loc2), 					 //location = (-10.936173,-37.061368) é a lâmpada nº1
						new AttrsUltraLight("c","0"),								 //count = 0 (nenhuma lâmpada apagada em um raio de 9m)
						new AttrsUltraLight("n",String.valueOf(idLampada))};         //número da Lâmpada ("id")
     			
     			UltraLight ultralight = new UltraLight(condicoesIniciais);
     			System.out.println("Initialize Lamps Ultralight: \n"+UltraLight.toStringMsg(ultralight));
     			iota.sendMeasure("localhost:7896","/iot/d","4jggokgpepnvsb2uv4s40d59ov","lamp"+idLampada,UltraLight.toStringMsg(ultralight));
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

    	try{
    	    Thread.sleep(1000*6);  //testar para definir o tempo em cada ambiente novo
    	}
    	catch(InterruptedException ex){
    	    Thread.currentThread().interrupt();
    	}
    	
    	initializeLamps(iota);
     	
     	
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
    		}
    		else {
				iota.sendMeasure("localhost:7896","/iot/d","4jggokgpepnvsb2uv4s40d59ov","lamp"+randomNum, UltraLight.toStringMsg(ultralight_on));
    		}

			System.out.println("Lamp"+randomNum + " | " + "S: " + (randomOff ? "off": "on"));
			try{
        	    Thread.sleep(7000); //testar para definir o tempo em cada ambiente novo
        	}
        	catch(InterruptedException ex){
        	    Thread.currentThread().interrupt();
        	}
		}
    	    	
    }

}
