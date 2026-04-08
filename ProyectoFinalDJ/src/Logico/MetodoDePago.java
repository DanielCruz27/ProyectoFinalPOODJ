package Logico;

import java.io.Serializable;

public abstract class MetodoDePago implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String nombreTitular;
	protected String idMetodo;
	
	public MetodoDePago(String nombreTitular, String idMetodo) {
		super();
		this.nombreTitular = nombreTitular;
		this.idMetodo = idMetodo;
	}
	public String getNombreTitular() {
		return nombreTitular;
	}
	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}
	public String getIdMetodo() {
		return idMetodo;
	}
	public void setIdMetodo(String idMetodo) {
		this.idMetodo = idMetodo;
	}

}
