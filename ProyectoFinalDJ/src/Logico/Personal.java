package Logico;

import java.io.Serializable;

public abstract class Personal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String idEmpleado;
	protected String nombre;
	protected String apellido;
	protected String cedula;
	protected float salarioBase;
	protected Usuario miCuenta;
	protected int estado;

	
	
	public Personal(String idEmpleado, String nombre, String apellido, String cedula, float salarioBase, Usuario miCuenta) {
		super();
		this.idEmpleado = idEmpleado;
		this.nombre = nombre;
		this.apellido = apellido;
		this.cedula = cedula;
		this.salarioBase = salarioBase;
		this.miCuenta = miCuenta;
		this.estado = 1;
	
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
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
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	
	
	
	
}
