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
	private ArrayList<MetodoDePago>misMetodos;
	private float deudaPendiente;
	private int cantPagosAtrasados;
	private ArrayList<Pago>misPagos;
	private Contrato miContrato;

	public Cliente(String idCliente, String nombreCliente, String apellidoCliente, String emailCliente,
			String direccionCliente, String cedula,  Usuario miCuenta, String zonaVivienda, int puntosAcumulados, boolean estadoCliente,
			ArrayList<MetodoDePago>misMetodos,float deudaPendiente, int cantPagosAtrasados, ArrayList<Pago> misPagos, Contrato miContrato) {
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
		this.deudaPendiente = deudaPendiente;
		this.cantPagosAtrasados = cantPagosAtrasados;
		this.miContrato = miContrato;

		if (misPagos == null) {
			this.misPagos = new ArrayList<Pago>();

		} else {
			this.misPagos = misPagos;
		}

		if (misMetodos == null) {
			this.misMetodos = new ArrayList<MetodoDePago>();

		} else {
			this.misMetodos = misMetodos;
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

	public ArrayList<Pago> getMisPagos() {
		return misPagos;
	}
	public void setMisPagos(ArrayList<Pago> misPagos) {
		this.misPagos = misPagos;
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

	public int getCantidadAtrasos() {

		int atrasos = 0;
		LocalDate hoy = LocalDate.now();

		if (misPagos != null) {
			for (Pago p : misPagos) {

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
	public Contrato getMiContrato() {
		return miContrato;
	}
	public void setMiContrato(Contrato miContrato) {
		this.miContrato = miContrato;
	}
	public ArrayList<MetodoDePago> getMisMetodos() {
		if (misMetodos == null) {
			misMetodos = new ArrayList<MetodoDePago>();
		}
		return misMetodos;
	}
	public void setMisMetodos(ArrayList<MetodoDePago> misMetodos) {
		this.misMetodos = misMetodos;
	}

	public void addMetodoPago(MetodoDePago metodo) {
		if (misMetodos == null) {
			misMetodos = new ArrayList<MetodoDePago>();
		}
		this.misMetodos.add(metodo);
	}





}
