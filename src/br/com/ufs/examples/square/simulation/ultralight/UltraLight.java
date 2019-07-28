package br.com.ufs.examples.square.simulation.ultralight;

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
