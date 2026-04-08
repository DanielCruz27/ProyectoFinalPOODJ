package Logico;

public class PlanMovil extends Servicio {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String numeroTelefonico;
	private int minutosIncluidos;
	private String redesLibresIncluidas;
	
	public PlanMovil(String idServicio, String nombreServicio, float precioBase, boolean estadoDelServicio, String numeroTelefonico,
			int minutosIncluidos, String redesLibresIncluidas) {
		super(idServicio, nombreServicio, precioBase, estadoDelServicio);
		this.numeroTelefonico = numeroTelefonico;
		this.minutosIncluidos = minutosIncluidos;
		this.redesLibresIncluidas = redesLibresIncluidas;
	}

	public String getNumeroTelefonico() {
		return numeroTelefonico;
	}

	public void setNumeroTelefonico(String numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}

	public int getMinutosIncluidos() {
		return minutosIncluidos;
	}

	public void setMinutosLibres(int minutosIncluidos) {
		this.minutosIncluidos = minutosIncluidos;
	}

	public String getRedesLibresIncluidas() {
		return redesLibresIncluidas;
	}

	public void setRedesLibresIncluidas(String redesLibresIncluidas) {
		this.redesLibresIncluidas = redesLibresIncluidas;
	}
	
	
	
}
