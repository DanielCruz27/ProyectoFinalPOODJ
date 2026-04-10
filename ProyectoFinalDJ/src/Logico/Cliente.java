package Logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idCliente;
	private String nombreCliente;
	private String apellidoCliente;
	private String emailCliente;
	private String direccionCliente;
	private String cedula;
	private Usuario miCuenta;
	private String zonaVivienda;
	private int puntosAcumulados;
	private boolean estadoCliente;
	private MetodoDePago miMetodo;
	private float deudaPendiente;
	private int cantPagosAtrasados;
	private ArrayList<Pago>misPagos;
	private ArrayList<Contrato>misContratos;

	public Cliente(String idCliente, String nombreCliente, String apellidoCliente, String emailCliente,
			String direccionCliente, String cedula,  Usuario miCuenta, String zonaVivienda, int puntosAcumulados, boolean estadoCliente,
			MetodoDePago miMetodo,float deudaPendiente, int cantPagosAtrasados, ArrayList<Pago> misPagos, ArrayList<Contrato> misContratos) {
		super();
		this.idCliente = idCliente;
		this.nombreCliente = nombreCliente;
		this.apellidoCliente = apellidoCliente;
		this.emailCliente = emailCliente;
		this.direccionCliente = direccionCliente;
		this.cedula = cedula;
		this.zonaVivienda = zonaVivienda;
		this.miCuenta = miCuenta;
		this.puntosAcumulados = puntosAcumulados;
		this.estadoCliente = estadoCliente;
		this.miMetodo = miMetodo;
		this.deudaPendiente = deudaPendiente;
		this.cantPagosAtrasados = cantPagosAtrasados;

		if (misPagos == null) {
			this.misPagos = new ArrayList<Pago>();

		} else {
			this.misPagos = misPagos;
		}

		if (misContratos == null) {
			this.misContratos = new ArrayList<Contrato>();

		} else {
			this.misContratos = misContratos;
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
	public float getDeudaPendiente() {
		return deudaPendiente;
	}
	public void setDeudaPendiente(float deudaPendiente) {
		this.deudaPendiente = deudaPendiente;
	}
	public int getCantPagosAtrasados() {
		return cantPagosAtrasados;
	}
	public void setCantPagosAtrasados(int cantPagosAtrasados) {
		this.cantPagosAtrasados = cantPagosAtrasados;
	}
	public Usuario getMiCuenta() {
		return miCuenta;
	}
	public void setMiCuenta(Usuario miCuenta) {
		this.miCuenta = miCuenta;
	}
	public Object getCantidadAtrasos() {
		int atrasos = 0;
	    LocalDate hoy = LocalDate.now();

	    if (misPagos != null) {
	        for (Pago p : misPagos) {
	            // Un atraso es un pago NO realizado Y cuya fecha ya pasó
	            if (!p.isEstadoPago() && p.getFechaEmision().isBefore(hoy)) {
	                atrasos++;
	            }
	        }
	    }
	    return atrasos;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	
		
	
	


}
