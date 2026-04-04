package Logico;

public class Tecnico extends Personal {
	private String tipoTecnico;
	private String zonAsignada;
	private boolean licencia;
	private int cantidadInstalaciones;
	private float bonoPorinstalaciones;
	private int horasExtrasTrabajadas;
	
	public Tecnico(String idEmpleado, String nombre, String cedula, float salarioBase, Usuario miCuenta, String tipoTecnico, String zonAsignada, boolean licencia, int cantidadInstalaciones,
			float bonoPorinstalaciones,int horasextras) {
		super(idEmpleado, nombre, cedula, salarioBase, miCuenta);
		this.tipoTecnico = tipoTecnico;
		this.zonAsignada = zonAsignada;
		this.licencia = licencia;
		this.cantidadInstalaciones = cantidadInstalaciones;
		this.bonoPorinstalaciones = bonoPorinstalaciones;
		this.horasExtrasTrabajadas = horasextras;
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
	@Override
	protected float calcularSueldoNeto() {
		return getSalarioBase() + bonoPorinstalaciones;
	}
	
	public float getBonoPorinstalaciones() {
		return bonoPorinstalaciones;
	}
	public void setBonoPorinstalaciones(float bonoPorinstalaciones) {
		this.bonoPorinstalaciones = bonoPorinstalaciones;
	}

	public int getHorasExtrasTrabajadas() {
		return horasExtrasTrabajadas;
	}

	public void setHorasExtrasTrabajadas(int horasExtrasTrabajadas) {
		this.horasExtrasTrabajadas = horasExtrasTrabajadas;
	}
	
	
}
