package Logico;

public class PlanHogar extends Servicio {
	private int numeroTelefonico;
	private int velocidadInternet;
	private String StreamingIncluido;
	private int minutosTelefonoHogar;
	public PlanHogar(int numeroTelefonico, int velocidadInternet, String streamingIncluido, int minutosTelefonoHogar) {
		super();
		this.numeroTelefonico = numeroTelefonico;
		this.velocidadInternet = velocidadInternet;
		StreamingIncluido = streamingIncluido;
		this.minutosTelefonoHogar = minutosTelefonoHogar;
	}
	public int getNumeroTelefonico() {
		return numeroTelefonico;
	}
	public void setNumeroTelefonico(int numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}
	public int getVelocidadInternet() {
		return velocidadInternet;
	}
	public void setVelocidadInternet(int velocidadInternet) {
		this.velocidadInternet = velocidadInternet;
	}
	public String getStreamingIncluido() {
		return StreamingIncluido;
	}
	public void setStreamingIncluido(String streamingIncluido) {
		StreamingIncluido = streamingIncluido;
	}
	public int getMinutosTelefonoHogar() {
		return minutosTelefonoHogar;
	}
	public void setMinutosTelefonoHogar(int minutosTelefonoHogar) {
		this.minutosTelefonoHogar = minutosTelefonoHogar;
	}
	
	

}
