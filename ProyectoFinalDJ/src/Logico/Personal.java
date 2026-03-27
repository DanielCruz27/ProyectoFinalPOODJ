package Logico;

public abstract class Personal {
	private String idEmpleado;
	private String nombre;
	private String cedula;
	private float salarioBase;
	
	public Personal(String idEmpleado, String nombre, String cedula, float salarioBase) {
		super();
		this.idEmpleado = idEmpleado;
		this.nombre = nombre;
		this.cedula = cedula;
		this.salarioBase = salarioBase;
	}
	public String getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(String idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public float getSalarioBase() {
		return salarioBase;
	}
	public void setSalarioBase(float salarioBase) {
		this.salarioBase = salarioBase;
	}
	
	protected abstract float calcularSueldoNeto();
	
	
}
