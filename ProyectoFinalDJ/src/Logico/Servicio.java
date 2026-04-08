package Logico;

import java.io.Serializable;

public abstract class Servicio implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String idServicio;
	protected String nombreServicio;
	protected float precioBase;
	protected boolean estadoDelServicio;
	
	public Servicio(String idServicio, String nombreServicio, float precioBase, boolean estadoDelServicio) {
		super();
		this.idServicio = idServicio;
		this.nombreServicio = nombreServicio;
		this.precioBase = precioBase;
		this.estadoDelServicio = estadoDelServicio;
	}

	public String getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public float getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(float precioBase) {
		this.precioBase = precioBase;
	}

	public boolean isEstadoDelServicio() {
		return estadoDelServicio;
	}

	public void setEstadoDelServicio(boolean estadoDelServicio) {
		this.estadoDelServicio = estadoDelServicio;
	}

}
