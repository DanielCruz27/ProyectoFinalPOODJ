package Logico;

public class PlanMovil extends Servicio {
	private String numeroTelefonico;
	private int minutosLibres;
	private String redesLibresIncluidas;
	
	public PlanMovil(String idServicio, String nombreServicio, float precioBase, String numeroTelefonico,
			int minutosLibres, String redesLibresIncluidas) {
		super(idServicio, nombreServicio, precioBase);
		this.numeroTelefonico = numeroTelefonico;
		this.minutosLibres = minutosLibres;
		this.redesLibresIncluidas = redesLibresIncluidas;
	}

	public String getNumeroTelefonico() {
		return numeroTelefonico;
	}

	public void setNumeroTelefonico(String numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}

	public int getMinutosLibres() {
		return minutosLibres;
	}

	public void setMinutosLibres(int minutosLibres) {
		this.minutosLibres = minutosLibres;
	}

	public String getRedesLibresIncluidas() {
		return redesLibresIncluidas;
	}

	public void setRedesLibresIncluidas(String redesLibresIncluidas) {
		this.redesLibresIncluidas = redesLibresIncluidas;
	}
	
	
	
}
