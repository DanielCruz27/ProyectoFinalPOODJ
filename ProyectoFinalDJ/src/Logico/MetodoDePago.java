package Logico;

import java.time.LocalDate;

public class MetodoDePago {

	private String nombreTitularTarjeta;
	private double numeroTarjeta;
	private LocalDate fechaVencimiento;
	private String tipoTarjeta;

	public MetodoDePago(String nombreTitularTarjeta, double numeroTarjeta, LocalDate fechaVencimiento,
			String tipoTarjeta) {
		super();
		this.nombreTitularTarjeta = nombreTitularTarjeta;
		this.numeroTarjeta = numeroTarjeta;
		this.fechaVencimiento = fechaVencimiento;
		this.tipoTarjeta = tipoTarjeta;
	}

	public String getNombreTitularTarjeta() {
		return nombreTitularTarjeta;
	}

	public void setNombreTitularTarjeta(String nombreTitularTarjeta) {
		this.nombreTitularTarjeta = nombreTitularTarjeta;
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

}
