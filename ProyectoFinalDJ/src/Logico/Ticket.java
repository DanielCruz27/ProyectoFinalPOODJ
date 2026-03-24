package Logico;

import java.time.LocalDate;

public class Ticket {

	private String idTicket;
	private Cliente elCliente;
	private String areaAtencion;
	private LocalDate horaGeneracion;
	private int prioridad;

	public Ticket(String idTicket, Cliente elCliente, String areaAtencion, LocalDate horaGeneracion, int prioridad) {
		super();
		this.idTicket = idTicket;
		this.elCliente = elCliente;
		this.areaAtencion = areaAtencion;
		this.horaGeneracion = horaGeneracion;
		this.prioridad = prioridad;
	}

	public String getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(String idTicket) {
		this.idTicket = idTicket;
	}

	public Cliente getElCliente() {
		return elCliente;
	}

	public void setElCliente(Cliente elCliente) {
		this.elCliente = elCliente;
	}

	public String getAreaAtencion() {
		return areaAtencion;
	}

	public void setAreaAtencion(String areaAtencion) {
		this.areaAtencion = areaAtencion;
	}

	public LocalDate getHoraGeneracion() {
		return horaGeneracion;
	}

	public void setHoraGeneracion(LocalDate horaGeneracion) {
		this.horaGeneracion = horaGeneracion;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}

}
