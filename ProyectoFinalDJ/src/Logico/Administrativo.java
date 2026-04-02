package Logico;

public class Administrativo extends Personal {
	private String departamento;
	private String nivelAcceso;
	
	public Administrativo(String idEmpleado, String nombre, String cedula, float salarioBase, Usuario miCuenta, String departamento, String nivelAcceso) {
		super(idEmpleado, nombre, cedula, salarioBase, miCuenta);
		this.departamento = departamento;
		this.nivelAcceso = nivelAcceso;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getNivelAcceso() {
		return nivelAcceso;
	}
	public void setNivelAcceso(String nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}
	@Override
	protected float calcularSueldoNeto() {
		return getSalarioBase();
	}
	
	
	
	
}
