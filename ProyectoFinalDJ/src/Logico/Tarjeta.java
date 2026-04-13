package Logico;

import java.time.LocalDate;

public class Tarjeta extends MetodoDePago {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double numeroTarjeta;
	private LocalDate fechaVencimiento;
	private String tipoTarjeta;
	private int codigoSeguridad;
	
	public Tarjeta(String nombreTitular, String idMetodo, double numeroTarjeta, LocalDate fechaVencimiento,
			String tipoTarjeta, int codigoSeguridad) {
		super(nombreTitular, idMetodo);
		this.numeroTarjeta = numeroTarjeta;
		this.fechaVencimiento = fechaVencimiento;
		this.tipoTarjeta = tipoTarjeta;
		this.codigoSeguridad = codigoSeguridad;
	}
	public double getNumeroTarjeta() {
		return numeroTarjeta;
	}
	public void setNumeroTarjeta(double numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public String getTipoTarjeta() {
		return tipoTarjeta;
	}
	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}
	public int getCodigoSeguridad() {
		return codigoSeguridad;
	}
	public void setCodigoSeguridad(int codigoSeguridad) {
		this.codigoSeguridad = codigoSeguridad;
	}
	
	@Override
	public String toString() {
	    return "Número: " + numeroTarjeta; // O lo que quieras mostrar en la tabla
	}

}
