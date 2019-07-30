package br.com.ufs.examples.square.simulation.ultralight;


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


public class UltraLight {
	private AttrsUltraLight[] attrsUltraLight;
	
	public UltraLight(AttrsUltraLight[] attrsUltraLight) {
		this.attrsUltraLight = attrsUltraLight;
	}


	public static String toStringMsg(UltraLight ultralight) {
		String msg = "";
		int i = 0;
		for(i=0; i < ultralight.getAttrsUltraLight().length-1; i++) {
			msg = msg + ultralight.attrsUltraLight[i].getName() + "|" + ultralight.attrsUltraLight[i].getValue() + "|";
		}
		msg = msg + ultralight.attrsUltraLight[i].getName()+ "|" + ultralight.attrsUltraLight[i].getValue();
		return msg;
	}
	
	public AttrsUltraLight[] getAttrsUltraLight() {
		return attrsUltraLight;
	}

	public void setAttrsUltraLight(AttrsUltraLight[] attrsUltraLight) {
		this.attrsUltraLight = attrsUltraLight;
	}
}
