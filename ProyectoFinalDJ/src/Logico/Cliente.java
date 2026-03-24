package Logico;

import java.util.ArrayList;

public class Cliente {

	private String idCliente;
	private String nombreCliente;
	private String apellidoCliente;
	private String direccionCliente;
	private String zonaVivienda;
	private int puntosAcumulados;
	private boolean estadoCliente;
	private MetodoDePago miMetodo;
	private ArrayList<Factura>misFacturas;
	private ArrayList<Servicio>serviciosContratados;
	
	public Cliente(String idCliente, String nombreCliente, String apellidoCliente, String direccionCliente,
			String zonaVivienda, int puntosAcumulados, boolean estadoCliente, MetodoDePago miMetodo,
			ArrayList<Factura> misFacturas, ArrayList<Servicio> serviciosContratados) {
		super();
		this.idCliente = idCliente;
		this.nombreCliente = nombreCliente;
		this.apellidoCliente = apellidoCliente;
		this.direccionCliente = direccionCliente;
		this.zonaVivienda = zonaVivienda;
		this.puntosAcumulados = puntosAcumulados;
		this.estadoCliente = estadoCliente;
		this.miMetodo = miMetodo;

		if (misFacturas == null) {
			this.misFacturas = new ArrayList<Factura>();

		} else {
			this.misFacturas = misFacturas;
		}

		if (serviciosContratados == null) {
			this.serviciosContratados = new ArrayList<Servicio>();

		} else {
			this.serviciosContratados = serviciosContratados;
		}
	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getApellidoCliente() {
		return apellidoCliente;
	}
	public void setApellidoCliente(String apellidoCliente) {
		this.apellidoCliente = apellidoCliente;
	}
	public String getDireccionCliente() {
		return direccionCliente;
	}
	public void setDireccionCliente(String direccionCliente) {
		this.direccionCliente = direccionCliente;
	}
	public String getZonaVivienda() {
		return zonaVivienda;
	}
	public void setZonaVivienda(String zonaVivienda) {
		this.zonaVivienda = zonaVivienda;
	}
	public int getPuntosAcumulados() {
		return puntosAcumulados;
	}
	public void setPuntosAcumulados(int puntosAcumulados) {
		this.puntosAcumulados = puntosAcumulados;
	}
	public boolean isEstadoCliente() {
		return estadoCliente;
	}
	public void setEstadoCliente(boolean estadoCliente) {
		this.estadoCliente = estadoCliente;
	}
	public MetodoDePago getMiMetodo() {
		return miMetodo;
	}
	public void setMiMetodo(MetodoDePago miMetodo) {
		this.miMetodo = miMetodo;
	}
	public ArrayList<Factura> getMisFacturas() {
		return misFacturas;
	}
	public void setMisFacturas(ArrayList<Factura> misFacturas) {
		this.misFacturas = misFacturas;
	}
	public ArrayList<Servicio> getServiciosContratados() {
		return serviciosContratados;
	}
	public void setServiciosContratados(ArrayList<Servicio> serviciosContratados) {
		this.serviciosContratados = serviciosContratados;
	}

}
