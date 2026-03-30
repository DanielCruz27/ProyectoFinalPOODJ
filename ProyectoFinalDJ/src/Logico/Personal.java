package Logico;

public abstract class Personal {
	protected String idEmpleado;
	protected String nombre;
	protected String cedula;
	protected float salarioBase;
	protected String usuario;
	protected String contraseña;
	
	
	public Personal(String idEmpleado, String nombre, String cedula, float salarioBase, String usuario, String contraseña) {
		super();
		this.idEmpleado = idEmpleado;
		this.nombre = nombre;
		this.cedula = cedula;
		this.salarioBase = salarioBase;
		this.usuario = usuario;
		this.contraseña = contraseña;
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
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
	protected abstract float calcularSueldoNeto();
	
	
	
}
