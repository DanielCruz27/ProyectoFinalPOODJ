package Logico;

public class PlanHogar extends Servicio {
	private String numeroTelefonico;
	private int velocidadInternet;
	private String StreamingIncluido;
	private int minutosTelefonoHogar;
	
	
	public PlanHogar(String numeroTelefonico, int velocidadInternet, String streamingIncluido,
			int minutosTelefonoHogar) {
		super();
		this.setNumeroTelefonico(numeroTelefonico);
		this.velocidadInternet = velocidadInternet;
		StreamingIncluido = streamingIncluido;
		this.minutosTelefonoHogar = minutosTelefonoHogar;
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
	public String getNumeroTelefonico() {
		return numeroTelefonico;
	}
	public void setNumeroTelefonico(String numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}
	
	

}
