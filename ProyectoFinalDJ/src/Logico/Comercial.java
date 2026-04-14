package Logico;

public class Comercial extends Personal {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ventasRealizadas;
	private int comisiones;

	public Comercial(String idEmpleado, String nombre, String apellido, String cedula, float salarioBase, Usuario miCuenta, int ventasRealizadas, int comisiones) {
		super(idEmpleado, nombre,apellido, cedula, salarioBase, miCuenta);
		this.ventasRealizadas = ventasRealizadas;
		this.comisiones = comisiones;
	}

	public int getVentasRealizadas() {
		return ventasRealizadas;
	}
	public void setVentasRealizadas(int ventasRealizadas) {
		this.ventasRealizadas = ventasRealizadas;
	}
	public int getComisiones() {
		return comisiones;
	}
	public void setComisiones(int comisiones) {
		this.comisiones = comisiones;
	}
	@Override
	protected float calcularSueldoNeto() {
		// TODO Auto-generated method stub
		return getSalarioBase() + comisiones;
	}

	@Override
	public String getRol() {
		// TODO Auto-generated method stub
		return "comercial";
	}




}
