package Logico;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Altice {

	private ArrayList<Cliente>listaClientes;
	private ArrayList<Personal>listaEmpleados;
	private ArrayList<Servicio>catalogoServicio;
	private ArrayList<Pago>historialPagos;
	private ArrayList<Ticket>colaDeEspera;
	private ArrayList<Contrato>listaContratos;
	
	public Altice(ArrayList<Cliente> listaClientes, ArrayList<Personal> listaEmpleados,
			ArrayList<Servicio> catalogoServicio, ArrayList<Pago> historialPagos, ArrayList<Ticket> colaDeEspera,
			ArrayList<Contrato> listaContratos) {
		super();
		this.listaClientes = new ArrayList<>();
		this.listaEmpleados =  new ArrayList<>();
		this.catalogoServicio =  new ArrayList<>();
		this.historialPagos =  new ArrayList<>();
		this.colaDeEspera =  new ArrayList<>();
		this.listaContratos =  new ArrayList<>();
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
	public ArrayList<Pago> getHistorialPagos() {
		return historialPagos;
	}
	public void setHistorialPagos(ArrayList<Pago> historialPagos) {
		this.historialPagos = historialPagos;
	}
	public ArrayList<Ticket> getColaDeEspera() {
		return colaDeEspera;
	}
	public void setColaDeEspera(ArrayList<Ticket> colaDeEspera) {
		this.colaDeEspera = colaDeEspera;
	}
	public ArrayList<Contrato> getListaContratos() {
		return listaContratos;
	}
	public void setListaContratos(ArrayList<Contrato> listaContratos) {
		this.listaContratos = listaContratos;
	}
	
	private String generarNumeroTelefonico() {
		String numero = "";
		int opt = ThreadLocalRandom.current().nextInt(1,4);
		int number = ThreadLocalRandom.current().nextInt(1000000,10000000);
		String sufix = "";
		
		if(opt == 1) {
			sufix = "809";
		}else if(opt ==2) {
			sufix = "849";
		}else if(opt == 3) {
			sufix = "829";
		}
		numero = sufix + number;
		
		return numero;	
	}
	
	public boolean vincularMetodoPago(String idCliente) {
		Cliente client = buscarCliente(idCliente);
		boolean aux=false;
		 
		if(client != null) {
			aux = true;
			
		}
		
		
		return aux;		
	}
	
	public Cliente buscarCliente(String idCliente) {
		Cliente aux = null;
		boolean finded = false;
		int i=0;
		while(!finded||i>listaClientes.size()) {
			
			if(listaClientes.get(i).getIdCliente().equalsIgnoreCase(idCliente)) {
				aux = listaClientes.get(i);
			}
			i++;
		}
		
		
		return aux;
	}
	
	
	
	
	
}