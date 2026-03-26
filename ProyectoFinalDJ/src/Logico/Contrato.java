package Logico;

import java.time.LocalDate;
import java.util.ArrayList;

public class Contrato {

	private Cliente elTitular;
	private Servicio planContratado;
	private Personal vendedor;
	private ArrayList<Pago>historialDePagos;
	private LocalDate fechaFirma;

	public Contrato(Cliente elTitular, Servicio planContratado, Personal vendedor, ArrayList<Pago> historialDePagos,
			LocalDate fechaFirma) {
		super();
		this.elTitular = elTitular;
		this.planContratado = planContratado;
		this.vendedor = vendedor;

		if (historialDePagos == null) {
			this.historialDePagos = new ArrayList<Pago>();

		} else {
			this.historialDePagos = historialDePagos;
		}
		this.fechaFirma = fechaFirma;
	}
	public Cliente getElTitular() {
		return elTitular;
	}
	public void setElTitular(Cliente elTitular) {
		this.elTitular = elTitular;
	}
	public Servicio getPlanContratado() {
		return planContratado;
	}
	public void setPlanContratado(Servicio planContratado) {
		this.planContratado = planContratado;
	}
	public Personal getVendedor() {
		return vendedor;
	}
	public void setVendedor(Personal vendedor) {
		this.vendedor = vendedor;
	}
	public ArrayList<Pago> getHistorialDePagos() {
		return historialDePagos;
	}
	public void setHistorialDePagos(ArrayList<Pago> historialDePagos) {
		this.historialDePagos = historialDePagos;
	}
	public LocalDate getFechaFirma() {
		return fechaFirma;
	}
	public void setFechaFirma(LocalDate fechaFirma) {
		this.fechaFirma = fechaFirma;
	}

}
