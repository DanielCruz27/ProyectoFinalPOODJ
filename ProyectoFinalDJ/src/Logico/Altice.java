package Logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Altice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Altice altice = null;
	private ArrayList<Cliente> listaClientes;
	private ArrayList<Personal> listaEmpleados;
	private ArrayList<Servicio> catalogoServicio;
	private ArrayList<Pago> historialPagos;
	private ArrayList<Ticket> colaDeEspera;
	private ArrayList<Contrato> listaContratos;
	public  int codigoPersonal = 1;
	public  int codigoCliente = 1;
	public  int codigoContrato = 1;
	public  int codigoFactura = 1;

	public Altice() {
		super();
		this.listaClientes = new ArrayList<>();
		this.listaEmpleados = new ArrayList<>();
		this.catalogoServicio = new ArrayList<>();
		this.historialPagos = new ArrayList<>();
		this.colaDeEspera = new ArrayList<>();
		this.listaContratos = new ArrayList<>();
	}
	
	public static Altice getInstance() {
		if(altice==null) {
			altice = new Altice();
		}
		return altice;
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
		int opt = ThreadLocalRandom.current().nextInt(1, 4);
		int number = ThreadLocalRandom.current().nextInt(1000000, 10000000);
		String sufix = "";

		if (opt == 1) {
			sufix = "809";
		} else if (opt == 2) {
			sufix = "849";
		} else if (opt == 3) {
			sufix = "829";
		}
		numero = sufix + number;

		return numero;
	}
	
	public void contratarServicio(String idCliente,String idServicio, String idVendedor) {
		
		Cliente client = buscarCliente(idCliente);
		Servicio service  = buscarServicioById(idServicio);
		Personal vendedor = buscarEmpleadoPorId(idVendedor);
		
		if(client != null && service != null) {
			
			Servicio servicioReal = null;
			
			if(service instanceof PlanMovil) {
				PlanMovil p = (PlanMovil) service;
				servicioReal = new PlanMovil(p.getIdServicio(),p.getNombreServicio(), p.getPrecioBase(), true, generarNumeroTelefonico(), p.getMinutosIncluidos(),p.getRedesLibresIncluidas());
				
			}
			else if(service instanceof PlanHogar) {
				PlanHogar h =(PlanHogar) service;
				servicioReal = new PlanHogar(h.getIdServicio(), h.getNombreServicio(), h.getPrecioBase(), true,generarNumeroTelefonico(), h.getVelocidadInternet(), h.getStreamingIncluido(), h.getMinutosTelefonoHogar());
				
			}
			
			Contrato contratoExistente = buscarContratoPorCliente(idCliente);
			if(contratoExistente != null) {
				
				contratoExistente.getMisServicios().add(servicioReal);
				
			}
			else 
			{
				ArrayList<Servicio> listaInicial = new ArrayList<>();
				listaInicial.add(servicioReal);
				
				Contrato nuevo = new Contrato(client, listaInicial, vendedor,null, LocalDate.now());
				
				listaContratos.add(nuevo);
				client.getMisContratos().add(nuevo);
				
				codigoContrato++;
				
				
			}
		}	
	}
	
	private Contrato buscarContratoPorCliente(String idCliente) {
		Contrato aux = null;
		boolean finded = false;
		int i = 0;
		while (!finded && i < listaContratos.size()) {

			if (listaContratos.get(i).getElTitular().getIdCliente().equalsIgnoreCase(idCliente)) {
				aux = listaContratos.get(i);
				finded = true;
			}
			i++;
		}

		return aux;
	}
	
	public Personal buscarEmpleadoPorId(String idVendedor) {
		Personal aux = null;
		boolean finded = false;
		int i = 0;
		while (!finded && i < listaEmpleados.size()) {

			if (listaEmpleados.get(i).getIdEmpleado().equalsIgnoreCase(idVendedor)) {
				aux = listaEmpleados.get(i);
				finded = true;
			}
			i++;
		}

		return aux;
	}
	private Servicio buscarServicioById(String idServicio) {
		Servicio aux = null;
		boolean finded = false;
		int i = 0;
		while (!finded && i < catalogoServicio.size()) {

			if (catalogoServicio.get(i).getIdServicio().equalsIgnoreCase(idServicio)) {
				aux = catalogoServicio.get(i);
				finded = true;
			}
			i++;
		}

		return aux;
	}
	public boolean realizarRecarga(String numeroTelefonico, int saldoAgregar) {
		boolean done = false;
		Contrato contrato = findContractByNumber(numeroTelefonico);
		if(contrato != null) {
			ArrayList<Servicio> servicios = contrato.getMisServicios();
			int i = 0;
			while (!done && i < servicios.size()) {
				Servicio service = servicios.get(i);
				
				if(service instanceof PlanHogar) {
					PlanHogar ph = (PlanHogar)service;
					if(ph.getNumeroTelefonico().equalsIgnoreCase(numeroTelefonico)) {
						ph.setMinutosTelefonoHogar(ph.getMinutosTelefonoHogar() +saldoAgregar);
						done =  true;
						
					}
				}
				else if(service instanceof PlanMovil) {
					PlanMovil pm = (PlanMovil) service;
					if(pm.getNumeroTelefonico().equalsIgnoreCase(numeroTelefonico)) {
						pm.setMinutosLibres(pm.getMinutosIncluidos() + saldoAgregar);
						done = true;
					}
				}
				
			i++;
			}
			if(done) {
				Pago nuevoPago = new Pago("F-" +codigoFactura,LocalDate.now(),saldoAgregar +(saldoAgregar*0.18f),true,contrato.getElTitular().getMiMetodo(), saldoAgregar* 0.18f,contrato);
				this.historialPagos.add(nuevoPago);
				contrato.getElTitular().getMisPagos().add(nuevoPago);
				codigoFactura++;
			}
		}
		return done;
	}
	
	
	private Contrato findContractByNumber(String numeroTelefonico) {
		Contrato aux = null;
		for (Contrato contract : listaContratos) {
			for(Servicio service : contract.getMisServicios()) {
				if(service instanceof PlanHogar && ((PlanHogar)service).getNumeroTelefonico().equalsIgnoreCase(numeroTelefonico)) {
					aux = contract;
				}
				else if(service instanceof PlanMovil && ((PlanMovil)service).getNumeroTelefonico().equalsIgnoreCase(numeroTelefonico)){
					aux = contract;
				}
			}
		}
		return aux;
	
	}
	public boolean vincularMetodoPago(String idCliente,MetodoDePago metPago) {
		Cliente client = buscarCliente(idCliente);
		boolean aux = false;

		if (client != null) {
			aux = true;
			client.setMiMetodo(metPago);
		}

		return aux;
	}

	public Cliente buscarCliente(String idCliente) {
		Cliente aux = null;
		boolean finded = false;
		int i = 0;
		while (!finded && i < listaClientes.size()) {

			if (listaClientes.get(i).getIdCliente().equalsIgnoreCase(idCliente)) {
				aux = listaClientes.get(i);
				finded = true;
			}
			i++;
		}

		return aux;
	}

	public Cliente buscarClienteByEmail(String email) {
		Cliente aux = null;
		boolean finded = false;
		int i = 0;
		while (!finded && i < listaClientes.size()) {

			if (listaClientes.get(i).getEmailCliente().equalsIgnoreCase(email)) {
				aux = listaClientes.get(i);
				finded =  true;
			}
			i++;
		}

		return aux;
	}

	public void registrarCliente(Cliente cliente) {

		if (buscarClienteByEmail(cliente.getEmailCliente()) == null) {

			listaClientes.add(cliente);
		}

		
		
	}
	public float  reporteGanancias(LocalDate inicio, LocalDate fin) {
		float total = 0;
		
		for(Pago pago: historialPagos) {
			if(pago.getFechaEmision().isAfter(inicio)&&pago.getFechaEmision().isBefore(fin)) { 
				
				
				total += pago.getMontoTotal();
			}
		}
		
		
		return total;
	}
	
	public ArrayList<Cliente> reporteClientePorZona(String areaZone) {
		ArrayList<Cliente> ZoneClients = new ArrayList<>();
		
		for(Cliente client: listaClientes) {
			if(client.getZonaVivienda().equalsIgnoreCase(areaZone)) {
				ZoneClients.add(client);
			}
		}
		
		return ZoneClients;
	}
	
	public int getCodigotPersonal() { 
		
		return codigoPersonal; }
    public void incrementarPersonal() { 
    	
    	codigoPersonal++; }
    
    public int getCodigoCliente() { 
    	
    	return codigoCliente; 
    }
    public void incrementarCliente() { 
    	
    	codigoCliente++;
    	}
    
    
	public void RegistarPersonal(Personal empleado) {
        // Usamos el contador interno del objeto Altice
        empleado.setIdEmpleado("P-" + codigoPersonal); 
        listaEmpleados.add(empleado);
        codigoPersonal++; // Se incrementa y se guardará en el .dat
    }
	
	public void InsertaCliente(Cliente client) {
		listaClientes.add(client);
		codigoCliente++;
		
	}
	public boolean buscarUsuario(String nombreUsuario) {
		boolean finded = false;
		
		for(Cliente aux : listaClientes) {
			if(aux.getMiCuenta().getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
				finded = true;
			}
		}
		
		return finded;
		
	}

	public static void setInstance(Altice temp) {
        altice = temp;
    }
	

}