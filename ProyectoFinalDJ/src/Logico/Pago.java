package Logico;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pago {

	

	private String idFactura;
	private LocalDate fechaEmision;
	private float montoTotal;
	private boolean estadoPago;
	private MetodoDePago metodoUtilizado;
	private float itbis;
	private Contrato elContrato;
	
	public Pago(String idFactura, LocalDate fechaEmision, float montoTotal, boolean estadoPago,
			MetodoDePago metodoUtilizado, float itbis, Contrato elContrato) {
		super();
		this.idFactura = idFactura;
		this.fechaEmision = fechaEmision;
		this.montoTotal = montoTotal;
		this.estadoPago = estadoPago;
		this.metodoUtilizado = metodoUtilizado;
		this.itbis = itbis;
		this.elContrato = elContrato;
	}
	public String getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(String idFactura) {
		this.idFactura = idFactura;
	}
	public LocalDate getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(LocalDate fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public float getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(float montoTotal) {
		this.montoTotal = montoTotal;
	}
	public boolean isEstadoPago() {
		return estadoPago;
	}
	public void setEstadoPago(boolean estadoPago) {
		this.estadoPago = estadoPago;
	}
	public MetodoDePago getMetodoUtilizado() {
		return metodoUtilizado;
	}
	public void setMetodoUtilizado(MetodoDePago metodoUtilizado) {
		this.metodoUtilizado = metodoUtilizado;
	}
	public float getItbis() {
		return itbis;
	}
	public void setItbis(float itbis) {
		this.itbis = itbis;
	}
	public Contrato getElContrato() {
		return elContrato;
	}
	public void setElContrato(Contrato elContrato) {
		this.elContrato = elContrato;
	}
	
}
