package Logico;

import java.io.Serializable;
import java.time.LocalDate;

public class Valoracion implements Serializable {

	private static final long serialVersionUID = 1L;
	private String idValoracion;
	private String comentario;
	private int cantidadEstrellas; // Del 1 al 5
	private LocalDate fecha;
	private Cliente elCliente; // Para saber quién opinó

	public Valoracion(String idValoracion, String comentario, int cantidadEstrellas, LocalDate fecha, Cliente elCliente) {
		super();
		this.idValoracion = idValoracion;
		this.comentario = comentario;
		this.cantidadEstrellas = cantidadEstrellas;
		this.fecha = fecha;
		this.elCliente = elCliente;
	}

	// Getters y Setters
	public String getIdValoracion() {
		return idValoracion;
	}

	public void setIdValoracion(String idValoracion) {
		this.idValoracion = idValoracion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public int getCantidadEstrellas() {
		return cantidadEstrellas;
	}

	public void setCantidadEstrellas(int cantidadEstrellas) {
		this.cantidadEstrellas = cantidadEstrellas;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Cliente getElCliente() {
		return elCliente;
	}

	public void setElCliente(Cliente elCliente) {
		this.elCliente = elCliente;
	}
}