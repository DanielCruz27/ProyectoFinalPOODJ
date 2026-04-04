package Logico;

public class Administrativo extends Personal {
	private String departamento;
	
	public Administrativo(String idEmpleado, String nombre, String cedula, float salarioBase, Usuario miCuenta, String departamento) {
		super(idEmpleado, nombre, cedula, salarioBase, miCuenta);
		this.departamento = departamento;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
	@Override
	protected float calcularSueldoNeto() {
		return getSalarioBase();
	}
	
	
	
	
}
