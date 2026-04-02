package Logico;

public class Comercial extends Personal {
	private int ventasRealizadas;
	private int comisiones;
	
	public Comercial(String idEmpleado, String nombre, String cedula, float salarioBase, Usuario miCuenta, int ventasRealizadas, int comisiones) {
		super(idEmpleado, nombre, cedula, salarioBase, miCuenta);
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

	
}
