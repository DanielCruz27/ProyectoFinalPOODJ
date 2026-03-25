package Logico;

public class PlanMovil extends Servicio {
	private int numeroTelefonico;
	private int minutosLibres;
	private String redesLibresIncluidas;
	public PlanMovil(int numeroTelefonico, int minutosLibres, String redesLibresIncluidas) {
		super();
		this.numeroTelefonico = numeroTelefonico;
		this.minutosLibres = minutosLibres;
		this.redesLibresIncluidas = redesLibresIncluidas;
	}
	public int getNumeroTelefonico() {
		return numeroTelefonico;
	}
	public void setNumeroTelefonico(int numeroTelefonico) {
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
