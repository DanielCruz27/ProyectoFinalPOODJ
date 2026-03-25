package Logico;

import java.util.ArrayList;

public class Cliente {

	private String idCliente;
	private String nombreCliente;
	private String apellidoCliente;
	private String emailCliente;
	private String direccionCliente;
	private String zonaVivienda;
	private int puntosAcumulados;
	private boolean estadoCliente;
	private MetodoDePago miMetodo;
	private ArrayList<Pago>misPagos;
	private ArrayList<Contrato>misContratos;
	
	public Cliente(String idCliente, String nombreCliente, String apellidoCliente, String emailCliente,
			String direccionCliente, String zonaVivienda, int puntosAcumulados, boolean estadoCliente,
			MetodoDePago miMetodo, ArrayList<Pago> misPagos, ArrayList<Contrato> misContratos) {
		super();
		this.idCliente = idCliente;
		this.nombreCliente = nombreCliente;
		this.apellidoCliente = apellidoCliente;
		this.emailCliente = emailCliente;
		this.direccionCliente = direccionCliente;
		this.zonaVivienda = zonaVivienda;
		this.puntosAcumulados = puntosAcumulados;
		this.estadoCliente = estadoCliente;
		this.miMetodo = miMetodo;
		this.misPagos = misPagos;
		this.misContratos = misContratos;
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
	public String getEmailCliente() {
		return emailCliente;
	}
	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
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
	public ArrayList<Pago> getMisPagos() {
		return misPagos;
	}
	public void setMisPagos(ArrayList<Pago> misPagos) {
		this.misPagos = misPagos;
	}
	public ArrayList<Contrato> getMisContratos() {
		return misContratos;
	}
	public void setMisContratos(ArrayList<Contrato> misContratos) {
		this.misContratos = misContratos;
	}
	
	

}
