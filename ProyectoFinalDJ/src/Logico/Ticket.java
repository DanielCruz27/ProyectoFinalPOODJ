package Logico;

import java.io.Serializable;
import java.time.LocalDate;

public class Ticket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idTicket;
	private Cliente elCliente;
	private String areaAtencion;
	private LocalDate horaGeneracion;
	private int estado;
	private Tecnico tecnicoAsignado;

	public Ticket(String idTicket, Cliente elCliente, String areaAtencion, LocalDate horaGeneracion, int estado, Tecnico tecnicoAsignado) {
		super();
		this.idTicket = idTicket;
		this.elCliente = elCliente;
		this.areaAtencion = areaAtencion;
		this.horaGeneracion = horaGeneracion;
		this.estado = estado;
		this.tecnicoAsignado = null; 
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

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Tecnico getTecnicoAsignado() {
		return tecnicoAsignado;
	}

	public void setTecnicoAsignado(Tecnico tecnicoAsignado) {
		this.tecnicoAsignado = tecnicoAsignado;
	}


}
