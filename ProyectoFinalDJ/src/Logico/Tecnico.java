package Logico;

public class Tecnico extends Personal {
	private String tipoTecnico;
	private String zonAsignada;
	private int horasExtra;
	private boolean licencia;
	private int cantidadInstalaciones;
	public Tecnico(String idEmpleado, String nombre, String cedula, float salarioBase, String tipoTecnico,
			String zonAsignada, int horasExtra, boolean licencia, int cantidadInstalaciones) {
		super(idEmpleado, nombre, cedula, salarioBase);
		this.tipoTecnico = tipoTecnico;
		this.zonAsignada = zonAsignada;
		this.horasExtra = horasExtra;
		this.licencia = licencia;
		this.cantidadInstalaciones = cantidadInstalaciones;
	}
	public String getTipoTecnico() {
		return tipoTecnico;
	}
	public void setTipoTecnico(String tipoTecnico) {
		this.tipoTecnico = tipoTecnico;
	}
	public String getZonAsignada() {
		return zonAsignada;
	}
	public void setZonAsignada(String zonAsignada) {
		this.zonAsignada = zonAsignada;
	}
	public int getHorasExtra() {
		return horasExtra;
	}
	public void setHorasExtra(int horasExtra) {
		this.horasExtra = horasExtra;
	}
	public boolean isLicencia() {
		return licencia;
	}
	public void setLicencia(boolean licencia) {
		this.licencia = licencia;
	}
	public int getCantidadInstalaciones() {
		return cantidadInstalaciones;
	}
	public void setCantidadInstalaciones(int cantidadInstalaciones) {
		this.cantidadInstalaciones = cantidadInstalaciones;
	}
	
}
