package Logico;

public class Recarga extends Servicio {
	
	private int vigencia;
	private float MontoARecargar;
	
	public Recarga(String idServicio, String nombreServicio, float precioBase, int vigencia, float montoARecargar) {
		super(idServicio, nombreServicio, precioBase);
		this.vigencia = vigencia;
		MontoARecargar = montoARecargar;
	}

	public int getVigencia() {
		return vigencia;
	}

	public void setVigencia(int vigencia) {
		this.vigencia = vigencia;
	}

	public float getMontoARecargar() {
		return MontoARecargar;
	}

	public void setMontoARecargar(float montoARecargar) {
		MontoARecargar = montoARecargar;
	}

}
