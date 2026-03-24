package Logico;

import java.util.ArrayList;

public class Altice {

	private ArrayList<Cliente>listaClientes;
	private ArrayList<Personal>listaEmpleados;
	private ArrayList<Servicio>catalogoServicio;
	private ArrayList<Factura>historialFacturas;
	private ArrayList<Ticket>colaDeEspera;

	public Altice(ArrayList<Cliente> listaClientes, ArrayList<Personal> listaEmpleados,
			ArrayList<Servicio> catalogoServicio, ArrayList<Factura> historialFacturas,
			ArrayList<Ticket> colaDeEspera) {
		super();

		if (listaClientes == null) {
			this.listaClientes = new ArrayList<Cliente>();

		} else {
			this.listaClientes = listaClientes;
		}


		if (listaEmpleados == null) {
			this.listaEmpleados = new ArrayList<Personal>();

		} else {
			this.listaEmpleados = listaEmpleados;
		}


		if (catalogoServicio == null) {
			this.catalogoServicio = new ArrayList<Servicio>();

		} else {
			this.catalogoServicio = catalogoServicio;
		}

		if (historialFacturas == null) {
			this.historialFacturas = new ArrayList<Factura>();

		} else {
			this.historialFacturas = historialFacturas;
		}


		if (colaDeEspera == null) {
			this.colaDeEspera = new ArrayList<Ticket>();

		} else {
			this.colaDeEspera = colaDeEspera;
		}
	}

	public ArrayList<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(ArrayList<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public ArrayList<Personal> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(ArrayList<Personal> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}

	public ArrayList<Servicio> getCatalogoServicio() {
		return catalogoServicio;
	}

	public void setCatalogoServicio(ArrayList<Servicio> catalogoServicio) {
		this.catalogoServicio = catalogoServicio;
	}

	public ArrayList<Factura> getHistorialFacturas() {
		return historialFacturas;
	}

	public void setHistorialFacturas(ArrayList<Factura> historialFacturas) {
		this.historialFacturas = historialFacturas;
	}

	public ArrayList<Ticket> getColaDeEspera() {
		return colaDeEspera;
	}

	public void setColaDeEspera(ArrayList<Ticket> colaDeEspera) {
		this.colaDeEspera = colaDeEspera;
	}

}
