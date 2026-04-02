package Logico;

public abstract class Personal {
	protected String idEmpleado;
	protected String nombre;
	protected String cedula;
	protected float salarioBase;
	protected Usuario miCuenta;

	
	
	public Personal(String idEmpleado, String nombre, String cedula, float salarioBase, Usuario miCuenta) {
		super();
		this.idEmpleado = idEmpleado;
		this.nombre = nombre;
		this.cedula = cedula;
		this.salarioBase = salarioBase;
		this.miCuenta = miCuenta;
	
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
	
	public Usuario getMiCuenta() {
		return miCuenta;
	}
	public void setMiCuenta(Usuario miCuenta) {
		this.miCuenta = miCuenta;
	}
	
	
	protected abstract float calcularSueldoNeto();
	
	
	
	
}
